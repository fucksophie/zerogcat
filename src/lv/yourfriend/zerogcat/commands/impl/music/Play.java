package lv.yourfriend.zerogcat.commands.impl.music;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import lv.yourfriend.zerogcat.commands.Command;
import lv.yourfriend.zerogcat.utils.music.PlayerManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.unions.AudioChannelUnion;
import net.dv8tion.jda.api.managers.AudioManager;

public class Play extends Command {
    public Play() {
        super("play", "Play audio from most available remote sources", "play https://youtube.com/dgdraingang", Category.MUSIC);
    }

    public void execute(ArrayList<String> args, Member author, Guild guild, Message message) {
        AudioChannelUnion ch = author.getVoiceState().getChannel();

        if(ch == null) {
            message.reply("You are not in a voice channel!").queue();
            return;
        }

        Member myself = guild.retrieveMemberById(guild.getJDA().getSelfUser().getIdLong()).complete();

        if(myself.getVoiceState().getChannel() == null) {
            AudioManager audioManager = guild.getAudioManager();

            audioManager.openAudioConnection(ch);
        } else {
            if(!myself.getVoiceState().getChannel().equals(ch)) {
                message.reply("You are not in the same voice channel as me!").queue();
                return;
            }
        }

        String link = String.join(" ", args);

        if (!isUrl(link)) {
            link = "ytsearch:" + link;
        }

        PlayerManager.getInstance().loadAndPlay(message, link);
    }

    private boolean isUrl(String url) {
        try {
            new URI(url);
            return true;
        } catch (URISyntaxException e) {
            return false;
        }
    }
}
