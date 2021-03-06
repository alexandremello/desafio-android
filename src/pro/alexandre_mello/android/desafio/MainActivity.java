package pro.alexandre_mello.android.desafio;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pro.alexandre_mello.android.desafio.adapter.CategoryBaseAdapter;
import pro.alexandre_mello.android.desafio.bean.Category;
import pro.alexandre_mello.android.desafio.library.JSONParser;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {
	ListView lstCategory;
	ImageView imgCategory;
	TextView txtCategory;

	List<Category> lstCategories = new ArrayList<Category>();

	String url;

	JSONArray arrCategories = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		url = getApplicationContext().getString(R.string.uri) + "/categories.json";
		new getJSON().execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private class getJSON extends AsyncTask<String, String, JSONObject> {
		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			imgCategory = (ImageView) findViewById(R.id.imgCategory);
			txtCategory = (TextView) findViewById(R.id.txtWordCategory);

			pDialog = new ProgressDialog(MainActivity.this);
			pDialog.setMessage("Getting categories...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected JSONObject doInBackground(String... arg0) {
			JSONParser jParser = new JSONParser();

			// get json from url
			JSONObject json = jParser.getJSONFromUrl(url);
			return json;
		}

		@Override
		protected void onPostExecute(JSONObject json) {
			pDialog.dismiss();
			try {
				// Getting JSON Array from URL
				arrCategories = json.getJSONArray("categories");
				for (int i = 0; i < arrCategories.length(); i++) {
					JSONObject c = arrCategories.getJSONObject(i);
					// Storing JSON item in a Variable
					int id = c.getInt("id");
					String description = c.getString("description");
					String image_url = c.getString("image_url");

					Category category = new Category(id, description, image_url);
					lstCategories.add(category);

					lstCategory = (ListView) findViewById(R.id.lstCategory);

					CategoryBaseAdapter adapter = new CategoryBaseAdapter(
							MainActivity.this, lstCategories);
					lstCategory.setAdapter(adapter);

					lstCategory
							.setOnItemClickListener(new AdapterView.OnItemClickListener() {
								@Override
								public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
									Intent intent = new Intent(MainActivity.this, WordActivity.class);
									intent.putExtra("category", lstCategories.get(position));
									startActivity(intent);
								}
							});
				}
			} catch (JSONException e) {
				Log.e("JSON Parser", "Error " + e.toString());
			}
		}
	}

}
