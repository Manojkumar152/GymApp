package app.com.gymapp.ApiCalls;

import app.com.gymapp.Models.EditProfile;
import app.com.gymapp.Models.Login;
import app.com.gymapp.Models.ProfileData;
import app.com.gymapp.Models.RegistrationDetails;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
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

    @FormUrlEncoded
    @POST(Urls.GET_PROFILE)
    Call<ProfileData> getUserProfile(@Field("id") String id );

    @FormUrlEncoded
    @POST(Urls.UPDATE_USER_PROFILE)
    Call<ResponseBody> updateUserProfile(@Field("id") String s, @Field("username") String username, @Field("first_name") String fname, @Field("last_name")String lname, @Field("address") String address, @Field("email")String email, @Field("mobile")String mobile, @Field("password") String id);

}
