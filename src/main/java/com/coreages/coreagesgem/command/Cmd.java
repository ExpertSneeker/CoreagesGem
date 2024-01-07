package com.coreages.coreagesgem.command;

import com.coreages.coreagesgem.CoreagesGem;
import com.coreages.coreagesgem.utils.MsgUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * ClassName: Cmd
 * Package: com.coreages.coreagesgem.command
 * Description:
 *
 * @Author ExpertSneeker
 * @Create 2024/1/6 15:37
 * @Version 1.0
 */
public class Cmd implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.isOp()) {
            CoreagesGem.pl.loadConfig();
            MsgUtils.chat(sender, "&e配置文件重载完毕");
        }
        return false;
    }
}
