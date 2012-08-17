package my.fir.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MyActivity extends Activity {

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.myactivity);
		//Log.d("MyActivity", "text program create");
		
		Button button = (Button) findViewById(R.id.button);
		button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				TextView t = (TextView)findViewById(R.id.text);
				t.setText("Hello Pgm Clicked");
				t.setBackgroundColor(0xFFFF0000);
				t.setGravity(Gravity.LEFT);
				
				finish();
			}
		});
	}
	
}
