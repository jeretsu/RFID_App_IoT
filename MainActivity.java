package com.example.rfidapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private String dataName;
    private String dataStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseFirestore.getInstance();

        final DocumentReference docRef = db.collection("RFIDactions").document("RFIDdatabase");
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
        final TextView nameText = (TextView) findViewById(R.id.Name);
        final TextView statusText = (TextView) findViewById(R.id.Status);


            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.w("Listen failed.", error);
                    return;
                }

                String newinfo = snapshot.getData().toString();
                dataName = (String) snapshot.getData().get("Name");
                dataStatus = (String) snapshot.getData().get("Status");
                System.out.println(dataName);
                System.out.println(dataStatus);

                nameText.setText(dataName);
                statusText.setText(dataStatus);

                if (snapshot != null && snapshot.exists()) {
                    Log.d("Current data: ", newinfo);

                } else {
                    //Log.d("Current data: null");
                }

            }
        });
    }

}