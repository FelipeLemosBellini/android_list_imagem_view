package com.example.list_images

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.list_images.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    var counter: Int = 0;
    var listImage = listOf<String>(
        "https://images.unsplash.com/photo-1506748686214-e9df14d4d9d0",
        "https://images.unsplash.com/photo-1519337265831-281ec6cc8514",
        "https://images.unsplash.com/photo-1495567720989-cebdbdd97913",
        "https://images.unsplash.com/photo-1472214103451-9374bd1c798e",
        "https://images.unsplash.com/photo-1503023345310-bd7c1de61c7d",
        "https://images.unsplash.com/photo-1516110833967-214a01a98028",
        "https://images.unsplash.com/photo-1517817748491-31d1eb7b0e73",
        "https://images.unsplash.com/photo-1517948430545-d3b92eac6c15",
        "https://images.unsplash.com/photo-1518073891098-cb5bdb4b2491",
        "https://images.unsplash.com/photo-1499951360447-b19be8fe80f5",
        "https://images.unsplash.com/photo-1517495306984-1a43ea63fad3",
        "https://images.unsplash.com/photo-1531177077037-b3f97a7d35a7",
        "https://images.unsplash.com/photo-1534975680042-9fbd9d6a1f90",
        "https://images.unsplash.com/photo-1516651029879-d3a89b2b7e78",
        "https://images.unsplash.com/photo-1475823678248-624fc6f85785"
    );



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

        binding.fabCamera.setOnClickListener({
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            val image  = startActivityForResult(takePictureIntent, 1)
        })

        setImage(listImage[0])
        binding.btnNext.setOnClickListener() {
            counter++
            setImage(listImage[counter])
        }

        binding.btnGoBack.setOnClickListener() {
            if (counter > 0) {
                counter--
                setImage(listImage[counter])
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            val thumbnail: Bitmap = data?.getParcelableExtra("data")!!
            Glide.with(this)
                .load(thumbnail)
                .apply(RequestOptions.skipMemoryCacheOf(true))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                .into(binding.image)
        }
    }

    private fun setImage(image: String) {
        Glide.with(this)
            .load(image)
            .apply(RequestOptions.skipMemoryCacheOf(true))
            .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
            .into(binding.image)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("", "onDestroy")
    }

    override fun onPause() {
        super.onPause()
    }
}