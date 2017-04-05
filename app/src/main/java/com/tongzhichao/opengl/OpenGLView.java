package com.tongzhichao.opengl;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.tongzhichao.example.R;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by tongzhichao on 17-3-29.
 */

public class OpenGLView extends FrameLayout {

    private static final String TAG = "OpenGLView";
    private GLSurfaceView mGLSurfaceView;
    private int mGLWidth, mGLHeight;
    ByteBuffer mByteBuffer = ByteBuffer.allocate(9*4);

    float[] triangleData = new float[]{
            0.1f,0.6f,0.0f,
            -0.3f,0.0f,0.0f,
            0.3f,0.1f,0.0f};
    int[] triangleColor = new int[]{
            65535,0,0,0,
            0,65535,0,0,
            0,0,65535,0
    };


    public OpenGLView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.opengl_view, this);

        mByteBuffer.asFloatBuffer();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mGLSurfaceView = new GLSurfaceView(getContext());
        addView(mGLSurfaceView, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        mGLSurfaceView.setRenderer(mRederer);
        mGLSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    private GLSurfaceView.Renderer mRederer = new GLSurfaceView.Renderer() {
        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            Log.d(TAG, "onSurfaceCreated");
            gl.glMatrixMode(GL10.GL_PROJECTION);
            gl.glLoadIdentity();
            gl.glFrustumf(-400, 400, -240, 240, 0.3f, 100);
            gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
            gl.glMatrixMode(GL10.GL_MODELVIEW);
            gl.glLoadIdentity();
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            Log.d(TAG, "onSurfaceChanged");
            mGLWidth = width;
            mGLHeight = height;
        }

        @Override
        public void onDrawFrame(GL10 gl) {
            Log.d(TAG, "onDrawFrame");
            gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
            gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

            ByteBuffer bb = ByteBuffer.allocateDirect(
                    triangleData.length * 4);
            bb.order(ByteOrder.nativeOrder());
            FloatBuffer floatBuffer = bb.asFloatBuffer();
            floatBuffer.put(triangleData);
            floatBuffer.position(0);
            gl.glVertexPointer(3, GL10.GL_FLOAT, 0, floatBuffer);
            bb = ByteBuffer.allocateDirect(
                    triangleColor.length * 4);
            bb.order(ByteOrder.nativeOrder());
            IntBuffer intBuffer = bb.asIntBuffer();
            intBuffer.put(triangleColor);
            intBuffer.position(0);
            gl.glColorPointer(4, GL10.GL_FLOAT, 0, intBuffer);

            gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);
        }
    };

}
