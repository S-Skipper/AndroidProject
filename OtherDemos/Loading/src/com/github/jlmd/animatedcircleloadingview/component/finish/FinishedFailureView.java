package com.github.jlmd.animatedcircleloadingview.component.finish;

import com.dtt.menu2.R;

import android.content.Context;
/**
 * @author jlmd
 */
public class FinishedFailureView extends FinishedView {

  public FinishedFailureView(Context context, int parentWidth, int mainColor, int secondaryColor) {
    super(context, parentWidth, mainColor, secondaryColor);
  }

  @Override
  protected int getDrawable() {
    return R.drawable.ic_failure_mark;
  }

  @Override
  protected int getCircleColor() {
    return secondaryColor;
  }
}