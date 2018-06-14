package com.example.xkw.imagepos;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.Locale;


/**
 * An abstraction of image data.
 */

public class ImageData implements Serializable{
    private static final String LOG_TAG = "ImageData: ";

    private final int type;

    private static final int TYPE_LOCAL_FILE = 2;

    private transient File imageFile;
    private transient Bitmap bitmap;

    private String filePath;

    private ImageData(int type) {
        this.type = type;
    }

    public static ImageData newLocalImage(File localImageFile) {
        ImageData imageData = new ImageData(TYPE_LOCAL_FILE);
        imageData.imageFile = localImageFile;
        imageData.filePath = imageData.imageFile.getAbsolutePath();
        return imageData;
    }

    /*public String getFilepath() {
        if (this.filePath != null) return this.filePath;

        if (this.imageFile != null) {
            this.filePath = this.imageFile.getAbsolutePath();
        } else if (this.bitmap != null) {
            String imagePath = "temp/image_" + TimeUtils.getFormattedTimeString(TimeUtils.now(), SharedConstants.TIME_FILE_FORMAT, false) + ".jpg";
            this.imageFile = StorageUtils.getValidFile(getContext(), imagePath, false);
            try {
                FileOutputStream out = new FileOutputStream(this.imageFile);
                this.bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                out.flush();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.filePath = this.imageFile.getAbsolutePath();
        } else {
            Log.w(LOG_TAG, "Both file path and bitmap don't exist.");
        }

        return this.filePath;
    }

    public String toString() {
        return String.format(Locale.getDefault(), "<Image@%d%d>", this.type, this.hashCode());
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        if (this.type != TYPE_LOCAL_FILE) {
            if (this.imageFile != null && this.imageFile.exists()) {
                StorageUtils.safeDelete(this.imageFile);
            }
        }
    }

    public static File getValidFile(Context context, String filePath, boolean isPublic) {
        String dirPath, fileName;
        int lastPathSepIndex = filePath.lastIndexOf('/');
        if (lastPathSepIndex < 0) {
            dirPath = "";
            fileName = filePath;
        } else {
            dirPath = filePath.substring(0, lastPathSepIndex);
            fileName = filePath.substring(lastPathSepIndex + 1);
        }

        File dirFile;
        if (isPublic) {
            dirFile = getPublicDir(dirPath);
        } else {
            dirFile = getPrivateDir(context, dirPath);
        }

        return new File(dirFile, fileName);
    }*/

    public static File getPublicDir(String dirPath) {
        String fullDirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + dirPath;
        File targetDir = new File(fullDirPath);
        if (!targetDir.exists() && !targetDir.mkdirs()) {
            Log.e("", "fail to create dir: " + targetDir);
            return null;
        }
        return targetDir;
    }
}
