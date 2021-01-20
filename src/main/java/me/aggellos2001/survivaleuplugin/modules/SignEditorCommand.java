package me.aggellos2001.survivaleuplugin.modules;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Optional;
import me.aggellos2001.survivaleuplugin.SurvivalEUPlugin;
import me.aggellos2001.survivaleuplugin.utils.PluginActivity;
import me.aggellos2001.survivaleuplugin.utils.Utilities;
import me.mattstudios.mfmsg.base.MessageOptions;
import me.mattstudios.mfmsg.base.internal.Format;
import me.mattstudios.mfmsg.bukkit.BukkitMessage;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.persistence.PersistentDataType;

@CommandAlias("signeditor|seditor")
public class SignEditorCommand extends PluginActivity {

	private static final NamespacedKey key = new NamespacedKey(SurvivalEUPlugin.instance, "signOwner");
	private static final BukkitMessage parser = BukkitMessage.create(MessageOptions.builder().addFormat(Format.values()).build());

	@Default
	private void signEditor(final Player player, final int line, @Optional String newLine) {
		if (line > 4 || line < 1) {
			Utilities.sendMsg(player, "&cValid numbers are 1,2,3,4!");
			return;
		}
		final var block = player.getTargetBlock(4);
		if (block == null) return;
		if (!(block.getState() instanceof Sign)) {
			Utilities.sendMsg(player, "&cCannot find a sign where you look at! (&othe sign must be within 4 blocks near you!&r&c)");
			return;
		}
		final var sign = ((Sign) block.getState());
		final var owner = sign.getPersistentDataContainer().getOrDefault(key, PersistentDataType.STRING, "null");
		if (owner.equalsIgnoreCase("null")) {
			Utilities.sendMsg(player, "&cThis sign has no owner!");
			return;
		}
		if (!owner.equalsIgnoreCase(player.getName())) {
			Utilities.sendMsg(player, "&cThis sign was placed by &e" + owner + " &cso you cannot edit it!");
			return;
		}
		if (newLine == null)
			newLine = " ";
		sign.setLine(line - 1, parser.parse(newLine).toString());
		final var result = sign.update();
		if (result)
			Utilities.sendMsg(player, "&aSuccessfully changed line &e" + line + "&a!");
		else
			Utilities.sendMsg(player, "&cError! Please try again later!");
	}

	@EventHandler(ignoreCancelled = true)
	private void onSignPlace(final SignChangeEvent event) {
		final var player = event.getPlayer();
		final var sign = (Sign) event.getBlock().getState();
		sign.getPersistentDataContainer().set(key, PersistentDataType.STRING, player.getName());
		sign.update();
	}
}
