package ru.nsu.template.presentation.image;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import ru.nsu.template.presentation.list.ListViewModel;

public class ListViewModelFactory implements ViewModelProvider.Factory {
    private List<String> breed;

    public ListViewModelFactory(List<String> breed) {
        this.breed = breed;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ListViewModel(breed);
    }
}
