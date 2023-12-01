package wtf.drav.dravessentials.modules;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.Plugin;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;

// tpa suggest message
public class TpaSuggestMessage implements Listener {
	Plugin plugin;
	
	public TpaSuggestMessage(Plugin plugin) {
		this.plugin = plugin;
	}
	
	// event handler for when player runs /tpa
	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent e) {
		// check if player is running /tpa or /tpahere
		if(e.getMessage().toLowerCase().contains("/tpa ") || e.getMessage().toLowerCase().contains("/tpahere ")) {
			// if the player they inputted exists, create a custom message with clickable text to accept or deny the tpa request
			if(Bukkit.getPlayer(e.getMessage().split(" ")[1]) != null) {
				Bukkit.getServer().getScheduler().runTaskLater(plugin, new Runnable() {
		            public void run() {
						TextComponent messageAccept = new TextComponent( "Accept Teleport" );
						messageAccept.setColor(ChatColor.GREEN);
						messageAccept.setClickEvent(new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/tpaccept"));
						messageAccept.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new Text("Accept")));
						
						TextComponent messageDeny = new TextComponent( "Deny Teleport" );
						messageDeny.setColor(ChatColor.RED);
						messageDeny.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/tpdeny"));
						messageDeny.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new Text("Deny")));
						
						TextComponent message = new TextComponent("\u00A76Or you may click to ");
						message.addExtra(messageAccept);
						message.addExtra("\u00A76 or ");
						message.addExtra(messageDeny);
						Bukkit.getPlayer(e.getMessage().split(" ")[1]).spigot().sendMessage(message);
		            }
		        }, 2);
			}
		}
	}
}
