package fr.tynitv.itemvault;

import fr.tynitv.itemvault.command.VaultCommand;
import fr.tynitv.itemvault.vault.VaultManager;
import org.bukkit.plugin.java.JavaPlugin;

public class ItemVault extends JavaPlugin {

    private static ItemVault instance;
    private VaultManager vaultManager;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        this.vaultManager = new VaultManager(this);

        if (getCommand("pv") != null) {
            getCommand("pv").setExecutor(new VaultCommand(this, vaultManager));
        }

        getLogger().info("ItemVault v1.0.0 enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("ItemVault disabled!");
    }

    public static ItemVault getInstance() {
        return instance;
    }
}
