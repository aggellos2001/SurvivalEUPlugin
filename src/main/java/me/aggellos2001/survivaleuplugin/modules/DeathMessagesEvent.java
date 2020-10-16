package me.aggellos2001.survivaleuplugin.modules;

import me.aggellos2001.survivaleuplugin.hooks.LuckPermsHook;
import me.aggellos2001.survivaleuplugin.utils.PluginActivity;
import me.aggellos2001.survivaleuplugin.utils.Utilities;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.Random;

public final class DeathMessagesEvent extends PluginActivity {

	@EventHandler(ignoreCancelled = true)
	private void onPlayerDeath(final PlayerDeathEvent e) {
		final var lastDamageCause = e.getEntity().getLastDamageCause();
		final var killer = e.getEntity().getKiller();
		final var rank = LuckPermsHook.getPlayerRank(e.getEntity()).format;
		final var killerRank = LuckPermsHook.getPlayerRank(killer).format;
		final var deadPlayer = e.getEntity();
		final var randomNumber = new Random().nextInt(3);
		final var deadPlayerRankFormatted = rank + "&b " + deadPlayer.getName();
		final var killerRankFormatted = killer != null ? killerRank + "&b " + killer.getName() : "";
		String deathMessage = null;

		if (lastDamageCause == null) {
			e.setDeathMessage(Utilities.colorize("&6[&bSurvivalEU-&r&c&lDeath&r&6] " + deadPlayerRankFormatted + " died!", false));
			return;
		}

		final var cause = lastDamageCause.getCause();

		switch (cause) {
			case CONTACT: {
				if (killer != null) {
					deathMessage = deadPlayerRankFormatted + " walked into a cactus whilst trying to escape " +
							killerRankFormatted;
				} else {
					deathMessage = deadPlayerRankFormatted + " was pricked to death!";
				}
				break;
			}
			case ENTITY_ATTACK:
			case ENTITY_SWEEP_ATTACK: {
				if (killer != null) {
					if (randomNumber == 0) {
						deathMessage = deadPlayerRankFormatted + " died because of " + killerRankFormatted;
					} else {
						deathMessage = deadPlayerRankFormatted + " got killed by " + killerRankFormatted;
					}
				}
				break;
			}
			case PROJECTILE: {
				if (killer != null) {
					if (randomNumber == 0) {
						deathMessage = deadPlayerRankFormatted + " got shot by " + killerRankFormatted;
					} else {
						deathMessage = deadPlayerRankFormatted + " got sniped by " + killerRankFormatted;
					}
				} else {
					deathMessage = deadPlayerRankFormatted + " was shot to death!";
				}
				break;
			}
			case SUFFOCATION: {
				if (randomNumber == 0) {
					deathMessage = deadPlayerRankFormatted + " suffocated!";
				} else {
					deathMessage = deadPlayerRankFormatted + " couldn't breathe!";
				}
				break;
			}
			case FALL: {
				if (randomNumber == 0) {
					deathMessage = deadPlayerRankFormatted + " fell down really hard!";
				} else {
					deathMessage = deadPlayerRankFormatted + " slipped from a high altitude!";
				}
				break;
			}
			case FIRE: {
				if (randomNumber == 0) {
					deathMessage = deadPlayerRankFormatted + " was burnt!";
				} else {
					deathMessage = deadPlayerRankFormatted + " forgot he was on fire!";
				}
				break;
			}
			case FIRE_TICK: {
				if (randomNumber == 0) {
					deathMessage = deadPlayerRankFormatted + " did not put out the fire quickly enough!";
				} else {
					deathMessage = deadPlayerRankFormatted + " burned as he tried to save himself!";
				}
				break;
			}
			case MELTING:
			case CUSTOM: {
				deathMessage = deadPlayerRankFormatted + " died!";
				break;
			}
			case LAVA: {
				if (randomNumber == 0) {
					deathMessage = deadPlayerRankFormatted + " was burned by lava!";
				} else if (randomNumber == 1) {
					deathMessage = deadPlayerRankFormatted + " bathed in lava!";
				} else {
					deathMessage = deadPlayerRankFormatted + " he thought lava was red water!";
				}
				break;
			}
			case DROWNING: {
				if (randomNumber == 0) {
					deathMessage = deadPlayerRankFormatted + " drowned!";
				} else {
					deathMessage = deadPlayerRankFormatted + " forgot that he needs to breath oxygen and died!";
				}
				break;
			}
			case BLOCK_EXPLOSION: {
				if (randomNumber == 0) {
					deathMessage = deadPlayerRankFormatted + " stood near an explosion to see what will happen!";
				} else {
					deathMessage = deadPlayerRankFormatted + " exploded into a million pieces";
				}
				break;
			}
			case ENTITY_EXPLOSION: {
				if (randomNumber == 0) {
					deathMessage = deadPlayerRankFormatted + " thought that explosions are nice!";
				} else {
					deathMessage = deadPlayerRankFormatted + " exploded by an explosion! How is that possible?";
				}
				break;
			}
			case VOID: {
				if (randomNumber == 0) {
					deathMessage = deadPlayerRankFormatted + " he thought it was a good idea to jump into the void!";
				} else {
					deathMessage = deadPlayerRankFormatted + " he died stupidly by falling into nothingness!";
				}
				break;
			}
			case LIGHTNING: {
				if (randomNumber == 0) {
					deathMessage = deadPlayerRankFormatted + " died from an angry cloud!";
				} else {
					deathMessage = deadPlayerRankFormatted + " was so unlucky that a lightning killed him!";
				}
				break;
			}
			case SUICIDE: {
				deathMessage = deadPlayerRankFormatted + " committed suicide";
				break;
			}
			case STARVATION: {
				if (randomNumber == 0) {
					deathMessage = deadPlayerRankFormatted + " was starved to death";
				} else {
					deathMessage = deadPlayerRankFormatted + " forgot there is a food bar in this game!";
				}
				break;
			}
			case POISON: {
				if (randomNumber == 0) {
					deathMessage = deadPlayerRankFormatted + " died from poison";
				} else {
					deathMessage = deadPlayerRankFormatted + " was poisoned and died painfully";
				}
				break;
			}
			case MAGIC: {
				if (killer != null) {
					if (randomNumber == 0) {
						deathMessage = deadPlayerRankFormatted + " died because of magic by " + killerRankFormatted;
					} else {
						deathMessage = killerRankFormatted + " used magic to kill " + deadPlayerRankFormatted;
					}
				} else {
					deathMessage = deadPlayerRankFormatted + " died from magic";
				}
				break;
			}
			case WITHER: {
				deathMessage = deadPlayerRankFormatted + " withered far away!";
				break;
			}
			case FALLING_BLOCK: {
				deathMessage = deadPlayerRankFormatted + " was severely hit by a falling block!";
				break;
			}
			case THORNS: {
				deathMessage = deadPlayerRankFormatted + " was stung by thorns and died";
				break;
			}
			case DRAGON_BREATH: {
				deathMessage = deadPlayerRankFormatted + " burned by the dragon's breath!";
				break;
			}
			case FLY_INTO_WALL: {
				deathMessage = deadPlayerRankFormatted + " crashed into the wall too hard!";
				break;
			}
			case HOT_FLOOR: {
				deathMessage = deadPlayerRankFormatted + " didn't notice that magma blocks are red hot!";
				break;
			}
			case CRAMMING: {
				deathMessage = deadPlayerRankFormatted + " wanted to fit into one block with many others but failed";
				break;
			}
			case DRYOUT:
			default: {
				deathMessage = deadPlayerRankFormatted + " died!";
			}
		}
		if (deathMessage == null)
			e.setDeathMessage(Utilities.colorize("&6[&bSurvivalEU-&r&c&lDeath&r&6] " + deadPlayerRankFormatted + " died!", false));
		else
			e.setDeathMessage(Utilities.colorize("&6[&bSurvivalEU-&r&c&lDeath&r&6] " + deathMessage, false));
	}

}
