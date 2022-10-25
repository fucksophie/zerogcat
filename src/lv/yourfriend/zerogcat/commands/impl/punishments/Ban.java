package lv.yourfriend.zerogcat.commands.impl.punishments;

import java.util.ArrayList;
import java.util.List;

import lv.yourfriend.zerogcat.commands.Command;
import lv.yourfriend.zerogcat.utils.Config;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;

public class Ban extends Command {
    public Ban() {
        super("ban", "Ban a user", "ban @yourfriend", Category.PUNISHMENTS);
    }

    public void execute(ArrayList<String> args, Member author, Guild guild, Message message) {
        if (author.hasPermission(Permission.ADMINISTRATOR)) {
            List<User> users = message.getMentions().getUsers();

            if (users.size() == 0) {
                message.reply("No user specified for banning.").queue();
            } else {
                users.forEach(z -> {
                    message.reply("Banned " + z.getAsTag() + ".").queue();
                    Config.db.data.put("banned-" + z.getId(), "banned");
                });
            }
        } else {
            message.reply("No permissons.").queue();
        }
    }
}
