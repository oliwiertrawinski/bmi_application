package developer.android.laby3.bmiCalc

import developer.android.laby3.bmiCalc.Bmi

class BmiForCmKg(
    private val mass: Double,
    private val height: Double
) : Bmi {
    override fun count(): Double {
        require(mass>0)
        require(height>0)
        return mass / (height * height / 10000)
    }

}