package lv.yourfriend.zerogcat.utils.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;

import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TrackScheduler extends AudioEventAdapter {
    public AudioPlayer player;
    public BlockingQueue<AudioTrack> queue;
    public TextChannel channel;

    public TrackScheduler(AudioPlayer player, TextChannel channel) {
        this.player = player;
        this.channel = channel;

        this.queue = new LinkedBlockingQueue<>();
    }

    public void queue(AudioTrack track) {
        if (!this.player.startTrack(track, true)) {
            this.queue.offer(track);
        }
    }

    public void nextTrack() {
        AudioTrack tt = this.queue.poll();
        this.channel.sendMessage("Now playing **" + tt.getInfo().title + "** by **" + tt.getInfo().author + "**!").queue();
        this.player.startTrack(tt, false);
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        if (endReason.mayStartNext) {
            nextTrack();
        }
    }
}