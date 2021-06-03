package com.capstone.productdetection.ui.capture

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.*
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.exifinterface.media.ExifInterface
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.productdetection.ViewModelFactory
import com.capstone.productdetection.databinding.ActivityCaptureBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.task.vision.detector.ObjectDetector
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.max
import kotlin.math.min

class CaptureActivity : AppCompatActivity() {

    companion object {
        private const val CAMERA_PERMISSION_CODE = 1
        private const val CAMERA_REQUEST_CODE = 2
        const val TAG = "TFLite"
        const val REQUEST_IMAGE_CAPTURE: Int = 1
        private const val MAX_FONT_SIZE = 96F
    }

    private lateinit var captureBinding: ActivityCaptureBinding
    private lateinit var inputImageView: ImageView
    private lateinit var currentPhotoPath: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        captureBinding = ActivityCaptureBinding.inflate(layoutInflater)
        setContentView(captureBinding.root)

        captureBinding.btn.setOnClickListener{
            try {
                dispatchTakePictureIntent()
            } catch (e: ActivityNotFoundException) {
                Log.e(TAG, e.message.toString())
            }
        }
        inputImageView = captureBinding.ivImage



    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == CAMERA_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent, CAMERA_PERMISSION_CODE)
            } else {
                Toast.makeText(this, "Oops you just denied the permission for camera", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE &&
            resultCode == Activity.RESULT_OK
        ) {
            setViewAndDetect(getCapturedImage())
        }
    }

    private fun runObjectDetection(bitmap: Bitmap) {

        val image = TensorImage.fromBitmap(bitmap)

        val options = ObjectDetector.ObjectDetectorOptions.builder()
            .setMaxResults(1)
            .setScoreThreshold(0.5f)
            .build()

        val detector = ObjectDetector.createFromFileAndOptions(
            this,
            "model.tflite",
            options
        )

        val results = detector.detect(image)

        val resultToDisplay = results.map {
            val category = it.categories.first()
            val text = "${category.label}, ${category.score.times(100).toInt()}%"


            DetectionResult(it.boundingBox, text)

        }
        val imgWithResult = drawDetectionResult(bitmap, resultToDisplay)
        runOnUiThread {
            inputImageView.setImageBitmap(imgWithResult)

            val factory = ViewModelFactory.getInstance(this)
            val viewModel = ViewModelProvider(this@CaptureActivity, factory)[CaptureViewModel::class.java]
            val captureAdapter = CaptureAdapter()


            viewModel.getMaterial(results[0].categories[0].label.trim()).observe(this, { data ->
                captureAdapter.setList(data.material)
                captureAdapter.notifyDataSetChanged()
                Log.e(TAG, "Outpur")
            })

            with(captureBinding.rvListMaterial) {
                layoutManager = LinearLayoutManager(this@CaptureActivity)
                setHasFixedSize(true)
                adapter = captureAdapter
            }
        }
    }

    private fun setViewAndDetect(bitmap: Bitmap) {
        // Display capture image
        inputImageView.setImageBitmap(bitmap)

        // Run ODT and display result
        // Note that we run this in the background thread to avoid blocking the app UI because
        // TFLite object detection is a synchronised process.
        lifecycleScope.launch(Dispatchers.Default) {
            runObjectDetection(bitmap)
        }
    }

    private fun getCapturedImage(): Bitmap {
        // Get the dimensions of the View
        val targetW: Int = inputImageView.width
        val targetH: Int = inputImageView.height

        val bmOptions = BitmapFactory.Options().apply {
            // Get the dimensions of the bitmap
            inJustDecodeBounds = true

            BitmapFactory.decodeFile(currentPhotoPath, this)

            val photoW: Int = outWidth
            val photoH: Int = outHeight

            // Determine how much to scale down the image
            val scaleFactor: Int = max(1, min(photoW / targetW, photoH / targetH))

            // Decode the image file into a Bitmap sized to fill the View
            inJustDecodeBounds = false
            inSampleSize = scaleFactor
            inMutable = true
        }
        val exifInterface = ExifInterface(currentPhotoPath)
        val orientation = exifInterface.getAttributeInt(
            ExifInterface.TAG_ORIENTATION,
            ExifInterface.ORIENTATION_UNDEFINED
        )

        val bitmap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions)
        return when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> {
                rotateImage(bitmap, 90f)
            }
            ExifInterface.ORIENTATION_ROTATE_180 -> {
                rotateImage(bitmap, 180f)
            }
            ExifInterface.ORIENTATION_ROTATE_270 -> {
                rotateImage(bitmap, 270f)
            }
            else -> {
                bitmap
            }
        }
    }

    /**
     * rotateImage():
     *     Decodes and crops the captured image from camera.
     */
    private fun rotateImage(source: Bitmap, angle: Float): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(angle)
        return Bitmap.createBitmap(
            source, 0, 0, source.width, source.height,
            matrix, true
        )
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyy//MM/dd HH:mm:ss", Locale.getDefault()).toString()
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (e: IOException) {
                    Log.e(TAG, e.message.toString())
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        this,
                        "com.capstone.productdetection.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }
    }
    /**
     * drawDetectionResult(bitmap: Bitmap, detectionResults: List<DetectionResult>
     *      Draw a box around each objects and show the object's name.
     */
    private fun drawDetectionResult(bitmap: Bitmap, detectionResults: List<DetectionResult>): Bitmap {
        val outputBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
        val canvas = Canvas(outputBitmap)
        val pen = Paint()
        pen.textAlign = Paint.Align.LEFT

        detectionResults.forEach {
            // draw bounding box
            pen.color = Color.RED
            pen.strokeWidth = 8F
            pen.style = Paint.Style.STROKE
            val box = it.boundingBox
            canvas.drawRect(box, pen)


            val tagSize = Rect(0, 0, 0, 0)

            // calculate the right font size
            pen.style = Paint.Style.FILL_AND_STROKE
            pen.color = Color.YELLOW
            pen.strokeWidth = 2F

            pen.textSize = MAX_FONT_SIZE
            pen.getTextBounds(it.text, 0, it.text.length, tagSize)
            val fontSize: Float = pen.textSize * box.width() / tagSize.width()

            // adjust the font size so texts are inside the bounding box
            if (fontSize < pen.textSize) pen.textSize = fontSize

            var margin = (box.width() - tagSize.width()) / 2.0F
            if (margin < 0F) margin = 0F
            canvas.drawText(
                it.text, box.left + margin,
                box.top + tagSize.height().times(1F), pen
            )
        }
        return outputBitmap
    }

}

/**
 * DetectionResult
 *      A class to store the visualization info of a detected object.
 */
data class DetectionResult(val boundingBox: RectF, val text: String)