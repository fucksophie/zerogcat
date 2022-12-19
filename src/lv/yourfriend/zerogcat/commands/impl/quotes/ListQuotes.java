package lv.yourfriend.zerogcat.commands.impl.quotes;

import java.nio.ByteBuffer;
import java.util.ArrayList;

import org.lmdbjava.CursorIterable.KeyVal;

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
        for (final KeyVal<ByteBuffer> kv : Config.db.Entries()) {
            String[] parts = Config.db.bts(kv.key()).split("-");
            if (parts[0].equals("quote") && parts[1].equals(author.getId()))
                eb.addField("Quote ID: " + parts[2], Config.db.bts(kv.val()), true);
        }
        Config.db.Clean();

        message.getChannel().sendMessageEmbeds(eb.build()).queue();
    }
}
