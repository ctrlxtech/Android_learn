package com.ctrlx.lshi.ctrlxscenepage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;

import com.ctrlx.lshi.ctrlxscenepage.R;


public class TvPanelActivity extends Activity implements View.OnClickListener {

    SocketHandler socket = new SocketHandler();

    private boolean if_learn_mode = false;

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

        //SocketHandler socket = new SocketHandler();

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
        mBtnPanel.setOnClickListener(this);
        mBtnAV.setOnClickListener(this);
        mBtnMore.setOnClickListener(this);
        mBtnUp.setOnClickListener(this);
        mBtnLeft.setOnClickListener(this);
        mBtnCenter.setOnClickListener(this);
        mBtnRight.setOnClickListener(this);
        mBtnDown.setOnClickListener(this);
        mBtnSearch.setOnClickListener(this);

        /*
        mBtnPower.setOnLongClickListener(this);
        mBtnAV.setOnLongClickListener(this);
        mBtnMore.setOnLongClickListener(this);
        mBtnUp.setOnLongClickListener(this);
        mBtnLeft.setOnLongClickListener(this);
        mBtnCenter.setOnLongClickListener(this);
        mBtnRight.setOnLongClickListener(this);
        mBtnDown.setOnLongClickListener(this);
        mBtnSearch.setOnLongClickListener(this);*/

        backButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_btn_more:
                Thread connectionThread = new Thread(socket.new ConnectionThread(mContext));
                connectionThread.start();
                //Toast.makeText(mContext, "more clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.backButton:
                Intent intent = new Intent(TvPanelActivity.this, MainActivity.class);
                startActivity(intent);
                TvPanelActivity.this.finish();
                break;
            case R.id.tv_btn_search:
                if (if_learn_mode){
                    if_learn_mode = false;
                    Toast.makeText(mContext, "switched to control mode", Toast.LENGTH_SHORT).show();
                }
                else{
                    if_learn_mode = true;
                    Toast.makeText(mContext, "switched to learning mode", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                if (if_learn_mode){
                    Thread btnLearn = new Thread(socket.new ButtonLearn(mContext, view.getId()));
                    btnLearn.start();
                    Toast.makeText(mContext, "start learning", Toast.LENGTH_SHORT).show();
                }
                else{
                    Thread btnCtrl = new Thread(socket.new ButtonCtrl(mContext, view.getId()));
                    btnCtrl.start();
                    Toast.makeText(mContext, "start control", Toast.LENGTH_SHORT).show();
                }
        }

    }

/*
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
    } */

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
