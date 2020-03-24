package com.ekorydes.FirebaseConnection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ekorydes.bscsafirebase240320.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    //Step 1: Create object of Firebase & fire store


    private FirebaseFirestore objectFirebaseFirestore;
    private Dialog objectDialog;
    private EditText documentET,cityNameET,cityDetailsET,valuesTv;


     DocumentReference ObjectDocumentReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Step 2: Initialize Firebase & firestore object


        objectFirebaseFirestore=FirebaseFirestore.getInstance();
        objectDialog=new Dialog(this);

        objectDialog.setContentView(R.layout.please_wait);
        documentET=findViewById(R.id.documentIDET);

        cityNameET=findViewById(R.id.cityNameET);
        cityDetailsET=findViewById(R.id.Details);
        if(!documentET.getText().toString().isEmpty())

        ObjectDocumentReference=objectFirebaseFirestore.collection("YoutubeData").document(
                documentET.getText().toString()
        );
    }

    public void addValues(View v)
    {
        try
        {
            if(!documentET.getText().toString().isEmpty() && !cityNameET.getText().toString().isEmpty()
            && !cityDetailsET.getText().toString().isEmpty()) {
                objectDialog.show();
                Map<String, Object> objectMap = new HashMap<>();
                objectMap.put("city_name", cityNameET.getText().toString());

                objectMap.put("city_details", cityDetailsET.getText().toString());
                objectFirebaseFirestore.collection("NewCities")
                        .document(documentET.getText().toString()).set(objectMap)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                objectDialog.dismiss();
                                Toast.makeText(MainActivity.this, " Successfully", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                objectDialog.dismiss();
                                Toast.makeText(MainActivity.this, "Fail data", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
            else
            {
                Toast.makeText(this, " enter valid details", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e)
        {
            objectDialog.dismiss();
            Toast.makeText(this, "addValues:"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void getvaluesfromfirebase(View view)
    {
        try {
            if (!documentET.getText().toString().isEmpty()){

                ObjectDocumentReference = objectFirebaseFirestore.collection("YoutubeData").document(
                        documentET.getText().toString());
                ObjectDocumentReference.get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                String documnetE=documentSnapshot.getId();
                                String cityname =documentSnapshot.getId();
                                String details =documentSnapshot.getId();
                                valuesTv.setText
                                        (
                                                "Name "+documentET+"\n"+"city "+cityNameET+"\n"+
                                                        "detail "+cityDetailsET
                                        );



                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainActivity.this ,"fails",Toast.LENGTH_LONG).show();
                            }
                        });
        }

        }
        catch (Exception e)
        {
            Toast.makeText(this, "addValues:"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
