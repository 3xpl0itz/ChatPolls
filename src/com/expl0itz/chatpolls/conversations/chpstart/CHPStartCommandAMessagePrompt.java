package com.expl0itz.chatpolls.conversations.chpstart;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.MessagePrompt;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

import com.expl0itz.chatpolls.MainChatPolls;
import com.expl0itz.chatpolls.util.EachPoll;

public class CHPStartCommandAMessagePrompt extends MessagePrompt{

	private MainChatPolls plugin;
	private EachPoll currPoll;
	private Player currPlayer;
	
	public CHPStartCommandAMessagePrompt(MainChatPolls plugin, EachPoll curr, Player pl)
	{
		this.plugin = plugin;
		currPoll = curr;
		currPlayer = pl;
	}
	
	@Override
	public String getPromptText(ConversationContext context) 
	{
		return ChatColor.AQUA + plugin.pluginPrefix + ChatColor.GOLD + " Made the poll's creator anonymous.";
	}

	@Override
	protected Prompt getNextPrompt(ConversationContext context) 
	{
		return new CHPStartCommandParseSendPrompt(plugin, currPoll, currPlayer);
	}

}
