package com.dawoo.gamebox.util;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

import com.dawoo.gamebox.BoxApplication;
import com.dawoo.gamebox.ConstantValue;
import com.dawoo.gamebox.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by benson on 18-2-14.
 */

public class SoundUtil {
    private static final SoundUtil ourInstance = new SoundUtil();
    Map<String, Integer> map = new HashMap<String, Integer>();
    private SoundPool mSoundPool;
    private Context mContext = BoxApplication.getContext();
    private static boolean isOpen = false;

    public static SoundUtil getInstance() {
        return ourInstance;
    }

    private SoundUtil() {
        create();
    }

    private void create() {
        if (Build.VERSION.SDK_INT >= 21) {
            SoundPool.Builder builder = new SoundPool.Builder();
            //传入音频的数量
            builder.setMaxStreams(1);
            //AudioAttributes是一个封装音频各种属性的类
            AudioAttributes.Builder attrBuilder = new AudioAttributes.Builder();
            //设置音频流的合适属性
            attrBuilder.setLegacyStreamType(AudioManager.STREAM_MUSIC);
            builder.setAudioAttributes(attrBuilder.build());
            mSoundPool = builder.build();
        } else {
            //第一个参数是可以支持的声音数量，第二个是声音类型，第三个是声音品质
            mSoundPool = new SoundPool(1, AudioManager.STREAM_SYSTEM, 0);
        }
    }

    public void load(int rawId) {
        isOpen = SharePreferenceUtil.getVoiceStatus(mContext);
        if (mSoundPool == null) {
            create();
        }
        //第一个参数Context,第二个参数资源Id，第三个参数优先级
        int soundID = mSoundPool.load(mContext, rawId, 1);
        map.put(ConstantValue.VOICE_ON_CLICK, soundID);
    }

    public void play(String type, int rawId) {
        Integer soundID = map.get(type);
        if (0 == soundID) {
            load(rawId);
        } else {
            mSoundPool.play(soundID, 1, 1, 0, 0, 1);
        }
    }

    public void resume(int soundID) {
        if (0 != soundID) {
            mSoundPool.resume(soundID);
        }
    }

    public void playVoiceOnclick() {
        if (!isOpen) {
            return;
        }

        play(ConstantValue.VOICE_ON_CLICK, R.raw.anjian);
    }


    public void open() {
        isOpen = true;
        SharePreferenceUtil.saveVoiceStatus(mContext, true);
    }

    public void close() {
        isOpen = false;
        SharePreferenceUtil.saveVoiceStatus(mContext, false);
    }
}
