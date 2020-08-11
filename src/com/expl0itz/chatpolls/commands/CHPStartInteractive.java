package com.expl0itz.chatpolls.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.conversations.Conversable;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.entity.Player;

import com.expl0itz.chatpolls.MainChatPolls;
import com.expl0itz.chatpolls.conversations.chpstart.CHPStartCommandTitlePrompt;

public class CHPStartInteractive extends BasicCommand{
	
	public CHPStartInteractive(CommandSender sender, Command command, String label, String[] args,
			MainChatPolls plugin) 
	{
		super(sender, command, label, args, plugin);
	}

	public boolean interactive()
	{
		//Start interactive conversation with player
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
		return true;
	}
	
}	

