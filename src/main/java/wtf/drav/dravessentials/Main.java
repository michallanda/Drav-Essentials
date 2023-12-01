package wtf.drav.dravessentials;


import com.earth2me.essentials.Essentials;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import wtf.drav.dravessentials.modules.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class Main extends JavaPlugin {
	LuckPerms luckPerms;
	Essentials essentials;
	FileConfiguration config;
	
	// On Enable
	@Override
	public void onEnable() {
		this.saveDefaultConfig();
		
		// gets config file and luckperms + essentials plugin instances
		config = this.getConfig();
		luckPerms = LuckPermsProvider.get();
		essentials = (Essentials) Bukkit.getPluginManager().getPlugin("Essentials");
		
		// check if nicknames module is enabled in config, if so, enable module 
		if(config.getBoolean("nicknames")) {
			Nicknames nicknames = new Nicknames(luckPerms, essentials, this);
			this.getCommand("anick").setExecutor(nicknames);
			Bukkit.getPluginManager().registerEvents(nicknames, this);
		}
		
		// all command modules, checks if they are enabled in config, add them to the command list if so
		if(config.getBoolean("lastTimerEnabled")) {
			setAlias("lasttimer", config.getStringList("lastTimerAliases"));
			this.getCommand("lasttimer").setExecutor(new LastTimer(config, this));
		}
		if(config.getBoolean("scanMining"))	this.getCommand("scanmining").setExecutor(new ScanMining());
		if(config.getBoolean("showDiamonds"))	this.getCommand("showdiamonds").setExecutor(new ShowDiamonds(this));
		if(config.getBoolean("balanceReset"))	this.getCommand("balancereset").setExecutor(new BalanceReset(this));
		if(config.getBoolean("topDragonHits"))	this.getCommand("topdragonhits").setExecutor(new TopDragonHits());
		if(config.getBoolean("pkOpenGUI"))	this.getCommand("pkopengui").setExecutor(new ParkourGUI(this));
		
		// all event modules, checks if a module is enabled in the config, if so, enables the event
		if(config.getBoolean("onJoinBKick"))	Bukkit.getPluginManager().registerEvents(new OnJoinBedrockKick(luckPerms), this);
		if(config.getBoolean("onPlayerDeathHelp"))	Bukkit.getPluginManager().registerEvents(new OnPlayerDeathHelp(), this);
		if(config.getBoolean("flyFix"))	Bukkit.getPluginManager().registerEvents(new FlyFix(this), this);
		if(config.getBoolean("netherPortalSafeCheck"))	Bukkit.getPluginManager().registerEvents(new NetherPortalLimiter(config), this);
		if(config.getBoolean("tpaSuggestMessage"))	Bukkit.getPluginManager().registerEvents(new TpaSuggestMessage(this), this);
		if(config.getBoolean("topDragonHits"))	Bukkit.getPluginManager().registerEvents(new TopDragonHits(), this);
		if(config.getBoolean("parkourEventsFix"))	Bukkit.getPluginManager().registerEvents(new ParkourEventsFix(), this);
		if(config.getBoolean("launchers"))	Bukkit.getPluginManager().registerEvents(new Launchers(), this);
		if(config.getBoolean("pkOpenGUI"))	Bukkit.getPluginManager().registerEvents(new ParkourGUI(this), this);
		
		getLogger().info("Drav Essentials has been enabled!");
	}
	
	// When Disabling
	@Override
	public void onDisable() {
		getLogger().info("DravEssentials has been disabled!");
	}
	
	// allows for the modification of commands like /settimer to have alias' like /settimerxray or /settimerpk for different servers
	public void setAlias(String name, List<String> aliases) {
		try {
		    final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
		    bukkitCommandMap.setAccessible(true);
		    CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
		    
            Constructor<PluginCommand> c = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
            c.setAccessible(true);
            
            PluginCommand command = c.newInstance(name, this);
            
            aliases.add(0,name);
            command.setAliases(aliases);
            command.setName(name);
            
            commandMap.register(this.getDescription().getName(), command);
            command.setExecutor(new LastTimer(config, this));
		    
		} catch (NoSuchFieldException | IllegalAccessException | InstantiationException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException e) {
		    e.printStackTrace();
		}
	}
}
