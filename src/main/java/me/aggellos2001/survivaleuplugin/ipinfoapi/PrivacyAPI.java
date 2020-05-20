package me.aggellos2001.survivaleuplugin.ipinfoapi;

public class PrivacyAPI {

	public static final PrivacyAPI DEFAULT_RESPONSE = new PrivacyAPI(false, false, false, false);

	public boolean vpn;
	public boolean proxy;
	public boolean tor;
	public boolean hosting;

	public PrivacyAPI() {
	}

	public PrivacyAPI(boolean vpn, boolean proxy, boolean tor, boolean hosting) {
		this.vpn = vpn;
		this.proxy = proxy;
		this.tor = tor;
		this.hosting = hosting;
	}

	@Override
	public String toString() {
		return "VPN:" + vpn + ",Proxy:" + proxy + ",Tor:" + tor + ",Hosting:" + hosting;
	}
}
