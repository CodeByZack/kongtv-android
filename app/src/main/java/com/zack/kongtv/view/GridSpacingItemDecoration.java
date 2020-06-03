package com.zack.kongtv.view;
import android.graphics.Rect;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Zackv on 2018/4/10.
 */

public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

    private int spanCount;
    private int spacing;
    private int skipItem;
    private boolean includeEdge;

    public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
        this.spanCount = spanCount;
        this.spacing = spacing;
        this.includeEdge = includeEdge;
    }

    public GridSpacingItemDecoration(int skipItem,int spanCount, int spacing, boolean includeEdge) {
        this.spanCount = spanCount;
        this.spacing = spacing;
        this.includeEdge = includeEdge;
        this.skipItem = skipItem;
    }
/*    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view); // item position
        if(position<skipItem){
            return;
        }
        int column = (position-skipItem) % spanCount; // item column
        if (includeEdge) {
            outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
            outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

            if (position < spanCount) { // top edge
                outRect.top = spacing;
            }
            outRect.bottom = spacing; // item bottom
        } else {
            outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
            outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
            if (position >= spanCount) {
                outRect.top = spacing; // item top
            }
        }
    }*/

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view); // item position
        if(position<skipItem){
            return;
        }
        position = position - skipItem;
        int column = (position) % spanCount;//列数
        if(includeEdge){
            outRect.left = spacing;
            if (position < spanCount) { // top edge
                outRect.top = spacing;
            }
            outRect.bottom = spacing;
            if(column+1 == spanCount){
                outRect.right = spacing;
            }
        }else{
            if(column != 0){
                outRect.left = spacing;
            }
            if (position >= spanCount) {
                outRect.top = spacing; // item top
            }
        }
    }
}