# BungeeJoinMOTD

A simple but powerful **on-join** message of the day for **BungeeCord** powered by [MineDown](https://github.com/Phoenix616/MineDown).

![Screenshot](https://i.imgur.com/GQBRq3w.png)

## Features

- Powerful **JSON** and **Hex Color** messages using [MineDown syntax](https://wiki.phoenix616.dev/library:minedown:syntax).
- Extremely **easy** to use.
- Fully **configurable** in every way.
- Message of the day can be set in-game.

## Commands / Permissions

|                **Command**                |                  **Description**                  |     **Permission**     |
| :---------------------------------------: | :-----------------------------------------------: | :--------------------: |
|             /motd help                    |              View the plugin help.                |          n/a           |
|             /motd [view]                  |              View the message of the day.         | bungeejoinmotd.use     |
|             /motd add \<value\>           |          Add a line to the message of the day.    | bungeejoinmotd.admin   |
|             /motd remove \<line\>         |     Remove a line from the message of the day.    | bungeejoinmotd.admin   |
|             /motd set \<line\> \<value\>  |          Set a line in the message of the day.    | bungeejoinmotd.admin   |
|             /motd reload                  |          Reload the plugin configuration.         | bungeejoinmotd.admin   |

## Placeholders

|                **Placeholder**            |                  **Description**                  |
| :---------------------------------------: | :-----------------------------------------------: |
| %player_name%                             | The player's name.                                |
| %server_name%                             | The current server name.                          |
| %server_count%                            | The current server player count.                  |
| %server_time%                             | The current server time.                          |
| %bungee_name%                             | The network server name.                          |
| %bungee_count%                            | The network player count.                         |
