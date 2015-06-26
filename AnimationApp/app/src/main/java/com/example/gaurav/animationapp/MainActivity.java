package com.example.gaurav.animationapp;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.Arrays;


public class MainActivity extends Activity {

    private ListView mListView;
    private MyListAdapter mAdapter;
    private ImageView backButton;
    private View root;
    private RelativeLayout listContainer;
    private int listFullHeight;
    private boolean retracted= true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listContainer = (RelativeLayout) findViewById(R.id.list_container);
        backButton = (ImageView) findViewById(R.id.back_button);
        mAdapter = new MyListAdapter(this, Arrays.asList(getResources().getStringArray(R.array.item_names)));
        mListView = (ListView) findViewById(R.id.activity_mylist_listview);
        root = findViewById(R.id.root);
        mListView.setAdapter(mAdapter);
        openCloseDrawer(false);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (retracted) {
                    retracted = false;
                    animateDown();
                    mAdapter = new MyListAdapter(MainActivity.this, Arrays.asList(getResources().getStringArray(R.array.item_names)));
                    mListView.setAdapter(mAdapter);
                }

            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!retracted) {
                    retracted = true;
                    mAdapter.retractViews();
                    animateUp();
                }
            }
        });

    }

    //0 percent is full open and 100 percent is full close
    private void openCloseDrawer(boolean open) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) listContainer.getLayoutParams();
        if (open){
            layoutParams.height = convertDpToPixel(58 * 6, this);
        }else {
            layoutParams.height = convertDpToPixel(58 * 2, this);
        }
        listContainer.setLayoutParams(layoutParams);
        root.invalidate();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static int convertDpToPixel(float dp, Context context) {

        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        int px = (int) (dp * (metrics.densityDpi / 160f));
        return px;

    }

    private void animateDown() {
        DropDownAnim scale = new DropDownAnim(listContainer,convertDpToPixel(58 * 4, this), true );
        scale.setFillAfter(false);
        scale.setDuration(400);
        scale.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                openCloseDrawer(true);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        listContainer.startAnimation(scale);
    }

    private void animateUp() {
        DropDownAnim scale = new DropDownAnim(listContainer,convertDpToPixel(58 * 4, this), false );
        scale.setFillAfter(false);
        scale.setDuration(400);
        scale.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                openCloseDrawer(false);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        listContainer.startAnimation(scale);
    }


    class DropDownAnim extends Animation {
        private final int targetHeight;
        private final View view;
        private final boolean down;

        public DropDownAnim(View view, int targetHeight, boolean down) {
            this.view = view;
            this.targetHeight = targetHeight;
            this.down = down;
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            int newHeight;
            if (down) {
                newHeight = convertDpToPixel(58 * 2, MainActivity.this) +  (int) (targetHeight * interpolatedTime);
            } else {
                newHeight = convertDpToPixel(58 * 2, MainActivity.this) + (int) (targetHeight * (1 - interpolatedTime));
            }
            view.getLayoutParams().height = newHeight;
            view.requestLayout();
        }

        @Override
        public void initialize(int width, int height, int parentWidth,
                               int parentHeight) {
            super.initialize(width, height, parentWidth, parentHeight);
        }

        @Override
        public boolean willChangeBounds() {
            return true;
        }
    }

}
