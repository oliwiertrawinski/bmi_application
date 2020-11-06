package developer.android.laby3.bmiCalc

import developer.android.laby3.bmiCalc.Bmi

class BmiForInLbs(
private val mass: Double,
private val height: Double
) : Bmi {
    override fun count(): Double =
        703 * mass / (height * height)


}