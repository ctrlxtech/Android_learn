package com.ctrlx.lshi.ctrlxscenepage;


import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

/**
 * Created by lshi19 on 1/9/15.
 */
public class TvActivity extends ActionBarActivity implements View.OnClickListener{
    private Button mBtnTheatre;
    private RelativeLayout mLayout;

    int clickFlag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_mode);


        mLayout = (RelativeLayout) findViewById(R.id.layout);
        mBtnTheatre = (Button) findViewById(R.id.btn_theatre);

        mBtnTheatre.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_theatre:
                if (clickFlag == 0) {
                    mLayout.setBackground(getResources().getDrawable(R.drawable.clicked));
                    clickFlag = 1;
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
