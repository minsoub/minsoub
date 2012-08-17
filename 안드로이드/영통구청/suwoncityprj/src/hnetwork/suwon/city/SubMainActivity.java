package hnetwork.suwon.city;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class SubMainActivity extends Activity {

	private SubMainActivity cls;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.main);
        cls = this;
        
        ImageView img = (ImageView)findViewById(R.id.intro);
        
        img.setImageResource(R.drawable.intro);
        
        img.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v) {
        		Intent intent = new Intent(cls, MainActivity.class);
        		startActivity(intent);
        	}
        });
    }
}
