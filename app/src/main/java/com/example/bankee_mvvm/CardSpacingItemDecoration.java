package com.example.bankee_mvvm;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CardSpacingItemDecoration extends RecyclerView.ItemDecoration {
    private int spacing;

    public CardSpacingItemDecoration(int spacing) {
        this.spacing = spacing;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                               @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.left = spacing;
        outRect.right = spacing;
        outRect.bottom = spacing;

        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top = 0; // No top spacing for the first item
        } else {
            outRect.top = spacing;
        }
    }
}
