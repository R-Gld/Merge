package fr.rgld_.merge;

import fr.rgld_.merge.Events.PingEvent;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginDescription;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.text.MessageFormat;
import static net.md_5.bungee.api.ChatColor.*;

public final class Merge extends Plugin {
    private TCPServer srv;

    private int neme = 0;
    public int getNeme(){
        return neme;
    }
    private int khra = 0;
    public int getKhra(){
        return khra;
    }
    private boolean running = true;

    public int getMerged(){
        return neme + khra;
    }

    @Override
    public void onEnable() {
        CommandSender ccs = getProxy().getConsole();
        PluginDescription pdf = getDescription();
        ccs.sendMessage(MessageFormat.format("{0}--- {1} ---", GOLD, pdf.getName()));
        ccs.sendMessage(" ");

        try {
            new EventManager(this).register();
            ccs.sendMessage(RED + "The loading of the events is a success!");
        } catch(Throwable t) {
            ccs.sendMessage(RED + "The loading of the events has failed.");
            t.printStackTrace();
        }

        try {
            srv = new TCPServer(this, new Server(4574));
            new Thread(srv).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Thread(() -> {
            while(running){
                this.neme = getPlayers("play.nemerya.fr");
                this.khra = getPlayers("play.khraal.fr");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        ccs.sendMessage(" ");
        ccs.sendMessage(MessageFormat.format("{0}--- {1} ---", GOLD, pdf.getName()));
    }

    @Override
    public void onDisable() {
        srv.stop();
        running = false;
    }

    private int getPlayers(String address){
        try{
            Socket socket = new Socket(address, 4574);
            socket.setSoTimeout(2500);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            return Integer.parseInt(in.readLine());
        } catch(Throwable t){
            t.printStackTrace();
        }
        return 0;
    }

    private class EventManager {
        final Merge merge;

        EventManager(Merge merge) {
            this.merge = merge;
        }

        void register() {
            a(new PingEvent(merge));
        }

        private void a(Listener listener) {
            getProxy().getPluginManager().registerListener(merge, listener);
        }
    }
}
