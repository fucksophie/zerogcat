package lv.yourfriend.zerogcat.answers.impl;

import lv.yourfriend.zerogcat.answers.Answer;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Goober extends Answer {
    public Goober() {
        super("goober", "Goobin");
    }
    
    public Boolean matcher(String message) {
        return message.contains("goob");
    }

    public Boolean execute(MessageReceivedEvent event) {
        event.getMessage().reply("https://tenor.com/view/goober-cat-cta-warning-warning-cat-gif-25591795").queue();
        return false;
    }
}
