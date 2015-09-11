package com.superdroid.buttompanle.custom.badge;
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

import android.graphics.Bitmap;
import android.view.MotionEvent;
import android.view.ViewParent;

/**
 * ����:���� �ʼ�:bingoogolapple@gmail.com
 * ����ʱ��:15/7/6 ����10:42
 * ����:
 */
public interface BGABadgeable {
    /**
     * ��ʾԲ�����
     */
    void showCirclePointBadge();

    /**
     * ��ʾ���ֻ���
     *
     * @param badgeText
     */
    void showTextBadge(String badgeText);

    /**
     * ���ػ���
     */
    void hiddenBadge();

    /**
     * ��ʾͼ�����
     *
     * @param bitmap
     */
    void showDrawableBadge(Bitmap bitmap);

    /**
     * ���ø����onTouchEvent����
     *
     * @param event
     * @return
     */
    boolean callSuperOnTouchEvent(MotionEvent event);

    /**
     * �϶�����BGABadgeViewHelper.mMoveHiddenThreshold��̧����ָ������ʧ�Ĵ���
     *
     * @param delegate
     */
    void setDragDismissDelegage(BGADragDismissDelegate delegate);

    int getWidth();

    int getHeight();

    void postInvalidate();

    ViewParent getParent();

    int getId();
}