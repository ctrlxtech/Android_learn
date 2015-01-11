package com.belkin.wemo.localsdk.demo;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.belkin.wemo.localsdk.WeMoDevice;
import com.belkin.wemo.localsdk.demo.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Defines the view of separate WeMoDevice
 *  
 * @version 1.00 20 Aug 2013
 * @author Anastasia Artemyeva
 */
public class ListItem extends FrameLayout {
	/**
	 * Display a name of the WeMoDevice
	 */
	private TextView mName;
	
	/**
	 * Displays a current state of WeMoDevice 
	 */
	private TextView mState;	
	
	/**
	 * Displays a current logo
	 */
	private ImageView mLogo;
	
	/**
	 * Corresponding WeMoDevice
	 */
	private WeMoDevice mDevice = null;
	
	/**
	 * Constructor
	 */
	public ListItem(Context context) {
		super(context);
		init(context);
	}
	
	/**
	 * Sets the layout 
	 * 
	 * @param context application context
	 */	
	private void init(Context context) {
		LayoutInflater.from(context).inflate(R.layout.list_item_layout, this);

		mName = (TextView)findViewById(R.id.name);
		mState = (TextView)findViewById(R.id.state);
		mLogo = (ImageView)findViewById(R.id.logo);
	}
	
	/**
	 * Sets the WeMoDevice corresponding to this ListItem and displays its parameters
	 * 
	 * @param device the corresponding WeMoDevice
	 */
	public void setDevice(WeMoDevice device) {
		mDevice = device;
		mName.setText(mDevice.getFriendlyName());	
		setState(mDevice.getState().split("\\|")[0]);
		
		File logo = new File(mDevice.getLogo());

		if (logo.exists()) {
			//load the logo from local storage
		    Bitmap bitmap = BitmapFactory.decodeFile(logo.getAbsolutePath());
		    mLogo.setImageBitmap(bitmap);
		} else {
			// load the logo from URL
			new LogoDownloader().execute(mDevice.getLogoURL());
		}
	}
	
	/**
	 * @return the corresponding weMoDevice
	 */
	public WeMoDevice getDevice()
	{
		return mDevice;
	}
	
	/**
	 * Sets the displaying state depending on WeMoDevice type 
	 * 
	 * @param state the new state of WeMoDevice
	 */
	public void setState(String state) {
		int stateColor = getResources().getColor(R.color.blue_color);
		String stateText = null;
		
		//SWITCH || LIGHT_SWITCH
		String type = mDevice.getType();
		if (type.equals(WeMoDevice.SWITCH) || type.equals(WeMoDevice.LIGHT_SWITCH)) {
			if (state.equals(WeMoDevice.WEMO_DEVICE_ON)) {
				stateText = getResources().getString(R.string.state_on);
			} else if (state.equals(WeMoDevice.WEMO_DEVICE_OFF)) {
				stateText = getResources().getString(R.string.state_off);
				stateColor = getResources().getColor(R.color.black_color);
			} else if (state.equals(WeMoDevice.WEMO_DEVICE_UNDEFINED)) {
				stateColor = getResources().getColor(R.color.dark_grey_color);
			}
		//SENSOR	
		} else if (type.equals(WeMoDevice.SENSOR)) {
			stateText = state.equals(WeMoDevice.WEMO_DEVICE_ON) ? getResources().getString(R.string.state_motion)
																: getResources().getString(R.string.state_wait);
		//INSIGHT
		} else if (type.equals(WeMoDevice.INSIGHT)) {
			if (state.equals(WeMoDevice.WEMO_DEVICE_ON)) {
				stateText = getResources().getString(R.string.state_load);
			} else if (state.equals(WeMoDevice.WEMO_DEVICE_OFF)) {
				stateColor = getResources().getColor(R.color.black_color);
				stateText = getResources().getString(R.string.state_off);
			} else if (state.equals(WeMoDevice.WEMO_DEVICE_STAND_BY)) {
				stateColor = getResources().getColor(R.color.orange_color);
				stateText = getResources().getString(R.string.state_stand_by);
			} else if (state.equals(WeMoDevice.WEMO_DEVICE_UNDEFINED)) {
				stateColor = getResources().getColor(R.color.dark_grey_color);
			}
		}
		
		mState.setTextColor(stateColor);
		mState.setText(stateText == null ? "" : stateText);
	}
	
	/**
	 * Download the logo from URL
	 * 
	 * @author Anastasia Artemyeva
	 */
	private class LogoDownloader extends AsyncTask<String, Void, Bitmap> {

		@Override
		protected Bitmap doInBackground(String... parameters) {
			Bitmap bitmap = null;
			
			try {
				URL url = new URL(parameters[0]);
		 		HttpURLConnection connection = (HttpURLConnection)url.openConnection();
		 		connection.setRequestMethod("GET");
				if (url.getHost() != null) {
					connection.setRequestProperty("HOST", url.getHost());
				}
				InputStream inputStream = connection.getInputStream();
				bitmap = BitmapFactory.decodeStream(inputStream);
				inputStream.close();
			} catch (Exception e) {
				Log.e(getClass().getName(), "Downloading the logo is unsuccessful");
			}
			
			return bitmap;
		}
		
		@Override
		protected void onPostExecute(Bitmap bitmap) {
			if (bitmap != null) {
				mLogo.setImageBitmap(bitmap);
			}
		}		
	}
}