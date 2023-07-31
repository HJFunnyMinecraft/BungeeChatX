package site.hjfunny.bungeechatx;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.NodeType;
import net.luckperms.api.node.types.PrefixNode;
import net.luckperms.api.node.types.SuffixNode;
import net.luckperms.api.query.QueryOptions;

public class PlayerPrefixGetter {

    // 这个方法用于获取玩家的前缀
    public static String getPlayerPrefix(String playerName) {
        // 获取LuckPerms实例
        LuckPerms luckPerms = LuckPermsProvider.get();

        // 获取玩家对象
        User user = luckPerms.getUserManager().getUser(playerName);
        if (user == null) {
            // 玩家不存在，返回一个默认的前缀或者空字符串
            return "";
        }

        // 获取玩家的前缀节点
        QueryOptions queryOptions = luckPerms.getContextManager().getQueryOptions(user).orElse(luckPerms.getContextManager().getStaticQueryOptions());
        return user.getCachedData().getMetaData(queryOptions).getPrefix();
    }
}