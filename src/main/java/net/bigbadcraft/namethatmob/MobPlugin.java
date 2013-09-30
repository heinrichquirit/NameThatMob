package main.java.net.bigbadcraft.namethatmob;

import main.java.net.bigbadcraft.namethatmob.command.NameMobCommand;
import main.java.net.bigbadcraft.namethatmob.listener.EntityInteractListener;
import main.java.net.bigbadcraft.namethatmob.util.Methods;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.List;

/**
 * User: Heinrich Quirit
 * Last Modified: 9/30/13
 * Time: 12:35 AM
 */
public class MobPlugin extends JavaPlugin {

    public File config;

    public String plugName;
    public String plugVer;
    public int price;
    public int charCount;
    public List<String> badWords;
    public boolean vaultEnabled;

    public static Economy economy = null;

    public Methods methods;

    @Override
    public void onEnable() {
        methods = new Methods(this);
        plugName = getDescription().getName();
        plugVer = getDescription().getVersion();

        saveDefaultConfig();
        config = new File(getDataFolder(), "config.yml");
        methods.loadFile(config);

        price = getConfig().getInt("currency.currency-cost-per-nametag");
        charCount = getConfig().getInt("namethatmob.character-limit");
        badWords = getConfig().getStringList("namethatmob.blacklisted-words");
        vaultEnabled = getConfig().getBoolean("currency.vault-enable");

        methods.missing(this);

        getServer().getPluginManager().registerEvents(new EntityInteractListener(this), this);
        getCommand("namemob").setExecutor(new NameMobCommand(this));

        setupEconomy();
    }

    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(Economy.class);
        if (economyProvider != null) {
            economy = (Economy) economyProvider.getProvider();
        }
        return economy != null;
    }
}
