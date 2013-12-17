package pro.alexandre_mello.android.desafio;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pro.alexandre_mello.android.desafio.adapter.WordBaseAdapter;
import pro.alexandre_mello.android.desafio.bean.Word;
import pro.alexandre_mello.android.desafio.library.JSONParser;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

public class WordActivity extends Activity {
	ListView listWord;
	TextView txtWord;
	List<Word> lstWords = new ArrayList<Word>();
	//private int category_id = getIntent().getIntExtra("category_id", 0);

//	private String url = "http://192.168.10.196:3000/categories/"
//			+ String.valueOf(8) + "/words.json";
	private String url = "http://192.168.10.196:3000/categories/8/words.json";

	JSONArray arrWords = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_word);

		new getJSON().execute();
	}

	private class getJSON extends AsyncTask<String, String, JSONObject> {
		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			txtWord = (TextView) findViewById(R.id.txtWord);

			pDialog = new ProgressDialog(WordActivity.this);
			pDialog.setMessage("Getting words...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected JSONObject doInBackground(String... arg0) {
			JSONParser jParser = new JSONParser();

			JSONObject json = jParser.getJSONFromUrl(url);
			return json;
		}

		@Override
		protected void onPostExecute(JSONObject json) {
			pDialog.dismiss();
			try {
				arrWords = json.getJSONArray("words");
				for (int i = 0; i < arrWords.length(); i++) {
					JSONObject w = arrWords.getJSONObject(i);
					
					int id = w.getInt("id");
					String sWord = w.getString("word");
					
					Word word = new Word(id, sWord);
					lstWords.add(word);
					
					listWord = (ListView) findViewById(R.id.lstWord);
					
					WordBaseAdapter adapter = new WordBaseAdapter(WordActivity.this, lstWords);
					listWord.setAdapter(adapter);
				}
			} catch (JSONException e) {
				Log.e("JSON Parser", "Error " + e.toString());
			}
		}
	}
}
