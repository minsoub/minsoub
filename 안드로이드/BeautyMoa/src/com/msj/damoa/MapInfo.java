package com.msj.damoa;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;


// ������ ����(MD5): 28:17:1F:9F:E9:05:CC:E7:9E:97:DD:12:DB:6B:3D:26
// ����� �������� �ٸ� ��ǻ�Ϳ� �����ϸ� ����  APIŰ�� ����� �� �ִ�.
// keytool -list -keystore debug.keystore
public class MapInfo extends MapActivity {
	MapView map;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		
		Bundle data = getIntent().getExtras();
	    String addr = data.getString("map_addr");
	        
	    double[] lat_lon = getLocationLatLon(addr);
	    
	    Log.i("Lat", ""+lat_lon[0]);
	    Log.i("Lon", ""+lat_lon[1]);
	    
		setContentView(R.layout.map_info);
		
		map = (MapView)findViewById(R.id.maps);
		map.setSatellite(true);		// ��������
		
		GeoPoint point = new GeoPoint((int)(lat_lon[0] * 1E6), (int)(lat_lon[1] * 1E6));
		
		// �̹��� �� �߰�
		MapView.LayoutParams mapMarkerParams = new MapView.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT,
				point,
				MapView.LayoutParams.TOP_LEFT);
		
		ImageView mapMarker = new ImageView(getApplicationContext());
		mapMarker.setImageResource(R.drawable.pin_spot);
		map.addView(mapMarker, mapMarkerParams);
		
		MapController mc = map.getController();
		mc.animateTo(point);
		mc.setZoom(17);
		
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	
	private double[] getLocationLatLon(String addr)
	{
		Geocoder corder = new Geocoder(this);
		double[] return_data = new double[2];
		try {
			List<Address> gecodeResults = corder.getFromLocationName(addr, 1);
			Iterator<Address> locations = gecodeResults.iterator();
			
			while(locations.hasNext()) {
				Address loc = locations.next();
				return_data[0] = loc.getLatitude();
				return_data[1] = loc.getLongitude();
			}
			
		}catch(IOException e) {
			Log.e("GeoAddress", "Failed to get location info", e);
		}
		
		return return_data;
	}

}
