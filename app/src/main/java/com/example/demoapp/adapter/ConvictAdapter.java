package com.example.demoapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.demoapp.R;
import com.example.demoapp.models.Convict;

import java.util.List;

public class ConvictAdapter extends RecyclerView.Adapter<ConvictAdapter.ConvictHolder> {

    private final LayoutInflater inflater;
    private final int card;
    private final List<Convict> convicts;
    private final Context context;

    public ConvictAdapter(Context context, int view_case_card, List<Convict> convict) {
        inflater = LayoutInflater.from(context);
        card = view_case_card;
        this.convicts = convict;
        this.context = context;
    }

    @NonNull
    @Override
    public ConvictHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ConvictHolder(inflater.inflate(card, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ConvictHolder holder, int i) {
        Convict convict = convicts.get(i);
        String gender = convict.gender;
        String state = convict.state;
        holder.convictName.setText(convict.name);
        holder.convictAddress.setText(convict.address);
        holder.convictDetails.setText(convict.details);
        holder.convictGender.setText(gender.toLowerCase().contains("male") ? gender : "female");
        holder.convictState.setText(state.toLowerCase().contains("dead") ? state : "alive");
        holder.convictAge.setText(convict.age);
    }

    @Override
    public int getItemCount() {
        return convicts.size();
    }

    public class ConvictHolder extends RecyclerView.ViewHolder {

        TextView convictAddress, convictName, convictDetails, convictGender, convictState,convictAge;
        CardView card;

        public ConvictHolder(@NonNull View v) {
            super(v);
            convictAddress = v.findViewById(R.id.convictAddress);
            convictGender = v.findViewById(R.id.convictGender);
            convictName = v.findViewById(R.id.convictName);
            convictState = v.findViewById(R.id.convictstate);
            convictDetails = v.findViewById(R.id.convictDetails);
            convictAge = v.findViewById(R.id.convictAge);
            card = v.findViewById(R.id.card);
        }
    }
}
