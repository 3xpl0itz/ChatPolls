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
		if (args.length == 0)
		{
			sender.sendMessage(ChatColor.AQUA + MainChatPolls.pluginPrefix + " Not enough arguments!");
			return false;
		}
		else if (args.length > 1)
		{
			sender.sendMessage(ChatColor.AQUA + MainChatPolls.pluginPrefix + " Too many arguments!");
			return false;
		}
		else if (!args[0].chars().allMatch( Character::isDigit ) && args.length == 1) //check if first arg is a dig + exists
		{
			sender.sendMessage(ChatColor.AQUA + MainChatPolls.pluginPrefix + " Please type the number of your desired poll after /chpvote, followed by the number of what option you would like.");
			return true;
		}
		else if (MainChatPolls.currentPolls.size() == 0)
		{
			sender.sendMessage(ChatColor.AQUA + MainChatPolls.pluginPrefix + " There are no active polls!");
			return true;
		}
		EachPoll currPoll = new EachPoll("","","","",-1);
		for (EachPoll eaPoll : MainChatPolls.currentPolls)
		{
			if (eaPoll.getNum() == (Integer.parseInt(args[0])))
			{
				currPoll = eaPoll;
			}
		}
		if (currPoll.getNum() == -1) //if no poll with that number was found
		{
			sender.sendMessage(ChatColor.AQUA + MainChatPolls.pluginPrefix + " There is no active poll with the number " + Integer.parseInt(args[0]) + "!");
			sender.sendMessage(ChatColor.AQUA + "Available poll numbers:");
			for (EachPoll eaPoll : MainChatPolls.currentPolls)
			{
				sender.sendMessage(ChatColor.AQUA + "- " + eaPoll.getNum());
			}
		return true;
		}
		sender.sendMessage(ChatColor.AQUA + MainChatPolls.pluginPrefix + " Poll " + currPoll.getNum() + ":\nTitle: " +
		currPoll.getTitle() + "\nDescription: " + currPoll.getDescription());
		for (EachOption eaOpp : currPoll.getOptions())
		{
			sender.sendMessage(ChatColor.AQUA + "" + eaOpp.getChoiceNumber() + ") " + eaOpp.getOptionName() + "\n");
		}
		sender.sendMessage(ChatColor.AQUA + "Created by " + currPoll.getCreator() + " on [" + currPoll.getTime() + "].");
	return true;
	}
}
