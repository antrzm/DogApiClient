package ru.nsu.template.presentation.list;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import ru.nsu.template.R;

public class ListActivity extends AppCompatActivity {

    public static String LIST_KEY = "image_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
    }
}
