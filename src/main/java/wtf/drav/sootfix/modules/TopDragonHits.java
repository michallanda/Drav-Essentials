package wtf.drav.sootfix.modules;

import java.util.ArrayList;
import java.util.Hashtable;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

// top dragon hits
public class TopDragonHits implements CommandExecutor, Listener {
	public static Hashtable<String, Integer> hitsondragon = new Hashtable<String, Integer>();
	 
	// event that keeps track of dragon hits
    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();
        Entity damager = event.getDamager();
		// check if the hit entity is a dragon and if the damage was done by a player or an arrow
        if(entity instanceof EnderDragon && (damager instanceof Player || damager instanceof Arrow)){
            Player player;
			// if damage was done by a player, use the player, else get the player who shot the arrow
        	if(damager instanceof Player) player = (Player) damager;
        	else	player = (Player) ((Projectile) damager).getShooter();
            String playername = player.getName();
			
			// if dictionary already contains the player, add on to his hits, else add a new entry with 1 hit
            if(hitsondragon.containsKey(playername)){
                int hits = hitsondragon.get(playername);
                hits++;
                hitsondragon.remove(playername); hitsondragon.put(playername, hits);
            }else if(!hitsondragon.containsKey(playername)){
                hitsondragon.put(playername, 1);
            }
        }
    }
    
	// command for a list of dragon hits
	@Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player)sender;
		ArrayList<String> top = new ArrayList<String>();
		
		// go through all players in the hitsondragon dictionary, sort them by most hits, and send the list to the players chat
		for(String hits : hitsondragon.keySet()) {
			if(top.isEmpty()) {
				top.add(hits);
			}
			else {
				boolean passed = false;
				for(int i = 0; i < top.size(); i++) {
					if(hitsondragon.get(hits) >= hitsondragon.get(top.get(i))) {
						top.add(i, hits);
						break;
					}
					passed = true;
				}
				if(passed)	top.add(hits);
			}
		}
		for(String p : top) {
			player.sendMessage(p + " : " + hitsondragon.get(p));
		}
		return true;
	}
}
