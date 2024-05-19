package com.example.memorization.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.memorization.R;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class VoiceActivity extends AppCompatActivity
{
    EditText et_speech;
    Button btn_micToggle;

    SpeechRecognizer speechRecognizer;

    Intent SpeechIntent;
    TextToSpeech t1;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_voice);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) ->
        {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        checkPermission();

        et_speech = findViewById(R.id.et_speech);
        btn_micToggle = findViewById(R.id.btn_toggleMic);
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        SpeechIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        SpeechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        SpeechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        speechRecognizer.setRecognitionListener(new RecognitionListener()
        {
            @Override
            public void onReadyForSpeech(Bundle params)
            {

            }

            @Override
            public void onBeginningOfSpeech()
            {

            }

            @Override
            public void onRmsChanged(float rmsdB)
            {

            }

            @Override
            public void onBufferReceived(byte[] buffer)
            {

            }

            @Override
            public void onEndOfSpeech()
            {

            }

            @Override
            public void onError(int error)
            {

            }

            @Override
            public void onResults(Bundle bundle)
            {
                Toast.makeText(VoiceActivity.this, "ONRESULTSSSSSSS", Toast.LENGTH_SHORT).show();
                ArrayList<String> matches = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                if(matches != null){
                    et_speech.setText(matches.get(0));
                    Log.d("Matches: " , " " + matches.get(0));


                }
            }

            @Override
            public void onPartialResults(Bundle partialResults)
            {

            }

            @Override
            public void onEvent(int eventType, Bundle params)
            {

            }
        });

        et_speech.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                Timer timer = new Timer();
                timer.schedule(new TimerTask()
                {
                    @Override
                    public void run()
                    {
                        String str = et_speech.getText().toString();
                        Log.d("STRRRR,", "STRR: " + str);
                        //t1.speak(str, TextToSpeech.QUEUE_FLUSH, null, null);

                    }
                }, 1000);


            }



            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });


        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener()
        {
            @Override
            public void onInit(int status)
            {
                if(status != TextToSpeech.ERROR){
                    t1.setLanguage(Locale.US);
                }
            }
        });
        btn_micToggle.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                switch(event.getAction()){
                    case MotionEvent.ACTION_UP:
                        speechRecognizer.stopListening();
                        et_speech.setHint("You will see input here");
                        break;
                    case MotionEvent.ACTION_DOWN:
                        et_speech.setText("");
                        et_speech.setHint("Listening.....");
                        speechRecognizer.startListening(SpeechIntent);
                        break;
                }
                return false;
            }
        });


    }

    private void checkPermission(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(!(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED)){
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("Package: " + getPackageName()));
                startActivity(intent);
                finish();
            }
        }
    }
}