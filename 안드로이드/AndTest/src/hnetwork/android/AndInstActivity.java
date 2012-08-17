package hnetwork.android;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class AndInstActivity extends Activity implements OnClickListener {
	private EditText edtUser_id;
	private EditText edtUser_pass;
	private Button   btnLogin;
	private AndDbAdapter dbHelper;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Do so by calling setContentView(), passing it the reference to your layout resource 
        // in the form of: R.layout.layout_file_name For example, 
        // if your XML layout is saved as main_layout.xml, 
        // you would load it for your Activity like so
        setContentView(R.layout.main);
        
        btnLogin     = (Button)   findViewById(R.id.btn_save);
        edtUser_id   = (EditText) findViewById(R.id.user_id);
        edtUser_pass = (EditText) findViewById(R.id.user_pass);
        
        // button event 
        btnLogin.setOnClickListener(this);
        
        dbHelper = new AndDbAdapter(this);
        dbHelper.open();
    }

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		Log.w("MyPGM", "Button onClick....");
		
		if (edtUser_id.getText().toString().equals("")) {
			AlertDialog alertDialog = new AlertDialog.Builder(this).create();
			alertDialog.setTitle("Error Message");
			alertDialog.setMessage("���̵� �Է����� �ʾҽ��ϴ�!");
			alertDialog.setIcon(R.drawable.ic_launcher);
			alertDialog.setButton("OK", new DialogInterface.OnClickListener() {				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.dismiss(); 
				}
			}); 
			alertDialog.show();
			
			return;
		}
		if (edtUser_pass.getText().toString().equals("")) {
			AlertDialog alertDialog = new AlertDialog.Builder(this).create();
			alertDialog.setTitle("Error Message");
			alertDialog.setMessage("�н����带 �Է����� �ʾҽ��ϴ�!");
			alertDialog.setIcon(R.drawable.ic_launcher);
			alertDialog.setButton("OK", new DialogInterface.OnClickListener() {				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.dismiss(); 
				}
			});
			alertDialog.show();
			
			return;
		}
		
		long result = dbHelper.insertData(edtUser_id.getText().toString(), edtUser_pass.getText().toString());
		if (result == -1) {
			AlertDialog alertDialog = new AlertDialog.Builder(this).create();
			alertDialog.setTitle("Error Message");
			alertDialog.setMessage("����ϴµ� ������ �߻��Ͽ����ϴ�");
			alertDialog.setIcon(R.drawable.ic_launcher);
			alertDialog.setButton("OK", new DialogInterface.OnClickListener() {				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.dismiss(); 
				}
			}); 
			alertDialog.show();
			return;
		}else {
			AlertDialog alertDialog = new AlertDialog.Builder(this).create();
			alertDialog.setTitle("Success Message");
			alertDialog.setMessage("����� �Ϸ��Ͽ����ϴ�");
			alertDialog.setIcon(R.drawable.ic_launcher);
			alertDialog.setButton("OK", new DialogInterface.OnClickListener() {				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.dismiss(); 
					movePrevPage();
					
				}
			}); 
			alertDialog.show();
		}			
	}
	
	private void movePrevPage()
	{
		Intent i = new Intent(this, AndMain.class);
		
		startActivity(i);
	}
}