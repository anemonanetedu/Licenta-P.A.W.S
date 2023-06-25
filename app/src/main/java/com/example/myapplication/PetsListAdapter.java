package com.example.myapplication;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class PetsListAdapter extends RecyclerView.Adapter<PetsListAdapter.PetsListViewHolder>{

    Context context;
    ArrayList<Pet> pets;

    public PetsListAdapter(Context context, ArrayList<Pet> pets) {

        this.context = context;
        this.pets = pets;
    }

    @NonNull
    @Override
    public PetsListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.pet_infos_layout, parent, false);

        return new PetsListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PetsListViewHolder holder, int position) {

        Pet p = pets.get(position);
        holder.nameTV.setText(p.getName());
        holder.ageTV.setText(p.getAge());
        holder.weightTV.setText(p.getWeight());
        holder.rfidTV.setText(p.getRfidTag());
        holder.raceTV.setText(p.getRace());
        holder.typeTV.setText(p.getType());

        holder.feedPet.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View view) {

                Intent feedPetPage = new Intent(context, FeedPetPage.class);
                context.startActivity(feedPetPage);
            }
        });
    }

    @Override
    public int getItemCount() {

        return pets.size();
    }

    public static class PetsListViewHolder extends RecyclerView.ViewHolder{

        TextView nameTV, ageTV, weightTV, rfidTV, raceTV, typeTV;
        Button feedPet;

       public PetsListViewHolder(@NonNull View itemView) {
           super(itemView);

           nameTV = itemView.findViewById(R.id.pet_name);
           ageTV = itemView.findViewById(R.id.pet_age);
           weightTV = itemView.findViewById(R.id.pet_weight);
           rfidTV = itemView.findViewById(R.id.pet_rfig);
           raceTV = itemView.findViewById(R.id.pet_race);
           typeTV = itemView.findViewById(R.id.pet_type);

           feedPet = itemView.findViewById(R.id.feedPetBttn);
       }
   }

}
