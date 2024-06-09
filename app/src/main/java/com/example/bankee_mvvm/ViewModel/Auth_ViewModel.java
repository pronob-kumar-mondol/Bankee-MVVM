package com.example.bankee_mvvm.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Auth_ViewModel extends ViewModel {

    private FirebaseAuth auth;
    private MutableLiveData<FirebaseUser> userLiveData;
    private MutableLiveData<Boolean> authStatus;

    public Auth_ViewModel() {
        auth = FirebaseAuth.getInstance();
        userLiveData = new MutableLiveData<>();
        authStatus = new MutableLiveData<>();
        if (auth.getCurrentUser() != null) {
            userLiveData.postValue(auth.getCurrentUser());
        } else {
            userLiveData.postValue(null);
        }
    }

    public LiveData<FirebaseUser> getUserLiveData() {
        return userLiveData;
    }

    public LiveData<Boolean> getAuthStatus() {
        return authStatus;
    }

    public void login(String email, String password) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        userLiveData.postValue(auth.getCurrentUser());
                        authStatus.postValue(true);
                    } else {
                        authStatus.postValue(false);
                    }
                });
    }

    public void signup(String email, String password, String fullName) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = auth.getCurrentUser();
                        if (user != null) {
                            userLiveData.postValue(user);
                            authStatus.postValue(true);
                        }
                    } else {
                        authStatus.postValue(false);
                    }
                });
    }

    public void logout() {
        auth.signOut();
        userLiveData.postValue(null);
        authStatus.postValue(false);
    }


}
