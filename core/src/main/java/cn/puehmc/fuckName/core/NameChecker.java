package cn.puehmc.fuckName.core;

import java.util.List;

public class NameChecker {
    private final FuckNameConfig config;
    
    public NameChecker(FuckNameConfig config) {
        this.config = config;
    }
    
    /**
     * 检查玩家名字是否包含违规词语
     * @param playerName 玩家名字
     * @return 如果包含违规词语则返回该词语，否则返回null
     */
    public String getViolatedWord(String playerName) {
        if (playerName == null || playerName.isEmpty()) {
            return null;
        }
        
        String lowerName = playerName.toLowerCase();
        List<String> blacklist = config.getBlacklist();
        
        for (String word : blacklist) {
            if (word != null && !word.isEmpty() && lowerName.contains(word.toLowerCase())) {
                return word;
            }
        }
        
        return null;
    }
    
    /**
     * 检查玩家名字是否合法
     * @param playerName 玩家名字
     * @return 如果合法返回true，否则返回false
     */
    public boolean isNameValid(String playerName) {
        return getViolatedWord(playerName) == null;
    }
    
    /**
     * 获取格式化的踢出消息
     * @param violatedWord 违规词语
     * @return 格式化后的踢出消息
     */
    public String getFormattedKickMessage(String violatedWord) {
        return config.getKickMessage().replace("{word}", violatedWord);
    }
}