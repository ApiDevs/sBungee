package rip.api.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import rip.api.sBungee;
import rip.api.util.CC;
import rip.api.util.ComponentBuilderUtils;

public class FindCommand extends Command {
    public FindCommand() {
        super("find", "sbungee.find", new String[] { "findplayer", "find player", "look", "lookup", "where", "whereis" });
    }

    public void execute(CommandSender commandSender, String[] args) {
        if (args.length == 0) {
            commandSender.sendMessage(CC.translate("&cUsage: /find <player>"));
            return;
        }
        ProxiedPlayer player = sBungee.getInstance().getProxy().getPlayer(args[0]);
        if (player == null) {
            commandSender.sendMessage(CC.translate("&c" + args[0] + " is currently not on the network."));
            return;
        }
        if (commandSender.hasPermission("sbungee.find.admin")) {
            commandSender.sendMessage(ComponentBuilderUtils.buildHighlightedTextComponentWithClick(CC.translate("&9" + player.getName() + " &eis currently on &9" + player.getServer().getInfo().getName() + "&e."), CC.translate("&9Click to show ip and details."), "https://whatismyipaddress.com/ip/" + player.getAddress().getHostName()));
        } else {
            commandSender.sendMessage(CC.translate("&9" + player.getName() + " &eis currently on &9" + player.getServer().getInfo().getName() + "&e."));
        }
    }
}
