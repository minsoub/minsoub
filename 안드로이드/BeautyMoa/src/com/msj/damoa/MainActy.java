package com.msj.damoa;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Map.Entry;

import com.msj.xml.XmlMainRead;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class MainActy extends ListActivity {
	private ArrayList<HashMap<String, Object>> mList = null;
	private ArrayList<ListInfo> mainList = null;
	private XmlMainRead xmlData = null;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState);
        
        //requestWindowFeature(Window.FEATURE_LEFT_ICON);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        
        
        //setContentView(R.layout.main);
        xmlData  = new XmlMainRead();
        mainList = xmlData.getUrlXmlData();
        
        Log.i("MainActy", "Get Data : " + mainList.size());
       // HashMap<String, Integer> iconList = getConstants();
        
        
        mList = new ArrayList<HashMap<String, Object>>();
        
        for (int i=0; i<mainList.size(); i++) {
        	ListInfo info = (ListInfo)mainList.get(i);
        	
        	HashMap<String, Object> item = new HashMap<String, Object>();
        	item.put("col1", info.getImage());
        	item.put("col2", info.getTitle());
        	item.put("col3", info.getTitle());
        	item.put("col4", info.getCode());
        	mList.add(item);
        }
        
        //ListView lv = getListView();        
        //View header = getLayoutInflater().inflate(R.layout.title_bar, null, false);
        //lv.addHeaderView(header);
        
        CustomAdapter adapter = new CustomAdapter(
        		this,
        		mList,
        		R.layout.main,
        		new String[] { "col1", "col2", "col3"},
        		new int[] { R.id.main_icon, R.id.main_title, R.id.main_content }
        		);       

        setListAdapter(adapter);
        
        // ProgressBar
        //setProgress(5000);
        //setProgressBarVisibility(true); 
        
        //setFeatureDrawableResource(Window.FEATURE_LEFT_ICON, R.drawable.logo);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_bar);
        
        
        
    } 
    
    @Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		
		HashMap<String, Object> info = mList.get((int)id);
		String code = info.get("col4").toString();
		
		Log.i("Item Click", code);
		
		Iterator it = info.entrySet().iterator();
		if (it.hasNext()) {
			Entry entry = (Entry)it.next();
			String data = (String)entry.getKey();
			
			Intent i = new Intent(this, BeautyList.class);
			i.putExtra("selectedData", code);
			startActivity(i);
		}
	}

	/**
	 * 	<item>
	 * 	 <code><![CDATA[K001]]></code>
	 * 	 <title><![CDATA[피뷰샵]]></title>
	 * 	 <image><![CDATA[http://newkorea2.dothome.co.kr/android/image/k001.jpg]]></image>
	 * 	 <map><![CDATA[Y]]></map>
	 * 	</item>
	 * ArrayList<HashMap<String,Object>>
	 * @return
	 */
	private ArrayList<HashMap<String, Object>> getMainData()
	{
		ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
		
		HashMap<String, Object> item = new HashMap<String, Object>();
		
		return data;
	}
	
	private class CustomAdapter extends SimpleAdapter {

		private ArrayList<HashMap<String, Object>> item;
		
		public CustomAdapter(Context context, ArrayList<HashMap<String, Object>> data,
				int resource, String[] from, int[] to) {
			super(context, data, resource, from, to);
			// TODO Auto-generated constructor stub
			this.item = data;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View v = convertView;
			if (v == null)
			{
				Log.i("getView", "View is null............");
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.main, null);
				
				if (v == null)
				{
					Log.e("getView", "View Creation fail");
				}
			}		
			// 현재 포지션의 아이템을 얻는다.
			HashMap<String, Object> info = item.get(position);
						
            if (info != null) {
                ImageView imageView = (ImageView) v.findViewById(R.id.main_icon);
                if (imageView != null) {
                	Bitmap bm = LoadImage(info.get("col1").toString());	
                	imageView.setImageBitmap(bm);
                }
                imageView.setAdjustViewBounds(true);
				imageView.setScaleType(ImageView.ScaleType.FIT_XY);
				
				// title
				TextView titleView = (TextView)v.findViewById(R.id.main_title);
				titleView.setText(info.get("col2").toString());
				
				// Content
				TextView contentView = (TextView)v.findViewById(R.id.main_content);
				contentView.setText(info.get("col3").toString());                
            }

            return v;
		}
		
		private Bitmap LoadImage(String imagePath)
		{
			InputStream inputStream = OpenHttpConnection(imagePath);
			Bitmap bm = BitmapFactory.decodeStream(inputStream);
			
			return bm;
		}
		private InputStream OpenHttpConnection(String imagePath)
		{
			InputStream stream = null;
			try {
				URL url = new URL(imagePath);
				HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
				
				urlConnection.setRequestMethod("GET");
				urlConnection.connect();
				if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
					stream = urlConnection.getInputStream();
				}
			}catch(MalformedURLException e) {
				e.printStackTrace();
			}catch(IOException e) {
				e.printStackTrace();
			}
			return stream;
		}		

	}		
}