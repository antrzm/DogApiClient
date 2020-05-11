package ru.nsu.template.presentation.image;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.net.UnknownHostException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import ru.nsu.template.TemplateApplication;
import ru.nsu.template.data.model.BreedModel;
import ru.nsu.template.data.network.DogApi;
import ru.nsu.template.data.network.DogApiClient;

public class ImageViewModel extends ViewModel {

    public LiveData<String> observeErrorLiveData() {
        return errorLiveData;
    }

    private MutableLiveData<String> errorLiveData = new MutableLiveData<>("");

    public LiveData<String[]> observeSubBreedLiveData() {
        return subBreedLiveData;
    }

    private MutableLiveData<String[]> subBreedLiveData = new MutableLiveData<>();

    private DogApi api;

    public ImageViewModel() {
        api = DogApiClient.getClient(TemplateApplication.getInstance()).create(DogApi.class);
    }

    void searchSubBreedList(String breedName) {
        if (breedName.contains("-")) {
            String[] split = breedName.split("-");
            breedName = split[0];
        }
        //TODO: split by - and get first
        api.getSubBreeds(breedName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<BreedModel>() {
                    @Override
                    public void onSuccess(BreedModel breed) {
                        subBreedLiveData.setValue(breed.getMessage());
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof UnknownHostException) {
                            errorLiveData.setValue("No internet connection");
                        } else {
                            errorLiveData.setValue("Couldn't find dog");
                        }
                    }

                });
    }

}
