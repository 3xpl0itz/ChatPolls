package com.expl0itz.chatpolls.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.expl0itz.chatpolls.MainChatPolls;

public class BasicCommand 
{
	public CommandSender sender;
	public Command command;
	public String label;
	public String[] args;
	public final MainChatPolls plugin;
	
	public BasicCommand(CommandSender sender, Command command, String label, String[] args, MainChatPolls plugin)
	{
		this.sender = sender;
		this.command = command;
		this.label = label;
		this.args = args;
		this.plugin = plugin;
	}

}
