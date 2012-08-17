package com.msj.xml;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.util.Log;

import com.msj.damoa.ListInfo;

/**
 * 	<item>
 * 	 <code><![CDATA[K001]]></code>
 * 	 <title><![CDATA[�Ǻ伥]]></title>
 * 	 <image><![CDATA[http://newkorea2.dothome.co.kr/android/image/k001.jpg]]></image>
 * 	 <map><![CDATA[Y]]></map>
 * 	</item>
 * ArrayList<HashMap<String,Object>>
 * @return
 */
public class XmlMainRead {
	private String serverURL = "http://newkorea2.dothome.co.kr/android/service_list.asp";
	
	public ArrayList<ListInfo> getUrlXmlData() 
    {
    	
		ArrayList<ListInfo> list = new ArrayList<ListInfo>();
		
		try {

			URLConnection cn = new URL(serverURL).openConnection();   
	        cn.connect();   
	        InputStream stream = cn.getInputStream();
	        if (stream == null) {
	        	Log.e(getClass().getName(), "Unable to create InputStream for mediaUrl:" + serverURL);
	        }
	        	        
			XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
			
			XmlPullParser parser = parserCreator.newPullParser();
			
			parser.setInput(stream, null);
			
			int parserEvent = parser.getEventType();
			
			
			
			ListInfo info = null;	
			boolean item_tag  = false;
			
			while(parserEvent != XmlPullParser.END_DOCUMENT) {
				
				switch(parserEvent) {
				case XmlPullParser.START_TAG:
					String tag = parser.getName();					
					if (tag.compareTo("item") == 0) {
						item_tag = true;
					}else if(item_tag == true) {
						if (tag.compareTo("code") == 0) {
							//System.out.println("code : " + parser.nextText());
							info = new ListInfo();
							info.setCode(parser.nextText());
						}else if(tag.compareTo("title") == 0) {
							//System.out.println("title : " + parser.nextText());
							info.setTitle(parser.nextText());
						}else if(tag.compareTo("image") == 0) {
							//System.out.println("image : " + parser.nextText());
							info.setImage(parser.nextText());
						}else if(tag.compareTo("map") == 0) {
							//System.out.println("map : " + parser.nextText());
							info.setMap(parser.nextText());
							item_tag = false;
							
							ListInfo lf = new ListInfo();
							lf.setTitle(info.getTitle());
							lf.setCode(info.getCode());
							lf.setImage(info.getImage());
							lf.setMap(info.getMap());
							
							list.add(lf);
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
}
