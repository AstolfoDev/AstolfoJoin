package github.io.AstolfoDev.AstolfoJoin;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class Main extends JavaPlugin implements Listener {

    private File ConfigFile;
    private FileConfiguration Config;

    @Override
    public void onEnable() {
        createConfig();
        Bukkit.getPluginManager().registerEvents(this, this);
        getLogger().info("Plugin enabled!");
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (this.getConfig().getString("enable-joinleave").toLowerCase() == "true") {
            String joinMessage = this.getConfig().getString("join-message").replace("{playerName}", player.getDisplayName());
            event.setJoinMessage(joinMessage);
        }
        if (this.getConfig().getString("enable-welcome").toLowerCase() == "true") {
            String welcomeMessage = this.getConfig().getString("welcome-message").replace("{playerName}", player.getDisplayName());
            player.sendMessage(welcomeMessage);
        }
    }

    @EventHandler
    public void onPlayerQuitEvent (PlayerQuitEvent event) {
        if (this.getConfig().getString("enable-joinleave").toLowerCase() == "true") {
            Player player = event.getPlayer();
            String leaveMessage = this.getConfig().getString("leave-message").replace("{playerName}", player.getDisplayName());
            event.setQuitMessage(leaveMessage);
        }
    }

    private void createConfig() {
        ConfigFile = new File(getDataFolder(), "config.yml");
        if (!ConfigFile.exists()) {
            ConfigFile.getParentFile().mkdirs();
            saveResource("config.yml", false);
        }
        Config = new YamlConfiguration();
        try {
            Config.load(ConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
}
