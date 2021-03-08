package com.example.myapplication.fragments;

import android.graphics.Paint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {


    FirebaseFirestore firebaseFirestore;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        firebaseFirestore = FirebaseFirestore.getInstance();

        init();

        return view;
    }

    private void init() {
        firebaseFirestore = FirebaseFirestore.getInstance();
        List<String> uid = new ArrayList<>();


        Task<QuerySnapshot> collectionReference = firebaseFirestore.collection("Users")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(QueryDocumentSnapshot querySnapshot:queryDocumentSnapshots){
                            Log.v("Data Get", String.valueOf(querySnapshot));
                            Log.v("Data MGet", String.valueOf(querySnapshot.get("Address")));
                            uid.add(String.valueOf(querySnapshot.get("uid")));
                            Log.v("list", String.valueOf(uid));

                            getDatabaseData(String.valueOf(querySnapshot.get("uid")));

                        }
                    }
                });




    }
    private void getDatabaseData(String uid) {
        Task<QuerySnapshot> collectionReference = firebaseFirestore.collection("Users")
                .document(uid)
                .collection("user_products_details")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot querySnapshot:queryDocumentSnapshots) {
                            Log.v("Collection", String.valueOf(querySnapshot));
                            Log.v("Collection1", querySnapshot.get("product_name")+"and"+querySnapshot.get("product_price"));
                            String product_name = String.valueOf(querySnapshot.get("product_name"));
                            String product_price = String.valueOf(querySnapshot.get("product_price"));




                        }

                    }
                });
    }
}