package mx.tecnm.tepic.ladm_u3_practica1_basededatossqlite

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main3.*

class MainActivity3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        supportActionBar?.hide()

        button2.setOnClickListener {
            ConsultaLicenciasVencidas()
        }//btn

        button3.setOnClickListener {
            ConsultaSinVehiculo()
        }

        button7.setOnClickListener {
            Asignados()
        }

        button8.setOnClickListener {
            ConsultaVehiculosAños()
        }

    }

    fun ConsultaLicenciasVencidas(){
        val arregloConductor = Conductor(this).LicenciaVencida()
        listaConsultas.adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arregloConductor)

    }//ConsultaLicenciasVencidas

    fun ConsultaSinVehiculo(){
        val arregloConductor = Conductor(this).SinAsignarVehiculo()

        listaConsultas.adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arregloConductor)
    }//ConsultasSinVehiculo

    fun ConsultaVehiculosAños(){
        var input = EditText(this)
        input.hint = "NUMERO DE AÑOS"
        input.inputType = InputType.TYPE_CLASS_NUMBER

        AlertDialog.Builder(this)
            .setTitle("ATENCIÓN")
            .setMessage("VEHICULOS QUE CUENTEN CON N CANTIDAD DE AÑOS")
            .setView(input)
            .setPositiveButton("ACEPTAR"){d,i->
                if(!input.text.isEmpty()){
                    val arregloVehiculo = Vehiculo(this).consultarVehiculosNAños(input.text.toString().toInt())
                    listaConsultas.adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arregloVehiculo)
                }
            }
            .setNegativeButton("CENCELAR"){d,i-> d.cancel() }
            .show()
    }//ConsultaVehiculosAños

    fun Asignados(){
        AlertDialog.Builder(this)
            .setTitle("ATENCIÓN")
            .setMessage("QUE DESEA CONSULTAR?")
            .setPositiveButton("CONDUCTOR ASIGNADO A VEHICULO"){d,i->
                MostrarAsignados(true)
            }
            .setNeutralButton("VEHICULO ASIGNADO A CONDUCTOR"){d,i->
                MostrarAsignados(false)
            }
            .show()
    }//Asignados

    fun MostrarAsignados(Opcion:Boolean){
        var input = EditText(this)
        input.inputType = InputType.TYPE_CLASS_NUMBER

        if (Opcion){
            input.hint = "ID CONDUCTOR"
        } else {
            input.hint = "ID VEHICULO"
        }

        AlertDialog.Builder(this)
            .setTitle("ATENCIÓN")
            .setView(input)
            .setPositiveButton("ACEPTAR"){d,i->
                if (Opcion){
                    if (!input.text.toString().isEmpty()) {
                        val arreglo =
                            Vehiculo(this).consultarVehiculosConductor(input.text.toString())
                        listaConsultas.adapter =
                            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arreglo)
                    }
                } else {
                    if (!input.text.toString().isEmpty()) {
                        val arreglo =
                            Conductor(this).ConsultaConductorVehiculo(input.text.toString())
                        listaConsultas.adapter =
                            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arreglo)
                    }
                }
            }
            .setNegativeButton("CANCELAR"){d,i-> d.cancel()}
            .show()
    }

}