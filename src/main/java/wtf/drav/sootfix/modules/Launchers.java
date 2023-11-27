package wtf.drav.sootfix.modules;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

// launchers
public class Launchers implements Listener {
    // keep track if a player moves, if they do, get their current location, checks if their current location has a pressure plate
    // in it, checks if the block under the pressure plate has a sign, if so, sends the player by adding a new vector to their current 
    // velocity
	@EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        Location l = p.getLocation();
        Location plateLoc = new Location(l.getWorld(), l.getX(), l.getY(), l.getZ());
        Location signLoc = new Location(plateLoc.getWorld(), plateLoc.getX(), plateLoc.getY() - 2, plateLoc.getZ());
        if(plateLoc.getBlock().getType() == Material.SPRUCE_PRESSURE_PLATE) {
            if(signLoc.getBlock().getType() == Material.OAK_SIGN || signLoc.getBlock().getType() == Material.OAK_WALL_SIGN) {
                if(e.getFrom().getX() - e.getTo().getX() > 0) {
                	e.getPlayer().setVelocity(e.getPlayer().getVelocity().add(new Vector(-3,.4,0)));
                }
                else {
                	e.getPlayer().setVelocity(e.getPlayer().getVelocity().add(new Vector(3,.4,0)));
                }
            }
        } 
    }
}
