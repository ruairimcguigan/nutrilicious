package com.aquidigital.nutrilicious.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aquidigital.nutrilicious.R
import com.aquidigital.nutrilicious.model.data.Food
import com.aquidigital.nutrilicious.view.FoodsAdapter.FoodViewHolder
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.rv_foods_item.*

class FoodsAdapter(private var items: List<Food>) : RecyclerView.Adapter<FoodViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_foods_item, parent, false)
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: FoodViewHolder, position: Int) {
        viewHolder.bindTo(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun setData(items: List<Food>) {
        this.items = items
        notifyDataSetChanged()
    }

    inner class FoodViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        fun bindTo(food: Food) {
            tvFoodName.text = food.name
            tvFoodType.text = food.type

            val image = if (food.isFavourite) {
                android.R.drawable.btn_star_big_on
            } else {
                android.R.drawable.btn_star_big_off
            }
            ivStar.setImageResource(image)
        }

    }
}
