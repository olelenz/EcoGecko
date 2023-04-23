package si.uni_lj.fri.besthack.EcoGecko

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.tensorflow.lite.support.image.TensorImage
import si.uni_lj.fri.besthack.EcoGecko.ml.LiteModelAiyVisionClassifierFoodV11
import java.io.IOException

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FoodFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FoodFragment : Fragment() {

    companion object {
        val TAG = MainActivity::class.simpleName
    }

    lateinit var btn_pick: Button
    lateinit var btn_predict: Button
    lateinit var selected_img_view: ImageView
    lateinit var output_predict_view: TextView

    // HASHMAP FOR FOOD
    var foodEmissionsHashmap = HashMap<String, Float>()


    // For getting an image
    private val getImageRequestCode = 123
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val v: View = inflater.inflate(R.layout.fragment_food, container, false)

        // Fill with food data emissions
        // From website: https://foodfootprint.nl/en/foodprintfinder/

        foodEmissionsHashmap["pizza"] = 1.38F
        foodEmissionsHashmap["hamburger"] = 3.07F

        // From layout
        btn_pick = v.findViewById<Button>(R.id.btn_pickimg)
        btn_predict = v.findViewById<Button>(R.id.btn_predict)
        selected_img_view = v.findViewById<ImageView>(R.id.selected_image)
        output_predict_view = v.findViewById<TextView>(R.id.output_prediction)

        btn_pick.setOnClickListener() {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, getImageRequestCode)
        }

        btn_predict.setOnClickListener() {
            try {
                if (imageUri != null) {

                    val bitmap = MediaStore.Images.Media.getBitmap(
                        this.activity?.contentResolver, imageUri
                    )

                    if (bitmap != null) {
                        // Creates inputs for reference.
                        val model = LiteModelAiyVisionClassifierFoodV11.newInstance(v.context)

                        // Scales image to be used for classification
                        val scaled_bitmap = Bitmap.createScaledBitmap(bitmap, 224, 224, true)
                        val image = TensorImage.fromBitmap(scaled_bitmap)

                        // Runs model inference and gets result
                        val outputs = model.process(image)
                        val probability = outputs.probabilityAsCategoryList

                        //Log.d(MainActivity.TAG, probability.toString())


                        // Get max score - best prediction
                        var maxSoFar = 0f
                        var element_score = 0f
                        var bestPredictedLabel = ""

                        for (element in probability) {
                            element_score = element.score
                            if (element_score > maxSoFar) {
                                maxSoFar = element_score
                                bestPredictedLabel = element.label
                            }
                        }


                        // Set TextView to display the correct food type
                        output_predict_view.text = bestPredictedLabel

                        // Lowercase
                        bestPredictedLabel = bestPredictedLabel.lowercase()

                        // For pizza :D

                        if (bestPredictedLabel.contains("pizza", ignoreCase = true)) {
                            bestPredictedLabel = "pizza"
                        }

                        // Check if we have data for it in HashMap of carbon emissions values for food
                        val hasKey = foodEmissionsHashmap.containsKey(bestPredictedLabel)
                        if (hasKey) {
                            v.findViewById<TextView>(R.id.carbon_per_food).text = foodEmissionsHashmap[bestPredictedLabel].toString()
                        } else {
                            v.findViewById<TextView>(R.id.carbon_per_food).text = "?"
                        }


                        // Releases model resources if no longer used
                        model.close()
                    } else {
                        Log.d(TAG, "Error: Bitmap is null! :(")
                    }

                }


            } catch (e: IOException) {
                // TO DO
            }
        }

        return v
    }



    // Get image
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == AppCompatActivity.RESULT_OK && requestCode == getImageRequestCode) {
            imageUri = data?.data
            selected_img_view.setImageURI(imageUri)
        }
    }


}