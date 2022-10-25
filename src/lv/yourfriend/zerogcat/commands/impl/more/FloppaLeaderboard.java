package lv.yourfriend.zerogcat.commands.impl.more;

import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import lv.yourfriend.zerogcat.commands.Command;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;

public class FloppaLeaderboard extends Command {
    public FloppaLeaderboard() {
        super("catleaderboard", "deewend's game's leaderboard", "catleaderboard", Category.MORE);
    }

    public void execute(ArrayList<String> args, Member author, Guild guild, Message message) {
        try {
            String domain = "https://s.deewend.ru/gkgameserver?action=view_leaderboard";

            if(args.size() != 0) {
                if(args.get(0) == "alt") {
                    domain = "https://yourfriend.eu.pythonanywhere.com/gkgameserver?action=view_leaderboard";
                }
            }   

            String fullMsg = "Play this game at https://s.deewend.ru/gkgame/ !\n```ansi\n";

            try (Scanner s = new Scanner(
                    new URL(domain).openStream())
                    .useDelimiter("\\A")) {
                ArrayList<String> parts = new ArrayList<String>(
                        Arrays.asList((s.hasNext() ? s.next() : "").split(" ")));
                parts.remove(0);

                for (int x = 0; x < parts.size() / 3; x++) {
                    String name = parts.get(x * 3);
                    String points = parts.get((x * 3) + 1);
                    String time = parts.get((x * 3) + 2);
                    String color = "[0;0;3m";

                    if (x == 0)
                        color = "[0;33;1m";
                    if (x == 1)
                        color = "[0;30;1m";
                    if (x == 2)
                        color = "[0;0;1m";

                    fullMsg += color + (x + 1) + ". " + name + ", passed: " + points + ", playtime: "
                            + LocalTime.MIN.plusSeconds(Integer.valueOf(time)).toString() + "\n";
                }
                message.reply(fullMsg + "```").queue();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
