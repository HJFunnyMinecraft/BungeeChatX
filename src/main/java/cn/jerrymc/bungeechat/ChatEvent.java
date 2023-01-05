package cn.jerrymc.bungeechat;

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

        String displayServer = "["+player.getServer().getInfo().getName()+"]";
        String displayName = "<" + player.getName() + ">";

        TextComponent messageSrv = new TextComponent(displayServer);
        messageSrv.setColor(ChatColor.AQUA);
        messageSrv.setBold(true);
        TextComponent messagePlayer = new TextComponent(displayName);
        messagePlayer.setColor(ChatColor.YELLOW);
        messagePlayer.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ComponentBuilder("发送时间: "+ new Date()).create()));
        TextComponent messageSpace = new TextComponent(" ");
        TextComponent messageMain = new TextComponent(event.getMessage());
        // messageMain.setColor(ChatColor.GRAY);
        messageSrv.addExtra(messageSpace);
        messageSrv.addExtra(messagePlayer);
        messageSrv.addExtra(messageSpace);
        messageSrv.addExtra(messageMain);

        for(ProxiedPlayer recPlayer:plugin.getProxy().getPlayers()){
            if(recPlayer.getServer().getInfo().getName!=player.getServer().getInfo().getName()){
                recPlayer.sendMessage(messageSrv);
            }
        }

        plugin.getLogger().info("§c[BungeeChat]§r "+displayName+" "+event.getMessage());
    }
}
