package fr.rgld_.merge.Events;

import fr.rgld_.merge.Merge;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.api.ChatColor;

import java.util.UUID;

public class PingEvent implements Listener {

    private final Merge merge;

    public PingEvent(Merge merge) {
        this.merge = merge;
    }

    @EventHandler
    public void onProxyPing(ProxyPingEvent e){
        ServerPing sp = e.getResponse();
        sp.getPlayers().setOnline(merge.getMerged());
        sp.getPlayers().setSample(new ServerPing.PlayerInfo[] {
                line("&6&lNombre de joueur:"),
                line("  &6- Khraal:  &e" + merge.getKhra()),
                line("  &6- Nemerya: &e" + merge.getNeme()),
        });
    }

    private ServerPing.PlayerInfo line(String str) {
        return new ServerPing.PlayerInfo(ChatColor.translateAlternateColorCodes('&', str), UUID.randomUUID().toString());
    }

}
