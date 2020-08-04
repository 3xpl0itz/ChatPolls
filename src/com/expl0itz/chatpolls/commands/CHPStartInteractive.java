package com.expl0itz.chatpolls.commands;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.conversations.Conversable;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.entity.Player;

import com.expl0itz.chatpolls.MainChatPolls;
import com.expl0itz.chatpolls.conversations.chpstart.CHPStartCommandTitlePrompt;
import com.expl0itz.chatpolls.util.EachOption;
import com.expl0itz.chatpolls.util.EachPoll;

public class CHPStartInteractive extends BasicCommand{
	
	public CHPStartInteractive(CommandSender sender, Command command, String label, String[] args,
			MainChatPolls plugin) 
	{
		super(sender, command, label, args, plugin);
	}

	public boolean interactive()
	{
		int beforeSize = plugin.currentPolls.size(); //this is for if the user cancels the convo
		sender.sendMessage(ChatColor.AQUA + plugin.pluginPrefix + " Not enough arguments supplied. (Conversation support coming soon!)");
		sender.sendMessage(ChatColor.AQUA + plugin.pluginPrefix + " Starting poll creation...");
		Player inPlayer = (Player)sender;
		ConversationFactory cf = new ConversationFactory(plugin)
				.withModality(true)
				.withFirstPrompt(new CHPStartCommandTitlePrompt(plugin, inPlayer))
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
		
		//Get newly created poll
		EachPoll createdPoll = plugin.currentPolls.get(plugin.currentPolls.size()-1);
		Player p = (Player)sender;
		if (!(beforeSize < plugin.currentPolls.size()))
		{
			//Conversation did finish; send final poll to sender
			sender.sendMessage(ChatColor.AQUA + plugin.pluginPrefix + " Your poll was sent successfully:");
			p.sendMessage(plugin.colorize(ChatColor.AQUA + createdPoll.getCreator() + " has created a poll: " + createdPoll.getTitle() + "\nDescription: " + createdPoll.getDescription() + "\n"));
			for (EachOption eaOpp : createdPoll.getOptions())
			{
				p.sendMessage(plugin.colorize(ChatColor.AQUA + "" + eaOpp.getChoiceNumber() + ") " + eaOpp.getOptionName()));
			}
			p.sendMessage(ChatColor.AQUA + plugin.pluginPrefix + ChatColor.GOLD + " Use /chpvote " + createdPoll.getNum() + " <yourOption> to vote on this poll!");
		
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
		return true;
	}
	
}	

