package com.expl0itz.chatpolls.commands;

import java.math.BigInteger;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.expl0itz.chatpolls.MainChatPolls;
import com.expl0itz.chatpolls.util.EachOption;
import com.expl0itz.chatpolls.util.EachPoll;

public class CHPInfo extends BasicCommand {

	public CHPInfo(CommandSender sender, Command command, String label, String[] args, MainChatPolls plugin) 
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
		else if (args.length == 0)
		{
			sender.sendMessage(ChatColor.AQUA + plugin.pluginPrefix + " Not enough arguments!");
			sender.sendMessage(ChatColor.AQUA + "Available poll numbers:");
			for (EachPoll eaPoll : plugin.currentPolls)
			{
				sender.sendMessage(ChatColor.AQUA + "- " + eaPoll.getNum());
			}
			return false;
		}
		else if (args.length > 1)
		{
			sender.sendMessage(ChatColor.AQUA + plugin.pluginPrefix + " Too many arguments!");
			return false;
		}
		else if (args.length == 1 && !args[0].chars().allMatch( Character::isDigit )) //check if first arg is a dig + exists
		{
			sender.sendMessage(ChatColor.AQUA + plugin.pluginPrefix + " Please type the number of your desired poll after /chpvote, followed by the number of what option you would like.");
			return true;
		}
		EachPoll currPoll = new EachPoll("","","","",-1);
		for (EachPoll eaPoll : plugin.currentPolls)
		{
			if (eaPoll.getNum() == (Integer.parseInt(args[0])))
			{
				currPoll = eaPoll;
			}
		}
		if (currPoll.getNum() == -1) //if no poll with that number was found
		{
			sender.sendMessage(ChatColor.AQUA + plugin.pluginPrefix + " There is no active poll with the number " + Integer.parseInt(args[0]) + "!");
			sender.sendMessage(ChatColor.AQUA + "Available poll numbers:");
			for (EachPoll eaPoll : plugin.currentPolls)
			{
				sender.sendMessage(ChatColor.AQUA + "- " + eaPoll.getNum());
			}
		return true;
		}
		sender.sendMessage(plugin.colorize(ChatColor.AQUA + plugin.pluginPrefix + " Poll " + currPoll.getNum() + ":\nTitle: " +
		currPoll.getTitle() + "\nDescription: " + currPoll.getDescription()));
		for (EachOption eaOpp : currPoll.getOptions())
		{
			sender.sendMessage(plugin.colorize(ChatColor.AQUA + "" + eaOpp.getChoiceNumber() + ") " + eaOpp.getOptionName() + "\n"));
		}
		sender.sendMessage(plugin.colorize(ChatColor.AQUA + "Created by " + currPoll.getCreator() + " on [" + currPoll.getTime() + "]."));
	return true;
	}
}
