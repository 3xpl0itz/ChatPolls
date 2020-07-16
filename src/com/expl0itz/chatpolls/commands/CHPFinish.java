package com.expl0itz.chatpolls.commands;

import java.math.BigInteger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.expl0itz.chatpolls.MainChatPolls;
import com.expl0itz.chatpolls.util.EachOption;
import com.expl0itz.chatpolls.util.EachPoll;
import com.expl0itz.chatpolls.util.EachVote;

public class CHPFinish extends BasicCommand {

	public CHPFinish(CommandSender sender, Command command, String label, String[] args, MainChatPolls plugin) 
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
		int totalVotes = 0;
		EachPoll finishedPoll = new EachPoll("","","","",-1);
		for (EachPoll eaPoll : MainChatPolls.currentPolls) //find current poll, select it
		{
			if (eaPoll.getNum() == Integer.parseInt(args[0]))
			{
				finishedPoll = eaPoll;
			}
		}
		if (finishedPoll.getNum() == -1)
		{
			sender.sendMessage(ChatColor.AQUA + MainChatPolls.pluginPrefix + " Poll " + args[0] + " is not an active poll :(.");
			return true;
		}
		for (EachOption eaOpp : finishedPoll.getOptions())
		{
			totalVotes+=eaOpp.getNumVotes();
		}
		
		boolean badConfig = false;
		for (Player p : Bukkit.getOnlinePlayers())
		{
			if (totalVotes == 0) //avoid div by 0
			{
				sender.sendMessage(ChatColor.GOLD + "Nobody voted on the poll: " + finishedPoll.getTitle() + " :(.");
				for (int i = 0; i < MainChatPolls.currentPolls.size(); i++)
				{
					if (MainChatPolls.currentPolls.get(i).getNum() == Integer.parseInt(args[0]))
					{
						MainChatPolls.currentPolls.remove(i); //cleanup previous poll
					}
				}
			return true;
			}
			p.sendMessage(ChatColor.GOLD + "The poll " + finishedPoll.getTitle() + " has finished!");
			p.sendMessage(ChatColor.GOLD + "Results:");
		
			//Make a nice infographic for each user
			for (EachOption eaOpp : finishedPoll.getOptions())
			{
				for (EachVote eaVote : eaOpp.getVotes())
				{
					if (eaVote.getUsername().equals(p.getName()))
					{
						p.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "You voted for: " + eaOpp.getChoiceNumber() + ") " + eaOpp.getOptionName()
						+ " - " + "(" + (double)(eaOpp.getNumVotes()/totalVotes)*100 + "%)");
					}
				}
				p.sendMessage(ChatColor.AQUA + "" + eaOpp.getChoiceNumber() + ") "+ eaOpp.getOptionName() + " - "+ "(" + (double)(eaOpp.getNumVotes()/totalVotes)*100 + "%)");
			}
			
			//Get sound from config.yml!
			//https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Sound.html <--- put in config.yml
			if (!(plugin.getConfig().getString("ChatPollsSound.FinishPoll")).equalsIgnoreCase("None")) //if user does not put none in config, play something
			{
				//Get user-defined sound
				try 
				{
					p.playSound(p.getLocation(),Sound.valueOf(plugin.getConfig().getString("ChatPollsSound.FinishPoll")), 10, 1);
				} 
				catch (Exception e) //User defined an illegal sound :(
				{
					p.playSound(p.getLocation(), Sound.ENTITY_BLAZE_DEATH, 10, 1);
					badConfig = true;
				}
			}
		}
		if (badConfig) // :(
		{
			sender.sendMessage(ChatColor.AQUA + MainChatPolls.pluginPrefix + " Default blaze death sound was played because your config.yml has an invalid sound, or is corrupted."
			+ "\nPlease fix this issue to get rid of this message.\nValid Sounds List: https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Sound.html");
		}

		for (int i = 0; i < MainChatPolls.currentPolls.size(); i++)
		{
			if (MainChatPolls.currentPolls.get(i).getNum() == Integer.parseInt(args[0]))
			{
				MainChatPolls.currentPolls.remove(i); //cleanup previous poll
			}
		}
		return true; //Done!
	}
	

}
