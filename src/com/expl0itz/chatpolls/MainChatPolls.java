package com.expl0itz.chatpolls;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

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
import com.expl0itz.chatpolls.util.EachPoll;

public class MainChatPolls extends JavaPlugin
{
	public int pluginId = 81542;
	public int bstatsId = 8360;
	public double pluginVersion = 1.3;
	public String pluginPrefix = colorize("[ChP]");
	public ArrayList<EachPoll> currentPolls = new ArrayList<>(); //all active polls
	public Set<BukkitTask> backgroundTasks = new HashSet<BukkitTask>(); //all background tasks that should be cancelled at some point (like during reload) or recorded
	
	//OnEnable
	@Override
	public void onEnable()
	{
		//Load config + vals
		CHPConfigHandler CHPConfig = new CHPConfigHandler(this); 
		CHPConfig.loadConfiguration();
		
		//EventHandler
		getServer().getPluginManager().registerEvents(new CHPEventHandler(this), this);
		
		//We made it!
		getLogger().info("Enabled ChatPolls version " + pluginVersion + "."); 
	}
	
	//onDisable
	@Override
	public void onDisable()
	{
		cancelBackgroundTasks();
		getLogger().info("Disabled ChatPolls version " + pluginVersion + ".");
		//Disable any static args with = null here to deal with /reload properly
	}
	
	//Cancel all active background tasks, useful for /chpreload and onDisable()
	//So far, the following tasks are added to this HashMap:
	//- Update checker process
	public void cancelBackgroundTasks()
	{
		for (BukkitTask task : backgroundTasks) 
		{
			task.cancel();
		}
		backgroundTasks.clear();
	}
	
	//List of Commands
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		if (command.getName().equalsIgnoreCase("chp"))
		{
			sender.sendMessage(ChatColor.AQUA + pluginPrefix + " To see a list of available commands, type /help chatpolls.");
			return true;
		}
		else if (command.getName().equalsIgnoreCase("chpversion"))
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
