package me.aggellos2001.survivaleuplugin.modules;

import me.aggellos2001.survivaleuplugin.utils.PluginActivity;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;

public class MakeVillagersLoseJob extends PluginActivity {

	@EventHandler(ignoreCancelled = true)
	private void onPlayerInteractingWithVillager(final PlayerInteractEntityEvent e) {

		//Check if villager and no profession
		if (!((e.getRightClicked()) instanceof Villager)) return;
		final var villager = (Villager) e.getRightClicked();
		if (villager.getProfession() == Villager.Profession.NONE) return;
		if (e.getHand() == EquipmentSlot.OFF_HAND) return; //prevent event firing twice
		final var player = e.getPlayer();
		if (!player.isSneaking()) return; //player must sneak

		final var itemInHand = player.getInventory().getItemInMainHand();
		if (itemInHand.getType() != Material.DIAMOND) return; //if not holding diamond return

		e.setCancelled(true); //cancel event so trade gui doesn't open up

		if (player.getGameMode() != GameMode.SURVIVAL) {
			villager.setProfession(Villager.Profession.NONE); //not removing diamond if not survival
			return;
		}

		if (villager.getVillagerLevel() > 1 || villager.getVillagerExperience() > 1) {
			return; //prevent resetting profession if level > 1 & villager xp > 1
		}

		player.getInventory().setItemInMainHand(itemInHand.subtract()); //take away 1 diamond from hand
		villager.setProfession(Villager.Profession.NONE);

	}
}
