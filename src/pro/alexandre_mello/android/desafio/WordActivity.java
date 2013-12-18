package pro.alexandre_mello.android.desafio;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pro.alexandre_mello.android.desafio.bean.Category;
import pro.alexandre_mello.android.desafio.library.JSONParser;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class WordActivity extends Activity {
	ListView listWord;
	TextView txtWordCategory;
	TextView txtWord;
	Button btnAddWord;
	ArrayList<HashMap<String, String>> wordList = new ArrayList<HashMap<String, String>>();
	Intent intent;
	Category category;
	private String url;

	JSONArray arrWords = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.d("WordActivity", "onCreate");

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_word);

		txtWordCategory = (TextView) findViewById(R.id.txtWordCategory);
		btnAddWord = (Button) findViewById(R.id.btnAddWord);
		btnAddWord.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(WordActivity.this,
						AddWordActivity.class);
				intent.putExtra("category", category);
				//startActivity(intent);
				startActivityForResult(intent, 1);
			}
		});

		intent = getIntent();
		category = (Category) intent.getParcelableExtra("category");
		
		txtWordCategory.setText(category.getDescription());
		
		url = "http://192.168.10.196:3000/categories/"
				+ String.valueOf(category.getId()) + "/words.json";
		Log.d("WordActivity", "getJSON.execute");
		Log.d("WordActivity", url);
		new getJSON().execute();

	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		  if (requestCode == 1) {

		     if(resultCode == RESULT_OK){
		    	 wordList.clear();
		    	 new getJSON().execute();
		     }
		  }
		}//onActivityResult

	private class getJSON extends AsyncTask<String, String, JSONObject> {
		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			Log.d("WordActivity - getJSON", "onPreExecute");
			super.onPreExecute();

			txtWord = (TextView) findViewById(R.id.txtAddWordCategoryDescription);

			pDialog = new ProgressDialog(WordActivity.this);
			pDialog.setMessage("Getting words...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected JSONObject doInBackground(String... arg0) {
			Log.d("WordActivity - getJSON", "doInBackground");
			JSONParser jParser = new JSONParser();

			Log.d("WordActivity.getJSON.onPreExecute",
					"JSONParser.getJSONFromUrl");
			Log.d("WordActivity.getJSON.onPreExecute", url);
			JSONObject json = jParser.getJSONFromUrl(url);
			return json;
		}

		@Override
		protected void onPostExecute(JSONObject json) {
			Log.d("WordActivity.getJSON", "onPostExecute");

			pDialog.dismiss();
			try {
				Log.d("WordActivity.getJSON.onPostExecute", "getJSONArray");
				arrWords = json.getJSONArray("words");
				Log.d("WordActivity.getJSON.onPostExecute",
						"for arrWords.length()");
				for (int i = 0; i < arrWords.length(); i++) {
					JSONObject w = arrWords.getJSONObject(i);

					int id = w.getInt("id");
					String sWord = w.getString("word");

					Log.d("WordActivity.getJSON.onPostExecute",
							String.valueOf(id) + " - " + sWord);
					HashMap<String, String> map = new HashMap<String, String>();
					map.put("word", sWord);
					wordList.add(map);

					listWord = (ListView) findViewById(R.id.lstWord);
					ListAdapter adapter = new SimpleAdapter(WordActivity.this,
							wordList, R.layout.list_word,
							new String[] { "word" },
							new int[] { R.id.txtAddWordCategoryDescription });
					listWord.setAdapter(adapter);
				}
			} catch (JSONException e) {
				Log.e("JSON Parser", "Error " + e.toString());
			}
		}
	}
}
