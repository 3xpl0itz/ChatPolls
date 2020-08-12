package com.expl0itz.chatpolls.conversations.chpmodify;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;

import com.expl0itz.chatpolls.MainChatPolls;
import com.expl0itz.chatpolls.util.EachPoll;

public class CHPModifyDescriptionPrompt extends StringPrompt{

	private EachPoll currentPoll = null;
	public final MainChatPolls plugin;
	
	public CHPModifyDescriptionPrompt(MainChatPolls plugin, EachPoll p)
	{
		this.plugin = plugin;
		currentPoll = p;
	}
	
	@Override
	public Prompt acceptInput(ConversationContext context, String input) {
		if (input.equalsIgnoreCase("quit"))
		{
			return new CHPModifyExitMessage(plugin);
		}
		currentPoll.setDescription(input);
		return new CHPModifyChangesOk(plugin, currentPoll);
	}

	@Override
	public String getPromptText(ConversationContext arg0) {
		// TODO Auto-generated method stub
		return ChatColor.AQUA + plugin.pluginPrefix + " Please enter the new description of your selected poll.";
	}

}
