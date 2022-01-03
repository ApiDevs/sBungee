package rip.api.handlers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import rip.api.sBungee;

public class BungeeHandler {
    private boolean whitelisted;

    private List whitelists = sBungee.getInstance().getConfig().getStringList("whitelists");

    private Map prevServer = new HashMap<>();

    public boolean isWhitelisted(String uuid) {
        String uuids;
        Iterator<String> var2 = this.whitelists.iterator();
        do {
            if (!var2.hasNext())
                return false;
            uuids = var2.next();
        } while (!uuids.equals(uuid));
        return true;
    }

    public void setWhitelisted(String uuid, boolean bool) {
        if (bool) {
            if (!isWhitelisted(uuid))
                this.whitelists.add(uuid);
        } else if (isWhitelisted(uuid)) {
            this.whitelists.remove(uuid);
        }
    }

    public boolean isWhitelisted() {
        return this.whitelisted;
    }

    public void setWhitelisted(boolean whitelisted) {
        this.whitelisted = whitelisted;
    }

    public List getWhitelists() {
        return this.whitelists;
    }
}
