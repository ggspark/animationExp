package com.example.gaurav.animationapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gaurav Gupta <gaurav@thegauravgupta.com>
 * @since 25/Jun/2015
 */

public class MyListAdapter extends BaseAdapter {

    private static final String TRANSLATION_X = "translationX";
    public List<String> list;
    public static List<View> viewList; //List to keep current views of the list view
    private ViewGroup mParent; // List to keep the parent of the views
    private LayoutInflater inflater;
    private final Context mContext;

    public MyListAdapter(final Context context, List<String> arrayList) {
        mContext = context;
        list = arrayList;
        viewList = new ArrayList<View>();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(final int position) {
        return getItem(position).hashCode();
    }


    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        TextView view = (TextView) convertView;
        if (view == null) {
            view = (TextView) inflater.inflate(R.layout.list_row, null);
            viewList.add(view);
            mParent = parent;

        }

        view.setText(list.get(position));

        return view;
    }

    //Function to slide the views from right to left and bottom to top
    public void retractViews() {
        int i = 0;
        int j = viewList.size() - 1;
        while (j > 2 && mParent != null) {
            translateOutAnimation(mParent, viewList.get(j), i * 100);
            i++;
            j--;
        }
    }

    //Function to slide the views from left to right and top to bottom
    public void expandViews() {
        int i = 2;
        while (i < viewList.size() && mParent != null) {
            translateInAnimation(mParent, viewList.get(i), i * 100);
            i++;
        }
    }


    //Animation to slide in from left to right
    public void translateInAnimation(final ViewGroup parent, final View view, int delay) {
        Interpolator interpolator = new AnimationUtils().loadInterpolator(mContext, android.R.interpolator.linear);
        Animator animator = ObjectAnimator.ofFloat(view, TRANSLATION_X, 0 - parent.getWidth(), 0);
        animator.setDuration(300); // time in ms
        animator.setInterpolator(interpolator);
        animator.setStartDelay(delay);
        animator.start();

    }


    //Animation to slide out from right to left
    public void translateOutAnimation(final ViewGroup parent, final View view, int delay) {
        Interpolator interpolator = new AnimationUtils().loadInterpolator(mContext, android.R.interpolator.linear);
        Animator animator = ObjectAnimator.ofFloat(view, TRANSLATION_X, 0, 0 - parent.getWidth());
        animator.setDuration(300); // time in ms
        animator.setInterpolator(interpolator);
        animator.setStartDelay(delay);
        animator.start();

    }


}