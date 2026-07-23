package fr.tynitv.itemvault.command;

import fr.tynitv.itemvault.ItemVault;
import fr.tynitv.itemvault.vault.VaultManager;
import net.kyori.adventure.text.minimessage.MiniMessage;
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
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Commande reservée aux joueurs.");
            return true;
        }

        if (!player.hasPermission("itemvault.use")) {
            player.sendMessage(mm.deserialize("<red>Permission insuffisante.</red>"));
            return true;
        }

        vaultManager.openVault(player);
        return true;
    }
}
