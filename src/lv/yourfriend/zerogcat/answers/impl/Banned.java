package lv.yourfriend.zerogcat.answers.impl;

import lv.yourfriend.zerogcat.answers.Answer;
import lv.yourfriend.zerogcat.utils.Config;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Banned extends Answer {
    public Banned() {
        super("banned", "Disables usage of the bot for banned users and responds with a L");
    }

    public Boolean matcher(String message) {
        return true;
    }

    public Boolean execute(MessageReceivedEvent event) {
        if(Config.db.Get("banned-" + event.getAuthor().getId()) != null) {
            event.getMessage().addReaction(Emoji.fromUnicode("🇱")).queue();
            return true;
        }

        return false;
    }
}
