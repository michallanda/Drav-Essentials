package wtf.drav.dravessentials.libraries;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import me.block2block.hubparkour.api.HubParkourAPI;
import me.clip.placeholderapi.PlaceholderAPI;

// custom GUI instance
public class CustomGUI {
	private String type;
	private Inventory inv;
	private Player player;
	
	public CustomGUI(String type, Player player) {
		this.type = type;
		this.player = player;
	}
	
	// sets the GUI for a given player and opens it for them
	public void setGUI(Player ent, String type, Plugin plugin) {
		this.type = type;
		setEasyGUI(plugin);
		player.openInventory(inv);
	}
	
	// returns the players current inventory
	public String getCurrInv() {
		return type;
	}
	
	// clears the GUI and reinitializes it
	public void refreshGUI(Player ent, int type, Plugin plugin) {
		inv.clear();
		initializeSettingsInv(plugin);
	}
	
	// main settings GUI
    public void setEasyGUI(Plugin plugin) {
        inv = Bukkit.createInventory(null, 54, type.toUpperCase() + " PARKOURS");
        initializeSettingsInv(plugin);
    }

	// initializes the GUI by adding all custom items to it
    public void initializeSettingsInv(Plugin plugin) {
    	new BukkitRunnable() {
			public void run() {
				int currPos = 0;
				// gets all parkours, grabs their best times, record time, and the record holder through the Placeholders API
		        for(int i = 0; i < 100; i++) {
	        		Material mat = Material.GREEN_STAINED_GLASS_PANE;
	        		String bestTime = "%hubparkour_besttime_" + i + "%";
	        		String recordTime = "%hubparkour_recordtime_" + i + "%";
	        		String recordHolder = "%hubparkour_recordholder_" + i + "%";
	        		bestTime = PlaceholderAPI.setPlaceholders(player, bestTime);
	        		recordTime = PlaceholderAPI.setPlaceholders(player, recordTime);
	        		recordHolder = PlaceholderAPI.setPlaceholders(player, recordHolder);
	        		
					// checks if the player has a valid attempt on the parkour, if they do not, set the stained-glass to red, else
					// set it to green with the players best time, and the current record holder as the lore of the item
					try {
	        			Double.parseDouble(bestTime);
	        		}
	        		catch(NumberFormatException e){
	        			if(bestTime.contains("valid")) {
	        				continue;
	        			}
	        			else {
	        				mat = Material.RED_STAINED_GLASS_PANE;
	        			}
	        		}
	        		if(!HubParkourAPI.getParkour(i).getStart().getLocation().getWorld().getName().equals("world"+type))	continue;
	        		String name = "%hubparkour_parkourname_" + i + "%";
	        		name = PlaceholderAPI.setPlaceholders(player, name);
	                inv.setItem(currPos,createGuiItem(mat, ChatColor.RESET + name, ChatColor.RESET + "" + ChatColor.GRAY + "TIME : " + bestTime + ">" 
	                + ChatColor.DARK_GRAY + "-------" + ">"
	        		+ ChatColor.GREEN + "1st: " + recordHolder + " (" + recordTime + ")"));
	                currPos++;
		        }
				// adds an arror for player to go back a GUI page
		        inv.setItem(53, createGuiItem(Material.ARROW, ChatColor.RESET + "Return", null));

				// sets rest of the inventory to grey glass panes
		        for(int i = 0; i < 53; i++) {
		        	if(inv.getItem(i) == null) {
			        	inv.setItem(i, createGuiItem(Material.GRAY_STAINED_GLASS_PANE, ChatColor.RESET + "", null));
		        	}
		        }
			}
    	}.runTaskAsynchronously(plugin);
    }
	
	// creates a GUI item by adding lore to it
    protected ItemStack createGuiItem(final Material material, final String name, final String lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();
        
        // Set the name of the item
        meta.setDisplayName(name);
        if(lore != null)	meta.setLore(Arrays.asList(lore.split(">")));
        item.setItemMeta(meta);
        // Set the lore of the item
        return item;
    }
	
}
