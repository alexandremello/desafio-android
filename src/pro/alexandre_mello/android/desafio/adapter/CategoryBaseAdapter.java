package pro.alexandre_mello.android.desafio.adapter;

import java.util.List;

import pro.alexandre_mello.android.desafio.R;
import pro.alexandre_mello.android.desafio.bean.Category;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CategoryBaseAdapter extends BaseAdapter {
	Context context;
	List<Category> categories;

	public CategoryBaseAdapter(Context context, List<Category> categories) {
		super();
		this.context = context;
		this.categories = categories;
	}

	/* private view holder class */
	private class ViewHolder {
		ImageView imgCategory;
		TextView txtCategory;
	}

	@Override
	public int getCount() {
		return categories.size();
	}

	@Override
	public Object getItem(int arg0) {
		return categories.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return categories.indexOf(getItem(arg0));
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;

		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.list_category, null);
			holder = new ViewHolder();
			holder.imgCategory = (ImageView) convertView
					.findViewById(R.id.imgCategory);
			holder.txtCategory = (TextView) convertView
					.findViewById(R.id.txtCategory);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Category category = (Category) getItem(position);

		holder.txtCategory.setText(category.getDescription());
		//holder.imgCategory.setImageDrawable(getRemoteImage("http://192.168.10.196:3000" + category.getImage_url()));
		//UrlImageViewHelper.setUrlDrawable(holder.imgCategory, "http://192.168.10.196:3000" + category.getImage_url());
		return convertView;
	}

}
