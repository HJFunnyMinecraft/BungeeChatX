package site.hjfunny.bungeechatx;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;

public class AnnounceCommand extends Command {
    private final Plugin plugin;

    public AnnounceCommand(Plugin plugin){
        super("announce");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        StringBuilder msg = new StringBuilder();

        for (String v:args){
            msg.append(v).append(" ");
        }

        TextComponent c1 = new TextComponent("[通知] ");
        c1.setColor(ChatColor.RED);
        c1.setBold(true);
        TextComponent c2 = new TextComponent(msg.toString());
        c2.setColor(ChatColor.WHITE);
        c2.setBold(false);
        c1.addExtra(c2);

        for (ProxiedPlayer player:plugin.getProxy().getPlayers()) {
            player.sendMessage(c1);
        }

        plugin.getLogger().info(sender.getName()+" 发送了一条通知: " + msg);

    }
}
