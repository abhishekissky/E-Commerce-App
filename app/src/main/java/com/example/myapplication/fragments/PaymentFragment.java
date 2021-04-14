package com.example.myapplication.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.myapplication.Activities.MainActivity;
import com.example.myapplication.Activities.PaymentStatusActivity;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentPaymentBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.payumoney.core.PayUmoneySdkInitializer;
import com.payumoney.core.entity.TransactionResponse;
import com.payumoney.sdkui.ui.utils.PayUmoneyFlowManager;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

public class PaymentFragment extends Fragment implements PaymentResultListener {
    FirebaseFirestore firebaseFirestore;
    FirebaseUser firebaseUser;
    TextView topText;
    String address,name;
    ImageView paymentImg;
    public static String totalAmount;
    Uri uri;
    Checkout checkout;
    int amount;
    //for Paytm
    public static final String PAYTM_PACKAGE_NAME = "net.one97.paytm";
    //for Google Pay
    public static final String GPAY_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user";
    //for PayU
    String udf1 = "";
    String udf2 = "";
    String udf3 = "";
    String udf4 = "";
    String udf5 = "";
    String key;
    String salt;
    String txnId = "0nf7" + System.currentTimeMillis();
    // String txnId = "TXNID720431525261327973";
    String phone = "9458942703";
    String productName = "Testing Aryan1";
    String firstName = "Aryan";
    String productinfo = "Trying";
    String email = "aryan@sample.com";
    public PaymentFragment() {
        // Required empty public constructor
    }

    FragmentPaymentBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPaymentBinding.inflate(inflater,container,false);
        View view = binding.getRoot();

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();

        topText = getActivity().findViewById(R.id.paymentTxt);
        topText.setTextColor(getResources().getColor(R.color.black));
        paymentImg = getActivity().findViewById(R.id.paymentImg);
        paymentImg.setImageResource(R.drawable.ic_baseline_navigate_next_white_24);
        paymentImg.setBackgroundResource(R.drawable.green_oval_background);

        totalAmount= getArguments().getString("totalAmount");
        address = getArguments().getString("address");
        name = getArguments().getString("name");
        String docId = getArguments().getString("docId");
        amount = Integer.valueOf(totalAmount)*100;
        key = getString(R.string.merchantKey);
        salt = getString(R.string.merchantSalt);

        Log.v("id",docId);

        checkout = new Checkout();
        checkout.setKeyID("rzp_test_GFNqOIMtBkJC8R");
        binding.PaytmRBtn.setChecked(true);

        binding.totalAmount.setText(totalAmount);


