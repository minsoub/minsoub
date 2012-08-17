package hnetwork.android;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class AndListActivity extends ListActivity {

	private AndDbAdapter dbHelper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		//setContentView(R.layout.list);
		
		dbHelper = new AndDbAdapter(this);
        dbHelper.open();
        
		// Get all of the notes from the database and create the item
		Cursor c = dbHelper.fetchAllData();
		
		// This method allows the activity to take care of managing 
		// the given Cursor's lifecycle for you based on the activity's lifecycle
		startManagingCursor(c);
		
		String[] from = new String[] { "_id", "name", "passwd" };
		int[] to = new int[] { R.id.no, R.id.name, R.id.passwd };
		
		//for (int i=0; i<c.getCount(); i++) {
		//	
		//}
		
		SimpleCursorAdapter notes = 
			new SimpleCursorAdapter(this, R.layout.list, c, from, to);
		
		ListView lv = getListView();
		LayoutInflater inflater = getLayoutInflater(); 
		ViewGroup header = (ViewGroup)inflater.inflate(R.layout.header, lv, false); 
		lv.addHeaderView(header, null, false); 
		
		((TextView)findViewById(R.id.header_no)).setText("순번");
		((TextView)findViewById(R.id.header_name)).setText("성명");
		((TextView)findViewById(R.id.header_passwd)).setText("패스워드");
		
		setListAdapter(notes);

		
	}

	/**
	 * ListView가 클릭되었을 때 수행된다.
	 */
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		
		//String s = l.get
		Object o = this.getListAdapter().getItem(position);     
		Cursor c = (Cursor) o;
		
		String keyword = c.getString(0);
		
		Log.d("AndListActivity", keyword);
		
		Intent i = new Intent(this, AndInstActivity.class);
		i.putExtra("key", keyword);
		startActivity(i);
	}


}
