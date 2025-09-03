package cn.puehmc.fuckName.core;

import java.util.*;

public class FuckNameConfig {
    private List<String> blacklist;
    private String kickMessage;
    
    @SuppressWarnings("unchecked")
    public FuckNameConfig(Map<String, Object> data) {
        if (data == null) {
            this.blacklist = new ArrayList<>();
            this.kickMessage = "§c您所输入的名字不合法，请换个名字，不允许的：{word}";
            return;
        }
        
        this.blacklist = (List<String>) data.getOrDefault("blacklist", new ArrayList<>());
        this.kickMessage = (String) data.getOrDefault("kickMessage", "§c您所输入的名字不合法，请换个名字，不允许的：{word}");
    }
    

    
    // Getters
    public List<String> getBlacklist() {
        return blacklist;
    }
    
    public String getKickMessage() {
        return kickMessage;
    }
}