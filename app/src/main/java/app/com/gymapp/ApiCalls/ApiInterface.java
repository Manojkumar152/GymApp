package app.com.gymapp.ApiCalls;

import app.com.gymapp.Models.Login;
import app.com.gymapp.Models.RegistrationDetails;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

    @FormUrlEncoded
    @POST(Urls.URL_LOGIN)
    Call<Login> loginUser(@Field("username") String username, @Field("password") String password);

    @GET(Urls.FETCH_REGISTRATION_DETAILS)
    Call<RegistrationDetails> getRegistrationDetails();

   /* @FormUrlEncoded
    @POST(Urls.URL_LOGIN)
    Call<RegistrationDetails> RegisterDetail();
*/
}
