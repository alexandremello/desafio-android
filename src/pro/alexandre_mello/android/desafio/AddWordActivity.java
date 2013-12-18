package pro.alexandre_mello.android.desafio;

import org.json.JSONException;
import org.json.JSONObject;

import pro.alexandre_mello.android.desafio.bean.Category;
import pro.alexandre_mello.android.desafio.library.HttpClient;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddWordActivity extends Activity {
	private static final String TAG = "AddWordActivity";
	private String url;
	TextView txtCategory;
	EditText edtWord;
	Button btnSendJSON;
	Category category;
	Intent intent;
	JSONObject jsonObjSend;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_word);
		txtCategory = (TextView) findViewById(R.id.txtWordCategory);

		intent = getIntent();
		category = (Category) intent.getParcelableExtra("category");
		txtCategory.setText(category.getDescription());

		edtWord = (EditText) findViewById(R.id.edtWord);

		url = getApplicationContext().getString(R.string.uri) + "/categories/"
				+ String.valueOf(category.getId()) + "/words/";
		Log.i(TAG, url);

		btnSendJSON = (Button) findViewById(R.id.btnSendJSON);
		btnSendJSON.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new SendJSON().execute();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_word, menu);
		return true;
	}

	private class SendJSON extends AsyncTask<String, String, JSONObject> {
		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			Log.d(TAG, "sendJOSN.onPreExecute");
			super.onPreExecute();

			pDialog = new ProgressDialog(AddWordActivity.this);
			pDialog.setMessage("Sending new word...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected JSONObject doInBackground(String... arg0) {
			jsonObjSend = new JSONObject();
			JSONObject jsonWord = new JSONObject();
			String word = edtWord.getText().toString();
			try {
				// Add key/value pairs
				jsonWord.put("word", word);
				jsonObjSend.put("word", jsonWord);

				// Add a nested JSONObject (e.g. for header information)
				// JSONObject header = new JSONObject();
				// header.put("deviceType", "Android"); // Device type
				// header.put("deviceVersion", "4.4"); // Device OS version
				// header.put("language", "pt-br"); // Language of the
				// Android
				// client
				//jsonObjSend.put("header", header);

				// Output the JSON object we're sending to Logcat:
				Log.i(TAG, jsonObjSend.toString(2));

			} catch (JSONException e) {
				e.printStackTrace();
			}

			// Send the HttpPostRequest and receive a JSONObject in return
			JSONObject jsonObjRecv = HttpClient.SendHttpPost(url,
					jsonObjSend);

			// Toast.makeText(AddWordActivity.this, jsonObjRecv.toString(),
			// Toast.LENGTH_SHORT).show();
			return null;
		}

		@Override
		protected void onPostExecute(JSONObject json) {
			Log.d(TAG, "sendJSON.onPostExecute");
			pDialog.dismiss();
		}
	}
}
