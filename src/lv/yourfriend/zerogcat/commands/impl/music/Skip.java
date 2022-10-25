package lv.yourfriend.zerogcat.commands.impl.music;

import java.util.ArrayList;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;

import lv.yourfriend.zerogcat.commands.Command;
import lv.yourfriend.zerogcat.utils.music.GuildMusicManager;
import lv.yourfriend.zerogcat.utils.music.PlayerManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

public class Skip extends Command {
    public Skip() {
        super("skip", "Skip the now playing song", "skip", Category.MUSIC);
    }

    public void execute(ArrayList<String> args, Member author, Guild guild, Message message) {
        TextChannel channel = message.getChannel().asTextChannel();
        Member self = guild.retrieveMemberById(guild.getJDA().getSelfUser().getIdLong()).complete();
        GuildVoiceState selfVoiceState = self.getVoiceState();

        if (selfVoiceState.getChannel() == null) {
            channel.sendMessage("I need to be in a voice channel for this to work").queue();
            return;
        }

        GuildVoiceState memberVoiceState = author.getVoiceState();

        if (memberVoiceState.getChannel() == null) {
            channel.sendMessage("You need to be in a voice channel for this command to work").queue();
            return;
        }

        if (!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())) {
            channel.sendMessage("You need to be in the same voice channel as me for this to work").queue();
            return;
        }

        GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(guild, message.getChannel().asTextChannel());
        AudioPlayer audioPlayer = musicManager.audioPlayer;

        if (audioPlayer.getPlayingTrack() == null) {
            channel.sendMessage("There is no track playing currently").queue();
            return;
        }

        musicManager.scheduler.nextTrack();
        channel.sendMessage("Skipped the current track!").queue();
    }
}
