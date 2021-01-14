package com.example.budgettracker.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.budgettracker.R;
import com.example.budgettracker.model.TransactionDto;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private Context context;
    private List<TransactionDto> transactionList;

    public CustomAdapter(final Context context, final List<TransactionDto> transactionList) {
        this.transactionList = transactionList;
        this.context = context;
    }

    public void updateEvents(final List<TransactionDto> transactions) {
        transactionList = transactions;
        notifyDataSetChanged();
    }

    public void setData(List<TransactionDto> newData) {
        this.transactionList = newData;
        notifyDataSetChanged();
    }

    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View transView = layoutInflater.inflate(R.layout.row, parent, false);
        ViewHolder viewHolder = new ViewHolder(transView);
        return viewHolder;
    }

    public void clear() {
        final int size = transactionList.size();
        transactionList.clear();
        notifyItemRangeRemoved(0, size);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        TransactionDto transaction = transactionList.get(position);
        holder.textViewValue.setText(String.valueOf(transaction.getIncomeValue() != 0 ?transaction.getIncomeValue(): transaction.getExpenseValue()));
        holder.textViewDate.setText(transaction.getDateOfTransaction().toString());
        holder.textViewCategory.setText(transaction.getCategoryName());
    }

    @Override
    public long getItemId(final int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return transactionList != null ? transactionList.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewValue;
        public TextView textViewDate;
        public TextView textViewCategory;

        public ViewHolder(final View view) {
            super(view);

            textViewValue = view.findViewById(R.id.valueField);
            textViewCategory = view.findViewById(R.id.categoryField);
            textViewDate = view.findViewById(R.id.dateField);
        }
    }
}
