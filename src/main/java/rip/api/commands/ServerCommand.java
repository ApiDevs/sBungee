package rip.api.commands;


import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import rip.api.sBungee;
import rip.api.util.CC;

public class ServerCommand extends Command {
    public ServerCommand() {
        super("server", "sbungee.server", new String[] { "connect" });
    }

    public void execute(CommandSender commandSender, String[] args) {
        if (commandSender instanceof ProxiedPlayer) {
            ProxiedPlayer p = (ProxiedPlayer)commandSender;
            if (p.hasPermission("sbungee.server")) {
                if (args.length == 0) {
                    p.sendMessage(CC.translate("&6You are currently connected to &f" + p.getServer().getInfo().getName() + "&6."));
                    StringBuilder servers = new StringBuilder("&6Servers: &f");
                    boolean first = true;
                    for (String name : sBungee.getInstance().getProxy().getServers().keySet()) {
                        if (first) {
                            servers.append(name);
                        } else {
                            servers.append("&7, &f").append(name);
                        }
                        first = false;
                    }
                    p.sendMessage(CC.translate(servers.toString()));
                    p.sendMessage(CC.translate("&6Connect to a server with &e/server <name>"));
                } else if (args.length == 1) {
                    ServerInfo si = sBungee.getInstance().getProxy().getServerInfo(args[0]);
                    if (si != null) {
                        p.sendMessage(CC.translate("&6Connecting you to &f" + si.getName() + "&6!"));
                        p.connect(si);
                    } else {
                        p.sendMessage(CC.translate("&cNo Permission"));
                    }
                }
            } else {
                p.sendMessage(CC.translate("&cNo permission."));
            }
        }
    }
}
