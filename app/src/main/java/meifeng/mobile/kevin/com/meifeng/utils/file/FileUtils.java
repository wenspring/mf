package meifeng.mobile.kevin.com.meifeng.utils.file;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Response;

/**
 * @文件名称 : FileUtils.java
 * @文件描述 : 文件工具类
 */
public class FileUtils {
    /**
     * 读取表情配置文件
     *
     * @param data
     * @return
     */
//	public static List<String> getEmojiFile(Context context) {
//		try {
//			List<String> list = new ArrayList<String>();
//			InputStream in = context.getResources().getAssets().open("emoji");//读取资源文件.txt
//			BufferedReader br = new BufferedReader(new InputStreamReader(in,
//					"UTF-8"));
//			String str = null;
//			while ((str = br.readLine()) != null) {
//				list.add(str);
//			}
//
//			return list;
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
    public static String saveBitmapToLocal(Intent data) {
        Bundle extras = data.getExtras();
        Bitmap bitmap = null;
        if (extras != null) {
            bitmap = extras.<Bitmap>getParcelable("data");
            // String path = saveBitmapToLocal(bitmap);
        }
        // 存放照片的文件夹
        String strImgPath = getSavePath();
        // 照片命名
        String fileName = getCreateFileName();
        File out = new File(strImgPath);
        if (!out.exists()) {
            // 创建文件夹
            out.mkdirs();
        }
        out = new File(strImgPath, fileName);
        try {
            out.createNewFile();
            FileOutputStream fos = new FileOutputStream(out);
            fos.flush();
            bitmap.compress(CompressFormat.JPEG, 100, fos);
            fos.close(); // 这里有问题需要反复查看
        } catch (IOException e) {
            e.printStackTrace();
        }
        return strImgPath + fileName;
    }

    public static String getSavePath() {
//        String strImgPath = getSavePath(); //文件保存文件夾
//        File file = new File(strImgPath);
//        if (!file.exists()) { // 若是文件夾不存在，创建文件夹
//            boolean b = file.mkdirs();
//        }
        return Environment.getExternalStorageDirectory().toString()
                + "/android/meifeng/data/";
    }

    public static String getDatabasePath() {
        return Environment.getExternalStorageDirectory().toString()
                + "/android/meifeng/database/";
    }

    // image
    @SuppressLint("SimpleDateFormat")
    public static String getCreateFileName() {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())
                + ".jpg";
    }

    // audio file name
    @SuppressLint("SimpleDateFormat")
    public static String getCreateAudioFileName() {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())
                + "_audio.amr";
    }

    // 获取 audio file abs path
    public static String getAudioFileAbsPath() {
        return new File(getSavePath() + getCreateAudioFileName()).getAbsolutePath();
    }

    public static String saveBitmapToLocal(Bitmap mBitmap) {
        //文件保存文件夾
        String strImgPath = getSavePath();
        // 照片命名
        String fileName = "meifeng_" + getCreateFileName();
        if (mBitmap == null) {
            return "";
        }
        File file = new File(strImgPath);
        // 若是文件夾不存在，创建文件夹
        if (!file.exists()) {
            boolean b = file.mkdirs();
        }
        file = new File(strImgPath + fileName);
        try {
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file); // 寫入文件
            fos.flush();//刷新緩衝
            mBitmap.compress(CompressFormat.JPEG, 100, fos);
            fos.close();//關閉流
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return strImgPath + fileName;
    }

    /**
     * 文件生产bitmap
     *
     * @param pathName
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static Bitmap decodeSampledBitmapFromFile(String pathName, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathName, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight); // 防止图片过大，导致OOM发生
        options.inJustDecodeBounds = false;
        Bitmap src = BitmapFactory.decodeFile(pathName, options);
        return src;
    }

    /**
     * 生成 bitmap
     *
     * @param pathName
     * @return
     */
    public static Bitmap decodeSampledBitmapFromFile(String pathName) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathName, options);
        options.inSampleSize = 1; // 原图大小,
        options.inJustDecodeBounds = false;
        Bitmap src = BitmapFactory.decodeFile(pathName, options);
        return src;
    }

    /**
     * 生成 bitmap
     *
     * @param pathName
     * @return
     */
    public static Bitmap decodeSampledBitmapFromFileSmall(String pathName) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathName, options);
        options.inSampleSize = calculateInSampleSize(options,800,800);// 缩小
        options.inJustDecodeBounds = false;
        Bitmap src = BitmapFactory.decodeFile(pathName, options);
        return src;
    }

    /**
     * 压缩图片
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // 源图片的高度和宽度
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    /**
     * @param contentUri
     * @param act
     * @return
     */
    public static String getRealPathFromURI(Uri contentUri, Activity act) {
        String[] proj = {MediaStore.Audio.Media.DATA};
        Cursor cursor = act.managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    /*
        读取流，生产file audio
     */
    public static String getNetFileAudioPath(Response response) {
        InputStream is = null;
        byte[] buf = new byte[1024];
        int len = 0;
        FileOutputStream fos = null;
        //储存下载文件的目录
        String savePath = "";
        try {
            savePath = isExistDir(getSavePath());
            is = response.body().byteStream();
            long total = response.body().contentLength();
            File file = new File(savePath, FileUtils.getCreateAudioFileName()); // getNameFromUrl(url)
            fos = new FileOutputStream(file);
            long sum = 0;
            while ((len = is.read(buf)) != -1) {
                fos.write(buf, 0, len);
                sum += len;
            }
            fos.flush();
            return file.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (IOException e) {

            }
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {

            }
        }
        return savePath;
    }

    /*
        读取流，生产file图片
     */
    public static String getNetFilePath(Response response) {
        InputStream is = null;
        byte[] buf = new byte[1024];
        int len = 0;
        FileOutputStream fos = null;
        //储存下载文件的目录
        String savePath = "";
        try {
            savePath = isExistDir(getSavePath());
            is = response.body().byteStream();
            long total = response.body().contentLength();
            File file = new File(savePath, FileUtils.getCreateFileName()); // getNameFromUrl(url)
            fos = new FileOutputStream(file);
            long sum = 0;
            while ((len = is.read(buf)) != -1) {
                fos.write(buf, 0, len);
                sum += len;
                //int progress = (int) (sum * 1.0f / total * 100);
                //下载中
//				Message message = Message.obtain();
//				message.what = DOWNLOAD_PROGRESS;
//				message.obj = progress;
//				mHandler.sendMessage(message);

            }
            fos.flush();
            //下载完成
//			Message message = Message.obtain();
//			message.what = DOWNLOAD_SUCCESS;
//			message.obj = file.getAbsolutePath();
//			mHandler.sendMessage(message);
            return file.getAbsolutePath();
        } catch (Exception e) {
//			Message message = Message.obtain();
//			message.what = DOWNLOAD_FAIL;
//			mHandler.sendMessage(message);
            e.printStackTrace();
        } finally {
            try {
                if (is != null)
                    is.close();
            } catch (IOException e) {

            }
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {

            }
        }
        return savePath;
    }

    // 目录是否存在，不存在则创建
    public static String isExistDir(String saveDir) throws IOException {
        File downloadFile = new File(saveDir);
        if (!downloadFile.mkdirs()) {
            downloadFile.createNewFile();
        }
        String savePath = downloadFile.getAbsolutePath();
        return savePath;
    }

    // bitmap 读取旋转的角度
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 获得文件后缀
     *
     * @param filename
     * @return
     */
    public static String getExt(String filename) {
        return filename.substring(filename.lastIndexOf('.') + 1).toLowerCase();
    }

}
