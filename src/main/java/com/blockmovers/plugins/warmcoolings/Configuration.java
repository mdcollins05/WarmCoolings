/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blockmovers.plugins.warmcoolings;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author MattC
 */
public class Configuration {

    Main plugin = null;
    public String seperator = ".";
    public List<String> Warmups = new ArrayList();
    public List<String> Cooldowns = new ArrayList();

    public Configuration(Main plugin) {
        this.plugin = plugin;
    }

    public void reloadConfiguration() {
        plugin.reloadConfig();
        loadConfiguration();
    }

    public void loadConfiguration() {
        List<String> warmups = new ArrayList();
        warmups.add("/spawn,10");
        warmups.add("/heal,3");
        warmups.add("/food,3");
        plugin.getConfig().addDefault("warmups", warmups);
        List<String> cooldowns = new ArrayList();
        cooldowns.add("/spawn,60");
        cooldowns.add("/heal,60");
        cooldowns.add("/food,60");
        plugin.getConfig().addDefault("cooldowns", cooldowns);
        

        plugin.getConfig().options().copyDefaults(true);
        //Save the config whenever you manipulate it
        plugin.saveConfig();

        this.setVars();
    }

    public void setVars() {
        this.Warmups = plugin.getConfig().getStringList("warmups");
        this.Cooldowns = plugin.getConfig().getStringList("cooldowns");
    }
}
