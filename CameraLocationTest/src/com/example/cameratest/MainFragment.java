package com.example.cameratest;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainFragment extends Fragment implements OnClickListener {
	protected static final int MSG_SHOW_LOCATION = 0;
	private Button mBtnScan;
	private TextView mTvScanFormat;
	private TextView mTvScanContent;
	private Button mBtnLoc;
	private TextView mTvLoc;
	private LocationManager lm;
	private boolean b;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		b = openGPSSettings();
	}

	private boolean openGPSSettings() {
		boolean a;
		lm = (LocationManager) this.getActivity().getSystemService(Context.LOCATION_SERVICE);
		if (lm.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
			Toast.makeText(getActivity(), "GPS is await", Toast.LENGTH_SHORT).show();;
			a = true;
		} else {
			Toast.makeText(getActivity(), "GPS is not available", Toast.LENGTH_SHORT).show();;
			a = false;
		}
		return a;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_main, container, false);
		mBtnScan = (Button) view.findViewById(R.id.btn_scan);
		mTvScanFormat = (TextView) view.findViewById(R.id.tv_scan_format);
		mTvScanContent = (TextView) view.findViewById(R.id.tv_scan_content);
		mBtnLoc = (Button) view.findViewById(R.id.btn_loc);
		mTvLoc = (TextView) view.findViewById(R.id.tv_loc);
		mBtnScan.setOnClickListener(this);
		if (b) {
			mBtnLoc.setClickable(b);
			mBtnLoc.setOnClickListener(this);
		}
		return view;
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.btn_scan:
//			IntentIntegrator scanIntegrator = new IntentIntegrator(this);
//			scanIntegrator.initiateScan();
			Intent intent = new Intent("com.google.zxing.client.android.SCAN");
			intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
			startActivityForResult(intent,0);
			break;
		case R.id.btn_loc:
			mThread.start();
			break;
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
//		IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
//		Log.i("TAG", "HERE");
//		if (scanResult != null) {
//			String scanFormat = scanResult.getFormatName();
//			Log.i("TAG", scanFormat);
//			String scanContent = scanResult.getContents();
//			Log.i("TAG", scanContent);
//			mTvScanFormat.setText("Format: " + scanFormat);
//			mTvScanContent.setText("Content: " + scanContent);
//		} else {
//			Toast toast = Toast.makeText(getActivity().getApplicationContext(), "No scan data", Toast.LENGTH_SHORT);
//			toast.show();
//		}
		if (requestCode == 0) {
			if (resultCode == MainActivity.RESULT_OK) {
				String contents = intent.getStringExtra("SCAN_RESULT");
				String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
				mTvScanContent.setText(contents + "\n" + format);
			} else if (resultCode == MainActivity.RESULT_CANCELED) {
				Log.i("TAG", "Scan unsuccessful");
			}
		}
	}

	private final Thread mThread = new Thread() {
		@Override
		public void run() {
			Criteria c = new Criteria();
			c.setAccuracy(Criteria.ACCURACY_FINE);
			c.setAltitudeRequired(false);
			c.setBearingRequired(false);
			c.setCostAllowed(true);
			c.setPowerRequirement(Criteria.POWER_LOW);

			String provider = lm.getBestProvider(c, true);
			Location location = lm.getLastKnownLocation(provider);
			sendLocationMessage(location);
//			lm.requestLocationUpdates(provider, 100 * 1000, 5000, locationListener);
		}

		private void sendLocationMessage(Location location) {
			Message msg = Message.obtain();
			msg.what = MSG_SHOW_LOCATION;
			msg.obj = location;
			mHandler.sendMessage(msg);
		}

		private final LocationListener locationListener = new LocationListener() {

			@Override
			public void onLocationChanged(Location location) {
				sendLocationMessage(location);
			}

			@Override
			public void onStatusChanged(String provider, int status,
					Bundle extras) {

			}

			@Override
			public void onProviderEnabled(String provider) {

			}

			@Override
			public void onProviderDisabled(String provider) {

			}

		};


	};

	private final Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case MSG_SHOW_LOCATION:
				Location loc = (Location) msg.obj;
				if (msg.obj != null) {
					double  latitude = loc.getLatitude();
		            double longitude= loc.getLongitude();
		            mTvLoc.setText("latitude£º" +  latitude+ "\nlongtitude: " + longitude);
				} else {
					mTvLoc.setText("failed ");
				}
				break;
//			case MSG_TOAST:

			}

		}
	};


}
