package my.fir.android;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class ShopList extends Activity {

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.shoplist);
		
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
		
	}
	
	public void updateList(String imgUrl, String title, String content, String price)
	{
		String[] imgUrls  = imgUrl.split(",");
		String[] titles   = title.split(",");
		String[] contents = content.split(",");
		String[] prices   = price.split(",");
		
		
		ImageView imageView = (ImageView)findViewById(R.id.icon);
		
		URL imageURL = null;
		URLConnection conn = null;
		InputStream is = null;
		
		try
		{
			imageURL = new URL(imgUrls[0]); // "http://inx.co.kr/upload/logowhite.gif");
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
		Log.v("title=", titles[0]);
		TextView titleView = (TextView)findViewById(R.id.title);
		titleView.setText(titles[0]);
		
		// Content
		TextView contentView = (TextView)findViewById(R.id.content);
		contentView.setText(contents[0]);
		
		// price
		TextView priceView = (TextView)findViewById(R.id.price);
		priceView.setText(prices[0]+"원");
		
		// imageView 클릭 이벤트 처리
		imageView.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0)
			{
				// 다른 Activity로 이동한다.
				Intent intent = new Intent(ShopList.this, MyActivity.class);
				//intent.putExtra("variable_name", variable value);
				startActivity(intent);
				
			}
		});
	}
	
}

class MyXmlHandler extends DefaultHandler
{
	private StringBuffer imgURL = new StringBuffer();
	private StringBuffer title  = new StringBuffer();
	private StringBuffer content = new StringBuffer();
	private StringBuffer price  = new StringBuffer();
	
	private boolean hasImage = false;
	private boolean hasTitle = false;
	private boolean hasContent = false;
	private boolean hasPrice = false;
	
	private ShopList entity;
	
	public MyXmlHandler(ShopList entity)
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
	}
}
