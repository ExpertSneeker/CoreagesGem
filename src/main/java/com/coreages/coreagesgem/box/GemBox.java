package com.coreages.coreagesgem.box;

import com.coreages.coreagesgem.CoreagesGem;
import com.coreages.coreagesgem.utils.MsgUtils;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * ClassName: GemBox
 * Package: com.coreages.coreagesgem.box
 * Description:
 *
 * @Author ExpertSneeker
 * @Create 2024/1/6 15:37
 * @Version 1.0
 */
public class GemBox {
    private int max = -1;

    private ArrayList<String> support;

    private final Enchantment enchantment;

    private boolean indestructible = false;

    private ArrayList<String> lore = null;

    public GemBox(Map<?, ?> map) {
        Object o = map.get("max");
        if (o != null)
            this.max = ((Integer)o).intValue();
        o = map.get("support");
        if (o instanceof ArrayList)
            this.support = new ArrayList<>((Collection<? extends String>)o);
        this.enchantment = Enchantment.getByName(((String)map.get("enchant")).toUpperCase());
        if (map.containsKey("indestructible")) {
            String msg = (String)map.get("indestructible");
            if (msg.equalsIgnoreCase("t"))
                this.indestructible = true;
        }
        if (map.containsKey("addlore")) {
            List<?> list = (List)map.get("addlore");
            this.lore = new ArrayList<>();
            list.forEach(ob -> this.lore.add(MsgUtils.format(ob.toString())));
        }
    }

    public Enchantment getEnchantment() {
        return this.enchantment;
    }

    public boolean isIndestructible() {
        return this.indestructible;
    }

    public ArrayList<String> getLore() {
        return this.lore;
    }

    public ArrayList<String> getSupport() {
        return this.support;
    }

    public int getLevel(ItemStack itemStack) {
        return itemStack.getEnchantmentLevel(this.enchantment);
    }

    public boolean canStrengthen(int level) {
        int limit = (this.max != -1) ? this.max : CoreagesGem.max;
        return (level < limit);
    }

    public void strengthen(ItemStack itemStack, int level) {
        itemStack.addUnsafeEnchantment(this.enchantment, ++level);
    }
}
