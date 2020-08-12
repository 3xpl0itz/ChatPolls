package com.expl0itz.chatpolls.conversations.chpstart;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.MessagePrompt;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

import com.expl0itz.chatpolls.MainChatPolls;
import com.expl0itz.chatpolls.util.EachPoll;

public class CHPStartCommandRMessagePrompt extends MessagePrompt{

	private MainChatPolls plugin;
	private EachPoll currPoll;
	private Player currPlayer;
	private boolean rewards;
	
	public CHPStartCommandRMessagePrompt(MainChatPolls plugin, EachPoll curr, Player pl, boolean r)
	{
		this.plugin = plugin;
		currPoll = curr;
		currPlayer = pl;
		rewards = r;
	}
	
	@Override
	public String getPromptText(ConversationContext context) 
	{
		return ChatColor.AQUA + plugin.pluginPrefix + ChatColor.GOLD + " Rewards for poll set to " + rewards + ".";
	}

	@Override
	protected Prompt getNextPrompt(ConversationContext context) 
	{
		return new CHPStartCommandParseSendPrompt(plugin, currPoll, currPlayer);
	}

}
