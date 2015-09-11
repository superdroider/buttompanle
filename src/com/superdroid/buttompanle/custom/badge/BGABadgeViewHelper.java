/**
 * Copyright 2015 bingoogolapple
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.superdroid.buttompanle.custom.badge;

import com.superdroid.buttompanle.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;

/**
 * ����:���� �ʼ�:bingoogolapple@gmail.com ����ʱ��:15/7/6 ����10:13 ����:
 */
public class BGABadgeViewHelper {
	private Bitmap mBitmap;
	private BGABadgeable mBadgeable;
	private Paint mBadgePaint;
	/**
	 * ���±���ɫ
	 */
	private int mBadgeBgColor;
	/**
	 * �����ı�����ɫ
	 */
	private int mBadgeTextColor;
	/**
	 * �����ı������С
	 */
	private int mBadgeTextSize;
	/**
	 * ���±����������ؼ����±�Ե�����
	 */
	private int mBadgeVerticalMargin;
	/**
	 * ���±����������ؼ����ұ�Ե�����
	 */
	private int mBadgeHorizontalMargin;
	/***
	 * �����ı���Ե����±�����Ե��ľ���
	 */
	private int mBadgePadding;
	/**
	 * �����ı�
	 */
	private String mBadgeText;
	/**
	 * �����ı���ռ�����С
	 */
	private Rect mBadgeNumberRect;
	/**
	 * �Ƿ���ʾBadge
	 */
	private boolean mIsShowBadge;
	/**
	 * �����������ؼ��е�λ��
	 */
	private BadgeGravity mBadgeGravity;
	/**
	 * ����������ռ����
	 */
	private RectF mBadgeRectF;
	/**
	 * �Ƿ���϶�
	 */
	private boolean mDragable;
	/**
	 * �϶�ʱ�Ļ��¿ؼ�
	 */
	private BGADragBadgeView mDropBadgeView;
	/**
	 * �Ƿ������϶�
	 */
	private boolean mIsDraging;
	/**
	 * ���µĵ�
	 */
	private PointF mDownPointF;
	/**
	 * �϶�mMoveHiddenThreshold�����̧����ָ������ʧ
	 */
	private int mMoveHiddenThreshold;
	/**
	 * �϶�����BGABadgeViewHelper.mMoveHiddenThreshold��̧����ָ������ʧ�Ĵ���
	 */
	private BGADragDismissDelegate mDelegage;
	private boolean mIsShowDrawable = false;

	public BGABadgeViewHelper(BGABadgeable badgeable, Context context, AttributeSet attrs,
			BadgeGravity defaultBadgeGravity) {
		mBadgeable = badgeable;
		initDefaultAttrs(context, defaultBadgeGravity);
		initCustomAttrs(context, attrs);
		afterInitDefaultAndCustomAttrs();
		mDropBadgeView = new BGADragBadgeView(context, this);
	}

	private void initDefaultAttrs(Context context, BadgeGravity defaultBadgeGravity) {
		mBadgeNumberRect = new Rect();
		mBadgeRectF = new RectF();
		mBadgeBgColor = Color.RED;
		mBadgeTextColor = Color.WHITE;
		mBadgeTextSize = sp2px(context, 10);

		mBadgePaint = new Paint();
		mBadgePaint.setAntiAlias(true);
		mBadgePaint.setStyle(Paint.Style.FILL);
		// ����mBadgeText���У���֤mBadgeText����Ϊ1ʱ���ı�Ҳ�ܾ���
		mBadgePaint.setTextAlign(Paint.Align.CENTER);

		mBadgePadding = dp2px(context, 4);
		mBadgeVerticalMargin = dp2px(context, 4);
		mBadgeHorizontalMargin = dp2px(context, 4);

		mBadgeGravity = defaultBadgeGravity;
		mIsShowBadge = false;

		mBadgeText = null;

		mBitmap = null;

		mIsDraging = false;

		mDragable = false;

		mDownPointF = new PointF();

		mMoveHiddenThreshold = dp2px(context, 60);
	}

