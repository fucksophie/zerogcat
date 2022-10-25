package lv.yourfriend.zerogcat;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import lv.yourfriend.zerogcat.answers.Answer;
import lv.yourfriend.zerogcat.answers.AnswerManager;
import lv.yourfriend.zerogcat.commands.CommandManager;
import lv.yourfriend.zerogcat.utils.Config;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

public class ZeroGcat extends ListenerAdapter {
    public static void main(String[] args) {
        File conf = new File(String.join("", args));

        if (conf.exists()) {
            Config.parse(conf);
        } else {
            System.out.println("File does not exist");
            System.exit(0);
        }

        CommandManager.init();
        AnswerManager.init();
        JDABuilder builder = JDABuilder.createDefault(Config.token);

        builder.disableCache(CacheFlag.MEMBER_OVERRIDES);
        builder.setBulkDeleteSplittingEnabled(false);
        builder.enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT);

        builder.setActivity(Activity.watching("the funny cat"));
        builder.addEventListeners(new ZeroGcat());
        builder.setEnableShutdownHook(false);

        builder.build();
    }

    @Override
    public void onReady(ReadyEvent event) {
        Config.db.start(event);

    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot())
            return;
        if (!event.isFromGuild())
            return;

        String message = event.getMessage().getContentDisplay();
        ArrayList<String> args = new ArrayList<String>(Arrays.asList(message.split(" ")));
        String command = args.remove(0);

        for (Answer a : AnswerManager.answers)
            if (a.matcher(message))
                if (a.execute(event))
                    return;

        CommandManager.commands.forEach(c -> {
            if ((Config.prefix + c.name).equals(command))
                c.execute(args, event.getMember(), event.getGuild(), event.getMessage());
        });
    }
}
