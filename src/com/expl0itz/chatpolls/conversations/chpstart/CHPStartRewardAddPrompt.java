package com.expl0itz.chatpolls.conversations.chpstart;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;

import com.expl0itz.chatpolls.MainChatPolls;
import com.expl0itz.chatpolls.util.EachPoll;

public class CHPStartRewardAddPrompt extends StringPrompt{

	private MainChatPolls plugin;
	private EachPoll currPoll = null;
	private Player currPlayer;
	
	public CHPStartRewardAddPrompt(MainChatPolls plugin, EachPoll currPoll, Player pl)
	{
		this.currPoll = currPoll;
		this.plugin = plugin;
		currPlayer = pl;
	}

	@Override
	public Prompt acceptInput(ConversationContext context, String input) 
	{
		if (input.equalsIgnoreCase("exit"))
		{
			return new CHPStartRewardExitPrompt(plugin);
		}
		currPoll.addReward(input);
	
		return new CHPStartRewardTestPrompt(plugin, currPoll, currPlayer);
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
			output += " There are no commands set for this poll.\n";
		}
		return output + " Enter the command you'd like to add without the first slash: (exit to quit)\n(Typing %PLAYER% equals the voter's name.)";
	}
	
	
	
}
