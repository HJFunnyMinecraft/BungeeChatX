# BungeeChatX

**中文** | [English](https://github.com/HJFunnyMinecraft/BungeeChatX/blob/main/README.md)

基于 BungeeCord 的跨服聊天

![bStats Data](https://bstats.org/signatures/bungeecord/bungeechatx.svg)

## 插件特点

- √ 原版风格
- √ 不影响下游服务器消息处理插件
- √ 支持私聊

## 如何使用

将插件 `jar` 文件放入 Bungeecord 服务端（不是下游服务器） `/plugins` 目录下即可

## 指令

### `/bcmsg`

别名：`/bctell` `/msgs` `/tells`\
用法：`/bcmsg <玩家名> <消息>`\
描述：跨服私聊

### `/mention`

别名：`/men`\
用法：`/mention <玩家名> <消息>`
描述：提及某人（@功能）

### `/bcr`

别名：`/r`\
用法：`/bcr <on/off>`
描述：开启/关闭消息互通

## 配置文件

```yaml
# 默认配置
features:
  # 启用或禁用某些功能
  # 如果你想启用，设置为 true
  # 如果你想禁用，设置为 false
  playerJoinMessage: true
  playerLeaveMessage: true
messages:
  # 自定义你的消息模板
  # 你可以将其翻译为你的语言
  # 自由地使用颜色代码
  playerJoinMessage: '§a§l+ §r%0% 加入了服务器群组' # %0% 将会被替换为玩家的名称
  playerLeaveMessage: '§8§l- §r%0% 离开了服务器群组' # %0% 将会被替换为玩家的名称
  wrongCommand: '§c§l错误: §r代码语法错误！'
  playerNotFound: '§c§l错误: §r玩家§l %0% §r不存在！' # %0% 将会被替换为玩家的名称
  sendBannedWords: '你不允许发送文字 [ %0% ]，它已经被管理员禁用。' # %0% 将会被替换为玩家的名称
  playerRSOn: '消息互通已开启，您将收到来自所有服务器的消息。'
  playerRSOff: '消息互通已关闭，您将只收到来自您所在服务器的消息。'
bannedWords:
  wordList:
  # 您想要不允许玩家发送的文字，需要启用“bannedWords“功能
  - 'Fuck'
  - 'Shit'
```

```
