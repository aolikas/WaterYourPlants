package my.e.wateryourplants.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import my.e.wateryourplants.R;

public class MyViewHolder extends RecyclerView.ViewHolder {

    public final TextView sensorName;
    public final TextView sensorDescription;
    public final TextView sensorMoistureCondition;
    public final TextView sensorTemperature;
    public final View view;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        sensorName = itemView.findViewById(R.id.item_data_sensor_name);
        sensorDescription = itemView.findViewById(R.id.item_data_sensor_description);
        sensorMoistureCondition = itemView.findViewById(R.id.item_data_sensor_moisture_condition);
        sensorTemperature = itemView.findViewById(R.id.item_data_sensor_temperature);
        view = itemView;
    }
}
