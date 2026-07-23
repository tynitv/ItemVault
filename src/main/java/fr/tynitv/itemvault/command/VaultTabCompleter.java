package fr.tynitv.itemvault.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class VaultTabCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            List<String> nums = new ArrayList<>(Arrays.asList("1", "2", "3", "4", "5"));
            if (sender.hasPermission("itemvault.admin")) {
                nums.add("admin");
                nums.add("reload");
            }
            return filter(nums, args[0]);
        }

        if (args.length == 2 && args[0].equalsIgnoreCase("admin") && sender.hasPermission("itemvault.admin")) {
            List<String> players = Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
            return filter(players, args[1]);
        }

        if (args.length == 3 && args[0].equalsIgnoreCase("admin") && sender.hasPermission("itemvault.admin")) {
            return filter(Arrays.asList("1", "2", "3", "4", "5"), args[2]);
        }

        return completions;
    }

    private List<String> filter(List<String> options, String prefix) {
        String lower = prefix.toLowerCase();
        return options.stream().filter(s -> s.toLowerCase().startsWith(lower)).collect(Collectors.toList());
    }
}
