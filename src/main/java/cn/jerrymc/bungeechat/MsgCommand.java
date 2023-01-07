package cn.jerrymc.bungeechat;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;

public class MsgCommand extends Command {
    private final Plugin plugin;

    public MsgCommand(Plugin plugin){
        super("msg");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
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

        boolean successful = false;

        for(ProxiedPlayer recPlayer:plugin.getProxy().getPlayers()) {
            if(recPlayer.getName() == args[0]) {
                recPlayer.sendMessage(sendPrefix);
                successful = true;
            }
        }

        if(successful == true) {
            sender.sendMessage(sendPrefix);
            plugin.getLogger().info(displayPrefix + displayMsg.toString());
        } else {
            sender.sendMessage("§c§l错误：§r玩家§l" + args[0] + "§r不存在！");
        }
    }
}
