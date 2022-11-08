package com.google.qlsv;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddActivity extends AppCompatActivity {
     EditText edtmasv,edtten,edtgmail,edtsurl;

     Button btnadd,btnback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        edtmasv=(EditText) findViewById(R.id.edtmasv);
        edtten=(EditText) findViewById(R.id.edtten);
        edtgmail=(EditText) findViewById(R.id.edtgmail);
        edtsurl=(EditText) findViewById(R.id.edtimageurl);
        btnadd=(Button) findViewById(R.id.btnadd);
        btnback=(Button) findViewById(R.id.btnback);

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inserData();
                clear();
            }
        });
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }

    private void clear() {
        edtmasv.setText("");
        edtten.setText("");
        edtgmail.setText("");
        edtsurl.setText("");

    }

    private void inserData() {
        Map<String,Object> map =new HashMap<>();
        map.put("MaSV",edtmasv.getText().toString());
        map.put("Ten",edtten.getText().toString());
        map.put("Gmail",edtgmail.getText().toString());
        map.put("Surl",edtsurl.getText().toString());

        FirebaseDatabase.getInstance().getReference().child("SinhVien").push()
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(AddActivity.this,"Đã thêm thành công",Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure( Exception e) {
                        Toast.makeText(AddActivity.this,"Thất bại",Toast.LENGTH_SHORT).show();

                    }
                });

        }
    }

