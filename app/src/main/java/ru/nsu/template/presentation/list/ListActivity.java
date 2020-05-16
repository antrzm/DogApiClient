package ru.nsu.template.presentation.list;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import java.net.URI;
import java.util.List;

import ru.nsu.template.R;
import ru.nsu.template.presentation.image.ImageActivity;
import ru.nsu.template.presentation.image.ListViewModelFactory;

public class ListActivity extends AppCompatActivity {

    public static String LIST_KEY = "list_key";
    public static String BREED_KEY = "breed_key";
    public static String IMAGE_KEY = "image_key";

    private RecyclerView rvSubBreeds;
    private TextView tvBreedName;
    private ListAdapter adapter;

    private ListViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        tvBreedName = findViewById(R.id.tvBreedName);
        rvSubBreeds = findViewById(R.id.rvSubBreeds);
        adapter = new ListAdapter(this);

        Bundle args = getIntent().getExtras();
        if (args == null) {
            return;
        }

        final List<String> breedList = args.getStringArrayList(LIST_KEY);
        final String breed = args.getString(BREED_KEY);

        rvSubBreeds.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvSubBreeds.setAdapter(adapter);

        viewModel = ViewModelProviders.of(this,
                new ListViewModelFactory(breedList)).get(ListViewModel.class);

        viewModel.observeBreedsLiveData().observe(this, strings -> adapter.setItems(breedList));

        if (breed != null) {
            tvBreedName.setText(breed);
        }
    }

    public TextView getTvBreedName() {
        return tvBreedName;
    }

    public void openOnClick(String breed, String subBreed) {
        Intent intent = new Intent(ListActivity.this, ImageActivity.class);
        intent.putExtra(ImageActivity.IMAGE_NAME_KEY, breed+"-"+subBreed);
        viewModel.getImageUrl(breed, subBreed);

        viewModel.observeUriLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String uri) {
                intent.putExtra(IMAGE_KEY, uri);
                startActivity(intent);
            }
        });
    }

}
