package es.mxcircuit.mxcircuit.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import es.mxcircuit.mxcircuit.utils.Constants;

public class WebViewActivity extends AppCompatActivity implements View.OnClickListener {

    private WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(es.mxcircuit.mxcircuit.R.layout.activity_web_view);

        Intent intent = getIntent();
        String url = intent.getStringExtra(Constants.BUNDLE.URL_WEBVIEW);

        webview = (WebView) findViewById(es.mxcircuit.mxcircuit.R.id.webview);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setUseWideViewPort(true);
        webview.getSettings().setLoadWithOverviewMode(true);
        webview.getSettings().setDomStorageEnabled(true);

        webview.loadUrl(url);

        TextView domain = (TextView) findViewById(es.mxcircuit.mxcircuit.R.id.nameDomain);
        domain.setText(url);

        ImageView close = (ImageView) findViewById(es.mxcircuit.mxcircuit.R.id.closeWebview);
        close.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        finish();
    }
}
