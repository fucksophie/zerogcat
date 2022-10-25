package lv.yourfriend.zerogcat.answers.impl;

import lv.yourfriend.zerogcat.answers.Answer;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Gravity extends Answer {
    public Gravity() {
        super("gravedad cero", "Respond with zero gravity cat to any combination of 0g cat");
    }

    public Boolean matcher(String message) {
        return (message.contains("gravity") || message.contains("0g"))&& 
            message.contains("cat") && 
            (message.contains("zero") || message.contains("0"));
    }

    public Boolean execute(MessageReceivedEvent event) {
        String[] boys = {"https://tenor.com/view/antigravity-toast-butter-anti-gravity-gif-13644251", "https://tenor.com/view/cat-zero-gravity-cat-in-space-gif-14342225", "https://imgur.com/u0ALttx"};

        event.getMessage().reply(boys[(int)(Math.random() * boys.length)]).queue();
        return false;
    }
}
