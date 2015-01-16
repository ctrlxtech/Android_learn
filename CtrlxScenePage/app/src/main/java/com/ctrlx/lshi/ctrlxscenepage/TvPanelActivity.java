package com.ctrlx.lshi.ctrlxscenepage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.ctrlx.lshi.ctrlxscenepage.R;


public class TvPanelActivity extends Activity implements View.OnClickListener, View.OnLongClickListener {
    private Button mBtnPower;
    private Button mBtnPanel;
    private Button mBtnAV;
    private Button mBtnMore;
    private Button mBtnUp;
    private Button mBtnLeft;
    private Button mBtnCenter;
    private Button mBtnRight;
    private Button mBtnDown;
    private Button mBtnSearch;
    private Button backButton;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_tv);
        mContext = this.getApplicationContext();


        mBtnPower = (Button) findViewById(R.id.tv_btn_power);
        mBtnPanel = (Button) findViewById(R.id.tv_btn_panel);
        mBtnAV = (Button) findViewById(R.id.tv_btn_avtv);
        mBtnMore = (Button) findViewById(R.id.tv_btn_more);
        mBtnUp = (Button) findViewById(R.id.tv_btn_up);
        mBtnLeft = (Button) findViewById(R.id.tv_btn_left);
        mBtnCenter = (Button) findViewById(R.id.tv_btn_center);
        mBtnRight = (Button) findViewById(R.id.tv_btn_right);
        mBtnDown = (Button) findViewById(R.id.tv_btn_down);
        mBtnSearch = (Button) findViewById(R.id.tv_btn_search);
        backButton = (Button) findViewById(R.id.backButton);

        mBtnPower.setOnClickListener(this);
        mBtnPower.setOnLongClickListener(this);
        mBtnPanel.setOnClickListener(this);
        mBtnPanel.setOnLongClickListener(this);
        mBtnAV.setOnClickListener(this);
        mBtnAV.setOnLongClickListener(this);
        mBtnMore.setOnClickListener(this);
        mBtnMore.setOnLongClickListener(this);
        mBtnUp.setOnClickListener(this);
        mBtnUp.setOnLongClickListener(this);
        mBtnLeft.setOnClickListener(this);
        mBtnLeft.setOnLongClickListener(this);
        mBtnCenter.setOnClickListener(this);
        mBtnCenter.setOnLongClickListener(this);
        mBtnRight.setOnClickListener(this);
        mBtnRight.setOnLongClickListener(this);
        mBtnDown.setOnClickListener(this);
        mBtnDown.setOnLongClickListener(this);
        mBtnSearch.setOnClickListener(this);
        mBtnSearch.setOnLongClickListener(this);

        backButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_btn_power:
                Toast.makeText(mContext, "power clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_btn_panel:
                Toast.makeText(mContext, "panel clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_btn_avtv:
                Toast.makeText(mContext, "avtv clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_btn_more:
                Toast.makeText(mContext, "more clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_btn_up:
                Toast.makeText(mContext, "up clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_btn_left:
                Toast.makeText(mContext, "left clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_btn_center:
                Toast.makeText(mContext, "center clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_btn_right:
                Toast.makeText(mContext, "right clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_btn_down:
                Toast.makeText(mContext, "down clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_btn_search:
                Toast.makeText(mContext, "search clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.backButton:
                Intent intent = new Intent(TvPanelActivity.this, MainActivity.class);
                startActivity(intent);
                TvPanelActivity.this.finish();
                break;
        }
    }

    @Override
    public boolean onLongClick(View view) {
        switch (view.getId()) {
            case R.id.tv_btn_power:
                Toast.makeText(mContext, "power long clicked", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.tv_btn_panel:
                Toast.makeText(mContext, "panel long clicked", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.tv_btn_avtv:
                Toast.makeText(mContext, "avtv long clicked", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.tv_btn_more:
                Toast.makeText(mContext, "more long clicked", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.tv_btn_up:
                Toast.makeText(mContext, "up long clicked", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.tv_btn_left:
                Toast.makeText(mContext, "left long clicked", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.tv_btn_center:
                Toast.makeText(mContext, "center long clicked", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.tv_btn_right:
                Toast.makeText(mContext, "right long clicked", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.tv_btn_down:
                Toast.makeText(mContext, "down long clicked", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.tv_btn_search:
                Toast.makeText(mContext, "search long clicked", Toast.LENGTH_SHORT).show();
                return true;
        }
        return false;
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
