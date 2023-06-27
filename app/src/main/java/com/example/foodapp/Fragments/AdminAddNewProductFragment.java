package com.example.foodapp.Fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.foodapp.Models.Product;
import com.example.foodapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AdminAddNewProductFragment extends Fragment {
    private View view;
    private ImageView AddIMGInPut;
    private EditText NameInPut, PriceInPut, IngredientsInPut, CategoryInPut, AboutInPut;
    private CheckBox AnnonceCheckBox;
    private MaterialCardView AddNewProductBTN;
    private Uri image_file;
    private String ImageURL;
    private StorageReference ProductImgref;
    private DatabaseReference RefProduct;
    private Dialog dialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_admin_add_new_product, container, false);

        //init
        InisializationOfFealds();
        openGalleryResult();
        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_wait1);
        dialog.setCanceledOnTouchOutside(false);

        //add new product
        AddNewProductBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isNotEmpty()){
                    dialog.show();
                    String id = GenerateID();
                    ProductImgref.child(id+".jpeg").putFile(image_file)
                            .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                    if (task.isSuccessful()){
                                        ProductImgref.child(id+".jpeg").getDownloadUrl()
                                                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                    @Override
                                                    public void onSuccess(Uri uri) {
                                                        ImageURL = uri.toString();
                                                        RefProduct = FirebaseDatabase.getInstance(getString(R.string.DBURL))
                                                                .getReference().child("Products");
                                                        RefProduct = RefProduct.push();
                                                        String idd = RefProduct.getKey();
                                                        if (AnnonceCheckBox.isChecked()){
                                                            Product product = new Product(idd,NameInPut.getText().toString(),PriceInPut.getText().toString(),
                                                                    IngredientsInPut.getText().toString(),CategoryInPut.getText().toString(),AboutInPut.getText().toString(),ImageURL,id,true);
                                                            saveProductIntoDB(product);
                                                        }else {
                                                            Product product = new Product(idd,NameInPut.getText().toString(),PriceInPut.getText().toString(),
                                                                    IngredientsInPut.getText().toString(),CategoryInPut.getText().toString(),AboutInPut.getText().toString(),ImageURL,id,false);
                                                            saveProductIntoDB(product);
                                                        }


                                                    }
                                                });
                                    }else {
                                        dialog.dismiss();
                                        Toast.makeText(getActivity(), task.getException().toString(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }
            }
        });

        return view;
    }
    private void InisializationOfFealds(){
        AddIMGInPut = view.findViewById(R.id.AddIMGInPut);
        NameInPut = view.findViewById(R.id.NameInPut);
        PriceInPut = view.findViewById(R.id.PriceInPut);
        IngredientsInPut = view.findViewById(R.id.IngredientsInPut);
        CategoryInPut = view.findViewById(R.id.CategoryInPut);
        AboutInPut = view.findViewById(R.id.AboutInPut);
        AnnonceCheckBox = view.findViewById(R.id.AnnonceCheckBox);
        AddNewProductBTN = view.findViewById(R.id.AddNewProductBTN);
        RefProduct = FirebaseDatabase.getInstance(getString(R.string.DBURL))
                .getReference().child("Products");
        ProductImgref = FirebaseStorage.getInstance().getReference()
                .child("ProductImages");
    }
    private void openGalleryResult(){
        ActivityResultLauncher<Intent> openGalleryResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK){
                            Intent data = result.getData();
                            image_file = data.getData();
                            float desiredWidthDp = 150; // Desired width in dp
                            float desiredHeightDp = 100; // Desired height in dp

                            // Convert dp to pixels based on the device's screen density
                            float density = getResources().getDisplayMetrics().density;
                            int desiredWidthPx = (int) (desiredWidthDp * density);
                            int desiredHeightPx = (int) (desiredHeightDp * density);

                            // Resize or downscale the image before setting it to the ImageView
                            Bitmap resizedBitmap = resizeImage(image_file, desiredWidthPx, desiredHeightPx);

                            if (resizedBitmap != null) {
                                // Set the resized bitmap to the ImageView
                                AddIMGInPut.setImageBitmap(resizedBitmap);
                            } else {
                                // Handle the case where resizing failed
                                Toast.makeText(getActivity(),"Le redimensionnement a échoué.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

        AddIMGInPut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGalleryResult.launch(OpenGalery());
            }
        });
    }
    private Bitmap resizeImage(Uri imageUri, int desiredWidth, int desiredHeight) {
        try {
            InputStream inputStream = requireContext().getContentResolver().openInputStream(imageUri);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(inputStream, null, options);

            // Calculate an appropriate inSampleSize value
            options.inSampleSize = calculateInSampleSize(options, desiredWidth, desiredHeight);

            inputStream.close();
            inputStream = requireContext().getContentResolver().openInputStream(imageUri);

            // Decode the bitmap with the calculated inSampleSize
            options.inJustDecodeBounds = false;
            Bitmap resizedBitmap = BitmapFactory.decodeStream(inputStream, null, options);

            inputStream.close();

            return resizedBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    private int calculateInSampleSize(BitmapFactory.Options options, int desiredWidth, int desiredHeight) {
        final int width = options.outWidth;
        final int height = options.outHeight;
        int inSampleSize = 1;

        if (height > desiredHeight || width > desiredWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            while ((halfHeight / inSampleSize) >= desiredHeight && (halfWidth / inSampleSize) >= desiredWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
    private Intent OpenGalery(){
        Intent i = new Intent();
        i.setAction(Intent.ACTION_GET_CONTENT);
        i.setType("image/*");
        return i;
    }
    private boolean isNotEmpty(){
        if (NameInPut.getText().toString().isEmpty()){
            Toast.makeText(getActivity(), "Entrez le nom du produit", Toast.LENGTH_SHORT).show();
            return false;
        }else if (PriceInPut.getText().toString().isEmpty()){
            Toast.makeText(getActivity(), "Entrez le prix du produit", Toast.LENGTH_SHORT).show();
            return false;
        }else if (IngredientsInPut.getText().toString().isEmpty()){
            Toast.makeText(getActivity(), "Entrez les ingredients du produit", Toast.LENGTH_SHORT).show();
            return false;
        }else if (CategoryInPut.getText().toString().isEmpty()){
            Toast.makeText(getActivity(), "Entrez la catégorie de produit", Toast.LENGTH_SHORT).show();
            return false;
        }else if (AboutInPut.getText().toString().isEmpty()){
            Toast.makeText(getActivity(), "Entrez la description du produit", Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }
    }
    private String GenerateID(){
        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("yyyyMMdd");
        String saveCurrentDate = currentDate.format(calForDate.getTime());
        SimpleDateFormat currentTime = new SimpleDateFormat("HH : MM : ss a");
        String SaveCurrentTime = currentTime.format(calForDate.getTime());
        return saveCurrentDate+SaveCurrentTime;
    }
    protected void saveProductIntoDB(Product product){
        RefProduct.setValue(product)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            dialog.dismiss();
                            Toast.makeText(getActivity(), "Le produit a été ajoutée", Toast.LENGTH_SHORT).show();
                        }else {
                            dialog.dismiss();
                            Toast.makeText(getActivity(), task.getException().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}