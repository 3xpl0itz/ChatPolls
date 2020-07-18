package com.expl0itz.chatpolls.conversations;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.MessagePrompt;
import org.bukkit.conversations.Prompt;

import com.expl0itz.chatpolls.MainChatPolls;

public class CHPModifyExitMessage extends MessagePrompt {

	public final MainChatPolls plugin;
	
	public CHPModifyExitMessage(MainChatPolls plugin)
	{
		this.plugin = plugin;
	}
	
	@Override
	public String getPromptText(ConversationContext context) 
	{
		// TODO Auto-generated method stub
		return ChatColor.AQUA + plugin.pluginPrefix + " Exited the poll editor.";
	}

	@Override
	protected Prompt getNextPrompt(ConversationContext arg0) 
	{
		// TODO Auto-generated method stub
		return Prompt.END_OF_CONVERSATION;
	}

	

	
	
}
