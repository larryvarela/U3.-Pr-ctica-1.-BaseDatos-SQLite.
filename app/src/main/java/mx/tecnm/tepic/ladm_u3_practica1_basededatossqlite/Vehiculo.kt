package mx.tecnm.tepic.ladm_u3_practica1_basededatossqlite

import android.content.ContentValues
import android.content.Context
import androidx.appcompat.app.AlertDialog
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class Vehiculo(p:Context) {
    var ID = 0
    var Placa = ""
    var Marca = ""
    var Modelo = ""
    var Año = 0
    var IdConductor = 0
    val pnt = p

    fun insertar() : Boolean {

        val tablaConductor = BaseDatos(pnt,"PIZZA",null,1).writableDatabase

        val datos = ContentValues()

        datos.put("PLACA",Placa)
        datos.put("MARCA",Marca)
        datos.put("MODELO",Modelo)
        datos.put("AÑO",Año)
        datos.put("IDCONDUCTOR",IdConductor)

        val resultado = tablaConductor.insert("VEHICULO",null,datos)

        if (resultado == -1L){
            return false
        }

        return true
    }//insertar

    fun eliminar(idEliminar:Int):Boolean{
        val tablaVehiculo = BaseDatos(pnt,"PIZZA",null,1).writableDatabase

        val resultado = tablaVehiculo.delete("VEHICULO","IDVEHICULO=?", arrayOf(idEliminar.toString()))

        if (resultado == 0) return false
        return true
    }//eliminar

    fun actualizar(idActualizar:String):Boolean{
        val tablaVehiculo = BaseDatos(pnt,"PIZZA",null,1).readableDatabase
        val datos = ContentValues()

        datos.put("PLACA",Placa)
        datos.put("MARCA",Marca)
        datos.put("MODELO",Modelo)
        datos.put("AÑO",Año)
        datos.put("IDCONDUCTOR",IdConductor)

        val resultado = tablaVehiculo.update("VEHICULO",datos,"IDVEHICULO=?", arrayOf(idActualizar))
        if (resultado == 0) return false
        return true
    }//actualizar


    fun consultar() : ArrayList<String>{
        val tablaVehiculo = BaseDatos(pnt,"PIZZA",null,1).readableDatabase
        val resultado = ArrayList<String>()

        val cursor = tablaVehiculo.query("VEHICULO", arrayOf("*"),null,null,null,null,null)

        if (cursor.moveToFirst()){
            do {

                var dato = "ID: "+cursor.getInt(0)+"\nPlaca: "+cursor.getString(1)+"\nMarca: "+cursor.getString(2)+
                        "\nModelo: "+cursor.getString(3)+"\nAño: "+cursor.getString(4)+"\nID Conductor: "+cursor.getInt(5)

                resultado.add(dato)
            }while (cursor.moveToNext())
        } else {
            resultado.add("NO SE ENCONTRO DATA A MOSTRAR")
        }

        return resultado
    }//consultar

    fun consultarVehiculosNAños(Años:Int) : ArrayList<String>{
        val tablaVehiculo = BaseDatos(pnt,"PIZZA",null,1).readableDatabase
        val resultado = ArrayList<String>()

        val d = Date()
        val fecha = SimpleDateFormat("yyyy")

        val cursor = tablaVehiculo.query("VEHICULO", arrayOf("*"),"AÑO = (${fecha.format(d)}-${Años})",null,null,null,null)

        if (cursor.moveToFirst()){
            do {

                var dato = "ID: "+cursor.getInt(0)+"\nPlaca: "+cursor.getString(1)+"\nMarca: "+cursor.getString(2)+
                        "\nModelo: "+cursor.getString(3)+"\nAño: "+cursor.getString(4)+"\nID Conductor: "+cursor.getInt(5)

                resultado.add(dato)
            }while (cursor.moveToNext())
        } else {
            resultado.add("NO SE ENCONTRO DATA A MOSTRAR")
        }

        return resultado
    }//consultar

    fun consultarVehiculosConductor(ID:String) : ArrayList<String>{
        val tablaVehiculo = BaseDatos(pnt,"PIZZA",null,1).readableDatabase
        val resultado = ArrayList<String>()

        val cursor = tablaVehiculo.query("VEHICULO", arrayOf("*"),"IDCONDUCTOR = ?",
            arrayOf(ID),null,null,null)

        if (cursor.moveToFirst()){
            do {

                var dato = "ID: "+cursor.getInt(0)+"\nPlaca: "+cursor.getString(1)+"\nMarca: "+cursor.getString(2)+
                        "\nModelo: "+cursor.getString(3)+"\nAño: "+cursor.getString(4)+"\nID Conductor: "+cursor.getInt(5)

                resultado.add(dato)
            }while (cursor.moveToNext())
        } else {
            resultado.add("NO SE ENCONTRO DATA A MOSTRAR")
        }

        return resultado
    }//consultar


    fun consultar(idBuscar:String) : Vehiculo{
        val tablaVehiculo = BaseDatos(pnt,"PIZZA",null,1).readableDatabase

        val cursor = tablaVehiculo.query("VEHICULO", arrayOf("*"),"IDVEHICULO=?", arrayOf(idBuscar),null,null,null)
        val vehiculo = Vehiculo(MainActivity2())

        if (cursor.moveToFirst()){
            vehiculo.Placa = cursor.getString(1)
            vehiculo.Marca = cursor.getString(2)
            vehiculo.Modelo = cursor.getString(3)
            vehiculo.Año = cursor.getInt(4)
            vehiculo.IdConductor = cursor.getInt(5)
        }
        return vehiculo
    }//consultar

    fun consultarVH() : ArrayList<Vehiculo>{

        val tablaVehiculo = BaseDatos(pnt,"PIZZA",null,1).readableDatabase
        val resultado = ArrayList<Vehiculo>()

        val cursor = tablaVehiculo.query("VEHICULO", arrayOf("*"),null, null,null,null,null)


        if (cursor.moveToFirst()){
            do {

                var v = Vehiculo(MainActivity4())
                v.IdConductor = cursor.getInt(0)
                v.Placa = cursor.getString(1)
                v.Marca = cursor.getString(2)
                v.Modelo = cursor.getString(3)
                v.Año = cursor.getInt(4)
                v.IdConductor = cursor.getInt(5)

                resultado.add(v)
            }while (cursor.moveToNext())
        }

        return resultado
    }//consultar

    fun obtenerIDs():ArrayList<Int>{
        val tablaConductor = BaseDatos(pnt,"PIZZA",null,1).readableDatabase
        val resultado = ArrayList<Int>()

        val cursor = tablaConductor.query("VEHICULO", arrayOf("*"),null,null,null,null,null)

        if (cursor.moveToFirst()){
            do {
                resultado.add(cursor.getInt(0))
            }while (cursor.moveToNext())
        }
        return resultado
    }//obtenerIDs
}