package developer.android.laby3

import java.time.LocalDateTime
import java.util.*

data class BmiInfo(val title: String,val description:String, val lowerLimit: Double, val upperLimit: Double ){
    override fun toString(): String {
        return title
    }
    fun isInLimit(value: Double): Boolean {
        return value in lowerLimit..upperLimit
    }
}

data class historyRecord(var bmi: Double, var mass:Double, var height: Double, var date: String, var unit:Int){

    fun getLine1():String{
        val builder = StringBuilder()
        builder.append("Bmi: ")
            .append(bmi)
            .append("; Date: ")
            .append(date)

        return builder.toString()
    }

    fun getLine2():String{
        var massUnit = "kg"
        var heightUnit = "cm"
        if(unit == BMI_IN_LBS){
            massUnit = "lbs"
            heightUnit = "in"
        }
        val builder = StringBuilder()
        builder.append("Mass: ")
            .append(mass)
            .append(massUnit)
            .append("; Height: ")
            .append(height)
            .append(heightUnit)

        return builder.toString()
    }

}