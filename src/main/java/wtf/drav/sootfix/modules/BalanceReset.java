package wtf.drav.sootfix.modules;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

// balance reset
public class BalanceReset implements CommandExecutor {
	private Plugin plugin;
	
	public BalanceReset(Plugin plugin) {
		this.plugin = plugin;
	}

	// creates the command for balance reset
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		// check if sender is a player, and also has the permission to run the command
    	if (sender instanceof Player && sender.hasPermission("sootfix.balancereset")) {
    		if(args.length == 1) {
				// resets the balance and logs the info, keeps track of the current players balance and then resets their balance
    			plugin.getLogger().info("SootFix >> --- BAL RESET --- ");
    			Bukkit.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "bal " + args[0]);
    			Bukkit.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "eco reset " + args[0]);
    			plugin.getLogger().info("SootFix >> Mod: " + sender.getName() + ", Reset Player: " + args[0] + "\'s Eco!");
    			plugin.getLogger().info("SootFix >> --- BAL RESET --- ");

				// lets the staff know that the reset was done
    			((Player)sender).sendRawMessage("Reset Eco of: " + args[0]);
    			((Player)sender).sendRawMessage("Check the player balance with /bal to make sure it was properly reset.");
    		}	
    	}
        return true;
    }
}
