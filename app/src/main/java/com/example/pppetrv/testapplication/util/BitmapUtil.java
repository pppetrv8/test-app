package com.example.pppetrv.testapplication.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.location.Location;
import android.media.ExifInterface;
import android.net.Uri;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import timber.log.Timber;

/**
 * Utility class for work {@link Bitmap}.
 */
public final class BitmapUtil {

    private BitmapUtil() {
        /**/
    }

    public static final int DEFAULT_IMAGE_MAX_SIZE = 1024;

    /**
     * The method to calculate a sample size value that is a power of two based on a target
     * width and height.
     *
     * @param options   see {@link BitmapFactory.Options}
     * @param reqWidth  width of the surface where the {@link Bitmap} will be
     *                  drawn on, in pixels.
     * @param reqHeight height of the surface where the {@link Bitmap} will be
     *                  drawn on, in pixels.
     * @return the largest inSampleSize value that is a power of 2 and keeps both height and width
     * larger than reqWidth and reqHeight.
     */
    public static int calculateInSampleSize(final BitmapFactory.Options options,
                                            final int reqWidth, final int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            while ((halfHeight / inSampleSize) > reqHeight
                    || (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    public static void rotateImage(String filePath, int rotate) {
        try {
            Bitmap bm = BitmapFactory.decodeFile(filePath);
            Matrix matrix = new Matrix();
            matrix.postRotate(rotate, (float) bm.getWidth() / 2, (float) bm.getHeight() / 2);
            Bitmap rotatedBitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(),
                    matrix, true);
            bm.recycle();
            FileOutputStream fos = new FileOutputStream(filePath);
            rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 75, fos);
            fos.flush();
            fos.close();
            rotatedBitmap.recycle();
        } catch (Exception e) {
            Timber.e(e);
        }
    }

    private static int getOrientationFromExifInterface(Uri uri) {
        return getOrientationFromExifInterface(uri.getPath());
    }

    private static int getOrientationFromExifInterface(String filePath) {
        try {
            ExifInterface exif = new ExifInterface(filePath);
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_270:
                    return 270;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    return 180;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    return 90;
                case ExifInterface.ORIENTATION_NORMAL:
                    return 0;
                default:
                    return -1;
            }
        } catch (IOException e) {
            return -1;
        }
    }

    public static String checkPhotoOrientationUri(String filePath) {
        int rotate = getOrientationFromExifInterface(filePath);
        if (rotate >= 0) {
            BitmapUtil.rotateImage(filePath, rotate);
        }
        return filePath;
    }

    /**
     * The method decodes a bitmap from a file containing it minimizing the memory use, known that the bitmap
     * will be drawn in a surface of reqWidth x reqHeight.
     *
     * @param path      absolute path to the file containing the image.
     * @param reqWidth  width of the surface where the {@link Bitmap} will be
     *                  drawn on, in pixels.
     * @param reqHeight height of the surface where the {@link Bitmap} will be
     *                  drawn on, in pixels.
     * @return decoded {@link Bitmap}
     */
    public static Bitmap decodeBitmap(final String path,
                                      final int reqWidth, final int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth,
                reqHeight);
        options.inJustDecodeBounds = false;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inDither = true;
        return BitmapFactory.decodeFile(path, options);
    }


    /**
     * The method decodes an byte array from the specified {@link Bitmap}.
     *
     * @param bitmap see {@link Bitmap}
     * @return this {@link Bitmap} as a byte array
     */
    public static byte[] getBytes(final Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 75, stream);
        return stream.toByteArray();
    }

    /**
     * The method decodes an immutable bitmap from the specified byte array.
     *
     * @param image byte array of compressed image data
     * @return this byte array as a {@link Bitmap}
     */
    public static Bitmap getPhoto(final byte[] image) {
        return BitmapFactory.decodeByteArray(image, 75, image.length);
    }

    public static boolean storeGeoCoordsToImage(String imagePath, Location location) {
        File imageFile = new File(imagePath);
        return storeGeoCoordsToImage(imageFile, location);
    }

    public static boolean storeGeoCoordsToImage(File imagePath, Location location) {
        // Avoid NullPointer
        if (imagePath == null || location == null) {
            return false;
        }
        // If we use Location.convert(), we do not have to worry about absolute values.
        try {
            ExifInterface exif = new ExifInterface(imagePath.getAbsolutePath());
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            exif.setAttribute(ExifInterface.TAG_GPS_LATITUDE, getTagGeoCoordinate(latitude));
            exif.setAttribute(ExifInterface.TAG_GPS_LATITUDE_REF, latitude < 0 ? "S" : "N");
            exif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE, getTagGeoCoordinate(longitude));
            exif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF, longitude < 0 ? "W" : "E");
            exif.saveAttributes();
        } catch (Exception e) {
            // do something
            return false;
        }
        return true;
    }

    public static String getTagGeoCoordinate(double value) {
        if (value <= 0) {
            return "0/1,0/1,0/1000";
        }
        // You can adapt this to latitude very easily by passing location.getLatitude()
        String[] degMinSec = Location.convert(value, Location.FORMAT_SECONDS).split(":");
        return degMinSec[0] + "/1," + degMinSec[1] + "/1," + degMinSec[2] + "/1000";
    }
}
