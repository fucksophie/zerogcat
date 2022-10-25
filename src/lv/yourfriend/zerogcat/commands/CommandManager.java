package lv.yourfriend.zerogcat.commands;

import java.util.ArrayList;

import lv.yourfriend.zerogcat.commands.impl.channels.Create;
import lv.yourfriend.zerogcat.commands.impl.channels.Delete;
import lv.yourfriend.zerogcat.commands.impl.channels.SetName;
import lv.yourfriend.zerogcat.commands.impl.channels.Stats;
import lv.yourfriend.zerogcat.commands.impl.more.FloppaLeaderboard;
import lv.yourfriend.zerogcat.commands.impl.more.Help;
import lv.yourfriend.zerogcat.commands.impl.more.Test;
import lv.yourfriend.zerogcat.commands.impl.music.Play;
import lv.yourfriend.zerogcat.commands.impl.music.Queue;
import lv.yourfriend.zerogcat.commands.impl.music.Skip;
import lv.yourfriend.zerogcat.commands.impl.music.Stop;
import lv.yourfriend.zerogcat.commands.impl.punishments.Ban;
import lv.yourfriend.zerogcat.commands.impl.punishments.Jail;
import lv.yourfriend.zerogcat.commands.impl.punishments.Unban;
import lv.yourfriend.zerogcat.commands.impl.punishments.Unjail;
import lv.yourfriend.zerogcat.commands.impl.quotes.AddQuote;
import lv.yourfriend.zerogcat.commands.impl.quotes.ListQuotes;
import lv.yourfriend.zerogcat.commands.impl.quotes.Quote;
import lv.yourfriend.zerogcat.commands.impl.quotes.RemoveQuote;
import lv.yourfriend.zerogcat.commands.impl.settings.DisableCategory;
import lv.yourfriend.zerogcat.commands.impl.settings.DisableJail;
import lv.yourfriend.zerogcat.commands.impl.settings.EnableCategory;
import lv.yourfriend.zerogcat.commands.impl.settings.EnableJail;
import lv.yourfriend.zerogcat.commands.impl.settings.SetLimits;

public class CommandManager {
    static public ArrayList<Command> commands = new ArrayList<>();

    static public void init() { // TODO: Eventually use reflections for this
        commands.add(new AddQuote());
        commands.add(new Ban());
        commands.add(new Create());
        commands.add(new Delete());
        commands.add(new Jail());
        commands.add(new FloppaLeaderboard());
        commands.add(new ListQuotes());
        commands.add(new Quote());
        commands.add(new RemoveQuote());
        commands.add(new Test());
        commands.add(new Unban());
        commands.add(new Unjail());
        commands.add(new SetName());
        commands.add(new Stats());
        commands.add(new Help());
        commands.add(new DisableCategory());
        commands.add(new EnableCategory());
        commands.add(new EnableJail());
        commands.add(new DisableJail());
        commands.add(new SetLimits());
        commands.add(new Skip());
        commands.add(new Play());
        commands.add(new Queue());
        commands.add(new Stop());
    }

}
