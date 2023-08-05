package site.hjfunny.bungeechatx;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.TabExecutor;

public class MsgCommand extends Command implements TabExecutor {
    private final Plugin plugin;

    // Command Initial from LuckPerms Start
    private static final String NAME = "bcmsg";
    private static final String[] ALIASES = {"bctell", "msgs", "tells"};

    private static final String[] SLASH_ALIASES = Stream.concat(
            Stream.of(NAME),
            Arrays.stream(ALIASES)
    ).map(s -> '/' + s).toArray(String[]::new);

    private static final String[] ALIASES_TO_REGISTER = Stream.concat(
            Arrays.stream(ALIASES),
            Arrays.stream(SLASH_ALIASES)
    ).toArray(String[]::new);

    public MsgCommand(Plugin plugin){
        super(NAME, null, ALIASES_TO_REGISTER);
        this.plugin = plugin;
    }
    // Command Initial End

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length < 2) {
            // Wrong command syntax
            sender.sendMessage(new TextComponent(ConfigurationProcesser.PluginConfig.getString("messages.wrongCommand")));
            return;
        }

        // Build display prefix like "[Steve -> Alex] "
        String displayPrefix = "[" + sender.getName() + "§l -> §r" + args[0] + "] ";

        // Build messages
        StringBuilder displayMsg = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
            displayMsg.append(args[i]).append(" ");
        }

        TextComponent sendPrefix = new TextComponent(displayPrefix);
        sendPrefix.setColor(ChatColor.WHITE);
        sendPrefix.setBold(false);
        TextComponent messageMain = new TextComponent(displayMsg.toString());
        messageMain.setColor(ChatColor.WHITE);
        messageMain.setBold(false);

        sendPrefix.addExtra(messageMain);

        if(ConfigurationProcesser.PluginConfig.getBoolean("features.bannedWords")) {
            // Scan for banned words
            List<String> bannedWords = ConfigurationProcesser.PluginConfig.getStringList("bannedWords.wordList"); // Get words list from configuration processer
            String sendedMessage = displayMsg.toString().toLowerCase();
            for (String word : bannedWords) {
                if (sendedMessage.contains(word.toLowerCase())) {
                    plugin.getLogger().info("§c§l" + "[Banned Words Detected] " + "§r" + displayPrefix + displayMsg.toString());
                    sender.sendMessage(new TextComponent(ConfigurationProcesser.PluginConfig.getString("messages.sendBannedWords").replaceAll("%0%", word) + " (/bcmsg <player> <message>)"));
                    return; // Refuse to execute
                }
            }
        }
        
        ProxiedPlayer tplayer = ProxyServer.getInstance().getPlayer(args[0]); // Get target player
        if (tplayer != null) {
            // Target player found
            tplayer.sendMessage(sendPrefix);
            if(tplayer != sender) { // Send once if you send message to yourself
                sender.sendMessage(sendPrefix);
            }
            plugin.getLogger().info(displayPrefix + displayMsg.toString());
        } else {
            // Target player not found
            sender.sendMessage(new TextComponent(ConfigurationProcesser.PluginConfig.getString("messages.playerNotFound").replaceAll("%0%", args[0])));
        }
    }

    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        List<String> subCommands = new ArrayList<String>();
        if (args.length == 1) {
            if(args[0].length() == 0) {
                for(ProxiedPlayer recPlayer:plugin.getProxy().getPlayers()) {
                    subCommands.add(recPlayer.getName());
                }
            } else {
                for(ProxiedPlayer recPlayer:plugin.getProxy().getPlayers()) {
                    if(recPlayer.getName().length() < args[0].length()) continue;
                    if(recPlayer.getName().substring(0, 0 + args[0].length()).equalsIgnoreCase(args[0])) {
                        subCommands.add(recPlayer.getName());
                    }
                }
            }
        }
        return subCommands;
    }
}
