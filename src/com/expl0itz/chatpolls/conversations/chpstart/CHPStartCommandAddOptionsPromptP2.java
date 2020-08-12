package com.expl0itz.chatpolls.conversations.chpstart;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;

import com.expl0itz.chatpolls.MainChatPolls;
import com.expl0itz.chatpolls.util.EachPoll;

public class CHPStartCommandAddOptionsPromptP2 extends StringPrompt{
	
	private MainChatPolls plugin;
	private EachPoll currPoll;
	private Player currPlayer;
	
	public CHPStartCommandAddOptionsPromptP2(MainChatPolls plugin, EachPoll curr, Player pl)
	{
		this.plugin = plugin;
		currPoll = curr;
		currPlayer = pl;
	}
	
	@Override
	public Prompt acceptInput(ConversationContext context, String input) 
	{
		if (input.equalsIgnoreCase("exit"))
		{
			return new CHPStartCommandExitPrompt(plugin);
		}
		else if (input.equalsIgnoreCase("back"))
		{
			return new CHPStartCommandAddOptionsPrompt(plugin, currPoll, currPlayer, currPoll.getOptions().size());
		}
		else if (input.equalsIgnoreCase("add"))
		{
			return new CHPStartCommandAddOptionsPrompt(plugin, currPoll, currPlayer);
		}
		return new CHPStartCommandParseSendPrompt(plugin, currPoll, currPlayer);
	}

	@Override
	public String getPromptText(ConversationContext context) 
	{
		return ChatColor.AQUA + plugin.pluginPrefix + " Option added successfully. What would you like to do? (add, cancel, back, anything else to continue)";
	}
}
