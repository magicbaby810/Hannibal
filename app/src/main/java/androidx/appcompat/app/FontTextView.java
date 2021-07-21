package androidx.appcompat.app;

import android.app.PictureInPictureParams;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;


import java.util.PrimitiveIterator;

/**
 * @author liujia on 2019-08-12.
 */
public class FontTextView extends AppCompatTextView {

    private static final String NEW_FONT = "fonts/ZCOOLQingKeHuangYou-Regular.ttf";

    public FontTextView(Context context) {
        super(context);
    }

    public FontTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        replaceCustomFont();
    }


    private void replaceCustomFont() {
        Typeface typeface = getTypeface();
        int style = Typeface.NORMAL;
        if (typeface != null) {
            style = typeface.getStyle();
        }
        Typeface newTypeface = Typeface.createFromAsset(getContext().getAssets(), NEW_FONT);
        setTypeface(newTypeface, style);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return super.dispatchTouchEvent(event);
    }
}
