package me.aggellos2001.survivaleuplugin.modules;


import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import me.aggellos2001.survivaleuplugin.utils.PluginActivity;
import me.aggellos2001.survivaleuplugin.utils.Utilities;
import org.bukkit.command.CommandSender;

@CommandAlias("discord|twitter")
public final class DiscordCommand extends PluginActivity {

	@Default
	public boolean onCommand(final CommandSender sender) {
		Utilities.sendMsg(sender, "&6Social Media:\n" +
				"&eDiscord:&a&l https://discord.survivaleu.com/&r\n" +
				"&eTwitter:&b&l https://twitter.com/SurvivalEUMC");
		return true;
	}
}
