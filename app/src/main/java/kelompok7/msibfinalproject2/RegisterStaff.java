package kelompok7.msibfinalproject2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class RegisterStaff extends AppCompatActivity {
    EditText ETname, ETpassword, ETcfrimpassword;
    ImageButton back;
    Button registrasi;
    DbHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_staff);

        db = new DbHelper(this);
        ETname = findViewById(R.id.et_name);
        ETpassword = findViewById(R.id.et_Password);
        ETcfrimpassword = findViewById(R.id.et_Cfrim_Password);
        registrasi = findViewById(R.id.btn_register);
        back = findViewById(R.id.btn_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterStaff.this, HalamanAdmin.class);
                startActivity(intent);
            }
        });
        registrasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterStaff.this, LoginStaff.class);
                startActivity(intent);
                String name = ETname.getText().toString().trim();
                String password = ETpassword.getText().toString().trim();
                String cfrim_password = ETcfrimpassword.getText().toString().trim();
                if (password.equals(cfrim_password)) {
                    long val = db.tambahStaff(name, password);
                    if (val > 0) {
                        Toast.makeText(RegisterStaff.this, "Anda Berhasil Menambahkan Staff "+name, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RegisterStaff.this, "Password Tidak Sama", Toast.LENGTH_SHORT).show();
                }
                if (name.equals("") || password.equals("")) {
                    Toast.makeText(RegisterStaff.this, "Data Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}