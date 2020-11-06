package developer.android.laby3

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

object DataManager {

    val bmiInfo = ArrayList<BmiInfo>()
    var bmiHistory = ArrayList<historyRecord>()//array list przechwujaca floaty

    init{
        bmiInfo.add(BmiInfo("wygłodzenie", "Przy Twoim BMI zalecany jest przynajmniej miesięczny pobyt u babci.",0.0, 16.99))
        bmiInfo.add(BmiInfo("niedowaga", "Przy Twoim BMI możesz zjeść kilka pączków bez wyrzutów sumienia.",17.0, 18.49))
        bmiInfo.add(BmiInfo("waga prawidłowa", "Jestes w sam raz, tak trzymać!", 18.5, 24.99))
        bmiInfo.add(BmiInfo("nadwaga", "Przy Twoim BMI wskazane jest troche ruchu oraz przeanalizowanie swojeje diety. Albo po prostu nie wygrałeś na życiowej loterii dobrej przemiany materii.",25.0, 29.99))
        bmiInfo.add(BmiInfo("otyłość", "To juz nie przelewki tylko otyłość - zalecany jest kontakt z lekarzem.",30.0, 60.0))
    }

    fun  getDescription(value:Double): String {
        for (info in bmiInfo){
            if (info.isInLimit(value)){
                return info.description
            }
        }
        return "Niepoprawna wartość"
    }

    fun addLogToHistory(log: historyRecord){
        if(bmiHistory.size == 10){
            bmiHistory.removeAt(bmiHistory.size - 1)
        }
        bmiHistory.add(0,log)
    }

    fun loadData(applicationContext:Context) {
        var sharedPref = applicationContext.getSharedPreferences("SHARED_PREFS_FILE", Context.MODE_PRIVATE )
        var gson = com.google.gson.Gson()
        var json = sharedPref.getString("History", null)
        var type = object: TypeToken<ArrayList<historyRecord>>() {}.type
        if(!json.isNullOrBlank()){
            bmiHistory = gson.fromJson(json,type)
        }else {
            bmiHistory = ArrayList<historyRecord>()
        }
    }

    fun saveData( applicationContext: Context){
        var sharedPref = applicationContext.getSharedPreferences("SHARED_PREFS_FILE", Context.MODE_PRIVATE )
        var editor = sharedPref.edit()
        var gson = com.google.gson.Gson()
        var json = gson.toJson(bmiHistory)
        editor.putString("History", json)
        editor.apply()
    }

}