package com.kamron.pogoiv.widgets.behaviors;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.AppBarLayout.Behavior;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

public class DisableableAppBarLayoutBehavior extends Behavior {

    private boolean enabled = true;


    public DisableableAppBarLayoutBehavior() {
        super();
    }

    public DisableableAppBarLayoutBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override public boolean onStartNestedScroll(CoordinatorLayout parent, AppBarLayout child, View directTargetChild,
                                                 View target, int nestedScrollAxes, int type) {
        return enabled && super.onStartNestedScroll(parent, child, directTargetChild, target, nestedScrollAxes, type);
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }

}
