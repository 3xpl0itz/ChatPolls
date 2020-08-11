package com.expl0itz.chatpolls.conversations.chpstart;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;

import com.expl0itz.chatpolls.MainChatPolls;
import com.expl0itz.chatpolls.util.EachOption;
import com.expl0itz.chatpolls.util.EachPoll;

public class CHPStartCommandAddOptionsPrompt extends StringPrompt{

	private MainChatPolls plugin;
	private EachPoll currPoll;
	private int currNum = 0;
	private Player currPlayer;
	private boolean changeExistingOption = false;
	
	public CHPStartCommandAddOptionsPrompt(MainChatPolls plugin, EachPoll curr, Player pl)
	{
		this.plugin = plugin;
		currPoll = curr;
		currPlayer = pl;
		if (curr.getOptions().isEmpty())
		{
			currNum = 1;
		}
		else
		{
			currNum = currPoll.getOptions().size()+1;
		}
	}
	
	public CHPStartCommandAddOptionsPrompt(MainChatPolls plugin, EachPoll curr, Player pl, int n)
	{
		this.plugin = plugin;
		currPoll = curr;
		currPlayer = pl;
		currNum = n;
		changeExistingOption = true;
	}
	
	@Override
	public Prompt acceptInput(ConversationContext context, String input) 
	{
		if (input.equalsIgnoreCase("exit"))
		{
			return new CHPStartCommandExitPrompt(plugin);
		}
		if (changeExistingOption)
		{
			for (EachOption eaOpp : currPoll.getOptions())
			{
				if (eaOpp.getChoiceNumber() == currNum)
				{
					eaOpp.setNameOfOption(input);
				}
			}
		}
		if (input.equalsIgnoreCase("back"))
		{
			return new CHPStartCommandDescriptionPrompt(plugin, currPoll, currPlayer);
		}
		else
		{
			currPoll.addOptions(new EachOption(input, currNum));
		}
		return new CHPStartCommandAddOptionsPromptP2(plugin, currPoll, currPlayer);
	}

	@Override
	public String getPromptText(ConversationContext context) 
	{
		return ChatColor.AQUA + plugin.pluginPrefix + " What would you like to set option " + currNum + " as? (Type exit to cancel this command, or back to go back.)";
	}

}
