package com.example.cleanit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class Check_UI extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CheckItemAdapter adapter;
    private EditText editText;
    private Button btnAdd, btnDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_ui);

        recyclerView = findViewById(R.id.recyclerView);
        editText = findViewById(R.id.editText);
        btnAdd = findViewById(R.id.btnAdd);
        btnDone = findViewById(R.id.btnDone);

        adapter = new CheckItemAdapter(new ArrayList<>());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setVisibility(View.VISIBLE);
                btnDone.setVisibility(View.VISIBLE);
                editText.requestFocus();
            }
        });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editText.getText().toString().trim();
                if (!text.isEmpty()) {
                    adapter.addItem(text);
                    editText.setText("");
                    editText.setVisibility(View.GONE);
                    btnDone.setVisibility(View.GONE);
                }
            }
        });
    }
}
