package com.expl0itz.chatpolls.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.expl0itz.chatpolls.MainChatPolls;
import com.expl0itz.chatpolls.util.EachPoll;

public class CHPList extends BasicCommand{

	public CHPList(CommandSender sender, Command command, String label, String[] args, MainChatPolls plugin) 
	{
		super(sender, command, label, args, plugin);
	}
	
	public boolean processCommand()
	{
		if (plugin.currentPolls.size() == 0)
		{
			sender.sendMessage(ChatColor.AQUA + plugin.pluginPrefix + " There are no active polls!");
			return true;
		}
		
		sender.sendMessage(ChatColor.AQUA + "Available poll numbers:");
		for (EachPoll eaPoll : plugin.currentPolls)
		{
			sender.sendMessage(ChatColor.AQUA + "- " + eaPoll.getNum());
		}
		return true;
	}

}
