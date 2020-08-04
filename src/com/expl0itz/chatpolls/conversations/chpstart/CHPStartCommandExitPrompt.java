package com.expl0itz.chatpolls.conversations.chpstart;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.MessagePrompt;
import org.bukkit.conversations.Prompt;

import com.expl0itz.chatpolls.MainChatPolls;

public class CHPStartCommandExitPrompt extends MessagePrompt{

	MainChatPolls plugin;
	
	public CHPStartCommandExitPrompt(MainChatPolls p)
	{
		plugin = p;
	}
	
	@Override
	public String getPromptText(ConversationContext context) 
	{
		return ChatColor.AQUA + plugin.pluginPrefix + " Cancelling poll creation...";
	}

	@Override
	protected Prompt getNextPrompt(ConversationContext context) 
	{
		return END_OF_CONVERSATION;
	}

}
