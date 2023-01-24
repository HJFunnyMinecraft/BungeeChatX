package site.hjfunny.bungeechatx;

import java.util.List;

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
            if(!ProxiedPlayerList.JoinedPlayers.contains(event.getPlayer())) {
                ProxiedPlayerList.JoinedPlayers.add(event.getPlayer());
                if(ConfigurationProcesser.PluginConfig.getBoolean("features.playerJoinMessage")) {
                    String message = ConfigurationProcesser.PluginConfig.getString("messages.playerJoinMessage").replaceAll("%0%", event.getPlayer().getName());
                    plugin.getLogger().info(message);
                    for(ProxiedPlayer recPlayer:plugin.getProxy().getPlayers()){
                        recPlayer.sendMessage(message);
                    }
                }
            }
        PlayerAddressMapping.playerMap.put(event.getPlayer().getSocketAddress(), event.getPlayer());
    }

    @EventHandler
    public void onLeave(PlayerDisconnectEvent event){
        ProxiedPlayerList.JoinedPlayers.remove(event.getPlayer());
        if(ConfigurationProcesser.PluginConfig.getBoolean("features.playerJoinMessage")) {
            String message = ConfigurationProcesser.PluginConfig.getString("messages.playerLeaveMessage").replaceAll("%0%", event.getPlayer().getName());
                    plugin.getLogger().info(message);
                    for(ProxiedPlayer recPlayer:plugin.getProxy().getPlayers()){
                        recPlayer.sendMessage(message);
                    }
        }
        PlayerAddressMapping.playerMap.remove(event.getPlayer().getSocketAddress());
    }
}
