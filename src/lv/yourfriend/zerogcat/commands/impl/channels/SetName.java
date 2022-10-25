package lv.yourfriend.zerogcat.commands.impl.channels;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lv.yourfriend.zerogcat.commands.Command;
import lv.yourfriend.zerogcat.utils.Channels;
import lv.yourfriend.zerogcat.utils.Config;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

public class SetName extends Command {
    public SetName() {
        super("setname",
                "Changes your channel name",
                "setname <my-cool-channel> <My Cooler Channel> (the <>'s are required)",
                Category.CHANNELS);
    }

    public void execute(ArrayList<String> args, Member author, Guild guild, Message message) {
        if (Config.db.data.get("category-" + guild.getIdLong()) == null) {
            message.reply("This server is not configured for the channel feature. Please run enablecategory.").queue();
            return;
        }

        final Matcher matcher = Pattern.compile("<([^><]*)>", Pattern.MULTILINE).matcher(String.join(" ", args));
        ArrayList<String> coolArguments = new ArrayList<>();

        while (matcher.find()) {
            coolArguments.add(matcher.group(1));
        }

        if (coolArguments.size() != 2) {
            message.reply("Example: please-setname <my supercool channel> <my way cooler channel>").queue();
            return;
        }

        for (TextChannel z : Channels.channelsOwnedByID(guild, author.getId())) {
            if (z.getName().equals(coolArguments.get(0))) {
                z.getManager().setName(coolArguments.get(1)).complete();
                message.reply("Sucessfully changed name to " + coolArguments.get(1) + " from " + coolArguments.get(0))
                        .queue();
                return;
            }
        }

        message.reply("Could not find your requested channel.").queue();
    }
}
