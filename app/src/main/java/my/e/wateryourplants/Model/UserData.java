package my.e.wateryourplants.Model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class UserData {
    private String userName;
    private String userEmail;
    private String userSensorName;
    private String userSensorDescription;
    private String userSensorMoistureCondition;
    private Float userSensorTemperature;


    public UserData() {
    }

    public UserData(String userName, String userEmail) {
        this.userName = userName;
        this.userEmail = userEmail;
    }

    public UserData(String userSensorName, String userSensorDescription, String userSensorMoistureCondition) {
        this.userSensorName = userSensorName;
        this.userSensorDescription = userSensorDescription;
        this.userSensorMoistureCondition = userSensorMoistureCondition;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserSensorName() {
        return userSensorName;
    }

    public void setUserSensorName(String userSensorName) {
        this.userSensorName = userSensorName;
    }

    public String getUserSensorDescription() {
        return userSensorDescription;
    }

    public void setUserSensorDescription(String userSensorDescription) {
        this.userSensorDescription = userSensorDescription;
    }

    public String getUserSensorMoistureCondition() {
        return userSensorMoistureCondition;
    }

    public void setUserSensorMoistureCondition(String userSensorMoistureCondition) {
        this.userSensorMoistureCondition = userSensorMoistureCondition;
    }

    public Float getUserSensorTemperature() {
        return userSensorTemperature;
    }

    public void setUserSensorTemperature(Float userSensorTemperature) {
        this.userSensorTemperature = userSensorTemperature;
    }

    @Exclude
    public Map<String, Object> toMapUserNameEmail() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("userName", userName);
        result.put("userEmail", userEmail);
        return result;
    }

    @Exclude
    public Map<String, Object> toMapSensorData() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("userSensorName", userSensorName);
        result.put("userSensorDescription", userSensorDescription);
        return result;
    }
}
