package com.expl0itz.chatpolls.commands;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.conversations.Conversable;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.entity.Player;

import com.expl0itz.chatpolls.MainChatPolls;
import com.expl0itz.chatpolls.conversations.CHPStartProcessPrompt;
import com.expl0itz.chatpolls.util.EachOption;
import com.expl0itz.chatpolls.util.EachPoll;

public class CHPStart extends BasicCommand{
	
	public CHPStart(CommandSender sender, Command command, String label, String[] args, MainChatPolls plugin) 
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
		
		Date now = new Date();
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		ArrayList<String> inRewards = new ArrayList<>();
		EachPoll currentPoll = new EachPoll("","","","",-1,true,inRewards);
		boolean wantsAReward = false;
		int currOption = 0;
		int delay = 0;
		
		for (int parseArg = 0; parseArg < args.length; parseArg++)
		{
			if (args[parseArg].toString().equalsIgnoreCase("-t") && (parseArg + 1) < args.length)
			{
				currentPoll.setTitle(args[parseArg + 1].toString().replaceAll("_", " "));
			}
			else if (args[parseArg].toString().equalsIgnoreCase("-d") && (parseArg + 1) < args.length)
			{
				currentPoll.setDescription(args[parseArg + 1].toString().replaceAll("_", " "));
			}
			else if (args[parseArg].toString().equalsIgnoreCase("-o") && (parseArg + 1) < args.length)
			{
				EachOption eaOpp = new EachOption((args[parseArg + 1].toString().replaceAll("_", " ")), ++currOption);
				currentPoll.addOptions(eaOpp);
			}
			else if (args[parseArg].toString().equalsIgnoreCase("-delay") && (parseArg + 1) < args.length)
			{
				try
				{
					delay = 20*(Integer.parseInt(args[parseArg + 1])); //convert seconds to ticks
					sender.sendMessage(ChatColor.AQUA + plugin.pluginPrefix + " Sending poll in " + delay + " ticks. (" + Integer.parseInt(args[parseArg + 1]) + " seconds)");
				}
				catch (Exception e)
				{
					sender.sendMessage(ChatColor.AQUA + plugin.pluginPrefix + " The time you set was not a valid number. Sending poll immediately.");
				}
			}
			else if (args[parseArg].toString().equalsIgnoreCase("-a"))
			{
				currentPoll.setCreator("Anonymous");
			}
			else if (args[parseArg].toString().equalsIgnoreCase("-r"))
			{
				wantsAReward = true;
			}
			else if (args[parseArg].toString().equalsIgnoreCase("-clearchat"))
			{
				for (int c = 0; c < 257; c++)
				{
					Bukkit.broadcastMessage("");
				}
				sender.sendMessage(ChatColor.AQUA + plugin.pluginPrefix + " Chat cleared.");
			}
		}
		if (currentPoll.getTitle().equals("") || currentPoll.getDescription().equals("") || currentPoll.getOptions().size() == 0)
		{
			sender.sendMessage(ChatColor.AQUA + plugin.pluginPrefix + " Missing arguments!");
			return false;
		}
		currentPoll.setNumber(plugin.currentPolls.size() + 1);
		currentPoll.setTime(format.format(now));
		if (!currentPoll.getCreator().equals("Anonymous"))
		{
			currentPoll.setCreator(sender.getName());
		}
		
		plugin.currentPolls.add(currentPoll);
		if (wantsAReward)
		{
			sender.sendMessage(ChatColor.AQUA + plugin.pluginPrefix + " -r detected. Entered the poll command editor.");
			Player inPlayer = (Player)sender;
			ConversationFactory cf = new ConversationFactory(plugin)
					.withModality(true)
					.withFirstPrompt(new CHPStartProcessPrompt(plugin, currentPoll, inPlayer))
					.withTimeout(1200) 
					.thatExcludesNonPlayersWithMessage("Go away evil console!");
			try 
			{
				cf.withTimeout(plugin.getConfig().getInt("ChatPollsGeneral.customPollTimeout"));
			}
			catch (Exception e)
			{
				sender.sendMessage(ChatColor.AQUA + plugin.pluginPrefix + " Default 1200 second was enabled because your config is corrupted."
						+ "\nPlease fix this issue to get rid of this message. Continuing...");
			}
			cf.buildConversation((Conversable)sender).begin();
		}
		
		currentPoll.setVotableFalse();
		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
		    @Override
		    public void run() {
		    	boolean pollIsStillActive = false; //if the poll is /chpfinished before it is sent out to the server, let's not send it.
		    	for (EachPoll eaPoll : plugin.currentPolls)
				{
					if (eaPoll == currentPoll)
					{
						pollIsStillActive = true;
					}
				}
		    	
		    	if (pollIsStillActive)
		    	{
		    		currentPoll.setVotableTrue();
		    		sender.sendMessage(ChatColor.AQUA + plugin.pluginPrefix + " Sending poll to all available users...");
		    		
		    		for (Player p : Bukkit.getOnlinePlayers())
		    		{
		    			p.sendMessage(plugin.colorize(ChatColor.AQUA + currentPoll.getCreator() + " has created a poll: " + currentPoll.getTitle() + "\nDescription: " + currentPoll.getDescription() + "\n"));
						
		    			for (EachOption eaOpp : currentPoll.getOptions())
		    			{
		    				p.sendMessage(plugin.colorize(ChatColor.AQUA + "" + eaOpp.getChoiceNumber() + ") " + eaOpp.getOptionName()));
		    			}
		    			p.sendMessage(ChatColor.AQUA + plugin.pluginPrefix + ChatColor.GOLD + " Use /chpvote " + currentPoll.getNum() + " <yourOption> to vote on this poll!");
					
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
		    					sender.sendMessage(ChatColor.AQUA + plugin.pluginPrefix + " Default anvil sound was played because your config.yml has an invalid sound, or is corrupted."
		    					+ "\nPlease fix this issue to get rid of this message.\nValid Sounds List: https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Sound.html");
		    				}
		    			}
		    		}
		    	}
		    }
		}, delay);
		return true;
	}
}
