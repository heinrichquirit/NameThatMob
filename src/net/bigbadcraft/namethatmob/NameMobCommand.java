package net.bigbadcraft.namethatmob;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NameMobCommand implements CommandExecutor {
	
	public static HashMap<String, String> mobName = new HashMap<String, String>();
	
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Use this in game");
			return true;
		}
		
		if (cmd.getName().equalsIgnoreCase("namemob"))
			return nameMob(sender, args);
		
		return true;
	}
	
	private boolean nameMob(CommandSender sender, String[] args) {
		if (args.length == 0) {
			sender.sendMessage(ChatColor.GREEN + "Version: " + NameThatMob.version);
			sender.sendMessage(ChatColor.GREEN + "-/namemob price - Retrieves costs for usage.");
			sender.sendMessage(ChatColor.GREEN + "-/namemob <name> - Names entity with specified name once clicked on.");
			return true;
		}
		
		if (args.length >= 1) {
			
			if (args[0].equalsIgnoreCase("list"))
				sender.sendMessage("" + getWords());
			else if (args[0].equalsIgnoreCase("list2"))
				sender.sendMessage(getWords().toString());
			
			if (args[0].equalsIgnoreCase("price")) {
				sender.sendMessage(ChatColor.GREEN + "Cost to use: $" + getPrice());
			}
			else if (isVaultEnabled()) {
				addCommandSender(sender, args);
			} else {
				addCommandSender(sender, args);
			}
		}
		
		return true;
	}
	
	private void addCommandSender(CommandSender sender, String[] args) {
		String message = StringUtils.join(args, ' ', 0, args.length);
		message = ChatColor.translateAlternateColorCodes('&', message);
		
		if (!hasInsufficientFunds(sender.getName()) && !containsWords(sender, getWords(), args) && underCharLimit(args)) {
			tellSender(sender, message);
		} else if (hasInsufficientFunds(sender.getName())) {
			sender.sendMessage(ChatColor.RED + "You need $" + getPrice() + " to use this.");
		} else if (containsWords(sender, getWords(), args)) {
			sender.sendMessage(ChatColor.RED + "Your name cannot contain the following words:");
			for (String words : getWords()) {
				sender.sendMessage(ChatColor.RED + "- " + words);
			}
		} else if (!underCharLimit(args)) {
			sender.sendMessage(ChatColor.RED + "Your name must be under " + getCharLimit() + " characters.");
		}
		
	}
	
	private boolean hasInsufficientFunds(String name) {
		return !(NameThatMob.economy.getBalance(name) >= getPrice());
	}
	
	private boolean isVaultEnabled() {
		return NameThatMob.vaultEnabled;
	}
	
	private boolean containsWords(CommandSender sender, List<String> words, String[] args) {
		String message = StringUtils.join(args, ' ', 0, args.length);
		return getWords().contains(message);
	}
	
	private boolean underCharLimit(String[] args) {
		String message = StringUtils.join(args, ' ', 0, args.length);
		return message.length() <= NameThatMob.charCount;
	}
	
	private int getPrice() {
		return NameThatMob.configuredValue;
	}
	
	private int getCharLimit() {
		return NameThatMob.charCount;
	}
	
	private void tellSender(CommandSender sender, String message) {
		mobName.put(sender.getName(), message);
		sender.sendMessage(ChatColor.GREEN + "Stored '" + message + ChatColor.GREEN + "', now right click your mob.");
	}
	
	private List<String> getWords() {
		return NameThatMob.words;
	}
}
