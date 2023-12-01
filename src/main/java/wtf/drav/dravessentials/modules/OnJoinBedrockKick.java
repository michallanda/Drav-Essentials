package wtf.drav.dravessentials.modules;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import net.luckperms.api.LuckPerms;

// on bedrock join
public class OnJoinBedrockKick implements Listener {
	LuckPerms luckPerms;
	
	public OnJoinBedrockKick(LuckPerms luckPerms) {
		this.luckPerms = luckPerms;
	}
	
	// event that listens for a player joining the server, if their UUID starts with 6 0's, it means it is a bedrock player, send the
	// player to the hub
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		if(e.getPlayer().getUniqueId().toString().startsWith("000000")) {
			e.getPlayer().performCommand("server hub1");
		}
	}
}
