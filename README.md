# WaterYourPlants
This folder contains the source code for the Water Your Plants App.
As an example, this App is launched on PlayStore. 

# Introduction
Water Your Plants is an IoT project for automatic watering any plants. 
This project consists of three main parts: 
Android App for receiving data from sensors and controlling them. 
Arduino sketch for moisture and temperature sensor. 
Arduino sketch for controlling water pump. All Arduino sketches locate in a different folder (WaterYourPlantsAduino). 
 As an examample, this App is launched on PlayStore. 
 
 # Getting Started
 1. Install Android Studio, if you don't already have it.
 2. Download the sample.
 3. Import the sample into Android Studio.
 4. Add Firebase to this Projects. [Here more detailed instruction.](https://firebase.google.com/docs/android/setup)
 5. During the App registration in the Android package name enter:
    ```
    my.e.wateryourplants
    ```
 6. Download google-services.json file and move it into the app-level directory of the App.
 7. Within your Firebase Project select the Authentication panel and click the Get started.
 8. Click Email/Password and turn on the first Enable switch and click Save.
 9. Then in your Firebase Project select the Realtime Database panel and click the Create Database.
 10. Choose mode for your database.
 11. Database Rules
     ```
     {
      "rules" : {
      // User profiles are only readable and writable by the user who owns it
      "Users" : {
      "$user_id" : {
        ".write" : "$user_id === auth.uid && auth !== null",
        ".read" :  "$user_id === auth.uid && auth !== null"
         }
       }
      }
     }
     ```
 12. In Android Studio check Firebase connection Tools/Firebase/Realtime Database/ Get started with Realmtime Databse    
 12. Build and run project in Android Studio. 
 13. If everything is ok, go to Build/ Generate Signed Bundle/APK and generate this Project.
 14. Download APK to your phone (don't forget turn on Developer options on your phone).
 15. Run the App. Register/login user.
 16. Next step is Arduino sketches and sensors. Go to the [WaterYourPlantsArduino](https://github.com/aolikas/WaterYourPlantsAduino)
