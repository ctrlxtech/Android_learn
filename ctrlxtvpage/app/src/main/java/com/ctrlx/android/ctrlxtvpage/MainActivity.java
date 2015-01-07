package com.ctrlx.android.ctrlxtvpage;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
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


public class MainActivity extends Activity  {
    private static final String SEVERIP = "10.10.10.103";
    private static final int SEVERPORT = 6666;

    DatabaseHandler db;

    private Button mBtnPower;
    private Button mBtnPanel;
    private Button mBtnAV;
    private Button mBtnMore;
    private Button mBtnUp;
    private Button mBtnLeft;
    private Button mBtnRight;
    private Button mBtnDown;
    private Button mBtnSearch;

    Socket socket = null;
    PrintWriter out = null;
    BufferedReader in = null;
    String instruction;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        mContext = this.getApplicationContext();

        db = new DatabaseHandler(this);


        mBtnPower = (Button) findViewById(R.id.tv_btn_power);
        mBtnPanel = (Button) findViewById(R.id.tv_btn_panel);
        mBtnAV = (Button) findViewById(R.id.tv_btn_avtv);
        mBtnMore = (Button) findViewById(R.id.tv_btn_more);
        mBtnUp = (Button) findViewById(R.id.tv_btn_up);
        mBtnLeft = (Button) findViewById(R.id.tv_btn_left);
        mBtnRight = (Button) findViewById(R.id.tv_btn_right);
        mBtnDown = (Button) findViewById(R.id.tv_btn_down);
        mBtnSearch = (Button) findViewById(R.id.tv_btn_search);
//
//        mBtnPower.setOnClickListener(this);
//        mBtnPower.setOnLongClickListener(this);
//        mBtnPanel.setOnClickListener(this);
//        mBtnPanel.setOnLongClickListener(this);
//        mBtnAV.setOnClickListener(this);
//        mBtnAV.setOnLongClickListener(this);
//        mBtnMore.setOnClickListener(this);
//        mBtnMore.setOnLongClickListener(this);
//        mBtnUp.setOnClickListener(this);
//        mBtnUp.setOnLongClickListener(this);
//        mBtnLeft.setOnClickListener(this);
//        mBtnLeft.setOnLongClickListener(this);
//        mBtnRight.setOnClickListener(this);
//        mBtnRight.setOnLongClickListener(this);
//        mBtnDown.setOnClickListener(this);
//        mBtnDown.setOnLongClickListener(this);
//        mBtnSearch.setOnClickListener(this);
//        mBtnSearch.setOnLongClickListener(this);


