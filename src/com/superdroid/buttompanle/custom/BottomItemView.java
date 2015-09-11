package com.superdroid.buttompanle.custom;

import com.superdroid.buttompanle.R;
import com.superdroid.buttompanle.custom.badge.BGABadgeViewHelper;
import com.superdroid.buttompanle.custom.badge.BGABadgeViewHelper.BadgeGravity;
import com.superdroid.buttompanle.custom.badge.BGABadgeable;
import com.superdroid.buttompanle.custom.badge.BGADragDismissDelegate;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BottomItemView extends LinearLayout implements BGABadgeable {

	/**
	 * ����view
	 */
	private BGABadgeViewHelper mBadgeViewHelper;
	/**
	 * ������ɫ
	 */
	private ColorStateList mTextColor = null;
	/**
	 * ��ԴͼƬ
	 */
	private Drawable imgDrawable;
	/**
	 * �Ƿ�ѡ��
	 */
	private boolean isSelected = false;

	/**
	 * TextView����
	 */
	private String mText;
	/**
	 * ͼƬ���
	 */
	private int imgWidth;
	/**
	 * ͼƬ�߶�
	 */
	private int imgHeight;
	/**
	 * ͼƬ�����ֵľ���
	 */
	private int imgTextMargin;

	private ImageView mImageView;
	private TextView mTextView;

	public BottomItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mBadgeViewHelper = new BGABadgeViewHelper(this, context, attrs, BadgeGravity.RightTop);
		TypedArray mTypedArray = getContext().obtainStyledAttributes(attrs, R.styleable.BottomItemView);
		int count = mTypedArray.getIndexCount();
		for (int i = 0; i < count; i++) {
			int attr = mTypedArray.getIndex(i);
			switch (attr) {
			case R.styleable.BottomItemView_mTextColor:
				mTextColor = mTypedArray.getColorStateList(attr);
				break;
			case R.styleable.BottomItemView_mImgResource:
				imgDrawable = mTypedArray.getDrawable(attr);
				break;
			case R.styleable.BottomItemView_mText:
				mText = mTypedArray.getString(attr);
				break;
			case R.styleable.BottomItemView_mImgHeight:
				imgHeight = mTypedArray.getDimensionPixelSize(attr, dip2px(25));
				break;
			case R.styleable.BottomItemView_mImgWidth:
				imgWidth = mTypedArray.getDimensionPixelSize(attr, dip2px(25));
				break;
			case R.styleable.BottomItemView_mImgTextMargin:
				imgTextMargin = mTypedArray.getDimensionPixelSize(attr, dip2px(2));
				break;
			}
		}

		mTypedArray.recycle();
		initView();
		setWillNotDraw(false);
	}

	private void initView() {
		setOrientation(LinearLayout.VERTICAL);
		setPadding(0, dip2px(2), 0, 0);
		LinearLayout.LayoutParams mImageViewParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		mImageViewParams.gravity = Gravity.CENTER_HORIZONTAL;
		mImageView = new ImageView(getContext());
		mImageView.setImageDrawable(imgDrawable);
		Log.i("tag", "imgWidth=" + imgWidth);
		Log.i("tag", "imgHeight=" + imgHeight);
		Log.i("tag", "imgTextMargin=" + imgTextMargin);
		Log.i("tag", "dip2px(25)=" + dip2px(25));
		mImageViewParams.width = imgWidth == 0 ? dip2px(25) : imgWidth;
		mImageViewParams.height = imgHeight == 0 ? dip2px(25) : imgHeight;
		mImageViewParams.bottomMargin = imgTextMargin == 0 ? dip2px(2) : imgTextMargin;

		LinearLayout.LayoutParams mTextViewParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		mTextViewParams.gravity = Gravity.CENTER_HORIZONTAL;
		mTextView = new TextView(getContext());
		mTextView.setTextColor(mTextColor);
		mTextView.setText(mText);

		this.addView(mImageView, mImageViewParams);
		this.addView(mTextView, mTextViewParams);
	}

	/**
	 * �ı�view��ѡ��״̬
	 * 
	 * @param isSelected
	 */
	private void changeSelectStatus(boolean isSelected) {
		mTextView.setSelected(isSelected);
		mImageView.setSelected(isSelected);
	}

	public ImageView getImageView() {
		return mImageView;
	}

	public TextView getmTextView() {
		return mTextView;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		changeSelectStatus(isSelected);
		this.isSelected = isSelected;
	}

	/**
	 * ����TextView����������
	 * 
	 * @param mText
	 */
	public void setText(String mText) {
		this.mText = mText;
		mTextView.setText(mText);
	}

	/**
	 * �����ֻ��ķֱ��ʴ� dip �ĵ�λ ת��Ϊ px(����)
	 */
	public int dip2px(float dpValue) {
		final float scale = getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * �����ֻ��ķֱ��ʴ� px(����) �ĵ�λ ת��Ϊ dp
	 */
	public int px2dip(float pxValue) {
		final float scale = getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	@Override
	public void showCirclePointBadge() {
		mBadgeViewHelper.showCirclePointBadge();
	}

	@Override
	public void showTextBadge(String badgeText) {
		mBadgeViewHelper.showTextBadge(badgeText);
	}

	@Override
	public void hiddenBadge() {
		mBadgeViewHelper.hiddenBadge();
	}

	@Override
	public void showDrawableBadge(Bitmap bitmap) {
		mBadgeViewHelper.showDrawable(bitmap);
	}

	@Override
	public boolean callSuperOnTouchEvent(MotionEvent event) {
		return super.onTouchEvent(event);
	}

	@Override
	public void setDragDismissDelegage(BGADragDismissDelegate delegate) {
		mBadgeViewHelper.setDragDismissDelegage(delegate);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		mBadgeViewHelper.drawBadge(canvas);
		super.onDraw(canvas);
	}
}
