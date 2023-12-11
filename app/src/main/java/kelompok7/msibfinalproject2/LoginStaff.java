package kelompok7.msibfinalproject2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginStaff extends AppCompatActivity {
    private DbHelper dbHelper;
    Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_staff);
        dbHelper = new DbHelper(this);

        final EditText ETname = findViewById(R.id.editName);
        final EditText ETpassword = findViewById(R.id.editPassword);
        login =findViewById(R.id.btn_login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = ETname.getText().toString().trim();
                String password = ETpassword.getText().toString().trim();
                if (!name.isEmpty() && !password.isEmpty()) {
                    boolean success = dbHelper.cekLoginStaff(name, password);
                    if (success) {
                        Toast.makeText(LoginStaff.this, "Login berhasil", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginStaff.this, HalamanStaff.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginStaff.this, "Username atau password salah", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginStaff.this, "Semua data harus diisi", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}