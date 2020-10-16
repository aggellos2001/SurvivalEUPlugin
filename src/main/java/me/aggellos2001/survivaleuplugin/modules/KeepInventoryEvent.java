package me.aggellos2001.survivaleuplugin.modules;

import me.aggellos2001.survivaleuplugin.playerdata.PlayerData;
import me.aggellos2001.survivaleuplugin.utils.PluginActivity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

public class KeepInventoryEvent extends PluginActivity {

	@EventHandler
	private void onDeath(PlayerDeathEvent e){
		var playerData = PlayerData.getPlayerData(e.getEntity());
		if (playerData.keepingInventory){
			e.setKeepInventory(true);
			e.setKeepLevel(true);
			e.getDrops().clear();
			e.setDroppedExp(0);
		}
	}
}
