package wtf.drav.sootfix.modules;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.PortalCreateEvent;

// nether portal limiter
public class NetherPortalLimiter implements Listener {
	int overworldSize;
	
	// get the overworld worldborder size from the config
	public NetherPortalLimiter(FileConfiguration config) {
		overworldSize = config.getInt("overworldRadius") / 8; 
	}
	
	// on an event creation of a nether portal
	@EventHandler
	public void portCreate(PortalCreateEvent e) {
		Location loc = e.getBlocks().get(0).getLocation();
		// check if portal was created in the nether and if it is past the overworld radius, if so, cancel the event and let the player know
		// they cannot make a portal here
		if(e.getWorld().equals(Bukkit.getWorld("world_nether")) && ((overworldSize < Math.abs(loc.getBlockX())) || (overworldSize < Math.abs(loc.getBlockZ())))) {
			e.setCancelled(true);
			for(Entity nearby : e.getWorld().getNearbyEntities(loc, 6, 6, 6)) {
				if(nearby instanceof Player) {
					((Player) nearby).sendMessage("\u00A74You cannot build a portal here as you may suffocate on the other side!");
				}
			}
		}
	}
}
