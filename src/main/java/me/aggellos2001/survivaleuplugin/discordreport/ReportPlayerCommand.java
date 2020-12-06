package me.aggellos2001.survivaleuplugin.discordreport;

import co.aikar.commands.annotation.*;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import me.aggellos2001.survivaleuplugin.languages.Language;
import me.aggellos2001.survivaleuplugin.utils.PluginActivity;
import me.aggellos2001.survivaleuplugin.utils.Utilities;
import org.bukkit.entity.Player;

import java.awt.*;
import java.io.IOException;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@CommandAlias("report")
public class ReportPlayerCommand extends PluginActivity {

	@Default
	@Syntax("[player] <reason>")
	@CommandCompletion("@players @nothing")
	@Conditions("cooldown:seconds=10")
	private void reportPlayer(Player playerReporting, OnlinePlayer playerGettingReported, String reason) {
		if (playerGettingReported.getPlayer().equals(playerReporting)){
			Utilities.sendMsg(playerReporting, Language.REPORT_SELFREPORT.getTranslation(playerReporting));
			return;
		}
		var url = "https://discord.com/api/webhooks/784880586623942656/c_a3BOQvplKdrdOP4fb9pMP0UylJVRqKwlUcwBmVR5HhFGj6G57jpXeSihPlHWEp1TFR";
		var report = new DiscordWebHookReport(url, playerReporting, playerGettingReported.getPlayer());
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		LocalDateTime time = LocalDateTime.now();
		report.addEmbed(new DiscordWebHookReport.EmbedObject()
				.setAuthor("Minecraft Report",null,"https://cdn4.iconfinder.com/data/icons/the-weather-is-nice-today/64/weather_48-256.png")
				.setColor(Color.GREEN)
				.addField("Player created report:", playerReporting.getName(), true)
				.addField("Player being reported:", playerGettingReported.getPlayer().getName(), true)
				.addField("Report:",reason,false)
				.setFooter("Minecraft server time: " + dtf.format(time),"https://www.iconsdb.com/icons/preview/white/clock-xxl.png")
		);
		try {
			report.execute();
		} catch (IOException e) {
			Utilities.sendMsg(playerReporting, "&cReport failed! Try again in a few seconds!");
		}
	}
}
