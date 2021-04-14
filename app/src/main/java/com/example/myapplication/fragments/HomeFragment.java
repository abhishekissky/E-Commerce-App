package com.example.myapplication.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.myapplication.Activities.ProductViewListActivity;
import com.example.myapplication.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;


public class HomeFragment extends Fragment  {


    FirebaseFirestore firebaseFirestore;
    CardView Fruits,vegetableCard,Snakes,Bakery,Beverages,Grain;
    List<String> uid;
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        firebaseFirestore = FirebaseFirestore.getInstance();

        Fruits = view.findViewById(R.id.Fruits);
        vegetableCard = view.findViewById(R.id.vegetableCard);
        Snakes = view.findViewById(R.id.Snakes);
        Bakery = view.findViewById(R.id.Bakery);
        Beverages = view.findViewById(R.id.Beverages);
        Grain = view.findViewById(R.id.Grain);

        progressBar = view.findViewById(R.id.progressBar);
//        progressBar.setVisibility(View.VISIBLE);
        init();
        categoriesClick();
        SnakesClick();
        fruitClick();
        bakeryClick();
        beveragesClick();
        grainClick();

        return view;
    }

    private void grainClick() {
        Grain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),ProductViewListActivity.class);
                intent.putExtra("category","grain");
                startActivity(intent);
            }
        });
    }

    private void beveragesClick() {
        Beverages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),ProductViewListActivity.class);
                intent.putExtra("category","beverage");
                startActivity(intent);
            }
        });
    }

    private void bakeryClick() {
        Bakery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),ProductViewListActivity.class);
                intent.putExtra("category","bakery");
                startActivity(intent);
            }
        });
    }

    private void fruitClick() {
        Fruits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),ProductViewListActivity.class);
                intent.putExtra("category","fruit");
                startActivity(intent);
            }
        });

    }

    private void SnakesClick() {
        Snakes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),ProductViewListActivity.class);
                intent.putExtra("category","snake");
                startActivity(intent);
            }
        });
    }

    private void categoriesClick() {

        vegetableCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),ProductViewListActivity.class);
                intent.putExtra("category","vegetable");
                startActivity(intent);
            }
        });
    }

    private void init() {
        firebaseFirestore = FirebaseFirestore.getInstance();
//        uid = new ArrayList<>();

//        Task<QuerySnapshot> collectionReference = firebaseFirestore.collection("Users")
//                .get()
//                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                    @Override
//                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                        for(QueryDocumentSnapshot querySnapshot:queryDocumentSnapshots){
////                            Log.v("Data Get", String.valueOf(querySnapshot));
////                            Log.v("Data MGet", String.valueOf(querySnapshot.get("Address")));
//                            uid.add(String.valueOf(querySnapshot.get("uid")));
//
//                            Log.v("list", String.valueOf(uid));
//                            progressBar.setVisibility(View.GONE);
//
////                            getDatabaseData(String.valueOf(querySnapshot.get("uid")));
//
//                        }
//                    }
//                });
    }
//    private void getDatabaseData(String uid) {
//        Task<QuerySnapshot> collectionReference = firebaseFirestore.collection("Users")
//                .document(uid)
//                .collection("user_products_details").whereEqualTo("product_category","Vegetables")
//                .get()
//                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                    @Override
//                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                        for (QueryDocumentSnapshot querySnapshot:queryDocumentSnapshots) {
//                            Log.v("Collection", String.valueOf(querySnapshot));
//                            Log.v("Collection1", querySnapshot.get("product_name")+" and "+querySnapshot.get("product_price"));
//                            Map<String, Object> map = new HashMap<>();
//                            String product_name = String.valueOf(querySnapshot.get("product_name"));
//                            String product_price = String.valueOf(querySnapshot.get("product_price"));
//                            map.put("product_name",product_name);
//                            map.put("product_price",product_price);
//                            map.put("uid",uid);
//
//                            Log.v("Map",map.toString());
//
//                        }
//                    }
//                });
//
//    }


}