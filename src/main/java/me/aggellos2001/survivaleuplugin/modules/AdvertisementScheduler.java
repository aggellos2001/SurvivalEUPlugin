package me.aggellos2001.survivaleuplugin.modules;

import me.aggellos2001.survivaleuplugin.languages.Language;
import me.aggellos2001.survivaleuplugin.utils.Utilities;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public final class AdvertisementScheduler implements Runnable {
	@Override
	public void run() {
		for (final Player onlinePlayer : Bukkit.getOnlinePlayers()) {
			Utilities.sendMsg(onlinePlayer, Language.ADVERTISEMENT.getTranslation(onlinePlayer));
		}
	}
}
