package me.aggellos2001.survivaleuplugin.languages;

import me.aggellos2001.survivaleuplugin.utils.Utilities;
import org.bukkit.entity.Player;

public enum Language {

	NO_MONEY("&cNot enough money!", "&cΔεν έχεις αρκετά λεφτά!"),
	WILD_SUCCESS_TP("&aYou got teleported into the wild successfully!", "&aΗ τηλεμεταφορά σε τυχαίο σημείο ολοκληρώθηκε επιτυχώς!"),
	WILD_WILD_UNSUCCESFUL("&cTeleportation failed. Do /wild again!", "&cΗ τηλεμεταφορά σε τυχαίο σημείο απέτυχε. Κάνε ξανά /wild!"),
	WILD_MONEY_SUBTRACTED("&aYour balance was successfully charged %s$ for the teleportation!", "&aΠληρώσες επιτυχώς %s$ για την τηλεμεταφορά!"),
	WILD_PENDING_TP("&cTeleport is pending! Please wait!", "&cΗ διαδικασία τηλεμεταφοράς είναι σε εξέλιξη! Περιμένετε!"),
	WILD_COOLDOWN("&cWait %s seconds before using the command again!", "&cΠερίμενε %s δευτερόλετα πρίν ξαναχρησιμοποιήσεις την εντολή!"),
	WILD_UNSUPPORTED_ENVIRONMENT("&cWild is not supported in this world!", "&cΤο wild δεν υποστηρίζεται σε αυτό τον κόσμο!"),
	WILD_SEARCHING_FOR_LOCATION("&aYou will be teleported shortly! Please wait!", "&aΘα τηλεμεταφερθείς σύντομα! Παρακαλώ περίμενε!"),
	WELCOME_MESSAGE(
			"&6Season 3 is here!\n" + "&bThe server is &aSurvival&r.&b Do &c&l/wild&r&b to get teleported randomly into the world!\n"
					+ "Join our Discord Server: &e&lhttps://discord.gg/cVjZbvt",
			"&6Η Season 3 είναι εδώ!\n" + "&bΟ server είναι &aSurvival&r.&b Κάνε &c&l/wild&r&b για να βρεις ένα τυχαίο μέρος για να κάνεις το σπίτι σου!\n"
					+ "Discord Server: &e&lhttps://discord.gg/cVjZbvt"),
	BLOCK_VPN_REJECTED("&cUsing a VPN is not allowed!", "&cΗ χρήση του VPN δεν επιτρέπεται!"),
	DONATION_INFO(
			"&3&l&m                               \n" +
					"&6&lDonations\n" +
					Utilities.emptyLine() +
					"&eΤι Ranks υπάρχουν? Τι κερδίζω?\n" +
					"&bΠώς μπορώ να τα αγοράσω?\n" +
					"&aΜπορείτε να μάθετε τα πάντα στο Site!\n" +
					Utilities.emptyLine() +
					"&b&lhttps://survivaleu.com/ranks.html\n" +
					"&3&l&m                                \n",
			"&3&l&m                               \n" +
					"&6&lΔωρεές\n" +
					Utilities.emptyLine() +
					"&eAre there any ranks available?\n" +
					"&bHow can I buy them??\n" +
					"&aCheck our website for more information!\n" +
					Utilities.emptyLine() +
					"&b&lhttps://survivaleu.com/ranks.html\n" +
					"&3&l&m                                \n"),
	DONATION_POTION_ENABLE_FAIL_PVP("&cYou cannot enable pvp while having donation potions enabled!", "&cΔεν μπορείς να ενεργοποιήσεις τα potions με ενεργοποιημένο το PvP!"),
	DONATION_POTIONS_DISABLED("&cDonation potions were disabled!", "&cΤα potions απενεργοποιήθηκαν!"),
	DONATION_POTIONS_ENABLED("&aDonation potions were enabled!", "&aΤα potions ενεργοποιήθηκαν!"),
	DONATION_NOT_DONATED_ACCESS_DENIED("&cYou haven't donated yet!", "&cΔεν έχεις κάνει δωρεά!"),
	NO_EMPTY_HOME("&cYou have to name your home first. For example /sethome myhome", "&cΠρέπει να δώσεις όνομα στο σπίτι πριν κάνεις /sethome!&6&l Για παράδειγμα /sethome spiti1"),
	TAMED_HARM_DENIED("&cYou cannot harm this mob. It is tamed by someone else!", "&cΔεν μπορείς να σκοτώσεις αυτό το ζώο. Είναι εξημερωμένο από κάποιον άλλο!"),
	TAMED_PUT_LAVA_DENIED("&cYou cannot put lava here! There is a tamed mob nearby!", "&cΔεν μπορείς να βάλεις lava εδώ. Βρίσκεται κοντά ένα εξημερωμένο ζώο!"),
	PVP_COOLDOWN("&cPlease wait %s seconds before using this command again!", "&cΠερίμενε %s δευτερόλεπτα πριν χρησιμοποιήσεις ξανά την εντολή /pvp!"),
	PVP_DISABLED("&cPvP is now disabled!", "&cΤο PvP απενεργοποιήθηκε!"),
	PVP_ENABLED("&aPvP is now enabled!", "&aTo PvP ενεργοποιήθηκε!"),
	PVP_DONATION_POTIONS_DENIED("&cYou cannot enable PvP while donation potions are enabled!", "&cΔεν μπορείς να ενεργοποιήσεις το PvP με τα Donation Potion ανοιχτά!"),
	PVP_STATUS("&aYour PvP is %s", "&aΤο PvP σου είναι %s"),
	PVP_DISABLED_WARNING("&cYou have PvP disabled! Do /settings to enabled it!", "&cΈχεις το PvP απενεργοποιημένο! Κάνε /settings για να το ενεργοποιήσεις!"),
	PVP_OTHER_DISABLED_WARNING("&cPlayer %s has PvP disabled!", "&cΟ παίκτης %s έχει το PvP απενεργοποιημένο!"),
	SLIME_CHUNK_ALREADY_THERE("&aYou're in a Slime Chunk. Slimes spawn at Y less or equal of 40!", "&aΒρίσκεσαι σε Slime Chunk! Τα Slime κάνουν spawn σε Y μικρότερο ή ίσο του 40!"),
	SLIME_CHUNK_SWAMP_BIOME("&aYou are in a SWAMP Biome. Slimes here can spawn on a light level below 8 (Which means no bright light sources nearby)!", "&aΒρίσκεσαι σε SWAMP Biome. Εδώ τα Slimes μπορούν να κάνουν spawn σε 50-70 Y με light level 7 ή λιγότερο (όχι πολύ φως δηλαδή)."),
	SLIME_CHUNK_NOT_SUITABLE_LOCATION("&cSlimes cannot spawn in the biome you're in right now! Find a slime chunk or a swamp biome!", "&cΣτο Βiome που βρίσκεσαι δεν γίνεται να κάνουν spawn τα slimes. Πρέπει να είσαι ή σε Slime Chunk ή σε SWAMP Biome."),
	SLOW_MODE_COOLDOWN("&cPlease wait %s seconds before chatting again!", "&cΠαρακαλώ περίμενε %s δευτερόλεπτα πριν ξαναστείλες μήνυμα!"),
	VOTE_INFO(
			"&6Please vote to help the server!\n" +
					"&b1.Vote here: &a&lhttps://minecraft-mp.com/server/173291/vote/\n" +
					"&b2.Once you submit your vote do &a&l/vote claim&r&b to get your reward!\n" +
					"&c&lYou must have some empty space on your inventory (1 slot minimum) before doing /vote claim or " +
					"your reward may be lost!",
			"&6Κάνοντας Vote βοηθάς τον σέρβερ!\n" +
					"&b1.Κάνε vote εδώ: &a&lhttps://minecraft-mp.com/server/173291/vote/\n" +
					"&b2.Μόλις κάνεις vote κάνε &a&l/vote claim&r&b για να πάρεις την ανταμοιβή σου!\n" +
					"&c&lΠρέπει να έχεις χώρο στο inventory σου (1 slot τουλάχιστον) πριν κάνεις /vote claim αλλιώς " +
					"θα χαθεί η ανταμοιβή σου!"
	),
	VOTE_DID_NOT_VOTED("&cYou haven't voted yet!", "&cΔεν έκανες vote ακόμα!"),
	VOTE_DID_NOT_CLAIMED("&aYou have voted but you didn't do /vote claim yet.", "&aΈκανες vote &cαλλά δεν έκανες &a&l/vote claim!"),
	VOTE_VOTED("&aYou have voted and taken your reward! Thank you for supporting us! Want to help more? Check /donation", "&aΈκανες vote και έχεις πάρει την ανταμοιβή σου! Ευχαριστούμε που μας υποστηρίζεις! Θέλεις να βοηθήσεις περισσότερο? Δες /donation"),
	VOTE_ERROR("&cThere was a problem! Please try again later or contact a staff member!", "&cΥπήρξε ένα πρόβλημα και δεν μπορούμε να ελέγξουμε το αίτημα σου. Δοκιμάσε αργότερα!"),
	VOTE_NO_INV_SPACE("&c&lYou don't have enough space in your inventory!", "&c&lΔεν έχεις χώρο στο inventory για να πάρεις την ανταμοιβή σου!"),
	VOTE_ALREADY_CLAIMED("&cYou have claimed your reward for today already! Try again later!", "&cΈχεις κάνει claim ήδη μια φορά σήμερα! Δοκίμασε αργότερα!"),
	VOTE_REWARD_RECEIVED("&aYou got your reward successfully! Thank you for the support!", "&aΠήρες την ανταμοιβή σου επιτυχώς! Ευχαριστούμε για την υποστήριξη!"),
	VOTE_REWARDS("&aYou got 500 claim blocks, 2 phantom membranes and 1000$ for your vote!", "&aΠήρες 500 claim block, 2 phantom membrane και 1000$ για το vote σου!"),
	VOTE_BROADCAST("&aPlayer&6 %s &avoted for the server!", "&aΟ παίκτης&6 %s &aέκανε vote!"),
	SIT_ON_STAIRS_ENABLED("&aYou can now sit on any stair blocks by right click them!", "&aΤώρα μπορείς να κάθεσαι στις σκάλες με δεξί κλίκ πάνω τους!"),
	SIT_ON_STAIRS_DISABLED("&cYou will no longer sit on stair blocks when right clicking them", "&cΤώρα δεν μπορείς να κάθεσαι στις σκάλες με δεξί κλίκ πάνω τους!"),
	SIT_ON_STAIRS_NOTIFY("&eYou can disable sitting on a stair blocks any time by doing /settings ", "&eΜπορείς να απενεργοποιήσεις να κάθεσε στις σκάλες με την εντολή: /settings"),
	SIT_ON_STAIRS_DELAY("&cPlease wait %s seconds before sitting again!", "&cΠερίμενε %s δευτερόλεπτα πριν ξανακαθήσεις!"),
	ADVERTISEMENT("&aRemember to /vote once a day. Do you want a rank? Do /donation for more info! Join our discord server /discord", "&aΜην ξεχνάς να κάνεις /vote. Ενδιαφέρεσαι για rank? Κάνε /donation για πληροφορίες! Μπες στο Discord μας /discord"),
	PAYSELF_EXPLOIT("&cYou cannot send money to accounts with the same IP!", "&cΔεν μπορείς να στείλεις λεφτά σε account με στην ίδια IP!"),
	SLOW_COMMAND("&cWait %s seconds before running a command!", "&cΠερίμενε %s δευτερόλεπτα πριν ξανατρέχεις κάποια εντολή!"),
	SHOP_NOT_AVAILABLE("&cThis item is not available in the shop currently!", "&cΑυτό το item δεν βρίσκεται στο shop!"),
	SHOP_PRICES_BUY("&aBuy price: %s", "&aΤιμή buy: %s"),
	SHOP_PRICES_SELL("&r&aSell price: %s", "Τιμή sell: %s"),
	SHOP_SUCCESS_BUY("&aSuccessfully bought %s %s for %s$!", "&aΑγόρασες επιτυχώς %s %s για %s$"),
	SHOP_NOT_ENOUGH_SPACE("&cThere was not enough space! Some items were dropped on the ground!", "&aΔεν είχες αρκετό χώρο. Κάποια item έπεσαν στο πάτωμα."),
	SHOP_INVALID_AMOUNT("&cInvalid amount number!", "&cΛανθασμένος αριθμός!"),
	SHOP_BUY_NO_AMOUNT("&cSpecify how much %s you want to buy!", "&cΓράψε πόσο %s θες να αγοράσεις!"),
	SHOP_SELL_NOT_SPECIFIED("&cYou must specify if you want to sell %s from the hand or your inventory!",
			"&cΠρέπει να γράψεις αν θες να πουλήσεις %s από το χέρι (hand) η το inventory (inventory)!"),
	SHOP_SELL_HAND_DIFFERENT_ITEM("&cYou are trying to sell %s but you hold %s in your hand!", "&cΠροσπαθείς να πουλήσεις %s αλλά κρατάς στο χέρι %s!"),
	SHOP_SELL_SUCCESS("&aSuccessfully sold %s %s for %s$!", "&aΠούλησες %s %s επιτυχώς για %s"),
	SHOP_SELL_INVENTORY_NO_ITEM("&cNo %s was found in your inventory!", "&cΔεν βρέθηκε %s στο inventory σου!"),
	SHOP_INFO(
			"\n&6&l&m         &r&6&l SHOP &m         &r&6&l\n" +
					Utilities.emptyLine() +
					"&b&lTo see available items:\n" +
					"&6&l/shop buy/sell&b(space)&6&l and a list will popup with all the items\n" +
					Utilities.emptyLine() +
					"&b&lTo buy / sell items:\n" +
					"&6&l/shop &bbuy&6&l item amount\n" +
					"&6&l/shop &bsell&6&l item hand/inventory\n" +
					Utilities.emptyLine() +
					"&b&lTo see prices: \n" +
					"&6&l/shop buy/sell item\n" +
					Utilities.emptyLine() +
					"&6&l&m                            "
			,
			"\n&6&l&m         &r&6&l SHOP &m         &r&6&l\n" +
					Utilities.emptyLine() +
					"&b&lΓια να δείτε τα διαθέσιμα item:\n" +
					"&6&l/shop buy/sell&b(space)&6&l and a list will popup with all the items\n" +
					Utilities.emptyLine() +
					"&b&lΓια αγορά / πώληση:\n" +
					"&6&l/shop &bbuy&6&l item amount\n" +
					"&6&l/shop &bsell&6&l item hand/inventory\n" +
					Utilities.emptyLine() +
					"&b&lΓια να δείτε τις τιμές: \n" +
					"&6&l/shop buy/sell item\n" +
					Utilities.emptyLine() +
					"&6&l&m                            "
	),
	SHOP_CANNOT_BUY("&cYou cannot buy this item!", "&cΔεν μπορείς να αγοράσεις αυτό το item!"),
	SHOP_BUY_ZERO("&cYou cannot buy 0 %s", "&cΔεν μπορείς να αγοράσεις 0 %s"),
	SHOP_CANNOT_SELL("&cYou cannot sell this item!", "&cΔεν μπορείς να πουλήσεις αυτό το item!"),
	LAGINFO_HIGH_PING(
			"&aServer TPS: %s\n" +
					"&cYour ping: %s\n" +
					"&cYou have a high ping so you will lag in the server!",
			"&aServer TPS: %s\n" +
					"&cYour ping: %s\n" +
					"&cΈχεις μεγάλο ping οπότε θα κολλάς!"
	),
	LAGINFO_LOW_FPS(
			"&aServer TPS: %s\n" +
					"&aYour ping: %s\n" +
					"&aThe server is not lagging. You have no ping issues. Make sure you have optifine if you experience FPS drops!",
			"&aServer TPS: %s\n" +
					"&aYour ping: %s\n" +
					"&aΟ server δεν κολλάει ούτε έχεις προβλήματα ping. Αν και πάλι κολλάς βάλε optifine."
	),
	LAGINFO_LOW_TPS(
			"&cServer TPS: %s\n" +
					"&aYour ping: %s\n" +
					"&cThe server is lagging. The server usually stops lagging after a while!",
			"&cServer TPS: %s\n" +
					"&aYour ping: %s\n" +
					"&cΟ server κολλάει λίγο. Περίμενε λίγα λεπτά και συνήθως σταματάει να κολλάει!"
	),
	VOTEKICK_STARTED(Utilities.getHexColor("#ffc403") +
			"&lA votekick has been started for player &e %s " + Utilities.getHexColor("#ffc403") + "&lby&e %s\n" +
			Utilities.getHexColor("#ffc403") + "/votekick yes\n" +
			Utilities.getHexColor("#ff0314") + "/votekick no\n" +
			"&eVote ends in 60 seconds!",

			Utilities.getHexColor("#ffc403") +
					"&lΨηφοφορία για kick άρχισε για τον παίκτη &e %s " + Utilities.getHexColor("#ffc403") + "&lαπό τον παίκτη&e %s\n" +
					Utilities.getHexColor("#ffc403") + "/votekick yes\n" +
					Utilities.getHexColor("#ff0314") + "/votekick no\n" +
					"&eΗ ψηφοφορία λήγει σε 60 δευτερόλεπτα!"),
	VOTEKICK_CANNOT_KICK_SELF("&cYou cannot votekick yourself!", "&cΔεν μπορείς να ξεκινήσεις ψηφοφορία για τον εαυτό σου!"),
	VOTEKICK_FAILED("&cThe votekicked was failed. Not enough votes!",
			"&cΤο votekick δεν ολοκληρώθηκε. Δεν μαζεύτηκαν αρκετά vote!"),
	VOTEKICK_SUCCESS("&aThe votekicked was successfull. Kicking player %s!"
			, "&aΤο votekick oλοκληρώθηκε! Ο παίκτης %s θα διωχθεί!"),
	VOTEKICK_NOT_ONGOING("&cThere is no votekick currently ongoing!",
			"&cΚαμία ψηφοφορία σε εξέλιξη!"),
	VOTEKICK_CANT_VOTE_FOR_SELF("&cYou cannot vote while being votekicked!",
			"&cΔεν μπορείς να ψηφίσεις για τον εαυτό σου!"),
	VOTEKICK_VOTED_YES("&aYou voted YES!", "&aΨήφισες &lΝΑΙ!"),
	VOTEKICK_VOTED_NO("&aYou have voted NO!", "&aΨήφισες &c&lΟΧΙ"),
	VOTEKICK_ALREADY_VOTED("&cYou have already voted on this vote!", "&cΈχεις ήδη ψηφίσει σε αυτό αυτή την ψηφοφορία"),
	VOTEKICK_VOTE_ALREADY_ONGOING("&cThere is a vote ongoing for player &e%s &c.", "&cΕίναι σε εξέλιξη ήδη μια ψηφοφορία για τον παίκτη &e%s &c."),
	REPORT_SELFREPORT("&cYou cannot report yourself!","&cΔεν μπορείς να κάνεις αναφορά στον εαυτό σου!");


	Language(
			final String english, final String greek) {
		this.english = english;
		this.greek = greek;
	}

	public final String english;
	public final String greek;

	public String getTranslation(final Player player) {
		if (PlayerLanguage.getPlayerLanguage(player).equals("greek"))
			return this.greek;
		else return this.english;
	}
}
