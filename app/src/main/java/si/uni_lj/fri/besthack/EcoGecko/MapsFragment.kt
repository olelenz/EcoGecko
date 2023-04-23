package si.uni_lj.fri.besthack.EcoGecko

import android.Manifest
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import si.uni_lj.fri.besthack.EcoGecko.databinding.FragmentMapsBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class MapsFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: FragmentMapsBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var previousLocation: Location? = null
    private var resetLocation: Boolean? = false
    private var distance: Float? = 0f
    private var emission: Float? = 0f
    private val transportTypes = arrayOf(
        "Short Flight ✈️",
        "Medium Car (Gasoline) 🚗",
        "Medium Car (Diesel) 🚗",
        "Medium Flight ✈️",
        "Long Flight ✈️",
        "Bus 🚌",
        "Medium Motorcycle 🏍",
        "Gasoline Car (Two Passenger) 🚘",
        "Medium Electric Vehicle 🚙",
        "National Rail 🚂",
        "Ferry ⛴",
        "Eurostar (International Rail) 🚞",
        "Walking"
    )
    private val transportEmissions = hashMapOf<String, Int>(
        "Short Flight ✈️" to 255,
        "Medium Car (Gasoline) 🚗" to 192,
        "Medium Car (Diesel) 🚗" to 171,
        "Medium Flight ✈️" to 156,
        "Long Flight ✈️" to 150,
        "Bus 🚌" to 105,
        "Medium Motorcycle 🏍" to 103,
        "Gasoline Car (Two Passenger) 🚘" to 96,
        "Medium Electric Vehicle 🚙" to 53,
        "National Rail 🚂" to 41,
        "Ferry ⛴" to 19,
        "Eurostar (International Rail) 🚞" to 6,
        "Walking " to 0,
    )

    private var emissionData = mutableListOf<Float>()
    private var selectedItem = ""
    private var emissionDataString = ""
    private var currentlySelected = ""
    private var isJourneyStarted = false
    private var  sharedPreferences:SharedPreferences? = null
    private var  sp2:SharedPreferences? = null
    private var xp: Long = 0

    companion object {
        private const val REQUEST_ID_LOCATION_PERMISSIONS = 100
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapsBinding.inflate(inflater, container, false)
        val adapter = activity?.let {
            ArrayAdapter(
                it,
                android.R.layout.simple_spinner_item,
                transportTypes
            )
        }
        binding.spinner.adapter = adapter



        sharedPreferences =
            requireContext().getSharedPreferences("my_prefs", Context.MODE_PRIVATE)

        sp2 = requireContext().getSharedPreferences("my_prefs2", Context.MODE_PRIVATE)
        var editor = sharedPreferences?.edit()
        editor?.clear()
        editor?.apply()

        val exp:Long = sp2?.getString("xp", "0")!!.toLong()

        if(exp > 0){
            xp = exp
        }


        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedItem = parent.getItemAtPosition(position) as String
                distance = 0.0f
                emission = 0.0f
                resetLocation = true
                if (emissionData.isNotEmpty()) {
                    editor = sharedPreferences?.edit()
                    editor?.putString(currentlySelected, emissionData.joinToString(","))
                    editor?.apply()
                }
                currentlySelected = selectedItem
                emissionData.clear()
                //Toast.makeText(activity?.applicationContext, selectedItem, Toast.LENGTH_SHORT).show()
                emissionDataString = sharedPreferences?.getString(selectedItem, "").toString()
                if (!emissionDataString.isEmpty()) {
                    /*
                    Toast.makeText(
                        activity?.applicationContext,
                        emissionDataString,
                        Toast.LENGTH_SHORT
                    ).show()
                     */
                    emissionDataString.split(",").map { it.toFloat() }.toMutableList()
                    val emissionDataList = emissionDataString.split(",")

                    // Convert each substring to a float and add it to the list
                    emissionDataList.forEach {
                        val floatValue = it.toFloatOrNull()
                        if (floatValue != null) {
                            emissionData.add(floatValue)
                        }
                    }
                    binding.aca.text =
                        "Average carbon emission" + ": " + emissionData.average().toFloat() + " g"
                    return
                }
                binding.distance.text = "Distance" + ": " + 0.0 + " meters"

                binding.ca.text = "Carbon emission" + ": " + 0.0 + " g"
                binding.aca.text = "Average carbon emission" + ": " + 0.0 + " g"
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }

        }
        return binding.root
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val journeyButton = binding.journey

        journeyButton.setOnClickListener {
            if (isJourneyStarted) {
                // end journey logic goes here
                isJourneyStarted = false
                journeyButton.text = "Start Journey"
                fusedLocationClient.removeLocationUpdates(locationCallback)

                // Reset journey variables
                distance = 0.0f
                emission = 0.0f
                if (emissionData.isNotEmpty()) {
                    val editor = sharedPreferences?.edit()
                    editor?.putString(currentlySelected, emissionData.joinToString(","))
                    editor?.apply()
                }
                if(xp > 0){
                    var current = sharedPreferences?.getString("xp", "0")?.toLong()
                    current = current!! + xp
                    Toast.makeText(activity?.applicationContext, current.toString(), Toast.LENGTH_SHORT).show()
                    val editor = sp2?.edit()
                    editor?.putString("xp", current.toString())
                    editor?.apply()
                }
                resetLocation = true
                previousLocation = null
            } else {
                // start journey logic goes here
                isJourneyStarted = true
                journeyButton.text = "End Journey"
                showLastKnownLocation()
            }
        }

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val ljubljana = LatLng(46.0, 14.0)
        mMap.addMarker(MarkerOptions().position(ljubljana).title("Marker in Ljubljana"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(ljubljana))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun showLastKnownLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) || ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                Snackbar.make(
                    binding.root,
                    R.string.permission_location_rationale,
                    Snackbar.LENGTH_INDEFINITE
                ).setAction(R.string.ok) {
                    // If the user agrees with the Snackbar, proceed with asking for the permissions
                    ActivityCompat.requestPermissions(
                        requireActivity(),
                        arrayOf(
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ),
                        REQUEST_ID_LOCATION_PERMISSIONS
                    )
                }.show()
            } else {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ),
                    REQUEST_ID_LOCATION_PERMISSIONS
                )
            }
        } else {
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null /* Looper */)
        }

    }

    private val locationRequest: LocationRequest = LocationRequest.create().apply {
        interval = 10000
        fastestInterval = 5000
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    private val locationCallback = object : LocationCallback() {
        @RequiresApi(Build.VERSION_CODES.O)
        override fun onLocationResult(locationResult: LocationResult) {
            locationResult
            for (location in locationResult.locations) {
                mMap.addMarker(
                    MarkerOptions()
                        .position(LatLng(location.latitude, location.longitude))
                        .title(
                            LocalDateTime.now().format(
                                DateTimeFormatter.ofLocalizedDateTime(
                                    FormatStyle.SHORT, FormatStyle.SHORT)))
                )
                val zoom_level = 15.0F
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(location.latitude, location.longitude), zoom_level))
                binding.lat.text = getString(R.string.latitude) + ": " + location.latitude.toString()
                binding.lon.text = getString(R.string.longitude) + ": " + location.longitude.toString()

                if(resetLocation!!){
                    previousLocation = location
                    resetLocation = false
                }
                if(previousLocation != null && location.distanceTo(previousLocation!!) > 0.0){
                    distance = distance!! + location.distanceTo(previousLocation!!)
                    if(selectedItem != "Walking"){
                        emission =  emission!! + transportEmissions.get(selectedItem)!!.toFloat()*location.distanceTo(previousLocation!!)/1000
                    }
                    else{
                        xp += (distance!!.toLong()/100)
                        binding.xp.text = "You gained " + (distance!!.toLong()/100).toString() + " xp"
                    }

                    if(emission!!.toFloat() > 0.0){
                        emissionData.add(emission!!.toFloat())
                    }

                }
                binding.distance.text = "Distance" + ": " + distance + " meters"

                binding.ca.text = "Carbon emission" + ": " + emission + " g"
                val average = emissionData.average()
                if(emissionData.isEmpty()){
                    binding.aca.text = "Average carbon emission" + ": " + 0.0 + " g"
                }
                else{
                    binding.aca.text = "Average carbon emission" + ": " + average.toFloat() + " g"
                }


                previousLocation = location
                //Toast.makeText(activity?.applicationContext, emissionData.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}