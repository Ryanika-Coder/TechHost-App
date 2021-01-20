package com.tech.host;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.*;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.appbar.AppBarLayout;
import android.app.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.content.*;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.webkit.*;
import android.animation.*;
import android.view.animation.*;
import java.util.*;
import java.util.regex.*;
import java.text.*;
import org.json.*;
import android.widget.LinearLayout;
import android.widget.ImageView;
import android.webkit.WebView;
import android.webkit.WebSettings;
import java.util.Timer;
import java.util.TimerTask;
import android.content.Intent;
import android.content.ClipData;
import android.net.Uri;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.webkit.WebViewClient;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;
import androidx.core.content.ContextCompat;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.pm.PackageManager;


public class MainpremiumActivity extends  AppCompatActivity  { 
	
	public final int REQ_CD_FP = 101;
	private Timer _timer = new Timer();
	
	private Toolbar _toolbar;
	private AppBarLayout _app_bar;
	private CoordinatorLayout _coordinator;
	private double exit = 0;
	private boolean availableNetwork = false;
	
	private LinearLayout linear1;
	private ImageView imageview1;
	private WebView webview1;
	private WebView webview2;
	
	private TimerTask timer;
	private Intent fp = new Intent(Intent.ACTION_GET_CONTENT);
	private Intent intent = new Intent();
	private AlertDialog.Builder dialog;
	private AlertDialog.Builder dialog2;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.mainpremium);
		initialize(_savedInstanceState);
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
	
	private void initialize(Bundle _savedInstanceState) {
		
		_app_bar = (AppBarLayout) findViewById(R.id._app_bar);
		_coordinator = (CoordinatorLayout) findViewById(R.id._coordinator);
		_toolbar = (Toolbar) findViewById(R.id._toolbar);
		setSupportActionBar(_toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _v) {
				onBackPressed();
			}
		});
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		imageview1 = (ImageView) findViewById(R.id.imageview1);
		webview1 = (WebView) findViewById(R.id.webview1);
		webview1.getSettings().setJavaScriptEnabled(true);
		webview1.getSettings().setSupportZoom(true);
		webview2 = (WebView) findViewById(R.id.webview2);
		webview2.getSettings().setJavaScriptEnabled(true);
		webview2.getSettings().setSupportZoom(true);
		fp.setType("*/*");
		fp.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
		dialog = new AlertDialog.Builder(this);
		dialog2 = new AlertDialog.Builder(this);
		
		webview1.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageStarted(WebView _param1, String _param2, Bitmap _param3) {
				final String _url = _param2;
				isNetworkConnectionAvailable();
				if (!availableNetwork) {
					_customToast("You are not connected to the internet", "#f44336");
					webview1.setVisibility(View.GONE);
					imageview1.setVisibility(View.VISIBLE);
				}
				super.onPageStarted(_param1, _param2, _param3);
			}
			
			@Override
			public void onPageFinished(WebView _param1, String _param2) {
				final String _url = _param2;
				if (availableNetwork && webview1.getVisibility() == View.GONE) {
					webview1.setVisibility(View.VISIBLE);
					imageview1.setVisibility(View.GONE);
				}
				super.onPageFinished(_param1, _param2);
			}
		});
		
		webview2.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageStarted(WebView _param1, String _param2, Bitmap _param3) {
				final String _url = _param2;
				
				super.onPageStarted(_param1, _param2, _param3);
			}
			
			@Override
			public void onPageFinished(WebView _param1, String _param2) {
				final String _url = _param2;
				
				super.onPageFinished(_param1, _param2);
			}
		});
	}
	
	private void initializeLogic() {
		setTitle("TechHost App (Premium)");
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			getWindow().setNavigationBarColor(Color.parseColor("#008dcd"));
		}
		
		webview1.setBackgroundColor(Color.parseColor("#ff171731"));
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		webview1.getSettings().setPluginState(WebSettings.PluginState.ON);
		webview1.getSettings().setDomStorageEnabled(true); webview1.getSettings().setDatabaseEnabled(true); webview1.getSettings().setAppCacheEnabled(true);
		webview1.setWebChromeClient(new WebChromeClient() {
			
			protected void openFileChooser(ValueCallback uploadMsg, String acceptType) {
				mUploadMessage = uploadMsg; Intent i = new Intent(Intent.ACTION_GET_CONTENT); i.addCategory(Intent.CATEGORY_OPENABLE);
				i.setType("image/*"); startActivityForResult(Intent.createChooser(i, "File Browser"), FILECHOOSER_RESULTCODE);
			}
			
			// For Lollipop 5.0+ Devices
			public boolean onShowFileChooser(WebView mWebView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
				if (uploadMessage != null) {
					uploadMessage.onReceiveValue(null);
					uploadMessage = null; } uploadMessage = filePathCallback; Intent intent = fileChooserParams.createIntent(); try {
					startActivityForResult(intent, REQUEST_SELECT_FILE);
				} catch (ActivityNotFoundException e) {
					uploadMessage = null; Toast.makeText(getApplicationContext(), "Cannot Open File Chooser", Toast.LENGTH_LONG).show(); return false; }
				return true; }
			
			//For Android 4.1 only
			protected void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
				mUploadMessage = uploadMsg; Intent intent = new Intent(Intent.ACTION_GET_CONTENT); intent.addCategory(Intent.CATEGORY_OPENABLE); intent.setType("image/*"); startActivityForResult(Intent.createChooser(intent, "File Browser"), FILECHOOSER_RESULTCODE);
			}
			
			protected void openFileChooser(ValueCallback<Uri> uploadMsg) {
				mUploadMessage = uploadMsg; Intent i = new Intent(Intent.ACTION_GET_CONTENT); i.addCategory(Intent.CATEGORY_OPENABLE);
				i.setType("image/*"); startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE);
			}
		});
		webview1.loadUrl("https://premium.techhost.cc");
		webview2.loadUrl("https://support.techhost.cc");
		webview2.setVisibility(View.GONE);
		imageview1.setVisibility(View.GONE);
		webview1.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
		
		webview1.setDownloadListener(new DownloadListener() {
				public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
						ProgressDialog downloading = ProgressDialog.show(MainpremiumActivity.this, "Please wait", "Downloading " + URLUtil.guessFileName(url, contentDisposition, mimetype) + "...", true);
						DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
						String cookies = CookieManager.getInstance().getCookie(url);
						request.addRequestHeader("cookie", cookies);
						request.addRequestHeader("User-Agent", userAgent);
						request.setDescription("Please wait...");
						request.setTitle(URLUtil.guessFileName(url, contentDisposition, mimetype));
						request.allowScanningByMediaScanner(); request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
						java.io.File aatv = new java.io.File(Environment.getExternalStorageDirectory().getPath() + "/TechHost App");
						if(!aatv.exists()){
								if (!aatv.mkdirs()){
										_customToast("Failed to download the file", "#f44336");
										return;
								}
						}
						request.setDestinationInExternalPublicDir("/TechHost App", URLUtil.guessFileName(url, contentDisposition, mimetype));
						DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
						manager.enqueue(request);
						downloading.show();
						//Notif if success
						BroadcastReceiver onComplete = new BroadcastReceiver() {
								public void onReceive(Context ctxt, Intent intent) {
										downloading.dismiss();
										unregisterReceiver(this);
								}};
						registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
				}
		});
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			case REQ_CD_FP:
			if (_resultCode == Activity.RESULT_OK) {
				ArrayList<String> _filePath = new ArrayList<>();
				if (_data != null) {
					if (_data.getClipData() != null) {
						for (int _index = 0; _index < _data.getClipData().getItemCount(); _index++) {
							ClipData.Item _item = _data.getClipData().getItemAt(_index);
							_filePath.add(FileUtil.convertUriToFilePath(getApplicationContext(), _item.getUri()));
						}
					}
					else {
						_filePath.add(FileUtil.convertUriToFilePath(getApplicationContext(), _data.getData()));
					}
				}
			}
			break;
			
			case REQUEST_SELECT_FILE:
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
				if (uploadMessage == null) return; uploadMessage.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(_resultCode, _data)); uploadMessage = null; }
			break;
			
			case FILECHOOSER_RESULTCODE:
			if (null == mUploadMessage){
				return; }
			Uri result = _data == null || _resultCode != RESULT_OK ? null : _data.getData(); mUploadMessage.onReceiveValue(result);
			mUploadMessage = null;
			
			if (true){
			}
			else {
				
			}
			break;
			default:
			break;
		}
	}
	
	@Override
	public void onBackPressed() {
		if (webview1.canGoBack()) {
			webview1.goBack();
		}
		else {
			exit++;
			if (exit == 2) {
				finishAffinity();
			}
			else {
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
	public void onDestroy() {
		super.onDestroy();
		finish();
	}
	public void _useless () {
	}
	private ValueCallback<Uri> mUploadMessage;
	public ValueCallback<Uri[]> uploadMessage;
	public static final int REQUEST_SELECT_FILE = 100;
	private final static int FILECHOOSER_RESULTCODE = 1;
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 0, 0,"Refresh").setIcon(R.drawable.refresh).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		
		menu.add(0, 1, 1, "Switch To Basic").setIcon(R.drawable.change).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		
		menu.add(0, 2, 2, "Delete Cache").setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
		
		menu.add(0, 3, 3, "About").setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
		
		menu.add(0, 4, 4, "Privacy Policy").setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case 0:
			webview1.reload();
			webview2.loadUrl("https://support.techhost.cc");
			break;
			case 1 :
			dialog2.setMessage("Are you sure you want to switch to the basic panel ?");
			dialog2.setPositiveButton("YES", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface _dialog, int _which) {
					intent.setClass(getApplicationContext(), MainbasicActivity.class);
					startActivity(intent);
				}
			});
			dialog2.setNegativeButton("NO", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface _dialog, int _which) {
					
				}
			});
			dialog2.create().show();
			break;
			case 2 :
			webview1.clearCache(true);
			webview2.clearCache(true);
			webview1.clearHistory();
			webview2.clearHistory();
			_customToast("Cache deleted", "#43a047");
			break;
			case 3 :
			intent.setClass(getApplicationContext(), AboutActivity.class);
			startActivity(intent);
			break;
			case 4 :
			dialog.setTitle("PRIVACY POLICY");
			dialog.setMessage("● The app doesn't collect any user personal data as, for example, name, picture or location.\n\n● Consequently, the app doesn't share any personal information with any other entity or third parties.\n\n● We allow third-party companies to serve ads and collect certain anonymous information when you visit our app. These companies may use anonymous information such as your Google Advertising ID, your device type and version, browsing activity, location and other technical data relating to your device, in order to provide advertisements.");
			dialog.setPositiveButton("CLOSE", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface _dialog, int _which) {
					
				}
			});
			dialog.create().show();
			break;
		}
		return super.onOptionsItemSelected(item);
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
	
	
	public void _customToast (final String _text, final String _color) {
		LayoutInflater inflater = getLayoutInflater();
		View toastLayout = inflater.inflate(R.layout.toast, null);
		TextView textview1 = (TextView) toastLayout.findViewById(R.id.textview1);
		LinearLayout linear1 = (LinearLayout) toastLayout.findViewById(R.id.linear1);
		
		android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
		gd.setColor(Color.parseColor(_color));
		gd.setCornerRadius(60);
		gd.setStroke(2, Color.parseColor("#ffffff"));
		linear1.setBackground(gd);
		
		textview1.setText(_text);
		Toast toast = new Toast(getApplicationContext());
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(toastLayout);
		toast.show();
	}
	
	
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}
	
	@Deprecated
	public int getLocationX(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[0];
	}
	
	@Deprecated
	public int getLocationY(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[1];
	}
	
	@Deprecated
	public int getRandom(int _min, int _max) {
		Random random = new Random();
		return random.nextInt(_max - _min + 1) + _min;
	}
	
	@Deprecated
	public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
		ArrayList<Double> _result = new ArrayList<Double>();
		SparseBooleanArray _arr = _list.getCheckedItemPositions();
		for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
			if (_arr.valueAt(_iIdx))
			_result.add((double)_arr.keyAt(_iIdx));
		}
		return _result;
	}
	
	@Deprecated
	public float getDip(int _input){
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
	}
	
	@Deprecated
	public int getDisplayWidthPixels(){
		return getResources().getDisplayMetrics().widthPixels;
	}
	
	@Deprecated
	public int getDisplayHeightPixels(){
		return getResources().getDisplayMetrics().heightPixels;
	}
	
}