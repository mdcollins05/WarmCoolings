/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blockmovers.plugins.warmcoolings;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 *
 * @author MattC
 */
public class CommandWarmupTask implements Runnable {

    private Main plugin;
    private Player player;
    private Location loc;
    private String command;

    public CommandWarmupTask(Main plugin, Player player, Location loc, String command) {
        this.plugin = plugin;
        this.player = player;
        this.loc = loc;
        this.command = command;
    }

    @Override
    public void run() {
        Location currentLocObj = player.getLocation();
        int currentLoc = currentLocObj.getBlockX() + currentLocObj.getBlockY() + currentLocObj.getBlockZ();
        int originalLoc = loc.getBlockX() + loc.getBlockY() + loc.getBlockZ();
        if (originalLoc != currentLoc) {
            player.sendMessage(ChatColor.RED + "You've moved! The command has been cancelled.");
        } else {
            plugin.getServer().dispatchCommand(player, command.substring(1));
            plugin.util.setLastRun(player.getName(), "/" + command.toLowerCase().split(" ")[0]);
        }
        plugin.getServer().getScheduler().cancelTask(plugin.playerWarmupTaskID.get(player.getName()));
        plugin.playerWarmupTaskID.remove(player.getName());
    }
}
