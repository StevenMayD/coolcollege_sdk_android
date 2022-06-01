package com.coolcollege.aar.provider;

import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;

import androidx.core.content.FileProvider;

import com.coolcollege.aar.application.Options;
import com.coolcollege.aar.bean.TempFileBean;
import com.coolcollege.aar.bean.VideoInfoBean;

import java.io.File;
import java.io.IOException;

/**
 * Created by Evan_for on 2020/7/9
 */
public class FilePathProvider {

    public static final String TYPE_IMG = "img";
    public static final String TYPE_VIDEO = "video";
    public static final String TYPE_AUDIO = "audio";

    public static TempFileBean getOutputMediaFileUri(String type) {
        String tempPath;
        String cacheFileName;
        String tempFileName;
        if (TYPE_IMG.equals(type)) {
            cacheFileName = "pictures";
            tempFileName = "img_" + System.currentTimeMillis() + ".jpg";
        } else if (TYPE_AUDIO.equals(type)) {
            cacheFileName = "audio";
            tempFileName = "audio_" + System.currentTimeMillis() + ".mp3";
        } else {
            cacheFileName = "video";
            tempFileName = "video_" + System.currentTimeMillis() + ".mp4";
        }
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            tempPath = Options.get().getExternalCacheDir() + File.separator + cacheFileName + File.separator;
        } else {
            tempPath = Options.get().getCacheDir() + File.separator + cacheFileName + File.separator;
        }
        File file = new File(tempPath, tempFileName);
        File parentFile = file.getParentFile();
        if (parentFile != null) {
            if (!parentFile.exists()) parentFile.mkdir();
        }

        Uri uri;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(Options.get(),
                    Options.get().getPackageName() + ".provider", file);
        } else {
            uri = Uri.fromFile(file);
        }
        return new TempFileBean(uri, file.getAbsolutePath(), file);
    }


    public static String getDuration(Uri uri) {
        try {
            MediaPlayer mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(Options.get(), uri);
            mediaPlayer.prepare();
            return String.valueOf(mediaPlayer.getDuration() / 1000);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getDuration(String path) {
        try {
            MediaPlayer mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();
            return String.valueOf(mediaPlayer.getDuration() / 1000);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getFileSize(String path) {
        return String.valueOf(new File(path).length());
    }


    public static VideoInfoBean getVideoInfo(String path) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(path);
        String width = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH);
        String height = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT);
        String bitrate = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_BITRATE);
        return new VideoInfoBean(Integer.parseInt(width), Integer.parseInt(height), Integer.parseInt(bitrate), width + "," + height);
    }

    public static String getCacheDirPath() {
        return getCacheDirPath("video_",".mp4");
    }

    public static String getCacheDirPath(String fileTypeName,String fileSuffix) {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            return Options.get().getExternalCacheDir() + File.separator + fileTypeName + System.currentTimeMillis() + fileSuffix;
        } else {
            return Options.get().getCacheDir() + File.separator + fileTypeName + System.currentTimeMillis() + fileSuffix;
        }
    }

}
