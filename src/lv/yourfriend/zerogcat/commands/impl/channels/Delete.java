package lv.yourfriend.zerogcat.commands.impl.channels;

import java.util.ArrayList;

import lv.yourfriend.zerogcat.commands.Command;
import lv.yourfriend.zerogcat.utils.Channels;
import lv.yourfriend.zerogcat.utils.Config;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

public class Delete extends Command {
    public Delete() {
        super("delete", "Delete a channel", "delete my-cool-channel", Category.CHANNELS);
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

        for (TextChannel z : Channels.channelsOwnedByID(guild, author.getId())) {
            if (z.getName().equals(String.join(" ", args))) {
                z.delete().queue();

                message.reply("Channel deleted.").queue();
                return;
            }
        }

        message.reply("Could not find your requested channel.").queue();
        return;
    }
}
