package mx.tecnm.tepic.ladm_u3_practica1_basededatossqlite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView

class MainActivity : AppCompatActivity() {
    var lista : ListView?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lista = findViewById(R.id.menuprincipal)
        lista!!.setOnItemClickListener { adapterView, view, i, l ->
            when (i) {
                0 -> {
                    var activity1 = Intent(this, MainActivity1::class.java)
                    startActivity(activity1)
                }
                1 -> {
                    var activity2 = Intent(this, MainActivity2::class.java)
                    startActivity(activity2)
                }
                2 -> {
                    var activity3 = Intent(this, MainActivity3::class.java)
                    startActivity(activity3)
                }
                3 -> {
                    var activity4 = Intent(this, MainActivity4::class.java)
                    startActivity(activity4)
                }

            }
        }
    }
}