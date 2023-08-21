package site.hjfunny.bungeechatx;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class MessagesProcesser {
    static Configuration PluginMessages;

    static public Boolean LoadConfig(Plugin plugin) throws IOException {
        try {
            PluginMessages = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(plugin.getDataFolder(), "messages.yml"));
        } catch (IOException e) {
            if (!plugin.getDataFolder().exists()) {
                plugin.getDataFolder().mkdir();
            }
            File file = new File(plugin.getDataFolder(), "messages.yml");
            if (!file.exists()) {
                try (InputStream in = plugin.getResourceAsStream("messages.yml")) {
                    Files.copy(in, file.toPath());
                } catch (IOException ee) {
                    ee.printStackTrace();
                    return false;
                }
            }
            plugin.getLogger().info("Default messages file copied.");
            try {
                MessagesProcesser.PluginMessages = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(plugin.getDataFolder(), "config.yml"));
            } catch (Exception eee) {
                eee.printStackTrace();
                plugin.getLogger().warning("Error while proceeding the YAML file. Check above and modify the file.");
                return false;
            }
        } catch (Exception eee) {
            eee.printStackTrace();
            plugin.getLogger().warning("Error while proceeding the YAML file. Check above and modify the file, then run /bcx reload.");
            return false;
        } finally {
            plugin.getLogger().info("Messages file loaded.");
        }
        return true;
    }
}
