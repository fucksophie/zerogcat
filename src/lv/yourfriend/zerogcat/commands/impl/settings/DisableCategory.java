package lv.yourfriend.zerogcat.commands.impl.settings;

import java.util.ArrayList;

import lv.yourfriend.zerogcat.commands.Command;
import lv.yourfriend.zerogcat.utils.Config;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;

public class DisableCategory extends Command {
    public DisableCategory() {
        super("disablecategory", "Disable category/channels usage in this discord", "disablecategory",
                Category.SETTINGS);
    }

    public void execute(ArrayList<String> args, Member author, Guild guild, Message message) {
        if (author.hasPermission(Permission.ADMINISTRATOR)) {
            if (Config.db.data.get("category-" + guild.getId()) == null) {
                message.reply("Category is already disabled!").queue();
                return;
            } else {
                Config.db.data.remove("category-" + guild.getId());
                message.reply("Category disabled!").queue();
                return;
            }
        } else {
            message.reply("No permissons.").queue();
        }
    }
}
