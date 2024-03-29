package site.hjfunny.bungeechatx;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class ConfigurationProcesser {
    static Configuration PluginConfig;

    static public Boolean LoadConfig(Plugin plugin) throws IOException {
        try {
            PluginConfig = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(plugin.getDataFolder(), "config.yml"));
        } catch (IOException e) {
            if (!plugin.getDataFolder().exists()) {
                plugin.getDataFolder().mkdir();
            }
            File file = new File(plugin.getDataFolder(), "config.yml");
            if (!file.exists()) {
                try (InputStream in = plugin.getResourceAsStream("config.yml")) {
                    Files.copy(in, file.toPath());
                } catch (IOException ee) {
                    ee.printStackTrace();
                    return false;
                }
            }
            plugin.getLogger().info("Default configuration file copied.");
            try {
                ConfigurationProcesser.PluginConfig = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(plugin.getDataFolder(), "config.yml"));
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
            plugin.getLogger().info("Configuration file loaded.");
        }
        return true;
    }
}
