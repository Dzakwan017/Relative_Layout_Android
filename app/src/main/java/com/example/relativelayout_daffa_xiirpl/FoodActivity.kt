package com.example.relativelayout_daffa_xiirpl

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.example.relativelayout_daffa_xiirpl.adapter.CategoriesAdapter
import com.example.relativelayout_daffa_xiirpl.model.CategoriesModel
import com.example.relativelayout_daffa_xiirpl.networking.Api
import org.json.JSONException
import org.json.JSONObject
import java.util.ArrayList
import com.androidnetworking.AndroidNetworking as AndroidNetworking1

class FoodActivity : AppCompatActivity() {
    lateinit var categoriesAdapter: CategoriesAdapter
    lateinit var categoriesModel: CategoriesModel
    lateinit var categoriesList: ArrayList<CategoriesModel>
    lateinit var rvCategories: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food)
        //isi nilai default (inisialisasi)
        categoriesList = ArrayList()
        rvCategories = findViewById(R.id.rv_food)
        var layoutManager = GridLayoutManager(this, 2, RecyclerView.VERTICAL, false)
        rvCategories.layoutManager = layoutManager
        rvCategories.setHasFixedSize(true)

        categories
    }

    private val categories : Unit
        get(){
            AndroidNetworking1.get(Api.Categories)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {
                    override fun onResponse(response: JSONObject?) {
                        try {
                            var foodArray = response?.getJSONArray("categories")
                            for (i in 0 until  foodArray!!.length()){
                                var temp = foodArray.getJSONObject(i)
                                var dataApi = CategoriesModel()
                                dataApi.strCategory = temp.getString("strCategory")
                                dataApi.strCategoryThumb = temp.getString("strCategoryThumb")
                                dataApi.strCategoryDescription = temp.getString("strCategoryDescription")

                                categoriesList.add(dataApi)
                                showCategories()
                            }
                        }
                        catch (e: JSONException){
                            e.printStackTrace()
                            Toast.makeText(this@FoodActivity, "Object not found", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onError(anError: ANError?) {
                        Toast.makeText(this@FoodActivity, "Close connection", Toast.LENGTH_SHORT).show()
                    }

                })
        }

    private fun showCategories() {
        //inisisalisasi categoriesAdapter dengan array category List dari API
        categoriesAdapter = CategoriesAdapter(this, categoriesList)
        rvCategories.adapter = categoriesAdapter
    }

}