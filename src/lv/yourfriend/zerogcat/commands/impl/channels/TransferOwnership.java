package lv.yourfriend.zerogcat.commands.impl.channels;

import java.util.ArrayList;

import lv.yourfriend.zerogcat.commands.Command;
import lv.yourfriend.zerogcat.utils.Channels;
import lv.yourfriend.zerogcat.utils.Config;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

public class TransferOwnership extends Command {
    public TransferOwnership() {
        super("transferownership",
                "Transfers ownership of a channel",
                "transferownership 757958296060690492 my-cool-channel",
                Category.MORE);
    }

    public void execute(ArrayList<String> args, Member author, Guild guild, Message message) {
        if (Config.db.Get("category-" + guild.getIdLong()) == null) {
            message.reply("This server is not configured for the channel feature. Please run enablecategory.").queue();
            return;
        }

        if (args.size() == 0) {
            message.reply("being in 0 gravity won't stop you from including your arguments").queue();
            return;
        }

        String otherPerson = args.remove(0);

        try {
            message.getJDA().retrieveUserById(otherPerson).complete();
        } catch (Exception e) {
            message.reply(
                    "Please supply a real ID! Example: transferownership 757958296060690492 my-cool-channel")
                    .queue();
            return;
        }

        String channelName = String.join(" ", args);
        TextChannel found = null;

        for (TextChannel z : Channels.channelsOwnedByID(guild, author.getId())) {
            if (z.getName().equals(channelName)) {
                found = z;
            }
        }

        if (found == null) {
            message.reply("Channel mentioned does not exist!").queue();
            return;
        }

        ArrayList<TextChannel> channelsOwnedByOtherPerson = Channels.channelsOwnedByID(guild, otherPerson);

        if (channelsOwnedByOtherPerson.size() >= Channels.getMaxChannels(guild.getIdLong(), Long.valueOf(otherPerson))) {
            message.reply("The other person already owns the max amount of channels!").queue();
            return;
        }

        found.getManager().setTopic(otherPerson).complete();
        message.reply("Ownership of " + channelName + " transfered to <@" + otherPerson + ">!").queue();
    }
}
