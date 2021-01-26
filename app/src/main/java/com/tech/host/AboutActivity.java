package com.tech.host;

import android.os.*;
import android.graphics.*;
import android.widget.*;
import java.util.*;
import android.view.*;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toolbar.LayoutParams;

public class AboutActivity extends AppCompatActivity {
	private Toolbar _toolbar;
	private TextView textview1;
	private ListView listview1;
	private ArrayList<HashMap<String, Object>> list = new ArrayList<>();
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_activity);
		initialize(savedInstanceState);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			getWindow().setNavigationBarColor(Color.parseColor("#008DCD"));
		}
		android.content.pm.PackageManager pm = getPackageManager();
		try {
			android.content.pm.PackageInfo pInfo = pm.getPackageInfo(getPackageName(), 0);
			String versionName = pInfo.versionName;
			int versionCode = pInfo.versionCode;
			textview1.setText(versionName + " (" + versionCode + ")");
		} catch (android.content.pm.PackageManager.NameNotFoundException e) {}
		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("list", getResources().getString(R.string.list1));
			list.add(_item);
		}

		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("list", getResources().getString(R.string.list2));
			list.add(_item);
		}

		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("list", getResources().getString(R.string.list3));
			list.add(_item);
		}

		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("list", getResources().getString(R.string.list4));
			list.add(_item);
		}

		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("list", getResources().getString(R.string.list5));
			list.add(_item);
		}
		{
			HashMap<String, Object> _item = new HashMap<>();
			_item.put("list", getResources().getString(R.string.list6));
			list.add(_item);
		}
		listview1.setAdapter(new Listview2Adapter(list));
		((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
    }
	
	private void initialize(Bundle savedInstanceState) {
		_toolbar = (Toolbar) findViewById(R.id.toolbar);
		textview1 = (TextView) findViewById(R.id.textview1);
		listview1 = (ListView) findViewById(R.id.listview1);
		setSupportActionBar(_toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _v) {
				onBackPressed();
			}
		});
	}
	
	@Override
	public void onBackPressed() {
		finish();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		finish();
	}
	
	public class Listview2Adapter extends BaseAdapter {
		ArrayList<HashMap<String, Object>> _data;
		public Listview2Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}

		@Override
		public int getCount() {
			return _data.size();
		}

		@Override
		public HashMap<String, Object> getItem(int _index) {
			return _data.get(_index);
		}

		@Override
		public long getItemId(int _index) {
			return _index;
		}
		@Override
		public View getView(final int _position, View _v, ViewGroup _container) {
			LayoutInflater _inflater = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View _view = _v;
			if (_view == null) {
				_view = _inflater.inflate(R.layout.list, null);
			}

			final LinearLayout linear1 = (LinearLayout) _view.findViewById(R.id.linear1);
			final TextView textview1 = (TextView) _view.findViewById(R.id.textview1);

			textview1.setText(list.get((int)_position).get("list").toString());

			return _view;
		}
	}
}
