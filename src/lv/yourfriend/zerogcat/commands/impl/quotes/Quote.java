package lv.yourfriend.zerogcat.commands.impl.quotes;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map.Entry;

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

        for (Entry<String, String> entry : Config.db.data.entrySet()) {
            String[] parts = entry.getKey().split("-");

            if (parts[0].equals("quote"))
                cleaned.add(new AbstractMap.SimpleEntry<String, String>(parts[1], entry.getValue()));
        }

        Entry<String, String> yz = cleaned.get((int) (Math.random() * cleaned.size()));

        User user = message.getJDA().retrieveUserById(yz.getKey()).complete();

        message.reply("Quote from " + user.getAsTag() + ": " + yz.getValue()).queue();
        return;
    }
}
