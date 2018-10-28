package com.smartgenlabs.princequest;

import android.content.Context;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

/**
 * Created by Pankaj Vaghela on 01-07-2018.
 */
public class AppUtils {


    public static FirebaseUser currentUser;
    public static XfwUser currentUserLocal;

    public static void setCurrentUserListener(final Context context, String uid) {
        final DocumentReference userRef = FirebaseFirestore.getInstance().collection("users").document(uid);

        userRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot document, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    M.showNoticeAlert(context, "Error!", "Some error occurred while fetching user data. Please try after some time");

                } else if (document.exists()) {
                    XfwUser curUserLocal = document.toObject(XfwUser.class);
                    AppUtils.currentUserLocal = curUserLocal;
                    AppUtils.currentUserLocal.setUid(document.getId());
                    //Log.d(TAG, "DocumentSnapshot data: " + document.getData());

                    if (curUserLocal.isAuthority()) {
                        SharedPreference.set(context, C.IS_AUTHORITY, true);
                        FirebaseMessaging.getInstance().subscribeToTopic(C.FCM_TOPIC_AUTHORITY);
                    } else SharedPreference.set(context, C.IS_AUTHORITY, false);

                    if (curUserLocal.isAdmin()) {
                        SharedPreference.set(context, C.IS_ADMIN, true);
                        FirebaseMessaging.getInstance().subscribeToTopic(C.FCM_TOPIC_ADMIN);
                    } else SharedPreference.set(context, C.IS_ADMIN, false);

                    if (document.contains(C.TEXT_DISPLAY_NAME))
                        SharedPreference.set(context, C.TEXT_DISPLAY_NAME, document.getString(C.TEXT_DISPLAY_NAME));
                    if (!curUserLocal.getAndroid_fcm_token().equals(FirebaseInstanceId.getInstance().getToken())) {
                        Map<String, Object> fcmToken = new HashMap<>();
                        fcmToken.put(C.TEXT_FCM_TOKEN, FirebaseInstanceId.getInstance().getToken());
                        userRef.set(fcmToken, SetOptions.merge());
                    }
                } else {
                    //   Log.d("User data", "No User data found");
                }
            }
        });
    }


}
