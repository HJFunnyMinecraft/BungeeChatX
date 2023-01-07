package site.hjfunny.bungeechatx;

import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Map;

public class PlayerAddressMapping {
    static public Map<SocketAddress, ProxiedPlayer> playerMap = new HashMap<SocketAddress, ProxiedPlayer>();
}
