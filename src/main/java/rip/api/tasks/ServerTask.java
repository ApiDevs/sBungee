package rip.api.tasks;

import java.util.HashMap;
import java.util.Map;
import net.md_5.bungee.api.Callback;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import rip.api.sBungee;
import rip.api.util.CC;

public class ServerTask implements Runnable {
    private Map<ServerInfo, Boolean> servers = new HashMap<>();

    public ServerTask() {
        for (ServerInfo serverInfo : sBungee.getInstance().getProxy().getServers().values())
            this.servers.put(serverInfo, Boolean.valueOf(false));
    }

    public void run() {
        for (ServerInfo serverInfo : sBungee.getInstance().getProxy().getServers().values()) {
            serverInfo.ping(new Callback<ServerPing>() {
                public void done(ServerPing sp, Throwable ex) {
                    boolean online = (ex == null);
                    if (!ServerTask.this.servers.containsKey(serverInfo)) {
                        ServerTask.this.servers.put(serverInfo, Boolean.valueOf(online));
                    } else if (((Boolean)ServerTask.this.servers.get(serverInfo)).booleanValue() != online) {
                        for (ProxiedPlayer p : sBungee.getInstance().getProxy().getPlayers()) {
                            if (p.hasPermission("sbungee.servertask"))
                                p.sendMessage(online ? CC.translate("&8[&eServer Monitor&8] &fAdding server " + serverInfo.getName() + "...") : CC.translate("&8[&eServer Monitor&8] &fRemoving server " + serverInfo.getName() + "..."));
                        }
                        ServerTask.this.servers.put(serverInfo, Boolean.valueOf(online));
                    }
                }
            });
        }
    }
}
