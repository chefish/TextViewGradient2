package com.fish.textviewgradient2;

import android.content.Context;
import android.graphics.EmbossMaskFilter;
import android.graphics.Point;
import android.graphics.Shader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

/**
 * https://android--examples.blogspot.com/2015/11/android-how-to-apply-textview-text.html
 */
public class MainActivity extends AppCompatActivity {
    private Context mContext;
    private RelativeLayout mRL;
    private TextView mTV;
    private Button mBTN;
    private RadioGroup mRG;
    private int mWidth;
    private int mHeight;
    private Random mRandom = new Random();
    private Shader shader;

    private GradientManager mGradientManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the application context
        mContext = getApplicationContext();

        // Get the widgets reference from XML layout
        mRL = (RelativeLayout) findViewById(R.id.rl);
        mTV = (TextView) findViewById(R.id.tv);
        mBTN = (Button) findViewById(R.id.btn);
        mRG = (RadioGroup) findViewById(R.id.rg);

        // Set a click listener for Button widget
        mBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the TextView width and height in pixels
                mWidth = mTV.getWidth();
                mHeight = mTV.getHeight();
                Point size = new Point(mWidth,mHeight);

                // Initializing a new instance of GradientManager class
                mGradientManager = new GradientManager(mContext,size);

                // Get a random indicator to define next gradient type
                int indicator = mRandom.nextInt(3);

                if(indicator == 0){
                    shader = mGradientManager.getRandomLinearGradient();
                    mTV.setText("Linear Gradient");
                }else if(indicator == 1){
                    shader = mGradientManager.getRandomRadialGradient();
                    mTV.setText("Radial Gradient");
                }else {
                    shader = mGradientManager.getRandomSweepGradient();
                    mTV.setText("Sweep Gradient");
                }

                /*
                    public void setLayerType (int layerType, Paint paint)
                        Specifies the type of layer backing this view. The layer can be LAYER_TYPE_NONE,
                        LAYER_TYPE_SOFTWARE or LAYER_TYPE_HARDWARE.

                        A layer is associated with an optional Paint instance that controls how the
                        layer is composed on screen.

                    Parameters
                        layerType : The type of layer to use with this view, must be one of
                            LAYER_TYPE_NONE, LAYER_TYPE_SOFTWARE or LAYER_TYPE_HARDWARE
                        paint : The paint used to compose the layer. This argument is optional and
                            can be null. It is ignored when the layer type is LAYER_TYPE_NONE
                */
                /*
                    public static final int LAYER_TYPE_SOFTWARE
                        Indicates that the view has a software layer. A software layer is backed by
                        a bitmap and causes the view to be rendered using Android's software rendering
                        pipeline, even if hardware acceleration is enabled.
                */
                mTV.setLayerType(View.LAYER_TYPE_SOFTWARE,null);

                /*
                    Paint
                        The Paint class holds the style and color information about how to draw
                        geometries, text and bitmaps.
                */
                /*
                    Shader
                        Known Direct Subclasses
                        BitmapShader, ComposeShader, LinearGradient, RadialGradient, SweepGradient

                        Shader is the based class for objects that return horizontal spans of colors
                        during drawing. A subclass of Shader is installed in a Paint calling
                        paint.setShader(shader). After that any object (other than a bitmap) that
                        is drawn with that paint will get its color(s) from the shader.
                */
                mTV.getPaint().setShader(shader);
            }
        });

        // Set a checked change listener for RadioGroup
        mRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.rb_normal) {
                    // Remove the mask filter
                    mTV.getPaint().setMaskFilter(null);
                } else if (i == R.id.rb_emboss) {
                    /*
                        MaskFilter
                            MaskFilter is the base class for object that perform transformations on an
                            alpha-channel mask before drawing it. A subclass of MaskFilter may be installed
                            into a Paint. Blur and emboss are implemented as subclasses of MaskFilter.
                    */
                    /*
                        public EmbossMaskFilter (float[] direction, float ambient, float specular,
                            float blurRadius)

                        Create an emboss maskfilter

                        Parameters
                            direction : array of 3 scalars [x, y, z] specifying the direction of the
                                light source
                            ambient : 0...1 amount of ambient light
                            specular : coefficient for specular highlights (e.g. 8)
                            blurRadius : amount to blur before applying lighting (e.g. 3)
                        Returns
                            the emboss maskfilter
                    */
                    EmbossMaskFilter embossFilter = new EmbossMaskFilter(
                            new float[]{1f, 5f, 1f}, // direction of the light source
                            0.8f, // ambient light between 0 to 1
                            8, // specular highlights
                            7f // blur before applying lighting
                    );
                    // Apply the emboss mask filter
                    mTV.getPaint().setMaskFilter(embossFilter);
                } else if (i == R.id.rb_deboss) {
                    EmbossMaskFilter debossFilter = new EmbossMaskFilter(
                            new float[]{0f, -1f, 0.5f}, // direction of the light source
                            0.8f, // ambient light between 0 to 1
                            13, // specular highlights
                            7.0f // blur before applying lighting
                    );
                    // Apply the deboss mask filter
                    mTV.getPaint().setMaskFilter(debossFilter);
                }
            }
        });
    }
}