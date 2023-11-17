package kelompok7.msibfinalproject2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterUser extends AppCompatActivity {
//    Button btnregisteruser;
//    EditText etnamauser, etemailuser, etpassworduser;
//    private AddDFP addDFP;
    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        dbHelper = new DbHelper(this);

        final EditText etnamauser = findViewById(R.id.et_name_user_rg);
        final EditText etemailuser = findViewById(R.id.et_email_user_rg);
        final EditText etpassworduser = findViewById(R.id.et_password_user_rg);

        Button btnregisteruser = findViewById(R.id.btn_createaccount);
        btnregisteruser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etnamauser.getText().toString();
                String email = etemailuser.getText().toString();
                String password = etpassworduser.getText().toString();

                if (!username.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
                    boolean success = dbHelper.addUser(username, email, password);
                    if (success) {
                        Toast.makeText(RegisterUser.this, "Registrasi berhasil", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterUser.this, LoginUser.class);
                        startActivity(intent);
                        finish();
                    }else {
                        Toast.makeText(RegisterUser.this, "Registrasi gagal", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RegisterUser.this, "Semua data harus diisi", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}