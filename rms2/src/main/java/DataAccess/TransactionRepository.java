package DataAccess;

import java.util.ArrayList;

import DataModel.TransactionModel;

public interface TransactionRepository {
    void createTransaction(TransactionModel transaction);

    TransactionModel getTransactionByID(int transactionID);
    ArrayList<TransactionModel> getAllTransactions();
    
    void updateTransaction(TransactionModel transaction);

    void deleteTransaction(int transactionID);
}
