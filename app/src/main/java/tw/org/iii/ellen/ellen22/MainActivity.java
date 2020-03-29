package tw.org.iii.ellen.ellen22;
//APP呼叫相機掃條碼,再傳回資料

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class MainActivity extends AppCompatActivity {
    private WebView webView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    123);
        }else {
            init() ;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults) ;
        init() ;
    }

    private void init(){
        webView = findViewById(R.id.webView) ;
        initWebView() ;
    }

    private void initWebView(){
        WebSettings settings = webView.getSettings() ;
        settings.setJavaScriptEnabled(true) ;

        webView.addJavascriptInterface(new MyJSObject(),"myJSObject") ;
        webView.loadUrl("file:///android_asset/ellen.html") ;
    }

    public class MyJSObject{
        @JavascriptInterface
        public void fromJS(){
            gotoScan() ;
        }
    }

    private void gotoScan(){
        Intent intent = new Intent(this,ScanActivity.class) ;
        startActivityForResult(intent,321) ;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 321 && resultCode == RESULT_OK){
            webView.loadUrl(String.format("javascript:showCode('%s')",data.getStringExtra("code")));
        }
    }
}
