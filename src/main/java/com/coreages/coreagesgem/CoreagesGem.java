package com.coreages.coreagesgem;

import com.coreages.coreagesgem.box.GemBox;
import com.coreages.coreagesgem.command.Cmd;
import com.coreages.coreagesgem.listener.Event;
import com.coreages.coreagesgem.utils.MsgUtils;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.*;

public final class CoreagesGem extends JavaPlugin {
    public static CoreagesGem pl;

    public static String prefix;

    public static int max;

    public static HashMap<String, HashMap<String, GemBox>> boxs;

    public static HashMap<String, ArrayList<String>> group;

    public void onEnable() {
        pl = this;
        loadConfig();
        getServer().getPluginManager().registerEvents(new Event(), this);
        Objects.requireNonNull(getCommand("CoreagesGem")).setExecutor(new Cmd());

    }

    public void onDisable() {}

    public void loadConfig() {
        File config = new File(getDataFolder(), "config.yml");
        final YamlConfiguration cc = loadYaml(config);
        prefix = MsgUtils.format(cc.getString("prefix"));
        max = cc.getInt("max");
        boxs = new HashMap<>();
        for (Map<?, ?> map : (Iterable<Map<?, ?>>)cc.getMapList("enchant")) {
            String type = ((String)map.get("type")).toUpperCase();
            HashMap<String, GemBox> gem = boxs.get(type);
            if (gem == null)
                gem = new HashMap<>();
            gem.put(MsgUtils.format((String)map.get("name")), new GemBox(map));
            boxs.put(type, gem);
        }
        group = new HashMap<>();
        for (String text : cc.getConfigurationSection("group").getKeys(false)) {

            // 创建一个 ArrayList 对象，用来存储每个键对应的内容
            ArrayList<String> list = new ArrayList<String>();

            // 用 getStringList 方法获取每个键对应的内容，它是一个 List 类型的
            List<String> content = cc.getStringList("group." + text);

            list.addAll(content);

            group.put(text, list);

        }
    }

    public YamlConfiguration loadYaml(File file) {
        if (!file.exists())
            saveResource(file.getName(), true);
        return YamlConfiguration.loadConfiguration(file);
    }
}
