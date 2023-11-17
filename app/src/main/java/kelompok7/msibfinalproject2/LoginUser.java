package kelompok7.msibfinalproject2;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginUser extends AppCompatActivity {

    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);

        dbHelper = new DbHelper(this);

        final EditText etnama = findViewById(R.id.et_name_user);
        final EditText etpassword = findViewById(R.id.et_password_user);

        Button btnloginuser = findViewById(R.id.btn_loginuser);
        btnloginuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etnama.getText().toString().trim();
                String password = etpassword.getText().toString().trim();

                if (!username.isEmpty() && !password.isEmpty()) {
                    boolean success = dbHelper.checkUser(username, password);

                    if (success) {
                        Toast.makeText(LoginUser.this, "Login berhasil", Toast.LENGTH_SHORT).show();
                        Intent intent;
                        intent = new Intent(LoginUser.this, HalamanMenu.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginUser.this, "Username atau password salah", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginUser.this, "Semua data harus diisi", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}