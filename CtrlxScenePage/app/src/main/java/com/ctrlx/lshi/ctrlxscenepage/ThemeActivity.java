package com.ctrlx.lshi.ctrlxscenepage;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.Timer;

/**
 * Created by lshi19 on 1/9/15.
 */
public class ThemeActivity extends Activity implements View.OnClickListener{
    private Button mBtnTheatre;
    private RelativeLayout mLayout;

    private SlidingLayout slidingLayout; //侧滑布局对象，用于通过手指滑动将左侧的菜单布局进行显示或隐藏。
    private Button menuButton;
    private ListView contentListView; //放在content布局中的ListView。
    private ArrayAdapter<String> contentListAdapter; //作用于contentListView的适配器。
    private String[] contentItems = { "Home", "Theme Modes"};  //用于填充contentListAdapter的数据源。
    private Intent intent;

    private Context mContext;
    final Handler handler = new Handler();

    int clickFlag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme_mode);
        mContext = this.getApplicationContext();

        mLayout = (RelativeLayout) findViewById(R.id.layout);
        mBtnTheatre = (Button) findViewById(R.id.btn_theatre);
        mBtnTheatre.setOnClickListener(this);

        slidingLayout = (SlidingLayout) findViewById(R.id.slidingLayout);
        menuButton = (Button) findViewById(R.id.menuButton);
        contentListView = (ListView) findViewById(R.id.menuList);
        contentListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                contentItems);
        contentListView.setAdapter(contentListAdapter);
        mLayout = (RelativeLayout) findViewById(R.id.layout);
        // 将监听滑动事件绑定在contentListView上
        slidingLayout.setScrollEvent(mLayout);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (slidingLayout.isLeftLayoutVisible()) {
                    slidingLayout.scrollToRightLayout();
                } else {
                    slidingLayout.scrollToLeftLayout();
                }
            }
        });

        contentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String text = contentItems[position];
                Toast.makeText(ThemeActivity.this, text, Toast.LENGTH_SHORT).show();
                switch (position) {
                    case 0:
                        intent = new Intent(ThemeActivity.this, MainActivity.class);
                        startActivity(intent);
                        ThemeActivity.this.finish();
                        break;
                    case 1:
                        break;
                }
                if (slidingLayout.isLeftLayoutVisible()) {
                    slidingLayout.scrollToRightLayout();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_theatre:
                if (clickFlag == 0) {
                    mLayout.setBackground(getResources().getDrawable(R.drawable.clicked));
                    clickFlag = 1;

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // Do something after 5s = 5000ms
                            try {
                                Toast.makeText(mContext, "TV is turned on", Toast.LENGTH_SHORT).show();
                                Thread.sleep(1000);
                                Toast.makeText(mContext, "Speaker is turned on", Toast.LENGTH_SHORT).show();
                                Thread.sleep(1000);
                                Toast.makeText(mContext, "Disc Player is turned on", Toast.LENGTH_SHORT).show();
                                Thread.sleep(1000);
                                Toast.makeText(mContext, "Light is turned off", Toast.LENGTH_SHORT).show();
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                        }
                    }, 1000);



                } else {
                    mLayout.setBackground(getResources().getDrawable(R.drawable.unclick));
                    clickFlag = 0;
                }
                break;
        }

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
}
