package com.belkin.wemo.localsdk.demo;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.belkin.wemo.localsdk.WeMoDevice;
import com.belkin.wemo.localsdk.WeMoSDKContext;
import com.belkin.wemo.localsdk.WeMoSDKContext.NotificationListener;

/**
 * Application demonstrates using of WeMoSDK
 * It displays the list of found WeMoDevices, which can be refreshed,
 * notify about additional and removal of a WeMoDevice
 * and provide ability to control the state of the WeMoDevices
 * 
 * @version 1.00 20 Aug 2013
 * @author Anastasia Artemyeva
 */
public class MainActivity extends Activity implements NotificationListener, 
													  OnClickListener, 
													  OnItemClickListener {
	private ListView mListView;
	private Button mRefreshButton;
	private View mProgressBar;
	
	private WeMoSDKContext mWeMoSDKContext = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mListView = (ListView)findViewById(R.id.list);
		mRefreshButton = (Button)findViewById(R.id.refresh_button);
		mProgressBar = findViewById(R.id.progress_bar);
		
		mListView.setOnItemClickListener(this);
		mRefreshButton.setOnClickListener(this);
		
		mWeMoSDKContext = new WeMoSDKContext(getApplicationContext());		
		mWeMoSDKContext.addNotificationListener(this);
	}
	
	/**
	 * Refresh the list of found devices on first launch
	 * and on coming into foreground
	 */
	@Override
	protected void onStart() {		
		refresh();
		super.onStart();
	}
	
	/**
	 * Stop WeMoSDKContext on application close
	 */
	@Override
	protected void onDestroy() {
		mWeMoSDKContext.stop();
		super.onDestroy();
	}

	/**
	 * Listens the WeMoDevice events
	 * Updates ListView on REFRESH_LIST event, show notification on ADD_DEVICE, REMOVE_DEVICE
	 * and change state of corresponding ListItem of ListView on CHANGE_STATE event 
	 * 
	 * @param event the event
	 * @param udn the UDN of WeMoDevice provided the event
	 */
	@Override
	public void onNotify(final String event, final String udn) {
		this.runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				WeMoDevice wemoDevice = mWeMoSDKContext.getWeMoDeviceByUDN(udn);
				
				if (event.equals(WeMoSDKContext.REFRESH_LIST)) {
					ArrayList<String> udns = mWeMoSDKContext.getListOfWeMoDevicesOnLAN();
					ArrayList<WeMoDevice> wemoDevices = new ArrayList<WeMoDevice>(); 
					
					for (String udn : udns) {
						WeMoDevice listDevice = mWeMoSDKContext.getWeMoDeviceByUDN(udn);
						if (listDevice != null && listDevice.isAvailable()) {
							wemoDevices.add(listDevice);
						}
					}
					mListView.setAdapter(new Adapter(getApplicationContext(), 0, wemoDevices));
					mProgressBar.setVisibility(View.GONE);					
					mRefreshButton.setEnabled(true);
				} else if (wemoDevice == null) {
					//do nothing because of incorrect notification
				} else if (event.equals(WeMoSDKContext.ADD_DEVICE)) {
					Toast.makeText(getApplicationContext(), 
							       getString(R.string.notification_add) + wemoDevice.getFriendlyName(), 
							       Toast.LENGTH_SHORT).show();	
				} else if (event.equals(WeMoSDKContext.REMOVE_DEVICE)) {
					Toast.makeText(getApplicationContext(), 
							       getString(R.string.notification_remove) + wemoDevice.getFriendlyName(),
							       Toast.LENGTH_SHORT).show();
				} else if (event.equals(WeMoSDKContext.CHANGE_STATE) || event.equals(WeMoSDKContext.SET_STATE)) {
					for(int i = 0; i <= mListView.getLastVisiblePosition() - mListView.getFirstVisiblePosition(); i++) {
						ListItem listItem = (ListItem)mListView.getChildAt(i);
						if (listItem.getDevice().getUDN().equals(udn)) {
							listItem.setState(wemoDevice.getState().split("\\|")[0]);
							break;
						}
					}
				}				
			}
		});	
	}
	
	/**
	 * The adapter to ListView
	 * 
	 * @author Anastasia Artemyeva
	 */
	private class Adapter extends ArrayAdapter<WeMoDevice> {
		private ArrayList<WeMoDevice> mDevices;

		/**
		 * Constructor 
		 * 
		 * @param context application context
		 * @param resource (not used)
		 * @param devices the array of WeMoDevices to display
		 */
		public Adapter(Context context, int resource, ArrayList<WeMoDevice> devices) {
			super(context, resource, devices);
			mDevices = devices;
		}
		
		/**
		 * Creates ListItem corresponding to WeMoDevice
		 *	 
		 * @param position the position of WeMoDevice in entry array
		 * @param convertView the created view, corresponded to the WeMoDevice
		 * @param parent (not used)
		 * 
		 * @return created ListItem view
		 */
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = new ListItem(getApplicationContext());
			}
			((ListItem)convertView).setDevice(mDevices.get(position));
			
			return convertView;
		}
	}

	/**
	 * Handle the click on refresh button 
	 * and update the device list
	 */
	@Override
	public void onClick(View view) {
		if (view.getId() == R.id.refresh_button) {
			refresh();
		}		
	}
	
	/**
	 * Updates the list of WeMoDevices in WeMoSDKContext
	 */
	private void refresh() {
		mProgressBar.setVisibility(View.VISIBLE);
		mRefreshButton.setEnabled(false);
		mWeMoSDKContext.refreshListOfWeMoDevicesOnLAN();
	}

	/**
	 * Change the state of a WeMoDevice (if this can be done)
	 * 
	 * @param list ListView
	 * @param view ListItem
	 * @param position the position of current ListItem in ListView
	 * @param id the id of the current view
	 */
	@Override
	public void onItemClick(AdapterView<?> list, View view, int position, long id) {
		WeMoDevice device = ((ListItem)view).getDevice(); 
		
		//we can change the state of switches and insight devices only
		String type = device.getType();
		String state = device.getState().split("\\|")[0];
		
		if(type.equals(WeMoDevice.SWITCH) 
				|| type.equals(WeMoDevice.LIGHT_SWITCH)
				|| type.equals(WeMoDevice.INSIGHT)) {
			String newState = WeMoDevice.WEMO_DEVICE_ON;
			
			if (state.equals(WeMoDevice.WEMO_DEVICE_ON) || state.equals(WeMoDevice.WEMO_DEVICE_STAND_BY)) {
				newState = WeMoDevice.WEMO_DEVICE_OFF;
			}
			
			mWeMoSDKContext.setDeviceState(newState, device.getUDN());
			((ListItem)view).setState(WeMoDevice.WEMO_DEVICE_UNDEFINED);
		}
	}
}