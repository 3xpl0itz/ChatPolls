package com.expl0itz.chatpolls.conversations.chpmodify;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;

import com.expl0itz.chatpolls.MainChatPolls;
import com.expl0itz.chatpolls.util.EachOption;
import com.expl0itz.chatpolls.util.EachPoll;

public class CHPModifyChangesOk extends StringPrompt{

	private EachPoll currPoll = null;
	public final MainChatPolls plugin;
	
	public CHPModifyChangesOk(MainChatPolls plugin, EachPoll p)
	{
		this.plugin = plugin;
		currPoll = p;
	}
	
	@Override
	public Prompt acceptInput(ConversationContext context, String input) 
	{
		if (input.equalsIgnoreCase("y"))
		{
			for (EachPoll eaPoll : plugin.currentPolls)
			{
				if (eaPoll.getNum() == currPoll.getNum())
				{
					eaPoll = currPoll;
				}
			}
		}
		else if (input.equalsIgnoreCase("n"))
		{
			for (EachPoll eaPoll : plugin.currentPolls)
			{
				if (eaPoll.getNum() == currPoll.getNum())
				{
					eaPoll = currPoll;
				}
			}
			return new CHPModifyStartProcessPrompt(plugin, currPoll); //back to the beginning
		}
		return new CHPModifyExitMessage(plugin);
	}

	@Override
	public String getPromptText(ConversationContext arg0) 
	{
		String out = (plugin.colorize(ChatColor.AQUA + plugin.pluginPrefix + " Poll " + currPoll.getNum() + ":\nTitle: " +
		currPoll.getTitle() + "\nDescription: " + currPoll.getDescription() + "\n"));
		for (EachOption eaOpp : currPoll.getOptions())
		{
			out += (plugin.colorize(ChatColor.AQUA + "" + eaOpp.getChoiceNumber() + ") " + eaOpp.getOptionName() + "\n"));
		}
		out += (plugin.colorize(ChatColor.AQUA + "Created by " + currPoll.getCreator() + " on [" + currPoll.getTime() + "]."));
		return out + "\nIs this okay? ('Y' for yes and quit, 'N' for change something else, anything else to quit)";
	}

}
