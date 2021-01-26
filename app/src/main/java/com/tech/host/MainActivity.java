package com.tech.host;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import im.delight.android.webview.AdvancedWebView;
import android.support.v4.content.ContextCompat;
import android.support.v4.app.ActivityCompat;
import android.Manifest;
import android.webkit.WebSettings;
import android.webkit.CookieManager;
import java.util.Timer;
import java.util.TimerTask;
import android.annotation.SuppressLint;
import android.net.*;
import android.app.*;
import android.content.pm.*;
import android.content.*;
import android.widget.*;
import android.view.*;
import android.graphics.*;
import android.os.*;

public class MainActivity extends AppCompatActivity implements AdvancedWebView.Listener {
	private Toolbar _toolbar;
	private ProgressBar progressbar1;
	private ImageView imageview1;
	private AdvancedWebView webview1;
	private AlertDialog.Builder dialog;
	private Intent intent = new Intent();
	private double exit = 0;
	private boolean availableNetwork = false;
	private Timer _timer = new Timer();
	private TimerTask timer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
		initialize(savedInstanceState);
			if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
		|| ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
			ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
		}
		else {
			initializeLogic();
		}
	}
	
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == 1000) {
			initializeLogic();
		}
	}

	private void initialize(Bundle savedInstanceState) {
		_toolbar = (Toolbar) findViewById(R.id.toolbar);
		imageview1 = (ImageView) findViewById(R.id.imageview1);
		webview1 = (AdvancedWebView) findViewById(R.id.webview1);
		progressbar1 = (ProgressBar) findViewById(R.id.progressbar1);
		webview1.setBackgroundColor(Color.TRANSPARENT);
		imageview1.setVisibility(View.GONE);
		progressbar1.setVisibility(View.GONE);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			getWindow().setNavigationBarColor(Color.parseColor("#008DCD"));
		}
		dialog = new AlertDialog.Builder(this);
		setSupportActionBar(_toolbar);
	}
	
	private void initializeLogic () {
		webview1.setListener(this, this);
		webview1.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
		webview1.getSettings().setJavaScriptEnabled(true);
		webview1.setThirdPartyCookiesEnabled(true);
		webview1.setCookiesEnabled(true);
		webview1.setMixedContentAllowed(false);
		webview1.setGeolocationEnabled(false);
		webview1.loadUrl("https://panel.techhost.cc");
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 0, 0, getResources().getString(R.string.refresh)).setIcon(R.drawable.refresh).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

		menu.add(0, 1, 1, getResources().getString(R.string.delete_cache)).setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);

		menu.add(0, 2, 2, getResources().getString(R.string.about)).setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);

		menu.add(0, 3, 3, getResources().getString(R.string.pp)).setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);

		menu.add(0, 4, 4, getResources().getString(R.string.cfu)).setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case 0 :
				webview1.reload();
			break;
			case 1 :
				webview1.clearCache(true);
				webview1.clearHistory();
				_customToast("Cache deleted", "#43a047");
			break;
			case 2 :
				intent.setClass(getApplicationContext(), AboutActivity.class);
				startActivity(intent);
			break;
			case 3 :
				dialog.setTitle(getResources().getString(R.string.pp_title));
				dialog.setMessage(getResources().getString(R.string.pp_msg));
				dialog.setPositiveButton(getResources().getString(R.string.pp_close), new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						// Do nothing
					}
				});
				dialog.create().show();
			break;
			case 4 :
				_customToast("Coming soon", "#03a9f4");
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void _customToast (final String _text, final String _color) {
		LayoutInflater inflater = getLayoutInflater();
		View toastLayout = inflater.inflate(R.layout.toast, null);
		TextView textview1 = (TextView) toastLayout.findViewById(R.id.textview1);
		LinearLayout linear1 = (LinearLayout) toastLayout.findViewById(R.id.linear1);

		android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
		gd.setColor(Color.parseColor(_color));
		gd.setCornerRadius(30);
		gd.setStroke(2, Color.parseColor("#ffffff"));
		linear1.setBackground(gd);

		textview1.setText(_text);
		Toast toast = new Toast(getApplicationContext());
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(toastLayout);
		toast.show();
	}
	
	public boolean isNetworkConnectionAvailable(){
		ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		if(activeNetwork != null && activeNetwork.isAvailable() && activeNetwork.isConnected()) {
			return availableNetwork = true;
		} else {
			return availableNetwork = false;
		}
	}
	
	@SuppressLint("NewApi")
	@Override
	public void onBackPressed() {
		if (webview1.canGoBack()) {
			webview1.goBack();
		} else {
			exit++;
			if (exit == 2) {
				finishAffinity();
			} else {
				_customToast("Press back again to exit", "#03a9f4");
				timer = new TimerTask() {
					@Override
					public void run() {
						runOnUiThread(new Runnable() {
								@Override
								public void run() {
									exit = 0;
								}
							});
					}
				};
				_timer.schedule(timer, (int)(3000));
			}
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		finishAffinity();
	}
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        webview1.onActivityResult(requestCode, resultCode, intent);
    }
	
	@Override
	public void onPageStarted(String url, Bitmap favicon) {
		progressbar1.setVisibility(View.VISIBLE);
		isNetworkConnectionAvailable();
		if (!availableNetwork) {
			_customToast("You are not connected to the internet", "#f44336");
			webview1.setVisibility(View.GONE);
			imageview1.setVisibility(View.VISIBLE);
		}
		
	}

	@Override
	public void onPageFinished(String url) {
		progressbar1.setVisibility(View.GONE);
		if (availableNetwork && webview1.getVisibility() == View.GONE) {
			webview1.setVisibility(View.VISIBLE);
			imageview1.setVisibility(View.GONE);
		}
	}

	@Override
	public void onPageError(int errorCode, String description, String failingUrl) {
		if (progressbar1.getVisibility() == View.VISIBLE) {
			progressbar1.setVisibility(View.GONE);
		}
	}

	@Override
	public void onDownloadRequested(String url, String suggestedFilename, String mimeType, long contentLength, String contentDisposition, String userAgent) {
		final ProgressDialog downloading = ProgressDialog.show(this, "Please wait", "Downloading " + suggestedFilename + "...", true);
		downloading.show();
		DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
		String cookies = CookieManager.getInstance().getCookie(url);
		request.addRequestHeader("cookie", cookies);
		request.addRequestHeader("User-Agent", userAgent);
		request.allowScanningByMediaScanner();
		request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
		java.io.File aatv = new java.io.File(Environment.getExternalStorageDirectory().getPath() + "/TechHost App");
		request.setDestinationInExternalPublicDir("/TechHost App", suggestedFilename);
		DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
		manager.enqueue(request);
		BroadcastReceiver onComplete = new BroadcastReceiver() {
			public void onReceive(Context ctxt, Intent intent) {
				downloading.dismiss();
				unregisterReceiver(this);
		}};
		registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
	}

	@Override
	public void onExternalPageRequest(String url) {
		// Do nothing
	}
}
