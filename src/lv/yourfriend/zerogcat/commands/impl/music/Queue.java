package lv.yourfriend.zerogcat.commands.impl.music;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;

import lv.yourfriend.zerogcat.commands.Command;
import lv.yourfriend.zerogcat.utils.music.GuildMusicManager;
import lv.yourfriend.zerogcat.utils.music.PlayerManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

public class Queue extends Command {
    public Queue() {
        super("queue", "Get the current queue in this guild", "queue", Category.MUSIC);
    }

    public void execute(ArrayList<String> args, Member author, Guild guild, Message message) {
        TextChannel channel = message.getChannel().asTextChannel();
        GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(guild, message.getChannel().asTextChannel());
        BlockingQueue<AudioTrack> queue = musicManager.scheduler.queue;

        if (queue.isEmpty()) {
            channel.sendMessage("The queue is currently empty").queue();
            return;
        }

        int trackCount = Math.min(queue.size(), 20);
        List<AudioTrack> trackList = new ArrayList<>(queue);
        String msg = "**Current Queue:**\n";

        for (int i = 0; i <  trackCount; i++) {
            AudioTrack track = trackList.get(i);
            AudioTrackInfo info = track.getInfo();
            msg += (i+1) + ". **" + info.title + "**" + " by " + info.author + ". **" + formatTime((track.getDuration())) + "**\n";
        }

        if (trackList.size() > trackCount) {
            msg += "And **" + (trackList.size() - trackCount) + "** more...";
        }

        msg += "\n\n";
        AudioTrackInfo playingRightNow = musicManager.audioPlayer.getPlayingTrack().getInfo();

        msg += "Currently playing: **" + playingRightNow.title + "** by **" + playingRightNow.author + "**!";
        message.reply(msg.toString()).queue();
    }

    private String formatTime(long timeInMillis) {
        long hours = timeInMillis / TimeUnit.HOURS.toMillis(1);
        long minutes = timeInMillis / TimeUnit.MINUTES.toMillis(1);
        long seconds = timeInMillis % TimeUnit.MINUTES.toMillis(1) / TimeUnit.SECONDS.toMillis(1);

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
