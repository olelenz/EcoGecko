package eco.gecko.ecogecko

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.google.android.flexbox.FlexboxLayout

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    //private lateinit var flexbox: FlexboxLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_first, container, false)

        // initializing empty board
        //flexbox = view.findViewById(R.id.flexbox)

        // newGame Button
        val newGame = view.findViewById<Button>(R.id.button_first)
        newGame.setOnClickListener{ println("hi")}

        return view
    }

    /*
    private fun createNewGame() {
        val adapter = GridAdapter()
        val textView = TextView(context)
        textView.text = "New Text"
        val layoutParams = FlexboxLayout.LayoutParams(
            FlexboxLayout.LayoutParams.WRAP_CONTENT,
            FlexboxLayout.LayoutParams.WRAP_CONTENT
        )
        textView.layoutParams = layoutParams
        //flexbox.addView(textView)
    }*/
}