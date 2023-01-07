package site.hjfunny.bungeechatx;

import net.md_5.bungee.api.plugin.Plugin;
import org.bstats.bungeecord.Metrics;

public final class Main extends Plugin {
    @Override
    public void onEnable() {
        getLogger().info("§c ____                               ____ _           _  __  __§r");
        getLogger().info("§c| __ ) _   _ _ __   __ _  ___  ___ / ___| |__   __ _| |_\\ \\/ /§r");
        getLogger().info("§c|  _ \\| | | | '_ \\ / _` |/ _ \\/ _ \\ |   | '_ \\ / _` | __|\\  / §r");
        getLogger().info("§c| |_) | |_| | | | | (_| |  __/  __/ |___| | | | (_| | |_ /  \\ §r");
        getLogger().info("§c|____/ \\__,_|_| |_|\\__, |\\___|\\___|\\____|_| |_|\\__,_|\\__/_/\\_\\§r");
        getLogger().info("§c                   |___/                                      §r");
        getLogger().info("Plugin by §bbcmray & CodeZhangBorui§r, version §b1.5.1§r, §lLoading...§r");
        // 注册监听器
        getProxy().getPluginManager().registerListener(this, new PostLoginEvent());
        getProxy().getPluginManager().registerListener(this, new ChatEvent(this));

        // 注册指令
        getProxy().getPluginManager().registerCommand(this,new MsgCommand(this));

        // 注册 bStats
        int pluginId = 17333; // <-- Replace with the id of your plugin!
        Metrics metrics = new Metrics(this, pluginId);

        getLogger().info("§c§l[BungeeChatX]§r Enabled!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("§c§l[BungeeChatX]§r Disabled!");
    }
}
