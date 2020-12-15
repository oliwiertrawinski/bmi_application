package developer.android.laby3

import HistoryAdapter
import android.content.Context
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import developer.android.laby3.DataBase.DataBaseHelper



class HistoryActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var sensorManager: SensorManager
    private lateinit var background:ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        background = findViewById(R.id.history_background)

        val dbHelper = DataBaseHelper(applicationContext)
        val db = dbHelper.writableDatabase

        recyclerView = findViewById<View>(R.id.history_list) as RecyclerView

        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.adapter = HistoryAdapter(applicationContext, db)
    }

    override fun onResume() {
        super.onResume()
        var sensor = sensorManager.getSensorList(Sensor.TYPE_LIGHT).get(0)
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null) {
            var luxValue:Int = event.values[0].toInt()
            if (luxValue>SENSOR_CHANGE_VAL){
                background.setBackgroundColor(Color.WHITE)

            }else{
                background.setBackgroundColor(Color.GRAY)
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }
}


