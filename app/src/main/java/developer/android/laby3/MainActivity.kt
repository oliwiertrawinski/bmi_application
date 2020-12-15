package developer.android.laby3

import android.content.ContentValues
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import developer.android.laby3.DataBase.DataBaseHelper
import developer.android.laby3.DataBase.TableInfo
import developer.android.laby3.bmiCalc.BmiForCmKg
import developer.android.laby3.bmiCalc.BmiForInLbs
import developer.android.laby3.databinding.ActivityMainBinding



import kotlinx.android.synthetic.main.activity_main.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {
    private var currentUnit = BMI_CM_KG

    lateinit var binding: ActivityMainBinding


    var currentBmi:Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Toast.makeText(applicationContext, "Trkst do wyswietlenia", Toast.LENGTH_SHORT).show()

        val dbHelper = DataBaseHelper(applicationContext)
        val db = dbHelper.writableDatabase


        buttonCountBmi.setOnClickListener{
            var isCorrect = true

            if (textNoteMass.text.isEmpty()) {
                textNoteMass.error = getString(R.string.mass_is_empty)
                isCorrect = false;
            } else if (!isMassCorrect(textNoteMass.text.toString().toDouble())) {
                textNoteMass.error = getString(R.string.mass_is_incorrect)
                isCorrect = false;
            }
            if(textNoteHeight.text.isEmpty())
            {
                textNoteHeight.error = getString(R.string.height_is_empty)
                isCorrect = false;
            } else if(!isHeightCorrect(textNoteHeight.text.toString().toDouble()))
            {
                textNoteHeight.error = getString(R.string.height_is_incorrect)
                isCorrect = false;
            }


            if(isCorrect) {
                val height = String.format("%.2f",textNoteHeight.text.toString().toDouble()).toDouble()
                val mass = String.format("%.2f",textNoteMass.text.toString().toDouble()).toDouble()
                if(currentUnit == BMI_CM_KG){
                    currentBmi = BmiForCmKg(mass, height).count()
                }else if(currentUnit == BMI_IN_LBS){
                    currentBmi = BmiForInLbs(mass, height).count()
                } else{
                    currentBmi = INVALID_UNIT.toDouble()
                }
                currentBmi = String.format("%.2f", currentBmi).toDouble()
                textLabelResult.text = currentBmi.toString()
                setColor(currentBmi)
                var historyRecord = HistoryRecord(
                    currentBmi,
                    mass,
                    height,
                    getCurrentDate(),
                    currentUnit
                )


                val value = ContentValues()
                value.put("mass", mass)
                value.put("height", height)
                value.put("unit", currentUnit)
                value.put("bmi", currentBmi)
                value.put("date", getCurrentDate())

                db.insertOrThrow(TableInfo.TABLE_NAME, null, value)
            }
        }

        textLabelResult.setOnClickListener{
            val intent = Intent(this, BmiDetailsActivity::class.java)
            intent.putExtra("bmi", currentBmi)
            startActivityForResult(intent, 0)
        }
    }

    private fun setColor(res: Double) {
        when (res) {
            in 0.0..18.49 -> textLabelResult.setTextColor(Color.BLUE)
            in 18.5..24.99 -> textLabelResult.setTextColor(Color.GREEN)
            else -> { // Note the block
                textLabelResult.setTextColor(Color.RED)
            }
        }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("currentBmi", textLabelResult.text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        textLabelResult.text = savedInstanceState.getString("currentBmi")
        setColor(currentBmi)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_history -> {
                val intent = Intent(this, HistoryActivity::class.java)
                startActivityForResult(intent, 0)
                true
            }
            R.id.action_change_unit -> {
                changeUnit()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun changeUnit() {
        if(currentUnit == BMI_CM_KG) {
            currentUnit = BMI_IN_LBS
            textLabelMass.setText("Mass [lbs]")
            textLabelHeight.setText("Height [in]")
        }else {
            currentUnit = BMI_CM_KG
            textLabelMass.setText("Mass [kg]")
            textLabelHeight.setText("Height [cm]")
        }
    }

    private fun isHeightCorrect(height: Double):Boolean{
        var isCorrect = false;
        if(currentUnit == BMI_CM_KG ){
            isCorrect = height in MIN_CM..MAX_CM
        }
        if(currentUnit == BMI_IN_LBS){
            isCorrect = height in cmToIn(MIN_CM)..cmToIn(MAX_CM)
        }
        return isCorrect
    }

    private fun isMassCorrect(mass: Double):Boolean{
        var isCorrect = false;
        if(currentUnit == BMI_CM_KG ){
            isCorrect = mass in MIN_KG..MAX_KG
        }
        if(currentUnit == BMI_IN_LBS){
            isCorrect = mass in kgToLbs(MIN_KG)..kgToLbs(MAX_KG)
        }
        return isCorrect
    }

    fun kgToLbs(mass: Double):Double{
        return 2.20462*mass
    }

    fun cmToIn(height: Double):Double{
        return 0.393701*height
    }

    fun getCurrentDate(): String {
        val dateFormatter: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        dateFormatter.setLenient(false)
        val today = Date()
        val s: String = dateFormatter.format(today)
        return s
    }

}