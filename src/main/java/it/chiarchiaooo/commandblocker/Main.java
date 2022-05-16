package it.chiarchiaooo.commandblocker;


import it.chiarchiaooo.commandblocker.Commands.Arguments.HelpArgument;
import it.chiarchiaooo.commandblocker.Commands.Arguments.ReloadArgument;
import it.chiarchiaooo.commandblocker.Commands.Arguments.RestartArgument;
import it.chiarchiaooo.commandblocker.Commands.MainCommand;
import it.chiarchiaooo.commandblocker.Listeners.CommandEvent;
import it.chiarchiaooo.commandblocker.Listeners.TabCompleteEvent;
import it.chiarchiaooo.commandblocker.utils.CommandHandler;
import it.chiarchiaooo.commandblocker.utils.TabCompleteHandler;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class Main extends JavaPlugin {

    public String sendmsg (Player p,String s) {
        s = ChatColor.translateAlternateColorCodes('&',s.replace("%prefix%",getConfig().getString("prefix")));
        if (p == null) return s;
        s = s.replace("%player%", p.getName());
        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") !=null) {
            return PlaceholderAPI.setPlaceholders(p, s);
        }
        return s;
    }

    public void onEnable() {
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        getLogger().info("");
        getLogger().info(ChatColor.translateAlternateColorCodes('&',"&6&lCommandBlocker"));
        getLogger().info("");
        getLogger().info(ChatColor.translateAlternateColorCodes('&',"&eMade by &6Chiarchiaooo&7 (&6Sussoliny#9971&7)"));
        getLogger().info("");

        CommandHandler handler = new CommandHandler(this); //register cmds
        handler.register("cmdblock",new MainCommand());
        handler.register("restart",new RestartArgument(this));
        handler.register("reload",new ReloadArgument(this));
        handler.register("help",new HelpArgument());
        getCommand("cmdblock").setExecutor(handler);
        getCommand("cmdblock").setTabCompleter(new TabCompleteHandler());

        getServer().getPluginManager().registerEvents(new CommandEvent(this), this); //register events
        getServer().getPluginManager().registerEvents(new TabCompleteEvent(this), this);

        getLogger().info("");
        getLogger().info(ChatColor.translateAlternateColorCodes('&',"&aPlugin successfully enabled "));
        getLogger().info("");
    }

    public void onDisable() {
        getLogger().info(ChatColor.RED + "Disabled ");
    }
}

//  - /cmdblock info: Shows plugin infos
//  - /cmdblock reload: Reload config file
//  - /cmdblock restart: Plugin force restart