package com.minecraftdimensions.bungeesuiteportals;


import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.minecraftdimensions.bungeesuiteportals.commands.DeletePortalCommand;
import com.minecraftdimensions.bungeesuiteportals.commands.ListPortalsCommand;
import com.minecraftdimensions.bungeesuiteportals.commands.SetPortalCommand;
import com.minecraftdimensions.bungeesuiteportals.listeners.PhysicsListener;
import com.minecraftdimensions.bungeesuiteportals.listeners.PlayerMoveListener;
import com.minecraftdimensions.bungeesuiteportals.listeners.PortalsMessageListener;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;

public class BungeeSuitePortals extends JavaPlugin {

	public static BungeeSuitePortals INSTANCE = null;
	public static String OUTGOING_PLUGIN_CHANNEL = "BSPortals";
	static String INCOMING_PLUGIN_CHANNEL = "BungeeSuitePorts";
	public static WorldEditPlugin WORLDEDIT = null;

	@Override
	public void onEnable() {
		INSTANCE = this;
		loadWorldEdit();
		registerListeners();
		registerChannels();
		registerCommands();
	}

	private void loadWorldEdit() {
		WORLDEDIT = (WorldEditPlugin) getServer().getPluginManager().getPlugin("WorldEdit");
		if(WORLDEDIT==null){
			Bukkit.getLogger().log(Level.INFO, "No worldedit found, You will not be able to create portals!");
		}
		
	}

	private void registerCommands() {
		getCommand("setportal").setExecutor(new SetPortalCommand());
		getCommand("delportal").setExecutor(new DeletePortalCommand());
		getCommand("portals").setExecutor(new ListPortalsCommand());
	}

	private void registerChannels() {
		Bukkit.getMessenger().registerIncomingPluginChannel(this,
				INCOMING_PLUGIN_CHANNEL, new PortalsMessageListener());
		Bukkit.getMessenger().registerOutgoingPluginChannel(this,
				OUTGOING_PLUGIN_CHANNEL);
		Bukkit.getMessenger()
				.registerOutgoingPluginChannel(this, "BungeeCord");
	}

	private void registerListeners() {
		getServer().getPluginManager().registerEvents(new PlayerMoveListener(),
				this);
		getServer().getPluginManager().registerEvents(
				new PhysicsListener(), this);
		
	}
}
