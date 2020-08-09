package me.aggellos2001.survivaleuplugin.hooks;

import me.aggellos2001.survivaleuplugin.SurvivalEUPlugin;
import me.aggellos2001.survivaleuplugin.utils.PluginActivity;
import me.aggellos2001.survivaleuplugin.utils.Utilities;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.group.Group;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;

public final class LuckPermsHook extends PluginActivity {

	@Override
	public boolean hasEvents() {
		return true;
	}

	@Override
	public boolean hasCommands() {
		return false;
	}

	public static final LuckPermsHook INSTANCE = new LuckPermsHook();

	private static final HashMap<Player, Ranks> PERM_CACHE = new HashMap<>();

	public static LuckPerms permissionAPI;

	private LuckPermsHook() {
	}

	public enum Ranks {

		OWNER("&6&lOwner&r", "group.owner", true),
		COOWNER("&1&lCoOwner&r", "group.coowner", true),
		ADMINISTRATOR("&c&lAdmin&r", "group.administrator", true),
		MODERATOR("&e&lMod&r", "group.moderator", true),
		TRAINEE("&b&lTrainee&r", "group.trainee", true),
		HELPER("&9&lHelper&r", "group.helper", true),
		EMERALD("&a&lEmerald&r", "group.emerald", false),
		DIAMOND("&b&lDiamond&r", "group.diamond", false),
		IRON("&f&lIron&r", "group.iron", false),
		COAL("&0&lCoal&r", "group.coal", false),
		TWITCH("&5&lTwi&d&ltch&r", "group.twitch", false),
		YOUTUBER("&4&lYoutuber&r", "group.youtuber", false),
		BUILDER("&d&lBuilder&r", "group.builder", false),
		DEFAULT("&a&lMember&r", "group.default", false);

		public final String format;
		public final String permission;
		public final boolean isStaff;
		private static final String[] POSSIBLE_GROUPS = new String[]{
				"owner", "coowner", "administrator", "moderator", "trainee", "helper",
				"emerald", "diamond", "iron", "coal",
				"twitch", "youtube", "builder","default"
		};

		Ranks(final String format, final String permission, final boolean isStaff) {
			this.format = format;
			this.permission = permission;
			this.isStaff = isStaff;
		}

		private static Ranks valueOfPermission(final String permission) {
			for (final Ranks value : Ranks.values()) {
				if (value.permission.equals(permission))
					return value;
			}
			return DEFAULT;
		}
	}


	@EventHandler(ignoreCancelled = true)
	private void addPlayerToCache(final PlayerJoinEvent e) {
		PERM_CACHE.put(e.getPlayer(), findPlayerRankFirstTime(e.getPlayer()));
		e.setJoinMessage(Utilities.colorize(LuckPermsHook.getPlayerRank(e.getPlayer()).format + "&b " + e.getPlayer().getName() + " &a&ljoined the server!", true));
	}

	@EventHandler(ignoreCancelled = true)
	private void removePlayerFromCache(final PlayerQuitEvent e) {
		e.setQuitMessage(Utilities.colorize(LuckPermsHook.getPlayerRank(e.getPlayer()).format + "&b " + e.getPlayer().getName() + " &c&lleft the server!", true));
		PERM_CACHE.remove(e.getPlayer());
	}

	private boolean isStaff(final Player p) {
		return getPlayerRank(p).isStaff;
	}

	@EventHandler(ignoreCancelled = true)
	private void formatChat(final AsyncPlayerChatEvent e) {
		final var player = e.getPlayer();
		final String rank = getPlayerRank(player).format;
		final String chatColor = rank.substring(0, 2);
		e.setFormat(Utilities.colorize(rank + "&r %s:&r %s "));
		if (isStaff(player) || player.isOp()) {
			e.setMessage(Utilities.colorize(e.getMessage()));
			e.setFormat(Utilities.colorize(rank + "&r %s:&r" + chatColor + " %s "));
		}
		e.setMessage(e.getMessage().replace("&k", " "));

	}

	public static void setup() {
		if (SurvivalEUPlugin.instance.getServer().getPluginManager().getPlugin("LuckPerms") == null) {
			permissionAPI = null;
			throw new IllegalStateException("Luck Perms API initialization unsuccessful! Use Luck Perms please!");
		} else {
			permissionAPI = LuckPermsProvider.get();
		}
	}

	private Ranks findPlayerRankFirstTime(final Player player) {
		if (player == null) {
			return Ranks.DEFAULT;
		}
		if (player.getName().equals("GGRLX")) {
			return Ranks.COOWNER;
		}
		for (final String group : Ranks.POSSIBLE_GROUPS) {
			if (player.hasPermission("group." + group)) {
				return Ranks.valueOfPermission("group." + group);
			}
		}
		return Ranks.DEFAULT;
	}

	public static Ranks getPlayerRank(final Player player) {
		return PERM_CACHE.getOrDefault(player, Ranks.DEFAULT);
	}
}
