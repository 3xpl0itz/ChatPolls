package com.expl0itz.chatpolls.conversations;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.NumericPrompt;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;

import com.expl0itz.chatpolls.MainChatPolls;
import com.expl0itz.chatpolls.util.EachOption;
import com.expl0itz.chatpolls.util.EachPoll;

public class CHPModifyOptionPrompt extends NumericPrompt{

	private EachPoll currentPoll = null;
	public final MainChatPolls plugin;
	
	public CHPModifyOptionPrompt(MainChatPolls plugin, EachPoll p)
	{
		this.plugin = plugin;
		currentPoll = p;
	}

	@Override
	public String getPromptText(ConversationContext arg0) 
	{
		String out = ChatColor.AQUA + plugin.pluginPrefix + "Choose an option:\n";
		for (EachOption eaOpp : currentPoll.getOptions())
		{
			out += eaOpp.getChoiceNumber() + ") " + eaOpp.getOptionName() + "\n";
		}
		return out + " Which option would you like to choose? (0 to cancel)";
	}

	@Override
	protected Prompt acceptValidatedInput(ConversationContext context, Number input) 
	{
		if (input.intValue() == 0)
		{
			return new CHPModifyExitMessage(plugin); 
		}
		for (EachOption eaOpp : currentPoll.getOptions())
		{
			if (eaOpp.getChoiceNumber() == input.intValue())
			{
				return new CHPModifyOptionPromptP2(plugin, eaOpp, currentPoll, input.intValue());
			}
		}
		return this;
	}

}
