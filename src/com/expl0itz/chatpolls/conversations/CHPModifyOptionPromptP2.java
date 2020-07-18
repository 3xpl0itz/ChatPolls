package com.expl0itz.chatpolls.conversations;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;

import com.expl0itz.chatpolls.MainChatPolls;
import com.expl0itz.chatpolls.util.EachOption;
import com.expl0itz.chatpolls.util.EachPoll;

public class CHPModifyOptionPromptP2 extends StringPrompt{

	private int optionValue = 1;
	private EachPoll currentPoll = null;
	private EachOption currentOption = null;
	public final MainChatPolls plugin;
	
	public CHPModifyOptionPromptP2(MainChatPolls plugin, EachOption oo, EachPoll p, int o)
	{
		this.plugin = plugin;
		optionValue = o;
		currentPoll = p;
		currentOption = oo;
	}
	
	@Override
	public Prompt acceptInput(ConversationContext context, String input) 
	{
		if (input.equalsIgnoreCase("quit"))
		{
			return new CHPModifyExitMessage(plugin); 
		}
		currentOption.setNameOfOption(input);
		return new CHPModifyChangesOk(plugin, currentPoll);
	}

	@Override
	public String getPromptText(ConversationContext arg0) 
	{
		return ChatColor.AQUA + plugin.pluginPrefix + " Option " + optionValue + " selected. \nType your replacement text:";
	}

}
