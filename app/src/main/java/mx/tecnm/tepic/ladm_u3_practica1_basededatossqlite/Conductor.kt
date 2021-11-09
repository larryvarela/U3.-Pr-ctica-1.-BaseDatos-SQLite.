package mx.tecnm.tepic.ladm_u3_practica1_basededatossqlite

import android.content.ContentValues
import android.content.Context
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class Conductor(p:Context) {
    var ID = 0
    var Nombre = ""
    var Domicilio = ""
    var NoLicencia = ""
    var Vence = ""
    val pnt = p

    fun insertar() : Boolean {

        val tablaConductor = BaseDatos(pnt,"PIZZA",null,1).writableDatabase

        val datos = ContentValues()

        datos.put("NOMBRE",Nombre)
        datos.put("DOMICILIO",Domicilio)
        datos.put("NOLICENCIA",NoLicencia)
        datos.put("VENCE",Vence)

        val resultado = tablaConductor.insert("CONDUCTOR",null,datos)
        //el metodo insert regresa un ID unico de renglon de tabla, regresa -1 si no se pudo

        if (resultado == -1L){
            return false
        }

        return true
    }//insertar

    fun eliminar(idEliminar:Int):Boolean{
        val tablaConductor = BaseDatos(pnt,"PIZZA",null,1).writableDatabase

        val resultado = tablaConductor.delete("CONDUCTOR","IDCONDUCTOR=?", arrayOf(idEliminar.toString()))

        if (resultado == 0) return false
        return true
    }//eliminar

    fun actualizar(idActualizar:String):Boolean{
        val tablaConductor = BaseDatos(pnt,"PIZZA",null,1).readableDatabase
        val datos = ContentValues()

        datos.put("NOMBRE",Nombre)
        datos.put("DOMICILIO",Domicilio)
        datos.put("NOLICENCIA",NoLicencia)
        datos.put("VENCE",Vence)

        val resultado = tablaConductor.update("CONDUCTOR",datos,"IDCONDUCTOR=?", arrayOf(idActualizar))
        if (resultado == 0) return false
        return true
    }//actualizar

    fun consultarCD() : ArrayList<Conductor>{
        val tablaConductor = BaseDatos(pnt,"PIZZA",null,1).readableDatabase
        val resultado = ArrayList<Conductor>()

        val cursor = tablaConductor.query("CONDUCTOR", arrayOf("*"),null,null,null,null,null)

        if (cursor.moveToFirst()){
            do {

                var c = Conductor(MainActivity4())

                c.ID = cursor.getInt(0)
                c.Nombre = cursor.getString(1)
                c.Domicilio = cursor.getString(2)
                c.NoLicencia = cursor.getString(3)
                c.Vence = cursor.getString(4)

                resultado.add(c)
            }while (cursor.moveToNext())
        }

        return resultado
    }//consultar


    fun consultar() : ArrayList<String>{
        val tablaConductor = BaseDatos(pnt,"PIZZA",null,1).readableDatabase
        val resultado = ArrayList<String>()

        val cursor = tablaConductor.query("CONDUCTOR", arrayOf("*"),null,null,null,null,null)

        if (cursor.moveToFirst()){
            do {

                var dato = "ID: "+cursor.getInt(0)+"\nNombre: "+cursor.getString(1)+"\nDomicilio: "+cursor.getString(2)+
                        "\nNo. Licencia: "+cursor.getString(3)+"\nVence: "+cursor.getString(4)

                resultado.add(dato)
            }while (cursor.moveToNext())
        } else {
            resultado.add("NO SE ENCONTRO DATA A MOSTRAR")
        }

        return resultado
    }//consultar

    fun consultar(idBuscar:String) : Conductor{
        val tablaConductor = BaseDatos(pnt,"PIZZA",null,1).readableDatabase

        val cursor = tablaConductor.query("CONDUCTOR", arrayOf("*"),"IDCONDUCTOR=?", arrayOf(idBuscar),null,null,null)
        val conductor = Conductor(MainActivity4())

        if (cursor.moveToFirst()){
            conductor.Nombre = cursor.getString(1)
            conductor.Domicilio = cursor.getString(2)
            conductor.NoLicencia = cursor.getString(3)
            conductor.Vence = cursor.getString(4)
        }
        return conductor
    }//consultar

    fun LicenciaVencida() : ArrayList<String>{
        val tablaConductor = BaseDatos(pnt,"PIZZA",null,1).readableDatabase
        val resultado = ArrayList<String>()

        val d = Date()
        val fecha = SimpleDateFormat("yyyy-MM-dd")

        val cursor = tablaConductor.query("CONDUCTOR", arrayOf("*"),"VENCE<?", arrayOf(fecha.format(d)),null,null,null)

        if (cursor.moveToFirst()){
            do {
                var dato = "ID: "+cursor.getInt(0)+"\nNombre: "+cursor.getString(1)+"\nDomicilio: "+cursor.getString(2)+
                        "\nNo. Licencia: "+cursor.getString(3)+"\nVence: "+cursor.getString(4)

                resultado.add(dato)
            }while (cursor.moveToNext())
        } else {
            resultado.add("NO SE ENCONTRO DATA A MOSTRAR")
        }
        return resultado
    }//LicenciaVencida

    fun SinAsignarVehiculo() : ArrayList<String>{
        val tablaConductor = BaseDatos(pnt,"PIZZA",null,1).readableDatabase
        val resultado = ArrayList<String>()
        var ids = "("

        val consulta = tablaConductor.query("VEHICULO", arrayOf("IDCONDUCTOR"),null,null,null,null,null)

        if (consulta.moveToFirst()){
            do {
                ids += "'"+consulta.getInt(0)+"'"
                if (consulta.moveToNext()){
                    ids += ","
                    consulta.moveToPrevious()
                } else {
                    ids += ")"
                }
            }while (consulta.moveToNext())
            System.out.println(ids)
        }
        else {  ids += "'0')"}

        val cursor = tablaConductor.query("CONDUCTOR", arrayOf("*"),"IDCONDUCTOR NOT IN ${ids}",
            null,null, null,null)

        if (cursor.moveToFirst()){
            do {
                var dato = "ID: "+cursor.getInt(0)+"\nNombre: "+cursor.getString(1)+"\nDomicilio: "+cursor.getString(2)+
                        "\nNo. Licencia: "+cursor.getString(3)+"\nVence: "+cursor.getString(4)

                resultado.add(dato)
            }while (cursor.moveToNext())
        } else {
            resultado.add("NO SE ENCONTRO DATA A MOSTRAR")
        }
        return resultado
    }//SinAsignarVehiculo


    fun ConsultaConductorVehiculo(ID:String) : ArrayList<String>{
        val tablaConductor = BaseDatos(pnt,"PIZZA",null,1).readableDatabase
        val resultado = ArrayList<String>()
        var id = 0

        val consulta = tablaConductor.query("VEHICULO", arrayOf("IDCONDUCTOR"),"IDVEHICULO = ?",
            arrayOf(ID),null,null,null)

        if (consulta.moveToFirst()){
            id = consulta.getInt(0)
        }

        val cursor = tablaConductor.query("CONDUCTOR", arrayOf("*"),"IDCONDUCTOR = ?",
            arrayOf(id.toString()),null, null,null)

        if (cursor.moveToFirst()){
            do {
                var dato = "ID: "+cursor.getInt(0)+"\nNombre: "+cursor.getString(1)+"\nDomicilio: "+cursor.getString(2)+
                        "\nNo. Licencia: "+cursor.getString(3)+"\nVence: "+cursor.getString(4)

                resultado.add(dato)
            }while (cursor.moveToNext())
        } else {
            resultado.add("NO SE ENCONTRO DATA A MOSTRAR")
        }
        return resultado
    }//SinAsignarVehiculo


    fun obtenerIDs():ArrayList<Int>{
        val tablaConductor = BaseDatos(pnt,"PIZZA",null,1).readableDatabase
        val resultado = ArrayList<Int>()

        val cursor = tablaConductor.query("CONDUCTOR", arrayOf("*"),null,null,null,null,null)

        if (cursor.moveToFirst()){
            do {
                resultado.add(cursor.getInt(0))
            }while (cursor.moveToNext())
        }
        return resultado
    }//obtenerIDs
}