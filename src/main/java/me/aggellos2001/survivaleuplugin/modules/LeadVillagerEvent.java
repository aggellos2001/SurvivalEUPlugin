package me.aggellos2001.survivaleuplugin.modules;


import me.aggellos2001.survivaleuplugin.utils.PluginActivity;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class LeadVillagerEvent extends PluginActivity {

	@EventHandler(ignoreCancelled = true)
	private void onVillagerClick(final PlayerInteractEntityEvent e) {

		if (!e.getPlayer().isSneaking()) return;
		if (e.getHand().equals(EquipmentSlot.OFF_HAND)) return;
		if (!(e.getRightClicked() instanceof Villager)) return;
		if (!e.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.LEAD)) return;

		e.setCancelled(true);


		final var entity = (Villager) e.getRightClicked();
		final var player = e.getPlayer();
		final var lead = player.getInventory().getItemInMainHand();


		if (player.getGameMode().equals(GameMode.CREATIVE)) {
			entity.setLeashHolder(e.getPlayer());
			return;
		}

		player.getInventory().remove(lead);
		player.getInventory().addItem(new ItemStack(lead.getType(), lead.getAmount() - 1));
		entity.setLeashHolder(player);

	}
}
