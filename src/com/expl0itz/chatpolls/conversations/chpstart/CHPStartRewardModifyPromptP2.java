package com.expl0itz.chatpolls.conversations.chpstart;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;

import com.expl0itz.chatpolls.MainChatPolls;
import com.expl0itz.chatpolls.util.EachPoll;

public class CHPStartRewardModifyPromptP2 extends StringPrompt{

	private MainChatPolls plugin;
	private EachPoll currPoll = null;
	private Player currPlayer;
	private int rewardNum;
	
	public CHPStartRewardModifyPromptP2(MainChatPolls plugin, EachPoll currentPoll, Player pl, int rewardNum)
	{
		this.plugin = plugin;
		this.currPoll = currentPoll;
		currPlayer = pl;
		this.rewardNum = rewardNum;
	}
	
	@Override
	public Prompt acceptInput(ConversationContext context, String input) 
	{
		if (input.equalsIgnoreCase("exit"))
		{
			return new CHPStartRewardExitPrompt(plugin);
		}
		currPoll.getRewards().set(rewardNum-1, input);
		return new CHPStartRewardTestPrompt(plugin, currPoll, currPlayer);
	}

	@Override
	public String getPromptText(ConversationContext context) 
	{
		return ChatColor.AQUA + plugin.pluginPrefix + " Selected reward number " + rewardNum + ".\n"
				+ "Enter the new command for this number.";
	}

}
