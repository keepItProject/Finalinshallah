package com.example.myapplication.Adaptors;

import android.R.color;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.Data.UserCategory;
import com.example.myapplication.Data.Userinvoice;
import com.example.myapplication.Empty.activity_cloth;
import com.example.myapplication.R;
import com.example.myapplication.activity_add_catagory;
import com.example.myapplication.activity_homepage;
import com.example.myapplication.docinfoActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import static android.graphics.Color.parseColor;
import static androidx.core.content.ContextCompat.startActivity;

public class cateAdapter extends RecyclerView.Adapter<cateAdapter.CateView> {
    private ArrayList<UserCategory> userCategories;

    private ArrayList<Integer> images;
    String key;
    private Context mContext;
    private Intent intent;
    private String mode;
    private Userinvoice userinvoice;

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }


    public ArrayList<UserCategory> getUserCategories() {
        return userCategories;
    }

    public void setUserCategories(ArrayList<UserCategory> userCategories) {
        this.userCategories = userCategories;
        notifyDataSetChanged();
    }

    public cateAdapter(Context mContext) {
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public CateView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_cate, parent, false);
        return new CateView(view);
    }

    @SuppressLint("ResourceAsColor")
    public void onBindViewHolder(@NonNull final CateView holder, final int position) {
        final UserCategory userCategory = userCategories.get(position);
        if (!getMode().equals("normal")) {
            if (userCategory.getType().equals("static")) {

holder.cardView.setCardBackgroundColor(android.R.color.transparent);
              //  holder.liner.setBackgroundColor(parseColor("#77000000"));
                ///
                holder.cardView.setCardBackgroundColor(android.R.color.transparent);

                //  holder.cardView.setCardElevation(0);
                //  holder.liner.setBackgroundColor(R.drawable.round45);

                holder.liner.setBackgroundResource(R.drawable.round100);
                holder.cardView.setCardElevation(0);

             //  holder.cardView.setCardElevation(0);
                //holder.liner.setBackgroundColor(R.drawable.round100);
               // \holder.cardView.setCardBackgroundColor(R.drawable.round100);
                holder.cardView.setCardElevation(0);
            }
        } else {

           //holder.liner.setBackgroundColor(parseColor(R.drawable.round45));
          //  holder.cardView.setCardElevation(0);
           holder.cardView.setCardBackgroundColor(android.R.color.transparent);

            //  holder.cardView.setCardElevation(0);
         //  holder.liner.setBackgroundColor(R.drawable.round45);

          //  holder.liner.setBackgroundColor(R.color.colorPrimaryDark);
            holder.liner.setBackgroundResource(R.drawable.round45);
            holder.cardView.setCardElevation(0);
        }

        holder.titel.setText(userCategory.getName());
        if (userCategory.getImage() != null) {
            Glide.with(mContext).load(userCategory.getImage()).into(holder.image);
        } else {
            holder.image.setImageResource(R.drawable.catec);
        }

        if (userCategory.getType().equals("user")) {
            Glide.with(mContext).load(R.drawable.supermarket).into(holder.image);
        }

        if (userCategory.getUser_id().equals("add")) {
            Glide.with(mContext).load(R.drawable.add).into(holder.image);
        }
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userCategory.getType().equals("static")) {
                    if (getMode().equals("normal")) {
                        intent = new Intent(mContext, activity_cloth.class);
                        intent.putExtra("id", userCategory.getId());
                        intent.putExtra("title", userCategory.getName());
                        mContext.startActivity(intent);
                    }


                } else if (userCategory.getType().equals("add")) {
                    if (getMode().equals("normal")) {
                        intent = new Intent(mContext, activity_add_catagory.class);
                        mContext.startActivity(intent);
                    }

                } else {

                    if (getMode().equals("edit")) {


                        /*intent = new Intent(mContext, EditActivity.class);
                        intent.putExtra("x",holder.cardView.getX());
                        intent.putExtra("y",holder.cardView.getY());
                        intent.putExtra("cate",userCategory);
                        mContext.startActivity(intent);*/

                        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setCancelable(true);
                        View view = LayoutInflater.from(mContext).inflate(R.layout.edit_cate, null, false);
                        CardView yes_card = view.findViewById(R.id.yes_card);
                        final EditText edit_text = view.findViewById(R.id.edit_text);
                        edit_text.setText(userCategories.get(position).getName());

                        builder.setView(view);
                        final AlertDialog alertDialog = builder.show();

                        CardView no_card = view.findViewById(R.id.no_card);
                        no_card.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                alertDialog.dismiss();

                            }
                        });


                        yes_card.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                String name = edit_text.getText().toString().trim();
                                if (!name.isEmpty()) {
                                    FirebaseDatabase.getInstance().getReference().child("category").orderByChild("name").equalTo(name).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot2) {
                                            if (dataSnapshot2.exists()) {
                                                showExisitDialog(holder.itemView.getContext());
                                            } else {
                                                HashMap<String, Object> map = new HashMap<>();
                                                map.put("name", name);
                                                FirebaseDatabase.getInstance().getReference().child("category").child(userCategory.getId()).updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Toast.makeText(mContext, "تم التعديل بنجاح", Toast.LENGTH_SHORT).show();
                                                            alertDialog.dismiss();
                                                        }
                                                    }
                                                });
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });


                                } else {
                                    Toast.makeText(mContext, "يرجى كتابة اسم الفئة", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                    }
                    if (getMode().equals("delete")) {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setCancelable(true);
                        View view = LayoutInflater.from(mContext).inflate(R.layout.delete_cate_dialog, null, false);
                        CardView yes_card = view.findViewById(R.id.yes_card);

                        builder.setView(view);
                        final AlertDialog alertDialog = builder.show();

                        CardView no_card = view.findViewById(R.id.no_card);
                        no_card.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                alertDialog.dismiss();


                            }
                        });


                        yes_card.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                FirebaseDatabase.getInstance().getReference().child("category").child(userCategory.getId()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    String f=userCategory.getId();
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            FirebaseDatabase.getInstance().getReference().child("document").addListenerForSingleValueEvent(new ValueEventListener() {
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot2) {
                                                    for (DataSnapshot datashot : dataSnapshot2.getChildren()) {

                                                        String categoryid= datashot.child("categoryId").getValue(String.class);
                                                        if(categoryid.equals(userCategory.getId())){
                                                            key = datashot.getKey();

                                                            FirebaseDatabase.getInstance().getReference().child("document").child(key).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    if (task.isSuccessful()) {
                                                                    }
                                                                }
                                                            });

                                                        }}}
                                                @Override
                                                public void onCancelled (@NonNull DatabaseError databaseError){

                                                }
                                            });
                                            Toast.makeText(mContext, "تم الحذف بنجاح", Toast.LENGTH_SHORT).show();
                                            alertDialog.dismiss();
                                                        }


                                    }

                                });


                            }
                        });


                    }

                    if (getMode().equals("normal")) {
                        intent = new Intent(mContext, activity_cloth.class);
                        intent.putExtra("title", userCategory.getName());

                        intent.putExtra("id", userCategory.getId());
                        mContext.startActivity(intent);
                    }

                }


            }
        });

    }


    @Override
    public int getItemCount() {
        return getUserCategories().size();
    }

    //viewHolder
    class CateView extends RecyclerView.ViewHolder {
        TextView titel;
        ImageView image;
        CardView cardView;
        View view;
        LinearLayout liner;

        CateView(@NonNull View itemView) {
            super(itemView);
            liner = itemView.findViewById(R.id.liner1);
            titel = itemView.findViewById(R.id.textt);
            image = itemView.findViewById(R.id.imagee);
            cardView = itemView.findViewById(R.id.card1);
            view = itemView;
           // cardView.setBackgroundColor(Color.parseColor("@drawable/round"));


        }
    }

    private void showExisitDialog(Context context) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        View view = LayoutInflater.from(context).inflate(R.layout.category_exist_dialog, null, false);
        builder.setView(view);
        final AlertDialog alertDialog = builder.show();

        CardView repeat_card = view.findViewById(R.id.repeat_card);
        repeat_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();

            }
        });


    }


}