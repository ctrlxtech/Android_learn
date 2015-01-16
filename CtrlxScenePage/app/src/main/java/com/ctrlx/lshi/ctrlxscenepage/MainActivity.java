package com.ctrlx.lshi.ctrlxscenepage;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity implements OnClickListener{
    private Button mBtnTv;
    private LinearLayout mLayout;

    private SlidingLayout slidingLayout; //侧滑布局对象，用于通过手指滑动将左侧的菜单布局进行显示或隐藏。
    private Button menuButton;
    private ListView contentListView; //放在content布局中的ListView。
    private ArrayAdapter<String> contentListAdapter; //作用于contentListView的适配器。
    private String[] contentItems = { "Home", "Theme Modes"};  //用于填充contentListAdapter的数据源。
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnTv = (Button) findViewById(R.id.btn_tv);

        mBtnTv.setOnClickListener(this);

        slidingLayout = (SlidingLayout) findViewById(R.id.slidingLayout);
        menuButton = (Button) findViewById(R.id.menuButton);
        contentListView = (ListView) findViewById(R.id.menuList);
        contentListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                contentItems);
        contentListView.setAdapter(contentListAdapter);
        mLayout = (LinearLayout) findViewById(R.id.content);
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

        contentListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String text = contentItems[position];
                Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
                switch (position) {
                    case 0:
                        break;
                    case 1:
                        intent = new Intent(MainActivity.this, ThemeActivity.class);
                        startActivity(intent);
                        MainActivity.this.finish();
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
            case R.id.btn_tv:
                Intent intent = new Intent(MainActivity.this, TvPanelActivity.class);
                startActivity(intent);
                MainActivity.this.finish();
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
