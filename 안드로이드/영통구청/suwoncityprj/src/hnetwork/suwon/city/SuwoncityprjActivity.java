package hnetwork.suwon.city;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;

public class SuwoncityprjActivity extends Activity {
    /** Called when the activity is first created. */
	private SuwoncityprjActivity cls;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.main);
        cls = this;
        ImageView img = (ImageView)findViewById(R.id.intro);
        
        img.setImageResource(R.drawable.intro);
        
        TimerTask task = new TimerTask() {
        	public void run() {
        		Intent intent = new Intent(cls, MainActivity.class);
        		startActivity(intent);
        	}
        };
        
        Timer timer = new Timer();
        
        timer.schedule(task, 2000);
    }
}