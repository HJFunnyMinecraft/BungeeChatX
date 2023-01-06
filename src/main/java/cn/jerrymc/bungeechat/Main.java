package cn.jerrymc.bungeechat;

import net.md_5.bungee.api.plugin.Plugin;

public final class Main extends Plugin {
    @Override
    public void onEnable() {
        getLogger().info("§c ____                               ____ _           _   ");
        getLogger().info("§c| __ ) _   _ _ __   __ _  ___  ___ / ___| |__   __ _| |_ ");
        getLogger().info("§c|  _ \| | | | '_ \ / _` |/ _ \/ _ \ |   | '_ \ / _` | __|");
        getLogger().info("§c| |_) | |_| | | | | (_| |  __/  __/ |___| | | | (_| | |_ ");
        getLogger().info("§c|____/ \__,_|_| |_|\__, |\___|\___|\____|_| |_|\__,_|\__|");
        getLogger().info("§c                   |___/                                 ");
        getLogger().info("Plugin by §bbcmray & CodeZhangBorui§r, version §b1.3§r, Loading...");
        // 注册监听器
        getProxy().getPluginManager().registerListener(this, new PostLoginEvent());
        getProxy().getPluginManager().registerListener(this, new ChatEvent(this));

        // 注册指令
//        getProxy().getPluginManager().registerCommand(this,new AnnounceCommand(this));
        getLogger().info("§c[BungeeChat]§r 插件已加载!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("§c[BungeeChat]§r 插件已卸载!");
    }
}
