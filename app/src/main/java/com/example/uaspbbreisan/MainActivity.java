package com.example.uaspbbreisan;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<BukuModel> bukuList = new ArrayList<>();
    String URL = "http://192.168.0.135/perpusapi/api/list_buku.php"; // Ganti sesuai IP kamu

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadBuku();
    }

    private void loadBuku() {
        StringRequest request = new StringRequest(Request.Method.GET, URL,
                response -> {
                    try {
                        JSONArray array = new JSONArray(response);
                        bukuList.clear();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject obj = array.getJSONObject(i);
                            bukuList.add(new BukuModel(
                                    obj.getInt("book_id"),
                                    obj.getString("title"),
                                    obj.getString("author"),
                                    obj.getInt("stock")
                            ));
                        }
                        recyclerView.setAdapter(new BukuAdapter(this, bukuList));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Gagal parsing data", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(this, "Gagal load data", Toast.LENGTH_SHORT).show()
        );

        Volley.newRequestQueue(this).add(request);
    }
}
