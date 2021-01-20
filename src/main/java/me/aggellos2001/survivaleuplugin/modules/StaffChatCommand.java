package me.aggellos2001.survivaleuplugin.modules;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import me.aggellos2001.survivaleuplugin.utils.PluginActivity;
import me.aggellos2001.survivaleuplugin.utils.Utilities;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@CommandAlias("staffchat|schat")
@CommandPermission("seu.staffchat")
public class StaffChatCommand extends PluginActivity {

	@Default
	private void staffChat(final Player player, final String message) {
		Utilities.sendPermissionMsg(Bukkit.getOnlinePlayers(),
				"seu.staffchat",
				"&2[&6&lSC&r&2]&e " + player.getName() + ": &r&b" + message,
				false);
	}
}
