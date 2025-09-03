package cn.puehmc.fuckName.core;

/**
 * 基础命令处理器，包含共同的命令逻辑
 */
public abstract class BaseCommandHandler {
    protected final ConfigManager configManager;
    
    public BaseCommandHandler(ConfigManager configManager) {
        this.configManager = configManager;
    }
    
    /**
     * 处理重载命令的核心逻辑
     * @param senderName 命令发送者名称
     * @return 重载结果消息
     */
    public CommandResult handleReload(String senderName) {
        try {
            // 调用平台特定的重载逻辑
            boolean success = performReload();
            if (success) {
                String message = "FuckName 配置已重载！";
                logInfo("配置已被 " + senderName + " 重载");
                return new CommandResult(true, message);
            } else {
                String message = "重载配置失败！";
                logWarning("配置重载失败");
                return new CommandResult(false, message);
            }
        } catch (Exception e) {
            String message = "重载配置时发生错误: " + e.getMessage();
            logError("重载配置时发生错误: " + e.getMessage());
            return new CommandResult(false, message);
        }
    }
    
    /**
     * 检查是否为重载命令
     */
    public boolean isReloadCommand(String[] args) {
        return args.length > 0 && args[0].toLowerCase().equals("reload");
    }
    
    /**
     * 获取用法消息
     */
    public String getUsageMessage() {
        return "用法: /fuckname reload - 重载配置文件";
    }
    
    /**
     * 获取权限不足消息
     */
    public String getNoPermissionMessage() {
        return "你没有权限执行此命令！";
    }
    
    // 抽象方法，由子类实现平台特定的功能
    protected abstract boolean performReload();
    protected abstract void logInfo(String message);
    protected abstract void logWarning(String message);
    protected abstract void logError(String message);
    
    /**
     * 命令执行结果
     */
    public static class CommandResult {
        private final boolean success;
        private final String message;
        
        public CommandResult(boolean success, String message) {
            this.success = success;
            this.message = message;
        }
        
        public boolean isSuccess() {
            return success;
        }
        
        public String getMessage() {
            return message;
        }
    }
}