package com.expl0itz.chatpolls.conversations;

import org.bukkit.ChatColor;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;

import com.expl0itz.chatpolls.MainChatPolls;
import com.expl0itz.chatpolls.util.EachPoll;

public class CHPModifyStartProcessPrompt extends StringPrompt {

	private EachPoll currentPoll = null;
	private Player currPlayer;
	public final MainChatPolls plugin;
	
	public CHPModifyStartProcessPrompt(MainChatPolls plugin, EachPoll p)
	{
		this.plugin = plugin;
		currentPoll = p;
	}
	
	public CHPModifyStartProcessPrompt(MainChatPolls plugin, EachPoll p, Player pl)
	{
		this.plugin = plugin;
		currentPoll = p;
		currPlayer = pl;
	}
	
	@Override
	public Prompt acceptInput(ConversationContext context, String input) 
	{
		if (input.equalsIgnoreCase("description"))
		{
			return new CHPModifyDescriptionPrompt(plugin, currentPoll);
		}
		else if (input.equalsIgnoreCase("title"))
		{
			return new CHPModifyTitlePrompt(plugin, currentPoll);
		}
		else if (input.equalsIgnoreCase("option"))
		{
			return new CHPModifyOptionPrompt(plugin, currentPoll);
		}
		else if (input.equalsIgnoreCase("active"))
		{
			return new CHPModifyActivePrompt(plugin, currentPoll);
		}
		else if (input.equalsIgnoreCase("rewards"))
		{
			return new CHPStartProcessPrompt(plugin, currentPoll, currPlayer); 
		}
		else if (input.equalsIgnoreCase("quit"))
		{
			return new CHPModifyExitMessage(plugin);
		}
		else
		{
			return this;
		}
	}

	@Override
	public String getPromptText(ConversationContext arg0) 
	{
		return ChatColor.AQUA + plugin.pluginPrefix + " Enter what you would like to modify. (title, description, option, active, rewards, quit)";
	}

}
