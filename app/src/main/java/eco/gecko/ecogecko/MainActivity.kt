package eco.gecko.ecogecko

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import eco.gecko.ecogecko.databinding.ActivityMainBinding
import java.io.BufferedReader
import java.io.InputStream

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val data = readData()
        val levelList = ArrayList<Int>()
        data.forEach { levelList.add(it.first) }
        val boardList = ArrayList<String>()
        data.forEach { boardList.add(it.second) }
        val setOfDifficulties = levelList.toSet()
        println(setOfDifficulties)


        /**
         * FRAGMENT MANAGER
         */

        // Setting up Fragment Manager to navigate
        // ListFragment is our StartFragment
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.add(R.id.fragment_container, MainMenu())
        transaction.commit()
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