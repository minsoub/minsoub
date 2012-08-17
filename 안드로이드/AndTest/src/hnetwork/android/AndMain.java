package hnetwork.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AndMain extends Activity implements OnClickListener {

	Button btnList;
	Button btnInst;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.mainlayout);
		
		btnList = (Button) findViewById(R.id.btn_list);
		btnInst = (Button) findViewById(R.id.btn_insert);
		
		btnList.setOnClickListener(this);
		btnInst.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()) {
		case R.id.btn_list:
			Intent i = new Intent(this, AndListActivity.class);
			startActivity(i);
			break;
		case R.id.btn_insert:
			Intent j = new Intent(this, AndInstActivity.class);
			startActivity(j);
			break;
		}
	}

}
