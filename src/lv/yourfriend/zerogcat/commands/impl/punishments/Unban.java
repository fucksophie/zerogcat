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

public class Unban extends Command {
    public Unban() {
        super("unban", "Unban a user", "unban @yourfriend", Category.PUNISHMENTS);
    }

    public void execute(ArrayList<String> args, Member author, Guild guild, Message message) {
        if (author.hasPermission(Permission.ADMINISTRATOR)) {
            List<User> users = message.getMentions().getUsers();

            if (users.size() == 0) {
                message.reply("No user specified for unbanning.").queue();
            } else {
                users.forEach(z -> {
                    message.reply("Unbanned " + z.getAsTag() + "!").queue();
                    Config.db.data.remove("banned-" + z.getId());
                });
            }
        } else {
            message.reply("No permissons.").queue();
        }
    }
}
