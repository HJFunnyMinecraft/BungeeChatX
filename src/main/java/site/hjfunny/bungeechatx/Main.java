package site.hjfunny.bungeechatx;

import net.md_5.bungee.api.plugin.Plugin;
import org.bstats.bungeecord.Metrics;

import java.io.IOException;
import java.net.SocketException;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;

public final class Main extends Plugin {

    public String PluginVersion = "v1.5.2";
    public String LatestVersion = "";
    public String GithubVerApiUrl = "https://api.github.com/repos/HJFunnyMinecraft/BungeeChatX/releases/latest";

    public static void chkUpdate() {
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
                getLogger.info("Http-Status: " + status);
                getLogger.info("§cError while checking the updates of the plugin");
                getLogger.info("§cCheck updates manually at §e§lhttps://github.com/HJFunnyMinecraft/BungeeChatX");
                return;
            }
        } catch(SocketException e) {
            e.printStackTrace();
            getLogger.info("§cError while checking the updates of the plugin");
            getLogger.info("§cCheck updates manually at §e§lhttps://github.com/HJFunnyMinecraft/BungeeChatX");
            return;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            getLogger.info("§cError while checking the updates of the plugin");
            getLogger.info("§cCheck updates manually at §e§lhttps://github.com/HJFunnyMinecraft/BungeeChatX");
            return;
        } catch (ParseException e) {
            e.printStackTrace();
            getLogger.info("§cError while checking the updates of the plugin");
            getLogger.info("§cCheck updates manually at §e§lhttps://github.com/HJFunnyMinecraft/BungeeChatX");
            return;
        } catch (IOException e) {
            e.printStackTrace();
            getLogger.info("§cError while checking the updates of the plugin");
            getLogger.info("§cCheck updates manually at §e§lhttps://github.com/HJFunnyMinecraft/BungeeChatX");
            return;
        } finally {
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                getLogger.info("§cError while checking the updates of the plugin");
                getLogger.info("§cCheck updates manually at §e§lhttps://github.com/HJFunnyMinecraft/BungeeChatX");
                return;
            }
        }

        private Map vermap = (Map) JSON.parse(result);
        LatestVersion = vermap.get("tag_name").toString();
        if(PluginVersion != LatestVersion) {
            getLogger.info("§2§lNew Update Available!");
            getLogger.info("§eGet update at §lhttps://github.com/HJFunnyMinecraft/BungeeChatX");
        } else {
            getLogger.info("§2§lThis is the latest version!");
        }
    }
    
    @Override
    public void onEnable() {
        getLogger().info("§c ____                               ____ _           _  __  __§r");
        getLogger().info("§c| __ ) _   _ _ __   __ _  ___  ___ / ___| |__   __ _| |_\\ \\/ /§r");
        getLogger().info("§c|  _ \\| | | | '_ \\ / _` |/ _ \\/ _ \\ |   | '_ \\ / _` | __|\\  / §r");
        getLogger().info("§c| |_) | |_| | | | | (_| |  __/  __/ |___| | | | (_| | |_ /  \\ §r");
        getLogger().info("§c|____/ \\__,_|_| |_|\\__, |\\___|\\___|\\____|_| |_|\\__,_|\\__/_/\\_\\§r");
        getLogger().info("§c                   |___/                                      §r");
        getLogger().info("Plugin by §bbcmray & CodeZhangBorui§r, §b" + PluginVersion + "§r, §lLoading...§r");
        // 注册监听器
        getProxy().getPluginManager().registerListener(this, new PostLoginEvent());
        getProxy().getPluginManager().registerListener(this, new ChatEvent(this));

        // 注册指令
        getProxy().getPluginManager().registerCommand(this,new MsgCommand(this));

        // 注册 bStats
        int pluginId = 17333;
        Metrics metrics = new Metrics(this, pluginId);

        //检查更新
        chkUpdate();

        getLogger().info("§c§l[BungeeChatX]§r Enabled!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("§c§l[BungeeChatX]§r Disabled!");
    }
}
