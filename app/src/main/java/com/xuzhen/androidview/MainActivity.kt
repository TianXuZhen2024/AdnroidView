package com.xuzhen.androidview

import android.os.Bundle
import android.view.MotionEvent
import android.webkit.WebView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.xuzhen.androidview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        window.statusBarColor = ContextCompat.getColor(this, com.xuzhen.library_base.R.color.statusBarColor)
        initView()

    }

    private fun initView() {
        val items = mutableListOf<String>()
        var i = 0
        repeat(20) {
            i++
            items.add("这是第${i}条数据")
        }
        val testAdapter = TestAdapter(items)
        testAdapter.setStateViewLayout(this, com.xuzhen.library_base.R.layout.item_empty_view)
        binding.recycle.apply {
            adapter = testAdapter
            (adapter as TestAdapter).isStateViewEnable = true
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }
}