package si.uni_lj.fri.besthack.EcoGecko

import ai.onnxruntime.OnnxTensor
import ai.onnxruntime.OrtEnvironment
import ai.onnxruntime.OrtLoggingLevel
import ai.onnxruntime.OrtSession
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import java.nio.FloatBuffer


class EmissionFragment : Fragment() {

    private var  sharedPreferences: SharedPreferences? = null
    private var inputEditText: EditText? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_emission, container, false)

        // Initialize the views
        inputEditText = view.findViewById<EditText>( R.id.input_electricity)
        val outputTextView = view.findViewById<TextView>( R.id.output_bodyfat )
        val button = view.findViewById<Button>(R.id.btn_calculate)



        /* with edittext input
        button.setOnClickListener {


            // Parse input from inputEditText
            val inputs = inputEditText.text.toString().toFloatOrNull()
            if (inputs != null) {
                val ortEnvironment = OrtEnvironment.getEnvironment()
                val ortSession = createORTSession( ortEnvironment )
                val output = runPrediction(ortSession, ortEnvironment)
                outputTextView.text = "CO2 emission output is ${output}"
            }
            else {
                Toast.makeText( this , "Please check the inputs" , Toast.LENGTH_LONG ).show()
            }
        }*/

        button.setOnClickListener {

            val ortEnvironment = OrtEnvironment.getEnvironment()
            val ortSession = createORTSession(ortEnvironment)
            val output = runPrediction(ortSession, ortEnvironment)

            val roundedoutput = String.format("%.2f", output)
            outputTextView.text = roundedoutput

        }
        return view
    }

    // Create an OrtSession with the given OrtEnvironment
    private fun createORTSession( ortEnvironment: OrtEnvironment) : OrtSession {
        val modelBytes = resources.openRawResource( R.raw.emissions_model).readBytes()
        return ortEnvironment.createSession( modelBytes )
    }

    private fun runPrediction(ortSession: OrtSession, ortEnvironment: OrtEnvironment): Float {
        // GET DATA
        // Get travel and electricity data from EditText and SharedPreferences
        sharedPreferences =
            requireContext().getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        val motorBike:Float = sharedPreferences?.getString("Medium Motorcycle \uD83C\uDFCD", "0.0")!!
            .toFloat()
        val car = sharedPreferences?.getString("Medium Car (Gasoline) \uD83D\uDE97", "0.0")!!.toFloat()
        val bus = sharedPreferences?.getString("Bus \uD83D\uDE8C", "0.0")!!.toFloat()
        //Toast.makeText(activity?.applicationContext, motorBike, Toast.LENGTH_SHORT).show()
        var inputed = 0.0f
        if(inputEditText?.text != null){
            inputed = inputEditText!!.text.toString().toFloat()
        }

        // TEST INPUT FOR THE MODEL
        //val value = floatArrayOf(720f, 10f, 750f, 0f, 112f)

        // Create float array of inputs
        val value = floatArrayOf(inputed, motorBike!!.toFloat(), car!!.toFloat(), 0f, bus!!.toFloat())
        val inputName = ortSession.inputNames?.iterator()?.next()
        // Make a FloatBuffer of the inputs
        val floatBufferInputs = FloatBuffer.wrap(value)
        val inputTensor = OnnxTensor.createTensor(ortEnvironment, floatBufferInputs, longArrayOf(1, 5))
        // Run the model
        val options = OrtSession.RunOptions().apply {
            logLevel = OrtLoggingLevel.ORT_LOGGING_LEVEL_VERBOSE
        }
        val results = ortSession.run(mapOf(inputName to inputTensor), options)
        val output = results[0].value as Array<FloatArray>
        return output[0][0]
    }

}