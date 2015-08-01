package za.co.be.rettakid.facemap.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import org.androidannotations.annotations.EActivity;

import za.co.be.rettakid.facemap.R;

@EActivity(R.layout.activity_main)
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this,CameraActivity_.class);
        startActivity(intent);
    }

}
