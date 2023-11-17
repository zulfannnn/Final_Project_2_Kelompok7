package kelompok7.msibfinalproject2;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
public class HalamanAdmin extends AppCompatActivity {
    Button staff, stock;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_admin);
        Bundle extras1 = getIntent().getExtras();
        String intent = extras1.getString("yourkey1");
        staff = findViewById(R.id.btn_staff);
        stock = findViewById(R.id.btn_addstock);
        staff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HalamanAdmin.this, RegisterStaff.class);
                startActivity(intent);
            }
        });
        stock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HalamanAdmin.this, HalamanTambahStock.class);
                startActivity(intent);
            }
        });
    }
}