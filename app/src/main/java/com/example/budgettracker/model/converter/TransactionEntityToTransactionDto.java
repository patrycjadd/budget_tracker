package com.example.budgettracker.model.converter;

import android.app.Application;

import com.example.budgettracker.model.TransactionDto;
import com.example.budgettracker.model.TransactionEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class TransactionEntityToTransactionDto {
    private Application application;

    public TransactionEntityToTransactionDto(Application application) {
        this.application = application;
    }

    public TransactionDto convertToDto(TransactionEntity transaction) throws ExecutionException, InterruptedException {
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setTransactionId(transaction.getTransactionId());
        transactionDto.setDateOfTransaction(transaction.getDateOfTransaction());
        transactionDto.setIncomeValue(transaction.getIncomeValue());
        transactionDto.setExpenseValue(transaction.getExpenseValue() );
        transactionDto.setCategoryName(CategoryIdToNameConverter.convertIdToName(application, transaction.getCategoryId()));
        return transactionDto;
    }

    public List<TransactionDto> convertToDtos(List<TransactionEntity> transactions) throws ExecutionException, InterruptedException {
        List<TransactionDto> transactionDtos = new ArrayList<>();
        for(TransactionEntity transaction: transactions){
            transactionDtos.add(convertToDto(transaction));
        }
        return transactionDtos;
    }
}
