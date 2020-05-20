package me.aggellos2001.survivaleuplugin.utils;

import co.aikar.taskchain.TaskChain;
import co.aikar.taskchain.TaskChainAbortAction;
import org.bukkit.entity.Player;

public class MessageActionHandler implements TaskChainAbortAction<Player, String, Object> {
	@Override
	public void onAbort(TaskChain<?> chain, Player arg1) {
		Utilities.sendMsg(arg1, "&cSomething went wrong!");
	}

	@Override
	public void onAbort(TaskChain<?> chain, Player arg1, String arg2) {
		Utilities.sendMsg(arg1, arg2);
	}
}