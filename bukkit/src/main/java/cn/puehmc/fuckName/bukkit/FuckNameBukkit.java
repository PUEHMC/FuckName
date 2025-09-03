package cn.puehmc.fuckName.bukkit;

import cn.puehmc.fuckName.core.ConfigManager;
import cn.puehmc.fuckName.core.FuckNameConfig;
import cn.puehmc.fuckName.core.NameChecker;
import org.bukkit.plugin.java.JavaPlugin;

import java.nio.file.Path;

public class FuckNameBukkit extends JavaPlugin {
    
    private ConfigManager configManager;
    private NameChecker nameChecker;
    private static FuckNameBukkit instance;
    
    public static FuckNameBukkit getInstance() {
        return instance;
    }
    
    public FuckNameConfig getFuckNameConfig() {
        return configManager.getConfig();
    }
    
    public ConfigManager getConfigManager() {
        return configManager;
    }
    
    public NameChecker getNameChecker() {
        return nameChecker;
    }
    
    @Override
    public void onEnable() {
        instance = this;
        
        // 初始化配置管理器
        Path configPath = getDataFolder().toPath().resolve("FuckName.yml");
        configManager = new ConfigManager(configPath);
        
        // 加载配置
        FuckNameConfig config = configManager.loadConfig();
        nameChecker = new NameChecker(config);
        
        // 注册命令
        FuckNameCommand command = new FuckNameCommand(this);
        getCommand("fuckname").setExecutor(command);
        getCommand("fuckname").setTabCompleter(command);
        
        // 注册事件监听器
        getServer().getPluginManager().registerEvents(new PlayerConnectionListener(this), this);
        
        getLogger().info("FuckName 插件已启用！");
    }
    
    @Override
    public void onDisable() {
        getLogger().info("FuckName 插件已禁用！");
    }
    
    /**
     * 重新加载配置
     */
    public void reloadConfig() {
        FuckNameConfig config = configManager.reloadConfig();
        nameChecker = new NameChecker(config);
        getLogger().info("配置重新加载成功");
    }
}