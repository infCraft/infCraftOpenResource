package org.time.activitycoin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.time.activitycoin.gui.GuiItem;
import org.time.activitycoin.utils.Utils;

import com.google.common.collect.Lists;

public class ActivityCoin extends JavaPlugin implements Listener {
	// �����ļ�
	public static YamlConfiguration config;
	// ��������ļ�
	public static YamlConfiguration players;
	
	public static ActivityCoin instance;
	
	public static final String prefix = "��e������̳� ��7>> ��r";
	
	public void onEnable() {
		instance = this;
		
		Bukkit.getPluginCommand("acoin").setExecutor(new Commands());
		Bukkit.getPluginCommand("acoin").setTabCompleter(new Commands());
		Bukkit.getPluginManager().registerEvents(this, this);
		
		config = Utils.loadFile(instance, "config.yml");
		players = Utils.loadFile(instance, "players.yml");
		
		readConfig();
		readPlayerData();
		
		// ��ʱ��������
		new BukkitRunnable() {

			@Override
			public void run() {
				savePlayerData();
			}
			
		}.runTaskTimer(instance, 12000L, 12000L);
	}
	
	public void onDisable() {
		savePlayerData();
	}
	
	/**
	 * ���ز��
	 */
	public static void reload() {
		Bukkit.getScheduler().cancelTasks(instance);
		savePlayerData();
		
		config = Utils.loadFile(instance, "config.yml");
		players = Utils.loadFile(instance, "players.yml");
		
		readConfig();
		readPlayerData();
		
		new BukkitRunnable() {

			@Override
			public void run() {
				savePlayerData();
			}
			
		}.runTaskTimer(instance, 12000L, 12000L);
	}
	
	/**
	 * ��ȡconfig.yml
	 */
	private static void readConfig() {
		List<GuiItem> list = Lists.newArrayList();
		for (String id: config.getConfigurationSection("Gui").getKeys(false)) {
			list.add(readGuiItem(id));
		}
		Cache.guiitems = list;
 	}
	
	/**
	 * �����л�guiitem
	 * @param id ��Ӧguiitem��id
	 * @return ������GuiItem����
	 */
	@NotNull
	private static GuiItem readGuiItem(String id) {
		ItemStack item = new ItemStack(Material.getMaterial(config.getString("Gui."+id+".Material").toUpperCase()));
		ItemMeta meta = item.getItemMeta();
		
		// �ж�name,lore,enchant
		String name = config.getString("Gui."+id+".Name");
		if (name != null) meta.setDisplayName(Utils.replaceAll(name));
		List<String> lore = config.getStringList("Gui."+id+".Lore");
		if (lore != null) meta.setLore(Utils.changeLore(lore, "&", "��"));
		List<String> enchs = config.getStringList("Gui."+id+".Enchantments");
		Map<Enchantment,Integer> enchants = Utils.getEnchantsFromStringList(enchs);
		item.setItemMeta(meta);
		item.addUnsafeEnchantments(enchants);
		
		// ��ʼ��������
		int price = config.getInt("Gui."+id+".Price");
		List<String> commands = Utils.changeLore(config.getStringList("Gui."+id+".Action"), "&", "��");
		GuiItem guiitem = new GuiItem(id, item, price, commands);
		return guiitem;
	}
	
	/**
	 * ��ȡplayerdata
	 */
	private static void readPlayerData() {
		Map<String,Integer> map = new HashMap<>();
		for (String id: players.getConfigurationSection("").getKeys(false)) {
			map.put(id, players.getInt(id));
		}
		Cache.data = map;
	}
	
	/**
	 * �����������
	 */
	private static void savePlayerData() {
		for (String player: Cache.data.keySet()) {
			players.set(player, Cache.data.get(player));
		}
		try {
			players.save(new File(instance.getDataFolder(), "players.yml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * ������
	 * @param e
	 */
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		Inventory inv = e.getInventory();
		// ���ж��Ƿ�Ϊ������̳�
		if (!e.getView().getTitle().equalsIgnoreCase("������̳�")) return;
		
		// ȡ���¼�
		e.setCancelled(true);
		
		// �ж��Ƿ����Ǵ򿪵�gui�����ǾͲ�ִ������Ĵ���
		if (inv != e.getClickedInventory()) return;
		
		// ��������ȡ�����Ϣ
		int page = Integer.parseInt(inv.getItem(47).getItemMeta().getDisplayName());
		int slot = e.getSlot();
		Player p = (Player) e.getWhoClicked();
		
		// ����հ״�slot = -999
		//p.sendMessage(slot+"");
		// �ж��Ƿ�Ϊ���һ��
		if (slot < 0||slot == 45||slot == 47||slot == 48||slot == 49||slot == 50||slot == 51||slot == 53) return;
		// ��ҳ����
		else if (slot == 46) {
			if (inv.getItem(46).getType() != Material.GREEN_STAINED_GLASS_PANE) return;
			p.openInventory(Utils.buildPreset(page-1, p.getUniqueId()));
		}
		else if (slot == 52) {
			if (inv.getItem(52).getType() != Material.GREEN_STAINED_GLASS_PANE) return;
			p.openInventory(Utils.buildPreset(page+1, p.getUniqueId()));
		}
		// ��������ֻʣ������õ���Ʒ�Ϳ�����
		else {
			if (inv.getItem(slot) == null) return;
			
			// ��ȡ������Ʒ����Ų���ȡ����
			int number = page*45+slot;
			GuiItem guiitem = Cache.guiitems.get(number);
			// �ж��Ƿ�Ǯ
			if (!Cache.data.containsKey(p.getName())) Utils.createNewPlayerData(p.getName());
			int money = Cache.data.get(p.getName());
			if (money<guiitem.getPrice()) {
				p.sendMessage(prefix+"��c��û���㹻�Ļ���ң�");
				return;
			}
			// ����
			Cache.data.replace(p.getName(), money-guiitem.getPrice());
			
			// ִ��ָ��
			guiitem.runCommands(p.getName());
			p.sendMessage(prefix+"��a����ɹ���");
			Utils.log(p.getName(), guiitem);
			
			// ˢ��gui
			inv.setItem(49, Utils.createItem(Material.PAINTING, "��6"+p.getName(), Lists.newArrayList("��f���������: ��e"+Cache.data.get(p.getName()))));
		}
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if (!Cache.data.containsKey(p.getName())) Cache.data.put(p.getName(), 0);
	}
}
