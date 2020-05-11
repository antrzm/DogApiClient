package ru.nsu.template.data.network;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import ru.nsu.template.data.model.BreedModel;
import ru.nsu.template.data.model.ImageModel;

public interface DogApi {

    @GET("breed/{breedName}/images/random")
    Single<ImageModel> getBreedPhoto(@Path("breedName") String breedName);

    @GET("breeds/image/random")
    Single<ImageModel> getRandomPhoto();

    @GET("breed/{breedName}/list")
    Single<BreedModel> getSubBreeds(@Path("breedName") String breedName);

}
