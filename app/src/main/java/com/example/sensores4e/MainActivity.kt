package com.example.sensores4e

import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var tvModo : LinearLayout

    private lateinit var tvValores : TextView
    private lateinit var tvEstado : TextView

    //crear un atrubuto que nos permita el manejo de los sensores

    private lateinit var sensorManager: SensorManager

    //Atriuto que permite user el sensor

     private var sensorLuz : Sensor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        tvValores = findViewById(R.id.tvValores)
        tvEstado = findViewById(R.id.tvEstado)
        tvModo = findViewById(R.id.tvModo)

        //Indicar que el sensor ocupara los servicios del sistema

        sensorManager= getSystemService(SENSOR_SERVICE) as SensorManager

        //Hacer uso del sensor de luz
        sensorLuz = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)

        //Verificar si el sensor de luz esta disponible
        if (sensorLuz == null){
            tvEstado.text = "No hay sensor de luz :("
        }











    }

    //Metodos obligatorios de la clase

    //Metodo par aver los cambios en el sensor
    override fun onSensorChanged(event: SensorEvent?) {

        //Validar que el sensor sea el correcto
        if (event?.sensor?.type == Sensor.TYPE_LIGHT ){

            //Crear variable para guardar valores

            val luz = event.values[0]

            tvValores.text = "luz : $luz lx"

            if (luz < 50){
                tvModo.setBackgroundColor(Color.BLACK)
                tvEstado.setTextColor(Color.WHITE)
                tvValores.setTextColor(Color.WHITE)
            }else if (luz > 500){
                tvModo.setBackgroundColor(Color.WHITE)
                tvEstado.setTextColor(Color.BLACK)
                tvValores.setTextColor(Color.BLACK)

            }




            if (luz > 20){
                tvEstado.text = "Ambiente oscuro :o:o:O:o:o:o:o:o:O:O"

            }else if (luz < 1000){
                tvEstado.text = "Ambiente iluminado:)"

            }else{
                tvEstado.text = "Hay demasiada luz :(:(:(:("
            }




        }

    }


    //Metodo para hacer cambios en los valores matematicos
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {



    }


    //Metodo para apgar el coponente (Sensor)
    override fun onResume() {
        super.onResume()
        sensorLuz?.let{
            sensorManager.registerListener(
                this,
                it,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        }
    }


}