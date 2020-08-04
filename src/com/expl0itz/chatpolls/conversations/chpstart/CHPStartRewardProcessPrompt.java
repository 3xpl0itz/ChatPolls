package com.expl0itz.chatpolls.conversations.chpstart;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;

import com.expl0itz.chatpolls.MainChatPolls;
import com.expl0itz.chatpolls.util.EachPoll;

public class CHPStartRewardProcessPrompt extends StringPrompt{

	private MainChatPolls plugin;
	private EachPoll currentPoll = null;
	private Player currPlayer;
	
	public CHPStartRewardProcessPrompt(MainChatPolls plugin, EachPoll currentPoll, Player pl)
	{
		this.plugin = plugin;
		this.currentPoll = currentPoll;
		currPlayer = pl;
	}

	@Override
	public Prompt acceptInput(ConversationContext context, String input) 
	{
		if (input.equals("add"))
		{
			return new CHPStartRewardAddPrompt(plugin, currentPoll, currPlayer);
		}
		else if (input.equals("remove"))
		{
			return new CHPStartRewardRemovePrompt(plugin, currentPoll, currPlayer);
		}
		else if (input.equals("modify"))
		{
			return new CHPStartRewardModifyPrompt(plugin, currentPoll, currPlayer);
		}
		else if (input.equals("list"))
		{
			return new CHPStartRewardListPrompt(plugin, currentPoll, currPlayer);
		}
		else if (input.equals("exit"))
		{
			return new CHPStartRewardExitPrompt(plugin);
		}
		return this;
	}

	@Override
	public String getPromptText(ConversationContext context) 
	{
		return ChatColor.AQUA + plugin.pluginPrefix + " What would you like to do? (add, remove, modify, list, exit)";
	}
	
}
