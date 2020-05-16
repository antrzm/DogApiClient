package ru.nsu.template.presentation.list;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class ListViewModel extends ViewModel {

    public LiveData<List<String>> observeBreedsLiveData() {
        return subBreedLiveData;
    }

    private MutableLiveData<String> errorLiveData = new MutableLiveData<>("");

    private MutableLiveData<List<String>> subBreedLiveData = new MutableLiveData<>();

    public ListViewModel(List<String> subBreedList) {
        subBreedLiveData.setValue(subBreedList);
    }

}
