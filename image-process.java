package com.example.suba_fixed.Activities.MLVision;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.ImageCapture;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.suba_fixed.Database.db_connection;
import com.example.suba_fixed.MainActivity;
import com.example.suba_fixed.R;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.Text;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class Vision extends AppCompatActivity implements SurfaceHolder.Callback, Detector.Processor {

    private SurfaceView cameraView;
    private TextView txtView;
    private TextView txtView2;
    private CameraSource cameraSource;

    Button btnok;
    Button btngettext;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                try {
                    cameraSource.start(cameraView.getHolder());
                } catch (Exception e) {

                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_vision);
        cameraView = findViewById(R.id.surface_view);
        txtView = findViewById(R.id.txtview);
        txtView2 = findViewById(R.id.txtview2);
        btnok = findViewById(R.id.button);
        btngettext = findViewById(R.id.button10);
        Button btnCancel = findViewById(R.id.button9);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });


        databaseReference = FirebaseDatabase.getInstance().getReference("MyUsers").child(firebaseUser.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                db_connection data = snapshot.getValue(db_connection.class);
                String VisionCard = data.getVisionCard();

                txtView2.setText("Kayıtlı : " + VisionCard);

            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                txtView2.setText("Sisteme kayıtlı bir data yok!");
            }
        });


        TextRecognizer txtRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
        if (!txtRecognizer.isOperational()) {
            Log.e("Main Activity", "Sistem arızası!");
        } else {
            cameraSource = new CameraSource.Builder(getApplicationContext(), txtRecognizer)
                    .setFacing(CameraSource.CAMERA_FACING_BACK)
                    .setAutoFocusEnabled(true)
                    .setRequestedFps(2.0f)
                    .build();
            cameraView.getHolder().addCallback(this);
            txtRecognizer.setProcessor(this);

        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},1);
                return;
            }
            cameraSource.start(cameraView.getHolder());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        cameraSource.stop();
    }

    @Override
    public void release() {
    }

    @Override
    public void receiveDetections(Detector.Detections detections) {
       SparseArray items = detections.getDetectedItems();
       final StringBuilder strBuilder = new StringBuilder();

           TextBlock item = (TextBlock)items.valueAt(1);
           strBuilder.append(item.getValue());

           btngettext.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   txtView.setText("Alınan metin: " + strBuilder.toString());
                   String a = strBuilder.toString();

                   btnok.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {

                           String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                           databaseReference = FirebaseDatabase.getInstance().getReference("MyUsers").child(uid);
                           HashMap<String, Object> hashMap = new HashMap<>();
                           hashMap.put("VisionCard", a);
                           databaseReference.updateChildren(hashMap);

                       }
                   });

               }
           });




    }


}



