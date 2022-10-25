package lv.yourfriend.zerogcat.commands.impl.music;

import java.util.ArrayList;

import lv.yourfriend.zerogcat.commands.Command;
import lv.yourfriend.zerogcat.utils.music.GuildMusicManager;
import lv.yourfriend.zerogcat.utils.music.PlayerManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;

public class Stop extends Command {
    public Stop() {
        super("stop", "Instantly stop playing music", "stop", Category.MUSIC);
    }

    public void execute(ArrayList<String> args, Member author, Guild guild, Message message) {
        Member self = guild.retrieveMemberById(guild.getJDA().getSelfUser().getIdLong()).complete();
        GuildVoiceState selfVoiceState = self.getVoiceState();

        if (selfVoiceState.getChannel() == null) {
            message.reply("I need to be in a voice channel for this to work").queue();
            return;
        }

        GuildVoiceState memberVoiceState = author.getVoiceState();

        if (memberVoiceState.getChannel() == null) {
            message.reply("You need to be in a voice channel for this command to work").queue();
            return;
        }

        if (!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())) {
            message.reply("You need to be in the same voice channel as me for this to work").queue();
            return;
        }

        GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(guild, message.getChannel().asTextChannel());

        musicManager.scheduler.player.stopTrack();
        musicManager.scheduler.queue.clear();

        message.reply("The player has been stopped and the queue has been cleared").queue();
    }
}
