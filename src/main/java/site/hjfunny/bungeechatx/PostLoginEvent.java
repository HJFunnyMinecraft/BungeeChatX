package site.hjfunny.bungeechatx;

import net.md_5.bungee.api.connection.ProxiedPlayer;
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
        if(!PlayerDataProcesser.JoinedPlayers.contains(event.getPlayer())) {
            PlayerDataProcesser.JoinedPlayers.add(event.getPlayer());
            if(ConfigurationProcesser.PluginConfig.getBoolean("features.playerJoinMessage")) {
                String message = ConfigurationProcesser.PluginConfig.getString("messages.playerJoinMessage").replaceAll("%0%", event.getPlayer().getName());
                plugin.getLogger().info(message);
                for(ProxiedPlayer recPlayer:plugin.getProxy().getPlayers()){
                    recPlayer.sendMessage(message);
                }
            }
        }
        PlayerDataProcesser.PlayerSocketMap.put(event.getPlayer().getSocketAddress(), event.getPlayer());
        PlayerDataProcesser.PlayerReceiveSettings.put(event.getPlayer().getName(), true);
    }

    @EventHandler
    public void onLeave(PlayerDisconnectEvent event){
        PlayerDataProcesser.JoinedPlayers.remove(event.getPlayer());
        if(ConfigurationProcesser.PluginConfig.getBoolean("features.playerJoinMessage")) {
            String message = ConfigurationProcesser.PluginConfig.getString("messages.playerLeaveMessage").replaceAll("%0%", event.getPlayer().getName());
                    plugin.getLogger().info(message);
                    for(ProxiedPlayer recPlayer:plugin.getProxy().getPlayers()){
                        recPlayer.sendMessage(message);
                    }
        }
        PlayerDataProcesser.PlayerSocketMap.remove(event.getPlayer().getSocketAddress());
        PlayerDataProcesser.PlayerReceiveSettings.remove(event.getPlayer().getName());
    }
}
