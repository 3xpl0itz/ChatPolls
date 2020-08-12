package com.expl0itz.chatpolls.conversations.chpstart;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.conversations.Conversable;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;

import com.expl0itz.chatpolls.MainChatPolls;
import com.expl0itz.chatpolls.util.EachOption;
import com.expl0itz.chatpolls.util.EachPoll;

public class CHPStartCommandParseSendPrompt extends StringPrompt{

	private MainChatPolls plugin;
	private EachPoll currPoll;
	private Player currPlayer;
	private SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss"); //current date and time in a nice format
	//private boolean clearChat, rewards = false;
	
	public CHPStartCommandParseSendPrompt(MainChatPolls plugin, EachPoll curr, Player pl)
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
		currPoll.toggleVotable(false); //Cannot vote on a poll by default before announcement in chat
		String[] parseArgs = input.split(" ");
		
		//Parse user input
		if (parseArgs[0].equalsIgnoreCase("delay"))
		{
			if ((parseArgs.length > 1 && parseArgs[1].chars().allMatch( Character::isDigit) && Integer.parseInt(parseArgs[1]) >= 0))
			{
				currPoll.setDelay(Integer.parseInt(parseArgs[1]));
				return new CHPStartCommandDMessagePrompt(plugin, currPoll, currPlayer, false);
			}
			else
			{
				return new CHPStartCommandDMessagePrompt(plugin, currPoll, currPlayer, true);
			}
		}
		if (parseArgs[0].equalsIgnoreCase("clearchat"))
		{
			currPoll.toggleClearChat(!(currPoll.isClearChat()));
			return new CHPStartCommandCCMessagePrompt(plugin, currPoll, currPlayer, currPoll.isClearChat());
		}
		if (parseArgs[0].equalsIgnoreCase("anonymous"))
		{
			currPoll.setCreator("Anonymous");
			return new CHPStartCommandAMessagePrompt(plugin, currPoll, currPlayer);
		}
		if (parseArgs[0].equalsIgnoreCase("rewards"))
		{
			currPoll.toggleRewards(!(currPoll.hasRewards()));
			return new CHPStartCommandRMessagePrompt(plugin, currPoll, currPlayer, currPoll.hasRewards());
		}
		if (parseArgs[0].equalsIgnoreCase("exit"))
		{
			return new CHPStartCommandExitPrompt(plugin);
		}
		if (parseArgs[0].equalsIgnoreCase("back"))
		{
			return new CHPStartCommandAddOptionsPrompt(plugin, currPoll, currPlayer);
		}
		if (!parseArgs[0].equalsIgnoreCase("done"))
		{
			return this;
		}
		plugin.currentPolls.add(currPoll);
		
		if (currPoll.hasRewards())
		{
			//Enter rewards editor
			ConversationFactory cf = new ConversationFactory(plugin)
					.withModality(true)
					.withFirstPrompt(new CHPStartRewardProcessPrompt(plugin, currPoll, currPlayer))
					.withTimeout(1200) 
					.thatExcludesNonPlayersWithMessage("Go away evil console!");
			try 
			{
				cf.withTimeout(plugin.getConfig().getInt("ChatPollsGeneral.customPollTimeout"));
			}
			catch (Exception e)
			{
				currPlayer.sendMessage(ChatColor.AQUA + plugin.pluginPrefix + " Default 1200 second was enabled because your config is corrupted."
						+ "\nPlease fix this issue to get rid of this message. Continuing...");
			}
			cf.buildConversation((Conversable)currPlayer).begin();
			//return new CHPStartRewardProcessPrompt(plugin, currPoll, currPlayer);
		}
		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() 
		{
		    @Override
		    public void run() 
		    {
		    	for (EachPoll eaPoll : plugin.currentPolls) //if the poll is still active/unfinished, then announce it; otherwise do nothing
				{
					if (eaPoll.getTime() == currPoll.getTime()) 
					{
						currPoll.toggleVotable(true); //allow voting
			    		//Announce poll to all online players
						if (currPoll.isClearChat())
						{
							for (int c = 0; c < 257; c++)
							{
								Bukkit.broadcastMessage("");
							}
						}
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
		}, currPoll.getDelay()*20);
		return new CHPStartCommandExitPrompt(plugin);
	}

	@Override
	public String getPromptText(ConversationContext context) 
	{
		if (currPoll.getCreator().equals(""))
		{
			currPoll.setCreator("3xpl0itz");
		}
		return ChatColor.AQUA + plugin.pluginPrefix + " Currently toggled options:\n" + ChatColor.GOLD + "Will have rewards: " + ChatColor.GREEN + currPoll.hasRewards() 
			+ ChatColor.GOLD + "\nChat will be cleared: " + ChatColor.GREEN + currPoll.isClearChat() + ChatColor.GOLD + "\nCurrent author: " + ChatColor.GREEN + currPoll.getCreator()
			+ ChatColor.GOLD + "\nDelay: " + ChatColor.GREEN + currPoll.getDelay() + " seconds\n" + ChatColor.AQUA +
			"Is there anything you'd like to change?\nValid options: rewards, anonymous, delay <positiveNumberInSeconds>, clearchat, exit, back, 'done' to continue\nExample input: rewards";
	}

}
