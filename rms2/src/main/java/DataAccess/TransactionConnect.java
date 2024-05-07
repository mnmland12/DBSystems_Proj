package DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;

import DataModel.EmployeeModel;
import DataModel.LocationModel;
import DataModel.ProductModel;
import DataModel.TransactionModel;

public class TransactionConnect implements TransactionRepository {
    private Connection connection;

    public TransactionConnect(Connection connection){
        this.connection = connection;
    }

    public ArrayList<TransactionModel> getTransactionsByProductID(int productID){
        String sql = "SELECT t.* FROM transaction t "+
                    "JOIN productssold ps ON t.transactionid = ps.transactionid "+
                    "WHERE ps.productid = ? ORDER BY t.date DESC";
        ArrayList<TransactionModel> transactions = new ArrayList<>();
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, productID);
            try(ResultSet rs = stmt.executeQuery()){
                while (rs.next()) {
                    TransactionModel transaction = new TransactionModel();
                    transaction.setTransactionID(rs.getInt("transactionID"));
                    transaction.setDate(rs.getDate("date"));
                    transaction.setTotal(rs.getDouble("total"));
                    transaction.setPaymentMethod(rs.getString("paymentmethod"));
                    EmployeeConnect employee = new EmployeeConnect(connection);
                    transaction.setEmployee(employee.getEmployeeByID(rs.getInt("empID")));
                    LocationConnect location = new LocationConnect(connection);
                    transaction.setLocation(location.getLocationByID(rs.getInt("locationID")));
                    transaction.setNotes(rs.getString("notes"));

                    transactions.add(transaction);
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return transactions;
    }

    @Override
    public void createTransaction(TransactionModel transaction) {
        Date d = transaction.getDate();
        double t = transaction.getTotal();
        String p = transaction.getPaymentMethod();
        EmployeeModel emp = transaction.getEmployee();
        EmployeeConnect em = new EmployeeConnect(connection);
        int empID = em.getIDByFirstName(emp.getFirstName());
        LocationModel loc = transaction.getLocation();
        LocationConnect l = new LocationConnect(connection);
        int locID = l.getIDbyLocationName(loc.getLocationName());
        String n = transaction.getNotes();

        String sql = "INSERT INTO transaction (date, total, paymentmethod, empID, locationID, notes)"+
                    " VALUES (?, ?, ?, ?, ?, ?)";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setDate(1, d);
            stmt.setDouble(2, t);
            stmt.setString(3, p);
            stmt.setInt(4, empID);
            stmt.setInt(5, locID);
            stmt.setString(6, n);

            int ra = stmt.executeUpdate();
            if(ra > 0){
                System.out.println("Transaction created successfully.");
            }else{
                System.out.println("Transaction creation unsuccessful.");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public TransactionModel getTransactionByID(int transactionID) {
        String sql = "SELECT * FROM transaction WHERE transactionID = ?";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, transactionID);
            try(ResultSet rs = stmt.executeQuery()){
                if(!rs.next()){
                    return null;
                }
                TransactionModel transaction = new TransactionModel();
                transaction.setDate(rs.getDate("date"));
                transaction.setTotal(rs.getDouble("total"));
                transaction.setPaymentMethod(rs.getString("paymentmethod"));
                EmployeeConnect employee = new EmployeeConnect(connection);
                transaction.setEmployee(employee.getEmployeeByID(rs.getInt("empID")));
                LocationConnect location = new LocationConnect(connection);
                transaction.setLocation(location.getLocationByID(rs.getInt("locationID")));
                transaction.setNotes(rs.getString("notes"));
                return transaction;
          }
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ArrayList<TransactionModel> getAllTransactions() {
        ArrayList<TransactionModel> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transaction ORDER BY date";

        try(PreparedStatement stmt = connection.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()){
            while(rs.next()){
                TransactionModel transaction = new TransactionModel();
                transaction.setTransactionID(rs.getInt("transactionID"));
                transaction.setDate(rs.getDate("date"));
                transaction.setTotal(rs.getDouble("total"));
                transaction.setPaymentMethod(rs.getString("paymentmethod"));
                EmployeeConnect employee = new EmployeeConnect(connection);
                transaction.setEmployee(employee.getEmployeeByID(rs.getInt("empID")));
                LocationConnect location = new LocationConnect(connection);
                transaction.setLocation(location.getLocationByID(rs.getInt("locationID")));
                transaction.setNotes(rs.getString("notes"));
                
                transactions.add(transaction);
            }
            return transactions;
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public void updateTransaction(int oldEmp, int newEmp) {
        String sql = "update transaction set empID = ? where empID = ?";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, newEmp);
            stmt.setInt(2, oldEmp);
            int ra = stmt.executeUpdate();
            if(ra > 0){
                System.out.println("Update Transaction Successful. Rows Changed: " + ra);
            }else{
                System.out.println("Transaction table not updated");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void deleteTransaction(int transactionID) {
        String sql = "DELETE FROM transaction WHERE transactionID = ?";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, transactionID);
            int ra = stmt.executeUpdate();
            if(ra < 1){
                System.out.println("Transaction deletion unsuccessful.");
            }else{
                System.out.println("Transaction deletion successful.");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public int getTransactionID(TransactionModel transaction){
        String sql = "SELECT * FROM transaction WHERE date = ? AND empid = ? AND locationid = ? AND total = ?";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setDate(1, transaction.getDate());
            EmployeeConnect emp = new EmployeeConnect(connection);
            stmt.setInt(2, emp.getIDByFirstName(transaction.getEmployee().getFirstName()));
            LocationConnect loc = new LocationConnect(connection);
            stmt.setInt(3, loc.getIDbyLocationName(transaction.getLocation().getLocationName()));
            stmt.setDouble(4, transaction.getTotal());
            try(ResultSet rs = stmt.executeQuery()){
                if(!rs.next()){
                    return 0;
                }
                return rs.getInt("transactionid");
            }
        }catch(SQLException e){
            e.printStackTrace();
            return 0;
        }
    }

    public ArrayList<Integer> getTransactionIDByEmpID(int empID){
        String sql = "Select transactionid from transaction where empid = ?";
        ArrayList<Integer> transactionIDs = new ArrayList<>();
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, empID);
            try(ResultSet rs = stmt.executeQuery()){
                while(rs.next()){
                    int tID = rs.getInt("transactionid");
                    transactionIDs.add(tID);
                }
                return transactionIDs;
            }
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void updateTransaction(TransactionModel transaction) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateTransaction'");
    }

    public ArrayList<TransactionModel> getTransactionByLocDate(int loc, Date date) {
        ArrayList<TransactionModel> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transaction WHERE locationid = ? AND date = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, loc);
            stmt.setDate(2, date);
            try(ResultSet rs = stmt.executeQuery()){
                while (rs.next()) {
                    TransactionModel transaction = new TransactionModel();
                    transaction.setTransactionID(rs.getInt("transactionID"));
                    transaction.setDate(rs.getDate("date"));
                    transaction.setTotal(rs.getDouble("total"));
                    transaction.setPaymentMethod(rs.getString("paymentmethod"));
                    EmployeeConnect employee = new EmployeeConnect(connection);
                    transaction.setEmployee(employee.getEmployeeByID(rs.getInt("empID")));
                    LocationConnect location = new LocationConnect(connection);
                    transaction.setLocation(location.getLocationByID(rs.getInt("locationID")));
                    transaction.setNotes(rs.getString("notes"));

                    transactions.add(transaction);
                }
                return transactions;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public HashMap<Date, Double> getAverageTotals(int locID){
        String sql = "SELECT date, AVG(total) AS average_total "+
                    "FROM transaction WHERE locationID = ? GROUP BY date";

        HashMap<Date, Double> avgTotals = new HashMap<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, locID);
            try(ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Date date = rs.getDate("date");
                double averageTotal = rs.getDouble("average_total");
                avgTotals.put(date, averageTotal);
            }
        
            return avgTotals;
            }
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public HashMap<Date, Double> getTotals(int locID) {
        String sql = "SELECT date, SUM(total) AS dateTotal " +
                "FROM transaction WHERE locationID = ? GROUP BY date";

        HashMap<Date, Double>totals = new HashMap<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, locID);
            try(ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Date date = rs.getDate("date");
                double total = rs.getDouble("dateTotal");
                totals.put(date, total);
            }

            return totals;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public HashMap<ProductModel, Integer> getMostSold(int locID, int numDays) {
        String sql = "SELECT ps.productID, SUM(ps.quantity) AS totalQuantity " +
                "FROM productssold ps " +
                "JOIN transaction t ON ps.transactionID = t.transactionID " +
                "JOIN product p ON ps.productID = p.productID " +
                "WHERE t.locationID = ? " +
                "AND t.date >= ? " +
                "AND t.date <= CURRENT_DATE " + 
                "GROUP BY ps.productID " +
                "ORDER BY totalQuantity DESC LIMIT 5"; 

        LocalDate endDate = LocalDate.now(); // End date is current date
        LocalDate startDate = endDate.minusDays(numDays); // Calculate start date

        HashMap<ProductModel, Integer> mostSoldInRange = new HashMap<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, locID);
            stmt.setDate(2, Date.valueOf(startDate));
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int productID = rs.getInt("productID");
                    ProductConnect pConn = new ProductConnect(connection);
                    ProductModel product = pConn.getProductByID(productID);
                    int totalQuantity = rs.getInt("totalQuantity");
                    mostSoldInRange.put(product, totalQuantity);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mostSoldInRange;
    }

    public HashMap<String, Integer> getSoldYesterday(int locID) {
        String sql = "SELECT ps.productID, SUM(ps.quantity) AS totalQuantity " +
                "FROM productssold ps " +
                "JOIN transaction t ON ps.transactionID = t.transactionID " +
                "JOIN product p ON ps.productID = p.productID " +
                "WHERE t.locationID = ? " +
                "AND t.date = CURRENT_DATE - INTERVAL '1' DAY " +
                "GROUP BY ps.productID " +
                "ORDER BY ps.productid ASC";

        HashMap<String, Integer> mostSoldInRange = new HashMap<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, locID);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int productID = rs.getInt("productID");
                    ProductConnect pConn = new ProductConnect(connection);
                    ProductModel product = pConn.getProductByID(productID);
                    String keyString = product.getProductName() + " " + product.getSize();
                    int totalQuantity = rs.getInt("totalQuantity");
                    mostSoldInRange.put(keyString, totalQuantity);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mostSoldInRange;
    }
    
}
