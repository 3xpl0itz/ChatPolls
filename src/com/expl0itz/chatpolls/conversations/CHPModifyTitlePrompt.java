package com.expl0itz.chatpolls.conversations;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;

import com.expl0itz.chatpolls.MainChatPolls;
import com.expl0itz.chatpolls.util.EachPoll;

public class CHPModifyTitlePrompt extends StringPrompt{

	private EachPoll currentPoll = null;
	public final MainChatPolls plugin;
	
	public CHPModifyTitlePrompt(MainChatPolls plugin, EachPoll p)
	{
		this.plugin = plugin;
		currentPoll = p;
	}
	
	@Override
	public Prompt acceptInput(ConversationContext context, String input) 
	{
		if (input.equalsIgnoreCase("quit"))
		{
			return new CHPModifyExitMessage(plugin);
		}
		currentPoll.setTitle(input);
		return new CHPModifyChangesOk(plugin, currentPoll);
	}

	@Override
	public String getPromptText(ConversationContext arg0) 
	{
		return ChatColor.AQUA + plugin.pluginPrefix + " Please enter the new title of your selected poll:";
	}

}
