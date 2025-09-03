package cn.puehmc.fuckName.velocity;

import cn.puehmc.fuckName.core.ConfigManager;
import cn.puehmc.fuckName.core.FuckNameConfig;
import cn.puehmc.fuckName.core.NameChecker;
import com.google.inject.Inject;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import org.slf4j.Logger;

import java.nio.file.Path;

@Plugin(id = "fuckname", name = "FuckName", version = "1.0-SNAPSHOT")
public class FuckNameVelocity {

    @Inject
    private Logger logger;
    
    @Inject
    private ProxyServer server;
    
    @Inject
    @DataDirectory
    private Path dataDirectory;
    
    private ConfigManager configManager;
    private NameChecker nameChecker;
    private static FuckNameVelocity instance;
    
    public static FuckNameVelocity getInstance() {
        return instance;
    }
    
    public Logger getLogger() {
        return logger;
    }
    
    public FuckNameConfig getConfig() {
        return configManager.getConfig();
    }
    
    public ConfigManager getConfigManager() {
        return configManager;
    }
    
    public NameChecker getNameChecker() {
        return nameChecker;
    }
    
    public ProxyServer getServer() {
        return server;
    }
    
    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        instance = this;
        
        // 初始化配置管理器
        Path configPath = dataDirectory.resolve("FuckName.yml");
        configManager = new ConfigManager(configPath);
        
        // 加载配置
        FuckNameConfig config = configManager.loadConfig();
        nameChecker = new NameChecker(config);
        
        // 注册命令
        CommandManager commandManager = server.getCommandManager();
        FuckNameCommand command = new FuckNameCommand(this);
        commandManager.register("fuckname", command, "fn");
        
        // 注册事件监听器
        server.getEventManager().register(this, new PlayerConnectionListener(this));
        
        logger.info("FuckName 插件已启用！");
    }
    
    /**
     * 重新加载配置
     */
    public void reloadConfig() {
        FuckNameConfig config = configManager.reloadConfig();
        nameChecker = new NameChecker(config);
        logger.info("配置重新加载成功");
    }
}