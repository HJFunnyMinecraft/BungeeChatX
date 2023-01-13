package site.hjfunny.bungeechatx;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.protocol.packet.Chat;
import org.w3c.dom.Text;

import java.util.Date;

public class ChatEvent implements Listener {
    private final Plugin plugin;

    public ChatEvent(Plugin plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onChat(net.md_5.bungee.api.event.ChatEvent event){
        if(event.isCommand()||event.isCancelled()||event.isProxyCommand()) return;

        ProxiedPlayer player = PlayerAddressMapping.playerMap.get(event.getSender().getSocketAddress());
        String displayServer;
        if(player.getServer() == null) {
            displayServer = "[?] ";
            plugin.getLogger().info("Error while processing the server information of player '" + player.getName() + "'");
        } else {
            displayServer = "[" + player.getServer().getInfo().getName() + "] ";
        }
        String displayName = "<" + player.getName() + "> ";

        TextComponent messageSrv = new TextComponent(displayServer);
        messageSrv.setColor(ChatColor.AQUA);
        messageSrv.setBold(true);
        TextComponent messagePlayer = new TextComponent(displayName);
        messagePlayer.setColor(ChatColor.WHITE);
        messagePlayer.setBold(false);
        messagePlayer.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ComponentBuilder("TIME: "+ new Date()).create()));
        TextComponent messageSpace = new TextComponent(" ");
        TextComponent messageMain = new TextComponent(event.getMessage());
        messageMain.setColor(ChatColor.WHITE);
        messageMain.setBold(false);
        messageSrv.addExtra(messagePlayer);
        messageSrv.addExtra(messageMain);

        for(ProxiedPlayer recPlayer:plugin.getProxy().getPlayers()){
            if(recPlayer.getServer() == null) {
                plugin.getLogger().info("Error while processing the server information of player '" + recPlayer.getName() + "', ignore it.");
            } else if(recPlayer.getServer() != player.getServer()){
                recPlayer.sendMessage(messageSrv);
            }
        }

        plugin.getLogger().info("§b§l" + displayServer + "§r " + displayName + " " + event.getMessage());
    }
}
