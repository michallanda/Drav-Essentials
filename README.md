<img align="right" width="250" src="./img/mainicon.png">

# Soot Essentials for SootMC

A Minecraft Spigot Plugin that adds QOL changes for all servers found on the SootMC Network.

---

## Addons

### Nicknames

Nicknames is an addon that works on top of of the Spigot Plugin [Essentials](https://essentialsx.net). Essentials adds the option for players to have Nicknames in game, however this is all limited to the scope of a single Minecraft server. When running a network of servers, there is no option for players to have the same nickname across the network, which this addon fixes. The addon keeps track of any changes a player makes to their nickname, and pushes those changes to all other servers across the network.

### Last Timer

Last Timer is a configurable addon that adds the option for staff to use a ```/lasttimer``` command to keep reminders on things that may be needed to be checked. One example is checking players mines for the use of xray.

The command is used by running ```/lasttimer```, and will send a message to the staff who ran the command with the last time the timer was reset. Using ```/lasttimer set``` sets the timer to the current time.

### Scan Mining

Scan Mining is a simple addon that, when the ```/scanmining``` command is run, all users that are below a certain y-level at the time the command is executed, will be listed to the staff member using the command. This is useful when a staff member wants to actively look at players mining to see if any are using xray.

### Show Diamonds

Show Diamonds is an addon that when the command ```/showdiamonds``` is run, all previously mined diamonds are highlighted with an X to be able to easily visualize where diamonds previously were. This is useful for when a staff member is checking a players mining paths for suspition of using xray.

This addon takes advantage of API calls to the very popular Spigot plugin [CoreProtect](https://www.coreprotect.net) to be able to see where diamonds previously were and what player mined them.

### Balance Reset

Balance Reset is an addon that gives server moderators the power to reset the money balance of any player on the server. This addon works on top of the plugin [Essentials](https://essentialsx.net), as the plugin does not have a way to undo or log balance wipes, meaning accidentally wiping someones balance is very difficult to reverse, and balance wipes cannot be tracked.

This addon adds the command ```/balancereset [player]```, which logs the balance of the user before the wipe, as well as tracks which staff member ran the command.

### Top Dragon Hits

Top Dragon Hits is a module that keeps track of the amount of hits each player does to the Ender Dragon. This allows for a competition to be held for whoever hits the Ender Dragon the most before its death.

The module is continuously keeping track of dragon hits, and the list is accessable from the command ```/topdragonhits```.

### On Join Bedrock Kick

On Join Bedrock Kick is an addon for Minecraft servers running GeyserMC or any fork of it that allows for crossplatform connectivity with Minecraft Bedrock players. This addon can be activated on a single server and will disallow any Bedrock user from joining, making that individual server Java Minecraft only.

### On Player Death Help

On Player Death Help is a plugin that, when a player dies in the game, will send the player information about their death, with the main information being the coordinates of the players death so they can get back to their items.

### Fly Fix

Fly fix is an addon that fixes some odd behaviors of the ```/fly``` command from the [Essentials](https://essentialsx.net) plugin.

The main fixes are that when a player joins a server, it will keep the players fly on if they were previously flying before leaving, and will keep flying on when a player switches gamemodes.

### Nether Portal Safe Check

Nether Portal Safe Check is an addon that, when used with a world border in the game, makes sure the player does not set up a nether portal that would teleport them outside of the overworld world border, allowing for a larger nether map, without it having to be preportional to the overworld.

### TPA Suggest Message

TPA Suggest Message is an addon that sends a player suggests of wanting to accept or deny a TPA when another player sends them a request. This works inconjuction of the plugin [Essentials](https://essentialsx.net), as Essentials adds a TPA command that allows a player to request a teleport to another player. To accept or deny it with Essentials alone, the player needs to run ```/tpaccept``` or ```/tpadeny```, but this addon gives the option of just clicking a prompt on screen.

### Launchers

Launchers is an addon that makes spruce pressure plates launchers. These launchers are created by putting a sign a block under the pressure plate, and that pressure plate will launch the player in the direction they are looking when walking over the pressure plate.
