package net.swiprnoswiping.swipr;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.widget.ImageView;
import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;


public class QRActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);
    }

    try {
        String random_string = "randomSecretConfirmationKey"
        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
        Bitmap bitmap = barcodeEncoder.encodeBitmap(random_string, BarcodeFormat.QR_CODE, 400, 400);
        ImageView imageViewQrCode = (ImageView) findViewById(R.id.qrCode);
        imageViewQrCode.setImageBitmap(bitmap);
    } catch(Exception e) {

    }



    public void backButton(View view){
        Intent backIntent = new Intent(this, MapActivity.class);
        startActivity(backIntent);
    }
}
