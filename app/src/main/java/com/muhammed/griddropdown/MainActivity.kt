package com.muhammed.griddropdown

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewTreeObserver
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.GridView
import android.widget.LinearLayout
import android.widget.PopupWindow
import com.muhammed.griddropdown.databinding.ActivityMainBinding

/**
 * Created by Muhammed Elşami on 30.11.2023.
 */

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding
    private val items = arrayOf("Item1", "Item2", "Item3", "Item4", "Item5", "Item6", "Item7")

    private lateinit var popupWindow : PopupWindow
    private lateinit var gridView : GridView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        initView()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun initView() {

        gridView = GridView(this).apply {
            numColumns = 5
            adapter = ArrayAdapter(this@MainActivity, R.layout.list_item, items)
            onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
                mainBinding.dropdownMenu.setText(items[position])
                popupWindow.dismiss()
            }
            //background = getDrawable(R.color.backgroundColor1)
        }

        mainBinding.dropdownMenuLayout.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                mainBinding.dropdownMenuLayout.viewTreeObserver.removeOnGlobalLayoutListener(this)
                val layoutWidth = mainBinding.dropdownMenuLayout.width

                popupWindow = PopupWindow(gridView, layoutWidth, LinearLayout.LayoutParams.WRAP_CONTENT).apply {
                    isOutsideTouchable = true
                    isFocusable = true
                }

                mainBinding.dropdownMenu.setOnClickListener{
                    popupWindow.showAsDropDown(mainBinding.dropdownMenu)
                }
            }
        })
    }
}