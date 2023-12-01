package wtf.drav.dravessentials.modules;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import me.block2block.hubparkour.api.HubParkourAPI;
import me.block2block.hubparkour.api.events.player.ParkourPlayerFailEvent.FailCause;
import me.block2block.hubparkour.api.events.player.ParkourPlayerFinishEvent;
import me.block2block.hubparkour.api.events.player.ParkourPlayerLeaveEvent;

// parkour fix
public class ParkourEventsFix implements Listener {
	// event listener for the onParkourFinish event from HubParkour, teleports the player back to the start of the parkour
	@EventHandler
	public void onParkourFinish(ParkourPlayerFinishEvent e) {
		Player player = e.getPlayer().getPlayer();
		Location start = e.getParkour().getRestartPoint().getLocation();
		player.teleport(start.clone().add(.5,0,.5));
	}
	
	// event listener for when a player leaves a parkour from HubParkour, teleports the player back to the start of the parkour 
	@EventHandler
	public void onParkourLeave(ParkourPlayerLeaveEvent e) {
		Player player = e.getPlayer().getPlayer();
		Location start = e.getParkour().getRestartPoint().getLocation();
		player.teleport(start.clone().add(.5,0,.5));
	}
	
	// even listener for when a player changes worlds, causes them to fail the parkour they were doing
	@EventHandler
	public void onWorldChange(PlayerChangedWorldEvent e) {
		Player player = e.getPlayer();
		if(HubParkourAPI.isInParkour(player)) {
			HubParkourAPI.getPlayer(player).end(FailCause.CUSTOM);
		}
		
	}
}
