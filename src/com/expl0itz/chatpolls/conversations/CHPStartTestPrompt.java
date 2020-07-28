package com.expl0itz.chatpolls.conversations;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.MessagePrompt;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

import com.expl0itz.chatpolls.MainChatPolls;
import com.expl0itz.chatpolls.util.EachPoll;

public class CHPStartTestPrompt extends MessagePrompt{

	private MainChatPolls plugin;
	private EachPoll currentPoll = null;
	private Player currPlayer;
	
	public CHPStartTestPrompt(MainChatPolls plugin, EachPoll currentPoll, Player pl)
	{
		this.plugin = plugin;
		this.currentPoll = currentPoll;
		currPlayer = pl;
	}

	@Override
	public String getPromptText(ConversationContext context) 
	{
		return ChatColor.AQUA + plugin.pluginPrefix + " Testing commands and returning to main menu...if your command doesn't work as expected, modify or remove it.";
	}

	@Override
	protected Prompt getNextPrompt(ConversationContext arg0) {
	for (String eaCommand : currentPoll.getRewards())
		{
			String outCommand = eaCommand.replaceAll("%PLAYER%", currPlayer.getName()); //don't replace %PLAYER% yet
			plugin.getServer().dispatchCommand(Bukkit.getConsoleSender(), outCommand);
		}
	return new CHPStartProcessPrompt(plugin, currentPoll, currPlayer);
	}

}
