package fr.tynitv.itemvault.vault;

import fr.tynitv.itemvault.ItemVault;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class VaultManager {

    private final ItemVault plugin;
    private final Map<String, Inventory> vaults = new ConcurrentHashMap<>();
    private final MiniMessage mm = MiniMessage.miniMessage();

    public VaultManager(ItemVault plugin) {
        this.plugin = plugin;
    }

    public void openVault(Player player, int vaultNumber) {
        openTargetVault(player, player.getUniqueId(), player.getName(), vaultNumber);
    }

    public void openTargetVault(Player viewer, UUID ownerUuid, String ownerName, int vaultNumber) {
        String key = ownerUuid.toString() + "_" + vaultNumber;
        int rows = plugin.getConfig().getInt("default-rows", 6);
        int size = Math.min(54, Math.max(9, rows * 9));

        String rawTitle = plugin.getConfig().getString("messages.title", "<gradient:#F857A6:#FF5858><bold>Coffre Virtuel #{number}</bold></gradient>")
                .replace("{number}", String.valueOf(vaultNumber))
                .replace("{player}", ownerName);

        Inventory inv = vaults.computeIfAbsent(key, k -> Bukkit.createInventory(null, size, mm.deserialize(rawTitle)));

        String prefix = plugin.getConfig().getString("messages.prefix", "<gradient:#F857A6:#FF5858><bold>ItemVault</bold></gradient> <gray>»</gray> ");
        viewer.sendMessage(mm.deserialize(prefix + "<gray>Ouverture du coffre <white>#" + vaultNumber + "</white> de <gold>" + ownerName + "</gold>...</gray>"));

        viewer.playSound(viewer.getLocation(), Sound.BLOCK_CHEST_OPEN, 1.0f, 1.0f);
        viewer.openInventory(inv);
    }
}
