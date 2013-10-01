/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blockmovers.plugins.warmcoolings;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 *
 * @author MattC
 */
public class Listeners implements Listener {

    Main plugin = null;

    public Listeners(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        if (event.isCancelled()) {
            return;
        }
        Player p = event.getPlayer();
        String command = event.getMessage().toLowerCase().split(" ")[0];
        String fullCommand = event.getMessage();
        Boolean didCooldown = false;
        if (!p.hasPermission("warmcoolings.cooldown.exempt")) {
            for (String cooldown : plugin.config.Cooldowns) {
                String[] split = cooldown.split(",");
                if (command.startsWith(split[0]) || command.equals(split[0])) {
                    Integer lastRun = plugin.util.getCurrentTime() - plugin.util.getLastRun(p.getName(), plugin.util.removeLeadingSlash(command));
                    if (lastRun >= Integer.valueOf(split[1])) {
                        didCooldown = false;
                    } else {
                        didCooldown = true;
                        p.sendMessage(ChatColor.RED + "You must wait " + plugin.util.timeToString(Integer.valueOf(split[1]) - lastRun) + " until you can use " + command + " again!");
                        break;
                    }
                }
            }
        }
        Boolean didWarmup = false;
        if (!p.hasPermission("warmcoolings.warmup.exempt")) {
            if (!didCooldown) {
                for (String warmup : plugin.config.Warmups) {
                    String[] split = warmup.split(",");
                    if (command.startsWith(split[0]) || command.equals(split[0])) {
                        if (plugin.playerWarmupTaskID.containsKey(p.getName())) {
                            plugin.getServer().getScheduler().cancelTask(plugin.playerWarmupTaskID.get(p.getName()));
                            plugin.playerWarmupTaskID.remove(p.getName());
                        }
                        CommandWarmupTask task = new CommandWarmupTask(plugin, p, p.getLocation(), fullCommand);
                        Integer tid = plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, task, Integer.valueOf(split[1]) * 20l);
                        plugin.playerWarmupTaskID.put(p.getName(), tid);
                        p.sendMessage(ChatColor.RED + "You must wait " + plugin.util.timeToString(Integer.valueOf(split[1])) + " before " + command + " has warmed up!");
                        didWarmup = true;
                        break;
                    }
                }
            }
            if (didWarmup == true || didCooldown == true) {
                event.setCancelled(true);
            } else {
                plugin.util.setLastRun(p.getName(), plugin.util.removeLeadingSlash(command));
            }
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (event.getFrom().getBlock().equals(event.getTo().getBlock())) {
            return;
        }
        Player player = event.getPlayer();
        if (plugin.playerWarmupTaskID.containsKey(player.getName())) {
            player.sendMessage(ChatColor.RED + "You've moved! The command has been cancelled.");
            plugin.getServer().getScheduler().cancelTask(plugin.playerWarmupTaskID.get(player.getName()));
            plugin.playerWarmupTaskID.remove(player.getName());
        }
    }
}
