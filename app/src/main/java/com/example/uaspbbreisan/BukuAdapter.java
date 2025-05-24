
package com.example.uaspbbreisan;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.RequestQueue;


import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BukuAdapter extends RecyclerView.Adapter<BukuAdapter.ViewHolder> {

    Context context;
    List<BukuModel> list;

    public BukuAdapter(Context context, List<BukuModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_buku, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BukuModel buku = list.get(position);
        holder.tvJudul.setText(buku.getTitle());
        holder.tvPenulis.setText(buku.getAuthor());
        holder.tvStok.setText("Stok: " + buku.getStock());

        holder.btnReservasi.setOnClickListener(v -> {
            Intent intent = new Intent(context, ReservasiActivity.class);
            intent.putExtra("book_id", buku.getBookId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvJudul, tvPenulis, tvStok;
        Button btnReservasi;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvJudul = itemView.findViewById(R.id.tvJudul);
            tvPenulis = itemView.findViewById(R.id.tvPenulis);
            tvStok = itemView.findViewById(R.id.tvStok);
            btnReservasi = itemView.findViewById(R.id.btnReservasi);
        }
    }
}
