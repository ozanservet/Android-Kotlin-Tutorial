package com.example.a7minuteworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_b_m_i.*
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {

    val METRIC_UNIT_VIEW = "METRIC_UNIT_VIEW"
    val US_UNIT_VIEW = "US_UNIT_VIEW"
    var currentVisibleView: String = METRIC_UNIT_VIEW       // current unit view that we are showing

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_b_m_i)

        // adding actionBar.
        setSupportActionBar(toolbar_bmi_activity)
        val actionbar = supportActionBar
        if(actionbar != null){
            actionbar.setDisplayHomeAsUpEnabled(true)
            actionbar.title = "CALCULATE BMI"
        }
        toolbar_bmi_activity.setNavigationOnClickListener {
            onBackPressed()
        }

        btnCalculateUnits.setOnClickListener {

            if(currentVisibleView.equals(METRIC_UNIT_VIEW)){
                if(validateMetricUnits()){
                    val heightValue : Float = etMetricUnitHeight.text.toString().toFloat() / 100        // metric calculation
                    val weightValue : Float = etMetricUnitWeight.text.toString().toFloat()

                    val bmi = weightValue / (heightValue * heightValue)
                    displayBMIResult(bmi)
                }else{
                    Toast.makeText(this, "Please enter valid value.", Toast.LENGTH_SHORT).show()
                }
            }else{
                // For US Unit
                if(validateUSUnits()){
                    val usUnitHeightValueFeet : Float = etUsUnitHeightFeet.text.toString().toFloat()
                    val usUnitHeightValueInch : Float = etUsUnitHeightInch.text.toString().toFloat()
                    val usUnitValueWeight : Float = etUsUnitWeight.text.toString().toFloat()

                    val  heightValue = usUnitHeightValueInch + usUnitHeightValueFeet * 12
                    val bmi = 703 * (usUnitValueWeight / (heightValue * heightValue))
                    displayBMIResult(bmi)
                }else{
                    Toast.makeText(this, "Please enter valid value.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        makeVisibleMetricUnitsView()

        rgUnits.setOnCheckedChangeListener { group, checkedId ->
            if(checkedId == R.id.rbMetricUnits){
                makeVisibleMetricUnitsView()
            }else{
                makeVisibleUsUnitsView()
            }
        }
    }

    // Metric Units View adjustments
    private fun makeVisibleMetricUnitsView(){

        // we are storing which unit we are working for now inside this variable.
        currentVisibleView = METRIC_UNIT_VIEW

        // make metric measurement text spaces visible
        tilMetricUnitWeight.visibility = View.VISIBLE
        tilMetricUnitHeight.visibility = View.VISIBLE

        // clear the texts if there is any.
        etMetricUnitHeight.text!!.clear()
        etMetricUnitWeight.text!!.clear()

        // make US Units text spaces not visible
        llUsUnitsView.visibility = View.GONE

        // the result texts are not visible now.
        llDisplayBMIResult.visibility = View.INVISIBLE
    }

    // Us Units View adjustments
    private fun makeVisibleUsUnitsView(){

        // we are storing which unit we are working for now inside this variable.
        currentVisibleView = US_UNIT_VIEW

        // make tilMetricUnitWeight and tilMetricUnitHeight(metric measurements) not visible
        tilMetricUnitWeight.visibility = View.GONE
        tilMetricUnitHeight.visibility = View.GONE

        // clear the texts if there is any.
        etUsUnitWeight.text!!.clear()
        etUsUnitHeightFeet.text!!.clear()
        etUsUnitHeightInch.text!!.clear()

        // make US Units text spaces visible
        llUsUnitsView.visibility = View.VISIBLE

        // the result texts are not visible now.
        llDisplayBMIResult.visibility = View.INVISIBLE
    }

    private fun displayBMIResult(bmi: Float){
        val bmiLabel: String
        val bmiDescription: String

        // According to bmi we are categorising body shape
        if (bmi.compareTo(15f) <= 0) {
            bmiLabel = "Very severely underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0
        ) {
            bmiLabel = "Severely underweight"
            bmiDescription = "Oops!You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0
        ) {
            bmiLabel = "Underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0
        ) {
            bmiLabel = "Normal"
            bmiDescription = "Congratulations! You are in a good shape!"
        } else if (java.lang.Float.compare(bmi, 25f) > 0 && java.lang.Float.compare(
                bmi,
                30f
            ) <= 0
        ) {
            bmiLabel = "Overweight"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0
        ) {
            bmiLabel = "Obese Class | (Moderately obese)"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0
        ) {
            bmiLabel = "Obese Class || (Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        } else {
            bmiLabel = "Obese Class ||| (Very Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        }

        // the result texts are visible now.
        llDisplayBMIResult.visibility = View.VISIBLE

        // this is used to round the result value to two decimals after the decimal point.
        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2,RoundingMode.HALF_EVEN).toString()

        tvBMIValue.text = bmiValue
        tvBMIType.text = bmiLabel
        tvBMIDescription.text = bmiDescription
    }

    private fun validateUSUnits(): Boolean{
        var isValid = true

        when {
            etUsUnitWeight.text.toString().isEmpty() -> isValid = false
            etUsUnitHeightInch.text.toString().isEmpty() -> isValid = false
            etUsUnitHeightFeet.text.toString().isEmpty() -> isValid = false
        }

        return isValid
    }

    private fun validateMetricUnits(): Boolean{
        var isValid = true

        if(etMetricUnitWeight.text.toString().isEmpty())
            isValid = false
        else if(etMetricUnitHeight.text.toString().isEmpty())
            isValid = false

        return isValid
    }
}