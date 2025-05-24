package com.example.uaspbbreisan;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    EditText etNis, etNama, etUsername, etPassword, etAlamat, etPhone, etKelas, etTglLahir;
    RadioGroup rgKelamin;
    Button btnRegister;
    String URL = "http://192.168.0.135/perpusapi/api/register.php"; // Ganti IP sesuai laragon

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Inisialisasi semua komponen
        etNis = findViewById(R.id.etNis);
        etNama = findViewById(R.id.etNama);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etAlamat = findViewById(R.id.etAlamat);
        etPhone = findViewById(R.id.etPhone);
        etKelas = findViewById(R.id.etKelas);
        etTglLahir = findViewById(R.id.etTglLahir);
        rgKelamin = findViewById(R.id.rgKelamin);
        btnRegister = findViewById(R.id.btnRegister);

        // Tanggal lahir picker
        etTglLahir.setOnClickListener(v -> showDatePicker());

        btnRegister.setOnClickListener(v -> {
            String nis = etNis.getText().toString().trim();
            String nama = etNama.getText().toString().trim();
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String alamat = etAlamat.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();
            String kelas = etKelas.getText().toString().trim();
            String tglLahir = etTglLahir.getText().toString().trim();

            int selectedId = rgKelamin.getCheckedRadioButtonId();
            if (selectedId == -1) {
                Toast.makeText(this, "Pilih jenis kelamin", Toast.LENGTH_SHORT).show();
                return;
            }
            RadioButton rbSelected = findViewById(selectedId);
            String kelamin = rbSelected.getText().toString();

            // Validasi
            if (nis.isEmpty() || nama.isEmpty() || username.isEmpty() || password.isEmpty() ||
                    alamat.isEmpty() || phone.isEmpty() || kelas.isEmpty() || tglLahir.isEmpty()) {
                Toast.makeText(this, "Semua field wajib diisi", Toast.LENGTH_SHORT).show();
                return;
            }

            register(nis, nama, username, password, alamat, phone, kelas, kelamin, tglLahir);
        });
    }

    private void showDatePicker() {
        final Calendar c = Calendar.getInstance();
        int y = c.get(Calendar.YEAR);
        int m = c.get(Calendar.MONTH);
        int d = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            String tgl = year + "-" + (month + 1) + "-" + dayOfMonth;
            etTglLahir.setText(tgl);
        }, y, m, d);
        dpd.show();
    }

    private void register(String nis, String nama, String username, String password, String alamat, String phone, String kelas, String kelamin, String tglLahir) {
        StringRequest request = new StringRequest(Request.Method.POST, URL,
                response -> {
                    Toast.makeText(this, response, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, LoginActivity.class));
                    finish();
                },
                error -> Toast.makeText(this, "Error: " + error.toString(), Toast.LENGTH_SHORT).show()
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> param = new HashMap<>();
                param.put("nis", nis);
                param.put("nama", nama);
                param.put("username", username);
                param.put("password", password);
                param.put("alamat", alamat);
                param.put("phone", phone);
                param.put("kelas", kelas);
                param.put("kelamin", kelamin);
                param.put("tgl_lahir", tglLahir);
                return param;
            }
        };

        Volley.newRequestQueue(this).add(request);
    }
}
