package fungsiu.kotlin.module

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import fungsiu.kotlin.R
import fungsiu.kotlin.base.BaseActivity
import fungsiu.kotlin.module.post.view.PostListActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Handler().postDelayed({
            val intent = Intent(context, PostListActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
            overridePendingTransition(0, 0)
        }, 1000)
    }

}