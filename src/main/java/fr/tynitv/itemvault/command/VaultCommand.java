package fr.tynitv.itemvault.command;

import fr.tynitv.itemvault.ItemVault;
import fr.tynitv.itemvault.vault.VaultManager;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class VaultCommand implements CommandExecutor {

    private final ItemVault plugin;
    private final VaultManager vaultManager;
    private final MiniMessage mm = MiniMessage.miniMessage();

    public VaultCommand(ItemVault plugin, VaultManager vaultManager) {
        this.plugin = plugin;
        this.vaultManager = vaultManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("itemvault.admin")) {
                sender.sendMessage(mm.deserialize("<red>Permission insuffisante.</red>"));
                return true;
            }
            plugin.reloadConfig();
            sender.sendMessage(mm.deserialize("<gradient:#F857A6:#FF5858><bold>ItemVault</bold></gradient> <gray>»</gray> <green>Configuration rechargée.</green>"));
            return true;
        }

        if (args.length > 0 && args[0].equalsIgnoreCase("admin")) {
            if (!sender.hasPermission("itemvault.admin")) {
                sender.sendMessage(mm.deserialize("<red>Permission insuffisante.</red>"));
                return true;
            }
            if (!(sender instanceof Player adminPlayer)) {
                sender.sendMessage("Commande reservée aux joueurs.");
                return true;
            }
            if (args.length < 2) {
                sender.sendMessage(mm.deserialize("<red>Utilisation : /pv admin <joueur> [numéro]</red>"));
                return true;
            }
            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                sender.sendMessage(mm.deserialize("<red>Joueur introuvable.</red>"));
                return true;
            }
            int vaultNum = 1;
            if (args.length >= 3) {
                try { vaultNum = Integer.parseInt(args[2]); } catch (Exception ignored) {}
            }
            vaultManager.openTargetVault(adminPlayer, target.getUniqueId(), target.getName(), vaultNum);
            return true;
        }

        if (!(sender instanceof Player player)) {
            sender.sendMessage("Commande reservée aux joueurs.");
            return true;
        }

        if (!player.hasPermission("itemvault.use")) {
            player.sendMessage(mm.deserialize("<red>Permission insuffisante.</red>"));
            return true;
        }

        int vaultNum = 1;
        if (args.length > 0) {
            try {
                vaultNum = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                vaultNum = 1;
            }
        }

        if (vaultNum > 1 && !player.hasPermission("itemvault.vault." + vaultNum) && !player.hasPermission("itemvault.admin")) {
            player.sendMessage(mm.deserialize("<red>Vous n'avez pas accès au coffre #" + vaultNum + ".</red>"));
            return true;
        }

        vaultManager.openVault(player, vaultNum);
        return true;
    }
}
