package com.bignerdranch.android.wintervacationhomework;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by 14158 on 2018/2/20.
 */

public class SimpleAdapter extends RecyclerView.Adapter<MyViewHolder> implements RecycleItemTouchHelper.ItemTouchHelperCallback {

    private List<String> mData;

    public interface OnItemClickListener
    {
        void onItemClick(View view,int position);
        void onItemLongClick(View view,int position);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setmOnItemClickListener(OnItemClickListener listener){
        this.mOnItemClickListener = listener;
    }

    SimpleAdapter(List<String> data){
        this.mData = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        View view = mInflater.inflate(R.layout.item_single_textview,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int pos) {
        holder.tv.setText(mData.get(pos));

        if(mOnItemClickListener != null){

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.itemView,pos);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnItemClickListener.onItemLongClick(holder.itemView,pos);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    void addData(int position){
        mData.add(position,"Insert one");
        notifyItemInserted(position);
        notifyItemChanged(position,mData.size()-position);
    }

    void deleteData(int position){
        mData.remove(position);
        notifyItemRemoved(position);
        notifyItemChanged(position,mData.size()-position);
    }

    public void onItemDelete(int position){
        mData.remove(position);
        notifyItemRemoved(position);
        notifyItemChanged(position,mData.size()-position);
    }

    @Override
    public void onMove(int fromPosition, int toPosition) {
        Collections.swap(mData,fromPosition,toPosition);
        notifyItemMoved(fromPosition,toPosition);
    }

}

class MyViewHolder extends RecyclerView.ViewHolder{

    TextView tv;

    MyViewHolder(View arg0){
        super(arg0);
        tv=arg0.findViewById(R.id.text_item);
    }
}