        binding.continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.PaytmRBtn.isChecked()){

                    uri = getPaytmUri("Seller","8299017844@paytm",totalAmount);
                    payWithPaytm(PAYTM_PACKAGE_NAME);

                }else if (binding.GPayRBtn.isChecked()){

                    uri = getUpiPaymentUrl("Seller","8299017844@paytm",totalAmount);
                    payWithGpay(GPAY_PACKAGE_NAME);

                }else if (binding.razorPayRBtn.isChecked()){
                    createOrderId();
                }else if (binding.PayURBtn.isChecked()){
                    payUApi();
                }else if (binding.codRBtn.isChecked()){
                    sendData();
                }
            }
        });




        return view;
    }

    private void payUApi() {

        String serverCalculatedHash= hashCal("SHA-512", hashSequence);

        PayUmoneySdkInitializer.PaymentParam.Builder builder = new
                PayUmoneySdkInitializer.PaymentParam.Builder();
        builder.setAmount(totalAmount)                          // Payment amount
                .setTxnId(txnId)                                             // Transaction ID
                .setPhone(phone)                                           // User Phone number
                .setProductName(productName)                   // Product Name or description
                .setFirstName(firstName)                              // User First name
                .setEmail(email)                                            // User Email ID
                .setsUrl(getString(R.string.sUrl))                    // Success URL (surl)
                .setfUrl(getString(R.string.fUrl))                     //Failure URL (furl)
                .setUdf1(udf1)
                .setUdf2(udf2)
                .setUdf3(udf3)
                .setUdf4(udf4)
                .setUdf5(udf5)
                .setIsDebug(true)                              // Integration environment - true (Debug)/ false(Production)
                .setKey(getString(R.string.merchantKey))                        // Merchant key
                .setMerchantId(getString(R.string.merchantID));             // Merchant ID

        PayUmoneySdkInitializer.PaymentParam paymentParam=null;
        //declare paymentParam object
        try{
            paymentParam = builder.build();
            //set the hash
            paymentParam.setMerchantHash(serverCalculatedHash);
        }
        catch (Exception e)
        {
            Log.d("Aryan","Error in hash1");
        }

        PayUmoneyFlowManager.startPayUMoneyFlow(paymentParam, getActivity(), R.style.AppTheme_default, false);

    }

    String hashSequence = key+"|"+txnId+"|"+totalAmount+"|"+productinfo+"|"+firstName+"|"+email+"|"+udf1+"|"+udf2+"|"+udf3+"|"+udf4+"|"+udf5+"||||||"+salt;
    public static String hashCal(String type, String hashString) {
        StringBuilder hash = new StringBuilder();
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance(type);
            messageDigest.update(hashString.getBytes());
            byte[] mdbytes = messageDigest.digest();
            for (byte hashByte : mdbytes) {
                hash.append(Integer.toString((hashByte & 0xff) + 0x100, 16).substring(1));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hash.toString();
    }

    // for RazorPay API
    public void createOrderId(){
        try {
            JSONObject orderRequest = new JSONObject();
            try {
                orderRequest.put("amount", amount); // amount in the smallest currency unit
                orderRequest.put("currency", "INR");
                orderRequest.put("receipt", "order_rcptid_11");
                orderRequest.put("name","Seller");

                checkout.open(getActivity(),orderRequest);
            }
            catch (Exception e){
                Log.d("Aryan","Error at Json creation");
            }
        } catch (Exception e) {
            // Handle Exception
            System.out.println(e.getMessage());
        }
    }

    //for GPay API
    private static Uri getUpiPaymentUrl(String name , String uid , String amt){
        return new Uri.Builder().scheme("upi").authority("pay").appendQueryParameter("pa",uid)
                .appendQueryParameter("pn",name)
                .appendQueryParameter("am",amt)
                .appendQueryParameter("cu","INR").build();

    }
    private void payWithGpay(String packageName){
        if(isAppInstalled(getContext(),packageName)){
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(uri);
            intent.setPackage(packageName);
            startActivityForResult(intent,0);
        }
        else{
            Toast.makeText(getContext(), "App is not installed please Install app first", Toast.LENGTH_SHORT).show();
        }
    }

    //for Paytm Api

    private static Uri getPaytmUri(String name,String upi,String amount){
        return new Uri.Builder().scheme("upi")
                .authority("pay").appendQueryParameter("pa",upi)
                .appendQueryParameter("pn",name)
                .appendQueryParameter("am",amount)
                .appendQueryParameter("cu","INR").build();
    }

    private void payWithPaytm(String packageName){

        if(isAppInstalled(getContext(),packageName)){

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(uri);
            intent.setPackage(packageName);
            startActivityForResult(intent,0);
        }else{
            Toast.makeText(getContext(), "Please Install PayTm First then try again", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    private static boolean isAppInstalled(Context context, String packageName){
        try{
            context.getPackageManager().getApplicationInfo(packageName,0);
            return  true;
        }
        catch (PackageManager.NameNotFoundException e){

            return false;
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String status=null;

        //for GPay
        if(data != null){

        }
        if(RESULT_OK == resultCode ){

            Toast.makeText(getContext(), "Done", Toast.LENGTH_SHORT).show();
            sendData();
        }


        //for PayTm
        if(data != null){
            Intent intent = new Intent(getContext(), PaymentStatusActivity.class);
            intent.putExtra("status",data.getStringExtra("status"));
            status = data.getStringExtra("status").toLowerCase();
        }
        if(RESULT_OK == resultCode && status.equals("success")){
            sendData();
            Toast.makeText(getContext(), "Payment was successfully of amount "+totalAmount, Toast.LENGTH_SHORT).show();

        }
        else{

            Toast.makeText(getContext(), "Payment Failed please try again", Toast.LENGTH_SHORT).show();
        }


        /// for PayU
        Log.d("Aryan", "request code " + requestCode + " resultcode " + resultCode);
        if (requestCode == PayUmoneyFlowManager.REQUEST_CODE_PAYMENT && resultCode == RESULT_OK && data != null) {
            TransactionResponse transactionResponse = data.getParcelableExtra( PayUmoneyFlowManager.INTENT_EXTRA_TRANSACTION_RESPONSE );

            if (transactionResponse != null && transactionResponse.getPayuResponse() != null) {

                if(transactionResponse.getTransactionStatus().equals( TransactionResponse.TransactionStatus.SUCCESSFUL )){
                    sendData();
                    Toast.makeText(getContext(), "Successfull payment", Toast.LENGTH_SHORT).show();

                    //Success Transaction
                } else{
                    Toast.makeText(getContext(), "Error at last moment", Toast.LENGTH_SHORT).show();
                    //Failure Transaction
                }

                // Response from Payumoney
                String payuResponse = transactionResponse.getPayuResponse();

                // Response from SURl and FURL
                String merchantResponse = transactionResponse.getTransactionDetails();
            }
            //One else if is remaining here I deleted it because it is showing errors resultModel
            else {
                Log.d("Aryan", "Both objects are null!");
            }
        }
    }
    //for RazorPay
    @Override
    public void onPaymentSuccess(String s) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle("Succcess!!");
        alert.show();
        sendData();
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(getContext(), "There is some Error", Toast.LENGTH_SHORT).show();

    }

    private void sendData(){

        firebaseFirestore = FirebaseFirestore.getInstance();
        Task<QuerySnapshot> collectionReference = firebaseFirestore.collection("Customer")
                .document(firebaseUser.getUid())
                .collection("card")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(QueryDocumentSnapshot querySnapshot:queryDocumentSnapshots){
                            Log.v("Data Get", String.valueOf(querySnapshot));
//                            Log.v("Data MGet", String.valueOf(querySnapshot.get("Address")));
                            String discountPercentage = String.valueOf(querySnapshot.get("discountPercentage"));
                            String discountPrice = String.valueOf(querySnapshot.get("discountPrice"));
                            String offerPrice = String.valueOf(querySnapshot.get("offerPrice"));
                            String gst = String.valueOf(querySnapshot.get("gst"));
                            String imageUrl = String.valueOf(querySnapshot.get("imageUrl"));
                            String dispatchIn = String.valueOf(querySnapshot.get("dispatchIn"));
                            String product_qty_i_d = String.valueOf(querySnapshot.get("product_qty_i_d"));
                            String productUnit = String.valueOf(querySnapshot.get("productUnit"));
                            String seller_uid = String.valueOf(querySnapshot.get("seller_uid"));
                            String product_name = String.valueOf(querySnapshot.get("product_name"));
                            String productMrp = String.valueOf(querySnapshot.get("productMrp"));
                            String customerUid = firebaseUser.getUid();

                            placeOrder(discountPercentage,discountPrice,offerPrice,gst
                                    ,imageUrl,dispatchIn,product_qty_i_d,productUnit,seller_uid
                                    ,product_name,productMrp,customerUid);
//                            getDatabaseData(String.valueOf(querySnapshot.get("uid")));

                        }
                    }
                });
    }

    private void placeOrder(String discountPercentage, String discountPrice
            , String offerPrice, String gst, String imageUrl, String dispatchIn
            , String product_qty_i_d, String productUnit, String seller_uid
            , String product_name, String productMrp, String customerUid) {

        Log.v("All Data",discountPercentage +"///"+imageUrl);
        Map<String ,Object> add = new HashMap<>();
        add.put("product_name",product_name);
        add.put("discountPrice",discountPrice);
        add.put("offerPrice",offerPrice);
        add.put("gst",gst);
        add.put("dispatchIn",dispatchIn);
        add.put("product_qty_i_d",product_qty_i_d);
        add.put("productUnit",productUnit);
        add.put("productMrp",productMrp);
        add.put("customerUid",customerUid);
        add.put("customerName",name);
        add.put("customerAddress",address);
        add.put("totalAmount",totalAmount);
        add.put("discountPercentage",discountPercentage);
        add.put("imageUrl",imageUrl);
            firebaseFirestore.collection("Users")
                    .document(seller_uid).collection("orderList")
                    .add(add)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {

                        }
                    });


            firebaseFirestore.collection("Customer")
                    .document(firebaseUser.getUid())
                    .collection("orderList")
                    .add(add)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            startActivity(new Intent(getContext(), MainActivity.class));
                        }
                    });

    }
}