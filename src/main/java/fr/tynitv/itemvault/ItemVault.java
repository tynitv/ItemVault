package fr.tynitv.itemvault;

import fr.tynitv.itemvault.command.VaultCommand;
import fr.tynitv.itemvault.command.VaultTabCompleter;
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
            getCommand("pv").setTabCompleter(new VaultTabCompleter());
        }

        getLogger().info("ItemVault v1.1.0 enabled with Multi-Vaults & TabCompleter!");
    }

    @Override
    public void onDisable() {
        getLogger().info("ItemVault disabled!");
    }

    public static ItemVault getInstance() {
        return instance;
    }

    public VaultManager getVaultManager() {
        return vaultManager;
    }
}
