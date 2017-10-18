package fcm;

/**
 * Created by marco on 17/10/17.
 */

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class SNCFirebaseInstanceService extends FirebaseInstanceIdService {

    final String tokenPreferenceKey = "fcm_token";
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        /*SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.edit().putString(tokenPreferenceKey, FirebaseInstanceId.getInstance().getToken()).apply();*/
    }

    private void sendRegistrationToServer(String token) {

    }
}
