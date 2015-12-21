package com.nbsp.queuer.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.nbsp.queuer.R;

/**
 * Linear layout width maximum width and height.
 */
public class BoundedLinearLayout extends LinearLayout {

    /** Поле класса, содержащее значение bounded width. */
    private final int mBoundedWidth;
    
    /** Поле класса, содержащее значение bounded height. */
    private final int mBoundedHeight;

    /** Поле класса, содержащее значение on soft keyboard listener. */
    private OnSoftKeyboardListener mOnSoftKeyboardListener;

    /**
     * Instantiates a new bounded linear layout.
     *
     * @param context the context
     */
    public BoundedLinearLayout(Context context) {
        super(context);
        mBoundedWidth = 0;
        mBoundedHeight = 0;
    }

    /**
     * Instantiates a new bounded linear layout.
     *
     * @param context the context
     * @param attrs the attrs
     */
    public BoundedLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BoundedView);
        mBoundedWidth = a.getDimensionPixelSize(R.styleable.BoundedView_bounded_width, 0);
        mBoundedHeight = a.getDimensionPixelSize(R.styleable.BoundedView_bounded_height, 0);
        a.recycle();
    }

    /* (non-Javadoc)
     * @see android.widget.LinearLayout#onMeasure(int, int)
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // Adjust width as necessary
        int measuredWidth = MeasureSpec.getSize(widthMeasureSpec);
        if(mBoundedWidth > 0 && mBoundedWidth < measuredWidth) {
            int measureMode = MeasureSpec.getMode(widthMeasureSpec);
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(mBoundedWidth, measureMode);
        }

        // Adjust height as necessary
        int measuredHeight = MeasureSpec.getSize(heightMeasureSpec);
        if(mBoundedHeight > 0 && mBoundedHeight < measuredHeight) {
            int measureMode = MeasureSpec.getMode(heightMeasureSpec);
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(mBoundedHeight, measureMode);
        }

        // Call on soft keyboard callbacks
        if (mOnSoftKeyboardListener != null) {
            final int newSpec = MeasureSpec.getSize(heightMeasureSpec);
            final int oldSpec = getMeasuredHeight();
            if (oldSpec > newSpec){
                mOnSoftKeyboardListener.onShown();
            } else {
                mOnSoftKeyboardListener.onHidden();
            }
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * Метод для установки значения поля класса on soft keyboard listener.
     *
     * @param onSoftKeyboardListener the new on soft keyboard listener
     */
    public void setOnSoftKeyboardListener(OnSoftKeyboardListener onSoftKeyboardListener) {
        mOnSoftKeyboardListener = onSoftKeyboardListener;
    }

    /**
     * The listener interface for receiving onSoftKeyboard events.
     * The class that is interested in processing a onSoftKeyboard
     * event implements this interface, and the object created
     * with that class is registered with a component using the
     * component's <code>addOnSoftKeyboardListener<code> method. When
     * the onSoftKeyboard event occurs, that object's appropriate
     * method is invoked.
     */
    public interface OnSoftKeyboardListener {
        
        /**
         * On shown.
         */
        void onShown();
        
        /**
         * On hidden.
         */
        void onHidden();
    }
}