package developer.android.laby3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class BmiDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi)

        val currentBmi  = intent.getDoubleExtra("bmi",0.0)
        findViewById<TextView>(R.id.bmiValue).text ="%.2f".format(currentBmi)

        findViewById<TextView>(R.id.bmiDescription).text = DataManager.getDescription(currentBmi)

        findViewById<Button>(R.id.returnButton).setOnClickListener { view ->
            finish()
        }
    }
}