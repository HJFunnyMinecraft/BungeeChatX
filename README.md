# BungeeChatX

**English** | [中文](https://github.com/HJFunnyMinecraft/BungeeChatX/blob/main/README_ZH.md)

Cross-Server Chat Plugin based on BungeeCord

![bStats Data](https://bstats.org/signatures/bungeecord/bungeechatx.svg)

## Plugin Feature

- √ Minecraft Vanilla Style
- √ Won't effect message processing plugin on proxied server
- √ Support Private Chat

## How to use

Put `jar` file into `/plugins` folder in your Bungeecord server (not proxied server).

## Commands

### `/bcmsg`

Aliases：`/bctell` `/msgs` `/tells`\
Usage: `/bcmsg <PlayerName> <Message>`\
Description: Private chat across servers

### `/mention`

Aliases: `/men`\
Usage: `/mention <PlayerName> <Message>`\
Description: Mention someone

### `/bcr`

Aliases: `/r`\
Usage: `/bcr <on/off>`\
Description: Enable/disable cross-server message communication

## Config File

```yaml
# Default Config
features:
  # Enable or disable some features
  # If you want to enable, set to true
  # If you want to disable, set to false
  playerJoinMessage: true
  playerLeaveMessage: true
  bannedWords: false
messages:
  # Customsize your own message template
  # You can translate all the messages to your language here
  # Freely to use color code
  playerJoinMessage: '§a§l+ §r%0% joined the server group' # %0% will be replaced by player's name
  playerLeaveMessage: '§8§l- §r%0% left the server group' # %0% will be replaced by player's name
  wrongCommand: '§c§lError: §rWrong command syntax!'
  playerNotFound: '§c§lError: §rPlayer§l %0% §rnot found!' # %0% will be replaced by player's name
  sendBannedWords: 'You can nott send the word [ %0% ], it has been banned by your server operator.' # %0% will be replaced by banned word
  playerRSOn: 'You will receive messages from anywhere.'
  playerRSOff: 'You will only receive messages from your server.'
bannedWords:
  wordList:
  # The words you want to prevent players to send, require to enable bannedWords feature
  - 'Fuck'
  - 'Shit'
```