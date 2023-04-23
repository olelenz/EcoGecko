package si.uni_lj.fri.besthack.EcoGecko

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import si.uni_lj.fri.besthack.EcoGecko.FragmentDifficulty
import si.uni_lj.fri.besthack.EcoGecko.R

class FragmentMenu : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_menu, container, false)

        // Track Button
        val trackButton = view.findViewById<Button>(R.id.track_button)
        trackButton.setOnClickListener{ moveToTrack() }

        // Play Button
        val playButton = view.findViewById<Button>(R.id.play_button)
        playButton.setOnClickListener{ moveToPlay() }


        // Inflate the layout for this fragment
        return view
    }

    private fun moveToTrack() {
        println("not implemented yet")
    }

    private fun moveToPlay() {
        val fragmentManager = requireActivity().supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, FragmentDifficulty())
        transaction.addToBackStack(null)
        transaction.commit()
    }

}