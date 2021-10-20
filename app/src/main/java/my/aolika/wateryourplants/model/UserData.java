package my.aolika.wateryourplants.model;

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
    private Float userSensorPumpWateringDuration;
    private Float userSensorSleepModeTime;
    private Boolean userSensorPumpWateringAutomatic;
    private Boolean userSensorPumpWatering;
    private Boolean userSensorSleepModeAutomatic;
    private Boolean userSensorNotifyDryCondition;


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

    public UserData(String userSensorName,
                    String userSensorDescription,
                    Float userSensorPumpWateringDuration,
                    Boolean userSensorPumpWatering,
                    Boolean userSensorPumpWateringAutomatic,
                    Float userSensorSleepModeTime,
                    Boolean userSensorSleepModeAutomatic) {
        this.userSensorName = userSensorName;
        this.userSensorDescription = userSensorDescription;
        this.userSensorPumpWateringDuration = userSensorPumpWateringDuration;
        this.userSensorPumpWatering = userSensorPumpWatering;
        this.userSensorPumpWateringAutomatic = userSensorPumpWateringAutomatic;
        this.userSensorSleepModeTime = userSensorSleepModeTime;
        this.userSensorSleepModeAutomatic = userSensorSleepModeAutomatic;
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

    public Float getUserSensorPumpWateringDuration() {
        return userSensorPumpWateringDuration;
    }

    public void setUserSensorPumpWateringDuration(Float userSensorPumpWateringDuration) {
        this.userSensorPumpWateringDuration = userSensorPumpWateringDuration;
    }

    public Boolean getUserSensorPumpWateringAutomatic() {
        return userSensorPumpWateringAutomatic;
    }

    public void setUserSensorPumpWateringAutomatic(Boolean userSensorPumpWateringAutomatic) {
        this.userSensorPumpWateringAutomatic = userSensorPumpWateringAutomatic;
    }

    public Boolean getUserSensorPumpWatering() {
        return userSensorPumpWatering;
    }

    public void setUserSensorPumpWatering(Boolean userSensorPumpWatering) {
        this.userSensorPumpWatering = userSensorPumpWatering;
    }

    public Float getUserSensorSleepModeTime() {
        return userSensorSleepModeTime;
    }

    public void setUserSensorSleepModeTime(Float userSensorSleepModeTime) {
        this.userSensorSleepModeTime = userSensorSleepModeTime;
    }

    public Boolean getUserSensorSleepModeAutomatic() {
        return userSensorSleepModeAutomatic;
    }

    public void setUserSensorSleepModeAutomatic(Boolean userSensorSleepModeAutomatic) {
        this.userSensorSleepModeAutomatic = userSensorSleepModeAutomatic;
    }

    public Boolean getUserSensorNotifyDryCondition() {
        return userSensorNotifyDryCondition;
    }

    public void setUserSensorNotifyDryCondition(Boolean userSensorNotifyDryCondition) {
        this.userSensorNotifyDryCondition = userSensorNotifyDryCondition;
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

    @Exclude
    public Map<String, Object> toMapSensorWateringDuration() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("userSensorPumpWateringDuration", userSensorPumpWateringDuration);
        return result;
    }

    @Exclude
    public Map<String, Object> toMapSensorSleepModeTime() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("userSensorSleepModeTime", userSensorSleepModeTime);
        return result;
    }

    @Exclude
    public Map<String, Object> toMapSensorWatering() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("userSensorPumpWatering", userSensorPumpWatering);
        return result;
    }

    @Exclude
    public Map<String, Object> toMapSensorWateringAutomatic() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("userSensorPumpWateringAutomatic", userSensorPumpWateringAutomatic);
        return result;
    }

    @Exclude
    public Map<String, Object> toMapSensorSleepModeAutomatic() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("userSensorSleepModeAutomatic", userSensorSleepModeAutomatic);
        return result;
    }

    @Exclude
    public Map<String, Object> toMapSensorNotifyDryCondition() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("userSensorNotifyDryCondition", userSensorNotifyDryCondition);
        return result;
    }
}
