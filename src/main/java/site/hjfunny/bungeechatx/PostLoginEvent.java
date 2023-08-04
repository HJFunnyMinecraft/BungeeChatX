package site.hjfunny.bungeechatx;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

public class PostLoginEvent implements Listener {
    private final Plugin plugin;
    
    public PostLoginEvent(Plugin plugin){
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onPostLogin(net.md_5.bungee.api.event.PostLoginEvent event){
        // Post Login Event Logic
        if(!PlayerDataProcesser.JoinedPlayers.contains(event.getPlayer())) {
            // This player joins the Bungeecord, not switch the server
            PlayerDataProcesser.JoinedPlayers.add(event.getPlayer());
            if(ConfigurationProcesser.PluginConfig.getBoolean("features.playerJoinMessage")) {
                // Send player joining message
                String message = ConfigurationProcesser.PluginConfig.getString("messages.playerJoinMessage").replaceAll("%0%", event.getPlayer().getName());
                plugin.getLogger().info(message);
                ProxyServer.getInstance().broadcast(new TextComponent(message));
            }
            // Add default player personal settings to the memory
            PlayerDataProcesser.PlayerReceiveSettings.put(event.getPlayer().getName(), true);
        }
        PlayerDataProcesser.PlayerSocketMap.put(event.getPlayer().getSocketAddress(), event.getPlayer());
    }

    @EventHandler
    public void onLeave(PlayerDisconnectEvent event){
        // Leave Event Logic
        PlayerDataProcesser.JoinedPlayers.remove(event.getPlayer());
        if(ConfigurationProcesser.PluginConfig.getBoolean("features.playerJoinMessage")) {
            // Send player leaving message
            String message = ConfigurationProcesser.PluginConfig.getString("messages.playerLeaveMessage").replaceAll("%0%", event.getPlayer().getName());
            plugin.getLogger().info(message);
            ProxyServer.getInstance().broadcast(new TextComponent(message));
        }
        PlayerDataProcesser.PlayerSocketMap.remove(event.getPlayer().getSocketAddress());
        // Remove player personal settings from the memory
        PlayerDataProcesser.PlayerReceiveSettings.remove(event.getPlayer().getName());
    }
}
