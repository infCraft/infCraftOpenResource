package org.time.activitycoin.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import com.google.common.collect.Lists;

import net.minecraft.server.v1_16_R3.NBTTagCompound;

public class BasicUtil {
	//�����ļ�
	@NotNull
	public static YamlConfiguration loadFile(Plugin p, String file) {
		File f = new File(p.getDataFolder(), file);
		if (!f.exists()) createFile(p, f);
		p.getLogger().info("��a�ɹ����� ��e"+f.getName());
		return YamlConfiguration.loadConfiguration(f);
	}
	//�����ļ�
	public static void createFile(Plugin p, File f) {
		p.getLogger().info("��cδ�ҵ� ��e"+f.getName()+" ��c�������´���...");
		p.saveResource(f.getName(), true);
	}
	//��ȡ���ĵ�ʱ��
	public static long getUsedTime(long start) {
		return System.currentTimeMillis()-start;
	}
	//�滻ĳ��lore��������е�ĳ���ַ�
	public static List<String> changeLore(List<String> lore,String regax,String replacement) {
		List<String> lore2 = Lists.newArrayList();
		for (int i=0;i<lore.size();i++) {
			lore2.add(lore.get(i).replaceAll(regax, replacement));
		}
		return lore2;
	}
	//��һ����Ʒ�Ӹ�ħ
	public static ItemStack addEnchantmentToItem(ItemStack item, Enchantment enchant, int level) {
		ItemMeta meta = item.getItemMeta();
		meta.addEnchant(enchant, level, true);
		item.setItemMeta(meta);
		return item;
	}
	//��������
	public static ItemStack setAmount(ItemStack item, int amount) {
		ItemStack item2 = item.clone();
		item2.setAmount(amount);
		return item2;
	}
	//��������
	public static ItemStack setName(ItemStack item, String name) {
		ItemStack item2 = item.clone();
		ItemMeta meta = item2.getItemMeta();
		meta.setDisplayName(name);
		item2.setItemMeta(meta);
		return item2;
	}
	//�����µ���Ʒ
	public static ItemStack createItem(Material material,String name) {
		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		item.setItemMeta(meta);
		return item;
	}
	public static ItemStack createItem(Material material,String name,List<String> lore) {
		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		if (!lore.isEmpty()) meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	public static ItemStack createItem(Material material,String name,List<String> lore, boolean unbreakable) {
		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		if (!lore.isEmpty()) meta.setLore(lore);
		meta.setUnbreakable(unbreakable);
		item.setItemMeta(meta);
		return item;
	}
	public static ItemStack createItem(Material material,List<String> lore,int amount) {
		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();
		if (!lore.isEmpty()) meta.setLore(lore);
		item.setItemMeta(meta);
		if (amount>64) amount = 64;
		item.setAmount(amount);
		return item;
	}
	public static ItemStack createItem(Material material, String name, List<String> lore, List<Enchantment> enchantment, List<Integer> level) {
		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		meta.setLore(lore);
		for (int i=0;i<enchantment.size();i++) meta.addEnchant(enchantment.get(i), level.get(i), true);
		item.setItemMeta(meta);
		return item;
	}
	public static ItemStack createItem(Material material, String name, List<String> lore, List<Enchantment> enchantment, List<Integer> level, boolean unbreakable) {
		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		meta.setLore(lore);
		for (int i=0;i<enchantment.size();i++) meta.addEnchant(enchantment.get(i), level.get(i), true);
		meta.setUnbreakable(unbreakable);
		item.setItemMeta(meta);
		return item;
	}
	public static ItemStack createItem(Material material,String name,Enchantment enchantment,int level) {
		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		meta.addEnchant(enchantment, level, true);
		item.setItemMeta(meta);
		return item;
	}
	public static ItemStack createItem(Material material,String name,List<String> lore,Enchantment enchantment,int level) {
		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		if (!lore.isEmpty()) meta.setLore(lore);
		meta.addEnchant(enchantment, level, true);
		item.setItemMeta(meta);
		return item;
	}
	public static ItemStack createItem(Material material,String name,List<String> lore,List<Enchantment> enchantment,HashMap<Enchantment,Integer> level) {
		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		if (!lore.isEmpty()) meta.setLore(lore);
		if (!enchantment.isEmpty()&&!level.isEmpty()) {
			for (Enchantment en:enchantment) {
				meta.addEnchant(en, level.get(en), true);
			}
		}
		item.setItemMeta(meta);
		return item;
	}
	public static ItemStack createItem(Material material,String name, int amount) {
		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		item.setItemMeta(meta);
		item.setAmount(amount);
		return item;
	}
	public static ItemStack createItem(Material material,String name,List<String> lore, int amount) {
		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		if (!lore.isEmpty()) meta.setLore(lore);
		item.setItemMeta(meta);
		item.setAmount(amount);
		return item;
	}
	public static ItemStack createItem(Material material, String name, List<String> lore, List<Enchantment> enchantment, List<Integer> level, int amount) {
		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		meta.setLore(lore);
		for (int i=0;i<enchantment.size();i++) meta.addEnchant(enchantment.get(i), level.get(i), true);
		item.setItemMeta(meta);
		item.setAmount(amount);
		return item;
	}
	public static ItemStack createItem(Material material,String name,Enchantment enchantment,int level, int amount) {
		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		meta.addEnchant(enchantment, level, true);
		item.setItemMeta(meta);
		item.setAmount(amount);
		return item;
	}
	public static ItemStack createItem(Material material,String name,List<String> lore,Enchantment enchantment,int level, int amount) {
		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		if (!lore.isEmpty()) meta.setLore(lore);
		meta.addEnchant(enchantment, level, true);
		item.setItemMeta(meta);
		item.setAmount(amount);
		return item;
	}
	public static ItemStack createItem(Material material,String name,List<String> lore,List<Enchantment> enchantment,HashMap<Enchantment,Integer> level, int amount) {
		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		if (!lore.isEmpty()) meta.setLore(lore);
		if (!enchantment.isEmpty()&&!level.isEmpty()) {
			for (Enchantment en:enchantment) {
				meta.addEnchant(en, level.get(en), true);
			}
		}
		item.setItemMeta(meta);
		item.setAmount(amount);
		return item;
	}
	/*
	 * ����������Ʒ
	 * ��ע��sfitem����������ʹ�ã�
	 */
	public static ItemStack createTextureItem(Material material,String name,List<String> lore, int customModelData) {
		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		if (!lore.isEmpty()) meta.setLore(lore);
		meta.setCustomModelData(customModelData);
		item.setItemMeta(meta);
		return item;
	}
	//������
	//attackspeed�Ǵ�4��ʼ�ģ�Ҫ����
	public static ItemStack createSword(Material material,String name,List<String> lore, double damage, double attackSpeed) {
		return createSword(material, name, lore, damage, attackSpeed, false);
	}
	public static ItemStack createSword(Material material,String name,List<String> lore, double damage, double attackSpeed, boolean unbreakable) {
		return createSword(material, name, lore, damage, attackSpeed, unbreakable, null, null);
	}
	public static ItemStack createSword(Material material,String name,List<String> lore, double damage, double attackSpeed, boolean unbreakable, List<Enchantment> enchantment, List<Integer> level) {
		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		meta.setLore(lore);
		meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(UUID.randomUUID(), "awa1", damage, Operation.ADD_NUMBER, EquipmentSlot.HAND));
		meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(UUID.randomUUID(), "awa2", attackSpeed, Operation.ADD_NUMBER, EquipmentSlot.HAND));
		meta.setUnbreakable(unbreakable);
		if (enchantment != null) for (int i=0;i<enchantment.size();i++) meta.addEnchant(enchantment.get(i), level.get(i), true);
		item.setItemMeta(meta);
		return item;
	}
	//��Ʒ�����Ƿ���ƥ�����Ʒ�������������٣�
	@Deprecated
	public static boolean hasItemWithoutAmount(Inventory inv, ItemStack item) {
		for (ItemStack item2: inv.getContents()) {
			if (item2 == null||item2.getType() == Material.AIR) continue;
			if (item.clone().asOne().equals(item2.clone().asOne())) return true;
		}
		return false;
	}
	//�ҵ���Ʒ����ƥ�����Ʒ������������
	@Deprecated
	public static ItemStack getSameItemWithoutAmount(Inventory inv, ItemStack item) {
		for (ItemStack item2: inv.getContents()) {
			if (item2 == null||item2.getType() == Material.AIR) continue;
			if (item.clone().asOne().equals(item2.clone().asOne())) return item2;
		}
		return null;
	}
	//�����ļ�
	public static void move(File src, File des) throws IOException {
		FileInputStream fis = new FileInputStream(src);
		FileOutputStream fos = new FileOutputStream(des);
		int len = 0;
		byte[] buffer = new byte[1024];
		while ((len=fis.read(buffer))!=-1) {
			fos.write(buffer, 0, len);
		}
		fis.close();
		fos.close();
	}
	//��ȡNBT
	public static NBTTagCompound getNBTTagCompound(ItemStack item) {
		net.minecraft.server.v1_16_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
		if (!nmsItem.hasTag()) return null;
		return nmsItem.getTag();
	}
	//����NBT
	public static ItemStack setNBT(ItemStack item, String dataName, Object data) {
		net.minecraft.server.v1_16_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
		NBTTagCompound compound = nmsItem.hasTag()?nmsItem.getTag():new NBTTagCompound();
		if (data instanceof Integer) compound.setInt(dataName, (int) data);
		if (data instanceof String) compound.setString(dataName, (String) data);
		nmsItem.setTag(compound);
		return CraftItemStack.asBukkitCopy(nmsItem);
	}
	//ɾ��NBT
	public static ItemStack removeNBT(ItemStack item, String dataName) {
		net.minecraft.server.v1_16_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
		NBTTagCompound compound = nmsItem.getTag();
		compound.remove(dataName);
		nmsItem.setTag(compound);
		return CraftItemStack.asBukkitCopy(nmsItem);
	}
	//��������NBT
	public static ItemStack setNBT(ItemStack item, HashMap<String,Object> map) {
		net.minecraft.server.v1_16_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
		NBTTagCompound compound = nmsItem.hasTag()?nmsItem.getTag():new NBTTagCompound();
		for (String key: map.keySet()) {
			if (map.get(key) instanceof Integer) compound.setInt(key, (int) map.get(key));
			if (map.get(key) instanceof String) compound.setString(key, (String) map.get(key));
		}
		nmsItem.setTag(compound);
		return CraftItemStack.asBukkitCopy(nmsItem);
	}
	//����ɾ��NBT
	public static ItemStack removeNBT(ItemStack item, List<String> list) {
		net.minecraft.server.v1_16_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
		NBTTagCompound compound = nmsItem.hasTag()?nmsItem.getTag():new NBTTagCompound();
		for (String key: list) {
			compound.remove(key);
		}
		nmsItem.setTag(compound);
		return CraftItemStack.asBukkitCopy(nmsItem);
	}
	//��ȡ������ȵ�String
	public static String getRandomString(int minLength, int maxLength) {
	    String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	    Random random = new Random();
	    int length = random.nextInt(maxLength) % (maxLength - minLength + 1) + minLength;
	    StringBuffer sb = new StringBuffer();
	    for (int i = 0; i < length; i++) {
	    	int number = random.nextInt(62);
	    	sb.append(str.charAt(number));
	    }
	    return sb.toString();
	}
	//ֱ�����lore
	public static ItemStack addLore(ItemStack item, String content) {
		ItemMeta meta = item.getItemMeta();
		List<String> lore = meta.hasLore()?meta.getLore():Lists.newArrayList();
		lore.add(content);
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	//ֱ���滻��Ʒlore
	public static ItemStack setLore(ItemStack item, List<String> lore) {
		ItemMeta meta = item.getItemMeta();
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	//������Ʒname��lore
	public static ItemStack setItem(ItemStack item, String name, List<String> lore) {
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	//��ȡһ����Χ�ڵ������(min-(max-1))
	public static int getRandomNumber(int min, int max) {
		return (new Random()).nextInt(max-min)+min;
	}
	//��ȡһ����Χ�ڵ������(min~max)double����
	public static double getRandomDouble(double min, double max) {
		return Math.random()*(max-min)+min;
	}
	//�������ļ���д������
	public static void write(Plugin p, String fileName, YamlConfiguration y, String path, Object value) {
		y.set(path, value);
		try {
			y.save(new File(p.getDataFolder(), fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//���ļ���׷�Ӷ���
	public static void write(File f, String... msg) throws IOException {
		if (!f.exists()) f.createNewFile();
		FileWriter fw = new FileWriter(f, true);
		BufferedWriter bw = new BufferedWriter(fw, 1024);
		for (String ms: msg) {
			bw.write(ms);
			bw.write("\n");
		}
		bw.close();
	}
	//�����̶ֹ���0-15(�������ɿ���)
	public static int get0to15(int num) {
		while (num<0||num>15) {
			if (num<0) num+=16;
			if (num>15) num-=16;
		}
		return num;
	}
	//ð������(��С)
	public static void sort(List<Integer> list) {
		for (int i = 0; i < list.size(); i++) {
			for (int j = 0; j < list.size() - 1; j++) {
				if (list.get(j) < list.get(j + 1)) {
					int t = list.get(j);
					list.set(j, list.get(j + 1));
					list.set(j + 1, t);
				}
			}
		}
	}
	
	/**
	 * ��ȡ��Ӧ��ҵ�ͷ­
	 * @param player ��ҵ�UUID
	 * @return ����ҵ�ͷ­
	 */
	@NotNull
	public static ItemStack getPlayerHead(@NotNull UUID player) {
		ItemStack item = new ItemStack(Material.PLAYER_HEAD);
		SkullMeta meta = (SkullMeta) item.getItemMeta();
		meta.setOwningPlayer(Bukkit.getOfflinePlayer(player));
		item.setItemMeta(meta);
		return item;
	}
}
