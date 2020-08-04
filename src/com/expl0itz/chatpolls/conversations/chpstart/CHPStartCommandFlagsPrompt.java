package com.expl0itz.chatpolls.conversations.chpstart;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;

import com.expl0itz.chatpolls.MainChatPolls;
import com.expl0itz.chatpolls.util.EachOption;
import com.expl0itz.chatpolls.util.EachPoll;

public class CHPStartCommandFlagsPrompt extends StringPrompt{

	private MainChatPolls plugin;
	private EachPoll currPoll;
	private Player currPlayer;
	private SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss"); //current date and time in a nice format
	private int delay = 0;
	
	public CHPStartCommandFlagsPrompt(MainChatPolls plugin, EachPoll curr, Player pl)
	{
		this.plugin = plugin;
		currPoll = curr;
		currPlayer = pl;
	}
	
	@Override
	public Prompt acceptInput(ConversationContext context, String input) 
	{
		//Setup the rest of the poll:::
		currPoll.setNumber(plugin.currentPolls.size() + 1); //Number of the current poll
		currPoll.setTime(format.format(new Date())); //Time + Date poll was created
		currPoll.setVotableFalse(); //Cannot vote on a poll by default before announcement in chat
		
		//Better arg parsing :)
		String[] parseArgs = input.split(" ");
		for (int inc = 0; inc < parseArgs.length; inc++)
		{
			if (parseArgs[inc].equalsIgnoreCase("delay") && (inc + 1) < parseArgs.length)
			{
				//Set delay
				if ((parseArgs[inc + 1].toString().equals("")))
				{
					return this;
				}
				if ((parseArgs[inc + 1].toString().substring(0,1).equals("-")))
				{
					return this;
				}
				try
				{
					delay = 20*(Integer.parseInt(parseArgs[inc + 1])); //convert seconds to ticks
				}
				catch (Exception e)
				{
					return this;
				}
			}
			if (parseArgs[inc].equalsIgnoreCase("rewards"))
			{
				//Enter rewards editor
				return new CHPStartRewardProcessPrompt(plugin, currPoll, currPlayer);
			}
			if (parseArgs[inc].equalsIgnoreCase("clearchat"))
			{
				//Clear the chat before a poll is sent
				for (int c = 0; c < 257; c++)
				{
					Bukkit.broadcastMessage("");
				}
			}
			if (parseArgs[inc].equalsIgnoreCase("anonymous") || (currPoll.getCreator().equalsIgnoreCase("anonymous")))
			{
				//Enable anonymous mode
				currPoll.setCreator("Anonymous");
			}
			else
			{
				currPoll.setCreator(currPlayer.getName());
			}
		}
		plugin.currentPolls.add(currPoll);
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() 
		{
		    @Override
		    public void run() 
		    {
		    	for (EachPoll eaPoll : plugin.currentPolls) //if the poll is still active/unfinished, then announce it; otherwise do nothing
				{
					if (eaPoll.getTime() == currPoll.getTime()) 
					{
						currPoll.setVotableTrue(); //allow voting
			    		//sender.sendMessage(ChatColor.AQUA + plugin.pluginPrefix + " Sending poll to all available users...");
			    		
			    		//Announce poll to all online players
			    		for (Player p : Bukkit.getOnlinePlayers())
			    		{
			    			p.sendMessage(plugin.colorize(ChatColor.AQUA + currPoll.getCreator() + " has created a poll: " + currPoll.getTitle() + "\nDescription: " + currPoll.getDescription() + "\n"));
			    			for (EachOption eaOpp : currPoll.getOptions())
			    			{
			    				p.sendMessage(plugin.colorize(ChatColor.AQUA + "" + eaOpp.getChoiceNumber() + ") " + eaOpp.getOptionName()));
			    			}
			    			p.sendMessage(ChatColor.AQUA + plugin.pluginPrefix + ChatColor.GOLD + " Use /chpvote " + currPoll.getNum() + " <yourOption> to vote on this poll!");
						
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
			    					p.sendMessage(ChatColor.AQUA + plugin.pluginPrefix + " Default anvil sound was played because your config.yml has an invalid sound, or is corrupted."
			    					+ "\nPlease fix this issue to get rid of this message.\nValid Sounds List: https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Sound.html");
			    				}
			    			}
			    		}
					}
				}
		    }
		}, delay);
		return new CHPStartCommandExitPrompt(plugin);
	}

	@Override
	public String getPromptText(ConversationContext context) 
	{
		return ChatColor.AQUA + plugin.pluginPrefix + " Type in each flag you'd like separated by a space, any invalid term for no flags, or exit.\nPossible flags: rewards, anonymous, delay <numberInSeconds>, clearchat\nExample input: rewards anonymous delay 20";
	}

}
