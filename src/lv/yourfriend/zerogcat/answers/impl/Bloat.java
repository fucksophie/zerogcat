package lv.yourfriend.zerogcat.answers.impl;

import lv.yourfriend.zerogcat.answers.Answer;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Bloat extends Answer {
    public Bloat() {
        super("bloat", "mentions yeti 4 times if the word bloat is mentioned");
    }
    
    public Boolean matcher(String message) {
        return message.contains("bloat");
    }

    public Boolean execute(MessageReceivedEvent event) {
        event.getMessage().reply("<@739032871087374408><@739032871087374408><@739032871087374408><@739032871087374408> BLOAT??").queue();
        return false;
    }
}
