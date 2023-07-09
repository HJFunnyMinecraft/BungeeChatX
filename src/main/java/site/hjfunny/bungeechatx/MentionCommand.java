package site.hjfunny.bungeechatx;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.protocol.packet.Chat;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class MentionCommand extends Command implements TabExecutor {
    private final Plugin plugin;

    // Command Initial from LuckPerms Start
    private static final String NAME = "mention";
    private static final String[] ALIASES = {"men", "at"};

    private static final String[] SLASH_ALIASES = Stream.concat(
            Stream.of(NAME),
            Arrays.stream(ALIASES)
    ).map(s -> '/' + s).toArray(String[]::new);

    private static final String[] ALIASES_TO_REGISTER = Stream.concat(
            Arrays.stream(ALIASES),
            Arrays.stream(SLASH_ALIASES)
    ).toArray(String[]::new);

    public MentionCommand(Plugin plugin){
        super(NAME, null, ALIASES_TO_REGISTER);
        this.plugin = plugin;
    }
    // Command Initial End

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length < 1) {
            sender.sendMessage("§c§l错误：§r参数过少！");
            return;
        }

        String displayPrefix = "<" + sender.getName() + "> ";
        String mentionPrefix = "@" + args[0] + " ";

        StringBuilder displayMsg = new StringBuilder();
        if(args.length > 1) {
            for (int i = 1; i < args.length; i++) {
                displayMsg.append(args[i]).append(" ");
            }
        }

        TextComponent senderPrefix = new TextComponent(displayPrefix);
        senderPrefix.setColor(ChatColor.WHITE);
        senderPrefix.setBold(false);
        TextComponent mentionedPrefix = new TextComponent(mentionPrefix);
        mentionedPrefix.setColor(ChatColor.LIGHT_PURPLE);
        mentionedPrefix.setBold(true);
        TextComponent otherPrefix = new TextComponent(mentionPrefix);
        otherPrefix.setColor(ChatColor.GRAY);
        otherPrefix.setBold(false);
        TextComponent messageMain = new TextComponent(displayMsg.toString());
        messageMain.setColor(ChatColor.WHITE);
        messageMain.setBold(false);

        TextComponent mentionedMes = new TextComponent("");
        mentionedMes.addExtra(senderPrefix);
        mentionedMes.addExtra(mentionedPrefix);
        mentionedMes.addExtra(messageMain);
        TextComponent otherMes = new TextComponent("");
        otherMes.addExtra(senderPrefix);
        otherMes.addExtra(otherPrefix);
        otherMes.addExtra(messageMain);
        
        if(ConfigurationProcesser.PluginConfig.getBoolean("features.bannedWords")) {
            List<String> bannedWords = ConfigurationProcesser.PluginConfig.getStringList("bannedWords.wordList");
            String sendedMessage = displayMsg.toString();
            for (String word : bannedWords) {
                if (sendedMessage.contains(word)) {
                    plugin.getLogger().info("§c§l" + "[Banned Words Detected] " + "§r" + displayPrefix + mentionPrefix + displayMsg.toString());
                    sender.sendMessage(ConfigurationProcesser.PluginConfig.getString("messages.sendBannedWords").replaceAll("%0%", word));
                    return;
                }
            }
        }

        if (ProxyServer.getInstance().getPlayer(args[0]) != null) {
            for(ProxiedPlayer recPlayer:plugin.getProxy().getPlayers()){
                if(recPlayer.getName().equals(args[0])) {
                    recPlayer.sendMessage(mentionedMes);
                } else {
                    recPlayer.sendMessage(otherMes);
                }
            }
            plugin.getLogger().info(displayPrefix + mentionPrefix + displayMsg.toString());
        } else {
            sender.sendMessage("§c§l错误：§r玩家 §l" + args[0] + "§r 不存在！");
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
