package org.app.opengl_es_android_version.util;

import android.content.Context;
import android.opengl.GLES20;
import android.util.Log;

import org.app.opengl_es_android_version.config.LoggerConfig;

import static android.opengl.GLES10.glGetError;
import static android.opengl.GLES20.GL_COMPILE_STATUS;
import static android.opengl.GLES20.GL_FRAGMENT_SHADER;
import static android.opengl.GLES20.GL_LINK_STATUS;
import static android.opengl.GLES20.GL_VALIDATE_STATUS;
import static android.opengl.GLES20.GL_VERTEX_SHADER;
import static android.opengl.GLES20.glAttachShader;
import static android.opengl.GLES20.glDeleteShader;
import static android.opengl.GLES20.glGetProgramInfoLog;
import static android.opengl.GLES20.glGetProgramiv;
import static android.opengl.GLES20.glGetShaderInfoLog;
import static android.opengl.GLES20.glGetShaderiv;
import static android.opengl.GLES20.glLinkProgram;

public class ShaderHelper {

    private static final String TAG = ShaderHelper.class.getSimpleName();

    public static int compileVertexShader(String shareCode) {
        return compileShader(GL_VERTEX_SHADER, shareCode);
    }

    public static int compileFragmentShader(String shadeCode) {
        return compileShader(GL_FRAGMENT_SHADER, shadeCode);
    }

    private static int compileShader(int type, String shaderCode) {
        final int shaderObjectId = GLES20.glCreateShader(type);
        if (shaderObjectId == 0) {
            if (LoggerConfig.ON) {
                Log.w(TAG, "Warning! Could not create new shader, glGetError:" + glGetError());
            }
            return 0;
        }
        GLES20.glShaderSource(shaderObjectId, shaderCode);

        GLES20.glCompileShader(shaderObjectId);

        final int[] compileStatus = new int[1];
        glGetShaderiv(shaderObjectId, GL_COMPILE_STATUS, compileStatus, 0);

        if (LoggerConfig.ON) {
            Log.i(TAG, "Result of compiling source:" + "\n" + shaderCode + "\n"
                    + glGetShaderInfoLog(shaderObjectId));
        }

        if (compileStatus[0] == 0) {
            glDeleteShader(shaderObjectId);
            if (LoggerConfig.ON) {
                String error = "Warning! Compilation of shader failed, glGetError:" + glGetError();
                Log.w(TAG, error);
            }
            return 0;
        }
        return shaderObjectId;
    }

    /***
     *  获取着色器语言的内容并加载到代码中
     * @param vertexShaderId
     * @param fragmentShaderId
     * @return
     */
    public static int linkProgram(int vertexShaderId, int fragmentShaderId) {
        final int programObjectId = GLES20.glCreateProgram();
        if (programObjectId == 0) {
            if (LoggerConfig.ON) {
                Log.w(TAG, " Warning! Could not create new program, glGetError:" + glGetError());
            }
            return 0;
        }

        glAttachShader(programObjectId, vertexShaderId);
        glAttachShader(programObjectId, fragmentShaderId);

        glLinkProgram(programObjectId);

        final int[] linkStatus = new int[1];
        glGetProgramiv(programObjectId, GL_LINK_STATUS, linkStatus, 0);

        if (LoggerConfig.ON) {
            Log.i(TAG, "Result of linking program:"
                    + GLES20.glGetProgramInfoLog(programObjectId));
        }

        if (linkStatus[0] == 0) {
            GLES20.glDeleteProgram(programObjectId);
            if (LoggerConfig.ON) {
                Log.w(TAG, " Warning! Linking of program failed, glGetError:" + glGetError());
            }
            return 0;
        }

        return programObjectId;
    }

    //效验程序是否可用
    public static boolean validateProgram(int programObjectId) {
        GLES20.glValidateProgram(programObjectId);

        final int[] validateStatus = new int[1];
        GLES20.glGetProgramiv(programObjectId, GL_VALIDATE_STATUS, validateStatus, 0);

        if (LoggerConfig.ON) {
            Log.i(TAG, "Result of validating program:" + validateStatus[0]
                    + "\nLog:" + glGetProgramInfoLog(programObjectId));
        }

        return validateStatus[0] != GLES20.GL_FALSE;
    }


    /***
     * 链接glsl
     * @param context
     * @param vertexShaderSourceRawId
     * @param fragmentShaderSourceRawId
     * @return
     */
    public static int buildProgram(Context context, int vertexShaderSourceRawId, int fragmentShaderSourceRawId) {
        int programObjectId;
        String vertexShaderSource = TextResourceReader.readTextFileFromResource(context, vertexShaderSourceRawId);
        String fragmentShaderSource = TextResourceReader.readTextFileFromResource(context, fragmentShaderSourceRawId);

        int vertexShader = compileVertexShader(vertexShaderSource);
        int fragmentShader = compileFragmentShader(fragmentShaderSource);

        programObjectId = linkProgram(vertexShader, fragmentShader);

        if (LoggerConfig.ON) {
            validateProgram(programObjectId);
        }

        return programObjectId;
    }

    public static int buildProgram(String vertexShaderSource, String fragmentShaderSource) {
        int programObjectId;

        int vertexShader = compileVertexShader(vertexShaderSource);
        int fragmentShader = compileFragmentShader(fragmentShaderSource);

        programObjectId = linkProgram(vertexShader, fragmentShader);

        if (LoggerConfig.ON) {
            validateProgram(programObjectId);
        }

        return programObjectId;
    }


}
