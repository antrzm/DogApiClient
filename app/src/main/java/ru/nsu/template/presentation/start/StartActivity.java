package ru.nsu.template.presentation.start;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import ru.nsu.template.R;
import ru.nsu.template.presentation.image.ImageActivity;

public class StartActivity extends AppCompatActivity {
    StartViewModel viewModel;

    private Button bRandom;
    private Button bBreed;
    private EditText etBreed;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bRandom = findViewById(R.id.bRandom);
        bBreed = findViewById(R.id.bByBreed);
        etBreed = findViewById(R.id.etBreed);

        viewModel = ViewModelProviders.of(this).get(StartViewModel.class);
        context = this;

        viewModel.observeImageUriLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String image) {
                openImage(image);
            }
        });

        etBreed.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // empty
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.validateUsername(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // empty
            }
        });

        bRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.getRandom();
            }
        });

        viewModel.observeSearchButtonEnabled().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                bBreed.setEnabled(aBoolean);
            }
        });

        viewModel.observeErrorLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String error) {
                if (!error.equals("")) {
                    Toast.makeText(context, error, Toast.LENGTH_LONG).show();
                }
            }
        });

        bBreed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.searchBreed(etBreed.getText().toString());
            }
        });
    }

    private void openImage(String imageUri) {
        Bundle bundle = new Bundle();
        bundle.putString(ImageActivity.IMAGE_KEY, imageUri);
        bundle.putString(ImageActivity.IMAGE_NAME_KEY, getBreedFromURI(imageUri));
        bundle.putSerializable(ImageActivity.SUB_BREED_LIST_KEY, getBreedFromURI(imageUri));

        Intent intent = new Intent(StartActivity.this, ImageActivity.class);
        intent.putExtras(bundle);

        startActivity(intent);
    }

    private String getBreedFromURI(String uri) {
        List<String> split = Arrays.asList(uri.split("/"));
        int ind = split.indexOf("breeds");
        return split.get(++ind);
    }

}
