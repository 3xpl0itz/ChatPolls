package com.expl0itz.chatpolls.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.expl0itz.chatpolls.MainChatPolls;
import com.expl0itz.chatpolls.util.EachOption;
import com.expl0itz.chatpolls.util.EachPoll;
import com.expl0itz.chatpolls.util.EachVote;

public class CHPVote extends BasicCommand{
	
	public CHPVote(CommandSender sender, Command command, String label, String[] args, MainChatPolls plugin) 
	{
		super(sender, command, label, args, plugin);
	}

	public boolean processCommand()
	{
		if (args.length > 2)
		{
			sender.sendMessage(ChatColor.AQUA + plugin.pluginPrefix + " Too many arguments supplied.");
			return false;
		}
		else if (args.length == 0)
		{
			sender.sendMessage(ChatColor.AQUA + plugin.pluginPrefix + " Not enough arguments supplied.");
			return false;
		}
		else if (plugin.currentPolls.size() == 0)
		{
			sender.sendMessage(ChatColor.AQUA + plugin.pluginPrefix + " There are no active polls!");
		    return true;
		}
		else if (args.length == 1 && (!args[0].chars().allMatch( Character::isDigit ))) //check if first arg is a dig + exists
		{
			sender.sendMessage(ChatColor.AQUA + plugin.pluginPrefix + " Please type the number of your desired poll after /chpvote, followed by the number of what option you would like.");
			return true;
		}
		else if ((args.length == 2 && ((!args[0].chars().allMatch( Character::isDigit )) || !args[1].chars().allMatch( Character::isDigit )))) //check if first arg is a dig (not negative as well!) + exists
		{
			sender.sendMessage(ChatColor.AQUA + plugin.pluginPrefix + " Please type the number of your desired poll after /chpvote, followed by the number of what option you would like.");
			return true;
		}
		//Find poll user selected
		EachPoll selectedPoll = new EachPoll("","","","",-1,true,null);
		for (EachPoll eaPoll : plugin.currentPolls)
		{
			if (eaPoll.getNum() == Integer.parseInt(args[0]))
			{
				selectedPoll = eaPoll;
			}
		}
		if (selectedPoll.getNum() == -1)
		{
			sender.sendMessage(ChatColor.AQUA + plugin.pluginPrefix + " Poll " + args[0] + " is not an active poll :(.");
			return true;
		}
		else if (!selectedPoll.allowsVoting())
		{
			sender.sendMessage(ChatColor.AQUA + plugin.pluginPrefix + " Poll " + args[0] + " cannot be voted on yet :(.");
			return true;
		}
		else if ((args.length == 1) && (selectedPoll.getNum() == Integer.parseInt(args[0])))
		{
			sender.sendMessage(ChatColor.AQUA + plugin.pluginPrefix + " Options for poll " + selectedPoll.getNum() + ":" + "\n");
			for (EachOption eaOpp : selectedPoll.getOptions())
			{
				sender.sendMessage(ChatColor.AQUA + "" + eaOpp.getChoiceNumber() + ") " + eaOpp.getOptionName());
			}
			sender.sendMessage(ChatColor.AQUA + plugin.pluginPrefix + " Please type the number of your desired poll after /chpvote, followed by the number of what option you would like.");
			return true;
		}
		else if ((selectedPoll.getNum() == Integer.parseInt(args[0])))
		{
			boolean OppIsValid = false;
			for (EachOption eaOpp : selectedPoll.getOptions())
			{
				if (Integer.parseInt(args[1]) == eaOpp.getChoiceNumber())
				{
					sender.sendMessage(ChatColor.AQUA + plugin.pluginPrefix + " Voting for poll " + selectedPoll.getNum() + "...");
					OppIsValid = true;
				}
			}
			if (OppIsValid == false) //If the user's option is invalid...
			{
				sender.sendMessage(ChatColor.AQUA + plugin.pluginPrefix + " Option " + Integer.parseInt(args[1]) + " is not available on this poll!");
				sender.sendMessage(ChatColor.AQUA + plugin.pluginPrefix + " Options for poll " + selectedPoll.getNum() + ":" + "\n");
				for (EachOption eaOpp : selectedPoll.getOptions())
				{
					sender.sendMessage(ChatColor.AQUA + "" + eaOpp.getChoiceNumber() + ") " + eaOpp.getOptionName());
				}
				sender.sendMessage(ChatColor.AQUA + plugin.pluginPrefix + " Please type the number of your desired poll after /chpvote, followed by the number of what option you would like.");
				return true;
			}
		}
		else if (selectedPoll.getNum() == -1) //-1 is never a valid poll
		{
			sender.sendMessage(ChatColor.AQUA + plugin.pluginPrefix + " No poll with that number found!");
			return true;
		}
		else //whatever cases we don't catch
		{
			sender.sendMessage(ChatColor.AQUA + plugin.pluginPrefix + " No poll with that number found!");
			return true;
		}
			
		for (EachOption eaOpp : selectedPoll.getOptions()) //check if user voted already
		{
			for (EachVote eaVote : eaOpp.getVotes()) //if there is an option that the user already voted for
			{
				if (eaVote.getUsername().equals(sender.getName()))
				{
					sender.sendMessage(ChatColor.AQUA + plugin.pluginPrefix + " You already voted for this poll!");
					return true;
				}
			}
		}
					
		//User has never voted + their vote is valid; add their vote
		for (EachOption eaOption : selectedPoll.getOptions())
		{
			if (eaOption.getChoiceNumber() == Integer.parseInt(args[1]))
			{
				eaOption.addVote((eaOption.getOptionName()), sender.getName());
				sender.sendMessage(ChatColor.AQUA + plugin.pluginPrefix + " If your vote was valid, it was successfully added.");
				if (selectedPoll.getRewards().size() > 0) //dispense rewards :)
				{
					for (String eaReward : selectedPoll.getRewards())
					{
						String outCommand = eaReward.replaceAll("%PLAYER%", sender.getName()); //don't replace %PLAYER% yet
						plugin.getServer().dispatchCommand(Bukkit.getConsoleSender(), outCommand);
					}
				}
			}
		}
	return true;
	}
	
}
