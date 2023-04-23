package si.uni_lj.fri.besthack.EcoGecko

import ai.onnxruntime.OnnxTensor
import ai.onnxruntime.OrtEnvironment
import ai.onnxruntime.OrtLoggingLevel
import ai.onnxruntime.OrtSession
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.io.BufferedReader
import java.io.InputStream
import java.nio.FloatBuffer

class MainActivity : AppCompatActivity() {

    companion object{
        val levelList = ArrayList<Int>()
        val boardList = ArrayList<String>()
        lateinit var setOfDifficulties: Set<Int>
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().addToBackStack("home")
            .replace(android.R.id.content, HomeFragment())
            .commit()
    }

    fun readData(): ArrayList<Pair<Int, String>>{
        var list: ArrayList<Pair<Int, String>> = ArrayList()
        var res: InputStream = resources.openRawResource(R.raw.small_db)
        val reader = BufferedReader(res.reader())
        val content = StringBuilder()
        try {
            var line = reader.readLine()
            while (line != null) {
                content.append(line)
                line = reader.readLine()
                if (line != null) {
                    var lineOut = line.split(",")
                    list.add(Pair(lineOut[1].toInt(), lineOut[2]))
                }
            }
        } finally {
            reader.close()
        }
        return list
    }

}