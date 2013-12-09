package main.java.net.bigbadcraft.namethatmob.listener;

import main.java.net.bigbadcraft.namethatmob.MobPlugin;
import main.java.net.bigbadcraft.namethatmob.util.Methods;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

/**
 * User: Heinrich Quirit
 * Last Modified: 9/30/13
 * Time: 12:36 AM
 */
public class EntityInteractListener implements Listener {

    private MobPlugin plugin;
    private Methods methods;

    public EntityInteractListener(MobPlugin plugin) {
        this.plugin = plugin;
        this.methods = plugin.methods;
    }

    @EventHandler
    public void onInteract(PlayerInteractEntityEvent event) {
        final Player player = event.getPlayer();
        if (methods.isAnimOrMons(event.getRightClicked().getType()) && methods.contains(player)) {
            LivingEntity entity = (LivingEntity) event.getRightClicked();
            if (methods.isNameNull(entity)) entity.setCustomName(" ");
            if (!entity.getCustomName().equals(methods.getMobName(player))) {
                if (!methods.isPoor(player)) {
                    entity.setCustomName(methods.getMobName(player));
                    entity.setCustomNameVisible(true);
                    methods.withdraw(player);
                    methods.remove(player);
                } else {
                    player.sendMessage(ChatColor.RED + "You need $" + Math.round(plugin.price) + " to use this.");
                }
            } else {
                player.sendMessage(ChatColor.RED + "That mob is already named: " + methods.getMobName(player) + ".");
            }
        }
    }
}
