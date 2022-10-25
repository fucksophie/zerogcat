package lv.yourfriend.zerogcat.answers.impl;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

import com.google.gson.JsonObject;

import lv.yourfriend.zerogcat.answers.Answer;
import lv.yourfriend.zerogcat.utils.Config;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Meow extends Answer {

    public Meow() {
        super("meeowww", "Respond with funny cats to \"meow\", \":3\", \"cta\"");
    }

    public Boolean matcher(String message) {
        return message.contains("meow") || message.contains(":3") || message.contains("cta");
    }

    public Boolean execute(MessageReceivedEvent event) {
        try {
            try(Scanner s = new Scanner(new URL("https://aws.random.cat/meow").openStream()).useDelimiter("\\A")) {
                event.getMessage().reply(
                    Config.gson.fromJson(s.hasNext() ? s.next() : "", JsonObject.class)
                    .get("file")
                    .getAsString()
                ).queue();
            }
        } catch (IOException e) {
            event.getMessage().reply("Ugh. there's consuquences for being in 0 gravity." + e.getLocalizedMessage()  ).queue();

            e.printStackTrace();
        }

        return false;
    }
}
