package com.expl0itz.chatpolls.event;

import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

import com.expl0itz.chatpolls.MainChatPolls;

public class CHPEventHandler implements Listener{
	
	public final MainChatPolls plugin;
	
	public CHPEventHandler(MainChatPolls plugin)
	{
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerJoinEvent(PlayerJoinEvent event)
	{
		try 
		{
			if (plugin.getConfig().getBoolean("ChatPollsGeneral.notifyUserOnLogin"))
			{
				Player p = event.getPlayer();
				if (plugin.currentPolls.size() > 0)
				{
					p.sendMessage(ChatColor.AQUA + plugin.pluginPrefix + " There are (" + plugin.currentPolls.size() + ")" + " active polls:\n");
					for (int i = 0; i < plugin.currentPolls.size(); i++)
					{
						p.sendMessage(ChatColor.AQUA + "" + (i+1) + ") " + plugin.currentPolls.get(i).getTitle() + "\n- Description: " + plugin.currentPolls.get(i).getDescription() + "\n");
					}
					p.sendMessage(ChatColor.AQUA + plugin.pluginPrefix + " Type /chpinfo <numberOfPoll> to get more information about each poll!");
				}
			}
		}
		catch (Exception e) //did not type true or false in config.yml
		{
			//Send it to console, ig
			ConsoleCommandSender console = plugin.getServer().getConsoleSender();
			console.sendMessage(ChatColor.AQUA + plugin.pluginPrefix + " ChatPolls was unable to determine the value for notifyUserOnLogin in your config.yml."
					+ "\nPlease fix this issue to get rid of this message.");
		}
	}
}
