package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DataAccess.DatabaseConnection;
import DataAccess.LocationConnect;
import DataAccess.TransactionConnect;
import DataModel.LocationModel;
import DataModel.ProductModel;
import DataModel.TransactionModel;

@WebServlet("/getPastInfo")
public class PastInformation extends HttpServlet{
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection conn = DatabaseConnection.getInstance();
        LocationConnect lConn = new LocationConnect(conn);
        ArrayList<LocationModel> locList = lConn.getAllLocations();
        HashMap<Integer, String> locMap = new HashMap<>();

        for(LocationModel l : locList){
            locMap.put(l.getLocationID(), l.getLocationName());
        }

        HttpSession session = request.getSession();
        session.setAttribute("locationMap", locMap);

        request.getRequestDispatcher("chooseLocInfo.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int locID = Integer.parseInt(request.getParameter("location"));

        
        Connection conn = DatabaseConnection.getInstance();
        TransactionConnect tConn = new TransactionConnect(conn);
        
        //get average for each day
        HashMap<Date, Double> avgTotal = tConn.getAverageTotals(locID);

        //get total for each day
        HashMap<Date, Double> total = tConn.getTotals(locID);

        //get products sold for past day, week, month
        HashMap<ProductModel, Integer> pastDay = tConn.getMostSold(locID, 1);
        HashMap<ProductModel, Integer> pastWeek = tConn.getMostSold(locID, 7);
        HashMap<ProductModel, Integer> pastMonth = tConn.getMostSold(locID, 30);

        pastDay = sortByValue(pastDay);
        pastMonth = sortByValue(pastMonth);
        pastWeek = sortByValue(pastWeek);

        HttpSession session = request.getSession();
        session.setAttribute("avgTotal", avgTotal);
        session.setAttribute("totals", total);
        session.setAttribute("pastDay", pastDay);
        session.setAttribute("pastWeek", pastWeek);
        session.setAttribute("pastMonth", pastMonth);

        request.getRequestDispatcher("insight.jsp").forward(request, response);
    }

    private HashMap<ProductModel, Integer> sortByValue(HashMap<ProductModel, Integer> map) {
        List<Map.Entry<ProductModel, Integer>> list = new ArrayList<>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<ProductModel, Integer>>() {
            @Override
            public int compare(Map.Entry<ProductModel, Integer> entry1, Map.Entry<ProductModel, Integer> entry2) {
                // Sort in descending order (entry2 before entry1)
                return entry2.getValue().compareTo(entry1.getValue());
            }
        });

        // Put the sorted list back into a HashMap
        HashMap<ProductModel, Integer> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<ProductModel, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }
}
