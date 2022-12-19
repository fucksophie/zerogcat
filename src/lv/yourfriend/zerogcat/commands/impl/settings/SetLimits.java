package lv.yourfriend.zerogcat.commands.impl.settings;

import java.util.ArrayList;

import lv.yourfriend.zerogcat.commands.Command;
import lv.yourfriend.zerogcat.utils.Config;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;

public class SetLimits extends Command {
    public SetLimits() {
        super("setlimits", "Set category amount limits for a user", "enablecategory 1033421870214676520 30",
                Category.SETTINGS);
    }

    public void execute(ArrayList<String> args, Member author, Guild guild, Message message) {
        if (author.hasPermission(Permission.ADMINISTRATOR)) {
            if (args.size() < 2) {
                message.reply("being in 0 gravity won't stop you from including your arguments").queue();
                return;
            }

            User u;
            try {
                u = message.getJDA().retrieveUserById(args.get(0)).complete();
            } catch (Exception e) {
                message.reply("User does not exist.").queue();
                return;
            }

            if (!args.get(1).matches("[0-9]+")) {
                message.reply("Not a number!").queue();
                return;
            }

            if (args.get(1) == String.valueOf(Config.maxAmountOfChannels)) {
                message.reply("Limits equal to default!").queue();
                Config.db.Delete("limits-" + args.get(0) + "-" + guild.getIdLong());
                return;
            }

            Config.db.Set("limits-" + args.get(0) + "-" + guild.getIdLong(), args.get(1));
            message.reply("Limits for " + u.getAsTag() + " set to " + args.get(1)).queue();
            return;

        } else {
            message.reply("No permissons.").queue();
        }
    }
}
