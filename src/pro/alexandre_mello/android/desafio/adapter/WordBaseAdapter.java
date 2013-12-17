package pro.alexandre_mello.android.desafio.adapter;

import java.util.List;

import pro.alexandre_mello.android.desafio.R;
import pro.alexandre_mello.android.desafio.bean.Word;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class WordBaseAdapter extends BaseAdapter {
	Context context;
	List<Word> words;

	public WordBaseAdapter(Context context, List<Word> words) {
		super();
		this.context = context;
		this.words = words;
	}

	private class ViewHolder {
		TextView txtWord;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;

		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.list_word, null);
			holder = new ViewHolder();
			holder.txtWord = (TextView) convertView.findViewById(R.id.txtWord);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		Word word = (Word) getItem(position);
		
		holder.txtWord.setText(word.getWord());
		return convertView;
	}

}
