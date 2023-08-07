package org.time.activitycoin;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.time.activitycoin.utils.Utils;

import com.google.common.collect.Lists;

public class Commands implements TabExecutor {

	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd,
			@NotNull String label, @NotNull String[] args) {
		if (args.length == 1) {
			if (!sender.isOp()) return StringUtil.copyPartialMatches(args[0], Lists.newArrayList("help", "shop"), Lists.newArrayList());
			return StringUtil.copyPartialMatches(args[0], Lists.newArrayList("help", "shop", "give", "take", "set", "see", "reload"), Lists.newArrayList());
		}
		return null;
	}

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label,
			@NotNull String[] args) {
		if (cmd.getName().equalsIgnoreCase("acoin")) {
			if (args.length == 0||(args.length == 1&&args[0].equalsIgnoreCase("help"))) {
				Utils.sendHelp(sender, sender.isOp());
				return true;
			}
			if (args.length == 1&&args[0].equalsIgnoreCase("shop")) {
				Player p = (Player) sender;
				p.openInventory(Utils.buildPreset(0, p.getUniqueId()));
				return true;
			}
			if (args.length == 1&&args[0].equalsIgnoreCase("reload")) {
				if (!sender.isOp()) {
					sender.sendMessage(ActivityCoin.prefix+"��c�㲻��OP��");
					return true;
				}
				ActivityCoin.reload();
				sender.sendMessage(ActivityCoin.prefix+"��a���سɹ���");
				return true;
			}
			if (args.length == 2&&args[0].equalsIgnoreCase("see")) {
				if (!sender.isOp()) {
					sender.sendMessage(ActivityCoin.prefix+"��c�㲻��OP��");
					return true;
				}
				if (!Cache.data.containsKey(args[1])) {
					sender.sendMessage(ActivityCoin.prefix+"��cû�и���ҵ���Ϣ��");
					return true;
				}
				sender.sendMessage(ActivityCoin.prefix+"��f��� ��6"+args[1]+" ��fӵ�� ��e"+Cache.data.get(args[1])+" ��f�������.");
				return true;
			}
			if (args.length == 3&&args[0].equalsIgnoreCase("give")) {
				if (!sender.isOp()) {
					sender.sendMessage(ActivityCoin.prefix+"��c�㲻��OP��");
					return true;
				}
				OfflinePlayer p = Bukkit.getOfflinePlayer(args[1]);
				if (p == null) {
					sender.sendMessage(ActivityCoin.prefix+"��c����Ҳ����ڣ�");
					return true;
				}
				// �µĻ��������
				int give = Integer.parseInt(args[2])+Cache.data.get(args[1]);
				Cache.data.replace(args[1], give);
				sender.sendMessage(ActivityCoin.prefix+"��a�����ɹ������ ��6"+ p.getName()+ " ��a����ӵ�� ��e"+give+" ��a�������.");
				if (p.isOnline()) ((Player) p).sendMessage(ActivityCoin.prefix+"��f������ӵ�� ��e"+give+" ��f�������.");
				return true;
			}
			if (args.length == 3&&args[0].equalsIgnoreCase("take")) {
				if (!sender.isOp()) {
					sender.sendMessage(ActivityCoin.prefix+"��c�㲻��OP��");
					return true;
				}
				OfflinePlayer p = Bukkit.getOfflinePlayer(args[1]);
				if (p == null) {
					sender.sendMessage(ActivityCoin.prefix+"��c����Ҳ����ڣ�");
					return true;
				}
				// �µĻ��������
				int take = Cache.data.get(args[1])-Integer.parseInt(args[2]);
				if (take<0) take = 0;
				Cache.data.replace(args[1], take);
				sender.sendMessage(ActivityCoin.prefix+"��a�����ɹ������ ��6"+ p.getName()+ " ��a����ӵ�� ��e"+take+" ��a�������.");
				if (p.isOnline()) ((Player) p).sendMessage(ActivityCoin.prefix+"��f������ӵ�� ��e"+take+" ��f�������.");
				return true;
			}
			if (args.length == 3&&args[0].equalsIgnoreCase("set")) {
				if (!sender.isOp()) {
					sender.sendMessage(ActivityCoin.prefix+"��c�㲻��OP��");
					return true;
				}
				OfflinePlayer p = Bukkit.getOfflinePlayer(args[1]);
				if (p == null) {
					sender.sendMessage(ActivityCoin.prefix+"��c����Ҳ����ڣ�");
					return true;
				}
				// �µĻ��������
				int set = Integer.parseInt(args[2]);
				if (set<0) set = 0;
				Cache.data.replace(args[1], set);
				sender.sendMessage(ActivityCoin.prefix+"��a�����ɹ������ ��6"+ p.getName()+ " ��a����ӵ�� ��e"+set+" ��a�������.");
				if (p.isOnline()) ((Player) p).sendMessage(ActivityCoin.prefix+"��f������ӵ�� ��e"+set+" ��f�������.");
				return true;
			}
		}
		return false;
	}

}
