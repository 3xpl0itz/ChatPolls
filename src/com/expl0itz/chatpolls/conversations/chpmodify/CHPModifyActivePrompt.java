package com.expl0itz.chatpolls.conversations.chpmodify;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;

import com.expl0itz.chatpolls.MainChatPolls;
import com.expl0itz.chatpolls.util.EachPoll;

public class CHPModifyActivePrompt extends StringPrompt{

	private EachPoll currPoll = null;
	private boolean setPoll;
	public final MainChatPolls plugin;
	
	public CHPModifyActivePrompt(MainChatPolls plugin, EachPoll p)
	{
		this.plugin = plugin;
		currPoll = p;
	}

	@Override
	public Prompt acceptInput(ConversationContext context, String input) 
	{
		if (input.equals("Y") && setPoll == true)
		{
			currPoll.toggleVotable(true);
			return new CHPModifyActivePromptChanged(plugin, currPoll);
		}
		else if (input.equals("Y") && setPoll == false)
		{
			currPoll.toggleVotable(false);
			return new CHPModifyActivePromptChanged(plugin, currPoll);
		}
		return new CHPModifyStartProcessPrompt(plugin, currPoll);
	}

	@Override
	public String getPromptText(ConversationContext context) 
	{
		String out = ChatColor.AQUA + plugin.pluginPrefix + " ";
		if (currPoll.allowsVoting())
		{
			setPoll = false;
			return out + "This poll allows voting. Would you like to disable voting? (Y, anything else to go to the main menu)";
		}
		else
		{
			setPoll = true;
			return out + "This poll is not yet able to be voted on. Would you like to enable voting? (Y, anything else to go to the main menu)";
		}
	}
	
}
