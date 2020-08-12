package com.expl0itz.chatpolls.conversations.chpstart;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.MessagePrompt;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

import com.expl0itz.chatpolls.MainChatPolls;
import com.expl0itz.chatpolls.util.EachPoll;

public class CHPStartRewardListPrompt extends MessagePrompt{

	private MainChatPolls plugin;
	private EachPoll currPoll = null;
	private Player currPlayer;
	
	public CHPStartRewardListPrompt(MainChatPolls plugin, EachPoll currentPoll, Player pl)
	{
		this.plugin = plugin;
		this.currPoll = currentPoll;
		currPlayer = pl;
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
			output += " There are no commands set for this poll.";
		}
		return output;
	}

	@Override
	protected Prompt getNextPrompt(ConversationContext context) 
	{
		return new CHPStartRewardProcessPrompt(plugin, currPoll, currPlayer);
	}
	
	

}
