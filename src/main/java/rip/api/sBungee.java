package rip.api;

import com.google.common.io.ByteStreams;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;
import net.md_5.bungee.api.Favicon;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.scheduler.ScheduledTask;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import rip.api.commands.FindCommand;
import rip.api.commands.HubCommand;
import rip.api.commands.ServerCommand;
import rip.api.commands.SetrankCommand;
import rip.api.commands.WhitelistCommand;
import rip.api.handlers.BungeeHandler;
import rip.api.listeners.BungeeListener;
import rip.api.tasks.ServerTask;

public class sBungee extends Plugin {
    private List staff;

    private Map prevServer = new HashMap<>();

    private static sBungee instance;

    private Configuration config;

    private BungeeHandler bungeeHandler;

    public static sBungee getInstance() {
        return instance;
    }

    public Configuration getConfig() {
        return this.config;
    }

    public BungeeHandler getBungeeHandler() {
        return this.bungeeHandler;
    }

    private double populationModifier = 1.25D;

    private Favicon favicon;

    private ScheduledTask scheduledTask;

    public double getPopulationModifier() {
        return this.populationModifier;
    }

    public void setPopulationModifier(double populationModifier) {
        this.populationModifier = populationModifier;
    }

    public Favicon getFavicon() {
        return this.favicon;
    }

    public ScheduledTask getScheduledTask() {
        return this.scheduledTask;
    }

    public void onEnable() {
        instance = this;
        setupCommands();
        setupListeners();
        setupConfig();
        setupHandlers();
        this.scheduledTask = getProxy().getScheduler().schedule(this, (Runnable)new ServerTask(), 0L, 10L, TimeUnit.SECONDS);
    }

    public void onDisable() {
        getInstance().getScheduledTask().cancel();
        this.config.set("whitelists", this.bungeeHandler.getWhitelists());
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(this.config, new File(getDataFolder(), "config.yml"));
        } catch (IOException var2) {
            var2.printStackTrace();
        }
    }

    public void setupCommands() {
        getProxy().getPluginManager().registerCommand(getInstance(), (Command)new ServerCommand());
        getProxy().getPluginManager().registerCommand(getInstance(), (Command)new HubCommand());
        getProxy().getPluginManager().registerCommand(getInstance(), (Command)new WhitelistCommand());
        getProxy().getPluginManager().registerCommand(getInstance(), (Command)new FindCommand());
        getProxy().getPluginManager().registerCommand(getInstance(), (Command)new SetrankCommand());
    }

    public void setupListeners() {
        getProxy().getPluginManager().registerListener(getInstance(), (Listener)new BungeeListener());
    }

    public void setupHandlers() {
        this.bungeeHandler = new BungeeHandler();
    }

    private void setupConfig() {
        File configFile = new File(getDataFolder(), "config.yml");
        try {
            this.config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!getDataFolder().exists())
            getDataFolder().mkdir();
        if (!configFile.exists())
            try {
                configFile.createNewFile();
                try(InputStream is = getResourceAsStream("config.yml");
                    OutputStream os = new FileOutputStream(configFile)) {
                    ByteStreams.copy(is, os);
                }
            } catch (IOException e) {
                throw new RuntimeException("Unable to create configuration file", e);
            }
    }

    public void reloadConfig() {
        try {
            this.config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(getDataFolder(), "config.yml"));
        } catch (IOException e) {
            throw new RuntimeException("Unable to load configuration", e);
        }
        File file = new File(getDataFolder(), "server-icon.png");
        if (file.exists())
            try {
                BufferedImage bufferedImage = ImageIO.read(file);
                this.favicon = Favicon.create(bufferedImage);
            } catch (IOException e2) {
                getLogger().warning("Favicon file is invalid or missing.");
            }
    }

    public void saveConfig() {
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(getConfig(), new File(getDataFolder(), "config.yml"));
        } catch (IOException e) {
            throw new RuntimeException("Unable to save configuration", e);
        }
    }

    public List getStaff() {
        return this.staff;
    }

    public void whitelist(boolean enabled) {
        getInstance().getConfig().set("Whitelisted", Boolean.valueOf(enabled));
        getInstance().saveConfig();
        getInstance().reloadConfig();
    }

    private void getId() {
    }
}
