package cn.puehmc.fuckName.core;

import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class ConfigManager {
    private final Path configPath;
    private final Yaml yaml;
    private FuckNameConfig config;
    
    public ConfigManager(Path configPath) {
        this.configPath = configPath;
        this.yaml = new Yaml();
    }
    
    /**
     * 加载配置文件
     * @return 加载的配置对象
     */
    @SuppressWarnings("unchecked")
    public FuckNameConfig loadConfig() {
        try {
            if (!Files.exists(configPath)) {
                createDefaultConfig();
            }
            
            try (InputStream inputStream = Files.newInputStream(configPath)) {
                Map<String, Object> data = yaml.load(inputStream);
                this.config = new FuckNameConfig(data);
                return this.config;
            }
        } catch (IOException e) {
            System.err.println("Failed to load config: " + e.getMessage());
            this.config = new FuckNameConfig(null); // 使用默认配置
            return this.config;
        }
    }
    
    /**
     * 重新加载配置文件
     * @return 重新加载的配置对象
     */
    public FuckNameConfig reloadConfig() {
        return loadConfig();
    }
    
    /**
     * 获取当前配置
     * @return 当前配置对象
     */
    public FuckNameConfig getConfig() {
        if (config == null) {
            loadConfig();
        }
        return config;
    }
    
    /**
     * 创建默认配置文件
     */
    private void createDefaultConfig() {
        try {
            // 确保父目录存在
            Files.createDirectories(configPath.getParent());
            
            String defaultConfig = "# FuckName Plugin Configuration\n" +
                    "# 踢出消息，{word} 会被替换为违规词语\n" +
                    "kickMessage: \"§c您所输入的名字不合法，请换个名字，不允许的：{word}\"\n" +
                    "\n" +
                    "# 黑名单词语列表\n" +
                    "blacklist:\n" +
                    "  - \"违禁词1\"\n" +
                    "  - \"违禁词2\"\n" +
                    "  - \"违禁词3\"\n" +
                    "  - \"违禁词4\"\n" +
                    "  - \"违禁词5\"\n";
            
            Files.write(configPath, defaultConfig.getBytes("UTF-8"));
        } catch (IOException e) {
            System.err.println("Failed to create default config: " + e.getMessage());
        }
    }
}