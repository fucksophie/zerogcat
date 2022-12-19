package lv.yourfriend.zerogcat.commands.impl.quotes;

import java.nio.ByteBuffer;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map.Entry;

import org.lmdbjava.CursorIterable.KeyVal;

import lv.yourfriend.zerogcat.commands.Command;
import lv.yourfriend.zerogcat.utils.Config;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;

public class Quote extends Command {
    public Quote() {
        super("quote", "Get a random quote", "quote", Category.QUOTES);
    }

    public void execute(ArrayList<String> args, Member author, Guild guild, Message message) {
        ArrayList<Entry<String, String>> cleaned = new ArrayList<Entry<String, String>>();

        for (final KeyVal<ByteBuffer> kv : Config.db.Entries()) {
            String[] parts = Config.db.bts(kv.key()).split("-");

            if (parts[0].equals("quote"))
                cleaned.add(new AbstractMap.SimpleEntry<String, String>(parts[1], Config.db.bts(kv.val())));
        }
        Config.db.Clean();

        if(cleaned.size() == 0) {
            message.reply("No quotes loaded. :sob:").queue();
            return;
        }

        Entry<String, String> yz = cleaned.get((int) (Math.random() * cleaned.size()));

        User user = message.getJDA().retrieveUserById(yz.getKey()).complete();

        message.reply("Quote from " + user.getAsTag() + ": " + yz.getValue()).queue();
        return;
    }
}
