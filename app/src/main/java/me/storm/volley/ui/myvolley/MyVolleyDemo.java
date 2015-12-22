package me.storm.volley.ui.myvolley;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.myrequest.FastJsonRequest;
import com.android.volley.myrequest.JsonPostRequest;
import com.android.volley.myrequest.UTF8StringRequest;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import me.storm.volley.R;
import me.storm.volley.RequestManager;
import me.storm.volley.ui.BaseActivity;
import me.storm.volley.data.UrlBean;
import me.storm.volley.model.User;
import me.storm.volley.data.VolleyApi;

public class MyVolleyDemo extends BaseActivity {

    private Button mImageRequestBtn, mNetworkImageViewBtn, mImageLoaderListenerBtn, mPostJosnRequestBtn, mOKFourBtn,
            fastJsonClassRequestBtn;
    private ImageView mShowImg, mShow2Img;
    private NetworkImageView mNetWorkImg;
    private TextView mJson1Txt, mMessageTxt, mMyTxt;
    private WebView mWebView;

    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.volley_demo1);

        mRequestQueue = RequestManager.getRequestQueue();// Volley.newRequestQueue(this);

        mImageRequestBtn = (Button) findViewById(R.id.volley_imageRequest_btn);
        mNetworkImageViewBtn = (Button) findViewById(R.id.volley_NetworkImageView_btn);
        mImageLoaderListenerBtn = (Button) findViewById(R.id.volley_ImageLoaderListener_btn);
        mPostJosnRequestBtn = (Button) findViewById(R.id.volley_PostJosnRequest_btn);
        mOKFourBtn = (Button) findViewById(R.id.volley_StringRequest_btn);
        fastJsonClassRequestBtn = (Button) findViewById(R.id.volley_fastJsonClassRequest_btn);

        mNetWorkImg = (NetworkImageView) findViewById(R.id.iv_image);
        mShowImg = (ImageView) findViewById(R.id.volley_imageRequest_img);
        mShow2Img = (ImageView) findViewById(R.id.volley_ImageLoaderListener_img);
        mJson1Txt = (TextView) findViewById(R.id.iv_text1);
        mMessageTxt = (TextView) findViewById(R.id.message_tv);
        mMyTxt = (TextView) findViewById(R.id.volley_fastjsonclassRequeset_txt);

        mWebView = (WebView) findViewById(R.id.webView1);

        mWebView.getSettings().setDefaultTextEncodingName("utf-8");
        mWebView.setWebViewClient(new WebViewClient() {
            // 重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        mImageRequestBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                onImageRequest();
            }
        });

        mNetworkImageViewBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                onNetworkImageView();
            }
        });

        mImageLoaderListenerBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                onImageLoaderListener();

            }
        });

        mPostJosnRequestBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                onPostJosnRequest();

            }
        });

        mOKFourBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                onUTF8StringRequest();
            }
        });

        fastJsonClassRequestBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                onFastJsonClassRequest();
            }
        });
    }

    private void onNetworkImageView() {
        ImageLoader imageLoader = RequestManager.getImageLoader();

        mNetWorkImg.setDefaultImageResId(R.drawable.ic_launcher);// 默认图片
        mNetWorkImg.setErrorImageResId(android.R.drawable.ic_input_delete);// 出错时的图片
        mNetWorkImg.setImageUrl(VolleyApi.IMAGE_URL, imageLoader);// 请求图片
    }

    private void onImageLoaderListener() {
        ImageLoader loader = RequestManager.getImageLoader();

        ImageListener listener = ImageLoader.getImageListener(mShow2Img,
                R.drawable.ic_launcher, android.R.drawable.ic_input_delete);

        loader.get(UrlBean.urls[7], listener, 0, 0);// 获取图片
    }

    private void onImageRequest() {
        executeRequest(new ImageRequest(VolleyApi.PIC_URL,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        mShowImg.setImageBitmap(response);
                    }
                }, 0, 0, Config.ARGB_8888,
                errorListener()));
    }

    private void onGetJosnRequest() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", "xinhua");
            jsonObject.put("password", "123456");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String url = VolleyApi.MY_JSONDEMO_ACTION + "?json=" + jsonObject.toString();

        executeRequest(new JsonObjectRequest(Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            mJson1Txt.setText("name :" + response.getString("name")
                                    + " password :" + response.getString("password"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, errorListener()));

    }

    private void onPostJosnRequest() {
        Map<String, String> parames = new HashMap<String, String>();
        parames.put("name", "xiaoming");
        parames.put("password", "123");

        executeRequest(new JsonPostRequest(Method.POST,
                VolleyApi.MY_JSONDEMO_ACTION, parames, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    mJson1Txt.setText("name :" + response.getString("name")
                            + " password :" + response.getString("password"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, errorListener()));
    }

    private void onUTF8StringRequest() {
        executeRequest(new UTF8StringRequest(Method.GET,
                VolleyApi.MY_STRING_ACTION, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                mMessageTxt.setText("你好 ：" + response);
            }
        }, errorListener()));
    }

    private void onFastJsonClassRequest() {
        Map<String, String> maps = new HashMap<String, String>();
        maps.put("name", "李四1");
        maps.put("password", "xinhua");

        mProgressDialog.show();

        FastJsonRequest request = new FastJsonRequest<User>(
                VolleyApi.MY_JSON_ACTION, maps, User.class, new Response.Listener<User>() {
            @Override
            public void onResponse(User response) {
                mMyTxt.setText("name :" + response.getUserName()
                        + " password :" + response.getPassWord() + " id :"
                        + response.getId());
                mProgressDialog.cancel();
            }
        }, errorListener());

        request.setShouldCache(true);
        executeRequest(request);
    }

}
