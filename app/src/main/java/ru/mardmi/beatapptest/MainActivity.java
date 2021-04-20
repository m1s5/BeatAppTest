package ru.mardmi.beatapptest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    final SoundPoolPlayer soundPoolPlayer = new SoundPoolPlayer(this);

    EditText metronomeText, beatText, tempoText;
    Button startButton;

    Toast toast;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        soundPoolPlayer.soundPoolInit();

        metronomeText = findViewById(R.id.metroText);
        beatText = findViewById(R.id.beatText);
        tempoText = findViewById(R.id.tempoText);

        startButton = findViewById(R.id.startButton);

    }


    public void onClickStart(View view) {
        try {
            int x = 1 / Integer.parseInt(getTrimmedString(tempoText)); //Both Parse and Arithmetic exceptions
        } catch (Exception e) {
            toast = Toast.makeText(this, "Tempo should be number more than 0", Toast.LENGTH_LONG);
            toast.show();
            return;
        }

        enableInputs(false);
        soundPoolPlayer.stopMe(false);
        soundPoolPlayer.playPattern(getTrimmedString(metronomeText), Integer.parseInt(getTrimmedString(tempoText)));
        soundPoolPlayer.playPattern(getTrimmedString(beatText), Integer.parseInt(getTrimmedString(tempoText)));
    }

    private String getTrimmedString(EditText editText){
        return editText.getText().toString().trim();
    }

    public void onClickStop(View view) {
        enableInputs(true);
        soundPoolPlayer.stopMe(true);
    }

    private void enableInputs(boolean b) {
        metronomeText.setEnabled(b);
        beatText.setEnabled(b);
        tempoText.setEnabled(b);
        startButton.setEnabled(b);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        soundPoolPlayer.onDestroy();
    }
}