        Button.OnClickListener buttonClickListener
                = new Button.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                Thread clientThread=new Thread(new ClientThread());
                clientThread.start();
            }};
        mBtnMore.setOnClickListener(buttonClickListener);

        Button.OnClickListener buttonConnectListener
                = new Button.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                Thread connectionThread = new Thread(new ConnectionThread());
                connectionThread.start();
            }};
        mBtnAV.setOnClickListener(buttonConnectListener);

        Button.OnClickListener buttonUpCrtlListener
                = new Button.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                Thread ctrlUp = new Thread(new ButtonUpCtrl());
                ctrlUp.start();
                Toast.makeText(mContext, "up clicked", Toast.LENGTH_SHORT).show();
            }};
        mBtnUp.setOnClickListener(buttonUpCrtlListener);

        Button.OnClickListener buttonDownCrtlListener
                = new Button.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                Thread ctrlDown = new Thread(new ButtonDownCtrl());
                ctrlDown.start();
                Toast.makeText(mContext, "down clicked", Toast.LENGTH_SHORT).show();
            }};
        mBtnDown.setOnClickListener(buttonDownCrtlListener);

        Button.OnClickListener buttonLeftCrtlListener
                = new Button.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                Thread ctrlLeft = new Thread(new ButtonLeftCtrl());
                ctrlLeft.start();
                Toast.makeText(mContext, "left clicked", Toast.LENGTH_SHORT).show();
            }};
        mBtnLeft.setOnClickListener(buttonLeftCrtlListener);


        Button.OnClickListener buttonRightCrtlListener
                = new Button.OnClickListener(){
            @Override
            public void onClick(View arg0) {
                Thread ctrlRight = new Thread(new ButtonRightCtrl());
                ctrlRight.start();
                Toast.makeText(mContext, "right clicked", Toast.LENGTH_SHORT).show();
            }};
        mBtnRight.setOnClickListener(buttonRightCrtlListener);

        Button.OnLongClickListener buttonUpLearnListener
                = new Button.OnLongClickListener(){
            public boolean onLongClick(View arg0) {
                Thread learnUp = new Thread(new ButtonUpLearn());
                learnUp.start();
                Toast.makeText(mContext, "up long clicked", Toast.LENGTH_SHORT).show();
                return true;
            }
        };
        mBtnUp.setOnLongClickListener(buttonUpLearnListener);


        Button.OnLongClickListener buttonDownLearnListener
                = new Button.OnLongClickListener(){
            public boolean onLongClick(View arg0) {
                Thread learnDown = new Thread(new ButtonDownLearn());
                learnDown.start();
                Toast.makeText(mContext, "down long clicked", Toast.LENGTH_SHORT).show();
                return true;
            }
        };
        mBtnDown.setOnLongClickListener(buttonDownLearnListener);


        Button.OnLongClickListener buttonLeftLearnListener
                = new Button.OnLongClickListener(){
            public boolean onLongClick(View arg0) {
                Thread learnLeft = new Thread(new ButtonLeftLearn());
                learnLeft.start();
                Toast.makeText(mContext, "left long clicked", Toast.LENGTH_SHORT).show();
                return true;
            }
        };
        mBtnLeft.setOnLongClickListener(buttonLeftLearnListener);


        Button.OnLongClickListener buttonRightLearnListener
                = new Button.OnLongClickListener(){
            public boolean onLongClick(View arg0) {
                Thread learnRight = new Thread(new ButtonRightLearn());
                learnRight.start();
                Toast.makeText(mContext, "right long clicked", Toast.LENGTH_SHORT).show();
                return true;
            }
        };
        mBtnRight.setOnLongClickListener(buttonRightLearnListener);


    }

