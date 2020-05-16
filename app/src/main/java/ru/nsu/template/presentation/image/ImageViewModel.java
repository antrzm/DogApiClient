package ru.nsu.template.presentation.image;

import android.os.Build;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.lang.reflect.Array;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public LiveData<ArrayList<String>> observeSubBreedLiveData() {
        return subBreedLiveData;
    }

    private MutableLiveData<ArrayList<String>> subBreedLiveData = new MutableLiveData<>();

    private DogApi api;

    public ImageViewModel() {
        api = DogApiClient.getClient(TemplateApplication.getInstance()).create(DogApi.class);
    }

    void searchSubBreedList(String breed) {
        if (breed.contains("-")) {
            final String[] breedName = breed.split("-");
            api.getSubBreeds(breedName[0])
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DisposableSingleObserver<BreedModel>() {
                        @Override
                        public void onSuccess(BreedModel breed) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                List<String> collect = breed.getMessage().stream()
                                        .map(i -> i = breedName[0] + "-" + i)
                                        .collect(Collectors.toList());
                                subBreedLiveData.setValue((ArrayList<String>) collect);
                            }
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

}
