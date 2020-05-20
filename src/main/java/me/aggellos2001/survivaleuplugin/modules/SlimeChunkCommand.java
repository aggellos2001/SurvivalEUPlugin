package me.aggellos2001.survivaleuplugin.modules;


import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import me.aggellos2001.survivaleuplugin.languages.Language;
import me.aggellos2001.survivaleuplugin.utils.PluginActivity;
import me.aggellos2001.survivaleuplugin.utils.Utilities;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;

@CommandAlias("slimechunk")
public final class SlimeChunkCommand extends PluginActivity {

	@Override
	public boolean hasEvents() {
		return false;
	}

	@Override
	public boolean hasCommands() {
		return true;
	}

	@Default
	private void onCommand(final Player player) {

		final var chunk = player.getLocation().getChunk();

		if (chunk.isSlimeChunk()) {
			Utilities.sendMsg(player,Language.SLIME_CHUNK_ALREADY_THERE.getTranslation(player));
		} else {
			if (player.getLocation().getBlock().getBiome().equals(Biome.SWAMP))
				Utilities.sendMsg(player,Language.SLIME_CHUNK_SWAMP_BIOME.getTranslation(player));
			else {
				Utilities.sendMsg(player,Language.SLIME_CHUNK_NOT_SUITABLE_LOCATION.getTranslation(player));
			}
		}
	}
}