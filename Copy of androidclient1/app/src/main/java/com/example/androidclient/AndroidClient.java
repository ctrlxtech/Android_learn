package com.example.androidclient;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class AndroidClient extends Activity {

	private static final String SEVERIP = "10.10.10.102";//10.32.106.222   192.168.1.45
	private static final int SEVERPORT = 6666;
	private String learntMsg;

	DatabaseHandler db;

	EditText textOut;
	TextView textIn;
	Button buttonSend1;
	Button buttonSend2;
	Button buttonSend3;
	Button buttonSend4;
	Button buttonSend5;
	Button buttonSend6;
	Button buttonSend7;
	Button buttonSend8;
	Button buttonSend9;
	Button buttonSend10;
	Button buttonSend11;
	Button buttonSend12;
	Socket socket = null;
	PrintWriter out = null;
	BufferedReader in = null;
	String instruction;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.v("MyActivity", "Start program!!!!!");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		db = new DatabaseHandler(this);

		buttonSend1 = (Button)findViewById(R.id.button1);
		buttonSend2 = (Button)findViewById(R.id.button2);
		buttonSend3 = (Button)findViewById(R.id.button3);
		buttonSend4 = (Button)findViewById(R.id.button4);
		buttonSend5 = (Button)findViewById(R.id.button5);
		buttonSend6 = (Button)findViewById(R.id.button6);
		buttonSend7 = (Button)findViewById(R.id.button7);
		buttonSend8 = (Button)findViewById(R.id.button8);
		buttonSend9 = (Button)findViewById(R.id.button9);
		buttonSend10 = (Button)findViewById(R.id.button10);
		buttonSend11 = (Button)findViewById(R.id.button11);
		buttonSend12 = (Button)findViewById(R.id.button12);
		textIn = (TextView)findViewById(R.id.textin);

		Button.OnClickListener buttonSendOnClickListener
		= new Button.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				Thread clientThread=new Thread(new ClientThread());
				clientThread.start();
			}};



			Button.OnClickListener buttonSendOnClickListener2
			= new Button.OnClickListener(){
				@Override
				public void onClick(View arg0) {
					Thread connectionThread = new Thread(new ConnectionThread());
					connectionThread.start();
				}};

				Button.OnClickListener buttonSendOnClickListener3
				= new Button.OnClickListener(){
					@Override
					public void onClick(View arg0) {
						Thread learnThread=new Thread(new LearningThread1());
						learnThread.start();
						System.out.println("Learning Clicked");
					}};

					Button.OnClickListener buttonSendOnClickListener4
					= new Button.OnClickListener(){
						@Override
						public void onClick(View arg0) {
							Thread controlThread = new Thread(new ControlThread1());
							controlThread.start();
						}};


						Button.OnClickListener buttonSendOnClickListener5
						= new Button.OnClickListener(){
							@Override
							public void onClick(View arg0) {
								Thread learnThread=new Thread(new LearningThread2());
								learnThread.start();
							}};		

							Button.OnClickListener buttonSendOnClickListener6
							= new Button.OnClickListener(){
								@Override
								public void onClick(View arg0) {
									Thread controlThread = new Thread(new ControlThread2());
									controlThread.start();
								}};
								
								Button.OnClickListener buttonSendOnClickListener7
								= new Button.OnClickListener(){
									@Override
									public void onClick(View arg0) {
										Thread learnThread=new Thread(new LearningThread3());
										learnThread.start();
									}};	
									
									Button.OnClickListener buttonSendOnClickListener8
									= new Button.OnClickListener(){
										@Override
										public void onClick(View arg0) {
											Thread controlThread = new Thread(new ControlThread3());
											controlThread.start();
										}};
										
										Button.OnClickListener buttonSendOnClickListener9
										= new Button.OnClickListener(){
											@Override
											public void onClick(View arg0) {
												Thread learnThread=new Thread(new LearningThread4());
												learnThread.start();
											}};	
											
											Button.OnClickListener buttonSendOnClickListener10
											= new Button.OnClickListener(){
												@Override
												public void onClick(View arg0) {
													Thread controlThread = new Thread(new ControlThread4());
													controlThread.start();
												}};
												
												Button.OnClickListener buttonSendOnClickListener11
												= new Button.OnClickListener(){
													@Override
													public void onClick(View arg0) {
														Thread learnThread=new Thread(new LearningThread5());
														learnThread.start();
													}};	
													
													Button.OnClickListener buttonSendOnClickListener12
													= new Button.OnClickListener(){
														@Override
														public void onClick(View arg0) {
															Thread controlThread = new Thread(new ControlThread5());
															controlThread.start();
														}};

								buttonSend1.setOnClickListener(buttonSendOnClickListener);
								buttonSend2.setOnClickListener(buttonSendOnClickListener2);
								buttonSend3.setOnClickListener(buttonSendOnClickListener3);
								buttonSend4.setOnClickListener(buttonSendOnClickListener4);
								buttonSend5.setOnClickListener(buttonSendOnClickListener5);
								buttonSend6.setOnClickListener(buttonSendOnClickListener6);
								buttonSend7.setOnClickListener(buttonSendOnClickListener7);
								buttonSend8.setOnClickListener(buttonSendOnClickListener8);
								buttonSend9.setOnClickListener(buttonSendOnClickListener9);
								buttonSend10.setOnClickListener(buttonSendOnClickListener10);
								buttonSend11.setOnClickListener(buttonSendOnClickListener11);
								buttonSend12.setOnClickListener(buttonSendOnClickListener12);


	}

	class ClientThread implements Runnable{
		@Override
		public void run(){
			createNewSocket();
			String receive=communicate("hello:iniWIFI");
			if(receive.contains("rdy")){
				communicate("info:Hotspot/12345678");
			}
			else{
				AndroidClient.this.runOnUiThread(new Runnable() {
					public void run() {
						int messageResId=R.string.connection_fail_toast;
						Toast.makeText(AndroidClient.this, messageResId, Toast.LENGTH_SHORT).show();
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
				AndroidClient.this.runOnUiThread(new Runnable() {
					public void run() {
						int messageResId=R.string.connection_success_toast;
						Toast.makeText(AndroidClient.this, messageResId, Toast.LENGTH_SHORT).show();
					}
				});
			}
			else{
				AndroidClient.this.runOnUiThread(new Runnable() {
					public void run() {
						int messageResId=R.string.connection_fail_toast;
						Toast.makeText(AndroidClient.this, messageResId, Toast.LENGTH_SHORT).show();
					}
				});
			}
		}
	}

	class ControlThread1 implements Runnable{
		@Override
		public void run(){
			createNewSocket();
			String check=communicate("hello:iniControl:1");
			if(check.contains("rdy")){
				List<ButtonEntry> list = db.getAllButton();
				boolean exist =false;
				for(ButtonEntry be:list){
					if(be.getID().equals(String.valueOf(R.id.button4))){
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
				AndroidClient.this.runOnUiThread(new Runnable() {
					public void run() {
						int messageResId=R.string.connection_fail_toast;
						Toast.makeText(AndroidClient.this, messageResId, Toast.LENGTH_SHORT).show();
					}
				});
			}
		}
	}

	class ControlThread2 implements Runnable{
		@Override
		public void run(){
			createNewSocket();
			String check=communicate("hello:iniControl:1");
			if(check.contains("rdy")){
				List<ButtonEntry> list = db.getAllButton();
				boolean exist =false;
				for(ButtonEntry be:list){
					if(be.getID().equals(String.valueOf(R.id.button6))){
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
				AndroidClient.this.runOnUiThread(new Runnable() {
					public void run() {
						int messageResId=R.string.connection_fail_toast;
						Toast.makeText(AndroidClient.this, messageResId, Toast.LENGTH_SHORT).show();
					}
				});
			}
		}
	}
	
	class ControlThread3 implements Runnable{
		@Override
		public void run(){
			createNewSocket();
			String check=communicate("hello:iniControl:1");
			if(check.contains("rdy")){
				List<ButtonEntry> list = db.getAllButton();
				boolean exist =false;
				for(ButtonEntry be:list){
					if(be.getID().equals(String.valueOf(R.id.button8))){
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
				AndroidClient.this.runOnUiThread(new Runnable() {
					public void run() {
						int messageResId=R.string.connection_fail_toast;
						Toast.makeText(AndroidClient.this, messageResId, Toast.LENGTH_SHORT).show();
					}
				});
			}
		}
	}
	
	class ControlThread4 implements Runnable{
		@Override
		public void run(){
			createNewSocket();
			String check=communicate("hello:iniControl:1");
			if(check.contains("rdy")){
				List<ButtonEntry> list = db.getAllButton();
				boolean exist =false;
				for(ButtonEntry be:list){
					if(be.getID().equals(String.valueOf(R.id.button10))){
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
				AndroidClient.this.runOnUiThread(new Runnable() {
					public void run() {
						int messageResId=R.string.connection_fail_toast;
						Toast.makeText(AndroidClient.this, messageResId, Toast.LENGTH_SHORT).show();
					}
				});
			}
		}
	}
	
	class ControlThread5 implements Runnable{
		@Override
		public void run(){
			createNewSocket();
			String check=communicate("hello:iniControl:1");
			if(check.contains("rdy")){
				List<ButtonEntry> list = db.getAllButton();
				boolean exist =false;
				for(ButtonEntry be:list){
					if(be.getID().equals(String.valueOf(R.id.button12))){
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
				AndroidClient.this.runOnUiThread(new Runnable() {
					public void run() {
						int messageResId=R.string.connection_fail_toast;
						Toast.makeText(AndroidClient.this, messageResId, Toast.LENGTH_SHORT).show();
					}
				});
			}
		}
	}

	class LearningThread1 implements Runnable{
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
					AndroidClient.this.runOnUiThread(new Runnable() {
						public void run() {
							System.out.println("Learning Time Out");
							int messageResId=R.string.learn_receive_timeout_toast;
							Toast.makeText(AndroidClient.this, messageResId, Toast.LENGTH_SHORT).show();
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
				ButtonEntry b =new ButtonEntry(String.valueOf(R.id.button4),receive);
				db.addInfo(b);
				//Make toast, learning success
				AndroidClient.this.runOnUiThread(new Runnable() {
					public void run() {
						int messageResId=R.string.learn_success_toast;
						Toast.makeText(AndroidClient.this, messageResId, Toast.LENGTH_SHORT).show();
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
				AndroidClient.this.runOnUiThread(new Runnable() {
					public void run() {
						int messageResId=R.string.learn_connect_fail_toast;
						Toast.makeText(AndroidClient.this, messageResId, Toast.LENGTH_SHORT).show();
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

	class LearningThread2 implements Runnable{
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
					AndroidClient.this.runOnUiThread(new Runnable() {
						public void run() {
							System.out.println("Learning Time Out");
							int messageResId=R.string.learn_receive_timeout_toast;
							Toast.makeText(AndroidClient.this, messageResId, Toast.LENGTH_SHORT).show();
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
				ButtonEntry b =new ButtonEntry(String.valueOf(R.id.button6),receive);
				db.addInfo(b);
				//Make toast, learning success
				AndroidClient.this.runOnUiThread(new Runnable() {
					public void run() {
						int messageResId=R.string.learn_success_toast;
						Toast.makeText(AndroidClient.this, messageResId, Toast.LENGTH_SHORT).show();
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
				AndroidClient.this.runOnUiThread(new Runnable() {
					public void run() {
						int messageResId=R.string.learn_connect_fail_toast;
						Toast.makeText(AndroidClient.this, messageResId, Toast.LENGTH_SHORT).show();
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
	
	class LearningThread3 implements Runnable{
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
					AndroidClient.this.runOnUiThread(new Runnable() {
						public void run() {
							System.out.println("Learning Time Out");
							int messageResId=R.string.learn_receive_timeout_toast;
							Toast.makeText(AndroidClient.this, messageResId, Toast.LENGTH_SHORT).show();
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
				ButtonEntry b =new ButtonEntry(String.valueOf(R.id.button8),receive);
				db.addInfo(b);
				//Make toast, learning success
				AndroidClient.this.runOnUiThread(new Runnable() {
					public void run() {
						int messageResId=R.string.learn_success_toast;
						Toast.makeText(AndroidClient.this, messageResId, Toast.LENGTH_SHORT).show();
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
				AndroidClient.this.runOnUiThread(new Runnable() {
					public void run() {
						int messageResId=R.string.learn_connect_fail_toast;
						Toast.makeText(AndroidClient.this, messageResId, Toast.LENGTH_SHORT).show();
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
	
	class LearningThread4 implements Runnable{
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
					AndroidClient.this.runOnUiThread(new Runnable() {
						public void run() {
							System.out.println("Learning Time Out");
							int messageResId=R.string.learn_receive_timeout_toast;
							Toast.makeText(AndroidClient.this, messageResId, Toast.LENGTH_SHORT).show();
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
				ButtonEntry b =new ButtonEntry(String.valueOf(R.id.button10),receive);
				db.addInfo(b);
				//Make toast, learning success
				AndroidClient.this.runOnUiThread(new Runnable() {
					public void run() {
						int messageResId=R.string.learn_success_toast;
						Toast.makeText(AndroidClient.this, messageResId, Toast.LENGTH_SHORT).show();
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
				AndroidClient.this.runOnUiThread(new Runnable() {
					public void run() {
						int messageResId=R.string.learn_connect_fail_toast;
						Toast.makeText(AndroidClient.this, messageResId, Toast.LENGTH_SHORT).show();
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
	
	class LearningThread5 implements Runnable{
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
					AndroidClient.this.runOnUiThread(new Runnable() {
						public void run() {
							System.out.println("Learning Time Out");
							int messageResId=R.string.learn_receive_timeout_toast;
							Toast.makeText(AndroidClient.this, messageResId, Toast.LENGTH_SHORT).show();
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
				ButtonEntry b =new ButtonEntry(String.valueOf(R.id.button12),receive);
				db.addInfo(b);
				//Make toast, learning success
				AndroidClient.this.runOnUiThread(new Runnable() {
					public void run() {
						int messageResId=R.string.learn_success_toast;
						Toast.makeText(AndroidClient.this, messageResId, Toast.LENGTH_SHORT).show();
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
				AndroidClient.this.runOnUiThread(new Runnable() {
					public void run() {
						int messageResId=R.string.learn_connect_fail_toast;
						Toast.makeText(AndroidClient.this, messageResId, Toast.LENGTH_SHORT).show();
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
