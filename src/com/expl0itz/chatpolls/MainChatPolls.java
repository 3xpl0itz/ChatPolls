package com.expl0itz.chatpolls;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import com.expl0itz.chatpolls.commands.CHPFinish;
import com.expl0itz.chatpolls.commands.CHPInfo;
import com.expl0itz.chatpolls.commands.CHPList;
import com.expl0itz.chatpolls.commands.CHPModify;
import com.expl0itz.chatpolls.commands.CHPReload;
import com.expl0itz.chatpolls.commands.CHPStart;
import com.expl0itz.chatpolls.commands.CHPTest;
import com.expl0itz.chatpolls.commands.CHPVote;
import com.expl0itz.chatpolls.event.CHPEventHandler;
import com.expl0itz.chatpolls.util.CHPConfigHandler;
import com.expl0itz.chatpolls.util.CHPUpdateChecker;
import com.expl0itz.chatpolls.util.EachPoll;
import com.expl0itz.chatpolls.util.Metrics;

public class MainChatPolls extends JavaPlugin
{
	public int pluginId = 81542;
	public int bstatsId = 8360;
	public String pluginPrefix = colorize("[ChP]");
	public Double pluginVersion = 1.21; //Double instead of prim type so we can set to null
	public ArrayList<EachPoll> currentPolls = new ArrayList<>();
	
	//OnEnable
	@Override
	public void onEnable()
	{
		//New Config Object
		CHPConfigHandler CHPConfig = new CHPConfigHandler(this); 
		
		//EventHandler
		getServer().getPluginManager().registerEvents(new CHPEventHandler(this), this);
		
		//Load Config
		CHPConfig.loadConfiguration();
		
		//Update Checker (decent, finally)
		try 
		{
			new CHPUpdateChecker(this, pluginId).getVersion(version -> //int = Spigot ID
			{
				if (Double.parseDouble(this.getDescription().getVersion()) <= pluginVersion) //convert to double
				{
					getLogger().info("No new update available.");
				}
				else
				{
					getLogger().info("There is a new update available! (Current version: " + pluginVersion + ", New version: " + version + ".)");
				}
			});
		}
		catch (Exception e)
		{
			getLogger().info("Unhandled exception while checking for updates. Please try again later.");
		}
		
		//Load Prefix from Config
		try
		{
			pluginPrefix = colorize(getConfig().getString("ChatPollsGeneral.prefixName"));
		}
		catch (Exception e)
		{
			getLogger().info("Error reading ChatPollsGeneral.prefixName from your config.plist. \nPlease fix this manually, or re-generate your config. Using default prefix.");
		}
		
		//BStats!
		try
		{
			if (getConfig().getBoolean("ChatPollsGeneral.enablebStats"))
			{
				Metrics metrics = new Metrics(this, bstatsId);
			}
		}
		catch (Exception e)
		{
			getLogger().info("Error reading ChatPollsGeneral.enablebStats from your config.plist. \nPlease fix this manually, or re-generate your config. Using default prefix.");
		}
		
		//We made it!
		getLogger().info("Enabled ChatPolls version " + pluginVersion + "."); 
	}
	
	//onDisable
	@Override
	public void onDisable()
	{
		getLogger().info("Disabled ChatPolls version " + pluginVersion + ".");
		//Disable any static args with = null here to deal with /reload properly
	}
	
	//List of Commands
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		if (command.getName().equalsIgnoreCase("chpversion"))
		{
			sender.sendMessage(ChatColor.AQUA + pluginPrefix + " ChatPolls by 3xpl0itz, version " + pluginVersion + ".");
			return true;
		}
		else if (command.getName().equalsIgnoreCase("chpstart"))
		{
			if (sender instanceof ConsoleCommandSender)
			{
				sender.sendMessage("Please don't run this command in console.");
				return true;
			}
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
		else if (command.getName().equalsIgnoreCase("chplist"))
		{
			CHPList chplist = new CHPList(sender, command, label, args, this);
			return chplist.processCommand();
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
		else if (command.getName().equalsIgnoreCase("chpmodify"))
		{
			if (sender instanceof ConsoleCommandSender)
			{
				sender.sendMessage("Please don't run this command in console.");
				return true;
			}
			CHPModify chpmodify = new CHPModify(sender, command, label, args, this);
			return chpmodify.processCommand();
		}
		else if (command.getName().equalsIgnoreCase("chptest"))
		{
			if (sender instanceof ConsoleCommandSender)
			{
				sender.sendMessage("Please don't run this command in console.");
				return true;
			}
			CHPTest chptest = new CHPTest(sender, command, label, args, this);
			return chptest.processCommand();
		}
		return false;
	}
	
	public String colorize(String msg) //this lets users use color codes :)
	{
	    String coloredMsg = "";
	    for(int i = 0; i < msg.length(); i++)
	    {
	        if(msg.charAt(i) == '&')
	            coloredMsg += '§';
	        else
	            coloredMsg += msg.charAt(i);
	    }
	    return coloredMsg;
	}
}
