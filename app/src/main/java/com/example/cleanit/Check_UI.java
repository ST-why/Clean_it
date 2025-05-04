package com.example.cleanit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

public class Check_UI extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CheckItemAdapter adapter;
    private CheckItemDatabaseHelper dbHelper;
    private EditText editText;
    private Button btnAdd, btnDone, btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_ui);

        recyclerView = findViewById(R.id.recyclerView);
        editText = findViewById(R.id.editText);
        btnAdd = findViewById(R.id.btnAdd);
        btnDone = findViewById(R.id.btnDone);
        btnSave = findViewById(R.id.btnSave);

        dbHelper = new CheckItemDatabaseHelper(this);
        List<CheckItem> itemList = dbHelper.getAllItems();

        adapter = new CheckItemAdapter(itemList, item -> {
            dbHelper.deleteItem(item.getId());
            adapter.removeItem(item);
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        btnAdd.setOnClickListener(v -> {
            editText.setVisibility(View.VISIBLE);
            btnDone.setVisibility(View.VISIBLE);
            editText.requestFocus();
        });

        btnDone.setOnClickListener(v -> {
            String text = editText.getText().toString().trim();
            if (!text.isEmpty()) {
                CheckItem newItem = new CheckItem(text, false);
                dbHelper.insertItem(newItem);
                adapter.addItem(newItem);
                editText.setText("");
                editText.setVisibility(View.GONE);
                btnDone.setVisibility(View.GONE);
            }
        });

        btnSave.setOnClickListener(v -> {
            dbHelper.clearAll();
            for (CheckItem item : adapter.getItems()) {
                dbHelper.insertItem(item);
            }
        });
    }
}