	private void initCustomAttrs(Context context, AttributeSet attrs) {

		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BGABadgeView);
		final int N = typedArray.getIndexCount();
		for (int i = 0; i < N; i++) {
			initCustomAttr(typedArray.getIndex(i), typedArray);
		}
		typedArray.recycle();
	}

	private void initCustomAttr(int attr, TypedArray typedArray) {
		if (attr == R.styleable.BGABadgeView_badge_bgColor) {
			mBadgeBgColor = typedArray.getColor(attr, mBadgeBgColor);
		} else if (attr == R.styleable.BGABadgeView_badge_textColor) {
			mBadgeTextColor = typedArray.getColor(attr, mBadgeTextColor);
		} else if (attr == R.styleable.BGABadgeView_badge_textSize) {
			mBadgeTextSize = typedArray.getDimensionPixelSize(attr, mBadgeTextSize);
		} else if (attr == R.styleable.BGABadgeView_badge_verticalMargin) {
			mBadgeVerticalMargin = typedArray.getDimensionPixelSize(attr, mBadgeVerticalMargin);
		} else if (attr == R.styleable.BGABadgeView_badge_horizontalMargin) {
			mBadgeHorizontalMargin = typedArray.getDimensionPixelSize(attr, mBadgeHorizontalMargin);
		} else if (attr == R.styleable.BGABadgeView_badge_padding) {
			mBadgePadding = typedArray.getDimensionPixelSize(attr, mBadgePadding);
		} else if (attr == R.styleable.BGABadgeView_badge_gravity) {
			int ordinal = typedArray.getInt(attr, mBadgeGravity.ordinal());
			mBadgeGravity = BadgeGravity.values()[ordinal];
		} else if (attr == R.styleable.BGABadgeView_badge_dragable) {
			mDragable = typedArray.getBoolean(attr, mDragable);
		}
	}

	private void afterInitDefaultAndCustomAttrs() {
		mBadgePaint.setTextSize(mBadgeTextSize);
	}

	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if (mDragable && mIsShowBadge && mBadgeRectF.contains(event.getX(), event.getY())) {
				mDownPointF.set(event.getRawX(), event.getRawY());
				mIsDraging = true;
				mBadgeable.getParent().requestDisallowInterceptTouchEvent(true);
				mDropBadgeView.onTouchEvent(event);
				mBadgeable.postInvalidate();
				return true;
			}
			break;
		case MotionEvent.ACTION_MOVE:
			if (mIsDraging) {
				mDropBadgeView.onTouchEvent(event);
				return true;
			}
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			if (mIsDraging) {
				mDropBadgeView.onTouchEvent(event);
				mIsDraging = false;
				if (satisfyMoveDismissCondition(event)) {
					hiddenBadge();

					if (mDelegage != null) {
						mDelegage.onDismiss(mBadgeable);
					}
				} else {
					mBadgeable.postInvalidate();
				}
				return true;
			}
			break;
		default:
			break;
		}
		return mBadgeable.callSuperOnTouchEvent(event);
	}

	public boolean satisfyMoveDismissCondition(MotionEvent event) {
		return PointF.length(event.getRawX() - mDownPointF.x, event.getRawY() - mDownPointF.y) > mMoveHiddenThreshold;
	}

	public void drawBadge(Canvas canvas) {
		if (mIsShowBadge && !mIsDraging) {
			if (mIsShowDrawable) {
				drawDrawableBadge(canvas);
			} else {
				drawTextBadge(canvas);
			}
		}
	}

	/**
	 * ����ͼ�����
	 *
	 * @param canvas
	 */
	private void drawDrawableBadge(Canvas canvas) {
		mBadgeRectF.left = mBadgeable.getWidth() - mBadgeHorizontalMargin - mBitmap.getWidth();
		mBadgeRectF.top = mBadgeVerticalMargin;
		switch (mBadgeGravity) {
		case RightTop:
			mBadgeRectF.top = mBadgeVerticalMargin;
			break;
		case RightCenter:
			mBadgeRectF.top = (mBadgeable.getHeight() - mBitmap.getHeight()) / 2;
			break;
		case RightBottom:
			mBadgeRectF.top = mBadgeable.getHeight() - mBitmap.getHeight() - mBadgeVerticalMargin;
			break;
		default:
			break;
		}
		canvas.drawBitmap(mBitmap, mBadgeRectF.left, mBadgeRectF.top, mBadgePaint);
		mBadgeRectF.right = mBadgeRectF.left + mBitmap.getWidth();
		mBadgeRectF.bottom = mBadgeRectF.top + mBitmap.getHeight();
	}

	/**
	 * �������ֻ���
	 *
	 * @param canvas
	 */
	private void drawTextBadge(Canvas canvas) {
		String badgeText = "";
		if (!TextUtils.isEmpty(mBadgeText)) {
			badgeText = mBadgeText;
		}
		// ��ȡ�ı�����ռ���
		mBadgePaint.getTextBounds(badgeText, 0, badgeText.length(), mBadgeNumberRect);
		// ������±����Ŀ��
		int badgeHeight = mBadgeNumberRect.height() + mBadgePadding * 2;
		int badgeWidth;
		// ��mBadgeText�ĳ���Ϊ1��0ʱ����������ĸ߶Ȼ�ȿ�ȴ󣬴�ʱ���ÿ�ȵ��ڸ߶�
		if (badgeText.length() == 1 || badgeText.length() == 0) {
			badgeWidth = badgeHeight;
		} else {
			badgeWidth = mBadgeNumberRect.width() + mBadgePadding * 2;
		}

		// ������±������µ�ֵ
		mBadgeRectF.top = mBadgeVerticalMargin;
		mBadgeRectF.bottom = mBadgeable.getHeight() - mBadgeVerticalMargin;
		switch (mBadgeGravity) {
		case RightTop:
			mBadgeRectF.bottom = mBadgeRectF.top + badgeHeight;
			break;
		case RightCenter:
			mBadgeRectF.top = (mBadgeable.getHeight() - badgeHeight) / 2;
			mBadgeRectF.bottom = mBadgeRectF.top + badgeHeight;
			break;
		case RightBottom:
			mBadgeRectF.top = mBadgeRectF.bottom - badgeHeight;
			break;
		default:
			break;
		}

		// ������±������ҵ�ֵ
		mBadgeRectF.right = mBadgeable.getWidth() - mBadgeHorizontalMargin;
		mBadgeRectF.left = mBadgeRectF.right - badgeWidth;

		// ���û��±���ɫ
		mBadgePaint.setColor(mBadgeBgColor);
		// ���ƻ��±���
		canvas.drawRoundRect(mBadgeRectF, badgeHeight / 2, badgeHeight / 2, mBadgePaint);

		if (!TextUtils.isEmpty(mBadgeText)) {
			// ���û����ı���ɫ
			mBadgePaint.setColor(mBadgeTextColor);
			// initDefaultAttrs������������mBadgeText���У��˴���xΪ���±��������ĵ�y
			float x = mBadgeRectF.left + badgeWidth / 2;
			// ע�⣺�����ı�ʱ��y��ָ�ı��ײ����������ı����м�
			float y = mBadgeRectF.bottom - mBadgePadding;
			// ���ƻ����ı�
			canvas.drawText(badgeText, x, y, mBadgePaint);
		}
	}

	public void showCirclePointBadge() {
		showTextBadge(null);
	}

	public void showTextBadge(String badgeText) {
		mIsShowDrawable = false;
		mBadgeText = badgeText;
		mIsShowBadge = true;
		mBadgeable.postInvalidate();
	}

	public void hiddenBadge() {
		mIsShowBadge = false;
		mBadgeable.postInvalidate();
	}

	public void showDrawable(Bitmap bitmap) {
		mBitmap = bitmap;
		mIsShowDrawable = true;
		mIsShowBadge = true;
		mBadgeable.postInvalidate();
	}

	public boolean isShowDrawable() {
		return mIsShowDrawable;
	}

	public RectF getBadgeRectF() {
		return mBadgeRectF;
	}

	public int getBadgePadding() {
		return mBadgePadding;
	}

	public String getBadgeText() {
		return mBadgeText;
	}

	public int getBadgeBgColor() {
		return mBadgeBgColor;
	}

	public int getBadgeTextColor() {
		return mBadgeTextColor;
	}

	public int getBadgeTextSize() {
		return mBadgeTextSize;
	}

	public Bitmap getBitmap() {
		return mBitmap;
	}

	public void setDragDismissDelegage(BGADragDismissDelegate delegage) {
		mDelegage = delegage;
	}

	public static int dp2px(Context context, float dpValue) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue,
				context.getResources().getDisplayMetrics());
	}

	public static int sp2px(Context context, float spValue) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue,
				context.getResources().getDisplayMetrics());
	}

	public enum BadgeGravity {
		RightTop, RightCenter, RightBottom
	}
}