package me.aggellos2001.survivaleuplugin.utils;

import co.aikar.commands.BaseCommand;
import org.bukkit.event.Listener;

public abstract class PluginActivity extends BaseCommand implements Listener {

	abstract public boolean hasEvents();
	abstract public boolean hasCommands();

}
