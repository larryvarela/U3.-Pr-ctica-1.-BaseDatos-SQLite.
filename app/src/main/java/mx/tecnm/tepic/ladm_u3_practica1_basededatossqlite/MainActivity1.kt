package mx.tecnm.tepic.ladm_u3_practica1_basededatossqlite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main1.*

class MainActivity1 : AppCompatActivity() {
    var idConductores = ArrayList<Int>()
    var idSeleccionado = 0
    var Actualizar = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main1)

        supportActionBar?.hide()

        MostrarConductores()

        button.setOnClickListener {
            val conductor = Conductor(this)

            conductor.Nombre = nombreConductor.text.toString()
            conductor.Domicilio = domicilioConductor.text.toString()
            conductor.NoLicencia = nolicenciaConductor.text.toString()
            conductor.Vence = fechaConductor.text.toString()

            if (Actualizar){

                val resultadoActualizar = conductor.actualizar(idSeleccionado.toString())
                Actualizar = false

                if (resultadoActualizar){
                    Toast.makeText(this,"EXITO SE ACTUALIZO", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this,"ERROR NO SE ACTUALIZO", Toast.LENGTH_LONG).show()
                }
                button.text = "INSERTAR"
                Limpiar()

            } else {
                val resultadoInsertar = conductor.insertar()

                if (resultadoInsertar){
                    Toast.makeText(this,"EXITO SE INSERTO", Toast.LENGTH_LONG).show()
                    Limpiar()
                } else {
                    Toast.makeText(this,"ERROR NO SE INSERTO", Toast.LENGTH_LONG).show()
                }

            }//else

        }//btnInsertarConductor

    }

    fun Limpiar(){
        //Limpiamos los campos
        nombreConductor.text.clear()
        domicilioConductor.text.clear()
        nolicenciaConductor.text.clear()
        fechaConductor.text.clear()

        MostrarConductores()
    }//Limpiar

    fun MostrarConductores(){
        val arregloConductor = Conductor(this).consultar()

        listaConductores.adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arregloConductor)

        idConductores.clear()
        idConductores = Conductor(this).obtenerIDs()
        activarEvento(listaConductores)
    }//MostrarConductores

    fun activarEvento(ListaArtistas: ListView){
        ListaArtistas.setOnItemClickListener { adapterView, view, i, l ->

            idSeleccionado = idConductores[i]
            AlertDialog.Builder(this)
                .setTitle("ATENCION")
                .setMessage("QUE DESEA HACER CON EL CONDUCTOR")
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
        button.text = "ACTUALIZAR"

        val conductor = Conductor(this).consultar(idSeleccionado.toString())

        nombreConductor.setText(conductor.Nombre)
        domicilioConductor.setText(conductor.Domicilio)
        nolicenciaConductor.setText(conductor.NoLicencia)
        fechaConductor.setText(conductor.Vence)

    }//Editar

    fun Eliminar(){
        AlertDialog.Builder(this)
            .setTitle("IMPORTANTE")
            .setMessage("SEGURO QUE DESEAS ELIMINAR ID ${idSeleccionado}")
            .setPositiveButton("SI"){d,i->
                val resultado = Conductor(this).eliminar(idSeleccionado)

                if (resultado){
                    Toast.makeText(this,"SE ELIMINO CON EXITO",Toast.LENGTH_LONG).show()
                    MostrarConductores()
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