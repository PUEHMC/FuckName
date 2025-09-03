package cn.puehmc.fuckName.core;

/**
 * 基础连接处理器，包含共同的玩家连接检查逻辑
 */
public abstract class BaseConnectionHandler {
    
    public BaseConnectionHandler() {
    }
    
    /**
     * 处理玩家连接的核心逻辑
     * @param playerName 玩家名
     * @param hasPermission 是否有绕过权限
     * @return 连接结果
     */
    public ConnectionResult handlePlayerConnection(String playerName, boolean hasPermission) {
        // 检查玩家是否有绕过权限
        if (hasPermission) {
            logInfo("玩家 " + playerName + " 拥有绕过权限，跳过名字检查");
            return new ConnectionResult(true, null);
        }
        
        // 获取当前的NameChecker实例
        NameChecker nameChecker = getCurrentNameChecker();
        
        // 检查黑名单并获取违规词语
        String violatedWord = nameChecker.getViolatedWord(playerName);
        if (violatedWord != null) {
            String message = nameChecker.getFormattedKickMessage(violatedWord);
            logInfo("玩家 " + playerName + " 被拒绝连接，违规词语: " + violatedWord);
            return new ConnectionResult(false, message);
        }
        
        logInfo("玩家 " + playerName + " 通过名字检查");
        return new ConnectionResult(true, null);
    }
    
    // 抽象方法，由子类实现平台特定的功能
    protected abstract NameChecker getCurrentNameChecker();
    protected abstract void logInfo(String message);
    
    /**
     * 连接检查结果
     */
    public static class ConnectionResult {
        private final boolean allowed;
        private final String kickMessage;
        
        public ConnectionResult(boolean allowed, String kickMessage) {
            this.allowed = allowed;
            this.kickMessage = kickMessage;
        }
        
        public boolean isAllowed() {
            return allowed;
        }
        
        public String getKickMessage() {
            return kickMessage;
        }
    }
}