package wtf.drav.sootfix.modules;

import java.util.Hashtable;
import java.util.UUID;

import me.block2block.hubparkour.api.HubParkourAPI;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import wtf.drav.sootfix.libraries.CustomGUI;

// parkour GUI 
public class ParkourGUI implements CommandExecutor, Listener {
	private Hashtable<UUID, CustomGUI> GUIList = new Hashtable<UUID, CustomGUI>();
	Plugin plugin;
	
	public ParkourGUI(Plugin plugin) {
		this.plugin = plugin;
	}
	
    // if player runs the command, create a new GUI and open it for the player
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    	if(sender instanceof Player) {
    		Player player = (Player)sender;
			GUIList.put(player.getUniqueId(), new CustomGUI(args[0],player));
			GUIList.get(player.getUniqueId()).setGUI(player, args[0], plugin);
    	}
    return true;
	}

    // event for player clicking on the inventory, if they click on an item that has Parkours in it, proceed
	@EventHandler
    public void onInventoryClick(final InventoryClickEvent e) {
    	if(!e.getView().getTitle().contains("PARKOURS")) return;
    	
        e.setCancelled(true);

        final ItemStack clickedItem = e.getCurrentItem();
        
        // verify current item is not null
        if (clickedItem == null || clickedItem.getType() == Material.AIR) return;
        
        final Player p = (Player) e.getWhoClicked();
        
        // if the player clicks on green or red stained glass, it means they want to go to specific parkour level, tp them to it
        if(clickedItem.getType() == Material.GREEN_STAINED_GLASS_PANE || clickedItem.getType() == Material.RED_STAINED_GLASS_PANE) {
            p.teleport(HubParkourAPI.getParkour(clickedItem.getItemMeta().getDisplayName()).getRestartPoint().getLocation().clone().add(.5,0,.5));   
        }
        // else if they click an arrow, open up the timedwars gui
        if(clickedItem.getType() == Material.ARROW)	{
        	p.closeInventory();
        	p.performCommand("gui open timedwarps");
        }
    }

    // cancel dragging in inventory
    @EventHandler
    public void onInventoryDrag(final InventoryDragEvent e) {
    	if(!e.getView().getTitle().contains("PARKOURS")) return;
        e.setCancelled(true);
    }
    
    // if player closes inventory, and they are in the normal world, clear their inventory
    @EventHandler
    public void onInventoryClose(final InventoryCloseEvent e) {
    	if(!e.getView().getTitle().contains("PARKOURS")) return;
    	if(e.getPlayer().getWorld().getName().equals("plotworld"))	return;
        for(int i = 0; i < 36; i++)	{
        	if(i == 1 || i == 7)	continue;
        	e.getPlayer().getInventory().clear(i);
        }
    }
    
    // if player tries to click item, cancel event so they cannot move items
    @EventHandler
    public void onItemClick(final PlayerInteractEvent e) {
    	if(!e.getPlayer().getOpenInventory().getTitle().contains("PARKOURS")) return;
        e.setCancelled(true);
    }
	
}
