package pro.alexandre_mello.android.desafio;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pro.alexandre_mello.android.desafio.adapter.CategoryBaseAdapter;
import pro.alexandre_mello.android.desafio.bean.Category;
import pro.alexandre_mello.android.desafio.library.JSONParser;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	ListView lstCategory;
	ImageView imgCategory;
	TextView txtCategory;

	List<Category> lstCategories = new ArrayList<Category>();

	private static String url = "http://192.168.10.196:3000/categories.json";

	JSONArray arrCategories = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

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
			txtCategory = (TextView) findViewById(R.id.txtCategory);

			pDialog = new ProgressDialog(MainActivity.this);
			pDialog.setMessage("Getting data...");
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
								public void onItemClick(AdapterView<?> parent,
										View view, int position, long id) {
									Toast.makeText(
											MainActivity.this,
											"You clicked at "
													+ lstCategories.get(
															+position)
															.getDescription(),
											Toast.LENGTH_SHORT).show();
								}
							});
				}
			} catch (JSONException e) {
				Log.e("JSON Parser", "Erro" + e.toString());
			}
		}
	}

}
