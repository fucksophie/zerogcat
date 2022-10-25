package lv.yourfriend.zerogcat.commands.impl.more;

import java.util.ArrayList;

import lv.yourfriend.zerogcat.commands.Command;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;

public class Test extends Command {
    public Test() {
        super("test", "Test uhh", "test", Category.MORE);
    }

    public void execute(ArrayList<String> args, Member author, Guild guild, Message message) {
        message.reply("testing tester").complete();
    }
}
