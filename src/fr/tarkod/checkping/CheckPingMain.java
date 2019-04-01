package fr.tarkod.checkping;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import fr.tarkod.checkping.commands.Ping;

public class CheckPingMain extends JavaPlugin {
	
	static CheckPingMain instance;
	public File cfile;
	public FileConfiguration config;
	
	public static CheckPingMain getInstance() {
		return instance;
	}
	
	public FileConfiguration getConfigPlus() {
		return config;
	}
	
	@Override
	public void onEnable() {
		instance = this;
		config = getConfig();
		config.options().copyDefaults(true);
		saveConfig();
		cfile = new File(getDataFolder() + "config.yml");
		
		getCommand("ping").setExecutor(new Ping());
		getCommand("myping").setExecutor(new Ping());
		getCommand("cprl").setExecutor(new Ping());
	}
}
