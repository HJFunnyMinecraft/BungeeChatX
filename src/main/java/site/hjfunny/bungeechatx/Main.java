package site.hjfunny.bungeechatx;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.bstats.bungeecord.Metrics;
import org.json.JSONObject;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;

public final class Main extends Plugin {

    public static String PluginVersion = "";
    public static String LatestVersion = "";
    public static String GithubVerApiUrl = "https://api.github.com/repos/HJFunnyMinecraft/BungeeChatX/releases/latest";

    public void chkUpdate() {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(GithubVerApiUrl);
        CloseableHttpResponse response = null;
        String result = null;
        int status = 0;
        try {
            response = httpClient.execute(httpGet);
            HttpEntity responseEntity = response.getEntity();
            status = response.getStatusLine().getStatusCode();
            result = EntityUtils.toString(responseEntity);

            if(status != 200) {
                getLogger().info("Http-Status: " + status);
                getLogger().info("§cError while checking the updates of the plugin");
                getLogger().info("§cCheck updates manually at §e§lhttps://github.com/HJFunnyMinecraft/BungeeChatX");
                return;
            }
        } catch(Exception e) {
            e.printStackTrace();
            getLogger().info("§cError while checking the updates of the plugin");
            getLogger().info("§cCheck updates manually at §e§lhttps://github.com/HJFunnyMinecraft/BungeeChatX");
            return;
        } finally {
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
                getLogger().info("§cError while checking the updates of the plugin");
                getLogger().info("§cCheck updates manually at §e§lhttps://github.com/HJFunnyMinecraft/BungeeChatX");
                return;
            }
        }
        try {
            LatestVersion = new JSONObject(result).getString("tag_name");
        } catch (Exception e) {
            e.printStackTrace();
            getLogger().info("§cError while checking the updates of the plugin");
            getLogger().info("§cCheck updates manually at §e§lhttps://github.com/HJFunnyMinecraft/BungeeChatX");
            return;
        }

        if(!PluginVersion.equals(LatestVersion)) {
            getLogger().info("§2§lNew Update Available!");
            getLogger().info("§eGet update at §lhttps://github.com/HJFunnyMinecraft/BungeeChatX/releases/tag/" + LatestVersion);
        } else {
            getLogger().info("§2§lThis is the latest version!");
        }
    }

    
    
    @Override
    public void onEnable() {
        PluginVersion = "v" + getDescription().getVersion().toString();
        getLogger().info("§c ____                               ____ _           _  __  __§r");
        getLogger().info("§c| __ ) _   _ _ __   __ _  ___  ___ / ___| |__   __ _| |_\\ \\/ /§r");
        getLogger().info("§c|  _ \\| | | | '_ \\ / _` |/ _ \\/ _ \\ |   | '_ \\ / _` | __|\\  / §r");
        getLogger().info("§c| |_) | |_| | | | | (_| |  __/  __/ |___| | | | (_| | |_ /  \\ §r");
        getLogger().info("§c|____/ \\__,_|_| |_|\\__, |\\___|\\___|\\____|_| |_|\\__,_|\\__/_/\\_\\§r");
        getLogger().info("§c                   |___/                                      §r");
        getLogger().info("Plugin by §bCodeZhangBorui & bcmray§r, §b" + PluginVersion + "§r, §lLoading...§r");
        
        // Load Config
        Boolean loadStatus = false;
        try {
            loadStatus = ConfigurationProcesser.LoadConfig(this);
        } catch (Exception e) {
            e.printStackTrace();
            getLogger().warning("Plugin config load failed, the plugin won't work properly!");
            ConfigurationProcesser.PluginConfig = new Configuration();
            return;
        }
        if(loadStatus == false) {
            getLogger().warning("Plugin config load failed, the plugin won't work properly!");
            ConfigurationProcesser.PluginConfig = new Configuration();
            return;
        }

        // Register Listener
        getProxy().getPluginManager().registerListener(this, new PostLoginEvent(this));
        getProxy().getPluginManager().registerListener(this, new ChatEvent(this));

        // Register Command
        getProxy().getPluginManager().registerCommand(this, new MsgCommand(this));
        getProxy().getPluginManager().registerCommand(this, new MentionCommand(this));
        getProxy().getPluginManager().registerCommand(this, new ReceiveSettingsCommand(this));
        getProxy().getPluginManager().registerCommand(this, new BcxCommand(this));

        // Register bStats
        Metrics metrics = new Metrics(this, 17333);

        // Check Updates
        chkUpdate();

        // Register LuckPerms Provider
        if(PlayerDataProcesser.initLuckpermsProvider() == false) {
            getLogger().warning("§c§l[BungeeChatX]§r The plugin cannot connect to Luckperms, disable the player prefix feature...");
        }

        getLogger().info("§c§l[BungeeChatX]§r Enabled!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("§c§l[BungeeChatX]§r Disabled!");
    }
}
