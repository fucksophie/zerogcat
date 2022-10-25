package lv.yourfriend.zerogcat.commands.impl.quotes;

import java.util.ArrayList;
import java.util.Map.Entry;

import lv.yourfriend.zerogcat.commands.Command;
import lv.yourfriend.zerogcat.utils.Config;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;

public class RemoveQuote extends Command {
    public RemoveQuote() {
        super("removequote", "Remove a quote by it's id", "removequote 102482", Category.QUOTES);
    }

    public void execute(ArrayList<String> args, Member author, Guild guild, Message message) {
        if (args.size() == 0) {
            message.reply("being in 0 gravity won't stop you from including your arguments").queue();
            return;
        }

        String value = null;

        for (Entry<String, String> entry : Config.db.data.entrySet()) {
            String[] parts = entry.getKey().split("-");

            if (parts[0].equals("quote") && parts[1].equals(author.getId()) && parts[2].equals(args.get(0)))
                value = entry.getKey();
        }

        if (value == null) {
            message.reply("Quote doesn't exist.").queue();
            return;
        }

        Config.db.data.remove(value);
        message.reply("Quote removed.").queue();
        return;
    }
}
