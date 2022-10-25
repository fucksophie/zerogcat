package lv.yourfriend.zerogcat.commands.impl.quotes;

import java.util.ArrayList;
import java.util.Map.Entry;

import lv.yourfriend.zerogcat.commands.Command;
import lv.yourfriend.zerogcat.utils.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;

public class ListQuotes extends Command {
    public ListQuotes() {
        super("listquotes", "List all of your quotes", "listquotes", Category.QUOTES);
    }

    public void execute(ArrayList<String> args, Member author, Guild guild, Message message) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Your quotes");
        for (Entry<String, String> entry : Config.db.data.entrySet()) {
            String[] parts = entry.getKey().split("-");
            if (parts[0].equals("quote") && parts[1].equals(author.getId()))
                eb.addField("Quote ID: " + parts[2], entry.getValue(), true);
        }

        message.getChannel().sendMessageEmbeds(eb.build()).queue();
    }
}