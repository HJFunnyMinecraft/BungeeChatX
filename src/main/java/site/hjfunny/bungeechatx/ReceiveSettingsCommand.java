package site.hjfunny.bungeechatx;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.TabExecutor;

public class ReceiveSettingsCommand extends Command implements TabExecutor {
    private final Plugin plugin;

    // Command Initial from LuckPerms Start
    private static final String NAME = "bcr";
    private static final String[] ALIASES = {"r"};

    private static final String[] SLASH_ALIASES = Stream.concat(
            Stream.of(NAME),
            Arrays.stream(ALIASES)
    ).map(s -> '/' + s).toArray(String[]::new);

    private static final String[] ALIASES_TO_REGISTER = Stream.concat(
            Arrays.stream(ALIASES),
            Arrays.stream(SLASH_ALIASES)
    ).toArray(String[]::new);

    public ReceiveSettingsCommand(Plugin plugin){
        super(NAME, null, ALIASES_TO_REGISTER);
        this.plugin = plugin;
    }
    // Command Initial End

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length != 1) {
            // Wrong command syntax
            sender.sendMessage(new TextComponent(ConfigurationProcesser.PluginConfig.getString("messages.wrongCommand")));
            return;
        }
        if(args[0].equals("on")) {
            // Enable cross-server messages communication
            PlayerDataProcesser.PlayerReceiveSettings.replace(sender.getName(), true);
            sender.sendMessage(new TextComponent(ConfigurationProcesser.PluginConfig.getString("messages.playerRSOn")));
        } else if(args[0].equals("off")) {
            // Disable cross-server messages communication
            PlayerDataProcesser.PlayerReceiveSettings.replace(sender.getName(), false);
            sender.sendMessage(new TextComponent(ConfigurationProcesser.PluginConfig.getString("messages.playerRSOff")));
        } else {
            // Wrong command syntax
            sender.sendMessage(new TextComponent(ConfigurationProcesser.PluginConfig.getString("messages.wrongCommand")));
        }
    }

    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        List<String> subCommands = new ArrayList<String>();
        if (args.length == 1) {
            subCommands.add("on");
            subCommands.add("off");
        }
        return subCommands;
    }
}
