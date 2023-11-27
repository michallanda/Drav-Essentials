package wtf.drav.sootfix.modules;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

// scan mining
public class ScanMining implements CommandExecutor {
	// command for scan mining
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		// if sender is a player and has permission, send the player a list of players under y = 20
    	if (sender instanceof Player && sender.hasPermission("sootfix.scanmining")) {
    		((Player)sender).sendRawMessage("Players Currently Mining: ");
    		for(Player player: Bukkit.getOnlinePlayers()) {
    			if(player.getLocation().getY() < 20) {
    				((Player)sender).sendRawMessage(" : " + player.getName());
    			}
    	
    		}
    	}
        return true;
    }
}
