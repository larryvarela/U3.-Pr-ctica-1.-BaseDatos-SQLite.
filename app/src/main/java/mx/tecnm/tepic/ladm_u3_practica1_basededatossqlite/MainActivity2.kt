package mx.tecnm.tepic.ladm_u3_practica1_basededatossqlite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main1.*
import kotlinx.android.synthetic.main.activity_main1.button
import kotlinx.android.synthetic.main.activity_main1.listaConductores
import kotlinx.android.synthetic.main.activity_main2.*

class MainActivity2 : AppCompatActivity() {
    var idVehiculo = ArrayList<Int>()
    var idSeleccionado = 0
    var Actualizar = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        supportActionBar?.hide()

        MostrarVehiculos()

        buttonV.setOnClickListener {
            val vehiculo = Vehiculo(this)

            vehiculo.Placa = placaVehiculo.text.toString()
            vehiculo.Marca = marcaVehiculo.text.toString()
            vehiculo.Modelo = modeloVehiculo.text.toString()
            vehiculo.Año = añoVehiculo.text.toString().toInt()
            vehiculo.IdConductor = idconductorVehiculo.text.toString().toInt()

            if (Actualizar){

                val resultadoActualizar = vehiculo.actualizar(idSeleccionado.toString())
                Actualizar = false

                if (resultadoActualizar){
                    Toast.makeText(this,"EXITO SE ACTUALIZO", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this,"ERROR NO SE ACTUALIZO", Toast.LENGTH_LONG).show()
                }
                buttonV.text = "INSERTAR"
                Limpiar()

            } else {
                val resultadoInsertar = vehiculo.insertar()

                if (resultadoInsertar){
                    Toast.makeText(this,"EXITO SE INSERTO", Toast.LENGTH_LONG).show()
                    Limpiar()
                } else {
                    Toast.makeText(this,"ERROR NO SE INSERTO", Toast.LENGTH_LONG).show()
                }

            }//else
        }
    }

    fun Limpiar(){
        placaVehiculo.text.clear()
        marcaVehiculo.text.clear()
        modeloVehiculo.text.clear()
        añoVehiculo.text.clear()
        idconductorVehiculo.text.clear()

        MostrarVehiculos()
    }//Limpiar

    fun MostrarVehiculos(){
        val arregloVehiculo = Vehiculo(this).consultar()

        listaVehiculos.adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arregloVehiculo)

        idVehiculo.clear()
        idVehiculo = Vehiculo(this).obtenerIDs()
        activarEvento(listaVehiculos)
    }//MostrarConductores

    fun activarEvento(ListaArtistas: ListView){
        ListaArtistas.setOnItemClickListener { adapterView, view, i, l ->

            idSeleccionado = idVehiculo[i]
            AlertDialog.Builder(this)
                .setTitle("ATENCION")
                .setMessage("QUE DESEA HACER CON EL VEHICULO")
                .setPositiveButton("EDITAR"){d,i-> Editar() }
                .setNegativeButton("ELIMINAR"){d,i-> Eliminar() }
                .setNeutralButton("CANCELAR"){d,i->
                    d.cancel()
                }
                .show()
        }
    }//activarEvento

    fun Editar(){
        Actualizar = true
        buttonV.text = "ACTUALIZAR"

        val vehiculo = Vehiculo(this).consultar(idSeleccionado.toString())

        placaVehiculo.setText(vehiculo.Placa)
        marcaVehiculo.setText(vehiculo.Marca)
        modeloVehiculo.setText(vehiculo.Modelo)
        añoVehiculo.setText(vehiculo.Año.toString())
        idconductorVehiculo.setText(vehiculo.IdConductor.toString())

    }//Editar

    fun Eliminar(){
        AlertDialog.Builder(this)
            .setTitle("IMPORTANTE")
            .setMessage("SEGURO QUE DESEAS ELIMINAR ID ${idSeleccionado}")
            .setPositiveButton("SI"){d,i->
                val resultado = Vehiculo(this).eliminar(idSeleccionado)

                if (resultado){
                    Toast.makeText(this,"SE ELIMINO CON EXITO",Toast.LENGTH_LONG).show()
                    MostrarVehiculos()
                } else {
                    Toast.makeText(this,"NO SE LOGRO ELIMINAR",Toast.LENGTH_LONG).show()
                }
            }
            .setNegativeButton("NO"){d,i->
                d.cancel()
            }
            .show()
    }//Eliminar
}