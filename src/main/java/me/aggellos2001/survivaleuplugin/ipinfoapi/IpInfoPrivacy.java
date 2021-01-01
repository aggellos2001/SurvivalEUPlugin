package me.aggellos2001.survivaleuplugin.ipinfoapi;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import me.aggellos2001.survivaleuplugin.SurvivalEUPlugin;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class IpInfoPrivacy {

	private static final ConcurrentMap<String, PrivacyAPI> IP_CACHE = new ConcurrentHashMap<>();
	private static final Gson gson = new Gson();
	private static final HttpClient client = HttpClient.newHttpClient();

	private PrivacyAPI requestAPI(@NotNull final String hostAddress) {
		var token = (String) SurvivalEUPlugin.config.getValue("ip-key");
		if (token.equalsIgnoreCase("REPLACE_WITH_API_TOKEN")) {
			Bukkit.getServer().getLogger().info("IP INFO API KEY NOT CONFIGURED!");
			return PrivacyAPI.DEFAULT_RESPONSE;
		}
		final var request = HttpRequest.newBuilder()
				.uri(URI.create("http://ipinfo.io/" + hostAddress + "/privacy?token=" + token))
				.GET()
				.timeout(Duration.ofSeconds(10))
				.build();
		try {
			final var response = client.send(request, HttpResponse.BodyHandlers.ofString()).body();
			final var json = gson.fromJson(response, PrivacyAPI.class);
			IP_CACHE.put(hostAddress, json);
			return IP_CACHE.get(hostAddress);
		} catch (IOException | InterruptedException | JsonSyntaxException e) {
			e.printStackTrace();
		}
		return PrivacyAPI.DEFAULT_RESPONSE;

	}

	public PrivacyAPI lookPrivacyIP(final String hostAddress) {
		final var ip = IP_CACHE.get(hostAddress);
		return ip != null ? ip : requestAPI(hostAddress);
	}
}
