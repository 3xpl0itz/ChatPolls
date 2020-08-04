package com.expl0itz.chatpolls.util;

import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import com.expl0itz.chatpolls.MainChatPolls;

public class CHPConfigHandler {
	
	private final MainChatPolls plugin;
	public int updateCheckTask = -1;
	
	public CHPConfigHandler(MainChatPolls plugin)
	{
		this.plugin = plugin;
	}
	
	public void loadDefaultRawConfiguration() //so I can generate a config.yml without breaking YAML formatting
	{
		//Default values:::
		
		//General Settings
		String prefixName = "ChatPollsGeneral.prefixName"; //prefix
		String notifyActivePollLogin = "ChatPollsGeneral.notifyUserOnLogin"; //notify user of acitve polls on login
		String customPollTimeout = "ChatPollsGeneral.customPollTimeout"; //timeout for /chpmodify
		String enablebStats = "ChatPollsGeneral.enablebStats"; //toggle bstats
		String updateCheckerDelay = "ChatPollsGeneral.updateCheckerDelay"; //update checker delay
		
		//Sounds
		String pollStartSound = "ChatPollsSound.StartPoll"; //Start Poll Sound
		String pollVoteSound = "ChatPollsSound.VotePoll"; //Voting Poll Sound
		String pollEndSound = "ChatPollsSound.FinishPoll"; //Ending Poll Sound
		
		//Load them in
		plugin.getConfig().addDefault(prefixName, "[ChP]");
		plugin.getConfig().addDefault(notifyActivePollLogin, true);
		plugin.getConfig().addDefault(customPollTimeout, 1200);
		plugin.getConfig().addDefault(enablebStats, true);
		plugin.getConfig().addDefault(updateCheckerDelay, 86400);
		plugin.getConfig().addDefault(pollStartSound, "BLOCK_ANVIL_LAND");
		plugin.getConfig().addDefault(pollVoteSound, "ENTITY_PLAYER_LEVELUP");
		plugin.getConfig().addDefault(pollEndSound, "ENTITY_BLAZE_DEATH");
		plugin.getConfig().options().copyDefaults(true); //append new val pairs to an existing config.yml
		plugin.saveConfig();
	}
	
	public void loadConfiguration()
	{
		//Moved from MainChatPolls.java as of v1.3
		//Update Checker that runs immediately
		try 
		{
			new CHPUpdateChecker(plugin, plugin.pluginId).getVersion(version -> //int = Spigot ID
			{
				if (Double.parseDouble(plugin.getDescription().getVersion()) <= plugin.pluginVersion) //convert to double
				{
					plugin.getLogger().info("No new update available.");
				}
				else
				{
					plugin.getLogger().info("There is a new update available! (Current version: " + plugin.pluginVersion + ", New version: " + version + ".)");
				}
			});
		}
		catch (Exception e)
		{
			plugin.getLogger().info("Unhandled exception while checking for updates. Please try again later.");
		}
		
		//Schedule update checker task
		int updateCheckerDelay = 86400;
		if (plugin.getConfig().getInt("ChatPollsGeneral.updateCheckerDelay") > 0) //read from config.plist; dont run if seconds = 0 or less
		{
			try
			{
				updateCheckerDelay = 20*((plugin.getConfig().getInt("ChatPollsGeneral.updateCheckerDelay"))); //convert seconds to ticks
			}
			catch (Exception e)
			{
				plugin.getLogger().info("Invalid delay specified in config.yml. Loading 86400 second default...");
			}
			
			BukkitTask updateCheckTask = new BukkitRunnable() 
			{
				@Override
				public void run() 
				{
					new CHPUpdateChecker(plugin, plugin.pluginId).getVersion(version -> //int = Spigot ID
					{
						if (Double.parseDouble(plugin.getDescription().getVersion()) <= plugin.pluginVersion) //convert to double
						{
							plugin.getLogger().info("No new update available.");
						}
						else
						{
							plugin.getLogger().info("There is a new update available! (Current version: " + plugin.pluginVersion + ", New version: " + version + ".)");
						}
					});
				}
			 
			}.runTaskTimer(plugin, updateCheckerDelay, updateCheckerDelay); 
			plugin.backgroundTasks.add(updateCheckTask); //adding this to backgroundTasks to be able to cancel it later
		}
		
		//Load Prefix from Config
		try
		{
			plugin.pluginPrefix = plugin.colorize(plugin.getConfig().getString("ChatPollsGeneral.prefixName"));
		}
		catch (Exception e)
		{
			plugin.getLogger().info("Error reading ChatPollsGeneral.prefixName from your config.plist. \nPlease fix this manually, or re-generate your config. Using default prefix.");
		}
		
		//BStats!
		try
		{
			if (plugin.getConfig().getBoolean("ChatPollsGeneral.enablebStats"))
			{
				Metrics metrics = new Metrics(plugin, plugin.bstatsId);
			}
		}
		catch (Exception e)
		{
			plugin.getLogger().info("Error reading ChatPollsGeneral.enablebStats from your config.plist. \nPlease fix this manually, or re-generate your config. Using default prefix.");
		}
		plugin.saveDefaultConfig(); //load pre-gen'd default config
        //loadDefaultRawConfiguration(); //maybe get this to copy comments someday, then we can use it
        // Hopefully can find a way to copy comments :(
	}
	
	public void reloadConfiguration()
	{
		plugin.reloadConfig();
		
		//Try to cancel any active tasks here (update checker, etc.) to avoid overlapping
		try
		{
			plugin.cancelBackgroundTasks();
		}
		catch (Exception e)
		{
			plugin.getLogger().info("An unhandled exception occured. Please contact the developer of this plugin, or restart ChatPolls.");
			//we should never get here
		}
		
		loadConfiguration();
		
	}
}
