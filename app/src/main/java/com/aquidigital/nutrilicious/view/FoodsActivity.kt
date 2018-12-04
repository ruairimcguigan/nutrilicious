package com.aquidigital.nutrilicious.view

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.aquidigital.nutrilicious.R
import com.aquidigital.nutrilicious.http.NETWORK
import com.aquidigital.nutrilicious.http.USDA_USDA_API
import com.aquidigital.nutrilicious.model.data.Food
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class FoodsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setSearchFoodList()
        navigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener())


        GlobalScope.launch(NETWORK) {
            USDA_USDA_API.getFoods("raw").execute()  // Logs results to Logcat due to interceptor
        }
    }

    private fun setSearchFoodList() = with(rvFoodsList) {
        adapter = FoodsAdapter(sampleData())
        layoutManager = LinearLayoutManager(this@FoodsActivity)
        addItemDecoration(DividerItemDecoration(this@FoodsActivity, LinearLayoutManager.VERTICAL))
        setHasFixedSize(true)

    }

    private fun navigationItemSelectedListener() : BottomNavigationView.OnNavigationItemSelectedListener{
        return BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_my_foods -> {
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }
    }

    private fun sampleData() = listOf(
        Food("Cheese", "Dairy", true),
        Food("Brussel sprouts", "Vegetables", true),
        Food("Tomatoes", "Fruit", false))

}
