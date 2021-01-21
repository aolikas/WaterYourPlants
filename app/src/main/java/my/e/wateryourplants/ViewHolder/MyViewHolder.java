package my.e.wateryourplants.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import my.e.wateryourplants.R;

public class MyViewHolder extends RecyclerView.ViewHolder {

    public TextView sensorName;
    public TextView sensorDescription;
    public TextView sensorMoistureCondition;
    public TextView sensorTemperature;
    public View view;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        sensorName = itemView.findViewById(R.id.item_data_sensor_name);
        sensorDescription = itemView.findViewById(R.id.item_data_sensor_description);
        sensorMoistureCondition = itemView.findViewById(R.id.item_data_sensor_moisture_condition);
        sensorTemperature = itemView.findViewById(R.id.item_data_sensor_temperature);
        view = itemView;
    }
}
