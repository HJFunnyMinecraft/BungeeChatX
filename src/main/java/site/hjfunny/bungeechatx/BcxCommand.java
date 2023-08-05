package site.hjfunny.bungeechatx;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.TabExecutor;
import net.md_5.bungee.config.Configuration;

public class BcxCommand extends Command implements TabExecutor {
    private final Plugin plugin;

    // Command Initial from LuckPerms Start
    private static final String NAME = "bcx";
    private static final String[] ALIASES = {};

    private static final String[] SLASH_ALIASES = Stream.concat(
            Stream.of(NAME),
            Arrays.stream(ALIASES)
    ).map(s -> '/' + s).toArray(String[]::new);

    private static final String[] ALIASES_TO_REGISTER = Stream.concat(
            Arrays.stream(ALIASES),
            Arrays.stream(SLASH_ALIASES)
    ).toArray(String[]::new);

    public BcxCommand(Plugin plugin){
        super(NAME, null, ALIASES_TO_REGISTER);
        this.plugin = plugin;
    }
    // Command Initial End

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(!sender.hasPermission("bcx.reload")) {
            // Just get the status
            sender.sendMessage(new TextComponent("§b§l[BungeeChatX]§r The server is running BungeeChatX Version " + plugin.getDescription().getVersion().toString()));
            return;
        }

        if(args.length == 0) {
            // Just get the status
            sender.sendMessage(new TextComponent("§b§l[BungeeChatX]§r The server is running BungeeChatX Version " + plugin.getDescription().getVersion().toString()));
            return;
        }
        if(args.length > 2) {
            // Wrong command syntax
            sender.sendMessage(new TextComponent(ConfigurationProcesser.PluginConfig.getString("messages.wrongCommand") + " (/bcx [status|reload])"));
        }
        if(args[0] == "status") {
            sender.sendMessage(new TextComponent("§b§l[BungeeChatX Status]§r"));
            sender.sendMessage(new TextComponent("§eThe Number of Proceedable Players: §r" + PlayerDataProcesser.JoinedPlayers.size()));
            sender.sendMessage(new TextComponent("§eLuckPerms Connecter: §r" + (PlayerDataProcesser.luckPermsStatus ? "Connected" : "Not Connect")));
        } else if(args[0] == "reload") {
            // Reload the config.yml
            Boolean reloadStatus = false;
            final Configuration configBackup = ConfigurationProcesser.PluginConfig;
            try {
                reloadStatus = ConfigurationProcesser.LoadConfig(plugin);
            } catch (IOException e) {
                e.printStackTrace();
                sender.sendMessage(new TextComponent("§e§lError: §rConfig file load failed, fallback to previous config."));
                ConfigurationProcesser.PluginConfig = configBackup;
                return;
            }
            if(reloadStatus == false) {
                sender.sendMessage(new TextComponent("§e§lError: §rConfig file load failed, fallback to previous config."));
                ConfigurationProcesser.PluginConfig = configBackup;
                return;
            }
            sender.sendMessage(new TextComponent("§aConfiguration reload successfully"));
        } else {
            // Wrong command syntax
            sender.sendMessage(new TextComponent(ConfigurationProcesser.PluginConfig.getString("messages.wrongCommand") + " (/bcx [status|reload])"));
        }
    }

    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        List<String> subCommands = new ArrayList<String>();
        if (args.length == 1) {
            subCommands.add("reload");
        }
        return subCommands;
    }
}
