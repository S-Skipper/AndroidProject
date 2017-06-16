package com.github.jlmd.animatedcircleloadingview.component.finish;

import com.dtt.menu2.R;

import android.content.Context;
/**
 * @author jlmd
 */
public class FinishedOkView extends FinishedView {

  public FinishedOkView(Context context, int parentWidth, int mainColor, int secondaryColor) {
    super(context, parentWidth, mainColor, secondaryColor);
  }

  @Override
  protected int getDrawable() {
    return R.drawable.ic_checked_mark;
  }

  @Override
  protected int getCircleColor() {
    return mainColor;
  }
}
