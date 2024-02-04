package com.coreages.coreagesgem.box;

import com.coreages.coreagesgem.CoreagesGem;
import com.coreages.coreagesgem.utils.MsgUtils;
import org.bukkit.ChatColor;
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

    //自定义材质
    private int custommodels = 0;

    private ArrayList<String> lore = null;
    private String name = null;
    private int uplevel = 1;

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
        if (map.containsKey("setname")) {
            String itemname = (String) map.get("setname");
            this.name = MsgUtils.format(itemname);
        }
        if (map.containsKey("custommodels")){
            this.custommodels = (Integer) map.get("custommodels");
        }
        if (map.containsKey("uplevel")){
            this.uplevel = (Integer) map.get("uplevel");
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
    public String getName(){return name;}
    public int getUplevel(){return uplevel;}

    public int getCustommodels() {
        return this.custommodels;
    }

    public ArrayList<String> getSupport() {
        return this.support;
    }

    public int getLevel(ItemStack itemStack) {
        return itemStack.getEnchantmentLevel(this.enchantment);
    }

    public boolean canStrengthen(int level) {
        int limit = (this.max != -1) ? this.max : CoreagesGem.max;
        return (level + this.uplevel <= limit);
    }

    public void strengthen(ItemStack itemStack, int level) {
        itemStack.addUnsafeEnchantment(this.enchantment, level + this.uplevel);
    }
}
