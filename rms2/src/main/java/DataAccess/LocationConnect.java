package DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DataModel.LocationModel;

public class LocationConnect implements LocationRepository {
    private Connection connection;

    public LocationConnect(Connection connection){
        this.connection = connection;
    }

    public void createLocation(LocationModel location) {
        String ln = location.getLocationName();
        String a = location.getAddress();
        String c = location.getCity();
        String s = location.getState();

        String sql = "INSERT INTO location (locationName, address, city, state) "+
                    "VALUES (? ,? ,? ,?)";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1, ln);
            stmt.setString(2, a);
            stmt.setString(3, c);
            stmt.setString(4, s);

            int ra = stmt.executeUpdate();

            if(ra > 0){
                System.out.println("Location Created successfully");
            }else{
                System.out.println("Location Insertion failed.");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public LocationModel getLocationByID(int locationID) {
        String sql = "SELECT * FROM location WHERE locationid = ?";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, locationID);
            try(ResultSet rs = stmt.executeQuery()){
            if(!rs.next()){
                return null;
            }
            LocationModel loc = new LocationModel(rs.getString("locationName"), rs.getString("address"), rs.getString("city"), rs.getString("state"));
            loc.setLocationID(locationID);
            return loc;
            }
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ArrayList<LocationModel> getAllLocations() {
        ArrayList<LocationModel> locations = new ArrayList<LocationModel>();
        String sql = "SELECT * FROM location";

        try(PreparedStatement stmt = connection.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()){
            while(rs.next()){
                LocationModel location = new LocationModel(rs.getString("locationName"), rs.getString("address"),
                        rs.getString("city"), rs.getString("state"));
                location.setLocationID(rs.getInt("locationID"));
                locations.add(location);
            }
            return locations;
        }catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public int getIDbyLocationName(String locationName){
        String sql = "SELECT * FROM location WHERE locationname = ?";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1, locationName);
            try(ResultSet rs = stmt.executeQuery()){
                if(!rs.next()){
                    return 0;
                }
                return rs.getInt("locationid");
            }
        }catch(SQLException e){
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public void updateLocation(LocationModel location) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateLocation'");
    }

    @Override
    public void deleteLocation(int locationID) {
        String sql = "DELETE FROM location WHERE locationid = ?";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, locationID);
            int rowsChanged = stmt.executeUpdate();
            if(rowsChanged < 1){
                System.out.println("Location Delete unsuccessful.");
            }else{
                System.out.println("Location Deletion Successful.");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public int getLocationByEmpID(int empID){
        String sql = "SELECT locationID FROM employeelocationassignment WHERE empID = ?";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, empID);
            try(ResultSet rs = stmt.executeQuery()){
                if(!rs.next()){
                    return 0;
                }
                return rs.getInt("locationID");
            }
        }catch(SQLException e){
            e.printStackTrace();
            return 0;
        }
    }
    
}
