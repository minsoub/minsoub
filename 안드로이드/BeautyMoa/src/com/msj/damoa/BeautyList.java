package com.msj.damoa;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

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
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class BeautyList extends ListActivity {
	private ArrayList<HashMap<String, Object>> mList = null;
	private ArrayList<OfficeInfo> oList = null;
	//private CustomAdapter adapter = null;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     
        Bundle data = getIntent().getExtras();
        String selectedData = data.getString("selectedData");
        
        // HashMap<String, Integer> iconList = getConstants();
        oList = getConstants(selectedData);
        
        System.out.println("oList : " + oList.size());
        
        mList = new ArrayList<HashMap<String, Object>>();
        
        for (int i=0; i<oList.size(); i++) {
        	HashMap<String, Object> item = new HashMap<String, Object>();
        	OfficeInfo info = (OfficeInfo)oList.get(i);

        	// 받은 URL 링크의 이미지를 가지고 이미지를 가져온다.
        	//Bitmap bm = LoadImage(info.getImage());
        	
        	item.put("col1", info.getImage());  // R.drawable.icon);
        	item.put("col2", "업체명 : " + info.getTitle());
        	item.put("col3", info.getDescritpion());
        	item.put("col4", "Tel : " + info.getTel()+" "+info.getAddress().replace("&nbsp;", " "));
        	item.put("col5", R.drawable.map_intro);
        	item.put("col6", info.getAddress().replace("&nbsp;", " "));
        	mList.add(item);
        	
        }
        // SimpleAdapter custom
        CustomAdapter adapter = new CustomAdapter(
        		this,
        		mList,
        		R.layout.beauty_list,
        		new String[] { "col1", "col2", "col3", "col4", "col5"},
        		new int[] {  R.id.subicon, R.id.title, R.id.content, R.id.address, R.id.icon2 }
        		);
        setListAdapter(adapter);               
    }
    
    /**
     * 아이템을 클릭했을 때 아이템의 주소를 가져와서 구글맵과 연동시킨다.
     */
    @Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		
		Log.i("Item click postion", ""+position);
		Log.i("Item click id", ""+id);
		HashMap<String, Object> info = mList.get((int)id);
		String addr = info.get("col6").toString();
		
		Log.i("Item Click", addr);
		
		Intent i = new Intent(this, MapInfo.class);
		i.putExtra("map_addr", addr);
		startActivity(i);

	}    
    
	private ArrayList<OfficeInfo> getConstants(String code) 
    {
    	
    	// 서버에서 XML 파일을 가져온다.
		//<item>
		//  <title><![CDATA[나비아야 놀자]]></title>
		//  <link><![CDATA[http://naver.com]]></link>
		//  <image><![CDATA[http://newkorea.dothome.co.kr/upload/10(0)(1).jpg]]></image>
		//  <tel><![CDATA[010-333-3411]]></tel>
		//  <address><![CDATA[인천&nbsp;부평구&nbsp;십정동&nbsp;182-95]]></address>
		//  <description><![CDATA[테스트입니다. ]]></description>
		//</item>
		ArrayList<OfficeInfo> list = new ArrayList<OfficeInfo>();
		try {
			String mediaUrl = "http://newkorea2.dothome.co.kr/android/feeds.asp?code="+code;

			URLConnection cn = new URL(mediaUrl).openConnection();   
	        cn.connect();   
	        InputStream stream = cn.getInputStream();
	        if (stream == null) {
	        	Log.e(getClass().getName(), "Unable to create InputStream for mediaUrl:" + mediaUrl);
	        }
	        	        
			XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
			
			XmlPullParser parser = parserCreator.newPullParser();
			
			parser.setInput(stream, null);
			
			int parserEvent = parser.getEventType();
			
			
			
			OfficeInfo info = null;	
			boolean item_tag  = false;
			
			while(parserEvent != XmlPullParser.END_DOCUMENT) {
				
				switch(parserEvent) {
				case XmlPullParser.START_TAG:
					String tag = parser.getName();					
					if (tag.compareTo("item") == 0) {
						item_tag = true;
					}else if(item_tag == true) {
						if (tag.compareTo("title") == 0) {
							//System.out.println("title : " + parser.nextText());
							info = new OfficeInfo();
							info.setTitle(parser.nextText());
						}else if(tag.compareTo("link") == 0) {
							//System.out.println("link : " + parser.nextText());
							info.setLink(parser.nextText());
						}else if(tag.compareTo("image") == 0) {
							//System.out.println("image : " + parser.nextText());
							info.setImage(parser.nextText());
						}else if(tag.compareTo("tel") == 0) {
							//System.out.println("tel : " + parser.nextText());
							info.setTel(parser.nextText());
						}else if(tag.compareTo("address") == 0) {
							//System.out.println("address : " + parser.nextText());
							info.setAddress(parser.nextText());
						}else if(tag.compareTo("description") == 0) {
							//System.out.println("description : " + parser.nextText());
							info.setDescritpion(parser.nextText());
							item_tag = false;
							
							OfficeInfo ts = new OfficeInfo();
							ts.setTitle(info.getTitle());
							ts.setTel(info.getTel());
							ts.setAddress(info.getAddress());
							ts.setDescritpion(info.getDescritpion());
							ts.setImage(info.getImage());
							ts.setLink(info.getLink());
							
							//Log.i("title : ", info.getTitle());
							
							list.add(ts);
						}
					}					
					break;
					
				//case XmlPullParser.TEXT:
						
				//	break;
				//case XmlPullParser.END_TAG:
						
				//	break;
				}
				parserEvent = parser.next();
			}
			
		} catch(XmlPullParserException ex) {
			ex.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return list;
    }
		
	private class CustomAdapter extends SimpleAdapter {

		private ArrayList<HashMap<String, Object>> item;
		private Context context;
		public CustomAdapter(Context context, ArrayList<HashMap<String, Object>> data,
				int resource, String[] from, int[] to) {
			super(context, data, resource, from, to);
			// TODO Auto-generated constructor stub
			this.item = data;
			this.context = context;
		}
		final OnClickListener myBtn = new OnClickListener() {
			@Override
			public void onClick(View v) {
				//int position = (Integer) v.getTag();
				// do something!
				
			}
		};

			
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View v = convertView;
			if (v == null)
			{
				Log.i("getView", "View is null............");
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.beauty_list, null);
				
				if (v == null)
				{
					Log.e("getView", "View Creation fail");
				}else {
					
					ImageView btnView = (ImageView) v.findViewById(R.id.icon2);
					// btnView.setImageResource(Integer.parseInt(info.get("col5").toString()));
	                btnView.setOnClickListener(myBtn);
				}
			}			
			HashMap<String, Object> info = item.get(position);
						
            if (info != null) {
                ImageView imageView = (ImageView) v.findViewById(R.id.subicon);
                if (imageView != null) {
                	Bitmap bm = LoadImage(info.get("col1").toString());	
                    //iv.setImageDrawable(bm);  // it.getImage());
                	imageView.setImageBitmap(bm);
                }
                imageView.setAdjustViewBounds(true);
				imageView.setScaleType(ImageView.ScaleType.FIT_XY);
				
				// title
				TextView titleView = (TextView)v.findViewById(R.id.title);
				titleView.setText(info.get("col2").toString());
				
				// Content
				TextView contentView = (TextView)v.findViewById(R.id.content);
				contentView.setText(info.get("col3").toString());
				
				// address
				TextView priceView = (TextView)v.findViewById(R.id.address);
				priceView.setText(info.get("col4").toString());
				
				ImageView btnView = (ImageView) v.findViewById(R.id.icon2);
				btnView.setImageResource(Integer.parseInt(info.get("col5").toString()));
            }

            return v;

            
			//return super.getView(position, convertView, parent);
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
