# FuckName 插件

一个简单的名字拦截插件，支持多个Minecraft服务器平台。

## 功能特性

- 支持Bukkit/Paper/Folia和Velocity平台
- 基于黑名单的玩家名检查
- 可配置的违规词语列表
- 支持配置文件热重载
- 权限绕过功能

## 安装说明

### Bukkit/Paper/Folia服务器

1. 将 `plugins/bukkit/FuckName-bukkit.jar` 复制到服务器的 `plugins` 目录
2. 重启服务器或使用 `/reload` 命令
3. 编辑 `plugins/FuckName/FuckName.yml` 配置文件

### Velocity代理服务器

1. 将 `plugins/velocity/FuckName-velocity.jar` 复制到Velocity的 `plugins` 目录
2. 重启Velocity服务器
3. 编辑 `plugins/fuckname/FuckName.yml` 配置文件

## 配置文件

```yaml
# 违规词语黑名单
blacklist:
  - "badword1"
  - "badword2"
  - "违规词语"

# 踢出消息模板 (%word% 会被替换为违规词语)
kick-message: "§c你的名字包含违规词语: %word%"
```

## 命令和权限

### 命令
- `/fuckname reload` - 重载配置文件

### 权限
- `fuckname.reload` - 允许重载配置文件
- `fuckname.bypass` - 绕过名字检查（仅Velocity）

## 项目结构

- `core/` - 核心逻辑模块
- `bukkit/` - Bukkit/Folia平台实现
- `velocity/` - Velocity平台实现

## 编译

```bash
mvn package
```

编译完成后，在 `target/` 目录下会生成包含所有平台插件的分发包。