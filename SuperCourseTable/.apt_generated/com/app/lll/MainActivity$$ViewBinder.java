// Generated code from Butter Knife. Do not modify!
package com.app.lll;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MainActivity$$ViewBinder<T extends com.app.lll.MainActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131165185, "field 'mFrameLayout'");
    target.mFrameLayout = finder.castView(view, 2131165185, "field 'mFrameLayout'");
  }

  @Override public void unbind(T target) {
    target.mFrameLayout = null;
  }
}
