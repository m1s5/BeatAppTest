package ru.mardmi.beatapptest;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

public class SoundPoolPlayer {
    Context context;
    SoundPool soundPool;
    int bar, beat, bass, tone, slap;
    boolean stop;

    Timer timer;


    public SoundPoolPlayer(Context context) {
        this.context = context;
        timer = new Timer();
    }

    void playPattern(String pattern, int tempo) {
        if (pattern.isEmpty()) return;

        final int[] measure = {0};
        tempo = 60000 / tempo;

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if (stop) {
                    return;
                }

                if (measure[0] == pattern.length()) {
                    measure[0] = 0;
                }
                play(pattern.charAt(measure[0]));
                measure[0]++;
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, tempo);
    }

    void play(char sound) {
        switch (sound) {
            case '!': {
                soundPool.play(bar, 1, 1, 0, 0, 1);
                break;
            }
            case '.': {
                soundPool.play(beat, 1, 1, 0, 0, 1);
                break;
            }
            case 'B': {
                soundPool.play(bass, 1, 1, 0, 0, 1);
                break;
            }
            case 'T': {
                soundPool.play(tone, 1, 1, 0, 0, 1);
                break;
            }
            case 'S': {
                soundPool.play(slap, 1, 1, 0, 0, 1);
                break;
            }
            default: {
                break;
            }
        }
    }

    public void soundPoolInit() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_UNKNOWN)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setMaxStreams(6)
                    .setAudioAttributes(audioAttributes)
                    .build();
        } else {
            soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        }

        bar = soundPool.load(context, R.raw.metro_bar, 1);
        beat = soundPool.load(context, R.raw.metro_beat, 1);
        bass = soundPool.load(context, R.raw.bass, 1);
        tone = soundPool.load(context, R.raw.tone, 1);
        slap = soundPool.load(context, R.raw.slap, 1);


    }

    public void onDestroy() {
        soundPool.release();
        soundPool = null;
    }

    public void stopMe(boolean stop) {
        this.stop = stop;
        if (timer != null) {
            timer.cancel();
        }
        timer = new Timer();
    }
}