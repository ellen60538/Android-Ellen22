package tw.org.iii.ellen.ellen22;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{
    private ZXingScannerView mScannerView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mScannerView = new ZXingScannerView(this) ; // Programmatically initialize the scanner view
        //mScannerView.setAspectTolerance(0.5f) ;華為手機需要這行
        setContentView(mScannerView) ;  // Set the scanner view as the content view整個畫面都是自訂View
        //setContentView(R.layout.activity_scan);
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera() ; // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here
        //Log.v("ellen", rawResult.getText()); // Prints scan results
        //Log.v("ellen", rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode, pdf417 etc.)

        // If you would like to resume scanning, call this method below:
        //mScannerView.resumeCameraPreview(this);

        setResult(RESULT_OK, new Intent().putExtra("code",rawResult.getText()));

        finish() ;
    }
}
