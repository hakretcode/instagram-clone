package com.hakretcode.instagram.database;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hakretcode.instagram.initial.factory.Presenter;

import java.io.FileNotFoundException;
import java.util.List;

public class UserCrud implements UserDao {
    private final Presenter presenter;

    public UserCrud(Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void auth(String email, String pass) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(result -> presenter.onComplete())
                .addOnSuccessListener(result -> presenter.onFinish(result.getUser() != null))
                .addOnFailureListener(presenter::onFailure)
                .addOnCanceledListener(() -> presenter.onFailure(new InterruptedException("Connection error")));
    }

    @Override
    public void isEmailAvailableForRegister(String user) {
        FirebaseAuth.getInstance().fetchSignInMethodsForEmail(user)
                .addOnCompleteListener(result -> presenter.onComplete())
                .addOnSuccessListener(result -> {
                    List<String> methods = result.getSignInMethods();
                    presenter.onFinish(methods == null || methods.isEmpty());
                })
                .addOnFailureListener(presenter::onFailure)
                .addOnCanceledListener(() -> presenter.onFailure(new InterruptedException("Connection error")));
    }

    @Override
    public void isUserAvailable(String username) {
        FirebaseFirestore.getInstance().collection("users").get()
                .addOnSuccessListener(response -> {
                    for (DocumentSnapshot document : response.getDocuments())
                        if (document.getString("username").equalsIgnoreCase(username)) {
                            presenter.onFailure(new FileNotFoundException("This username isn't available. Please try another"));
                            return;
                        }
                    presenter.onFinish(true);
                })
                .addOnCompleteListener(result -> presenter.onComplete())
                .addOnFailureListener(presenter::onFailure)
                .addOnCanceledListener(() -> presenter.onFailure(new InterruptedException("Connection error")));
    }

    @Override
    public void add(String email, String username, String name, String pass) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, pass)
                .addOnSuccessListener(response -> {
                    FirebaseFirestore.getInstance().collection("users")
                            .document(response.getUser().getUid())
                            .set(new User(username, name, email));
                    auth(email, pass);
                }).addOnCompleteListener(result -> presenter.onComplete())
                .addOnFailureListener(presenter::onFailure);
    }
}
