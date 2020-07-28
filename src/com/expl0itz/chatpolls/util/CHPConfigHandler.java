package com.expl0itz.chatpolls.util;

import com.expl0itz.chatpolls.MainChatPolls;

public class CHPConfigHandler {
	
	private final MainChatPolls plugin;
	
	public CHPConfigHandler(MainChatPolls plugin)
	{
		this.plugin = plugin;
	}
	
	public void loadDefaultRawConfiguration() //so I can generate a config.yml without breaking YAML formatting
	{
		//Default values:::
		
		//General Settings
		String prefixName = "ChatPollsGeneral.prefixName";
		String notifyActivePollLogin = "ChatPollsGeneral.notifyUserOnLogin";
		String customPollTimeout = "ChatPollsGeneral.customPollTimeout";
		
		//Sounds
		String pollStartSound = "ChatPollsSound.StartPoll"; //Start Poll Sound
		String pollEndSound = "ChatPollsSound.FinishPoll"; //Ending Poll Sound
		
		//Load them in
		plugin.getConfig().addDefault(prefixName, "[ChP]");
		plugin.getConfig().addDefault(notifyActivePollLogin, true);
		plugin.getConfig().addDefault(customPollTimeout, 1200);
		plugin.getConfig().addDefault(pollStartSound, "BLOCK_ANVIL_LAND");
		plugin.getConfig().addDefault(pollEndSound, "ENTITY_BLAZE_DEATH");
		plugin.getConfig().options().copyDefaults(true); //append new val pairs to an existing config.yml
		plugin.saveConfig();
	}
	
	public void loadConfiguration()
	{
		plugin.saveDefaultConfig(); //load pre-gen'd default config
        //loadDefaultRawConfiguration(); //maybe get this to copy comments someday, then we can use it
        // Hopefully can find a way to copy comments :(
	}
	
	public void reloadConfiguration()
	{
		plugin.reloadConfig();
		try //need to register some new config vals
		{
			plugin.pluginPrefix = plugin.getConfig().getString("ChatPollsGeneral.prefixName");
		}
		catch (Exception e)
		{
			plugin.pluginPrefix = "[ChP]";
			plugin.getLogger().info(e + "\nUnable to load provided config.yml. Please fix your values, or re-generate your config.");
		}
	}
}
