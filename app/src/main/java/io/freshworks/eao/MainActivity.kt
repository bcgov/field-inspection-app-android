package io.freshworks.eao

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.parse.ParseUser
import io.freshworks.eao.screens.inspections.InspectionsActivity
import io.freshworks.eao.screens.login.LoginActivity

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent =
                if (ParseUser.getCurrentUser() != null)
                    Intent(this, InspectionsActivity::class.java)
                else
                    Intent(this, LoginActivity::class.java)

        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

}
