package cn.jerrymc.bungeechat;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.ProxyServer;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class MsgCommand extends Command implements TabExecutor {
    private final Plugin plugin;

    // Command Initial from LuckPerms Start
    private static final String NAME = "msgs";
    private static final String[] ALIASES = {"msgserver", "msg", "tell"};

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
            sender.sendMessage("§c§l错误：§r参数过少！");
            return;
        }

        plugin.getLogger().info("[Debug] SenderName={" + sender.getName() + "}");
        plugin.getLogger().info("[Debug] Args0={" + args[0] + "}");

        String displayPrefix = "[" + sender.getName() + " -> " + args[0] + "] ";

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
        
        ProxiedPlayer tplayer = ProxyServer.getInstance().getPlayer(args[0]);
        if (tplayer != null) {
            tplayer.sendMessage(sendPrefix);
            sender.sendMessage(sendPrefix);
            plugin.getLogger().info(displayPrefix + displayMsg.toString());
        } else {
            sender.sendMessage("§c§l错误：§r玩家 §l" + args[0] + "§r 不存在！");
        }
    }

    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 1) {
            List<String> subCommands = new ArrayList<String>();
            for(ProxiedPlayer recPlayer:plugin.getProxy().getPlayers()) {
                subCommands.add(recPlayer.getName());
            }
            return subCommands;
        }
        return null;
    }
}
