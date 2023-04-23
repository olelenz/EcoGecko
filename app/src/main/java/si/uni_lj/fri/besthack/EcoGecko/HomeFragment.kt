package si.uni_lj.fri.besthack.EcoGecko

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kotlin.math.roundToInt


class HomeFragment : Fragment() {

    private var  sharedPreferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)

        val journeyButton = view.findViewById<Button>( R.id.btn_journey)
        val foodButton = view.findViewById<Button>( R.id.btn_food)
        val playButton = view.findViewById<Button>( R.id.btn_playgame)
        val carbonButton = view.findViewById<Button>( R.id.btn_carbon_emission)

        val exp = view.findViewById<TextView>( R.id.exp)
        val level = view.findViewById<TextView>( R.id.game_level)

        journeyButton.setOnClickListener{
            fragmentManager?.beginTransaction()?.addToBackStack("journey")!!
                .replace(android.R.id.content, MapsFragment())
                .commit()
        }
        carbonButton.setOnClickListener{
            fragmentManager?.beginTransaction()?.addToBackStack("carbon")!!
                .replace(android.R.id.content, EmissionFragment())
                .commit()
        }
        /*
        foodButton.setOnClickListener{
            fragmentManager?.beginTransaction()?.addToBackStack("food")!!
                .replace(android.R.id.content, EmissionFragment())
                .commit()
        }
        playButton.setOnClickListener{
            fragmentManager?.beginTransaction()?.addToBackStack("play")!!
                .replace(android.R.id.content, EmissionFragment())
                .commit()
        }

         */

        sharedPreferences =
            requireContext().getSharedPreferences("my_prefs2", Context.MODE_PRIVATE)
        val xp:Float = sharedPreferences?.getString("xp", "3")!!
            .toFloat()

        exp.text = xp.toString() + " EXP POINTS"
        level.text = (xp / 5).roundToInt().toString()


        return view
    }

}