package lv.yourfriend.zerogcat.commands.impl.more;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;

import lv.yourfriend.zerogcat.answers.Answer;
import lv.yourfriend.zerogcat.answers.AnswerManager;
import lv.yourfriend.zerogcat.commands.Command;
import lv.yourfriend.zerogcat.commands.CommandManager;
import lv.yourfriend.zerogcat.utils.Config;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;

public class Help extends Command {
    public Help() {
        super("help", "Get help about the bot", "help", Category.MORE);
    }

    public void execute(ArrayList<String> args, Member author, Guild guild, Message message) {
        HashMap<String, ArrayList<Command>> map = new HashMap<String, ArrayList<Command>>();

        for (Command c : CommandManager.commands) {
            ArrayList<Command> lst = map.get(c.cat.toString());

            if (lst == null) {
                map.put(c.cat.toString(), new ArrayList<Command>(Arrays.asList(c)));
            } else {
                lst.add(c);
                map.put(c.cat.toString(), lst);
            }
        }

        String help = "";

        for (Entry<String, ArrayList<Command>> entry : map.entrySet()) {
            help += "**" + entry.getKey() + "**\n";

            for (Command z : entry.getValue()) {
                help += "`" + Config.prefix + z.usage + "` - " + z.description + "\n";
            }

            help += "\n";
        }

        help += "\n**ANSWERS**\n";

        for (Answer a : AnswerManager.answers) {
            help += "ID `" + a.id + "` - " + a.description + "\n";
        }

        final String y = help;
        try {
            if (author.hasPermission(Permission.ADMINISTRATOR)) {
                if(args.size() != 0) {
                    if(args.get(0).equals("alt")) {
                        message.getChannel().sendMessage(y).queue();
                        return;
                    }
                }
            } else {
                message.reply("No permissons.").queue();
            }

            author.getUser().openPrivateChannel()
                    .flatMap(channel -> channel.sendMessage(y))
                    .complete();
            message.reply("📫 Check your DMS!").queue();
        } catch (Exception e) {
            message.reply("Your DMS appear to be closed.").queue();
            return;
        }

    }
}
