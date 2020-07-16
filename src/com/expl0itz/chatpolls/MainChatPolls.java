package com.expl0itz.chatpolls;

import java.io.File;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.expl0itz.chatpolls.commands.CHPFinish;
import com.expl0itz.chatpolls.commands.CHPInfo;
import com.expl0itz.chatpolls.commands.CHPReload;
import com.expl0itz.chatpolls.commands.CHPStart;
import com.expl0itz.chatpolls.commands.CHPVote;
import com.expl0itz.chatpolls.event.CHPEventHandler;
import com.expl0itz.chatpolls.util.CHPConfigHandler;
import com.expl0itz.chatpolls.util.CHPUpdateChecker;
import com.expl0itz.chatpolls.util.EachOption;
import com.expl0itz.chatpolls.util.EachPoll;

public class MainChatPolls extends JavaPlugin
{
	public static Double pluginVersion = 1.0; //Double instead of prim type so we can set to null
	public static String pluginPrefix = "[ChP]";
	public static ArrayList<EachPoll> currentPolls = new ArrayList<>();
	
	//OnEnable
	@Override
	public void onEnable()
	{
		getServer().getPluginManager().registerEvents(new CHPEventHandler(this), this); //EventHandler
		CHPConfigHandler CHPConfig = new CHPConfigHandler(this); //New Config Object
		CHPConfig.loadConfiguration(); //Load Config
		new CHPUpdateChecker(this, 12345).getVersion(version -> //int = Spigot ID
		{
			if (this.getDescription().getVersion().equalsIgnoreCase(version))
			{
				getLogger().info(pluginPrefix + " No new update available.");
			}
			else
			{
				getLogger().info(pluginPrefix + " There is a new update available! (Current version: " + pluginVersion + ", New version: " + version + ".)");
			}
		});
		getLogger().info("Enabled ChatPolls version " + pluginVersion + "."); //We good
	}
	
	//onDisable
	@Override
	public void onDisable()
	{
		getLogger().info("Disabling ChatPolls version " + pluginVersion + "...");
		currentPolls = null;
		pluginPrefix = null;
		pluginVersion = null;
		//Set all static vars to null, hopefully handles reloads properly :)
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		if (command.getName().equalsIgnoreCase("chpversion"))
		{
			sender.sendMessage(ChatColor.AQUA + pluginPrefix + " ChatPolls by 3xpl0itz, version " + pluginVersion + ".");
			return true;
		}
		else if (command.getName().equalsIgnoreCase("chpstart"))
		{
			CHPStart chpstart = new CHPStart(sender, command, label, args, this);
			return chpstart.processCommand();
		}
		else if (command.getName().equalsIgnoreCase("chpvote"))
		{
			if (sender instanceof ConsoleCommandSender)
			{
				sender.sendMessage("Please don't run this command in console.");
				return true;
			}
			CHPVote chpvote = new CHPVote(sender, command, label, args, this);
			return chpvote.processCommand();
		}
		else if (command.getName().equalsIgnoreCase("chpinfo"))
		{
			CHPInfo chpinfo = new CHPInfo(sender, command, label, args, this);
			return chpinfo.processCommand();
		}
		else if (command.getName().equalsIgnoreCase("chpfinish"))
		{
			CHPFinish chpfinish = new CHPFinish(sender, command, label, args, this);
			return chpfinish.processCommand();
		}
		else if (command.getName().equalsIgnoreCase("chpreload"))
		{
			CHPReload chpreload = new CHPReload(sender, command, label, args, this);
			return chpreload.processCommand();
		}
		return false;
	}
}
