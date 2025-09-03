package cn.puehmc.fuckName.velocity;

import cn.puehmc.fuckName.core.BaseConnectionHandler;
import cn.puehmc.fuckName.core.NameChecker;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.LoginEvent;
import com.velocitypowered.api.event.ResultedEvent;
import com.velocitypowered.api.proxy.Player;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public class PlayerConnectionListener extends BaseConnectionHandler {
    private final FuckNameVelocity plugin;
    
    public PlayerConnectionListener(FuckNameVelocity plugin) {
        super();
        this.plugin = plugin;
    }
    
    @Subscribe
    public void onPlayerLogin(LoginEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getUsername();
        
        // 使用基础处理器的逻辑
        boolean hasPermission = player.hasPermission("fuckname.bypass");
        ConnectionResult result = handlePlayerConnection(playerName, hasPermission);
        
        if (!result.isAllowed()) {
            event.setResult(ResultedEvent.ComponentResult.denied(
                LegacyComponentSerializer.legacySection().deserialize(result.getKickMessage())
            ));
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