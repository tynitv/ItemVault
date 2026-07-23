package fr.tynitv.itemvault.vault;

import fr.tynitv.itemvault.ItemVault;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class VaultManager {

    private final ItemVault plugin;
    private final Map<UUID, Inventory> playerVaults = new HashMap<>();
    private final MiniMessage mm = MiniMessage.miniMessage();

    public VaultManager(ItemVault plugin) {
        this.plugin = plugin;
    }

    public void openVault(Player player) {
        UUID uuid = player.getUniqueId();
        int rows = plugin.getConfig().getInt("default-rows", 6);
        String title = plugin.getConfig().getString("messages.title", "Coffre Virtuel");

        Inventory inv = playerVaults.computeIfAbsent(uuid, k -> Bukkit.createInventory(player, rows * 9, mm.deserialize(title)));

        String prefix = plugin.getConfig().getString("messages.prefix", "");
        player.sendMessage(mm.deserialize(plugin.getConfig().getString("messages.opened", "").replace("<prefix>", prefix)));
        player.openInventory(inv);
    }
}
