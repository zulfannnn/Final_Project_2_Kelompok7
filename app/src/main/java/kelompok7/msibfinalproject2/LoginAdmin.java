package kelompok7.msibfinalproject2;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginAdmin extends AppCompatActivity {
    private static final int REQUEST_CODE = 10;
    EditText name, password;
    Button btnLogin;
    DbHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);

        db = new DbHelper(this);
        name = findViewById(R.id.nameEdit);
        password = findViewById(R.id.passwordEdit);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameKey = name.getText().toString();
                String passwordKey = password.getText().toString();
                Boolean res = db.User(nameKey, passwordKey);
                if (nameKey.equals("admin") && passwordKey.equals("admin")) {
                    saveUserRole("admin");
                    Toast.makeText(getApplicationContext(), "Selamat Datang Admin " + nameKey,
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginAdmin.this, HalamanAdmin.class);
                    String value = name.getText().toString();
                    intent.putExtra("yourkey1", value);
                    startActivityForResult(intent, REQUEST_CODE);
                } else if (res) {
                    saveUserRole("user");
                    Intent moveToHome = new Intent(LoginAdmin.this, RegisterStaff.class);
                    String value = name.getText().toString();
                    moveToHome.putExtra("userkey1", value);
                    startActivityForResult(moveToHome, REQUEST_CODE);
                    Toast.makeText(getApplicationContext(), "Selamat Datang User " + nameKey, Toast.LENGTH_SHORT).show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginAdmin.this);
                    builder.setMessage("Username atau Password Anda salah!")
                            .setNegativeButton("Retry", null).create().show();
                }
            }
        });
    }
    private void saveUserRole(String role) {
        SharedPreferences preferences = getSharedPreferences("user_preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("user_role", role);
        editor.apply();
    }
}