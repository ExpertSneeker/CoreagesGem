package com.coreages.coreagesgem.utils;

import com.coreages.coreagesgem.CoreagesGem;
import org.bukkit.command.CommandSender;

/**
 * ClassName: MsgUtils
 * Package: com.coreages.coreagesgem.utils
 * Description:
 *
 * @Author ExpertSneeker
 * @Create 2024/1/6 15:38
 * @Version 1.0
 */
public class MsgUtils {
    public static String format(String msg) {
        if (msg == null)
            return "";
        return msg.replace("&", "§");
    }

    public static void chat(CommandSender sender, String msg) {
        sender.sendMessage(format(CoreagesGem.prefix + msg));
    }
}
