package rip.api.commands;

import net.md_5.bungee.api.plugin.*;
import net.md_5.bungee.api.*;
import net.md_5.bungee.api.connection.*;
import rip.api.*;
import rip.api.util.*;
import rip.api.sBungee;
import rip.api.util.MojangUtils;
import java.lang.String;

import java.util.*;

public class WhitelistCommand extends Command {
    public WhitelistCommand() {
        super("bwhitelist");
    }

    public void execute(CommandSender commandSender, String[] args) {
        if (commandSender instanceof ProxiedPlayer) {
            ProxiedPlayer p = (ProxiedPlayer)commandSender;
            if (!p.hasPermission("sbungee.whitelist")) {
                p.sendMessage("permission.");
                return;
            }
            if (args.length < 1) {
                p.sendMessage("/whitelist <on:off:add/remove name>");
                return;
            }
            if (args[0].equalsIgnoreCase("on")) {
                p.sendMessage("network is now whitelisted.");
                sBungee.getInstance().getBungeeHandler().setWhitelisted(true);
                return;
            }
            if (args[0].equalsIgnoreCase("off")) {
                p.sendMessage("network is no longer whitelisted.");
                sBungee.getInstance().getBungeeHandler().setWhitelisted(false);
                return;
            }
            if (args[0].equalsIgnoreCase("list")) {
                p.sendMessage("");
                p.sendMessage("players:");
                String uuid = null;
                Iterator<String> var5 = sBungee.getInstance().getBungeeHandler().getWhitelists().iterator();
                while (var5.hasNext()) {
                    uuid = var5.next();
                    try {
                        p.sendMessage(" + MojangUtils.fetchName(UUID.fromString(uuid)))");
                    } catch (Exception var8) {
                        uuid = "went wrong while loading the whitelist.";
                    }
                }
                if (uuid != null) {
                    p.sendMessage("");
                    p.sendMessage(uuid);
                }
                p.sendMessage("");
                return;
            }
            try {
                if (args.length >= 2) {
                    if (args[0].equalsIgnoreCase("add")) {
                        String uuid = args[1];
                        sBungee.getInstance().getBungeeHandler().setWhitelisted(MojangUtils.fetchUUID(uuid).toString(), true);
                        p.sendMessage("succesfully added + uuid +  the whitelist!");
                        return;
                    }
                    if (args[0].equalsIgnoreCase("remove")) {
                        String uuid = args[1];
                        sBungee.getInstance().getBungeeHandler().setWhitelisted(MojangUtils.fetchUUID(uuid).toString(), false);
                        p.sendMessage("succesfully removed + uuid +  the whitelist!");
                        return;
                    }
                }
            } catch (Exception exception) {}
            p.sendMessage("/bwhitelist <on:off:list:add/remove name>");
        }
    }
}
