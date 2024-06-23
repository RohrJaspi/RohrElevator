package dev.rohrjaspi.elevator;


import dev.rohrjaspi.elevator.listeners.ElevatorListener;
import dev.rohrjaspi.elevator.util.S;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private NamespacedKey elevatorKey;

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getConsoleSender().sendMessage("§7======================================");
        Bukkit.getConsoleSender().sendMessage(S.prefix + "§cStatus: §aenabled");
        Bukkit.getConsoleSender().sendMessage(S.prefix + "§cVersion: §6" + this.getDescription().getVersion());
        Bukkit.getConsoleSender().sendMessage(S.prefix + "§cDeveloper: §6" + this.getDescription().getAuthors());
        Bukkit.getConsoleSender().sendMessage("§7======================================");

        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new ElevatorListener(), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getConsoleSender().sendMessage("§7======================================");
        Bukkit.getConsoleSender().sendMessage(S.prefix + "§cStatus: §cdisabled");
        Bukkit.getConsoleSender().sendMessage(S.prefix + "§cVersion: §6" + this.getDescription().getVersion());
        Bukkit.getConsoleSender().sendMessage(S.prefix + "§cDeveloper: §6" + this.getDescription().getAuthors());
        Bukkit.getConsoleSender().sendMessage("§7======================================");
    }
}
