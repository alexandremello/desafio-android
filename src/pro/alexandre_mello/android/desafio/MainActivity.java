package pro.alexandre_mello.android.desafio;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import pro.alexandre_mello.android.desafio.bean.Category;
import pro.alexandre_mello.android.desafio.library.JSONParser;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	ListView lstCategory;
	ImageView imgCategory;
	TextView txtCategory;
	
	List<Category> lstCategories = new ArrayList<Category>();

	private static String url = "@string/uri" + "categories.json";

	JSONArray arrCategories;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

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
					// Adding value HashMap key => value
					Category category = new Category(id, description, image_url);
					lstCategories.add(category);
					
					lstCategory = (ListView) findViewById(R.id.lstCategory);
					
					ListAdapter adapter = new SimpleAdapter(MainActivity.this,
							oslist, R.layout.list_v, new String[] { TAG_VER,
									TAG_NAME, TAG_API }, new int[] { R.id.vers,
									R.id.name, R.id.api });
					lstCategory.setAdapter(adapter);
					lstCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> parent,
								View view, int position, long id) {
							Toast.makeText(
									MainActivity.this,
									"You Clicked at " + lstCategories.get(+position).getDescription(),
									Toast.LENGTH_SHORT).show();
						}
					});
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

	}

}