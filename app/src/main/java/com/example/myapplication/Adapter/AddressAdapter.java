package com.example.myapplication.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Activities.AddressActivity;
import com.example.myapplication.Models.AddressModel;
import com.example.myapplication.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddressAdapter extends FirestoreRecyclerAdapter<AddressModel,AddressAdapter.ViewHolder> {
    public String id=null;
    Context context;
    public String  totalPrice;
   public String destination,address,name;
    public AddressAdapter(@NonNull FirestoreRecyclerOptions<AddressModel> options , Context context,String totalPrice) {
        super(options);
        this.context=context;
        this.totalPrice = totalPrice;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull AddressModel model) {
        final String docId = this.getSnapshots().getSnapshot(position).getId();

        holder.customerName.setText(model.getName());
        address = model.getAddress() +","
                + model.getLandmark()+","
                + model.getCity()+","
                + model.getState()+","
                + model.getCountry()+","
                +model.getPinCode();
        holder.customerAddress.setText(address);
        holder.mobileNumber.setText(model.getMobile_no());
        holder.home.setText(model.getAddress_type());
        holder.radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               id=docId;
               destination =address;
               name = model.getName();
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setTitle("Delete Alert");
                alertDialogBuilder.setMessage("Are you sure you want to delete this address");
                alertDialogBuilder.setPositiveButton("Delete",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                                deleteFromDatabase(docId);

                            }
                        });

                alertDialogBuilder.setNegativeButton("cancel",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddressActivity.class);
                intent.putExtra("name",model.getName());
                intent.putExtra("mobileNu",model.getMobile_no());
                intent.putExtra("alternateMoNu",model.getAlternate_mobile_no());
                intent.putExtra("address",model.getAddress());
                intent.putExtra("landmark",model.getLandmark());
                intent.putExtra("city",model.getCity());
                intent.putExtra("state",model.getState());
                intent.putExtra("country",model.getCountry());
                intent.putExtra("pinCode",model.getPinCode());
                intent.putExtra("addressType",model.getAddress_type());
                intent.putExtra("docId",docId);
                intent.putExtra("accessCode","11");
                intent.putExtra("token","1");
                intent.putExtra("totalAmount",totalPrice);
                context.startActivity(intent);


            }
        });
    }



    private void deleteFromDatabase(String docId) {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore.collection("Customer").document(firebaseUser.getUid())
                .collection("address").document(docId)
                .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context, "Address Deleted", Toast.LENGTH_SHORT).show();

            }
        });

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.address_layout,parent,false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView customerName,home,customerAddress,mobileNumber;
        RadioButton radioButton;
        ImageView edit,delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            customerName = itemView.findViewById(R.id.customerName);
            home = itemView.findViewById(R.id.home);
            customerAddress = itemView.findViewById(R.id.customerAddress);
            mobileNumber = itemView.findViewById(R.id.mobileNumber);
            radioButton = itemView.findViewById(R.id.addressRadio);
            edit = itemView.findViewById(R.id.edit);
            delete = itemView.findViewById(R.id.delete);

        }
    }
}
