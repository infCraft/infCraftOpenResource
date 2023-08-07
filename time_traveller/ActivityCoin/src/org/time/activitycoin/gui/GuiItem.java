package org.time.activitycoin.gui;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.time.activitycoin.utils.Utils;

public class GuiItem {
	
	private String id;
	private ItemStack item;
	private int price;
	private List<String> commands;
	
	/**
	 * ����GUI�ڵ���Ʒ
	 * @param id ΨһID
	 * @param item չʾ��Ʒ
	 * @param price �۸񣬼����ĵĻ����
	 * @param commands ִ�е�ָ��
	 */
	public GuiItem(String id, ItemStack item, int price, List<String> commands) {
		this.id = id;
		this.item = item;
		this.price = price;
		this.commands = commands;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public ItemStack getItem() {
		return item;
	}
	public void setItem(ItemStack item) {
		this.item = item;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public List<String> getCommands() {
		return commands;
	}
	public void setCommands(List<String> commands) {
		this.commands = commands;
	}
	/**
	 * �Կ���̨���ִ��ָ��
	 * @param player ���GUI���������
	 */
	public void runCommands(String player) {
		for (int i=0;i<commands.size();i++) Bukkit.dispatchCommand(Bukkit.getConsoleSender(), Utils.replaceAll(commands.get(i)).replaceAll("%player%", player));
	}
}
