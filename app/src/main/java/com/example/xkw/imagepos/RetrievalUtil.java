package com.example.xkw.imagepos;

import android.database.Cursor;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xkw on 2018/6/14.
 */

public class RetrievalUtil {
    /**
     * @param locationKeyword learned from the message to query images
     * @return matched images
     */
    public static List<LocalImage> getImages(String locationKeyword){
        List<LocalImage> images = new ArrayList<>();
        // TODO:fetch image paths according to the location keyword from stored records
        // and then fetch the local images according to these paths.
        return images;

    }

    /**
     * @param periodOfTimes period of time to query images.
     * @return the matched images.
     */
    public static List<LocalImage> getImages(long[] periodOfTimes) {
        List<LocalImage> images = new ArrayList<>();

        Cursor c = ImgPosApplication
                        .getContext()
                        .getContentResolver()
                        .query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                new String[]{
                                        MediaStore.Images.Media.BUCKET_ID,
                                        MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                                        MediaStore.Images.Media._ID,
                                        MediaStore.Images.Media.DATE_ADDED,
                                        MediaStore.Images.Media.SIZE,
                                        MediaStore.Images.Media.DISPLAY_NAME,
                                        MediaStore.Images.Media.DATA
                                }, "(date_added >= ? AND date_added <= ?)",
                                new String[]{"" + periodOfTimes[0] / 1000, "" + periodOfTimes[1] / 1000}, null);

        if (c != null && c.moveToFirst()) {
            do {
                // Get the field values
                int bucketId = c.getInt(0);
                String bucketName = c.getString(1);
                int imageId = c.getInt(2);
                Long dateAdded = c.getLong(3) * 1000;
                long size = c.getLong(4);
                String imageName = c.getString(5);
                String filePath = c.getString(6);
                ImageData imageData = ImageData.newLocalImage(new File(filePath));
                LocalImage image = new LocalImage(dateAdded, imageData);
                image.setBUCKET_ID(bucketId);
                image.setBUCKET_NAME(bucketName);
                image.setIMAGE_ID(imageId);
                image.setIMAGE_NAME(imageName);
                image.setIMAGE_PATH(filePath);
                try {
                    final InputStream inputStream = ImgPosApplication.getContext().getContentResolver().openInputStream(Uri.fromFile(new File(filePath)));
                    final ExifInterface exif = new ExifInterface(inputStream);
                    final float[] latLong = new float[2];
                    if(exif.getLatLong(latLong)){
                        image.setLATITUDE(latLong[0]);
                        image.setLONGITUDE(latLong[1]);
                    }
                } catch (IOException e) {
                    Log.e("Error", "Error when getting location from image", e);
                }
                images.add(image);

            } while (c.moveToNext());
            c.close();
        }

        return images;
    }


    /**
     *
     * @return
     */
    public static List<LocalImage> getMostRecentImages() {
        List<LocalImage> images = new ArrayList<>();

        Cursor c =  ImgPosApplication
                        .getContext()
                        .getContentResolver()
                        .query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                new String[]{
                                        MediaStore.Images.Media.BUCKET_ID,
                                        MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                                        MediaStore.Images.Media._ID,
                                        MediaStore.Images.Media.DATE_ADDED,
                                        MediaStore.Images.Media.SIZE,
                                        MediaStore.Images.Media.DISPLAY_NAME,
                                        MediaStore.Images.Media.DATA
                                }, null,
                                new String[]{},
                                MediaStore.Images.Media.DATE_ADDED+" DESC");

        if (c != null && c.moveToFirst()) {
            do {
                // Get the field values
                int bucketId = c.getInt(0);
                String bucketName = c.getString(1);
                int imageId = c.getInt(2);
                Long dateAdded = c.getLong(3) * 1000;
                long size = c.getLong(4);
                String imageName = c.getString(5);
                String filePath = c.getString(6);
                ImageData imageData = ImageData.newLocalImage(new File(filePath));
                LocalImage image = new LocalImage(dateAdded, imageData);
                image.setBUCKET_ID(bucketId);
                image.setBUCKET_NAME(bucketName);
                image.setIMAGE_ID(imageId);
                image.setIMAGE_NAME(imageName);
                image.setIMAGE_PATH(filePath);
                //get the latitude and longitude cost al lot of time
                try {
                    final InputStream inputStream = ImgPosApplication.getContext().getContentResolver().openInputStream(Uri.fromFile(new File(filePath)));
                    final ExifInterface exif = new ExifInterface(inputStream);
                    final float[] latLong = new float[2];
                    if(exif.getLatLong(latLong)){
                        image.setLATITUDE(latLong[0]);
                        image.setLONGITUDE(latLong[1]);
                    }
                } catch (IOException e) {
                    Log.e("Error", "Error when getting location from image", e);
                }
                images.add(image);

            } while (c.moveToNext());
            c.close();
        }

        return images;
    }

    public static List<LocalImage> getMostRecentImages(int start, int end) {
        start = (start < 0) ? 0 : start;
        end = (end < start) ? start : end;

        List<LocalImage> images = new ArrayList<>();

        //MediaStore is in the package android.provider
        Cursor c = ImgPosApplication
                        .getContext()
                        .getContentResolver()
                        .query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            new String[]{
                                    MediaStore.Images.Media.BUCKET_ID,
                                    MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                                    MediaStore.Images.Media._ID,
                                    MediaStore.Images.Media.DATE_ADDED,
                                    MediaStore.Images.Media.SIZE,
                                    MediaStore.Images.Media.DISPLAY_NAME,
                                    MediaStore.Images.Media.DATA
                            }, null,
                            new String[]{},
                            MediaStore.Images.Media.DATE_ADDED+" DESC");

        int i = start;
        if (c != null) {
            c.move(start - 1);
            while (c.moveToNext() && (i < end)) {
                // Get the field values
                int bucketId = c.getInt(0);
                String bucketName = c.getString(1);
                int imageId = c.getInt(2);
                Long dateAdded = c.getLong(3) * 1000;
                long size = c.getLong(4);
                String imageName = c.getString(5);
                String filePath = c.getString(6);
                ImageData imageData = ImageData.newLocalImage(new File(filePath));
                LocalImage image = new LocalImage(dateAdded, imageData);
                image.setBUCKET_ID(bucketId);
                image.setBUCKET_NAME(bucketName);
                image.setIMAGE_ID(imageId);
                image.setIMAGE_NAME(imageName);
                image.setIMAGE_PATH(filePath);
                try {
                    final InputStream inputStream = ImgPosApplication.getContext().getContentResolver().openInputStream(Uri.fromFile(new File(filePath)));
                    final ExifInterface exif = new ExifInterface(inputStream);
                    final float[] latLong = new float[2];
                    if(exif.getLatLong(latLong)){
                        image.setLATITUDE(latLong[0]);
                        image.setLONGITUDE(latLong[1]);
                    }
                } catch (IOException e) {
                    Log.e("Error", "Error when getting location from image", e);
                }
                images.add(image);
                i++;
            }
            c.close();
        }

        return images;
    }
}
