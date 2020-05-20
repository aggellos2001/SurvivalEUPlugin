package me.aggellos2001.survivaleuplugin.modules;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Single;
import me.aggellos2001.survivaleuplugin.SurvivalEUPlugin;
import me.aggellos2001.survivaleuplugin.ipinfoapi.IpInfoPrivacy;
import me.aggellos2001.survivaleuplugin.languages.Language;
import me.aggellos2001.survivaleuplugin.utils.PluginActivity;
import me.aggellos2001.survivaleuplugin.utils.Utilities;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

@CommandAlias("blockvpn")
@CommandPermission("seu.blockvpn")
public final class BlockVPN extends PluginActivity {

	@Override
	public boolean hasEvents() {
		return true;
	}

	@Override
	public boolean hasCommands() {
		return true;
	}

	private final IpInfoPrivacy ipInfo = new IpInfoPrivacy();


	@EventHandler(priority = EventPriority.LOWEST)
	private void onAsyncJoin(final AsyncPlayerPreLoginEvent e) {
		final var ip = this.ipInfo.lookPrivacyIP(e.getAddress().getHostAddress());
		if (ip.vpn || ip.proxy || ip.tor) {
			e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, Utilities.colorize(Language.BLOCK_VPN_REJECTED.getTranslation(Bukkit.getPlayer(e.getName()))));
		}
	}

	@Default
	private void onAddingAPIKey(CommandSender sender, @Single String apiKey) {
		SurvivalEUPlugin.config.setValue("ip-key", apiKey);
		Utilities.sendMsg(sender, "&aSet api key successfully to " + apiKey +"!");
	}

}
