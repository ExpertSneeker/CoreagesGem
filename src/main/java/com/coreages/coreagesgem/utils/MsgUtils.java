package com.coreages.coreagesgem.utils;

import com.coreages.coreagesgem.CoreagesGem;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

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

    public static boolean loreKeywordHand(ItemStack item, List<String> words){

        //获取物品meta
        ItemMeta meta = item.getItemMeta();

        if (meta != null && meta.hasLore()) {
            //获取物品的lore
            List<String> lore = meta.getLore();

            // 检查lore中是否包含特定的字符串
            if (lore != null) {
                for (String line : lore) {
                    for (String keyword : words) {
                        if (line.contains(keyword)) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;//不包含
    }
}
