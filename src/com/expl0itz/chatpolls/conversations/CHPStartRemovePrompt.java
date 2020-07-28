package com.expl0itz.chatpolls.conversations;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;

import com.expl0itz.chatpolls.MainChatPolls;
import com.expl0itz.chatpolls.util.EachPoll;

public class CHPStartRemovePrompt extends StringPrompt{

	private MainChatPolls plugin;
	private EachPoll currPoll = null;
	private Player currPlayer;
	
	public CHPStartRemovePrompt(MainChatPolls plugin, EachPoll currentPoll, Player pl)
	{
		this.plugin = plugin;
		this.currPoll = currentPoll;
		currPlayer = pl;
	}
	
	@Override
	public Prompt acceptInput(ConversationContext context, String input) 
	{
		if (input.equalsIgnoreCase("exit"))
		{
			return new CHPStartExitPrompt(plugin);
		}
		else if (currPoll.getRewards().size() == 0)
		{
			return new CHPStartProcessPrompt(plugin, currPoll, currPlayer);
		}
		try
		{
			for (int currReward = 0; currReward < currPoll.getRewards().size(); currReward++)
			{
				if (Integer.parseInt(input) == (currReward+1))
				{
					currPoll.getRewards().remove(currReward);
					return new CHPStartRemovePromptP2(plugin, currPoll, currPlayer, Integer.parseInt(input));
				}
			}
		}
		catch (NumberFormatException e) //if user inputs invalid number
		{
		}
		return this;
	}

	@Override
	public String getPromptText(ConversationContext context) 
	{
		String output = ChatColor.AQUA + plugin.pluginPrefix + "";
		if (!(currPoll.getRewards().size() == 0))
		{
			output += " Current Post-Commands:\n";
			for (int currReward = 0; currReward < currPoll.getRewards().size(); currReward++)
			{
				output += (currReward+1) + ") " + currPoll.getRewards().get(currReward) + "\n";
			}
		}
		else
		{
			output += " There are no commands set for this poll. Please add one first.\n";
		}
		output += "Enter the number of the command you'd like to remove.";
		return output;
	}

}
