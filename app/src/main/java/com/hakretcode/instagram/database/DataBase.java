package com.hakretcode.instagram.database;

import android.os.Looper;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class DataBase {
    private static final List<User> users = new ArrayList<>();

    public static boolean auth(String email, String pass) {
        if (Looper.myLooper() == Looper.getMainLooper())
            throw new IllegalThreadStateException("The task must to be executed in parallel task");
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Task<AuthResult> authResultTask = FirebaseAuth.getInstance().signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(result -> countDownLatch.countDown());
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return authResultTask.isSuccessful();
    }

    public static boolean isEmailAvailableForRegister(String user) {
        return true;
    }

    public static void add(String email, String name, String pass) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, pass)
                .addOnSuccessListener(result -> users.add(new User(result.getUser().getUid(), name, email)));
    }

}
