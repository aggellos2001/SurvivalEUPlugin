package me.aggellos2001.survivaleuplugin.modules;


import me.aggellos2001.survivaleuplugin.utils.PluginActivity;
import me.aggellos2001.survivaleuplugin.utils.Utilities;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.similarity.JaroWinklerDistance;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public final class SwearFilterEvent extends PluginActivity {

	private final static String replaceLeetSpeak(String input) {
		input = input.replaceAll("1", "i");
		input = input.replaceAll("!", "i");
		input = input.replaceAll("3", "e");
		input = input.replaceAll("4", "a");
		input = input.replaceAll("@", "a");
		input = input.replaceAll("5", "s");
		input = input.replaceAll("7", "t");
		input = input.replaceAll("0", "o");
		input = input.replaceAll("9", "g");
		return input;
	}

	private final static String[] BAD_WORDS = new String[]{
			//greeklish
			"gamie", "gamis", "gamidi", "gamane", "gamimeno",
			"malaka", "asshole",
			"mounopan", "mouni", "muni",
			"arxidi", "poutana", "nigga", "papari", "vlaka", "blaka",
			"hlithio", "mpastardo", "niqqa", "diaolo",
			"floros", "gkamimen", "poustis", "kariola", "kourad", "sperma", "vromiari",
			"karkino", "ilithio", "hlithio", "blameno", "poutsa", "malakismeno",
			"moyni", "bizia", "byzia", "kolara", "roufokoli",
			"kavli", "blameno", "pisokola", "kwlara",

			//agglikes
			"slave", "cancer", "pussy", "dick", "porn", "hentai", "bitch",
			"slut", "lesbian", "whore", "retard",
			"penis", "vagina", "faggot", "fuck", "boob", "cunt",
			"bastard", "tits", "dildo",

			//ellinikes
			"μαλακα", "μαλάκα", "αρχιδι", "αρχίδια", "γαμo", "γαμω", "πουτανα", "κωλο", "κολο", "παπαρ", "παπάρ", "βλακα", "βλάκα",
			"βλαμέ", "ηλιθ", "ηλίθ", "πιπα", "πίπα", "χοντρ", "μλκ", "μπασταρ", "νιγκα", "διαολε", "διάολος", "φλω",
			"φλορε", "φλώρε", "φλόρος", "σκατ", "πουστη", "πούστη", "καριολη", "καριό", "καριωλ", "καριώλ", "κουραδ",
			"κουράδ", "σπερμα", "σπέρμα", "πεος", "πέος", "πεως", "πέως", "βρωμιαρ", "βρωμιάρ", "βρομιαρη", "βρομιάρη",
			"βλαμε", "βλαμέν", "ασσ", "πορν", "πουσσι", "ντικ", "πουσι", "γκει", "γκέι", "λεσβια", "λεσβία"
	};

	//words that we need to explicitly exclude
	private static final String[] FALSE_POS_LIST = {
			"peis", "peis?",
			"its", "it's", "cut", "rap", "boop",
			"jero", "hit", "tis", "bay", "rock", "pezeis",
			"yay", "plaka", "gamer", "book", "boo!", "swift",
			"shift", "port", "game", "birch", "spera", "speres", "mini", "sock",
			"where", "cant", "can't", "disk", "mouri", "mousi", "kali", "shot",
			"moni", "kick", "pick", "pic", "pastitsio", "luck", "mporo", "perma",
	};

	private static final Set<String> FALSE_POSITIVES = new HashSet<>() {{
		addAll(Arrays.asList(FALSE_POS_LIST));
	}};


	@EventHandler(ignoreCancelled = true)
	private void onPlayerChat(final AsyncPlayerChatEvent e) {
		if (e.getPlayer().isOp()) return;
		boolean foundBadWord = false;
		final var arguments = e.getMessage().split("\\s+");

		for (var i = 0; i < arguments.length; i++) {
			final var argument = arguments[i].replaceAll("[^A-Za-z]+", "");
			if (Utilities.isPlayerName(arguments[i])) continue;
			if (arguments[i].startsWith("/")) continue;
			if (FALSE_POSITIVES.contains(arguments[i].toLowerCase())) continue;

			for (final String swear : BAD_WORDS) {
//				final var similarity = StringUtils.getLevenshteinDistance(argument, swear);
				final var similarity = new LevenshteinDistance().apply(argument, swear);
				final var jaroWinkler = new JaroWinklerDistance().apply(argument, swear);
				if (argument.toLowerCase().startsWith(swear) || argument.toLowerCase().contains(swear) || similarity == 1 || jaroWinkler >= 0.93) {
					arguments[i] = "#".repeat(arguments[i].length());
					System.out.println("Found swear in argument " + argument + ". Swear is " + swear);
					foundBadWord = true;
					break;
				}
			}
		}


//		check whole arguments without splitting them to avoid this "g a y" and also replace leetspeak
		var removedWhiteSpace = StringUtils.deleteWhitespace(String.join("", arguments));
		for (final String swear : BAD_WORDS) {
			if (FALSE_POSITIVES.contains(removedWhiteSpace.toLowerCase())) continue;
			final var similarity = LevenshteinDistance.getDefaultInstance().apply(removedWhiteSpace, swear);
			final var jaroWinkler = new JaroWinklerDistance().apply(removedWhiteSpace, swear);
			if (removedWhiteSpace.toLowerCase().startsWith(swear) || removedWhiteSpace.toLowerCase().contains(swear) || similarity == 1 || jaroWinkler >= 0.93) {
				Arrays.fill(arguments, "#");
				System.out.println("Found swear in argument " + removedWhiteSpace + ". Swear is " + swear);
				foundBadWord = true;
				break;
			}
		}

		//same as above but this time instead of replacing leetspeak we check without symbols and also we remove duplicates
		final var sb = new StringBuilder();
		removedWhiteSpace.chars().distinct().forEach(c -> sb.append(((char) c)));
		removedWhiteSpace = sb.toString();
		final var removedSymbolsAndDuplicateChars = removedWhiteSpace.replaceAll("[^A-Za-z]+", "");

		for (final String swear : BAD_WORDS) {
			if (FALSE_POSITIVES.contains(removedSymbolsAndDuplicateChars.toLowerCase())) continue;
			final var similarity = LevenshteinDistance.getDefaultInstance().apply(removedSymbolsAndDuplicateChars, swear);
			final var jaroWinkler = new JaroWinklerDistance().apply(removedSymbolsAndDuplicateChars, swear);
			if (removedWhiteSpace.toLowerCase().startsWith(swear) || removedSymbolsAndDuplicateChars.toLowerCase().contains(swear) || similarity == 1 || jaroWinkler >= 0.93) {
				Arrays.fill(arguments, "#");
				System.out.println("Found swear in argument " + removedWhiteSpace + ". Swear is " + swear);
				foundBadWord = true;
				break;
			}
		}

		if (foundBadWord) {
			for (final Player player : Bukkit.getOnlinePlayers()) {
				if (player.hasPermission("seu.swear.notify")) {
					Utilities.sendMsg(player, "&c[Swear]&4 O παίκτης " + e.getPlayer().getName() + " προσπάθησε να πει: &e" + e.getMessage());
				}
			}
			for (int i = 0; i < arguments.length; i++) {
				arguments[i] = ChatColor.stripColor(arguments[i]);
			}
			e.setMessage(String.join(" ", arguments));
		}
		//block ads
		if (e.getMessage().equals("I joined using ChatCraft from my Android device! Download it for free!")) {
			e.setCancelled(true);
		}

	}
}
