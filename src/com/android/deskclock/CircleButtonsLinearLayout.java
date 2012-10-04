package com.android.deskclock;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * TODO: Insert description here. (generated by sblitz)
 */
public class CircleButtonsLinearLayout extends LinearLayout {
    private Context mContext;
    private int mCircleTimerViewId;
    private int mLeftButtonId;
    private int mRightButtonId;
    private int mStopButtonId;
    private float mLeftButtonPadding;
    private float mRightButtonPadding;
    private float mStrokeSize;
    private float mDiamOffset;
    private CircleTimerView mCtv;
    private ImageButton mLeft, mRight;
    private TextView mStop;

    public CircleButtonsLinearLayout(Context context) {
        this(context, null);
        mContext = context;
    }

    public CircleButtonsLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public void setCircleTimerViewIds(int circleTimerViewId, int leftButtonId, int rightButtonId,
            int stopButtonId, int leftButtonPaddingDimenId, int rightButtonPaddingDimenId) {
        mCircleTimerViewId = circleTimerViewId;
        mLeftButtonId = leftButtonId;
        mRightButtonId = rightButtonId;
        mStopButtonId = stopButtonId;
        mLeftButtonPadding = mContext.getResources().getDimension(leftButtonPaddingDimenId);
        mRightButtonPadding = mContext.getResources().getDimension(rightButtonPaddingDimenId);

        float diamondStrokeSize =
                mContext.getResources().getDimension(R.dimen.circletimer_diamond_size);
        float markerStrokeSize =
                mContext.getResources().getDimension(R.dimen.circletimer_marker_size);
        mStrokeSize = mContext.getResources().getDimension(R.dimen.circletimer_circle_size);
        mDiamOffset =
                Utils.calculateRadiusOffset(mStrokeSize, diamondStrokeSize, markerStrokeSize) * 2;

    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setViewPaddings();
    }

    protected void setViewPaddings() {
        if (mCtv == null) {
            mCtv = (CircleTimerView) findViewById(mCircleTimerViewId);
            if (mCtv == null) {
                return;
            }
            mLeft = (ImageButton) findViewById(mLeftButtonId);
            mRight = (ImageButton) findViewById(mRightButtonId);
            mStop = (TextView) findViewById(mStopButtonId);
        }

        int frameWidth = mCtv.getMeasuredWidth();
        int frameHeight = mCtv.getMeasuredHeight();
        int minBound = Math.min(frameWidth, frameHeight);
        int circleDiam = (int) (minBound - mDiamOffset);

        MarginLayoutParams stopParams = (MarginLayoutParams) mStop.getLayoutParams();
        stopParams.bottomMargin = circleDiam/6;
        if (minBound == frameWidth) {
            stopParams.bottomMargin += (frameHeight-frameWidth)/2;
        }

        int sideMarginOffset = (int) ((frameWidth - circleDiam - mStrokeSize) / 2)
                - (int) mContext.getResources().getDimension(R.dimen.timer_button_extra_offset);
        int leftMarginOffset = Math.max(0, sideMarginOffset - (int) mLeftButtonPadding);
        int rightMarginOffset = Math.max(0, sideMarginOffset - (int) mRightButtonPadding);
        int bottomMarginOffset = (frameHeight - minBound) / 2;
        MarginLayoutParams leftParams = (MarginLayoutParams) mLeft.getLayoutParams();
        leftParams.leftMargin = leftMarginOffset;
        leftParams.bottomMargin = bottomMarginOffset;
        MarginLayoutParams rightParams = (MarginLayoutParams) mRight.getLayoutParams();
        rightParams.rightMargin = rightMarginOffset;
        rightParams.bottomMargin = bottomMarginOffset;
    }
}