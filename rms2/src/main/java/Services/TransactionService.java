package Services;

import DataModel.*;
import DataAccess.*;

public class TransactionService {
    private TransactionConnect transactionConnect;

    public TransactionService(TransactionConnect transactionConnect){
        this.transactionConnect = transactionConnect;
    }

    //CRUD
    public void createTransaction(TransactionModel transaction){
        transactionConnect.createTransaction(transaction);
    }

    public TransactionModel getTransactionByID(int transactionID){
        return transactionConnect.getTransactionByID(transactionID);
    }

    public void updateTransaction(TransactionModel transaction){
        transactionConnect.updateTransaction(transaction);
    }

    public void deleteTransaction(int transactionID){
        transactionConnect.deleteTransaction(transactionID);
    }
}
