package jp.ykws.androidwebviewexample;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

	private static final String PREF_PREV_URL_STRING_KEY = "prevUrlString";

	private static final String JAVASCRIPT_PLAY_AUDIO = "javascript:playAudio();";
	private static final String JAVASCRIPT_PAUSE_AUDIO = "javascript:pauseAudio();";

	private EditText mUrlEditText;
	private WebView mWebView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mUrlEditText = (EditText) findViewById(R.id.urlEditText);
		SharedPreferences pref = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
		String prevUrl = pref.getString(PREF_PREV_URL_STRING_KEY, null);
		if (prevUrl != null) {
			mUrlEditText.setText(prevUrl);
		}

		mWebView = (WebView) findViewById(R.id.webView);
		mWebView.setWebViewClient(new WebViewClient() {

			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				view.loadUrl(JAVASCRIPT_PLAY_AUDIO);
			}
		});

		WebSettings webSettings = mWebView.getSettings();
		webSettings.setJavaScriptEnabled(true);

		findViewById(R.id.goButton).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				SharedPreferences pref = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
				pref.edit().putString(PREF_PREV_URL_STRING_KEY, mUrlEditText.getText().toString()).commit();

				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(mUrlEditText.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

				mWebView.requestFocus();
				mWebView.loadUrl(mUrlEditText.getText().toString());
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		mWebView.loadUrl(JAVASCRIPT_PLAY_AUDIO);
	}

	@Override
	protected void onPause() {
		super.onPause();
		mWebView.loadUrl(JAVASCRIPT_PAUSE_AUDIO);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((KeyEvent.KEYCODE_BACK == keyCode) && mWebView.canGoBack()) {
			mWebView.goBack();
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}

}
