package me.aggellos2001.survivaleuplugin.modules;


import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import me.aggellos2001.survivaleuplugin.utils.PluginActivity;
import me.aggellos2001.survivaleuplugin.utils.Utilities;
import org.bukkit.command.CommandSender;

@CommandAlias("discord")
public final class DiscordCommand extends PluginActivity {
	@Override
	public boolean hasEvents() {
		return false;
	}

	@Override
	public boolean hasCommands() {
		return true;
	}

	@Default
	public boolean onCommand(final CommandSender sender) {
		Utilities.sendMsg(sender,"&6Discord server: &e&lhttp://discord.survivaleu.com/");
		return true;
	}
}
