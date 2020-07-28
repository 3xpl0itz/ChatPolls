package com.expl0itz.chatpolls.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

import org.bukkit.Bukkit;
import org.bukkit.util.Consumer;

import com.expl0itz.chatpolls.MainChatPolls;

public class CHPUpdateChecker {

	public final MainChatPolls plugin;
	public int resourceId;
	
	public CHPUpdateChecker(MainChatPolls plugin, int resourceId) 
	{
        this.plugin = plugin;
        this.resourceId = resourceId;
    }

    public void getVersion(final Consumer<String> consumer) 
    {
        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> 
        {
            try (InputStream inputStream = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + this.resourceId).openStream(); Scanner scanner = new Scanner(inputStream)) 
            {
                if (scanner.hasNext()) 
                {
                    consumer.accept(scanner.next());
                }
            } 
            catch (IOException exception) 
            {
                this.plugin.getLogger().info("Unable to check for updates: " + exception.getMessage());
            }
        });
    }
}
	
