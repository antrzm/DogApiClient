package ru.nsu.template.presentation.list;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ru.nsu.template.R;
import ru.nsu.template.data.model.BreedModel;
import ru.nsu.template.presentation.image.ListViewModelFactory;

public class ListActivity extends AppCompatActivity {

    public static String LIST_KEY = "list_key";
    public static String BREED_KEY = "breed_key";

    private RecyclerView rvRepos;
    private TextView tvBreedName;
    private ListAdapter adapter;

    private Context context;

    private ListViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        context = this;

        tvBreedName = findViewById(R.id.tvBreedName);
        rvRepos = findViewById(R.id.rvSubBreeds);
        adapter = new ListAdapter();

        Bundle args = getIntent().getExtras();
        final List<String> breedList = args.getStringArrayList(LIST_KEY);
        final String breed = args.getString(BREED_KEY);

        tvBreedName.setText(breed);
        adapter.setItems(breedList);

        initList();

        viewModel = ViewModelProviders.of(this,
                new ListViewModelFactory(breedList)).get(ListViewModel.class);
    }

    private void initList() {
        rvRepos.setLayoutManager(new LinearLayoutManager(this));
        rvRepos.setAdapter(adapter);
    }

}
