package site.hjfunny.bungeechatx;

import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class PlayerDataProcesser {
    static public Map<SocketAddress, ProxiedPlayer> PlayerSocketMap = new HashMap<SocketAddress, ProxiedPlayer>();
    static public List<ProxiedPlayer> JoinedPlayers = new ArrayList<ProxiedPlayer>();
    static public Map<String, Boolean> PlayerReceiveSettings = new HashMap<String, Boolean>();
    static public LuckPerms luckPerms;

    public static Boolean initLuckpermsProvider() {
        // Initalize LuckPerms API Provider
        try {
            luckPerms = LuckPermsProvider.get();
        } catch (IllegalStateException e) {
            // LuckPerms hasn't installed or enabled
            luckPerms = null;
            return false;
        }
        return true;
    }
    public static String getPlayerPrefix(String playerName) {
        if(luckPerms == null) return null; // Return null if Luckperms hasn't installed or enabled
        User user = luckPerms.getUserManager().getUser(playerName);
        if (user == null) {
            // Player Not Found
            return null;
        }
        // Get player prefix from LuckPerms API
        return user.getCachedData().getMetaData(luckPerms.getContextManager().getQueryOptions(user).orElse(luckPerms.getContextManager().getStaticQueryOptions())).getPrefix();
    }
}
