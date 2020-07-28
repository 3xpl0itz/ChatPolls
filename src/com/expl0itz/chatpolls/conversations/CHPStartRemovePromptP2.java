package com.expl0itz.chatpolls.conversations;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.MessagePrompt;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

import com.expl0itz.chatpolls.MainChatPolls;
import com.expl0itz.chatpolls.util.EachPoll;

public class CHPStartRemovePromptP2 extends MessagePrompt{

	private MainChatPolls plugin;
	private EachPoll currPoll = null;
	private Player currPlayer;
	private int rewardNum;
	
	public CHPStartRemovePromptP2(MainChatPolls plugin, EachPoll currentPoll, Player pl, int rewardNum)
	{
		this.plugin = plugin;
		this.currPoll = currentPoll;
		currPlayer = pl;
		this.rewardNum = rewardNum;
	}
	
	@Override
	public String getPromptText(ConversationContext context) 
	{
		return ChatColor.AQUA + plugin.pluginPrefix + " Command " + rewardNum + " removed.";
	}

	@Override
	protected Prompt getNextPrompt(ConversationContext context) 
	{
		return new CHPStartProcessPrompt(plugin, currPoll, currPlayer);
	}

}
