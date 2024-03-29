package site.hjfunny.bungeechatx;

// import java.util.Date;
import java.util.List;

import net.md_5.bungee.api.ChatColor;
// import net.md_5.bungee.api.chat.ComponentBuilder;
// import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

public class ChatEvent implements Listener {
    private final Plugin plugin;

    public ChatEvent(Plugin plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onChat(net.md_5.bungee.api.event.ChatEvent event){
        if(event.isCommand()||event.isCancelled()||event.isProxyCommand()) return;

        ProxiedPlayer player = PlayerDataProcesser.PlayerSocketMap.get(event.getSender().getSocketAddress());

        String ProcessedMessage = event.getMessage();
        if(ConfigurationProcesser.PluginConfig.getBoolean("features.bannedWords")) {
            List<String> bannedWords = ConfigurationProcesser.PluginConfig.getStringList("bannedWords.wordList");
            String sendedMessage = event.getMessage().toLowerCase();
            for (String word : bannedWords) {
                if (sendedMessage.contains(word.toLowerCase())) {
                    plugin.getLogger().info("§c§l[Attention] " + player.getName() + " sended a message that contains banned word! Raw message: " + event.getMessage());
                    player.sendMessage(new TextComponent(MessagesProcesser.PluginMessages.getString("sendBannedWords").replace("%0%", word)));
                    event.setCancelled(true);
                    return;
                }
            }
        }
        
        String displayServer;
        if(player.getServer() == null) {
            displayServer = "[?] ";
            plugin.getLogger().info("Error while processing the server information of player '" + player.getName() + "'");
        } else {
            displayServer = "[" + player.getServer().getInfo().getName() + "] ";
        }
        String displayName = "<" + player.getName() + "> ";
        String playerPrefix = PlayerDataProcesser.getPlayerPrefix(player.getName());

        TextComponent messageSrv = new TextComponent(displayServer);
        messageSrv.setColor(ChatColor.AQUA);
        messageSrv.setBold(true);
        if(playerPrefix != null) {
            TextComponent playerPrefixTC = new TextComponent("§r" + playerPrefix + " §r");
            messageSrv.addExtra(playerPrefixTC);

        }
        TextComponent messagePlayer = new TextComponent(displayName);
        messagePlayer.setColor(ChatColor.WHITE);
        messagePlayer.setBold(false);
        // messagePlayer.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ComponentBuilder("TIME: "+ new Date()).create()));
        TextComponent messageMain = new TextComponent(ProcessedMessage);
        messageMain.setColor(ChatColor.WHITE);
        messageMain.setBold(false);
        messageSrv.addExtra(messagePlayer);
        messageSrv.addExtra(messageMain);

        for(ProxiedPlayer recPlayer:plugin.getProxy().getPlayers()){
            if(recPlayer.getServer() == null) {
                plugin.getLogger().warning("Error while processing the server information of player '" + recPlayer.getName() + "', ignore it.");
            } else if(recPlayer.getServer().getInfo().getName() != player.getServer().getInfo().getName() && PlayerDataProcesser.PlayerReceiveSettings.get(recPlayer.getName()) == true){
                recPlayer.sendMessage(messageSrv);
            }
        }

        if(playerPrefix != null) {
            plugin.getLogger().info("§b§l" + displayServer + "§r" + playerPrefix + " §r" + displayName + " " + event.getMessage());
        } else {
            plugin.getLogger().info("§b§l" + displayServer + "§r" + displayName + " " + event.getMessage());
        }
        
    }
}
