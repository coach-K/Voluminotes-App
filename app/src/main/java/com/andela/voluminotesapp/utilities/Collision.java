package com.andela.voluminotesapp.utilities;


import android.graphics.Region;
import android.view.View;

import com.andela.voluminotesapp.callbacks.OnIntersectListener;


public class Collision {
    private Region region1;
    private Region region2;

    public Collision(View layout1, View layout2) {
        region1 = getRegion(layout1);
        region2 = getRegion(layout2);
    }

    public void intersect(OnIntersectListener listener) {
        if (region1.op(region2, Region.Op.INTERSECT)) {
            listener.onIntersect();
        }
    }

    private Region getRegion(View viewHolder) {
        int[] layout = new int[2];
        viewHolder.getLocationOnScreen(layout);
        int x = layout[0];
        int y = layout[1];
        int width = viewHolder.getWidth();
        int height = viewHolder.getHeight();

        return new Region(x, y, x + width, y + height);
    }
}
