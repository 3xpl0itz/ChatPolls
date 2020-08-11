package com.expl0itz.chatpolls.commands;

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

public class CHPTest extends BasicCommand{
	
	//Init variables
	private ArrayList<String> inRewards = new ArrayList<>(); //blank rewards array to put into currentPoll; no rewards allowed for a test poll
	private EachPoll currentPoll = new EachPoll("","","","",0,-1,true,false,false,inRewards); //blank poll that we can fill with user-provided data later
	private SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss"); //current date and time in a nice format
	private int currOption = 0; //current option number; incremented for each new option
	
	public CHPTest(CommandSender sender, Command command, String label, String[] args, MainChatPolls plugin) 
	{
		super(sender, command, label, args, plugin);
	}
	
	public boolean processCommand()
	{
		//Check if args are valid
		if (args.length == 0)
		{
			sender.sendMessage(ChatColor.AQUA + plugin.pluginPrefix + " Interactive mode is not supported with /chptest.");
			return false;
		}
		else if (args.length < 6)
		{
			sender.sendMessage(ChatColor.AQUA + plugin.pluginPrefix + " Not enough arguments supplied.");
			return false;
		}
		
		//If all args are valid and we are not interactive, start parsing arguments...
		for (int parseArg = 0; parseArg < args.length; parseArg++)
		{
			if (args[parseArg].toString().equalsIgnoreCase("-t") && (parseArg + 1) < args.length) //parseArg+1<args.length is to avoid going past array bounds when looking for the next term
			{
				//Set title
				currentPoll.setTitle(args[parseArg + 1].toString().replaceAll("_", " "));
				if (currentPoll.getTitle().equals(""))
				{
					sender.sendMessage(ChatColor.AQUA + plugin.pluginPrefix + " Missing title!");
					return false;
				}
				if (currentPoll.getTitle().substring(0,1).equals("-"))
				{
					sender.sendMessage(ChatColor.AQUA + plugin.pluginPrefix + " Title cannot start with a dash!");
					return false;
				}
				parseArg++;
			}
			if (args[parseArg].toString().equalsIgnoreCase("-d") && (parseArg + 1) < args.length)
			{
				//Set description
				currentPoll.setDescription(args[parseArg + 1].toString().replaceAll("_", " "));
				if (currentPoll.getDescription().equals(""))
				{
					sender.sendMessage(ChatColor.AQUA + plugin.pluginPrefix + " Missing description!");
					return false;
				}
				if (currentPoll.getDescription().substring(0,1).equals("-"))
				{
					sender.sendMessage(ChatColor.AQUA + plugin.pluginPrefix + " Description cannot start with a dash!");
					return false;
				}
				parseArg++;
			}
			if (args[parseArg].toString().equalsIgnoreCase("-o") && (parseArg + 1) < args.length)
			{
				//Set option(s)
				EachOption eaOpp = new EachOption((args[parseArg + 1].toString().replaceAll("_", " ")), ++currOption);
				if ((args[parseArg + 1].toString().equals("")))
				{
					sender.sendMessage(ChatColor.AQUA + plugin.pluginPrefix + " Missing option!");
					return false;
				}
				if ((args[parseArg + 1].toString().substring(0,1).equals("-")))
				{
					sender.sendMessage(ChatColor.AQUA + plugin.pluginPrefix + " Option cannot start with a dash!");
					return false;
				}
				parseArg++;
				currentPoll.addOptions(eaOpp);
			}
			if (args[parseArg].toString().equalsIgnoreCase("-delay") && (parseArg + 1) < args.length)
			{
				//Set delay
				if ((args[parseArg + 1].toString().equals("")))
				{
					sender.sendMessage(ChatColor.AQUA + plugin.pluginPrefix + " Missing delay!");
					return false;
				}
				if ((args[parseArg + 1].toString().substring(0,1).equals("-")))
				{
					sender.sendMessage(ChatColor.AQUA + plugin.pluginPrefix + " Delay cannot be negative!");
					return false;
				}
				try
				{
					currentPoll.setDelay(Integer.parseInt(args[parseArg + 1])); 
					sender.sendMessage(ChatColor.AQUA + plugin.pluginPrefix + " Sending poll in " + currentPoll.getDelay() + " seconds. (" + Integer.parseInt(args[parseArg + 1]) + " seconds)");
				}
				catch (Exception e)
				{
					sender.sendMessage(ChatColor.AQUA + plugin.pluginPrefix + " The time you set was not a valid number.");
					return false;
				}
			}
			if (args[parseArg].toString().equalsIgnoreCase("-a"))
			{
				//Enable anonymous mode
				currentPoll.setCreator("Anonymous");
			}
			else
			{
				currentPoll.setCreator(sender.getName());
			}
			if (args[parseArg].toString().equalsIgnoreCase("-clearchat"))
			{
				currentPoll.toggleClearChat(true);
			}
		}
		
		//Last check for invalid entries
		if (currentPoll.getTitle().equals("") || currentPoll.getDescription().equals("") || currentPoll.getOptions().size() == 0)
		{
			sender.sendMessage(ChatColor.AQUA + plugin.pluginPrefix + " Missing -t, -d, or -o!");
			return false;
		}
		//Setup the rest of the poll:::
		currentPoll.setNumber(plugin.currentPolls.size() + 1); //Number of the current poll
		currentPoll.setTime(format.format(new Date())); //Time + Date poll was created
		currentPoll.toggleVotable(false); //Doesn't really matter since the poll won't be added, but test polls can never be voted on
		
		//Start a delayed task; if no delay is set, it will start instantly
		//Test poll is NOT added to official list of polls; no checks needed to see if it is active or not
		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() 
		{
		    @Override
		    public void run() 
		    {
			    sender.sendMessage(ChatColor.AQUA + plugin.pluginPrefix + " Sending test poll...");
			    Player p = (Player)sender; //Send test poll only to sender
			    p.sendMessage(plugin.colorize(ChatColor.AQUA + currentPoll.getCreator() + " has created a poll: " + currentPoll.getTitle() + "\nDescription: " + currentPoll.getDescription() + "\n"));
			    for (EachOption eaOpp : currentPoll.getOptions())
			    {
			    	p.sendMessage(plugin.colorize(ChatColor.AQUA + "" + eaOpp.getChoiceNumber() + ") " + eaOpp.getOptionName()));
			    }
			    p.sendMessage(ChatColor.AQUA + plugin.pluginPrefix + ChatColor.GOLD + " Use /chpvote " + currentPoll.getNum() + " <yourOption> to vote on this poll!");
			    if (currentPoll.isClearChat())
			    {
			    	//Clear the chat before a poll is sent
					for (int c = 0; c < 257; c++)
					{
						sender.sendMessage("");
					}
			    }
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
		}, currentPoll.getDelay()*20);
		return true;
	}
}
