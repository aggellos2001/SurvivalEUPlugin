package me.aggellos2001.survivaleuplugin.playerdata;

/**
 * Data class that we use with {@link com.google.gson.Gson} to create player data in {@link PlayerDataEvent}
 */
public final class PlayerData {

	/**
	 * An instance with default values from constructor {@link PlayerData#PlayerData()}
	 * Because is a static field it's implicitly transient. The keyword is added for readability.
	 */
	protected transient static final PlayerData DEFAULT = new PlayerData();

	public boolean keepingInventory;
	public boolean sittingOnStairs;
	public boolean pvp;
	public int supportPIN;
	public String chatColor;

	/**
	 * No argument constructor needed from Gson to get default values for new fields etc.
	 */
	private PlayerData() {
		this.keepingInventory = true;
		this.sittingOnStairs = false;
		this.pvp = false;
		this.supportPIN = 0;
		this.chatColor = "f"; //white default chat color
	}

	@Override
	public String toString() {
		return "PlayerData{" +
				"keepingInventory=" + keepingInventory +
				", sittingOnStairs=" + sittingOnStairs +
				", pvp=" + pvp +
				", supportPIN=" + supportPIN +
				", chatColor='" + chatColor + '\'' +
				'}';
	}
}
