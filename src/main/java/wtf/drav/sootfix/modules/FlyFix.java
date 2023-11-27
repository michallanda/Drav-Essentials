package wtf.drav.sootfix.modules;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

// fly fix
public class FlyFix implements Listener {
	Plugin plugin;
	
	public FlyFix(Plugin plugin) {
		this.plugin = plugin;
	}
	
	// fixes the issue where players lost their fly command when rejoining the server
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		// checks if player has the permission to fly from essentials, and is currently not flying, if so, sets them to flying
		if(e.getPlayer().hasPermission("essentials.fly") && !(e.getPlayer().isFlying())) {
        	e.getPlayer().setAllowFlight(true);
		}
	}
	
	// fixes the issue where players lost flight when changing gamemodes, simply checks if player is changing gamemodes and if they have fly
	// if so, enables flight for them and sets them to be actively flying to ensure they don't fall
	@EventHandler
	public void gmChange(PlayerGameModeChangeEvent e) {
		if(e.getPlayer().hasPermission("essentials.fly")) {
			Bukkit.getServer().getScheduler().runTaskLater(plugin, new Runnable() {
	            public void run() {
	            	e.getPlayer().setAllowFlight(true);
	            	e.getPlayer().setFlying(true);
	            	
	            }
	        }, 5);
		}
	}
}
