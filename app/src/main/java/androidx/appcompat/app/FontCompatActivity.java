package androidx.appcompat.app;

import android.content.ComponentName;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.R;
import androidx.appcompat.widget.VectorEnabledTintResources;
import androidx.core.view.ViewCompat;

import org.xmlpull.v1.XmlPullParser;

/**
 * @author liujia on 2019-08-12.
 */
public class FontCompatActivity extends AppCompatActivity  {


    private AppCompatDelegate appCompatDelegate;


    @NonNull
    @Override
    public AppCompatDelegate getDelegate() {

        if (null == appCompatDelegate) {
            appCompatDelegate = new CustomCompatDelegate(this, getWindow(), this);
        }

        return appCompatDelegate;
        //return super.getDelegate();
    }

    private class CustomCompatDelegate extends AppCompatDelegateImpl {


        private final boolean IS_PRE_LOLLIPOP = Build.VERSION.SDK_INT < 21;

        private CustomAppCompatViewInflater customAppCompatViewInflater;

        CustomCompatDelegate(Context context, Window window, AppCompatCallback callback) {
            super(context, window, callback);
        }


        @Override
        public View createView(View parent, String name, @NonNull Context context, @NonNull AttributeSet attrs) {

            if (customAppCompatViewInflater == null) {
                TypedArray a = mContext.obtainStyledAttributes(R.styleable.AppCompatTheme);
                String viewInflaterClassName =
                        a.getString(R.styleable.AppCompatTheme_viewInflaterClass);
                if ((viewInflaterClassName == null)
                        || AppCompatViewInflater.class.getName().equals(viewInflaterClassName)) {
                    // Either default class name or set explicitly to null. In both cases
                    // create the base inflater (no reflection)
                    customAppCompatViewInflater = new CustomAppCompatViewInflater();
                } else {
                    try {
                        Class viewInflaterClass = Class.forName(viewInflaterClassName);
                        customAppCompatViewInflater =
                                (CustomAppCompatViewInflater) viewInflaterClass.getDeclaredConstructor()
                                        .newInstance();
                    } catch (Throwable t) {
                        Log.i(TAG, "Failed to instantiate custom view inflater "
                                + viewInflaterClassName + ". Falling back to default.", t);
                        customAppCompatViewInflater = new CustomAppCompatViewInflater();
                    }
                }
            }

            boolean inheritContext = false;
            if (IS_PRE_LOLLIPOP) {
                inheritContext = (attrs instanceof XmlPullParser)
                        // If we have a XmlPullParser, we can detect where we are in the layout
                        ? ((XmlPullParser) attrs).getDepth() > 1
                        // Otherwise we have to use the old heuristic
                        : shouldInheritContext((ViewParent) parent);
            }

            return customAppCompatViewInflater.createView(parent, name, context, attrs, inheritContext,
                    IS_PRE_LOLLIPOP, /* Only read android:theme pre-L (L+ handles this anyway) */
                    true, /* Read read app:theme as a fallback at all times for legacy reasons */
                    VectorEnabledTintResources.shouldBeUsed() /* Only tint wrap the context if enabled */
            );

        }


        private boolean shouldInheritContext(ViewParent parent) {
            if (parent == null) {
                // The initial parent is null so just return false
                return false;
            }
            final View windowDecor = mWindow.getDecorView();
            while (true) {
                if (parent == null) {
                    // Bingo. We've hit a view which has a null parent before being terminated from
                    // the loop. This is (most probably) because it's the root view in an inflation
                    // call, therefore we should inherit. This works as the inflated layout is only
                    // added to the hierarchy at the end of the inflate() call.
                    return true;
                } else if (parent == windowDecor || !(parent instanceof View)
                        || ViewCompat.isAttachedToWindow((View) parent)) {
                    // We have either hit the window's decor view, a parent which isn't a View
                    // (i.e. ViewRootImpl), or an attached view, so we know that the original parent
                    // is currently added to the view hierarchy. This means that it has not be
                    // inflated in the current inflate() call and we should not inherit the context.
                    return false;
                }
                parent = parent.getParent();
            }
        }
    }




}
