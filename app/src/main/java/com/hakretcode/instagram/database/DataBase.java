package com.hakretcode.instagram.database;

import android.os.Looper;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.net.ConnectException;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

public class DataBase {
    public static boolean auth(String email, String pass) throws ConnectException {
        CountDownLatch countDownLatch = getCountDownLatch();
        Task<AuthResult> authResultTask = FirebaseAuth.getInstance().signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(result -> countDownLatch.countDown());
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        boolean result = authResultTask.isSuccessful();
        if (result) return true;
        throw new ConnectException("Connection error");
    }

    public static boolean isEmailAvailableForRegister(String user) throws ConnectException {
        CountDownLatch countDownLatch = getCountDownLatch();
        final SignInMethodQueryResult[] resultContainer = new SignInMethodQueryResult[1];
        FirebaseAuth.getInstance().fetchSignInMethodsForEmail(user)
                .addOnCompleteListener(response -> {
                     resultContainer[0] = response.isSuccessful() ? response.getResult() : null;
                    countDownLatch.countDown();
                });
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        var result = resultContainer[0];
        if (result != null) {List<String> methods = result.getSignInMethods();
        return methods == null || methods.isEmpty();}
        else throw new ConnectException("Connection error");
    }

    public static void add(String email, String username, String name, String pass) throws ConnectException {
        CountDownLatch countDownLatch = getCountDownLatch();
        ConnectException[] exceptionContainer = {null};
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(response -> {
                    if (response.isSuccessful()) FirebaseFirestore.getInstance().collection("users")
                            .document(response.getResult().getUser().getUid())
                            .set(new User(username, name, email));
                    else exceptionContainer[0] = new ConnectException("Connection error");
                    countDownLatch.countDown();
                });
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        var exception = exceptionContainer[0];
        if (exception != null) throw exception;
    }

    public static boolean isUserAvailable(String username) throws ConnectException {
        final boolean[] available = {true};
        CountDownLatch countDownLatch = getCountDownLatch();
        final ConnectException[] exceptionContainer = {null};
        FirebaseFirestore.getInstance().collection("users").get()
                .addOnCompleteListener(response -> {
                    if (response.isSuccessful()) {
                        QuerySnapshot result = response.getResult();
                        if (result != null) for (DocumentSnapshot document : result.getDocuments())
                            if (document.getString("username").equalsIgnoreCase(username)) {
                                available[0] = false;
                                break;
                            }
                    } else exceptionContainer[0] = new ConnectException("Connection error");
                    countDownLatch.countDown();
                });
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        final ConnectException exception = exceptionContainer[0];
        if (exception != null) throw exception;
        return available[0];
    }

    private static CountDownLatch getCountDownLatch() {
        if (Looper.myLooper() == Looper.getMainLooper())
            throw new IllegalThreadStateException("The task must to be executed in parallel task");
        return new CountDownLatch(1);
    }
}
