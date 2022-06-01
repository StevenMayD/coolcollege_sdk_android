package com.coolcollege.aar.provider;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.coolcollege.aar.application.Options;
import com.coolcollege.aar.bean.MediaItemBean;
import com.coolcollege.aar.bean.MimeType;
import com.coolcollege.aar.global.GlobalKey;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by Evan_for on 2020/7/8
 */
public class MediaProvider {

    public enum URI_TYPE {
        IMG, VIDEO;
    }

    public static HashMap<String, ArrayList<MediaItemBean>> getMediaList(URI_TYPE type) {
        HashMap<String, ArrayList<MediaItemBean>> map = new HashMap<>();

        Uri uri;
        String selection;
        String[] selectionArgs;
        String[] projection = null;
        if (URI_TYPE.IMG == type) {
            uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            selection = MediaStore.Images.Media.MIME_TYPE + " in(?,?,?,?,?)";
            selectionArgs = new String[]{MimeType.JPEG.getTypeName()
                    , MimeType.PNG.getTypeName(), MimeType.GIF.getTypeName()
                    , MimeType.BMP.getTypeName(), MimeType.WEBP.getTypeName()};
        } else {
            uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
            selection = MediaStore.Video.Media.MIME_TYPE + " in(?,?,?,?,?,?,?,?,?,?,?)";
            selectionArgs = new String[]{MimeType.MPEG.getTypeName()
                    , MimeType.MP4.getTypeName(), MimeType.QUICKTIME.getTypeName()
                    , MimeType.THREEGPP.getTypeName(), MimeType.THREEGPP2.getTypeName()
                    , MimeType.MKV.getTypeName(), MimeType.WEBM.getTypeName()
                    , MimeType.TS.getTypeName(), MimeType.AVI.getTypeName()
                    , MimeType.FLV.getTypeName(), MimeType.RMVB.getTypeName()};
            projection = new String[]{
                    MediaStore.Video.Media.DATA,
                    MediaStore.Video.Media.DISPLAY_NAME,
                    MediaStore.Video.Media.DURATION,
                    MediaStore.Video.Media.SIZE
            };
        }

        ContentResolver mContentResolver = Options.get().getContentResolver();

        Cursor mCursor = mContentResolver.query(uri, projection,
                selection, selectionArgs,
                MediaStore.Images.Media.DATE_MODIFIED + " desc");

        if (mCursor != null) {
            if (mCursor.moveToFirst()) {
                do {
                    String path;
                    String size;
                    if (type == URI_TYPE.IMG) {
                        // 获取图片的路径
                        path = mCursor.getString(mCursor
                                .getColumnIndex(MediaStore.Images.Media.DATA));
                        size = mCursor.getString(mCursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE));

                        MediaItemBean bean = new MediaItemBean(MimeType.IMG.getTypeName(), "", path, size);
                        String folderPath = getFolderPath(path);
                        putFile2Folder(map, folderPath, bean);
                        put2AllPic(map, bean);
                    } else {
                        try {
                            path = mCursor.getString(mCursor.getColumnIndex(MediaStore.Video.Media.DATA));
                            String duration = mCursor.getString(mCursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION));
                            size = mCursor.getString(mCursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE));

                            MediaItemBean bean = new MediaItemBean(MimeType.VIDEO.getTypeName(), String.valueOf(Long.parseLong(duration) / 1000), path, size);
                            String folderPath = getFolderPath(path);
                            putFile2Folder(map, folderPath, bean);
                            put2AllVideo(map, bean);
                        } catch (IllegalArgumentException e) {
                            e.printStackTrace();
                        }
                    }
                } while (mCursor.moveToNext());
            }
            mCursor.close();
        }
        return map;
    }

    private static void put2AllPic(HashMap<String, ArrayList<MediaItemBean>> map, MediaItemBean bean) {
        ArrayList<MediaItemBean> list = map.get(GlobalKey.ALL_PIC);
        if (list == null) {
            list = new ArrayList<>();
            list.add(bean);
            map.put(GlobalKey.ALL_PIC, list);
        } else {
            list.add(bean);
        }
    }

    private static void put2AllVideo(HashMap<String, ArrayList<MediaItemBean>> map, MediaItemBean bean) {
        ArrayList<MediaItemBean> list = map.get(GlobalKey.ALL_VIDEO);
        if (list == null) {
            list = new ArrayList<>();
            list.add(bean);
            map.put(GlobalKey.ALL_VIDEO, list);
        } else {
            list.add(bean);
        }
    }

    private static void putFile2Folder(HashMap<String, ArrayList<MediaItemBean>> map, String path, MediaItemBean bean) {
        ArrayList<MediaItemBean> list = map.get(path);
        if (list == null) {
            list = new ArrayList<>();
            list.add(bean);
            map.put(path, list);
        } else {
            list.add(bean);
        }
    }

    public static String getFolderName(String path) {
        if (GlobalKey.ALL_PIC.equals(path)) {
            return "所有图片";
        } else if (GlobalKey.ALL_VIDEO.equals(path)) {
            return "所有视频";
        } else {
            return path.substring(path.lastIndexOf(File.separator) + 1, path.length());
        }
    }

    public static ArrayList<String> getFolderList(HashMap<String, ArrayList<MediaItemBean>> map) {
        ArrayList<String> list = new ArrayList<>();
        for (String st : map.keySet()) {
            if (GlobalKey.ALL_PIC.equals(st)) {
                list.add("所有图片");
            } else if (GlobalKey.ALL_VIDEO.equals(st)) {
                list.add("所有视频");
            } else {
                list.add(getFolderName(st));
            }
        }
        return list;
    }

    public static ArrayList<String> getFolderPathList(HashMap<String, ArrayList<MediaItemBean>> map) {
        ArrayList<String> list = new ArrayList<>();
        for (String path : map.keySet()) {
            if (GlobalKey.ALL_PIC.equals(path)) {
                list.add(0, path);
            } else if (GlobalKey.ALL_VIDEO.equals(path)) {
                list.add(0, path);
            } else {
                list.add(path);
            }
        }
        return list;
    }

    public static String getFolderPath(String path) {
        if (path == null || TextUtils.isEmpty(path)) return "";

        File file = new File(path);
        File parentFile = file.getParentFile();
        if (parentFile != null) {
            return parentFile.getAbsolutePath();
        } else {
            return "";
        }
    }
}
