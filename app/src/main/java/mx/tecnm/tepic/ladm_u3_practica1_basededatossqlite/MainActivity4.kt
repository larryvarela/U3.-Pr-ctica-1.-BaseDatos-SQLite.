package mx.tecnm.tepic.ladm_u3_practica1_basededatossqlite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main1.*
import kotlinx.android.synthetic.main.activity_main1.listaConductores
import kotlinx.android.synthetic.main.activity_main4.*
import java.io.IOException
import java.io.OutputStreamWriter

class MainActivity4 : AppCompatActivity() {
    var idVehiculo = ArrayList<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)
        supportActionBar?.hide()

        button4.setOnClickListener {
            if (guardarConductores()){
                Toast.makeText(this,"CONDUCTORES GUARDADO CORRECTAMENTE",Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this,"ERROR AL GUARDAR",Toast.LENGTH_LONG).show()
            }
        }

        button5.setOnClickListener {
            if (guardarVehiculos()){
                Toast.makeText(this,"VEHICULOS GUARDADO CORRECTAMENTE",Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this,"ERROR AL GUARDAR",Toast.LENGTH_LONG).show()
            }
        }

        button6.setOnClickListener {
            if (guardarVehiculosConductores()){
                Toast.makeText(this,"GUARDADO CORRECTAMENTE",Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this,"ERROR AL GUARDAR",Toast.LENGTH_LONG).show()
            }
        }


    }


    fun guardarConductores() : Boolean{
        try {
            var datacontenido = ""
            val arreglo = Conductor(this).consultarCD()

            val archivo = OutputStreamWriter(openFileOutput("archivoConductores.txt", MODE_PRIVATE))

            (0 until arreglo.size).forEach {
                datacontenido += arreglo.get(it).ID.toString()+","+arreglo.get(it).Nombre+","+arreglo.get(it).Domicilio+","+
                        arreglo.get(it).NoLicencia+","+arreglo.get(it).Vence
            }

            archivo.write(datacontenido)
            archivo.flush()
            archivo.close()
            return true
        } catch (io: IOException){

            return false
        }
    }//guardarConductores

    fun guardarVehiculos() : Boolean{
        try {
            var datacontenido = ""
            val arreglo = Vehiculo(this).consultarVH()

            val archivo = OutputStreamWriter(openFileOutput("archivoVehiculos.txt", MODE_PRIVATE))

            (0 until arreglo.size).forEach {
                datacontenido += arreglo.get(it).ID.toString()+","+arreglo.get(it).Placa+","+arreglo.get(it).Marca+","+
                        arreglo.get(it).Modelo+","+arreglo.get(it).Año+","+arreglo.get(it).IdConductor
            }

            archivo.write(datacontenido)
            archivo.flush()
            archivo.close()
            return true
        } catch (io: IOException){

            return false
        }
    }//guardarVehiculos

    fun guardarVehiculosConductores() : Boolean{
        try {
            var datacontenido = ""
            val arreglo = Vehiculo(this).consultarVH()
            val arreglo2 = Conductor(this).consultarCD()

            val archivo = OutputStreamWriter(openFileOutput("archivoVHCD.txt", MODE_PRIVATE))

            (0 until arreglo.size).forEach {
                datacontenido += arreglo.get(it).ID.toString()+","+arreglo.get(it).Placa+","+arreglo.get(it).Marca+","+
                        arreglo.get(it).Modelo+","+arreglo.get(it).Año+","+arreglo.get(it).IdConductor
            }

            (0 until arreglo2.size).forEach {
                datacontenido += arreglo2.get(it).ID.toString()+","+arreglo2.get(it).Nombre+","+arreglo2.get(it).Domicilio+","+
                        arreglo2.get(it).NoLicencia+","+arreglo2.get(it).Vence
            }

            archivo.write(datacontenido)
            archivo.flush()
            archivo.close()
            return true
        } catch (io: IOException){

            return false
        }
    }//guardarVehiculos
}