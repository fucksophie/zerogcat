package lv.yourfriend.zerogcat.commands.impl.settings;

import java.util.ArrayList;

import lv.yourfriend.zerogcat.commands.Command;
import lv.yourfriend.zerogcat.utils.Config;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;

public class DisableJail extends Command {
    public DisableJail() {
        super("disablejail", "Disable the jail function", "disablecategory", Category.SETTINGS);
    }

    public void execute(ArrayList<String> args, Member author, Guild guild, Message message) {
        if (author.hasPermission(Permission.ADMINISTRATOR)) {
            if (Config.db.Get("jail-" + guild.getId()) == null) {
                message.reply("Jail is already disabled!").queue();
                return;
            } else {
                Config.db.Delete("jail-" + guild.getId());
                message.reply("Jail disabled!").queue();
                return;
            }
        } else {
            message.reply("No permissons.").queue();
        }
    }
}
