package com.expl0itz.chatpolls.conversations.chpstart;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;

import com.expl0itz.chatpolls.MainChatPolls;
import com.expl0itz.chatpolls.util.EachPoll;

public class CHPStartCommandTitlePrompt extends StringPrompt{

	private MainChatPolls plugin;
	private EachPoll newPoll = new EachPoll("", "", "", "", 0, -1, true, false, false, new ArrayList<String>());
	private Player currPlayer;
	
	public CHPStartCommandTitlePrompt(MainChatPolls plugin, Player pl)
	{
		this.plugin = plugin;
		currPlayer = pl;
	}
	
	public CHPStartCommandTitlePrompt(MainChatPolls plugin, EachPoll curr, Player pl)
	{
		this.plugin = plugin;
		newPoll = curr;
		currPlayer = pl;
	}
	
	@Override
	public Prompt acceptInput(ConversationContext context, String input) 
	{
		if (input.equalsIgnoreCase("exit"))
		{
			return new CHPStartCommandExitPrompt(plugin);
		}
		newPoll.setTitle(input);
		return new CHPStartCommandDescriptionPrompt(plugin, newPoll, currPlayer);
	}

	@Override
	public String getPromptText(ConversationContext context) 
	{
		return ChatColor.AQUA + plugin.pluginPrefix + " Enter the title of your poll. (Type exit to cancel this command.)";
	}

	
	
}
