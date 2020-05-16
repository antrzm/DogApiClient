package ru.nsu.template.presentation.image;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import ru.nsu.template.R;
import ru.nsu.template.presentation.list.ListActivity;
import ru.nsu.template.presentation.start.StartActivity;

public class ImageActivity extends AppCompatActivity {

    public static String IMAGE_KEY = "image_key";
    public static String IMAGE_NAME_KEY = "image_name_key";

    private Button bBack;
    private Button bList;
    private TextView tvBreedName;
    private Context context;

    private ImageViewModel viewModel;

    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        viewModel = ViewModelProviders.of(this).get(ImageViewModel.class);

        bBack = findViewById(R.id.buttonBack);
        bList = findViewById(R.id.buttonList);
        tvBreedName = findViewById(R.id.tvBreedName);

        Bundle args = getIntent().getExtras();
        String uri = args.getString(IMAGE_KEY);
        final String breed = args.getString(IMAGE_NAME_KEY);

        if (breed != null && !breed.equals("")) {
            tvBreedName.setText(breed);
            viewModel.searchSubBreedList(breed);
        }

        bBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ImageActivity.this, StartActivity.class);
                startActivity(intent);
            }
        });

        bList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openList(viewModel.observeSubBreedLiveData().getValue());
            }
        });

        viewModel.observeSubBreedLiveData().observe(
                this, new Observer<List<String>>() {
                    @Override
                    public void onChanged(List<String> subBreeds) {
                        if (subBreeds != null && subBreeds.size() > 0) {
                            bList.setEnabled(true);
                        }
                    }
                }
        );

        context = this;

        image = findViewById(R.id.ivPhoto);

        Glide.with(context)
                .load(Uri.parse(uri))
                .into(image);

    }

    private void openList(ArrayList<String> subBreeds) {
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(ListActivity.LIST_KEY, subBreeds);
        bundle.putString(ListActivity.BREED_KEY, String.valueOf(tvBreedName.getText()).split("-")[0]);

        Intent intent = new Intent(ImageActivity.this, ListActivity.class);
        intent.putExtras(bundle);

        startActivity(intent);
    }


}
