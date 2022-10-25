package lv.yourfriend.zerogcat.commands.impl.channels;

import java.awt.Color;
import java.util.ArrayList;

import lv.yourfriend.zerogcat.commands.Command;
import lv.yourfriend.zerogcat.utils.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageHistory;
import net.dv8tion.jda.api.entities.MessageEmbed.Field;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

public class Stats extends Command {
    public Stats() {
        super("stats", "See channel stats", "stats", Category.CHANNELS);
    }

    public void execute(ArrayList<String> args, Member author, Guild guild, Message message) {
        if (Config.db.data.get("category-" + guild.getIdLong()) == null) {
            message.reply("This server is not configured for the channel feature. Please run enablecategory.").queue();
            return;
        }

        Integer amountOfChannels = 0;
        Integer amountOfMessages = 0;

        for (TextChannel z : guild.getCategoryById(Config.db.data.get("category-" + guild.getIdLong()))
                .getTextChannels()) {
            MessageHistory history = MessageHistory.getHistoryFromBeginning(z).complete();
            amountOfMessages += history.size();
            amountOfChannels += 1;
        }

        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(new Color(0xB00B69));
        eb.setTitle("Channel creation stats");
        eb.addField(new Field("Channel count", "" + amountOfChannels, true));
        eb.addField(new Field("Message count", "" + amountOfMessages, true));
        message.getChannel().sendMessageEmbeds(eb.build()).queue();
    }
}
