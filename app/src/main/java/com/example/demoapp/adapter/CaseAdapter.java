package com.example.demoapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.demoapp.DetailCaseActivity;
import com.example.demoapp.R;
import com.example.demoapp.models.CaseReport;

import java.util.List;

public class CaseAdapter extends RecyclerView.Adapter<CaseAdapter.CaseHolder> {

    private final LayoutInflater inflater;
    private final int card;
    private final List<CaseReport> cases;
    private final Context context;

    public CaseAdapter(Context context, int view_case_card, List<CaseReport> cases) {
        inflater = LayoutInflater.from(context);
        card = view_case_card;
        this.cases = cases;
        this.context = context;
    }

    @NonNull
    @Override
    public CaseHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new CaseHolder(inflater.inflate(card, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CaseHolder caseHolder, int i) {
        CaseReport report = cases.get(i);
        caseHolder.numConvicts.setText("covicts " + report.noOfConvicts);
        caseHolder.caseId.setText(report.getCaseId());
        caseHolder.caseAddress.setText(report.address);
        caseHolder.caseDetails.setText(report.caseDetails);
        caseHolder.card.setTag(report);
        caseHolder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CaseReport caseReport = (CaseReport) v.getTag();
                Intent intent = new Intent(context, DetailCaseActivity.class);
                intent.putExtra("caseId", caseReport.getCaseId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cases.size();
    }

    public class CaseHolder extends RecyclerView.ViewHolder {

        TextView caseId, caseAddress, caseDetails, numConvicts;
        CardView card;

        public CaseHolder(@NonNull View v) {
            super(v);
            caseId = v.findViewById(R.id.caseId);
            caseAddress = v.findViewById(R.id.caseAddress);
            caseDetails = v.findViewById(R.id.caseDetails);
            numConvicts = v.findViewById(R.id.numConvicts);
            card = v.findViewById(R.id.card);
        }
    }
}
