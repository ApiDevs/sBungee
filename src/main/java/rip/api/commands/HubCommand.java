package rip.api.commands;

import java.util.Random;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import rip.api.util.CC;

public class HubCommand extends Command {

    public HubCommand() {
        super("hub");
    }

    public void execute(CommandSender commandSender, String[] args) {
        if (commandSender instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer)commandSender;
            if (args.length == 0) {
                Random random = new Random();
                int attachment = random.nextInt(2) + 1;
                player.sendMessage(CC.translate("&6Sending you to the Hub-0" + attachment + "..."));
                ServerInfo serverInfo = ProxyServer.getInstance().getServerInfo("Hub-0" + attachment);
                player.connect(serverInfo);
            } else if (args.length == 1) {
                try {
                    Integer.parseInt(args[0]);
                } catch (NumberFormatException e) {
                    player.sendMessage(CC.translate("&cA hub with that name cannot be found."));
                    return;
                }
                player.sendMessage(CC.translate("&6Sending you to the Hub-0") + args[0] + "...");
                ServerInfo serverInfo = ProxyServer.getInstance().getServerInfo("Hub-0" + args[0]);
                player.connect(serverInfo);
            }
        }
    }
}
