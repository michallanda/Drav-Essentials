package wtf.drav.dravessentials.modules;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.earth2me.essentials.Essentials;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.cacheddata.CachedMetaData;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.NodeType;
import net.luckperms.api.node.types.MetaNode;

// nicknames
public class Nicknames implements CommandExecutor, Listener {
	LuckPerms luckPerms;
	Essentials essentials; 
	Plugin plugin;
	
	public Nicknames(LuckPerms luckPerms, Essentials essentials, Plugin plugin) {
		this.luckPerms = luckPerms;
		this.essentials = essentials;
		this.plugin = plugin;
	}
	
	// anick override for staff to use, creates a command that removes the ~ before their to signify real name
	@Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    	if (sender instanceof Player && sender.hasPermission("dravfix.anick")) {
    		((Player)sender).performCommand("nick " + args[0]);
    		setNickname(luckPerms, essentials, (Player)sender, true);
        }
    return true;
	}

	// checks if a player joins, event for setting nickname on all servers
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		if(e.getPlayer().hasPermission("essentials.nick")) {
			// gets their current nickname from the luckperms api and sets that nickname to the players nickname
		    CachedMetaData metaData = luckPerms.getPlayerAdapter(Player.class).getMetaData(e.getPlayer());
		    // if the user has a nickname in the DB, and their nickname is bugged out (missing a ~ as a non-staff or has multiple ~), re-run the setnickname method
			if(metaData.getMetaValue("nickname") != null && (!e.getPlayer().hasPermission("dravfix.anick") && (metaData.getMetaValue("nickname").charAt(0) != '~') || metaData.getMetaValue("nickname").charAt(1) == '~')) {
	    		setNickname(luckPerms, essentials, e.getPlayer(), false);
		    }
		    // else if they have a username in the DB and it is not their current in game username, just change their current username
			if(metaData.getMetaValue("nickname") != null && essentials.getUser(e.getPlayer()).getNickname() != metaData.getMetaValue("nickname")) {
		    	essentials.getUser(e.getPlayer()).setNickname(metaData.getMetaValue("nickname"));
		    }
			// else just set their nickname to null, just in case they have have removed their nickname on a different server
		    else {
		    	essentials.getUser(e.getPlayer()).setNickname(null);
		    }
		}
	}
	
	// set nickname method that sets the players nickname, and saves their nickname to the luckperms db through API calls
	public void setNickname(LuckPerms luckPerms, Essentials essentials, Player player, boolean isStaff) {
		new BukkitRunnable() {
			public void run() {
				// obtain a user instance and their nickname 
			    User user = luckPerms.getPlayerAdapter(Player.class).getUser(player);
			    String nickname = essentials.getUser(player).getNickname();
				// if they have a nickname, remove any ~, and if they are not staff add back a ~, followed by setting their nickname
			    if(nickname != null) {
			    	nickname = nickname.replaceAll("~", "");
			    	if(!isStaff)	nickname = "~" + nickname;
			    	essentials.getUser(player).setNickname(nickname);
			    	
					// adding the nickname meta to the luckperms DB, ensuring it gets overridden
				    MetaNode node = MetaNode.builder("nickname", nickname).build();
				    user.data().clear(NodeType.META.predicate(mn -> mn.getMetaKey().equals("nickname")));
				    user.data().add(node);
			    }
			    else {
				    // clear the nickname meta 
				    user.data().clear(NodeType.META.predicate(mn -> mn.getMetaKey().equals("nickname")));
			    }
			    // save!
			    luckPerms.getUserManager().saveUser(user);
			}
		}.runTaskLaterAsynchronously(plugin, 3);
	}
	
	// event listener for the nickname command
	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent e) {
		// nick fix
		String[] args = e.getMessage().split(" ");	
		// if the player runs the nickname command and has the permission for it, run setNickname with their new nickname
		if(args[0].toLowerCase().equals("/nick") && e.getPlayer().hasPermission("essentials.nick")) {
			// if they also have the nickname others permission, set the nickname for the other user by checking if they are online
			// and running the set nickname for that other player
			if(e.getPlayer().hasPermission("essentials.nick.others") && args.length > 2) {
				if(Bukkit.getPlayer(args[1]) != null)	setNickname(luckPerms, essentials, Bukkit.getPlayer(args[1]), false);
				else {
					e.getPlayer().sendMessage("This command doesn't work on offline players!");
					e.setCancelled(true);
					return;
				}
			}
			setNickname(luckPerms, essentials, e.getPlayer(), false);
		}
	}
}
