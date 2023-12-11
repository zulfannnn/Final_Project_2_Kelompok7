package kelompok7.msibfinalproject2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class AboutPage extends AppCompatActivity {
    ImageView imageViewGoogle, imageViewLinkedIn, imageViewFb, imageViewIg, imageViewTw, imageViewGit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_page);

        imageViewGoogle = findViewById(R.id.imageViewGoogle);
        imageViewLinkedIn = findViewById(R.id.imageViewLinkedIn);
        imageViewFb = findViewById(R.id.imageViewFb);
        imageViewIg = findViewById(R.id.imageViewIg);
        imageViewTw = findViewById(R.id.imageViewTw);
        imageViewGit = findViewById(R.id.imageViewGit);
        imageViewGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.kode.id/"));
                startActivity(intent);
            }
        });
        imageViewLinkedIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/school/hacktiv8/"));
                startActivity(intent);
            }
        });
        imageViewFb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/hacktiv8"));
                startActivity(intent);
            }
        });
        imageViewIg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/hacktiv8id/"));
                startActivity(intent);
            }
        });
        imageViewTw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/hacktiv8id"));
                startActivity(intent);
            }
        });
        imageViewGit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/hacktiv8"));
                startActivity(intent);
            }
        });
    }
}