//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.tv_btn_power:
//                Toast.makeText(mContext, "power clicked", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.tv_btn_panel:
//                Toast.makeText(mContext, "panel clicked", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.tv_btn_avtv:
//                Thread connectionThread = new Thread(new ConnectionThread());
//                connectionThread.start();
//                Toast.makeText(mContext, "connection clicked", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.tv_btn_more:
//                Thread clientThread = new Thread(new ClientThread());
//                clientThread.start();
//                Toast.makeText(mContext, "more clicked", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.tv_btn_up:
//                Thread ctrlUp = new Thread(new ButtonUpCtrl());
//                ctrlUp.start();
//                Toast.makeText(mContext, "up clicked", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.tv_btn_left:
//                Thread ctrlLeft = new Thread(new ButtonLeftCtrl());
//                ctrlLeft.start();
//                Toast.makeText(mContext, "left clicked", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.tv_btn_right:
//                Thread ctrlRight = new Thread(new ButtonRightCtrl());
//                ctrlRight.start();
//                Toast.makeText(mContext, "right clicked", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.tv_btn_down:
//                Thread ctrlDown = new Thread(new ButtonDownCtrl());
//                ctrlDown.start();
//                Toast.makeText(mContext, "down clicked", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.tv_btn_search:
//                Toast.makeText(mContext, "search clicked", Toast.LENGTH_SHORT).show();
//                break;
//        }
//    }
//
//    @Override
//    public boolean onLongClick(View view) {
//        switch (view.getId()) {
//            case R.id.tv_btn_power:
//                Toast.makeText(mContext, "power long clicked", Toast.LENGTH_SHORT).show();
//                return true;
//            case R.id.tv_btn_panel:
//                Toast.makeText(mContext, "panel long clicked", Toast.LENGTH_SHORT).show();
//                return true;
//            case R.id.tv_btn_avtv:
//                Toast.makeText(mContext, "avtv long clicked", Toast.LENGTH_SHORT).show();
//                return true;
//            case R.id.tv_btn_more:
//                Toast.makeText(mContext, "more long clicked", Toast.LENGTH_SHORT).show();
//                return true;
//            case R.id.tv_btn_up:
//                Thread learnUp = new Thread(new ButtonUpLearn());
//                learnUp.start();
//                Toast.makeText(mContext, "up long clicked", Toast.LENGTH_SHORT).show();
//                return true;
//            case R.id.tv_btn_left:
//                Thread learnLeft = new Thread(new ButtonLeftLearn());
//                learnLeft.start();
//                Toast.makeText(mContext, "left long clicked", Toast.LENGTH_SHORT).show();
//                return true;
//            case R.id.tv_btn_right:
//                Thread learnRight = new Thread(new ButtonRightLearn());
//                learnRight.start();
//                Toast.makeText(mContext, "right long clicked", Toast.LENGTH_SHORT).show();
//                return true;
//            case R.id.tv_btn_down:
//                Thread learnDown = new Thread(new ButtonDownLearn());
//                learnDown.start();
//                Toast.makeText(mContext, "down long clicked", Toast.LENGTH_SHORT).show();
//                return true;
//            case R.id.tv_btn_search:
//                Toast.makeText(mContext, "search long clicked", Toast.LENGTH_SHORT).show();
//                return true;
//        }
//        return false;
//    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    class ClientThread implements Runnable{
        @Override
        public void run(){
            createNewSocket();
            String receive=communicate("hello:iniWIFI");
            if(receive.contains("rdy")){
                communicate("info:Hotspot/12345678");
            }
            else{
                MainActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        int messageResId=R.string.connection_fail_toast;
                        Toast.makeText(MainActivity.this, messageResId, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    class ConnectionThread implements Runnable{
        @Override
        public void run(){
            createNewSocket();
            String receive=communicate("hello:iniConnection");
            if(receive.contains("rdy")){
                MainActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        int messageResId=R.string.connection_success_toast;
                        Toast.makeText(MainActivity.this, messageResId, Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else{
                MainActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        int messageResId=R.string.connection_fail_toast;
                        Toast.makeText(MainActivity.this, messageResId, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    class ButtonUpCtrl implements Runnable{
        @Override
        public void run(){
            createNewSocket();
            String check=communicate("hello:iniControl:1");
            if(check.contains("rdy")){
                List<ButtonEntry> list = db.getAllButton();
                boolean exist =false;
                for(ButtonEntry be:list){
                    if(be.getID().equals(String.valueOf(R.id.tv_btn_up))){
                        exist=true;
                        String control = be.getControl();
                        //int index = control.indexOf("dwz");//should change here~~~~~~~
                        //control = control.substring(index);
                        communicate(control);
                        System.out.println("The Control info size: "+be.getControl().length());
                        System.out.println("The Control info in DB: "+be.getControl());
                    }
                }
                System.out.println("Already exist control info?: "+exist);
            }else{
                MainActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        int messageResId=R.string.connection_fail_toast;
                        Toast.makeText(MainActivity.this, messageResId, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    class ButtonDownCtrl implements Runnable{
        @Override
        public void run(){
            createNewSocket();
            String check=communicate("hello:iniControl:1");
            if(check.contains("rdy")){
                List<ButtonEntry> list = db.getAllButton();
                boolean exist =false;
                for(ButtonEntry be:list){
                    if(be.getID().equals(String.valueOf(R.id.tv_btn_down))){
                        exist=true;
                        String control = be.getControl();
                        //int index = control.indexOf("dwz");//should change here~~~~~~~
                        //control = control.substring(index);
                        communicate(control);
                        System.out.println("The Control info in DB: "+be.getControl());
                    }
                }
                System.out.println("Already exist control info?: "+exist);
            }else{
                MainActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        int messageResId=R.string.connection_fail_toast;
                        Toast.makeText(MainActivity.this, messageResId, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    class ButtonLeftCtrl implements Runnable{
        @Override
        public void run(){
            createNewSocket();
            String check=communicate("hello:iniControl:1");
            if(check.contains("rdy")){
                List<ButtonEntry> list = db.getAllButton();
                boolean exist =false;
                for(ButtonEntry be:list){
                    if(be.getID().equals(String.valueOf(R.id.tv_btn_left))){
                        exist=true;
                        String control = be.getControl();
                        //int index = control.indexOf("dwz");//should change here~~~~~~~
                        //control = control.substring(index);
                        communicate(control);
                        System.out.println("The Control info in DB: "+be.getControl());
                    }
                }
                System.out.println("Already exist control info?: "+exist);
            }else{
                MainActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        int messageResId=R.string.connection_fail_toast;
                        Toast.makeText(MainActivity.this, messageResId, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    class ButtonRightCtrl implements Runnable{
        @Override
        public void run(){
            createNewSocket();
            String check=communicate("hello:iniControl:1");
            if(check.contains("rdy")){
                List<ButtonEntry> list = db.getAllButton();
                boolean exist =false;
                for(ButtonEntry be:list){
                    if(be.getID().equals(String.valueOf(R.id.tv_btn_right))){
                        exist=true;
                        String control = be.getControl();
                        //int index = control.indexOf("dwz");//should change here~~~~~~~
                        //control = control.substring(index);
                        communicate(control);
                        System.out.println("The Control info in DB: "+be.getControl());
                    }
                }
                System.out.println("Already exist control info?: "+exist);
            }else{
                MainActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        int messageResId=R.string.connection_fail_toast;
                        Toast.makeText(MainActivity.this, messageResId, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    class ButtonUpLearn implements Runnable{
        boolean success = false;
        @Override
        public void run(){
            createNewSocket();
            String check=communicate("hello:iniLearn");
            if(check.contains("rdy")){
                System.out.println("Received 'rdy'");
                String receive = learningReceive();
                int counter=0;
                int learnCount=0;
                while(receive!=null&&receive.charAt(0)!='1'&&learnCount<2){
                    String check2=communicate("failed:iniLearn");
                    System.out.println("Send 'failed:iniLearn'");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    createNewSocket();
                    check2=communicate("hello:iniLearn");
                    System.out.println("Send 'hello:iniLearn'");
                    if(check2.contains("rdy")){
                        System.out.println("Received 'rdy'");
                        receive = learningReceive();
                    }

                    learnCount++;
                }
                if(learnCount>=2){
                    System.out.println("Learn twice, didnt get right format");
                }
                while(!receive.contains("xend")&&counter<60){
                    receive +=  learningReceive();
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    counter++;
                }
                if(counter>=60){//learning timeout
                    communicate("failed:iniLearn");
                    MainActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            System.out.println("Learning Time Out");
                            int messageResId=R.string.learn_receive_timeout_toast;
                            Toast.makeText(MainActivity.this, messageResId, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else{

                    communicate("recvd:iniLearn");

                    System.out.println("Receive contains 'xend'? "+receive.contains("xend"));
                    System.out.println("LearningMode receives size: "+receive.length());
                    System.out.println("LearningMode receives: "+receive);
                    int indexEnd = receive.indexOf("xend");
                    //int indexStart = receive.indexOf("d");
                    receive=receive.substring(0, indexEnd+4);
                    ButtonEntry b =new ButtonEntry(String.valueOf(R.id.tv_btn_up),receive);
                    db.addInfo(b);
                    //Make toast, learning success
                    MainActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            int messageResId=R.string.learn_success_toast;
                            Toast.makeText(MainActivity.this, messageResId, Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                Log.d("Reading: ", "Reading all button entries..");
                List<ButtonEntry> contacts = db.getAllButton();

                for (ButtonEntry cn : contacts) {
                    String log = "Id: "+cn.getID()+" , Control Information: " + cn.getControl();
                    // Writing Contacts to log
                    Log.d("Name: ", log);
                }

            }else{
                MainActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        int messageResId=R.string.learn_connect_fail_toast;
                        Toast.makeText(MainActivity.this, messageResId, Toast.LENGTH_SHORT).show();
                    }
                });
            }

        }

        public String learningReceive(){
            String revMessage = "";
            try {
                char[] messageByte = new char[1000];
                int byteRead = in.read(messageByte);
                if(byteRead>=0){
                    revMessage += new String(messageByte, 0, byteRead);
                }

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return revMessage;
        }
    }

    class ButtonDownLearn implements Runnable{
        boolean success = false;
        @Override
        public void run(){
            createNewSocket();
            String check=communicate("hello:iniLearn");
            if(check.contains("rdy")){
                System.out.println("Received 'rdy'");
                String receive = learningReceive();
                int counter=0;
                int learnCount=0;
                while(receive!=null&&receive.charAt(0)!='1'&&learnCount<2){
                    String check2=communicate("failed:iniLearn");
                    System.out.println("Send 'failed:iniLearn'");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    createNewSocket();
                    check2=communicate("hello:iniLearn");
                    System.out.println("Send 'hello:iniLearn'");
                    if(check2.contains("rdy")){
                        System.out.println("Received 'rdy'");
                        receive = learningReceive();
                    }

                    learnCount++;
                }
                if(learnCount>=2){
                    System.out.println("Learn twice, didnt get right format");
                }
                while(!receive.contains("xend")&&counter<60){
                    receive +=  learningReceive();
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    counter++;
                }
                if(counter>=60){//learning timeout
                    MainActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            System.out.println("Learning Time Out");
                            int messageResId=R.string.learn_receive_timeout_toast;
                            Toast.makeText(MainActivity.this, messageResId, Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                communicate("recvd:iniLearn");

                System.out.println("Receive contains 'xend'? "+receive.contains("xend"));
                System.out.println("LearningMode receives size: "+receive.length());
                System.out.println("LearningMode receives: "+receive);
                int indexEnd = receive.indexOf("xend");
                //int indexStart = receive.indexOf("d");
                receive=receive.substring(0, indexEnd+4);
                ButtonEntry b =new ButtonEntry(String.valueOf(R.id.tv_btn_down),receive);
                db.addInfo(b);
                //Make toast, learning success
                MainActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        int messageResId=R.string.learn_success_toast;
                        Toast.makeText(MainActivity.this, messageResId, Toast.LENGTH_SHORT).show();
                    }
                });

                Log.d("Reading: ", "Reading all button entries..");
                List<ButtonEntry> contacts = db.getAllButton();

                for (ButtonEntry cn : contacts) {
                    String log = "Id: "+cn.getID()+" , Control Information: " + cn.getControl();
                    // Writing Contacts to log
                    Log.d("Name: ", log);
                }

            }else{
                MainActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        int messageResId=R.string.learn_connect_fail_toast;
                        Toast.makeText(MainActivity.this, messageResId, Toast.LENGTH_SHORT).show();
                    }
                });
            }

        }

        public String learningReceive(){
            String revMessage = "";
            try {
                char[] messageByte = new char[1000];
                int byteRead = in.read(messageByte);
                if(byteRead>=0){
                    revMessage += new String(messageByte, 0, byteRead);
                }

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return revMessage;
        }
    }

    class ButtonLeftLearn implements Runnable{
        boolean success = false;
        @Override
        public void run(){
            createNewSocket();
            String check=communicate("hello:iniLearn");
            if(check.contains("rdy")){
                System.out.println("Received 'rdy'");
                String receive = learningReceive();
                int counter=0;
                int learnCount=0;
                while(receive!=null&&receive.charAt(0)!='1'&&learnCount<2){
                    String check2=communicate("failed:iniLearn");
                    System.out.println("Send 'failed:iniLearn'");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    createNewSocket();
                    check2=communicate("hello:iniLearn");
                    System.out.println("Send 'hello:iniLearn'");
                    if(check2.contains("rdy")){
                        System.out.println("Received 'rdy'");
                        receive = learningReceive();
                    }

                    learnCount++;
                }
                if(learnCount>=2){
                    System.out.println("Learn twice, didnt get right format");
                }
                while(!receive.contains("xend")&&counter<60){
                    receive +=  learningReceive();
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    counter++;
                }
                if(counter>=60){//learning timeout
                    MainActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            System.out.println("Learning Time Out");
                            int messageResId=R.string.learn_receive_timeout_toast;
                            Toast.makeText(MainActivity.this, messageResId, Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                communicate("recvd:iniLearn");

                System.out.println("Receive contains 'xend'? "+receive.contains("xend"));
                System.out.println("LearningMode receives size: "+receive.length());
                System.out.println("LearningMode receives: "+receive);
                int indexEnd = receive.indexOf("xend");
                //int indexStart = receive.indexOf("d");
                receive=receive.substring(0, indexEnd+4);
                ButtonEntry b =new ButtonEntry(String.valueOf(R.id.tv_btn_left),receive);
                db.addInfo(b);
                //Make toast, learning success
                MainActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        int messageResId=R.string.learn_success_toast;
                        Toast.makeText(MainActivity.this, messageResId, Toast.LENGTH_SHORT).show();
                    }
                });

                Log.d("Reading: ", "Reading all button entries..");
                List<ButtonEntry> contacts = db.getAllButton();

                for (ButtonEntry cn : contacts) {
                    String log = "Id: "+cn.getID()+" , Control Information: " + cn.getControl();
                    // Writing Contacts to log
                    Log.d("Name: ", log);
                }

            }else{
                MainActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        int messageResId=R.string.learn_connect_fail_toast;
                        Toast.makeText(MainActivity.this, messageResId, Toast.LENGTH_SHORT).show();
                    }
                });
            }

        }

        public String learningReceive(){
            String revMessage = "";
            try {
                char[] messageByte = new char[1000];
                int byteRead = in.read(messageByte);
                if(byteRead>=0){
                    revMessage += new String(messageByte, 0, byteRead);
                }

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return revMessage;
        }
    }

    class ButtonRightLearn implements Runnable{
        boolean success = false;
        @Override
        public void run(){
            createNewSocket();
            String check=communicate("hello:iniLearn");
            if(check.contains("rdy")){
                System.out.println("Received 'rdy'");
                String receive = learningReceive();
                int counter=0;
                int learnCount=0;
                while(receive!=null&&receive.charAt(0)!='1'&&learnCount<2){
                    String check2=communicate("failed:iniLearn");
                    System.out.println("Send 'failed:iniLearn'");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    createNewSocket();
                    check2=communicate("hello:iniLearn");
                    System.out.println("Send 'hello:iniLearn'");
                    if(check2.contains("rdy")){
                        System.out.println("Received 'rdy'");
                        receive = learningReceive();
                    }

                    learnCount++;
                }
                if(learnCount>=2){
                    System.out.println("Learn twice, didnt get right format");
                }
                while(!receive.contains("xend")&&counter<60){
                    receive +=  learningReceive();
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    counter++;
                }
                if(counter>=60){//learning timeout
                    MainActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            System.out.println("Learning Time Out");
                            int messageResId=R.string.learn_receive_timeout_toast;
                            Toast.makeText(MainActivity.this, messageResId, Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                communicate("recvd:iniLearn");

                System.out.println("Receive contains 'xend'? "+receive.contains("xend"));
                System.out.println("LearningMode receives size: "+receive.length());
                System.out.println("LearningMode receives: "+receive);
                int indexEnd = receive.indexOf("xend");
                //int indexStart = receive.indexOf("d");
                receive=receive.substring(0, indexEnd+4);
                ButtonEntry b =new ButtonEntry(String.valueOf(R.id.tv_btn_right),receive);
                db.addInfo(b);
                //Make toast, learning success
                MainActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        int messageResId=R.string.learn_success_toast;
                        Toast.makeText(MainActivity.this, messageResId, Toast.LENGTH_SHORT).show();
                    }
                });

                Log.d("Reading: ", "Reading all button entries..");
                List<ButtonEntry> contacts = db.getAllButton();

                for (ButtonEntry cn : contacts) {
                    String log = "Id: "+cn.getID()+" , Control Information: " + cn.getControl();
                    // Writing Contacts to log
                    Log.d("Name: ", log);
                }

            }else{
                MainActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        int messageResId=R.string.learn_connect_fail_toast;
                        Toast.makeText(MainActivity.this, messageResId, Toast.LENGTH_SHORT).show();
                    }
                });
            }

        }

        public String learningReceive(){
            String revMessage = "";
            try {
                char[] messageByte = new char[1000];
                int byteRead = in.read(messageByte);
                if(byteRead>=0){
                    revMessage += new String(messageByte, 0, byteRead);
                }

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return revMessage;
        }
    }



    public void createNewSocket(){
        try {
            InetAddress serverAddr = InetAddress.getByName(SEVERIP);
            socket = new Socket(serverAddr, SEVERPORT);

            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String communicate(String msg){
        String revMessage = "";
        instruction = msg;
        if(out!=null){
            out.print(instruction);
            out.flush();
        }
        try {
            char[] messageByte = new char[1000];
            int byteRead = in.read(messageByte);
            if(byteRead>=0){
                revMessage += new String(messageByte, 0, byteRead);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return revMessage;
    }
}
