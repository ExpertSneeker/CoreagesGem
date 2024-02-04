package com.coreages.coreagesgem.listener;

import com.coreages.coreagesgem.CoreagesGem;
import com.coreages.coreagesgem.box.GemBox;
import com.coreages.coreagesgem.utils.MsgUtils;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

/**
 * ClassName: Event
 * Package: com.coreages.coreagesgem.listener
 * Description:
 *
 * @Author ExpertSneeker
 * @Create 2024/1/6 15:37
 * @Version 1.0
 */
public class Event implements Listener {
    @EventHandler(priority = EventPriority.MONITOR)
    public void onClick(InventoryClickEvent e) {
        if (e.isCancelled())
            return;
        if (e.getSlot() == -999)
            return;
        ItemStack cursor = e.getCursor();
        if (cursor == null || cursor.getType() == Material.AIR)
            return;
        String type = cursor.getType().name();
        if (!CoreagesGem.boxs.containsKey(type))
            return;
        ItemStack click = e.getCurrentItem();
        if (click == null || click.getType() == Material.AIR)
            return;
        if (!cursor.hasItemMeta())
            return;
        if (!Objects.<ItemMeta>requireNonNull(cursor.getItemMeta()).hasLore())
            return;
        HashMap<String, GemBox> map = CoreagesGem.boxs.get(type);
        GemBox box = null;
        List<String> lore = cursor.getItemMeta().getLore();
        assert lore != null;
        for (Map.Entry<String, GemBox> entry : map.entrySet()) {
            if (lore.contains(entry.getKey())) {
                box = entry.getValue();
                break;
            }
        }
        if (box == null)
            return;
        e.setCancelled(true);
        type = click.getType().name();
        for (String text : box.getSupport()) {
            ArrayList<String> list = CoreagesGem.group.get(text);
            if (list != null && list.contains(type)) {
                boolean isChanged = MsgUtils.loreKeywordHand(click, Collections.singletonList("幻化"));
                if (e.getSlotType() == InventoryType.SlotType.RESULT) {
                    MsgUtils.chat(e.getWhoClicked(), "&c这个格子无法使用附魔宝石！");
                    return;
                }
                if (click.getAmount() > 1) {
                    MsgUtils.chat(e.getWhoClicked(), "&c重叠的物品无法被强化！");
                    return;
                }
                cursor.setAmount(cursor.getAmount() - 1);
                e.setCursor(cursor);
                if (box.getLore() != null && !isChanged) {
                    ItemMeta itemMeta = click.getItemMeta();
                    ArrayList<String> lores = new ArrayList<>();
                    assert itemMeta != null;
                    if (itemMeta.hasLore())
                        lores.addAll(itemMeta.getLore());
                    lores.addAll(box.getLore());
                    itemMeta.setLore(lores);
                    click.setItemMeta(itemMeta);
                }else {
                    ItemMeta itemMeta = click.getItemMeta();
                    ArrayList<String> lores = new ArrayList<>();
                    assert itemMeta != null;
                    lores.addAll(box.getLore());
                    itemMeta.setLore(lores);
                    click.setItemMeta(itemMeta);
                    MsgUtils.chat(e.getWhoClicked(), "&c武器已经幻化过，已替换原有的物品介绍！");
                }
                if (box.getName() != null){
                    ItemMeta itemMeta = click.getItemMeta();
                    itemMeta.setDisplayName(box.getName());
                    click.setItemMeta(itemMeta);
                }
                if (box.isIndestructible()) {
                    ItemMeta itemMeta = click.getItemMeta();
                    assert itemMeta != null;
                    itemMeta.setUnbreakable(true);
                    click.setItemMeta(itemMeta);
                }
                if(box.getCustommodels() > 0){
                    ItemMeta itemMeta = click.getItemMeta();
                    assert itemMeta != null;
                    itemMeta.setCustomModelData(box.getCustommodels());
                    click.setItemMeta(itemMeta);
                    MsgUtils.chat(e.getWhoClicked(), "&e幻化成功！");
                }
                if (box.getEnchantment() != null) {
                    int level = box.getLevel(click);
                    if (box.canStrengthen(level)) {
                        box.strengthen(click, level);
                        MsgUtils.chat(e.getWhoClicked(), "&e成功强化至等级&b " + (level + box.getUplevel()));
                    } else {
                        MsgUtils.chat(e.getWhoClicked(), "&c该物品已达到最大等级！");
                    }
                }
                e.setCurrentItem(click);
                return;
            }
        }
        MsgUtils.chat(e.getWhoClicked(), "&c该宝石无法对该物品进行强化！");
    }
}