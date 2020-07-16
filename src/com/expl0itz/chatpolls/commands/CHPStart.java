package com.expl0itz.chatpolls.commands;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.expl0itz.chatpolls.MainChatPolls;
import com.expl0itz.chatpolls.util.EachOption;
import com.expl0itz.chatpolls.util.EachPoll;

public class CHPStart extends BasicCommand{
	
	public CHPStart(CommandSender sender, Command command, String label, String[] args, MainChatPolls plugin) 
	{
		super(sender, command, label, args, plugin);
	}
	
	public boolean processCommand()
	{
		if (args.length < 4)
		{
			sender.sendMessage(ChatColor.AQUA + MainChatPolls.pluginPrefix + " Not enough arguments supplied.");
			return false;
		}
		//TODO: Give the ability to be anonymous
		Date now = new Date();
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		EachPoll currentPoll = new EachPoll(args[0].toString().replaceAll("_", " "),args[1].toString().replaceAll("_", " "), sender.getName(), format.format(now), MainChatPolls.currentPolls.size() + 1);
			
		for (int cycleThruOptions = 2; cycleThruOptions < args.length; cycleThruOptions++)
		{
			if (args[cycleThruOptions].toString().equalsIgnoreCase("-clearchat"))
			{
				for (int c = 0; c < 257; c++)
				{
					Bukkit.broadcastMessage("");
				}
				sender.sendMessage(ChatColor.AQUA + MainChatPolls.pluginPrefix + " Chat cleared.");
			}
			else
			{
				EachOption currPollOption = new EachOption(args[cycleThruOptions].toString().replaceAll("_", " "),cycleThruOptions-1);
				currentPoll.addOptions(currPollOption);
			}
		}
		sender.sendMessage(ChatColor.AQUA + MainChatPolls.pluginPrefix + " Sending poll to all available users...");
		
		boolean badConfig = false;
		for (Player p : Bukkit.getOnlinePlayers())
		{
			p.sendMessage(ChatColor.AQUA + sender.getName() + " has created a poll: " + currentPoll.getTitle() + "\nDescription: " + currentPoll.getDescription() + "\n");
				
			for (EachOption eaOpp : currentPoll.getOptions())
			{
				p.sendMessage(ChatColor.AQUA + "" + eaOpp.getChoiceNumber() + ") " + eaOpp.getOptionName());
			}
			p.sendMessage(ChatColor.AQUA + MainChatPolls.pluginPrefix + ChatColor.GOLD + " Use /chpvote " + currentPoll.getNum() + " <yourOption> to vote on this poll!");
			
			//Get sound from config.yml!
			//https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Sound.html <--- put in config.yml
			if (!(plugin.getConfig().getString("ChatPollsSound.StartPoll")).equalsIgnoreCase("None")) //if user does not put none in config, play something
			{
				//Get user-defined sound
				try 
				{
					p.playSound(p.getLocation(),Sound.valueOf(plugin.getConfig().getString("ChatPollsSound.StartPoll")), 10, 1);
				} 
				catch (Exception e) //User defined an illegal sound :(
				{
					p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_LAND, 10, 1);
					badConfig = true;
				}
			}
		}
		if (badConfig) // :(
		{
			sender.sendMessage(ChatColor.AQUA + MainChatPolls.pluginPrefix + " Default anvil sound was played because your config.yml has an invalid sound, or is corrupted."
			+ "\nPlease fix this issue to get rid of this message.\nValid Sounds List: https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Sound.html");
		}
		MainChatPolls.currentPolls.add(currentPoll);
	return true;
	}
}
