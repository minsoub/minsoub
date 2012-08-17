package hnetwork.suwon.city;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MainActivity extends Activity  implements OnItemClickListener
{
    /** Called when the activity is first created. */
	private ArrayList<HashMap<String, Object>> mList = null;
	private MainActivity cls;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Title remove : before called setContentView method.
        requestWindowFeature(Window.FEATURE_NO_TITLE);	
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.mainlist); 
        cls = this;

        mList = getContentData();  
                
        ListView listview = (ListView)findViewById(R.id.listviews);
        
        CustomAdapter adapter = new CustomAdapter(
        		this,
        		mList,
        		R.layout.list, 
        		new String[]{"img"},
        		new int[]{R.id.imageIcon}
        		);
        
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(this);

        // bottom button add
        ImageView homeBtn = (ImageView)findViewById(R.id.btnhome);
        homeBtn.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v) {
        		Intent intent = new Intent(cls, SubMainActivity.class);
        		startActivity(intent);
        	}
        });
    }
    
	public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
		// TODO Auto-generated method stub
		HashMap<String, Object> info = mList.get((int)id);
		
		Log.i("Item Click", info.get("url").toString());
		
		Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(info.get("url").toString()));
		startActivity(i);
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
		
			
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View v = convertView;
			if (v == null)
			{
				//Log.i("getView", "View is null............");
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.list, null);
				
				if (v == null)
				{
					Log.e("getView", "View Creation fail");
				}else {
					
					ImageView btnView = (ImageView) v.findViewById(R.id.imageIcon);
	                //btnView.setOnClickListener(myBtn);
				}
			}			
			HashMap<String, Object> info = item.get(position);
						
            if (info != null) {
                ImageView imageView = (ImageView) v.findViewById(R.id.imageIcon);
                if (imageView != null) {
                	imageView.setImageResource(Integer.parseInt(info.get("img").toString()));
                }
            }
            //v.setTag(info.get("url").toString());

            return v;
		}

	}
	
    private ArrayList<HashMap<String, Object>> getContentData()
    {
    	ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
    	
        HashMap<String, Object> map1 = new HashMap<String, Object>();
        map1.put("img", R.drawable.banner1);
        map1.put("url", "https://yt.suwon.go.kr/");        
        list.add(map1);
        
        HashMap<String, Object> map2 = new HashMap<String, Object>();
        map2.put("img", R.drawable.banner2);
        map2.put("url", "http://www.iros.go.kr");        
        list.add(map2);
        
        HashMap<String, Object> map3 = new HashMap<String, Object>();
        map3.put("img", R.drawable.banner3);
        map3.put("url", "http://www.hometax.go.kr/");        
        list.add(map3);
        
        HashMap<String, Object> map4 = new HashMap<String, Object>();
        map4.put("img", R.drawable.banner4);
        map4.put("url", "http://www.minwon.go.kr/");        
        list.add(map4);
        
        HashMap<String, Object> map5 = new HashMap<String, Object>();
        map5.put("img", R.drawable.banner5);
        map5.put("url", "http://www.wetax.go.kr/");        
        list.add(map5);
        
        HashMap<String, Object> map6 = new HashMap<String, Object>();
        map6.put("img", R.drawable.banner6);
        map6.put("url", "http://rtms.mltm.go.kr/");        
        list.add(map6);
        
        HashMap<String, Object> map7 = new HashMap<String, Object>();
        map7.put("img", R.drawable.banner7);
        map7.put("url", "http://www.realtyprice.or.kr/");        
        list.add(map7);
        
        HashMap<String, Object> map8 = new HashMap<String, Object>();
        map8.put("img", R.drawable.banner8);
        map8.put("url", "http://www.eais.go.kr/");        
        list.add(map8);
        
        HashMap<String, Object> map9 = new HashMap<String, Object>();
        map9.put("img", R.drawable.banner9);
        map9.put("url", "http://klis.gg.go.kr ");        
        list.add(map9);
        
        HashMap<String, Object> map10 = new HashMap<String, Object>();
        map10.put("img", R.drawable.banner10);
        map10.put("url", "http://gris.gg.go.kr");        
        list.add(map10);
        
        HashMap<String, Object> map11 = new HashMap<String, Object>();
        map11.put("img", R.drawable.banner11);
        map11.put("url", "http://gris.gg.go.kr");        
        list.add(map11);
        
        return list;
    }
}
