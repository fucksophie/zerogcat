package lv.yourfriend.zerogcat.answers;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public abstract class Answer {
    public String id;
    public String description;

    public Answer(String id, String description) {
        this.id = id;
        this.description = description;
    };

    abstract public Boolean matcher(String message);
    abstract public Boolean execute(MessageReceivedEvent event);
}
