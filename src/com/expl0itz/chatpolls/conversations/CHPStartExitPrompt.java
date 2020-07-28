package com.expl0itz.chatpolls.conversations;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.MessagePrompt;
import org.bukkit.conversations.Prompt;

import com.expl0itz.chatpolls.MainChatPolls;

public class CHPStartExitPrompt extends MessagePrompt {

	private MainChatPolls plugin;
	
	public CHPStartExitPrompt(MainChatPolls plugin)
	{
		this.plugin = plugin;
	}
	
	@Override
	public String getPromptText(ConversationContext arg0) 
	{
		return ChatColor.AQUA + plugin.pluginPrefix + " Exited the rewards editor.";
	}

	@Override
	protected Prompt getNextPrompt(ConversationContext arg0) 
	{
		return Prompt.END_OF_CONVERSATION;
	}

	
}
