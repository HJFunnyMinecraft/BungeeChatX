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
            plugin.getLogger().info(ConfigurationProcesser.PluginConfig.get("messages.playerJoinMessage").toString().replaceAll("%0%", event.getPlayer().getName()));
        }
        PlayerAddressMapping.playerMap.put(event.getPlayer().getSocketAddress(), event.getPlayer());
    }

    @EventHandler
    public void onLeave(PlayerDisconnectEvent event){
        ProxiedPlayerList.JoinedPlayers.remove(event.getPlayer());
        plugin.getLogger().info(ConfigurationProcesser.PluginConfig.get("messages.playerLeaveMessage").toString().replaceAll("%0%", event.getPlayer().getName()));
        PlayerAddressMapping.playerMap.remove(event.getPlayer().getSocketAddress());
    }
}
