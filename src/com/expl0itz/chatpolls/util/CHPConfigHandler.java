package com.expl0itz.chatpolls.util;

import com.expl0itz.chatpolls.MainChatPolls;

public class CHPConfigHandler {
	
	private final MainChatPolls plugin;
	
	public CHPConfigHandler(MainChatPolls plugin)
	{
		this.plugin = plugin;
	}
	
	public void loadDebugConfiguration() //so I can generate a config.yml without breaking YAML formatting
	{
		//Default values:::
		
		//General Settings
		String notifyActivePollLogin = "ChatPollsGeneral.notifyUserOnLogin";
		
		//Sounds
		String pollStartSound = "ChatPollsSound.StartPoll"; //Start Poll Sound
		String pollEndSound = "ChatPollsSound.FinishPoll"; //Ending Poll Sound
		
		//Load them in
		plugin.getConfig().addDefault(notifyActivePollLogin, true);
		plugin.getConfig().addDefault(pollStartSound, "BLOCK_ANVIL_LAND");
		plugin.getConfig().addDefault(pollEndSound, "ENTITY_BLAZE_DEATH");
		plugin.getConfig().options().copyDefaults(true); //append new val pairs to an existing config.yml
		plugin.saveConfig();
	}
	
	public void loadConfiguration()
	{
		plugin.saveDefaultConfig(); //load pre-gen'd default config
		//loadDebugConfiguration(); //generate debug config for easier formatting (not normally set)
	}
	
	public void reloadConfiguration()
	{
		plugin.reloadConfig();
	}
}
