package com.blockmovers.plugins.warmcoolings;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

    Logger log;
    public Configuration config = new Configuration(this);
    public Utility util = new Utility(this);
    //private Random randomGenerator = new Random();
    protected HashMap<String, HashMap> playerCommands = new HashMap();
    protected HashMap<String, Integer> playerWarmupTaskID = new HashMap();

    public void onEnable() {
        this.log = getLogger();
        
        PluginDescriptionFile pdffile = this.getDescription();
        PluginManager pm = this.getServer().getPluginManager(); //the plugin object which allows us to add listeners later on
        
        config.loadConfiguration();

        pm.registerEvents(new Listeners(this), this);

        log.info(pdffile.getName() + " version " + pdffile.getVersion() + " is enabled.");
    }

    public void onDisable() {
        PluginDescriptionFile pdffile = this.getDescription();

        log.info(pdffile.getName() + " version " + pdffile.getVersion() + " is disabled.");
    }

    public boolean onCommand(CommandSender cs, Command cmd, String alias, String[] args) {
        if (cmd.getName().equalsIgnoreCase("pvp")) {
            if (args.length >= 1) {
                if (args[0].equalsIgnoreCase("version")) {
                    PluginDescriptionFile pdf = this.getDescription();
                    cs.sendMessage(pdf.getName() + " " + pdf.getVersion() + " by MDCollins05");
                    return true;
                } else {
                    return false;
                }
            } else {
                // stuff
            }
            return true;
        }
        return false;
    }
    
//    public boolean isInteger(String input) {
//        try {
//            Integer.parseInt(input);
//            return true;
//        } catch (NumberFormatException e) {
//            return false;
//        }
//    }
//
//    public boolean chance(Integer percent, Integer ceiling) {
//        Integer randomInt = this.random(ceiling);
//        if (randomInt < percent) {
//            return true;
//        }
//        return false;
//    }
//
//    public Integer random(Integer ceil) {
//        Integer randomInt = this.randomGenerator.nextInt(ceil * 1000); //moar random?
//        Integer value = randomInt / 1000; //I think so, so now we fix that and round
//        return value;
//    }
}
