package com.bignerdranch.android.wintervacationhomework;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 14158 on 2018/2/11.
 */

public class ThirdPage extends Fragment implements View.OnClickListener {

    RecyclerView mRecyclerView;
    List<String> data;
    SimpleAdapter mAdapter;
    View view1;

    private SwipeRefreshLayout swipeRefresh;

    private int[] res = {R.id.menu_one,R.id.menu_two,R.id.menu_three};
    private List<FloatingActionButton>buttonList = new ArrayList<>();
    private boolean flag = true;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.third_layout, null);

        TextView view3 = view.findViewById(R.id.text_three);
        Typeface typeface = Typeface.createFromAsset(view.getContext().getAssets(), "cai_yun.TTF");
        view3.setTypeface(typeface);

        data = new ArrayList<>();
        for (int i = 'A'; i <= 'z'; i++) {
            data.add("" + (char) i);
        }

        mRecyclerView = view.findViewById(R.id.recycler_view);
        mAdapter = new SimpleAdapter(data);
        mRecyclerView.setAdapter(mAdapter);

        ItemTouchHelper.Callback callback = new RecycleItemTouchHelper(mAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mAdapter.setmOnItemClickListener(new SimpleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                view1 = view;
                TextView textView = view.findViewById(R.id.text_item);
                String str;
                str = textView.getText().toString();
                Intent intent = new Intent(getActivity(),MoodActivity.class);
                intent.putExtra("string_text",str);
                startActivityForResult(intent,1);

            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(getActivity(),"long click:"+position,Toast.LENGTH_SHORT).show();
            }
        });

        LayoutAnimationController lac = new LayoutAnimationController(AnimationUtils.loadAnimation(this.getActivity(),R.anim.zoom_in));
        lac.setOrder(LayoutAnimationController.ORDER_NORMAL);
        mRecyclerView.setLayoutAnimation(lac);
        mRecyclerView.startLayoutAnimation();

        for (int re : res) {
            FloatingActionButton buttonView = view.findViewById(re);
            buttonView.setOnClickListener(this);
            buttonList.add(buttonView);
        }

        swipeRefresh = view.findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshRecycler();
            }
        });

        return view;
    }

    private void refreshRecycler() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(2000);
                }catch (InterruptedException e) {
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //处理具体的刷新逻辑
                        LayoutAnimationController lac = new LayoutAnimationController(AnimationUtils.loadAnimation(getActivity(),R.anim.zoom_in));
                        lac.setOrder(LayoutAnimationController.ORDER_NORMAL);
                        mRecyclerView.setLayoutAnimation(lac);
                        mRecyclerView.startLayoutAnimation();
                        mAdapter.notifyDataSetChanged();
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.menu_three:
                if(flag){
                    startAnim();
                }else {
                    closeAnim();
                }

                final Animation loadAnimation1;
                final Animation loadAnimation2;
                loadAnimation1 = AnimationUtils.loadAnimation(getActivity(),R.anim.continue_two);
                loadAnimation2 = AnimationUtils.loadAnimation(getActivity(),R.anim.continue_one);
                buttonList.get(2).startAnimation(loadAnimation1);
                loadAnimation1.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        buttonList.get(2).startAnimation(loadAnimation2);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

                break;

            case R.id.menu_one:
                mAdapter.addData(1);
                final Animation loadAnimation3;
                loadAnimation3 = AnimationUtils.loadAnimation(getActivity(),R.anim.alpha);
                buttonList.get(0).startAnimation(loadAnimation3);
                break;

            case R.id.menu_two:
                mAdapter.deleteData(1);
                final Animation loadAnimation4;
                loadAnimation4 = AnimationUtils.loadAnimation(getActivity(),R.anim.alpha);
                buttonList.get(1).startAnimation(loadAnimation4);
                break;
        }
    }

    private void closeAnim() {
        for (int i = 0; i < res.length-1; i++) {
            ObjectAnimator animator = ObjectAnimator.ofFloat(buttonList.get(i), "translationY", -220-i * 220, 0F);
            animator.setDuration(500);
            animator.setInterpolator(new BounceInterpolator());
            animator.setStartDelay(i * 300);
            animator.start();
            flag = true;
        }
    }

    private void startAnim() {
        for(int i=0;i<res.length-1;i++){
            ObjectAnimator animator = ObjectAnimator.ofFloat(buttonList.get(i),"translationY",0F,-220-i*220);
            animator.setDuration(500);
            animator.setInterpolator(new BounceInterpolator());
            animator.setStartDelay(i*300);
            animator.start();
            flag = false;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case 1:
                if(resultCode == Activity.RESULT_OK) {
                    String returnData = data.getStringExtra("data_return");
                    TextView textView = view1.findViewById(R.id.text_item);
                    textView.setText(returnData);
                }
                break;
            default:
        }
    }
}
