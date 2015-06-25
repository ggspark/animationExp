package com.example.gaurav.animationapp;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
    private boolean retracted= false;

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
        openCloseDrawer(true);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(retracted) {
                    retracted = false;
                    openCloseDrawer(true);
                    mAdapter = new MyListAdapter(MainActivity.this, Arrays.asList(getResources().getStringArray(R.array.item_names)));
                    mListView.setAdapter(mAdapter);
                }

            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!retracted) {
                    retracted = true;
                    mAdapter.retractViews();
                    openCloseDrawer(false);
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

}
