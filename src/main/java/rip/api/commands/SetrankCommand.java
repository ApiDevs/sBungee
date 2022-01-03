package rip.api.commands;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ProxyReloadEvent;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Event;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import rip.api.sBungee;
import rip.api.util.CC;

public class SetrankCommand extends Command {
    public SetrankCommand() {
        super("setrank", "sbungee.setrank", new String[] { "bungee rank" });
    }

    public void execute(CommandSender commandSender, String[] args) {
        if (commandSender instanceof ProxiedPlayer)
            if (commandSender.hasPermission("sbungee.setrank")) {
                if (args.length == 2) {
                    ProxiedPlayer player = sBungee.getInstance().getProxy().getPlayer(args[0]);
                    if (player != null) {
                        player.removeGroups((String[])player.getGroups().toArray((Object[])new String[player.getGroups().size()]));
                        player.addGroups(new String[] { args[1] });
                        File file = new File(sBungee.getInstance().getDataFolder().getParentFile().getParent(), "config.yml");
                        try {
                            Configuration config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
                            for (String group : player.getGroups())
                                config.getStringList("groups." + player.getName()).remove(group);
                            player.disconnect(CC.translate("&cYour rank was set to &f" + args[1]));
                            config.getStringList("groups." + player.getName()).add(args[1]);
                            config.set("groups." + player.getName(), Arrays.asList(new String[] { args[1] }));
                            ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, file);
                            (BungeeCord.getInstance()).config.load();
                            BungeeCord.getInstance().stopListeners();
                            BungeeCord.getInstance().startListeners();
                            BungeeCord.getInstance().getPluginManager().callEvent((Event)new ProxyReloadEvent(commandSender));
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        sBungee.getInstance().getLogger().info(CC.translate(commandSender.getName() + " has set " + player.getName() + "'s rank to " + args[1] + "."));
                    } else {
                        commandSender.sendMessage(CC.translate("&cThat player is not online."));
                    }
                } else {
                    commandSender.sendMessage(CC.translate("&cUsage: /setrank <player> <rank>"));
                }
            } else {
                commandSender.sendMessage("&cNo permission.");
            }
    }
}
