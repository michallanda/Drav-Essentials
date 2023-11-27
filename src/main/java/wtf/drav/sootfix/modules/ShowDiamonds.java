package wtf.drav.sootfix.modules;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import net.coreprotect.CoreProtect;
import net.coreprotect.CoreProtectAPI;
import net.coreprotect.CoreProtectAPI.ParseResult;

// show diamonds
public class ShowDiamonds implements CommandExecutor {
	
	Plugin plugin;
	
	public ShowDiamonds(Plugin plugin) {
		this.plugin = plugin;
	}
	
	// command that displays previously mined diamonds with the barrier animation
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		// if the sender is a player and has permission, perform a lookup on the CoreProtect API and for every diamond block near the player
		// run an animation of the barrier block X
	   	if (sender instanceof Player && sender.hasPermission("sootfix.showdiamonds")) {
    		CoreProtectAPI api = getCoreProtect();
    		Player player = ((Player)sender);
    		if (api != null){ // ensure we have access to the API
    			List<String[]> lookup = api.performLookup(Integer.MAX_VALUE, Arrays.asList(args[0]), null, Arrays.asList(Material.DIAMOND_ORE, Material.DEEPSLATE_DIAMOND_ORE), null, null, 100, player.getLocation());
    			for(String[] block : lookup) {
    				ParseResult result = api.parseResult(block);
    				player.spawnParticle(Particle.BLOCK_MARKER, result.getX()+.5, result.getY()+.5, result.getZ()+.5, 1, Material.BARRIER.createBlockData());
      		  }
    		}
	   	}
		return false;
	}
	
	// code taken from CoreProtect Wiki
	private CoreProtectAPI getCoreProtect() {
	    Plugin corePlugin = plugin.getServer().getPluginManager().getPlugin("CoreProtect");
	 
	    // Check that CoreProtect is loaded
	    if (corePlugin == null || !(corePlugin instanceof CoreProtect)) {
	        return null;
	    }

	    // Check that the API is enabled
	    CoreProtectAPI CoreProtect = ((CoreProtect) corePlugin).getAPI();
	    if (CoreProtect.isEnabled() == false) {
	        return null;
	    }

	    // Check that a compatible version of the API is loaded
	    if (CoreProtect.APIVersion() < 6) {
	        return null;
	    }

	    return CoreProtect;
	}
}