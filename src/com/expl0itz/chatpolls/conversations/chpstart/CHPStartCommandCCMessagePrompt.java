package com.expl0itz.chatpolls.conversations.chpstart;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.MessagePrompt;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

import com.expl0itz.chatpolls.MainChatPolls;
import com.expl0itz.chatpolls.util.EachPoll;

public class CHPStartCommandCCMessagePrompt extends MessagePrompt{

	private MainChatPolls plugin;
	private EachPoll currPoll;
	private Player currPlayer;
	private boolean clearChat;
	
	public CHPStartCommandCCMessagePrompt(MainChatPolls plugin, EachPoll curr, Player pl, boolean c)
	{
		this.plugin = plugin;
		currPoll = curr;
		currPlayer = pl;
		clearChat = c;
	}
	
	@Override
	public String getPromptText(ConversationContext context) 
	{
		return ChatColor.AQUA + plugin.pluginPrefix + ChatColor.GOLD + " Clearchat on poll send set to " + clearChat + ".";
	}

	@Override
	protected Prompt getNextPrompt(ConversationContext context) 
	{
		return new CHPStartCommandParseSendPrompt(plugin, currPoll, currPlayer);
	}

}
