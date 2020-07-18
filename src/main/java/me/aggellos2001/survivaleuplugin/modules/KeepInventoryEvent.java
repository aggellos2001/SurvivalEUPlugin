package me.aggellos2001.survivaleuplugin.modules;

import me.aggellos2001.survivaleuplugin.playerdata.PlayerData;
import me.aggellos2001.survivaleuplugin.utils.PluginActivity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

public class KeepInventoryEvent extends PluginActivity {
	@Override
	public boolean hasEvents() {
		return true;
	}

	@Override
	public boolean hasCommands() {
		return false;
	}

	@EventHandler
	private void onDeath(PlayerDeathEvent e){
		var data = PlayerData.getPlayerData();
		var playerData = data.get(e.getEntity().getUniqueId());
		if (playerData.isKeepingInventory()){
			e.setKeepInventory(true);
			e.setKeepLevel(true);
			e.getDrops().clear();
			e.setDroppedExp(0);
		}
	}
}
