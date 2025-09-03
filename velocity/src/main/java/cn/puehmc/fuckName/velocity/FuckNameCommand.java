package cn.puehmc.fuckName.velocity;

import cn.puehmc.fuckName.core.BaseCommandHandler;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class FuckNameCommand extends BaseCommandHandler implements SimpleCommand {
    private final FuckNameVelocity plugin;
    
    public FuckNameCommand(FuckNameVelocity plugin) {
        super(plugin.getConfigManager());
        this.plugin = plugin;
    }
    
    @Override
    public void execute(Invocation invocation) {
        CommandSource source = invocation.source();
        String[] args = invocation.arguments();
        
        if (!isReloadCommand(args)) {
            source.sendMessage(LegacyComponentSerializer.legacySection().deserialize(
                "§c" + getUsageMessage()
            ));
            return;
        }
        
        // 检查权限
        if (source instanceof Player) {
            Player player = (Player) source;
            if (!player.hasPermission("fuckname.reload")) {
                source.sendMessage(LegacyComponentSerializer.legacySection().deserialize(
                    "§c" + getNoPermissionMessage()
                ));
                return;
            }
        }
        
        CommandResult result = handleReload(getSourceName(source));
        String colorCode = result.isSuccess() ? "§a" : "§c";
        source.sendMessage(LegacyComponentSerializer.legacySection().deserialize(
            colorCode + result.getMessage()
        ));
    }
    
    private String getSourceName(CommandSource source) {
        if (source instanceof Player) {
            return ((Player) source).getUsername();
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
            plugin.getLogger().error("重载配置时发生错误: " + e.getMessage());
            return false;
        }
    }
    
    @Override
    protected void logInfo(String message) {
        plugin.getLogger().info(message);
    }
    
    @Override
    protected void logWarning(String message) {
        plugin.getLogger().warn(message);
    }
    
    @Override
    protected void logError(String message) {
        plugin.getLogger().error(message);
    }
    
    @Override
    public CompletableFuture<List<String>> suggestAsync(Invocation invocation) {
        String[] args = invocation.arguments();
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
        
        return CompletableFuture.completedFuture(suggestions);
    }
    
    @Override
    public boolean hasPermission(Invocation invocation) {
        CommandSource source = invocation.source();
        
        // 控制台总是有权限
        if (!(source instanceof Player)) {
            return true;
        }
        
        Player player = (Player) source;
        
        // 只有reload命令，检查reload权限
        return player.hasPermission("fuckname.reload");
    }
}