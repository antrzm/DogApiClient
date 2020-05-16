package ru.nsu.template.presentation.list;

import android.net.Uri;
import android.os.Build;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.net.URI;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import ru.nsu.template.TemplateApplication;
import ru.nsu.template.data.model.BreedModel;
import ru.nsu.template.data.model.ImageModel;
import ru.nsu.template.data.network.DogApi;
import ru.nsu.template.data.network.DogApiClient;
import ru.nsu.template.presentation.image.ImageViewModel;

public class ListViewModel extends ViewModel {

    public LiveData<List<String>> observeBreedsLiveData() {
        return subBreedLiveData;
    }

    public LiveData<String> observeUriLiveData() {
        return subUriLiveData;
    }

    private MutableLiveData<String> errorLiveData = new MutableLiveData<>("");

    private MutableLiveData<List<String>> subBreedLiveData = new MutableLiveData<>();
    private MutableLiveData<String> subUriLiveData = new MutableLiveData<>();

    public ListViewModel(List<String> subBreedList) {
        subBreedLiveData.setValue(subBreedList);
    }

    public void getImageUrl(String breed, String subBreed) {
        DogApi api = DogApiClient.getClient(TemplateApplication.getInstance()).create(DogApi.class);
        api.getSubBreedImage(breed, subBreed)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<ImageModel>() {
                    @Override
                    public void onSuccess(ImageModel uri) {
                        subUriLiveData.setValue(uri.getMessage());
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
