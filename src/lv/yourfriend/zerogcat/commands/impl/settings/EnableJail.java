package lv.yourfriend.zerogcat.commands.impl.settings;

import java.util.ArrayList;

import lv.yourfriend.zerogcat.commands.Command;
import lv.yourfriend.zerogcat.utils.Config;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;

public class EnableJail extends Command {
    public EnableJail() {
        super("enablejail", "Set a role used for the jail", "enablejail 1033816644641886248", Category.SETTINGS);
    }

    public void execute(ArrayList<String> args, Member author, Guild guild, Message message) {
        if (author.hasPermission(Permission.ADMINISTRATOR)) {
            if (args.size() == 0) {
                message.reply("being in 0 gravity won't stop you from including your arguments").queue();
                return;
            }

            if (guild.getRoleById(args.get(0)) == null) {
                message.reply("Role does not exist.").queue();
                return;
            } else {
                Config.db.Set("jail-" + guild.getId(), args.get(0));
                message.reply("Jail role set!").queue();
                return;
            }
        } else {
            message.reply("No permissons.").queue();
        }
    }
}
