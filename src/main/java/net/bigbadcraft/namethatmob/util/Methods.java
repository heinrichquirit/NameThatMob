package main.java.net.bigbadcraft.namethatmob.util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Level;

import main.java.net.bigbadcraft.namethatmob.MobPlugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * User: Heinrich Quirit
 * Last Modified: 9/30/13
 * Time: 12:36 AM
 */
public class Methods {

    private MobPlugin plugin;

    private HashMap<String, String> mobName;
    private HashSet<EntityType> monsters;
    private HashSet<EntityType> animals;

    public Methods(MobPlugin plugin) {
        this.plugin = plugin;
        mobName = new HashMap<String, String>();
        monsters = new HashSet<EntityType>();
        animals = new HashSet<EntityType>();
        populateMonsters();
        populateAnimals();
    }

    // Mob name management

    public void saveName(Player player, String name) {
        mobName.put(player.getName(), name);
    }

    public void remove(Player player) {
        mobName.remove(player.getName());
    }

    public boolean contains(Player player) {
        return mobName.containsKey(player.getName());
    }

    public String getMobName(Player player) {
        return mobName.get(player.getName());
    }

    public boolean isNameNull(LivingEntity entity) {
        return entity.getCustomName() == null || entity.getCustomName().equals("");
    }

    public boolean isAnimOrMons(EntityType type) {
        return monsters.contains(type) || animals.contains(type);
    }

    // Miscel.

    public boolean containsSwear(String name) {
        for (String words : plugin.badWords) {
            return name.contains(words);
        }
        return false;
    }

    public boolean underCharLimit(String name) {
        return ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', name)).length() <= plugin.charCount;
    }

    public void withdraw(Player player) {
        int price = Math.round(plugin.price);
        if (plugin.vaultEnabled) {
            MobPlugin.economy.withdrawPlayer(player.getName(), price);
            player.sendMessage(ChatColor.GREEN + "[NameThatMob] $" + price + " has been taken from your account.");
        }
    }

    public boolean isPoor(Player player) {
        return !(MobPlugin.economy.getBalance(player.getName()) >= Math.round(plugin.price));
    }

    public void missing(Plugin plugin) {
        if (this.plugin.vaultEnabled) {
            if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
                log(Level.SEVERE, "Vault could not be found whilst enabled in the config.");
                log(Level.SEVERE, "Disabling NameThatMob");
                Bukkit.getPluginManager().disablePlugin(plugin);
                return;
            }
        }
    }

    public void log(Level lvl, String message) {
        Bukkit.getLogger().log(lvl, message);
    }

    public void loadFile(File file) {
        if (!file.exists()) {
            try {
                log(Level.WARNING, file.getName() + " not found, creating..");
                file.createNewFile();
                log(Level.INFO, file.getName() + " successfully created.");
            } catch (IOException ioe) {
                log(Level.SEVERE, file.getName() + " could not be created.");
                ioe.printStackTrace();
            }
        }
    }

    public void populateMonsters() {
        monsters.add(EntityType.BAT);
        monsters.add(EntityType.ENDERMAN);
        monsters.add(EntityType.BLAZE);
        monsters.add(EntityType.CAVE_SPIDER);
        monsters.add(EntityType.CREEPER);
        monsters.add(EntityType.GHAST);
        monsters.add(EntityType.GIANT);
        monsters.add(EntityType.MAGMA_CUBE);
        monsters.add(EntityType.PIG_ZOMBIE);
        monsters.add(EntityType.SILVERFISH);
        monsters.add(EntityType.SKELETON);
        monsters.add(EntityType.SLIME);
        monsters.add(EntityType.SPIDER);
        monsters.add(EntityType.WITCH);
        monsters.add(EntityType.WITHER);
        monsters.add(EntityType.ZOMBIE);
    }

    public void populateAnimals() {
        animals.add(EntityType.CHICKEN);
        animals.add(EntityType.COW);
        animals.add(EntityType.HORSE);
        animals.add(EntityType.IRON_GOLEM);
        animals.add(EntityType.OCELOT);
        animals.add(EntityType.PIG);
        animals.add(EntityType.SHEEP);
        animals.add(EntityType.SNOWMAN);
        animals.add(EntityType.SQUID);
        animals.add(EntityType.VILLAGER);
        animals.add(EntityType.WOLF);
    }

}
