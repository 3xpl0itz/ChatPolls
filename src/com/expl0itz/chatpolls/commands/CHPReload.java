package com.expl0itz.chatpolls.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.expl0itz.chatpolls.MainChatPolls;
import com.expl0itz.chatpolls.util.CHPConfigHandler;

public class CHPReload extends BasicCommand{
	
	public CHPReload(CommandSender sender, Command command, String label, String[] args, MainChatPolls plugin) 
	{
		super(sender, command, label, args, plugin);
	}

	public boolean processCommand()
	{
		CHPConfigHandler CHPConfig = new CHPConfigHandler(plugin);
		CHPConfig.reloadConfiguration();
		sender.sendMessage(ChatColor.AQUA + plugin.pluginPrefix + " Configuration reloaded.");
		return true;
	}
}
