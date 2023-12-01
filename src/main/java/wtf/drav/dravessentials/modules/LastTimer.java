package wtf.drav.dravessentials.modules;

import java.util.Calendar;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

// last timer
public class LastTimer implements CommandExecutor {
	FileConfiguration config;
	Plugin plugin;
	String placeholder = "";
	
	// get information on the lasttimer in the config file, where the data is kept on the last time the timer was set
	public LastTimer(FileConfiguration config, Plugin plugin) {
		this.config = config;
		this.plugin = plugin;
		this.placeholder = config.getString("lastTimerPlaceholder");
	}
	
	// command for last timer
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		// check if a player is running the command and if they have permission
    	if (sender instanceof Player && sender.hasPermission("dravfix.lasttimer")) {
			// if player runs the command with set after it, will set the timer to current time, informing the player of doing so
    		if(args.length > 0 && args[0].equals("set")) {
    			Calendar cal = Calendar.getInstance();
    			config.set("lastTimer", cal.getTimeInMillis());
    			plugin.saveConfig();
    			sender.sendMessage("Reset last " + placeholder + " timer!");
    		}
			// else just return the time difference between current time and the last time the timer was set and send to player
    		else {
    			long offset = Calendar.getInstance().getTimeInMillis() - config.getLong("lastTimer");
    			sender.sendMessage("Last " + placeholder + " check was " + ((int)((offset / 1000.0) / 3600.0)) + " hour(s) and " + ((int)(((offset / 1000.0) / 3600.0) % 1 * 60)) + " minute(s)");
    		}
    	}
        return true;
    }

}
