package me.aggellos2001.survivaleuplugin.modules;


import me.aggellos2001.survivaleuplugin.utils.PluginActivity;
import me.aggellos2001.survivaleuplugin.utils.Utilities;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public final class AntiSwearingEvent extends PluginActivity {

	private final static String[] BAD_WORDS = new String[]{
			//greeklish
			"malak", "arxid", "mouni", "gamo", "pouta", "kwlo", "nigg", "papar", "vlaka", "blaka",
			"hlith", "pipa", "mlk", "mpastar", "niqqa", "diaol",
			"flor", "skat", "gkami", "poust", "kariol", "kourad", "sperma", "peos", "vromiar",
			"skase", "knani", "karkin", "carriol", "ilithi", "hlioth", "blamen", "poutsa",
			"moyni", "bizia", "byzia", "kwlos", "kolara", "kwlara", "roufokoli",
			"gamimen", "kavli", "psoli", "blamen",
			//me simbola
			"m@l@k", "@rxid", "m0un", "g@m", "p0ut", "k0l0", "nigg", "p@p@r", "vl@k", "bl@k",
			"pip@", "x0ntr", "mp@st@", "di@ol", "gk@mi", "arx1d", "sperm@", "kl@ni", "k0l0s",

			//agglikes
			"slave", "cancer", "pussy", "dick", "porn", "hentai", "cock", "bitch", "gay",
			"slut", "lesbian", "shit", "jerk", "moron", "whore", "scumbag", "retard", "poop",
			"penis", "vagina", "faggot", "idiot", "fuck", "fool", "boob", "cunt", "weed",
			"bastard", "tittie", "titts", "fagot", "fak", "fuk", "dildo",
			//agglikes me simbola
			"sl@ve", "c@ncer", "p0rn", "c0ck", "@ss", "g@y", "f@t", "ret@rd", "p00p", "v@gin@", "f@ggot", "f@k",
			"shlt", "f4uck", "fack", "fock", "r3tard", "r3t@rd", "pvssy", "d!ck", "d1ck", "cvnt", "p3n1s", "pen!s",
			"p3nis", "v4g1na", "v4g!na", "v4g!n4", "v4g1n4", "v4gina", "pen1s", ".!.", "./.", ".|.", ",|,", ",/,",
			",!,", ".}.", ".{.", ".[.", ".].", ".[].", ",[],", ",{},",

			//ellinikes
			"μαλακ", "μαλάκ", "αρχιδ", "αρχίδ", "γαμo", "γαμω", "πουτ", "κωλο", "κολο", "παπαρ", "παπάρ", "βλακ", "βλάκ",
			"βλαμέ", "ηλιθ", "ηλίθ", "πιπα", "πίπα", "χοντρ", "μλκ", "μπασταρ", "νιγκα", "διαολ", "διάολ", "φλω",
			"φλορ", "φλώρ", "φλόρ", "σκατ", "πουστ", "πούστ", "καριολ", "καριό", "καριωλ", "καριώλ", "κουραδ",
			"κουράδ", "σπερμα", "σπέρμα", "πεος", "πέος", "πεως", "πέως", "βρωμιαρ", "βρωμιάρ", "βρομιαρ", "βρομιάρ",
			"βλαμε", "βλαμέν", "ασσ", "πορν", "πουσσι", "ντικ", "πουσι", "γκει", "γκέι", "λεσβια", "λεσβία"
	};

	@EventHandler(ignoreCancelled = true)
	private void onPlayerChat(final AsyncPlayerChatEvent e) {
		if (e.getPlayer().isOp()) return;
		boolean foundBadWord = false;
		final var arguments = e.getMessage().split("\\s+");
		for (var i = 0; i < arguments.length; i++) {
			if (Utilities.isPlayerName(arguments[i])) continue;
			if (arguments[i].startsWith("/")) continue;
			for (final String swear : BAD_WORDS) {
				if (arguments[i].toLowerCase().startsWith(swear) || arguments[i].toLowerCase().contains(swear)) {
					arguments[i] = "#" .repeat(arguments[i].length());
					foundBadWord = true;
					break;
				}
			}
		}
		if (foundBadWord) {
			for (final Player player : Bukkit.getOnlinePlayers()) {
				if (player.hasPermission("seu.swear.notify")) {
					Utilities.sendMsg(player, "&c[Swear]&4 O παίκτης " + e.getPlayer().getName() + " προσπάθησε να πει: &e" + e.getMessage());
				}
			}
			e.setMessage(String.join(" ", arguments));
		}
	}
}
