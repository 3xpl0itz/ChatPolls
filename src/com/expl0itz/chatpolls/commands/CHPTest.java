package com.expl0itz.chatpolls.commands;

import java.text.SimpleDateFormat;
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

public class CHPTest extends BasicCommand{

	public CHPTest(CommandSender sender, Command command, String label, String[] args, MainChatPolls plugin) 
	{
		super(sender, command, label, args, plugin);
	}

	public boolean processCommand()
	{
		if (args.length < 3)
		{
			sender.sendMessage(ChatColor.AQUA + plugin.pluginPrefix + " Not enough arguments supplied.");
			return false;
		}
		//TODO: Give the ability to be anonymous
		Date now = new Date();
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		EachPoll currentPoll = new EachPoll(args[0].toString().replaceAll("_", " "),args[1].toString().replaceAll("_", " "), sender.getName(), format.format(now), plugin.currentPolls.size() + 1);
			
		for (int cycleThruOptions = 2; cycleThruOptions < args.length; cycleThruOptions++)
		{
			if (args[cycleThruOptions].toString().equalsIgnoreCase("-clearchat"))
			{
				for (int c = 0; c < 257; c++)
				{
					sender.sendMessage("");
				}
				sender.sendMessage(ChatColor.AQUA + plugin.pluginPrefix + " Chat cleared.");
			}
			else
			{
				EachOption currPollOption = new EachOption(args[cycleThruOptions].toString().replaceAll("_", " "),cycleThruOptions-1);
				currentPoll.addOptions(currPollOption);
			}
		}
		sender.sendMessage(ChatColor.AQUA + plugin.pluginPrefix + " Sending poll to all available users...");
		
		boolean badConfig = false;
		sender.sendMessage(plugin.colorize(ChatColor.AQUA + sender.getName() + " has created a poll: " + currentPoll.getTitle() + "\nDescription: " + currentPoll.getDescription() + "\n"));	
		for (EachOption eaOpp : currentPoll.getOptions())
		{
			sender.sendMessage(plugin.colorize(ChatColor.AQUA + "" + eaOpp.getChoiceNumber() + ") " + eaOpp.getOptionName()));
		}
		sender.sendMessage(ChatColor.AQUA + plugin.pluginPrefix + ChatColor.GOLD + " Use /chpvote " + currentPoll.getNum() + " <yourOption> to vote on this poll!");
	
		//Get sound from config.yml!
		//https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Sound.html <--- put in config.yml
		Player player = (Player) sender; //Cast sender to player; we are only sending it to the sender here
		if (!(plugin.getConfig().getString("ChatPollsSound.StartPoll")).equalsIgnoreCase("None")) //if user does not put none in config, play something
		{
			//Get user-defined sound
			try 
			{
				player.playSound(player.getLocation(),Sound.valueOf(plugin.getConfig().getString("ChatPollsSound.StartPoll")), 10, 1);
			} 
			catch (Exception e) //User defined an illegal sound :(
			{
				player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 10, 1);
				badConfig = true;
			}
		}
		if (badConfig) // :(
		{
			sender.sendMessage(ChatColor.AQUA + plugin.pluginPrefix + " Default anvil sound was played because your config.yml has an invalid sound, or is corrupted."
			+ "\nPlease fix this issue to get rid of this message.\nValid Sounds List: https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Sound.html");
		}
		sender.sendMessage(ChatColor.AQUA + plugin.pluginPrefix + " This poll was a test, and only sent to you.\nRun /chpstart with the same arguments to run an official poll (if you have the appropriate permissions.)");
	return true;
	}
	
}
