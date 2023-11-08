package kelompok7.msibfinalproject2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginUser extends AppCompatActivity {
    Button btnloginuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);

        btnloginuser = findViewById(R.id.btn_loginuser);
        btnloginuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginUser.this, MenuUtama.class);
                startActivity(intent);
            }
        });
    }
}