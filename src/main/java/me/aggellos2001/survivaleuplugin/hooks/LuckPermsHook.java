package me.aggellos2001.survivaleuplugin.hooks;

import me.aggellos2001.survivaleuplugin.SurvivalEUPlugin;
import me.aggellos2001.survivaleuplugin.playerdata.PlayerDataEvent;
import me.aggellos2001.survivaleuplugin.utils.PluginActivity;
import me.aggellos2001.survivaleuplugin.utils.Utilities;
import me.mattstudios.mfmsg.base.MessageOptions;
import me.mattstudios.mfmsg.base.internal.Format;
import me.mattstudios.mfmsg.bukkit.BukkitMessage;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public final class LuckPermsHook extends PluginActivity {

	public static final LuckPermsHook INSTANCE = new LuckPermsHook();

	public static LuckPerms permissionAPI;

	public static Set<Player> mobileUser = new HashSet<>();

	private static final HashMap<Player, Ranks> PERM_CACHE = new HashMap<>();

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
				"twitch", "youtube", "builder", "default"
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
		mobileUser.remove(e.getPlayer()); //remove from list!
	}

	public boolean isStaff(final Player p) {
		return getPlayerRank(p).isStaff;
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	private void formatChat(final AsyncPlayerChatEvent e) {
		var data = PlayerDataEvent.getPlayerData(e.getPlayer());
		var chatOptions = MessageOptions.builder().removeFormat(Format.LEGACY_OBFUSCATED, Format.OBFUSCATED).build();
		var colorize = BukkitMessage.create(chatOptions);
		var rank = getPlayerRank(e.getPlayer()).format;
		var chatColor = "&" + data.chatColor;
		if (mobileUser.contains(e.getPlayer()))
			e.setFormat(colorize.parse("&b✆&r " + rank + "&r %s:&r %s").toString());
		else
			e.setFormat(colorize.parse(rank + "&r %s:&r %s").toString());
		e.setMessage(colorize.parse(chatColor + e.getMessage()).toString());
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
		if (player.getName().equals("alexandroulis")) {
			return Ranks.ADMINISTRATOR;
		}
		if (player.getName().equals("SchachShaolin78")) {
			return Ranks.ADMINISTRATOR;
		}
		if (player.getName().equals("BIGTOM2002")) {
			return Ranks.ADMINISTRATOR;
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
