package com.nbsp.queuer.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.LinearLayout;

import com.nbsp.queuer.R;


/**
 * Material item with icon.
 */

public class IconItemView extends LinearLayout {
    
    /** Поле класса, содержащее значение Constant MINIMUM_HEIGHT_DP. */
    private static final int MINIMUM_HEIGHT_DP = 72;
    
    /** Поле класса, содержащее значение Constant PADDING_DP. */
    private static final int PADDING_DP = 16;

    /** Поле класса, содержащее значение Constant ICON_TOP_MARGIN_DP. */
    private static final int ICON_TOP_MARGIN_DP = 24;
    
    /** Поле класса, содержащее значение Constant ICON_LEFT_MARGIN_DP. */
    private static final int ICON_LEFT_MARGIN_DP = 16;
    
    /** Поле класса, содержащее значение Constant ICON_RIGHT_MARGIN_DP. */
    private static final int ICON_RIGHT_MARGIN_DP = 20;
    
    /** Поле класса, содержащее значение Constant ICON_SIZE_DP. */
    private static final int ICON_SIZE_DP = 24;

    /** Поле класса, содержащее значение icon top margin. */
    private int mIconTopMargin = 0;

    /** Поле класса, содержащее значение icon. */
    private Drawable mIcon;

    /**
     * Instantiates a new icon item view.
     *
     * @param context the context
     */
    public IconItemView(Context context) {
        super(context);
        init(null);
    }

    /**
     * Instantiates a new icon item view.
     *
     * @param context the context
     * @param attrs the attrs
     */
    public IconItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    /**
     * Instantiates a new icon item view.
     *
     * @param context the context
     * @param attrs the attrs
     * @param defStyleAttr the def style attr
     */
    public IconItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    /**
     * Instantiates a new icon item view.
     *
     * @param context the context
     * @param attrs the attrs
     * @param defStyleAttr the def style attr
     * @param defStyleRes the def style res
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public IconItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    /* (non-Javadoc)
     * @see android.view.View#draw(android.graphics.Canvas)
     */
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        if (mIcon != null) {
            mIcon.draw(canvas);
        }
    }

    /**
     * Inits the IconItemView.
     *
     * @param attrs the attrs
     */
    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.IconItemView);

            Drawable icon = a.getDrawable(R.styleable.IconItemView_item_icon);
            mIconTopMargin = a.getDimensionPixelSize(R.styleable.IconItemView_item_icon_top_margin, 0);

            if (icon != null) {
                setIcon(icon);
            }

            a.recycle();
        }

        setWillNotDraw(false);
        setOrientation(VERTICAL);
        setMinimumHeight(dpToPx(MINIMUM_HEIGHT_DP));
        setPadding(dpToPx(ICON_LEFT_MARGIN_DP + ICON_SIZE_DP + ICON_RIGHT_MARGIN_DP), dpToPx(PADDING_DP), dpToPx(PADDING_DP), 0);
    }

    /**
     * Convert dp to px.
     *
     * @param dp the dp
     * @return the int
     */
    private int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    /**
     * Метод для установки значения поля класса icon.
     *
     * @param icon the new icon
     */
    public void setIcon(Drawable icon) {
        mIcon = icon;
        mIcon.setBounds(
                dpToPx(ICON_LEFT_MARGIN_DP),
                dpToPx(ICON_TOP_MARGIN_DP) + mIconTopMargin,
                dpToPx(ICON_LEFT_MARGIN_DP) + dpToPx(ICON_SIZE_DP),
                dpToPx(ICON_TOP_MARGIN_DP) + mIconTopMargin + dpToPx(ICON_SIZE_DP)
        );
    }
}
