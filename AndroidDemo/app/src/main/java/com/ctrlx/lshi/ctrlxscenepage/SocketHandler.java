package com.ctrlx.lshi.ctrlxscenepage;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import android.os.Handler;

import com.ctrlx.lshi.ctrlxscenepage.ButtonEntry;
import com.ctrlx.lshi.ctrlxscenepage.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;

public class SocketHandler {
    private static final String SEVERIP = "10.10.10.102";
    private static final int SEVERPORT = 6666;
    DatabaseHandler db;
    private static Socket socket = null;
    private static PrintWriter out = null;
    private static BufferedReader in = null;
    private static String instruction;

    private static Context mContext;

    class ClientThread implements Runnable {
        @Override
        public void run() {
            createNewSocket();
            String receive = communicate("hello:iniWIFI");
            if (receive.contains("rdy")) {
                communicate("info:Hotspot/12345678");
            } else {
                int messageResId = R.string.connection_fail_toast;
                Toast.makeText(mContext, messageResId, Toast.LENGTH_SHORT).show();
            }
        }
    }

    static class ConnectionThread implements Runnable {
        @Override
        public void run() {
            Toast.makeText(mContext, "start creating socket", Toast.LENGTH_SHORT).show();
            createNewSocket();
            String receive = communicate("hello:iniConnection");
            if (receive.contains("rdy")) {
                int messageResId = R.string.connection_success_toast;
                Toast.makeText(mContext, messageResId, Toast.LENGTH_SHORT).show();
            } else {
                int messageResId = R.string.connection_fail_toast;
                Toast.makeText(mContext, messageResId, Toast.LENGTH_SHORT).show();
            }
        }
    }

    class ButtonCtrl implements Runnable {
        int btn_id;

        ButtonCtrl(int btn_id) {
            this.btn_id = btn_id;
        }

        @Override
        public void run() {
            createNewSocket();
            String check = communicate("hello:iniControl:1");
            if (check.contains("rdy")) {
                List<ButtonEntry> list = db.getAllButton();
                boolean exist = false;
                for (ButtonEntry be : list) {
                    if (be.getID().equals(String.valueOf(btn_id))) {
                        exist = true;
                        String control = be.getControl();
                        communicate(control);
                        System.out.println("The Control info size: " + be.getControl().length());
                        System.out.println("The Control info in DB: " + be.getControl());
                    }
                }
                System.out.println("Already exist control info?: " + exist);
            } else {
                int messageResId = R.string.connection_fail_toast;
                Toast.makeText(mContext, messageResId, Toast.LENGTH_SHORT).show();
            }
        }
    }

    class ButtonLearn implements Runnable {
        boolean success = false;
        int btn_id;

        ButtonLearn(int btn_id) {
            this.btn_id = btn_id;
        }

        @Override
        public void run() {
            createNewSocket();
            String check = communicate("hello:iniLearn");
            if (check.contains("rdy")) {
                System.out.println("Received 'rdy'");
                String receive = learningReceive();
                int counter = 0;
                int learnCount = 0;
                while (receive != null && receive.charAt(0) != '1' && learnCount < 2) {
                    String check2 = communicate("failed:iniLearn");
                    System.out.println("Send 'failed:iniLearn'");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    createNewSocket();
                    check2 = communicate("hello:iniLearn");
                    System.out.println("Send 'hello:iniLearn'");
                    if (check2.contains("rdy")) {
                        System.out.println("Received 'rdy'");
                        receive = learningReceive();
                    }

                    learnCount++;
                }
                if (learnCount >= 2) {
                    System.out.println("Learn twice, didnt get right format");
                }
                while (!receive.contains("xend") && counter < 60) {
                    receive += learningReceive();
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    counter++;
                }
                if (counter >= 60) {//learning timeout
                    communicate("failed:iniLearn");
                    System.out.println("Learning Time Out");
                    int messageResId = R.string.learn_receive_timeout_toast;
                    Toast.makeText(mContext, messageResId, Toast.LENGTH_SHORT).show();
                } else {

                    communicate("recvd:iniLearn");

                    System.out.println("Receive contains 'xend'? " + receive.contains("xend"));
                    System.out.println("LearningMode receives size: " + receive.length());
                    System.out.println("LearningMode receives: " + receive);
                    int indexEnd = receive.indexOf("xend");
                    //int indexStart = receive.indexOf("d");
                    receive = receive.substring(0, indexEnd + 4);
                    ButtonEntry b = new ButtonEntry(String.valueOf(btn_id), receive);
                    db.addInfo(b);
                    //Make toast, learning success
                    int messageResId = R.string.learn_success_toast;
                    Toast.makeText(mContext, messageResId, Toast.LENGTH_SHORT).show();
                }

                Log.d("Reading: ", "Reading all button entries..");
                List<ButtonEntry> contacts = db.getAllButton();

                for (ButtonEntry cn : contacts) {
                    String log = "Id: " + cn.getID() + " , Control Information: " + cn.getControl();
                    // Writing Contacts to log
                    Log.d("Name: ", log);
                }

            } else {
                int messageResId = R.string.learn_connect_fail_toast;
                Toast.makeText(mContext, messageResId, Toast.LENGTH_SHORT).show();
            }

        }

        public String learningReceive() {
            String revMessage = "";
            try {
                char[] messageByte = new char[1000];
                int byteRead = in.read(messageByte);
                if (byteRead >= 0) {
                    revMessage += new String(messageByte, 0, byteRead);
                }

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return revMessage;
        }
    }

    public static void createNewSocket() {
        try {
            InetAddress serverAddr = InetAddress.getByName(SEVERIP);
            socket = new Socket(serverAddr, SEVERPORT);

            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println("createNewSocket");
            e.printStackTrace();
        }
    }

    public static String communicate(String msg) {
        String revMessage = "";
        instruction = msg;
        if (out != null) {
            out.print(instruction);
            out.flush();
        }
        try {
            char[] messageByte = new char[1000];
            int byteRead = in.read(messageByte);
            if (byteRead >= 0) {
                revMessage += new String(messageByte, 0, byteRead);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return revMessage;
    }
}