package org.app.opengl_es_android_version.util;

import android.content.Context;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TextResourceReader {

    /**
     * 从原生文件读入glsl
     * @param context
     * @param resourceId
     * @return
     */
    public static String readTextFileFromResource(Context context, int resourceId){
        StringBuilder builder = new StringBuilder();

        try {
            InputStream inputStream = context.getResources().openRawResource(resourceId);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String nextLine;

            while ((nextLine = bufferedReader.readLine()) != null) {
                builder.append(nextLine);
                builder.append('\n');
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return builder.toString();
    }
}
