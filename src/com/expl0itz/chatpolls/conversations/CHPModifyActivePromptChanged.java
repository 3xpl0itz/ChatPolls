package com.expl0itz.chatpolls.conversations;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.MessagePrompt;
import org.bukkit.conversations.Prompt;

import com.expl0itz.chatpolls.MainChatPolls;
import com.expl0itz.chatpolls.util.EachPoll;

public class CHPModifyActivePromptChanged extends MessagePrompt{

	public final MainChatPolls plugin;
	private EachPoll currPoll = null;
	
	public CHPModifyActivePromptChanged(MainChatPolls plugin, EachPoll p)
	{
		this.plugin = plugin;
		currPoll = p;
	}
	
	@Override
	public String getPromptText(ConversationContext arg0) 
	{
		return ChatColor.AQUA + plugin.pluginPrefix + " Poll status changed successfully. Returning to main menu.";
	}

	@Override
	protected Prompt getNextPrompt(ConversationContext arg0) 
	{
		return new CHPModifyStartProcessPrompt(plugin, currPoll);
	}

}
