package me.storm.volley.ui.myvolley;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.huewu.pla.lib.MultiColumnListView;
import com.huewu.pla.lib.internal.PLA_AbsListView;
import com.huewu.pla.lib.internal.PLA_AbsListView.OnScrollListener;

import me.storm.volley.R;
import me.storm.volley.RequestManager;
import me.storm.volley.data.UrlBean;

/**
 * 使用的开源的瀑布流 
 * 地址：https://github.com/youxiachai/pinterest-like-adapter-view
 * 有下拉刷新的功能的话 可以使用MultiColumnPullToRefreshListView
 * @author chen
 *
 */
public class ImageLoaderActivity extends Activity implements OnScrollListener{
	private MultiColumnListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageloader);
//        int width = getWindowManager().getDefaultDisplay().getWidth();
//        int itemWidth = (width - 20)/3; //随便写了个20 限定大小，不限定也可以
        listView = (MultiColumnListView) findViewById(R.id.volley_column_listView);
        listView.setAdapter(new ImageLoaderAdapter(this,UrlBean.urls));
        listView.setOnScrollListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


	@Override
	public void onScrollStateChanged(PLA_AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onScroll(PLA_AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		
	}
    public class ImageLoaderAdapter extends BaseAdapter {
        private Context context;
        private String[] urls;
        public ImageLoaderAdapter(Context ctx, String[] urls) {
            this.context = ctx;
            this.urls = urls;
        }

        @Override
        public int getCount() {
            return urls.length;
        }

        @Override
        public String getItem(int position) {
            // TODO Auto-generated method stub
            return urls[position];
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            NetWorkAdapterHolder holder;
            if(convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.imageloader_item,null);
                holder = new NetWorkAdapterHolder();
                holder.itemImage = (ImageView) convertView.findViewById(R.id.itemImage);
                holder.itemText = (TextView) convertView.findViewById(R.id.itemText);
                convertView.setTag(holder);
            } else {
                holder = (NetWorkAdapterHolder) convertView.getTag();
            }
            String url = getItem(position);

            //android.R.drawable.ic_menu_rotate代表默认图
            //android.R.drawable.ic_delete代表加载失败的图
            ImageLoader.ImageListener listener = ImageLoader.getImageListener(holder.itemImage,
                    android.R.drawable.ic_menu_rotate, android.R.drawable.ic_delete);
            RequestManager.getImageLoader().get(url, listener);

            holder.itemText.setText(url.substring(url.length() - 8));
            return convertView;
        }

        class NetWorkAdapterHolder {
            ImageView itemImage;
            TextView itemText;
        }

    }
}
