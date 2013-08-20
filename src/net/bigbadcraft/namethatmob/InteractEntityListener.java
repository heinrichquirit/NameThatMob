package net.bigbadcraft.namethatmob;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class InteractEntityListener implements Listener {
	
	private Set<EntityType> animals;
	private Set<EntityType> monsters;
	
	public InteractEntityListener() {
		animals = new HashSet<EntityType>();
		monsters = new HashSet<EntityType>();
		populateAnimals();
		populateMonsters();
	}
	
	@EventHandler
	public void onEntityInteract(PlayerInteractEntityEvent event) {
		final Player player = event.getPlayer();
		if (isAnimalOrMonster(event) && isInList(player.getName())) {
			LivingEntity entity = (LivingEntity) event.getRightClicked();
			if (entity.getCustomName() == null || entity.getCustomName().equals("")) entity.setCustomName(" ");
			if (!entity.getCustomName().equals(NameMobCommand.mobName.get(player.getName()))) {
				entity.setCustomName(NameMobCommand.mobName.get(player.getName()));
				entity.setCustomNameVisible(true);
				withdrawPlayer(player);
				NameMobCommand.mobName.remove(player.getName());
			} else {
				player.sendMessage(ChatColor.RED + "That mob already has that name!");
			}
		}
	}
	
	private void populateAnimals() {
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
	
	private void populateMonsters() {
		monsters.add(EntityType.BAT);
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
	
	private void withdrawPlayer(Player player) {
		NameThatMob.economy.withdrawPlayer(player.getName(), Math.round(NameThatMob.configuredValue));
		player.sendMessage(ChatColor.GREEN + "[NameThatMob] $" + Math.round(NameThatMob.configuredValue) + " has been taken from your account.");
	}
	
	private boolean isAnimalOrMonster(PlayerInteractEntityEvent event) {
		return monsters.contains(event.getRightClicked().getType()) || animals.contains(event.getRightClicked().getType());
	}
	
	private boolean isInList(String senderName) {
		return NameMobCommand.mobName.containsKey(senderName);
	}
}
