package com.expl0itz.chatpolls.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.conversations.Conversable;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.entity.Player;

import com.expl0itz.chatpolls.MainChatPolls;
import com.expl0itz.chatpolls.conversations.CHPModifyStartProcessPrompt;
import com.expl0itz.chatpolls.util.EachPoll;

public class CHPModify extends BasicCommand {

	public CHPModify(CommandSender sender, Command command, String label, String[] args, MainChatPolls plugin) 
	{
		super(sender, command, label, args, plugin);
	}
	
	public boolean processCommand()
	{
		if (args.length == 0)
		{
			sender.sendMessage(ChatColor.AQUA + plugin.pluginPrefix + " Not enough arguments!");
			return false;
		}
		else if (args.length > 1)
		{
			sender.sendMessage(ChatColor.AQUA + plugin.pluginPrefix + " Too many arguments!");
			return false;
		}
		else if (args.length == 1 && !args[0].chars().allMatch( Character::isDigit )) //check if first arg is a dig + exists
		{
			sender.sendMessage(ChatColor.AQUA + plugin.pluginPrefix + " Please type the number of your desired poll after /chpmodify.");
			return true;
		}
		else if (plugin.currentPolls.size() == 0)
		{
			sender.sendMessage(ChatColor.AQUA + plugin.pluginPrefix + " There are no active polls!");
			return true;
		}
		EachPoll currPoll = new EachPoll("","","","",-1,true,null);
		for (EachPoll eaPoll : plugin.currentPolls)
		{
			if (eaPoll.getNum() == (Integer.parseInt(args[0]))) //create a clone to avoid modifying the original poll yet
			{
				currPoll = eaPoll;
			}
		}
		if (currPoll.getNum() == -1) //if no poll with that number was found
		{
			sender.sendMessage(ChatColor.AQUA + plugin.pluginPrefix + " There is no active poll with the number " + Integer.parseInt(args[0]) + "!");
			sender.sendMessage(ChatColor.AQUA + "Available poll numbers:");
			for (EachPoll eaPoll : plugin.currentPolls)
			{
				sender.sendMessage(ChatColor.AQUA + "- " + eaPoll.getNum());
			}
			return true;
		}
		sender.sendMessage(ChatColor.AQUA + plugin.pluginPrefix + " Entered the poll command editor.");
		Player inPlayer = (Player)sender;
		ConversationFactory cf = new ConversationFactory(plugin)
				.withModality(true)
				.withFirstPrompt(new CHPModifyStartProcessPrompt(plugin, currPoll, inPlayer))
				.withTimeout(1200) 
				.thatExcludesNonPlayersWithMessage("Go away evil console!");
		try 
		{
			cf.withTimeout(plugin.getConfig().getInt("ChatPollsGeneral.customPollTimeout"));
		}
		catch (Exception e)
		{
			sender.sendMessage(ChatColor.AQUA + plugin.pluginPrefix + " Default 1200 second was enabled because your config is corrupted."
					+ "\nPlease fix this issue to get rid of this message. Continuing...");
		}
		cf.buildConversation((Conversable)sender).begin();
		sender.sendMessage(ChatColor.AQUA + plugin.pluginPrefix + " Exited the poll editor.");
		return true;
	}
	
}
