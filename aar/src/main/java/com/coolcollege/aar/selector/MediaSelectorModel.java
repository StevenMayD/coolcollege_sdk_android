package com.coolcollege.aar.selector;

public class MediaSelectorModel {

    private static final int SELECT_FILE_MAX_SIZE = 500; // 500m

    public static String getEmptyPickListText(String type) {
        StringBuilder sb = new StringBuilder();
        sb.append("请选择");
        if (MediaSelector.TYPE_IMG.equals(type)) {
            sb.append("图片");
        } else if (MediaSelector.TYPE_VIDEO.equals(type)) {
            sb.append("视频");
        }
        return sb.toString();
    }

    public static boolean checkFileSize(String size) {
        long mb = Long.parseLong(size) / 1024 / 1024;
        return mb < SELECT_FILE_MAX_SIZE;
    }

    public static String getRemindText(int count, String type) {
        StringBuilder sb = new StringBuilder();
        sb.append("最多只能选择");
        sb.append(count);
        if (MediaSelector.TYPE_IMG.equals(type)) {
            sb.append("张");
            sb.append("图片");
        } else {
            sb.append("个");
            sb.append("视频");
        }
        return sb.toString();
    }

}
