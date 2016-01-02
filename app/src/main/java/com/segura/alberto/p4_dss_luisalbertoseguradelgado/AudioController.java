package com.segura.alberto.p4_dss_luisalbertoseguradelgado;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * Created by alberto on 2/1/16.
 */
public enum AudioController {

    INSTANCE;

    MediaPlayer player = null;

    AudioController()
    {
    }

    public void playSong(Context c, int songId) {
        if (player != null)
        {
            if( player.isPlaying() )
            {
                player.stop();
                player.release();
            }
        }

        player = MediaPlayer.create(c, songId);
        player.start();
    }

    public void stop() {
        if (player != null)
        {
            if( player.isPlaying() )
            {
                player.stop();
                player.release();
            }
        }
    }

}
