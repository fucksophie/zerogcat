package lv.yourfriend.zerogcat.commands;

import java.util.ArrayList;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;

public abstract class Command {
    public enum Category {
        CHANNELS, QUOTES, MORE, PUNISHMENTS, ANSWER, SETTINGS, MUSIC
    }

    public String name;
    public String description;
    public String usage;

    public Category cat;

    public Command(String name, String description, String usage, Category cat) {
        this.name = name;
        this.description = description;
        this.cat = cat;
        this.usage = usage;
    }

    abstract public void execute(ArrayList<String> args, Member author, Guild guild, Message message);
}
