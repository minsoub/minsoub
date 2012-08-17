package my.fir.android;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import android.app.ListActivity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ShopMain extends ListActivity {

	private ArrayList<Items> items = null;
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.main);
		
		items = new ArrayList<Items>();
		
		// Items 등록
		addUrlItems();
				
	}
	public void updateList(String imgUrl, String title, String content, String price)
	{
		String[] imgUrls  = imgUrl.split(",");
		String[] titles   = title.split(",");
		String[] contents = content.split(",");
		String[] prices   = price.split(",");
		
		for (int i=0; i<imgUrls.length; i++)
		{
			Items item = new Items();
		
			item.setImgURL(imgUrls[i]);
			item.setTitle(titles[i]);
			item.setContent(contents[i]);
			item.setPrice(prices[i]);
			
			items.add(item);
		}
		
		
	}
	private void addUrlItems()
	{
		// 서버에 접속해서 XML 데이터를 읽어 온다.
		URL url = null;
		try
		{
			url = new URL("http://inx.co.kr/xml_read.asp");
			
			SAXParserFactory parserModel = SAXParserFactory.newInstance();
			SAXParser parser = parserModel.newSAXParser();
			XMLReader reader = parser.getXMLReader();
			
			MyXmlHandler myXml = new MyXmlHandler(this);
			reader.setContentHandler(myXml);
			reader.setErrorHandler(myXml);
			
			reader.parse(new InputSource(url.openStream()));						
		}
		catch(Exception ex)
		{
			Log.v("Error : ", ex.toString());
			ex.printStackTrace();
		}	
		
		ItemsAdapter adapter = new ItemsAdapter(this, R.layout.row, items);		
		setListAdapter(adapter);
	}
	
	private class MyXmlHandler extends DefaultHandler
	{
		private StringBuffer imgURL = new StringBuffer();
		private StringBuffer title  = new StringBuffer();
		private StringBuffer content = new StringBuffer();
		private StringBuffer price  = new StringBuffer();
		private StringBuffer keyNo  = new StringBuffer();
		
		private boolean hasImage = false;
		private boolean hasTitle = false;
		private boolean hasContent = false;
		private boolean hasPrice = false;
		private boolean hasKey   = false;
		
		private ShopMain entity;
		
		public MyXmlHandler(ShopMain entity)
		{
			this.entity = entity;
		}
		
		public void startElement(String uri, String localName, String qName, Attributes atts)
		{
			if (localName.equals("imgURL"))
			{
				hasImage = true;
			}
			else if(localName.equals("title"))
			{
				hasTitle = true;
			}
			else if(localName.equals("content"))
			{
				hasContent = true;
			}
			else if(localName.equals("price"))
			{
				hasPrice = true;
			}
			else if(localName.equals("keyNo"))
			{
				hasKey = true;
			}
		}
		
		public void endElement(String uri, String localName, String qName)
		{
			entity.updateList(imgURL.toString(), title.toString(), content.toString(), price.toString());
		}
		
		public void characters(char[] chars, int start, int leng)
		{
			if (hasImage)
			{
				hasImage = false;
				imgURL.append(chars, start, leng);
			}
			else if(hasTitle)
			{
				hasTitle = false;
				title.append(chars, start, leng);
			}
			else if(hasContent)
			{
				hasContent = false;
				content.append(chars, start, leng);
			}
			else if(hasPrice)
			{
				hasPrice = false;
				price.append(chars, start, leng);
			}
			else if(hasKey)
			{
				hasKey = false;
				keyNo.append(chars, start, leng);
			}
		}
	}
	
	private class ItemsAdapter extends ArrayAdapter<Items>
	{
		private ArrayList<Items> items;
		
		public ItemsAdapter(Context context, int textViewResourceId, ArrayList<Items> items)
		{
			super(context, textViewResourceId, items);
			this.items = items;
		}

		/* (non-Javadoc)
		 * @see android.widget.ArrayAdapter#getView(int, android.view.View, android.view.ViewGroup)
		 */
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View v = convertView;
			
			if (v == null)
			{
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.row, null);
			}
			Items p = items.get(position);
			
			if (p != null)
			{
				
				ImageView imageView = (ImageView)findViewById(R.id.icon);
				
				URL imageURL = null;
				URLConnection conn = null;
				InputStream is = null;
				
				try
				{
					imageURL = new URL(p.getImgURL()); // "http://inx.co.kr/upload/logowhite.gif");
					conn = imageURL.openConnection();
					
					conn.connect();
					
					is = conn.getInputStream();
					
					BufferedInputStream bis = new BufferedInputStream(is);
					Bitmap bitMap = BitmapFactory.decodeStream(bis);
					bis.close();
					is.close();
					imageView.setImageBitmap(bitMap);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				
				imageView.setAdjustViewBounds(true);
				imageView.setScaleType(ImageView.ScaleType.FIT_XY);
				
				// title
				Log.v("title=", p.getTitle());
				TextView titleView = (TextView)findViewById(R.id.title);
				titleView.setText(p.getTitle());
				
				// Content
				TextView contentView = (TextView)findViewById(R.id.content);
				contentView.setText(p.getContent());
				
				// price
				TextView priceView = (TextView)findViewById(R.id.price);
				priceView.setText(p.getPrice()+"원");
			}
			return v;
		}				
	}
}
