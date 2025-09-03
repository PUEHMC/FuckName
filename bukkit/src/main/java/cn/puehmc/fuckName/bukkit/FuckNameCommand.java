package cn.puehmc.fuckName.bukkit;

import cn.puehmc.fuckName.core.BaseCommandHandler;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class FuckNameCommand extends BaseCommandHandler implements CommandExecutor, TabCompleter {
    private final FuckNameBukkit plugin;
    
    public FuckNameCommand(FuckNameBukkit plugin) {
        super(plugin.getConfigManager());
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!isReloadCommand(args)) {
            sender.sendMessage(ChatColor.RED + getUsageMessage());
            return true;
        }
        
        // 检查权限
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (!player.hasPermission("fuckname.reload")) {
                sender.sendMessage(ChatColor.RED + getNoPermissionMessage());
                return true;
            }
        }
        
        CommandResult result = handleReload(getSourceName(sender));
        if (result.isSuccess()) {
            sender.sendMessage(ChatColor.GREEN + result.getMessage());
        } else {
            sender.sendMessage(ChatColor.RED + result.getMessage());
        }
        return true;
    }
    
    private String getSourceName(CommandSender sender) {
        if (sender instanceof Player) {
            return ((Player) sender).getName();
        } else {
            return "控制台";
        }
    }
    
    @Override
    protected boolean performReload() {
        try {
            plugin.reloadConfig();
            return true;
        } catch (Exception e) {
            plugin.getLogger().severe("重载配置时发生错误: " + e.getMessage());
            return false;
        }
    }
    
    @Override
    protected void logInfo(String message) {
        plugin.getLogger().info(message);
    }
    
    @Override
    protected void logWarning(String message) {
        plugin.getLogger().warning(message);
    }
    
    @Override
    protected void logError(String message) {
        plugin.getLogger().severe(message);
    }@Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> suggestions = new ArrayList<>();
        
        if (args.length <= 1) {
            // 只建议reload命令
            suggestions.add("reload");
            
            if (args.length == 1) {
                String input = args[0].toLowerCase();
                if (!"reload".startsWith(input)) {
                    suggestions.clear();
                }
            }
        }
        
        return suggestions;
    }
}