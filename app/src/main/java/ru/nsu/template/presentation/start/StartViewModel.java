package ru.nsu.template.presentation.start;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.net.UnknownHostException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import ru.nsu.template.TemplateApplication;
import ru.nsu.template.data.model.ImageModel;
import ru.nsu.template.data.network.DogApi;
import ru.nsu.template.data.network.DogApiClient;

public class StartViewModel extends ViewModel {

    public LiveData<Boolean> observeSearchButtonEnabled() {
        return searchButtonEnabled;
    }

    private MutableLiveData<Boolean> searchButtonEnabled = new MutableLiveData<>(false);

    public LiveData<String> observeErrorLiveData() {
        return errorLiveData;
    }

    private MutableLiveData<String> errorLiveData = new MutableLiveData<>("");

    public LiveData<String> observeImageUriLiveData() {
        return imageUriLiveData;
    }

    private MutableLiveData<String> imageUriLiveData = new MutableLiveData<>();

    private DogApi api;

    public StartViewModel() {
        api = DogApiClient.getClient(TemplateApplication.getInstance()).create(DogApi.class);
    }

    public void validateUsername(String username) {
        searchButtonEnabled.setValue(!username.equals(""));
    }

    void getRandom() {
        api.getRandomPhoto()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<ImageModel>() {
                    @Override
                    public void onSuccess(ImageModel randomImage) {
                        imageUriLiveData.setValue(randomImage.getMessage());
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof UnknownHostException) {
                            errorLiveData.setValue("No internet connection");
                        } else {
                            errorLiveData.setValue(e.getMessage());
                        }
                    }
                });
    }

    void searchBreed(final String breedName) {
        api.getBreedPhoto(breedName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<ImageModel>() {
                    @Override
                    public void onSuccess(ImageModel breedImage) {
                        if (breedImage.getStatus().equals("success")) {
                            imageUriLiveData.setValue(breedImage.getMessage());
                        } else {
                            errorLiveData.setValue(breedImage.getMessage());
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
