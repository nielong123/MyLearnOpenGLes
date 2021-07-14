package org.app.opengl_es_android_version.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.util.Log;

import org.app.opengl_es_android_version.R;

import java.util.ArrayList;
import java.util.List;

public class TextureHelper {

    private static final String TAG = TextureHelper.class.getSimpleName();

    public static int loadTexture(Context context, int resId) {
        final int[] textureObjectIds = new int[1];
        GLES20.glGenTextures(1, textureObjectIds, 0);

        if (textureObjectIds[0] == 0) {
            Log.e(TAG, "Could not generate a new OpenGL texture object!");
            return 0;
        }

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;   //指定需要的是原始数据，非压缩数据
        final Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resId, options);
        if (bitmap == null) {
            Log.e(TAG, "Resource ID " + resId + "could not be decode");
            GLES20.glDeleteTextures(1, textureObjectIds, 0);
            return 0;
        }
//        GLES20.glEnable(GLES20.GL_TEXTURE_2D);
        //激活纹理单元0
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
//        //告诉OpenGL后面纹理调用应该是应用于哪个纹理对象
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureObjectIds[0]);

//        GLES20.glDisable(GLES20.GL_TEXTURE_2D);
        //设置缩小的时候（GL_TEXTURE_MIN_FILTER）使用mipmap三线程过滤
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR_MIPMAP_LINEAR);
        //设置放大的时候（GL_TEXTURE_MAG_FILTER）使用双线程过滤
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
        //Android设备y坐标是反向的，正常图显示到设备上是水平颠倒的，解决方案就是设置纹理包装，纹理T坐标（y）设置镜面重复
        //ball读取纹理的时候  t范围坐标取正常值+1
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_MIRRORED_REPEAT);

        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);

        //快速生成mipmap贴图
        GLES20.glGenerateMipmap(GLES20.GL_TEXTURE_2D);

        bitmap.recycle();

        //解除纹理操作的绑定
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);

        return textureObjectIds[0];
    }

    public static int[] loadMultipleTexture(Context context, int resId, int textureNum) {
//        imagePathList.add(Environment.getExternalStorageDirectory().getAbsolutePath()+"/DCIM");
        final int[] textureObjectIds = new int[textureNum];
        GLES20.glGenTextures(textureNum, textureObjectIds, 0);

//        if (textureObjectIds[0] == 0) {
//            Log.e(TAG, "Could not generate a new OpenGL texture object!");
//            return null;
//        }

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;   //指定需要的是原始数据，非压缩数据
        List<Bitmap> bitmapList = new ArrayList<>();
        bitmapList.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.map1, options));
        bitmapList.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.map2, options));
        bitmapList.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.map3, options));
        bitmapList.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.map4, options));
        bitmapList.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.map5, options));
//        if (bitmap1 == null) {
//            Log.e(TAG, "Resource ID " + resId + "could not be decode");
//            GLES20.glDeleteTextures(1, textureObjectIds, 0);
//            return null;
//        }
//        GLES20.glEnable(GLES20.GL_TEXTURE_2D);
        //告诉OpenGL后面纹理调用应该是应用于哪个纹理对象
        int imageIndex = 0;
        for (int i = 0; i < textureNum; i++) {
            imageIndex++;
            if (imageIndex > (bitmapList.size() - 1)) {
                imageIndex = 0;
            }
            //激活纹理单元0
            GLES20.glActiveTexture(GLES20.GL_TEXTURE0 + i);
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureObjectIds[i]);
            //设置缩小的时候（GL_TEXTURE_MIN_FILTER）使用mipmap三线程过滤
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR_MIPMAP_LINEAR);
            //设置放大的时候（GL_TEXTURE_MAG_FILTER）使用双线程过滤
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
            //Android设备y坐标是反向的，正常图显示到设备上是水平颠倒的，解决方案就是设置纹理包装，纹理T坐标（y）设置镜面重复
            //ball读取纹理的时候  t范围坐标取正常值+1
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_MIRRORED_REPEAT);

            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmapList.get(imageIndex), 0);
            //快速生成mipmap贴图
            GLES20.glGenerateMipmap(GLES20.GL_TEXTURE_2D);
            //解除纹理操作的绑定
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, GLES20.GL_NONE);
        }

        for (Bitmap bitmap : bitmapList) {
            bitmap.recycle();
        }
        bitmapList.clear();

        return textureObjectIds.clone();
    }
}
