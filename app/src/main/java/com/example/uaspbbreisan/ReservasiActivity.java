package com.example.uaspbbreisan;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class ReservasiActivity extends AppCompatActivity {

    int bookId;
    String URL = "http://192.168.0.135/perpusapi/api/reservasi.php";
    TextView tvJudul, tvPenulis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservasi);

        tvJudul = findViewById(R.id.tvDetailJudul);
        tvPenulis = findViewById(R.id.tvDetailPenulis);
        Button btn = findViewById(R.id.btnKonfirmasiReservasi);

        // Ambil data dari intent
        bookId = getIntent().getIntExtra("book_id", -1);
        String judul = getIntent().getStringExtra("judul");
        String penulis = getIntent().getStringExtra("penulis");

        // Tampilkan ke TextView
        tvJudul.setText(judul);
        tvPenulis.setText(penulis);

        btn.setOnClickListener(v -> doReservasi());
    }

    private void doReservasi() {
        // Ambil member_id dari session
        SharedPreferences prefs = getSharedPreferences("session", MODE_PRIVATE);
        String memberId = prefs.getString("member_id", null);

        if (memberId == null) {
            Toast.makeText(this, "Session login tidak ditemukan", Toast.LENGTH_SHORT).show();
            return;
        }

        StringRequest request = new StringRequest(Request.Method.POST, URL,
                response -> Toast.makeText(this, "Reservasi berhasil", Toast.LENGTH_SHORT).show(),
                error -> Toast.makeText(this, "Error: " + error.toString(), Toast.LENGTH_SHORT).show()
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> param = new HashMap<>();
                param.put("member_id", memberId);
                param.put("book_id", String.valueOf(bookId));
                return param;
            }
        };

        Volley.newRequestQueue(this).add(request);
    }
}
