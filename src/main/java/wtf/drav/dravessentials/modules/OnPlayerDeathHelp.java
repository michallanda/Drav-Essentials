package wtf.drav.dravessentials.modules;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

// on player death
public class OnPlayerDeathHelp implements Listener {
	// event for if a player dies, sends them a message letting them know the coordinates where they died
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		e.getEntity().sendRawMessage("\u00A7cYou died! ): " + e.getEntity().getLocation().getBlockX() + ", " + e.getEntity().getLocation().getBlockY() + ", " + e.getEntity().getLocation().getBlockZ());
	}
}
