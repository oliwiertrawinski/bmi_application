package developer.android.laby3

object DataManager {

    val bmiInfo = ArrayList<BmiInfo>()
    var bmiHistory = ArrayList<HistoryRecord>()//array list przechwujaca floaty

    init {
        bmiInfo.add(
            BmiInfo(
                "wygłodzenie",
                "Przy Twoim BMI zalecany jest przynajmniej miesięczny pobyt u babci.",
                0.0,
                16.99
            )
        )
        bmiInfo.add(
            BmiInfo(
                "niedowaga",
                "Przy Twoim BMI możesz zjeść kilka pączków bez wyrzutów sumienia.",
                17.0,
                18.49
            )
        )
        bmiInfo.add(BmiInfo("waga prawidłowa", "Jestes w sam raz, tak trzymać!", 18.5, 24.99))
        bmiInfo.add(
            BmiInfo(
                "nadwaga",
                "Przy Twoim BMI wskazane jest troche ruchu oraz przeanalizowanie swojeje diety. Albo po prostu nie wygrałeś na życiowej loterii dobrej przemiany materii.",
                25.0,
                29.99
            )
        )
        bmiInfo.add(
            BmiInfo(
                "otyłość",
                "To juz nie przelewki tylko otyłość - zalecany jest kontakt z lekarzem.",
                30.0,
                60.0
            )
        )
    }

    fun getDescription(value: Double): String {
        for (info in bmiInfo) {
            if (info.isInLimit(value)) {
                return info.description
            }
        }
        return "Niepoprawna wartość"
    }

}

data class BmiInfo(val title: String,val description:String, val lowerLimit: Double, val upperLimit: Double ){
    override fun toString(): String {
        return title
    }
    fun isInLimit(value: Double): Boolean {
        return value in lowerLimit..upperLimit
    }
}


