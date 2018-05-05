package com.example.adria.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.media.MediaPlayer;
import android.support.v7.widget.Toolbar;

public class home extends AppCompatActivity implements View.OnClickListener {

    //variable para mi array bidimensional
    // [0][0]   [0][1]  [0][2]
    // [1][0]   [1][1]  [1][2]
    // [2][0]   [2][1]  [2][2]
    private Button[][] buttons = new Button[3][3];
    //de quien es el Turno
    private boolean xturn = true;
    //para contar los turnos, si hay 9 es empate por la cantidad de casillas
    private int rondaturno;
    //puntos xP!!
    private int xpuntos;
    private int opuntos;
    //para mostrar los puntos de cada jugador :3
    private TextView textViewX;
    private TextView textViewO;
    // musiquitaaaaa :3
    MediaPlayer audioo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        audioo = MediaPlayer.create(home.this,R.raw.audio);


        textViewX = findViewById(R.id.Jugadorx);
        textViewO = findViewById(R.id.Jugadoro);

        //Al array que cree arriba, le asigno los botones en un loop
        for (int i=0; i<3; i++){
            for (int j=0; j<3; j++){
                //ira corriendo por los id de los botones en el array 00,01,02...
                String IDboton = "button_" + i + j;
                //pa no tener que buscar uno a uno los ID de los botones
                int IDres = getResources().getIdentifier(IDboton,"id",getPackageName());
                buttons[i][j] = findViewById(IDres);

                //pones listenerssssss
                buttons[i][j].setOnClickListener(this);

            }

        }
        Button reset = findViewById(R.id.newgame);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            resetGame();
            }
        });

    }

    public void playIt(View v){
        if (audioo.isPlaying()) {
            audioo.pause();
        }
        else {
            audioo.start();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        audioo.pause();
    }

    @Override
    public void onClick(View v) {
        //Este if es pa chekear que ese boton que fue clikeado tiene un String vacio
        if(!((Button) v).getText().toString().equals("")){
            return;
        }
        if(xturn){
            ((Button)v).setText("X");
        }
        else{
            ((Button)v).setText("O");
        }
        rondaturno++;

        if (ganoalguieen()){
            if (xturn){
                xGana();
            }
            else{
                oGana();
            }
        }
        else if (rondaturno == 9){
            empate();
        }
        else{
            xturn = !xturn;
        }

    }

    private boolean ganoalguieen(){
        String[][] casilla = new String[3][3];

        for (int i=0; i<3; i++){
            for (int j=0; j<3; j++){
                casilla[i][j] = buttons [i][j].getText().toString();
            }
        }
        //chekeando h y v
        for (int i=0; i<3; i++){
            if (casilla[i][0].equals(casilla[i][1]) && casilla[i][0].equals(casilla[i][2]) && !casilla[i][0].equals("")){
                return true;
            }

        }
        for (int i=0; i<3; i++) {
            if (casilla[0][i].equals(casilla[1][i]) && casilla[0][i].equals(casilla[2][i]) && !casilla[0][i].equals("")) {
                return true;
            }

        }
        //chekear diagobakes
            if (casilla[0][0].equals(casilla[1][1]) && casilla[0][0].equals(casilla[2][2]) && !casilla[0][0].equals("")) {
                return true;
            }
        if (casilla[0][2].equals(casilla[1][1]) && casilla[0][2].equals(casilla[2][0]) && !casilla[0][2].equals("")) {
            return true;
        }

        return false;


    }

    private void xGana(){
        xpuntos++;
        Toast.makeText(this,"X GANA!", Toast.LENGTH_SHORT).show();
        sumarpuntosText();
        resetCasillas();
    }
    private void oGana(){
        opuntos++;
        Toast.makeText(this,"O GANA!", Toast.LENGTH_SHORT).show();
        sumarpuntosText();
        resetCasillas();
    }
    private void empate(){
        Toast.makeText(this,"EMPATE!", Toast.LENGTH_SHORT).show();
        resetCasillas();
    }

    private void sumarpuntosText(){
        textViewX.setText("X: " + xpuntos);
        textViewO.setText("O: " + opuntos);
    }
    private void resetCasillas(){
        for (int i=0; i<3; i++){
            for (int j=0; j<3; j++) {
                buttons[i][j].setText("");
            }
        }
        rondaturno = 0;
        xturn = true;
    }

    private void resetGame(){
        xpuntos = 0;
        opuntos = 0;
        sumarpuntosText();
        resetCasillas();


    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("rondaturno", rondaturno);
        outState.putInt("xpuntos", xpuntos);
        outState.putInt("opuntos", opuntos);
        outState.putBoolean("xturn",xturn);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        rondaturno = savedInstanceState.getInt("ronsaturno");
        xpuntos = savedInstanceState.getInt("xpuntos");
        opuntos = savedInstanceState.getInt("opuntos");
        xturn = savedInstanceState.getBoolean("xturn");

    }
}


