package me.thuongle.crashlyticssample

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.crashlytics.android.Crashlytics
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_create_a_non_fatal.setOnClickListener {
            val logMessage = "A test error"
            Timber.e(RuntimeException(logMessage))
            Toast.makeText(this, "Create $logMessage", Toast.LENGTH_LONG).show()
        }

        btn_create_a_crash.setOnClickListener {
            Crashlytics.getInstance().crash()
        }
    }
}
