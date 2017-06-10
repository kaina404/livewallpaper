package xjh.livewallpaper.common;

import android.app.ActivityManager;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.DisplayMetrics;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import xjh.livewallpaper.listener.ProgressResponseListener;

/**
 * Created by lovexujh on 2017/5/17
 */

public class Tool {

    /**
     * 更清晰
     *
     * @param videoPath
     * @return
     */
    public static Bitmap getVideoThumbnail(String videoPath) {
        MediaMetadataRetriever media = new MediaMetadataRetriever();
        media.setDataSource(videoPath);
        Bitmap bitmap = media.getFrameAtTime();
        return bitmap;
    }

    public static Bitmap getVideoThumbnail(String videoPath, int width, int height, int kind) {
        Bitmap bitmap = null;
        bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, kind);
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        return bitmap;
    }

    public static ArrayList<String> getMp4FilePath(Context mContext) {
        ArrayList<String> list = getMediaFilePath(mContext);
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            String s = list.get(i);
            if (s.toLowerCase().endsWith(".mp4")) {
                arrayList.add(s);
            }

        }
        return arrayList;
    }

    public static ArrayList<String> getMediaFilePath(Context mContext) {
        ArrayList<String> arrayList = new ArrayList<>();
        ContentResolver contentResolver = mContext.getContentResolver();
        String[] projection = new String[]{MediaStore.Video.Media.DATA};
        Cursor cursor = contentResolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, projection,
                null, null, MediaStore.Video.Media.DEFAULT_SORT_ORDER);
        cursor.moveToFirst();
        int fileNum = cursor.getCount();

        for (int counter = 0; counter < fileNum; counter++) {
            arrayList.add(cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA)));
            cursor.moveToNext();
        }
        cursor.close();
        return arrayList;
    }

    public static String Base64(String paramString) {
        if ((paramString == null) || (paramString.length() == 0)) {
            paramString = null;
        }
        try {
            byte[] arrayOfByte = paramString.getBytes("UTF-8");

            paramString = new String(Base64.encode(arrayOfByte, 0, arrayOfByte.length, 2), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            paramString = null;
        }
        return paramString;
    }

    public static String c(String paramString) {
        if ((paramString == null) || (paramString.length() < 1)) {
            paramString = "no_image.gif";
        }
        StringBuilder sb = new StringBuilder();
        try {
            byte[] arrayOfByte = MessageDigest.getInstance("MD5").digest(paramString.getBytes());
//            sb = new StringBuilder();
//            sb = "40";
            int j = arrayOfByte.length;
            int i = 0;
            if (i < j) {
                int k = arrayOfByte[i];
                if ((k & 0xFF) >> 4 == 0) {
                    sb.append("0").append(Integer.toHexString(k & 0xFF));
                }
                for (; ; ) {
                    i++;
                    sb.append(Integer.toHexString(k & 0xFF));
                    break;

                }
            }
        } catch (NoSuchAlgorithmException e) {
            paramString = "no_image.gif";
        }
        if (paramString.length() < 24) {
            paramString = sb.toString();
        } else {
            paramString = sb.toString();
        }
        return paramString;

    }

    public static boolean isEmpty(String s) {

        return s == null || s.length() == 0;
    }

    public static boolean isEmpty(String[] s) {
        return s == null || s.length == 0;
    }


    public static String getCacheDir() {
        String path;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_UNMOUNTED)) {
            path = Environment.getDownloadCacheDirectory().getAbsolutePath();
        } else {
            path = Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        return path;
    }

    /**
     * 分割 http://img1.huoying666.com/video/20170410/2/19_5baa9aae49c6713d0fe0962e1765aa12_pre.MP4_m3u8/video.m3u8
     * 去掉末尾的_m3u8/video.m3u8
     *
     * @param url
     * @return
     */
    public static String splitSpecMp4Url(String url) {
        String tmpUrl = "";
        String lastUrl = url;
        if (!isEmpty(url)) {
            tmpUrl = lastUrl.substring(0, url.toLowerCase().lastIndexOf(".mp4") + 4);
        }
        return tmpUrl;
    }


    public static String getMP4DownloadFilePath() {
        return getDefaultPath() + "mp4";
    }

    public static String getDefaultPath() {
        String path = Environment.getExternalStorageDirectory().getPath() + "/wallpapers/";
        File dir = new File(path);
        if (!dir.exists()) {
            try {
                dir.mkdir();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return path;
    }

    public static String createDownloadFilePath(String url) {
        //   /data/wallpapers/
        String fileDownloadPath = Tool.getDefaultPath() + Tool.getUrlType(url);
        File dir = new File(fileDownloadPath);
        boolean isCreateFileSuc = true;
        if (!dir.exists()) {
            try {
                dir.mkdir();
            } catch (Exception e) {
                e.printStackTrace();
                isCreateFileSuc = false;
            }
        }

        File file = new File(fileDownloadPath + "/" + getUrlName(url));
        boolean isCreateDirSuc = true;
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
                isCreateDirSuc = false;
            }
        }
        return isCreateDirSuc && isCreateFileSuc ? file.getAbsolutePath() : "";
    }

    public static String getDownloadFileAbsPath(String url) {
        return Tool.getDefaultPath() + Tool.getUrlType(url) + "/" + getUrlName(url);
    }

    /**
     * 1.mp4
     *
     * @param url
     * @return
     */
    public static String getUrlName(String url) {
        if (isEmpty(url)) {
            return "";
        } else {
            String tmp = url.substring(url.lastIndexOf("/") + 1);
            if (tmp.contains("?")) {
                return tmp.substring(0, tmp.indexOf("?"));
            } else {
                return tmp;
            }
        }
    }

    /**
     * mp4
     *
     * @param url
     * @return
     */
    public static String getUrlType(String url) {
        if (isEmpty(url)) {
            return "";
        } else {
            String tmp = getUrlName(url);
            return tmp.substring(tmp.indexOf(".") + 1);
        }
    }


    public static boolean serviceIsRunning(Context context, String serviceName) {
        List<ActivityManager.RunningServiceInfo> list = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getRunningServices(40);
        if (list == null || list.size() == 0) {
            return false;
        }
        for (int i = 0; i < list.size(); i++) {
            if (((ActivityManager.RunningServiceInfo) list.get(i)).service.getClassName().toString().equals(serviceName)) {
                return true;
            }
        }
        return false;

    }

    public static boolean isEmpty(List urls) {
        return urls == null || urls.size() == 0;
    }

    public static int getMax(int[] values) {
        if (values.length == 0) {
            throw new IllegalArgumentException("values must be greater than zero");
        }
        int max = values[0];
        for (int i = 1; i < values.length; i++) {
            if (values[i] > max) {
                max = values[i];
            }
        }
        return max;
    }


    public static boolean inputStream2File(InputStream ins, File file, ProgressResponseListener progressListener, double all) {
        FileOutputStream os;
        try {
            os = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        if (os == null) {
            return false;
        }
        int bytesRead;
        byte[] buffer = new byte[8192];
        try {
            if (progressListener == null) {
                while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }
            } else {
                double read = 0;//已经写入的
                while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                    os.write(buffer, 0, bytesRead);
                    read = read + bytesRead;
                    progressListener.onResponseProgress(read, all);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            if (ins != null) {
                try {
                    ins.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

        return true;
    }

    public static int dp2px(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (int) ((dp * displayMetrics.density) + 0.5);
    }

    public static BigDecimal divide(String d1, String d2) {
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        return b1.divide(b2, 10, BigDecimal.ROUND_HALF_DOWN);
    }

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }


    /**
     * 相片按相框的比例动态缩放
     *
     * @param bmp       要缩放的图片
     * @param newWidth  模板宽度
     * @param newHeight 模板高度
     * @return
     */
    public static Bitmap upImageSize(Bitmap bmp, int newWidth, int newHeight) {
        if (bmp == null) {
            return null;
        }
        // 计算比例
        float scaleX = (float) newWidth / bmp.getWidth();// 宽的比例
        float scaleY = (float) newHeight / bmp.getHeight();// 高的比例
        //新的宽高
        int newW = 0;
        int newH = 0;
        if (scaleX > scaleY) {
            newW = (int) (bmp.getWidth() * scaleX);
            newH = (int) (bmp.getHeight() * scaleX);
        } else if (scaleX <= scaleY) {
            newW = (int) (bmp.getWidth() * scaleY);
            newH = (int) (bmp.getHeight() * scaleY);
        }
        return Bitmap.createScaledBitmap(bmp, newW, newH, true);
    }
}
