package net.bigbadcraft.namethatmob;

import java.io.File;
import java.io.IOException;
import java.util.List;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class NameThatMob extends JavaPlugin {
	
	public static List<String> words;
	private static NameThatMob instance = new NameThatMob();
	
	public static boolean vaultEnabled;
	public static String version;
	public static int configuredValue;
	public static int charCount;
	public static Economy economy = null;
	private File file;
	
	public static NameThatMob getInstance() {
		return instance;
	}

	public void onEnable() {
		words = getConfig().getStringList("namethatmob.blacklisted-words");
		vaultEnabled = getConfig().getBoolean("currency.vault-enable");
		version = getDescription().getVersion();
		configuredValue = getConfig().getInt("currency.currency-cost-per-nametag");
		charCount = getConfig().getInt("namethatmob.character-limit");
		
		saveDefaultConfig();

		createConfig();

		setupEconomy();
		
		getServer().getPluginManager().registerEvents(new InteractEntityListener(), this);
		getCommand("namemob").setExecutor(new NameMobCommand());
	}

	private void createConfig() {
		this.file = new File(getDataFolder(), "config.yml");

		if (!this.file.exists())
			try {
				getLogger().warning("Configuration file doesn't exist, creating one..");
				this.file.createNewFile();
				getLogger().info("Configuration file successfully created.");
			} catch (IOException ex) {
				getLogger().severe("Could not create configuration file!");
				ex.printStackTrace();
			}
	}

	private boolean setupEconomy() {
		RegisteredServiceProvider<Economy> economyProvider = getServer()
				.getServicesManager().getRegistration(Economy.class);
		if (economyProvider != null) {
			economy = (Economy) economyProvider.getProvider();
		}

		return economy != null;
	}
}