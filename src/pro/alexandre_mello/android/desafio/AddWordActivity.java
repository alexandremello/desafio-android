package pro.alexandre_mello.android.desafio;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddWordActivity extends Activity {
	TextView txtCategory;
	EditText edtWord;
	Button   btnAddWord;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_word);
				
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_word, menu);
		return true;
	}
	
	private class sendJSON extends AsyncTask<String, String, String> {
		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			edtWord = (EditText) findViewById(R.id.edtWord);
		}
		
		@Override
		protected String doInBackground(String... arg0) {
			return null;
		}
		
		@Override
		protected void onPostExecute(String resp) {
			pDialog.dismiss();
			Toast toast = Toast.makeText(getApplicationContext(), "Foi para o servidor!", Toast.LENGTH_SHORT);
			toast.setGravity(Gravity.BOTTOM|Gravity.CENTER, 0, 0);
			toast.show();
		}
	}

}
