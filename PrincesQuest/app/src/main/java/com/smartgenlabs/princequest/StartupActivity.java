package com.smartgenlabs.princequest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.CustomEvent;
import com.crashlytics.android.core.CrashlyticsCore;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;
import java.util.Map;

import io.fabric.sdk.android.Fabric;

public class StartupActivity extends AppCompatActivity implements NetworkStateReceiver.NetworkStateReceiverListener {

    public static final String TAG = "startActivity";

    SharedPreference SP;
    DocumentReference userRef;
    NetworkStateReceiver networkStateReceiver;

//    XfwUser curUserLocal;

    TextInputEditText login_username_et, login_pwd_et;
    Button login_btn;

    ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_startup);


        mProgress = new ProgressDialog(this);
        mProgress.setTitle("Please Wait...");

        fabricLogEvent();
        startLoginFlow();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
           setUpUser();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        networkStateReceiver = new NetworkStateReceiver(this);
        networkStateReceiver.addListener(this);
        this.registerReceiver(networkStateReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    protected void onPause() {
        super.onPause();
        networkStateReceiver.removeListener(this);
        this.unregisterReceiver(networkStateReceiver);
    }


    CallbackManager mCallbackManager;
    private FirebaseAuth mAuth;

    private void startLoginFlow() {
        mAuth = FirebaseAuth.getInstance();

        login_username_et = findViewById(R.id.login_username_et);
        login_pwd_et = findViewById(R.id.login_pin_et);
        login_btn = findViewById(R.id.login_btn);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name, pass;
                name = login_username_et.getText().toString();
                pass = login_pwd_et.getText().toString();
                final String pwd = pass;
                if (TextUtils.isEmpty(name) || name.length() < 8) {
                    login_username_et.setError("username is too short not entered");
                } else if (TextUtils.isEmpty(pass)) {
                    login_pwd_et.setError("This is required");
                } else {
                    mProgress.setTitle("Logging In");
                    mProgress.setMessage("Please Wait...");
                    mProgress.setCanceledOnTouchOutside(false);
                    mProgress.show();

                    FirebaseFirestore.getInstance().collection(C.TEXT_USERS).whereEqualTo("username", name).get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                if (!task.getResult().isEmpty()) {
                                    for (QueryDocumentSnapshot dataSnapshot1 : task.getResult()) {
                                        curUserLocal = dataSnapshot1.toObject(XfwUser.class);
                                    }

                                    if (curUserLocal != null) {
                                        mAuth.signInWithEmailAndPassword(curUserLocal.fetchXfwEmail(), pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if (task.isSuccessful()) {
                                                    setUpUser();
                                                } else {
                                                    mProgress.dismiss();
                                                    Toast.makeText(getApplicationContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    } else {
                                        mProgress.dismiss();
                                        Toast.makeText(StartupActivity.this, "Username or password Invalid!", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    mProgress.dismiss();
                                    Toast.makeText(StartupActivity.this, "No data found for username provided", Toast.LENGTH_SHORT).show();
                                }
                            }else mProgress.dismiss();

                        }
                    });

                }
            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            setUpUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(StartupActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    private void setUpUser() {
        final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        AppUtils.currentUser = currentUser;
        curUserLocal = new XfwUser();
        SP = new SharedPreference(this);

        FirebaseFirestore.getInstance().collection("users").document(currentUser.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().exists()) {
                                curUserLocal = task.getResult().toObject(XfwUser.class);
                                if(curUserLocal.isActivated()){
                                    userRegular(task.getResult());
                                }else{
                                    AppUtils.currentUserLocal = curUserLocal;
                                    userNew();
                                }
                            } else {
                                userNew();
                                Log.d(TAG, "No User data found");
                            }
                        }
                    }
                });

    }

    private void userRegular(DocumentSnapshot document) {
            curUserLocal = document.toObject(XfwUser.class);
            AppUtils.currentUserLocal = curUserLocal;
            AppUtils.currentUserLocal.setUid(document.getId());
            //Log.d(TAG, "DocumentSnapshot data: " + document.getData());

            if (curUserLocal.isAuthority()) {
                SP.set(C.IS_AUTHORITY, true);
                FirebaseMessaging.getInstance().subscribeToTopic(C.FCM_TOPIC_AUTHORITY);
            } else SP.set(C.IS_AUTHORITY, false);

            if (curUserLocal.isAdmin()) {
                SP.set(C.IS_ADMIN, true);
                FirebaseMessaging.getInstance().subscribeToTopic(C.FCM_TOPIC_ADMIN);
            } else SP.set(C.IS_ADMIN, false);


            if (document.contains(C.TEXT_DISPLAY_NAME))
                SP.set(C.TEXT_DISPLAY_NAME, document.getString(C.TEXT_DISPLAY_NAME));
            SP.set(C.TEXT_UID, document.getId());

            if(curUserLocal.getAndroid_fcm_token()!=null){
                if (!curUserLocal.getAndroid_fcm_token().equals(FirebaseInstanceId.getInstance().getToken()))
                    updateFcmToken();
            }else{
                updateFcmToken();
            }

            sendToHome();
    }

    private void userNew(){
        mProgress.dismiss();
        startActivity(new Intent(StartupActivity.this, SetupUserAccount.class));
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }

    private void updateFcmToken() {
        Map<String, Object> fcmToken = new HashMap<>();
        fcmToken.put(C.TEXT_FCM_TOKEN, FirebaseInstanceId.getInstance().getToken());
        if(mAuth.getCurrentUser()!=null) {
            userRef = FirebaseFirestore.getInstance().collection(C.TEXT_USERS).document(mAuth.getCurrentUser().getUid());
            userRef.set(fcmToken, SetOptions.merge());
        }
    }

    private void sendToHome() {
        mProgress.dismiss();
        startActivity(new Intent(StartupActivity.this, HomeActivity.class));
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }


    private void fabricLogEvent() {
        Answers.getInstance().logCustom(new CustomEvent("App Opened")
                .putCustomAttribute("opened", 1)
                .putCustomAttribute("device_android_version", String.valueOf(Build.VERSION.SDK_INT))
                .putCustomAttribute("app_version_code", String.valueOf(BuildConfig.VERSION_CODE))
                .putCustomAttribute("app_version_name", String.valueOf(BuildConfig.VERSION_NAME)));
    }

    //******** NETWORK METHODS
    @Override
    public void onNetworkAvailable() {
        M.showNoNetworkAlert(this, false);
    }

    @Override
    public void onNetworkUnavailable() {
        M.showNoNetworkAlert(this, true);
    }
}
