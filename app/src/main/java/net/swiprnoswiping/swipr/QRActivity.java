package net.swiprnoswiping.swipr;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;


import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class QRActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);
        final RelativeLayout rl = (RelativeLayout) findViewById(R.id.reLay);
        final ImageView qrcode = (ImageView) findViewById(R.id.qrCode);
        final String random_string = getIntent().getExtras().getString("secret_key");
        try {
            Bitmap temp;
            temp = genCode(random_string);
            qrcode.setImageBitmap(temp);
        }
        catch (WriterException e) {
           System.out.println("God is cruel");
        }
        catch (IOException e){
           System.out.println("Please kill me");
        }
}

    private Bitmap genCode(String text) throws WriterException, IOException {
        int width = 400;
        int height = 400;
        QRCodeWriter writer = new QRCodeWriter();
        BitMatrix bitMatrix = writer.encode(text, BarcodeFormat.QR_CODE, width, height);
//        ByteArrayOutputStream pngStream = new ByteArrayOutputStream();
//        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngStream);
//        byte [] pngData = pngStream.toByteArray();
        Bitmap qrcode = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        for (int x = 0; x < width; x++){
            for (int y = 0; y < height; y++){
                qrcode.setPixel(x, y, bitMatrix.get(x,y) ? Color.BLACK : Color.WHITE);
            }
        }
        return qrcode;
    }


    public void backButton(View view){
        Intent backIntent = new Intent(this, MapActivity.class);
        startActivity(backIntent);
    }
}
