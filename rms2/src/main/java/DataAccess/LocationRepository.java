package DataAccess;

import DataModel.*;

import java.util.ArrayList;

public interface LocationRepository {
    void createLocation(LocationModel location);

    LocationModel getLocationByID(int locationID);
    ArrayList<LocationModel> getAllLocations();
    int getIDbyLocationName(String locationName);

    void updateLocation(LocationModel location);

    void deleteLocation(int locationID);
}
