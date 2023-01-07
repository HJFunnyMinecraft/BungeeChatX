package site.hjfunny.bungeechatx;

import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PostLoginEvent implements Listener {
    @EventHandler
    public void onPostLogin(net.md_5.bungee.api.event.PostLoginEvent event){
        PlayerAddressMapping.playerMap.put(event.getPlayer().getSocketAddress(), event.getPlayer());
    }

    @EventHandler
    public void onLeave(PlayerDisconnectEvent event){
        PlayerAddressMapping.playerMap.remove(event.getPlayer().getSocketAddress());
    }
}
