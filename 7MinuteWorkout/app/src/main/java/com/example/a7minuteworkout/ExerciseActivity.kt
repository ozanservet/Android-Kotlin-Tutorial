package com.example.a7minuteworkout

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_exercise_activity.*
import kotlinx.android.synthetic.main.dialog_custom_back_confirmation.*
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private var restTimer: CountDownTimer? = null       // this will countDown the timer.
    private var restProgress = 0        // how many seconds we passed. Don't forget we will count from backwards so 10-restProgress will work for us.
    private var restTimerDuration : Long = 30       // How many seconds user will rest

    private var exerciseTimer: CountDownTimer? = null
    private var exerciseProgress = 0
    private var exerciseTimerDuration: Long = 10        // How many seconds will be an exercise long

    private var exerciseList: ArrayList<ExerciseModel>? = null      // all exercises will be inside this array
    private var currentExercisePosition = -1        // we will store current exercise position not the array index.

    private var tts: TextToSpeech? = null       // tts(text to speech) so it is for anouncing exercise name and we will store inside this variable
    private var player: MediaPlayer? = null

    private var exerciseAdapter: ExerciseStatusAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise_activity)

        setSupportActionBar(toolbar_exercise_activity)
        val actionbar = supportActionBar
        if(actionbar != null){
            // we are activating our toolbar
            actionbar.setDisplayHomeAsUpEnabled(true)
        }

        // this is for back button.
        toolbar_exercise_activity.setNavigationOnClickListener {
            customDialogForBackButton()     // we are asking are you sure? Yes/No question using customDialog
        }

        tts = TextToSpeech(this, this)

        exerciseList = Constants_Exercises.defaultExerciseList()        // we initialized exercises

        setupRestView()     // we will count down from 10 to 0

        setupExerciseStatusRecyclerView()
    }

    // Creating restTimer, counting down and representing it on UI
    private fun setRestProgressBar(){
        progressBar.progress = restProgress
        // 10 seconds, 1second down every second. To modify from millisecond to second just delete three 0.
        restTimer = object: CountDownTimer(restTimerDuration * 1000, 1000){
            override fun onTick(millisUntilFinished: Long) {
                // there are two function we can override onTick() and finish(). onTick is for implementing what will happen in each countDown.
                restProgress++        // increase one second in a second :)
                progressBar.progress = restTimerDuration.toInt() - restProgress        // we are counting from 10 to 0.
                tvTimer.text = (restTimerDuration.toInt() - restProgress).toString()         // show seconds in UI.
            }

            // After we finished counting down we sent a message.
            override fun onFinish() {
                currentExercisePosition++

                // this will be the current exercise. This will help us in RecyclerView.
                exerciseList!![currentExercisePosition].setIsSelected(true)
                // this is because we changed the data inside exerciseList
                exerciseAdapter!!.notifyDataSetChanged()

                setupExerciseView()
            }
        }.start()       // starting timer.
    }

    // Creating restTimer, counting down and representing it on UI
    private fun setExerciseProgressBar(){
        progressBarExercise.progress = exerciseProgress
        // 30 seconds, 1second down every second. To modify from millisecond to second just delete three 0.
        exerciseTimer = object: CountDownTimer(exerciseTimerDuration * 1000, 1000){
            override fun onTick(millisUntilFinished: Long) {
                // there are two function we can override onTick() and finish(). onTick is for implementing what will happen in each countDown.
                exerciseProgress++        // increase one second in a second :)
                progressBarExercise.progress = exerciseTimerDuration.toInt() - exerciseProgress        // we are counting from 30 to 0.
                tvExerciseTimer.text = (exerciseTimerDuration.toInt() - exerciseProgress).toString()         // show seconds in UI.
            }

            // After we finished counting down we sent a message.
            override fun onFinish() {
                if(currentExercisePosition < exerciseList?.size!! - 1){
                    // the exercise finished. So the selected exercise is not the one now and we will set it after rest.
                    exerciseList!![currentExercisePosition].setIsSelected(false)
                    // this exercise is completed.
                    exerciseList!![currentExercisePosition].setIsCompleted(true)
                    // this is because we changed the data inside exerciseList
                    exerciseAdapter!!.notifyDataSetChanged()
                    setupRestView()
                }else{
                    // it will finish the ExerciseActivity
                    finish()
                    // and the finish screen will show up
                    val intent = Intent(this@ExerciseActivity, FinishActivity::class.java)
                    startActivity(intent)
                }
            }
        }.start()       // starting timer.
    }

    override fun onDestroy() {
        if(restTimer != null){
            restTimer!!.cancel()
            restProgress = 0
        }

        if(exerciseTimer != null){
            exerciseTimer!!.cancel()
            exerciseProgress = 0
        }

        if(tts != null){
            tts!!.stop()
            tts!!.shutdown()
        }

        if(player != null){
            player!!.stop()
        }

        super.onDestroy()
    }

    // this function will call setRestProgressBar()
    // which will count down from 10 to 0
    // and also if it is restTimer is null or smt idk, we need to reset some numbers. You can also use onDestroy() fun for it.
    private fun setupRestView(){

        try {
            // Adding sound
            //val soundURI = Uri.parse("android://com.example.a7minuteworout/" + R.raw.press_start)
            //player = MediaPlayer().create(applicationContext, soundURI)
            // Another way
            player = MediaPlayer.create(applicationContext, R.raw.press_start)        // applicationContext is this one, 2.parameter is the location of sound.
            player!!.isLooping = false      // the sound will play only once
            player!!.start()
        }catch (e: Exception){
            e.printStackTrace()
        }

        llRestView.visibility = View.VISIBLE        // After exercise the rest should be visible
        llExerciseView.visibility = View.GONE       // and exercise should be non visible while rest view.

        if(restTimer != null){
            restTimer!!.cancel()
            restProgress = 0
        }

        tvUpcomingExerciseName.text = exerciseList!![currentExercisePosition + 1].getName()     // we are showing the next exercise name
        setRestProgressBar()        // showing progressBar
    }

    // this function will call setRestProgressBar()
    // which will count down from 30 to 0
    // and also if it is restTimer is null or smt idk, we need to reset some numbers. You can also use onDestroy() fun for it.
    private fun setupExerciseView(){

        // we are using one page for two un related progress bars.
        // So make previous one invisible and new one visible.
        llRestView.visibility = View.GONE
        llExerciseView.visibility = View.VISIBLE

        if(exerciseTimer != null){
            exerciseTimer!!.cancel()
            exerciseProgress = 0
        }

        speakOut(exerciseList!![currentExercisePosition].getName())     // this will tell the exercise name by sound.

        setExerciseProgressBar()

        ivImage.setImageResource(exerciseList!![currentExercisePosition].getImage())
        tvExerciseName.text = exerciseList!![currentExercisePosition].getName()
    }

    override fun onInit(status: Int) {

        // if TextToSpeech works
        if(status == TextToSpeech.SUCCESS){
            val result = tts!!.setLanguage(Locale.US)       // the language for speech
            // we are checking the situations the language might not be supported or not downloaded in phone.
            if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e("TTS", "The Language specified is not supported!")
            }else{
                Log.e("TTS", "Initialization Failed!")
            }
        }
    }

    private fun speakOut(text: String){
        // we pass text for speech queue_flush and it is for directly speak instead of wait if there is some speech currently.
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    private fun setupExerciseStatusRecyclerView(){
        rvExerciseStatus.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        exerciseAdapter = ExerciseStatusAdapter(exerciseList!!, this)
        rvExerciseStatus.adapter = exerciseAdapter
    }

    private fun customDialogForBackButton(){

        val customDialog = Dialog(this)
        customDialog.setContentView(R.layout.dialog_custom_back_confirmation)

        customDialog.tvYes.setOnClickListener {
            finish()        // finishing ExerciseActivity activity
            customDialog.dismiss()
        }
        customDialog.tvNo.setOnClickListener {
            customDialog.dismiss()
        }

        customDialog.show()
    }

}