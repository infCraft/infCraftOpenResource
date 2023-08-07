package org.time.activitycoin.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Nonnull;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.time.activitycoin.ActivityCoin;
import org.time.activitycoin.Cache;
import org.time.activitycoin.gui.GuiItem;

import com.google.common.collect.Lists;

public class Utils extends BasicUtil {
	/**
	 * ָ�����
	 * @param sender ָ�����
	 * @param isOp �Ƿ�ΪOp
	 */
	public static void sendHelp(CommandSender sender, boolean isOp) {
		sender.sendMessage("��f=============== ��eActivityCoin ��f===============");
		sender.sendMessage("��6/acoin help ��e��ָ�����");
		sender.sendMessage("��6/acoin shop ��e�򿪻�����̳�");
		if (isOp) sender.sendMessage("��6/acoin give [���] [���������] ��e����ĳ�����һ�������Ļ����");
		if (isOp) sender.sendMessage("��6/acoin take [���] [���������] ��e����ĳ�����һ�������Ļ����");
		if (isOp) sender.sendMessage("��6/acoin set [���] [���������] ��e����ĳ����ҵĻ��������");
		if (isOp) sender.sendMessage("��6/acoin see [���] ��e�鿴ĳ����ҵĻ��������");
		if (isOp) sender.sendMessage("��6/acoin reload ��e���ز��");
	}
	
	/**
     * ��gui�����������Ʒ
     * @param inv ����
     * @param slot Ҫ��ӵĸ���
     * @param item ��Ʒģ��
     */
    public static void drawBackground(Inventory inv, int[] slot, ItemStack item) {
    	for (int i=0;i<slot.length;i++) inv.setItem(slot[i], item);
    }
    
    /**
     * ����GUI
     * @param page ��ǰҳ������0��ʼ��
     * @param maxpage ���ҳ��
     * @return �����õ�GUI
     */
    @NotNull
	public static Inventory buildPreset(int page, @NotNull UUID player) {
		Inventory inv = Bukkit.createInventory(null, 54, "������̳�");
		
		// �������ҳ��
		int size = Cache.guiitems.size();
		int maxpage = size == 0?0:(size-1)/45+1;
		
		// ������һ��
		drawBackground(inv, new int[] {45,47,48,50,51,53}, Utils.createItem(Material.GRAY_STAINED_GLASS_PANE, page+""));
		
		// ���ͷ­��Ʒ��������ʾ�������
		// ���ڸĳ��˻�
		/*ItemStack head = getPlayerHead(player);
		ItemMeta meta = head.getItemMeta();
		meta.setDisplayName("��6"+Bukkit.getOfflinePlayer(player).getName());
		List<String> lore = Lists.newArrayList("��f���������: ��e"+Cache.data.get(Bukkit.getOfflinePlayer(player).getName()));
		meta.setLore(lore);
		head.setItemMeta(meta);*/
		inv.setItem(49, Utils.createItem(Material.PAINTING, "��6"+Bukkit.getOfflinePlayer(player).getName(), Lists.newArrayList("��f���������: ��e"+Cache.data.get(Bukkit.getOfflinePlayer(player).getName()))));
		
		// ��ҳ��ť
		inv.setItem(46, Utils.createItem(Material.GREEN_STAINED_GLASS_PANE, "��a��һҳ", Lists.newArrayList("", "��e��"+(page+1)+"ҳ ��"+maxpage+"ҳ")));
		inv.setItem(52, Utils.createItem(Material.GREEN_STAINED_GLASS_PANE, "��a��һҳ", Lists.newArrayList("", "��e��"+(page+1)+"ҳ ��"+maxpage+"ҳ")));
		if (page == 0) inv.setItem(46, Utils.createItem(Material.BLACK_STAINED_GLASS_PANE, "��a��һҳ", Lists.newArrayList("", "��e��"+(page+1)+"ҳ ��"+maxpage+"ҳ")));
		if (page == maxpage-1) inv.setItem(52, Utils.createItem(Material.BLACK_STAINED_GLASS_PANE, "��a��һҳ", Lists.newArrayList("", "��e��"+(page+1)+"ҳ ��"+maxpage+"ҳ")));

		// ��Ʒ��ť
		for (int i=0;i<45;i++) {
			int number = page*45+i;
			if (number>=size) break;
			inv.setItem(i, Cache.guiitems.get(number).getItem());
		}
		
		return inv;
	}
    
    /**
     * �滻������Ҫ�滻���ַ�
     * ActivityCoin���ר��
     * @param str ��Ҫ�滻���ַ���
     * @return �滻����ַ���
     */
    public static String replaceAll(String str) {
    	return str.replaceAll("&", "��");
    }
    
    /**
     * �����л������ļ��е�Enchantments
     * @param enchants StringList
     * @return �����л����Map<Enchantment,Integer>����ʹ��addUnsafeEnchantments
     */
    @NotNull
    public static Map<Enchantment,Integer> getEnchantsFromStringList(@NotNull List<String> enchants) {
    	Map<Enchantment,Integer> enchs = new HashMap<>();
    	for (String str: enchants) {
    		Enchantment ench = Enchantment.getByName(str.split(",")[0].toUpperCase());
    		int level = Integer.parseInt(str.split(",")[1]);
    		enchs.put(ench, level);
    	}
    	return enchs;
    }
    
    /**
     * �����µ�������ݵ�cache��
     * @param name �������
     */
    public static void createNewPlayerData(@NotNull String name) {
    	Cache.data.put(name, 0);
    }
    
    /**
     * ��¼��ҵĹ������ݵ�logs.txt��
     * @param player ���
     * @param guiitem �����gui��Ʒ
     */
    public static void log(String player, @Nonnull GuiItem guiitem) {
		File f = new File(ActivityCoin.instance.getDataFolder(), "logs.txt");
		try {
			if (!f.exists()) f.createNewFile();
			FileWriter fw = new FileWriter(f, true);
			BufferedWriter bw = new BufferedWriter(fw, 2048);
			bw.write("["+(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()))+"] "+player+" bought "+guiitem.getId()+" $"+guiitem.getPrice()+"\n");
			bw.close();
			fw.close();
		} catch (IOException e) {
			System.err.println("ActivityCoin saving logs gged!");
			e.printStackTrace();
		}
	}
}
