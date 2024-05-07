package DataModel;

import java.io.Serializable;
import java.sql.Connection;

import DataAccess.*;

public class LocationModel implements Serializable{
    private int locationID;
    private String locationName;
    private String address;
    private String city;
    private String state;

    public LocationModel(String locationName, String address, String city, String state){
        this.locationName = locationName;
        this.address = address;
        this.city = city;
        this.state = state;
    }

    public LocationModel(){

    }

    //validation
    public static boolean validateLocationByID(int locationID){
        Connection conn = DatabaseConnection.getInstance();
        LocationConnect l = new LocationConnect(conn);
        if(l.getLocationByID(locationID) == null)
            return false;
        return true;
    }

    //toString
    public String toString(){
        return this.locationName+" "+this.address+" "+this.city+", "+this.state;  
    }

    //getters w/o setters
    public void setLocationID(int locationID){
        this.locationID = locationID;
    }

    public int getLocationID(){
        return locationID;
    }
    
    public String getLocationName(){
        return locationName;
    }

    public String getAddress(){
        return address;
    }

    public String getCity(){
        return city;
    }

    public String getState(){
        return state;
    }
}
