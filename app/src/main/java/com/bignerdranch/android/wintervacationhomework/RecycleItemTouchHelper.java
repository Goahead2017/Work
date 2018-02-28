package com.bignerdranch.android.wintervacationhomework;

/*import android.content.res.Resources;*/
/*import android.graphics.Bitmap;*/
/*import android.graphics.BitmapFactory;*/
/*
import android.graphics.Canvas;
*/
/*import android.graphics.Paint;*/
/*import android.graphics.Rect;*/
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
/*import android.view.View;*/

/**
 * Created by 14158 on 2018/2/22.
 */

public class RecycleItemTouchHelper extends ItemTouchHelper.Callback {

    private static final String TAG = "RecycleItemTouchHelper";
    private final ItemTouchHelperCallback helperCallback;

    RecycleItemTouchHelper(ItemTouchHelperCallback helperCallback) {
        this.helperCallback = helperCallback;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        Log.e(TAG, "getMovementFlags:");
        return makeMovementFlags(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.START);
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return super.isLongPressDragEnabled();
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return super.isItemViewSwipeEnabled();
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        Log.e(TAG, "onMove:");
        helperCallback.onMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        Log.e(TAG, "onSwiped:");
        helperCallback.onItemDelete(viewHolder.getAdapterPosition());
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
    }

    public interface ItemTouchHelperCallback {
        void onItemDelete(int position);

        void onMove(int fromPosition, int toPosition);
    }
}

   /* @Override
    public void onChildDraw(Canvas c,RecyclerView recyclerView,RecyclerView.ViewHolder viewHolder,float dX,float dY,int actionState,boolean isCurrentlyActive){
        if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){
            View itemView = viewHolder.itemView;
            Resources resources = MyApplication.getAppContext().getResources();
            *//*Bitmap bitmap = BitmapFactory.decodeResource(resources,R.drawable.delete);*//*
            *//*int padding = 1;*//*
            *//*int maxDrawWidth = 2*padding+bitmap.getWidth();*//*
            Paint paint = new Paint();
            paint.setColor(resources.getColor(R.color.colorBlue));
            *//*int x = Math.round(Math.abs(dX));*//*
            *//*int drawWidth = Math.min(x,maxDrawWidth);*//*
            *//*int itemTop = itemView.getBottom()-itemView.getHeight();*//*
*//*
            if(dX<0){
*//*
                *//*c.drawRect(itemView.getRight(),itemTop,drawWidth,itemView.getBottom(),paint);*//*
*//*
                if(x>padding){
*//*
*//*
                    Rect rect = new Rect();
*//*
                    *//*rect.right = itemView.getWidth()-padding;*//*
                    *//*rect.top = itemTop+(itemView.getBottom()-itemTop-bitmap.getHeight())/2;*//*
                    *//*int mixLeft = rect.right-bitmap.getWidth();*//*
                    *//*rect.left = Math.max(rect.right-x,mixLeft);*//*
                    *//*rect.bottom = rect.top+bitmap.getHeight();*//*
                    *//*Rect rect1 = null;*//*
*//*
                    if(x>itemView.getWidth()-rect.right){
*//*
                        *//*rect1 = new Rect();*//*
                        *//*rect1.left = itemView.getWidth()-rect.width();*//*
                        *//*rect1.top = 0;*//*
                        *//*rect1.bottom = bitmap.getHeight();*//*
                        *//*rect1.right = itemView.getWidth();*//*
               *//*     }*//*
                    *//*c.drawBitmap(bitmap,rect1,rect,paint);*//*
                 *//* }*//*
                itemView.setTranslationX(dX);
                float alpha = 1.0f-Math.abs(dX)/(float)itemView.getWidth();
                itemView.setAlpha(alpha);
            }*//*else {
                super.onChildDraw(c,recyclerView,viewHolder,dX,dY,actionState,isCurrentlyActive);
            }
        }else {
            super.onChildDraw(c,recyclerView,viewHolder,dX,dY,actionState,isCurrentlyActive);
        }*//*
    }
}
*/