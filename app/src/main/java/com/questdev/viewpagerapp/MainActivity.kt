package com.questdev.viewpagerapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.questdev.viewpagerapp.databinding.ActivityMainBinding
import com.questdev.viewpagerapp.databinding.ViewCarouselItemBinding
import kotlin.math.abs

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val adapter by lazy { Adapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.vpCarousel.adapter = adapter
        binding.vpCarousel.offscreenPageLimit = 3
        binding.vpCarousel.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        val pageTransformer = CompositePageTransformer().apply {
            addTransformer(MarginPageTransformer(40))
            addTransformer { page, position ->
                val r = 1 - abs(position)
                page.scaleY = 0.85f + r * 0.15f
            }
        }
        binding.vpCarousel.setPageTransformer(pageTransformer)

        adapter.submitList(items)
    }
}

class Adapter : RecyclerView.Adapter<ViewHolder>() {

    private var data = mutableListOf<CarouselItem>()

    fun submitList(list: MutableList<CarouselItem>) {
        data = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.binding.ivImage.setImageResource(item.image)
        holder.binding.tvDescription.text = holder.itemView.context.getString(item.description)
    }

    override fun getItemCount(): Int {
        return data.size
    }


}

class ViewHolder(val binding: ViewCarouselItemBinding): RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun from(parent: ViewGroup): ViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            return ViewHolder(ViewCarouselItemBinding.inflate(inflater, parent, false))
        }
    }
}

data class CarouselItem(
    val image: Int,
    val description: Int,
)

val items = mutableListOf(
    CarouselItem(R.drawable.image_1, R.string.lorem_ipsum),
    CarouselItem(R.drawable.image_2, R.string.lorem_ipsum),
    CarouselItem(R.drawable.image_3, R.string.lorem_ipsum),
    CarouselItem(R.drawable.image_4, R.string.lorem_ipsum),
    CarouselItem(R.drawable.image_5, R.string.lorem_ipsum),
    CarouselItem(R.drawable.image_6, R.string.lorem_ipsum),
)