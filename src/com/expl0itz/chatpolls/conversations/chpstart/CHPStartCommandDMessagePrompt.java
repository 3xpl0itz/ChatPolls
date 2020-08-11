package com.expl0itz.chatpolls.conversations.chpstart;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.MessagePrompt;
import org.bukkit.conversations.Prompt;
import org.bukkit.entity.Player;

import com.expl0itz.chatpolls.MainChatPolls;
import com.expl0itz.chatpolls.util.EachPoll;

public class CHPStartCommandDMessagePrompt extends MessagePrompt{

	private MainChatPolls plugin;
	private EachPoll currPoll;
	private Player currPlayer;
	private boolean isBadInput;
	
	public CHPStartCommandDMessagePrompt(MainChatPolls plugin, EachPoll curr, Player currPlayer, boolean isBadInput)
	{
		this.plugin = plugin;
		currPoll = curr;
		this.currPlayer = currPlayer;
		this.isBadInput = isBadInput;
	}
	
	@Override
	public String getPromptText(ConversationContext context) 
	{
		if (!isBadInput)
		{
			return ChatColor.AQUA + plugin.pluginPrefix + ChatColor.GOLD + " Set the poll's delay to " + currPoll.getDelay() + " seconds.";
		}
		return ChatColor.AQUA + plugin.pluginPrefix + ChatColor.RED + " Invalid delay entered. Proper usage: delay <positiveNumberInSeconds>";
	}

	@Override
	protected Prompt getNextPrompt(ConversationContext context) 
	{
		return new CHPStartCommandParseSendPrompt(plugin, currPoll, currPlayer);
	}

}
