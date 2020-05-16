package ru.nsu.template.presentation.list;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import ru.nsu.template.TemplateApplication;
import ru.nsu.template.data.model.BreedModel;
import ru.nsu.template.data.network.DogApi;
import ru.nsu.template.data.network.DogApiClient;

public class ListViewModel extends ViewModel {

    private List<String> breedName;
    private DogApi api;

    public LiveData<BreedModel> observeBreedsLiveData() {
        return subBreedLiveData;
    }

    public LiveData<String> observeErrorLiveData() {
        return errorLiveData;
    }

    private MutableLiveData<String> errorLiveData = new MutableLiveData<>("");

    private MutableLiveData<BreedModel> subBreedLiveData = new MutableLiveData<>();

    public ListViewModel(List<String> breed) {
        this.breedName = breed;

        api = DogApiClient.getClient(TemplateApplication.getInstance()).create(DogApi.class);

        BreedModel breedModel = new BreedModel();
        breedModel.setName(breedName);
        subBreedLiveData.setValue(breedModel);
    }

}
