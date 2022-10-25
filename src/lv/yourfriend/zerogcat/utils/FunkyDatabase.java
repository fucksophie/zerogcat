package lv.yourfriend.zerogcat.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageHistory;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.session.ReadyEvent;

public class FunkyDatabase {
    public Map<String, String> data;
    public Long databaseChannel;

    public FunkyDatabase(Long databaseChannel) {
        this.data = new HashMap<String, String>();
        this.databaseChannel = databaseChannel;
    }

    public String save() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(this.data);
            return new String(Base64.getEncoder().encodeToString(baos.toByteArray()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    public void start(ReadyEvent event) {
        TextChannel ch = event.getJDA().getChannelById(TextChannel.class, databaseChannel);
        MessageHistory history = MessageHistory.getHistoryFromBeginning(ch).complete();
        Message databaseMessage;

        if (history.size() == 0) {
            databaseMessage = ch.sendMessage(Config.db.save()).complete();
        } else {
            databaseMessage = history.getRetrievedHistory().get(0);
            Config.db.load(databaseMessage.getContentDisplay());
        }

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                databaseMessage.editMessage(Config.db.save()).complete();
                event.getJDA().shutdown();
            }
        });
    }

    @SuppressWarnings("unchecked")
    public void load(String db) {
        byte[] decoded = Base64.getDecoder().decode(db);
        ByteArrayInputStream bais = new ByteArrayInputStream(decoded);

        try (ObjectInputStream ois = new ObjectInputStream(bais)) {
            this.data = (Map<String, String>) ois.readObject();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }
}