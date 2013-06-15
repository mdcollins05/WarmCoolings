/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blockmovers.plugins.warmcoolings;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Random;

/**
 *
 * @author MattC
 */
public class Utility {

    Main plugin = null;
    private Random randomGenerator = new Random();

    public Utility(Main plugin) {
        this.plugin = plugin;
    }

    public Integer getLastRun(String player, String command) {
        Integer time = 0;
        if (plugin.playerCommands.containsKey(player)) {
            HashMap<String, Integer> commands = plugin.playerCommands.get(player);
            if (commands.containsKey(command)) {
                time = commands.get(command);
            }
        }
        return time;
    }

    public void setLastRun(String player, String command) {
        HashMap<String, Integer> commands = null;
        if (plugin.playerCommands.containsKey(player)) {
            commands = plugin.playerCommands.get(player);
        }
        if (commands == null) {
            commands = new HashMap();
        }
        commands.put(command, this.getCurrentTime());
        plugin.playerCommands.put(player, commands);
    }

    public Integer getCurrentTime() {
        Calendar cal = new GregorianCalendar();

        return (int) (cal.getTimeInMillis() / 1000L);
    }

    public String timeToString(Integer time) {
        StringBuilder sb = new StringBuilder();
        String s = null;
        Integer temp = 0;

        Integer year = 31536000;
        Integer week = 604800;
        Integer day = 86400;
        Integer hour = 3600;
        Integer minute = 60;

        if (time >= year) {
            temp = (int) Math.floor(time / year);
            sb.append(temp + " year");
            if (temp > 1) {
                sb.append("s");
            }
            sb.append(", ");
            time = (time % year);
        }
        if (time >= week) {
            temp = (int) Math.floor(time / week);
            sb.append(temp + " week");
            if (temp > 1) {
                sb.append("s");
            }
            sb.append(", ");
            time = (time % week);
        }
        if (time >= day) {
            temp = (int) Math.floor(time / day);
            sb.append(temp + " day");
            if (temp > 1) {
                sb.append("s");
            }
            sb.append(", ");
            time = (time % day);
        }
        if (time >= hour) {
            temp = (int) Math.floor(time / hour);
            sb.append(temp + " hour");
            if (temp > 1) {
                sb.append("s");
            }
            sb.append(", ");
            time = (time % hour);
        }
        if (time >= minute) {
            temp = (int) Math.floor(time / minute);
            sb.append(temp + " minute");
            if (temp > 1) {
                sb.append("s");
            }
            sb.append(", ");
            time = (time % minute);
        }
        if (time > 0) {
            sb.append(time + " second");
            if (time > 1) {
                sb.append("s");
            }
            s = sb.toString();
        }
        if (time == 0) {
            s = sb.substring(0, sb.length() - 2);
        }

        return s;
    }
}