package kelompok7.msibfinalproject2;

import static android.widget.Toast.makeText;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final  int REQUEST_CODE = 10;

    Button btnregister, btnlogin, btnadminlogin, btnstafflogin;
    private DbHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnregister = findViewById(R.id.btn_register);
        btnlogin = findViewById(R.id.btn_login);
        btnadminlogin = findViewById(R.id.btn_adminlogin);
        btnstafflogin = findViewById(R.id.btn_stafflogin);

        dbHelper = new DbHelper(this);
        android.database.sqlite.SQLiteDatabase database = dbHelper.getWritableDatabase();

        //Register
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterUser.class);
                startActivity(intent);
            }
        });
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginUser.class);
                startActivity(intent);
            }
        });
        btnadminlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginAdmin.class);
                startActivity(intent);
            }
        });
        btnstafflogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginStaff.class);
                startActivity(intent);
            }
        });
    }
}