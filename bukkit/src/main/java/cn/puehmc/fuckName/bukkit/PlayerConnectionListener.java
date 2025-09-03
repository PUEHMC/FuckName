package cn.puehmc.fuckName.bukkit;

import cn.puehmc.fuckName.core.BaseConnectionHandler;
import cn.puehmc.fuckName.core.NameChecker;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

public class PlayerConnectionListener extends BaseConnectionHandler implements Listener {
    private final FuckNameBukkit plugin;
    
    public PlayerConnectionListener(FuckNameBukkit plugin) {
        super();
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onPlayerPreLogin(AsyncPlayerPreLoginEvent event) {
        String playerName = event.getName();
        
        // 使用基础处理器的逻辑
        ConnectionResult result = handlePlayerConnection(playerName, false); // Bukkit在预登录阶段无法检查权限
        
        if (!result.isAllowed()) {
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, 
                ChatColor.translateAlternateColorCodes('§', result.getKickMessage()));
        }
    }
    
    @Override
    protected NameChecker getCurrentNameChecker() {
        return plugin.getNameChecker();
    }
    
    @Override
    protected void logInfo(String message) {
        plugin.getLogger().info(message);
    }
}