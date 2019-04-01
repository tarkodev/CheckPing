package fr.tarkod.checkping.commands;

import java.lang.reflect.InvocationTargetException;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import fr.tarkod.checkping.CheckPingMain;

public class Ping implements CommandExecutor {

	public static CheckPingMain main = CheckPingMain.getInstance();
	public static FileConfiguration file = main.getConfig();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			int pPing = getPing(p);
			if(cmd.getName().equalsIgnoreCase("ping")) {
				
				if(args.length == 0) {
					p.sendMessage(file.getString("your_ping").replace("%player%", p.getName()).replace("%ping%", "" + pPing));
					return true;
				}
				if(args.length == 1) {
					if(Bukkit.getPlayer(args[0]) != null) {
						Player o = (Player) Bukkit.getPlayer(args[0]);
						int oPing = getPing(o);
						p.sendMessage(file.getString("their_ping").replace("%player%", p.getName()).replace("%ping%", "" + oPing));
						return true;
					} else {
						p.sendMessage(file.getString("player_offline").replace("%player%", args[0]).replace("%ping%", "" + pPing));
						return true;
					}
				}
			}
			if(cmd.getName().equalsIgnoreCase("myping")) {
				p.sendMessage(file.getString("your_ping").replace("%player%", p.getName()).replace("%ping%", "" + pPing));
				return true;
			}
			if(cmd.getName().equalsIgnoreCase("cprl")) {
				if(p.hasPermission("checkping.reload")) {
					YamlConfiguration.loadConfiguration(main.cfile);
					p.sendMessage(file.getString("reload_pl").replace("%player%", p.getName()).replace("%ping%", "" + pPing));
					return true;
				}
			}
		}
		return false;
	}
	
	private static int getPing(Player p) {
		try {
			Object entityPlayer = p.getClass().getMethod("getHandle").invoke(p);
			return (int) entityPlayer.getClass().getField("ping").get(entityPlayer);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | NoSuchFieldException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
