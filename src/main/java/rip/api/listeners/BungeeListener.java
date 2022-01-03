package rip.api.listeners;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.event.ServerKickEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;
import rip.api.sBungee;
import rip.api.util.CC;

public class BungeeListener implements Listener {
    private static int protocol;

    private static String name;

    public static int getProtocol() {
        return protocol;
    }

    public static void setProtocol(int protocol) {
        BungeeListener.protocol = protocol;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        BungeeListener.name = name;
    }

    @EventHandler
    public void onKick(ServerKickEvent e) {
        ProxiedPlayer p = e.getPlayer();
        Random random = new Random();
        int rand = random.nextInt(2) + 1;
        ServerInfo connect = BungeeCord.getInstance().getServerInfo("Hub-0" + rand);
        if (e.getKickedFrom() == connect) {
            p.disconnect(CC.translate("&cKicked from &6" + e.getKickedFrom().getName() + "&c: &e" + e.getKickReason()));
            return;
        }
        e.setCancelServer(connect);
        e.setCancelled(true);
        BungeeCord.getInstance().getScheduler().schedule((Plugin)sBungee.getInstance(), () -> p.sendMessage(CC.translate("&cKicked from &6" + e.getKickedFrom().getName() + "&c: &e" + e.getKickReason())), 2L, TimeUnit.SECONDS);
    }

    @EventHandler
    public void onJoin(LoginEvent e) {
        if (sBungee.getInstance().getConfig().getBoolean("Whitelisted") &&
                !sBungee.getInstance().getBungeeHandler().isWhitelisted(e.getConnection().getUniqueId().toString())) {
            e.setCancelled(true);
            e.setCancelReason(CC.translate("&cThe network is currently whitelisted.\n&cAdditional info may be found at discord.gg/safepvp"));
        }
    }

    @EventHandler
    public void onProxy(ProxyPingEvent e) {
        e.getResponse().setVersion(new ServerPing.Protocol("", e.getResponse().getVersion().getProtocol()));
        e.setResponse(e.getResponse());
    }

    @EventHandler(priority = 64)
    public void onPing2(ProxyPingEvent e) {
        if (sBungee.getInstance().getConfig().getBoolean("Whitelisted")) {
                    e.setResponse(e.getResponse());
        }
    }
}