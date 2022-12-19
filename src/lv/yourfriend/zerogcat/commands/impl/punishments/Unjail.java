package lv.yourfriend.zerogcat.commands.impl.punishments;

import java.util.ArrayList;
import java.util.List;

import lv.yourfriend.zerogcat.commands.Command;
import lv.yourfriend.zerogcat.utils.Config;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;

public class Unjail extends Command {
    public Unjail() {
        super("unjail", "Unjail a user", "unjail @yourfriend", Category.PUNISHMENTS);
    }

    public void execute(ArrayList<String> args, Member author, Guild guild, Message message) {
        if (author.hasPermission(Permission.ADMINISTRATOR)) {
            if (Config.db.Get("jail-" + guild.getIdLong()) == null) {
                message.reply("This server is not configured for the channel feature. Please run enablejail.").queue();
                return;
            }

            List<Member> members = message.getMentions().getMembers();

            if (members.size() == 0) {
                message.reply("No user specified for unjail.").queue();
            } else {
                Role rank = guild.getRoleById(Config.db.Get("jail-" + guild.getIdLong()));

                members.forEach(z -> {
                    List<Role> ranks = z.getRoles();
                    if (ranks.contains(rank)) {
                        message.reply("Unjail-d " + z.getUser().getAsTag() + "!").queue();
                        guild.removeRoleFromMember(z, rank).complete();
                    } else {
                        message.reply("User " + z.getUser().getAsTag() + " is not jaild!").queue();
                    }
                });
            }
        } else {
            message.reply("No permissons.").queue();
        }
    }
}
