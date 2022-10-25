package lv.yourfriend.zerogcat.commands.impl.channels;

import java.util.ArrayList;

import lv.yourfriend.zerogcat.commands.Command;
import lv.yourfriend.zerogcat.utils.Channels;
import lv.yourfriend.zerogcat.utils.Config;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.requests.restaction.ChannelAction;

public class Create extends Command {
    public Create() {
        super("create", "Creates a channel", "create My Cool Channel", Category.CHANNELS);
    }

    public void execute(ArrayList<String> args, Member author, Guild guild, Message message) {
        if (Config.db.data.get("category-" + guild.getIdLong()) == null) {
            message.reply("This server is not configured for the channel feature. Please run enablecategory.").queue();
            return;
        }

        if (args.size() == 0) {
            message.reply("being in 0 gravity won't stop you from including your arguments").queue();
            return;
        }

        Integer numberOfOwnedChannels = Channels.channelsOwnedByID(guild, author.getId()).size();

        if (numberOfOwnedChannels >= Channels.getMaxChannels(author.getIdLong())) {
            message.reply("You already own the max amount of channels!").queue();
            return;
        }

        for (TextChannel z : guild.getCategoryById(Config.db.data.get("category-" + guild.getIdLong()))
                .getTextChannels()) {
            if (z.getName().equals(String.join(" ", args)
                    .toLowerCase().replaceAll(" ", "-"))) {

                message.reply("A channel with this name already exists!").queue();
                return;
            }

        }

        ChannelAction<TextChannel> textChannel = guild.createTextChannel(String.join(" ", args));
        textChannel.setTopic(author.getId());
        textChannel.setParent(guild.getCategoryById(Config.db.data.get("category-" + guild.getIdLong())));
        textChannel.complete();
        message.reply("Channel created. You own: " + (numberOfOwnedChannels + 1) + " channels out of "
                + Channels.getMaxChannels(author.getIdLong()) + " allowed.").queue();
        return;
    }
}
