package com.example.newdiffutils.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newdiffutils.databinding.ActivityMainBinding
import com.example.newdiffutils.network.models.Todo
import com.example.newdiffutils.ui.adapters.MyAdapter
import com.example.newdiffutils.ui.viewmodels.APICallingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding : ActivityMainBinding
    private lateinit var myAdapter: MyAdapter
    private lateinit var apiCallingViewModel: APICallingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        setUpViewModel()
        setUpRecyclerView()

        apiCallingViewModel.responseContainer.observe(this, Observer {
            if(it != null){
                myAdapter.todos = it
            }
            else{
                Toast.makeText(this, "There is some error!", Toast.LENGTH_SHORT).show()
            }
        })

        apiCallingViewModel.isShowProgress.observe(this, Observer {
            if (it){
                activityMainBinding.mainProgressBar.visibility = View.VISIBLE
            }
            else{
                activityMainBinding.mainProgressBar.visibility = View.GONE
            }
        })

        apiCallingViewModel.errorMessage.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })

        /**
         * Description: This handler will add the new data in the adapter todo list,
         *              So this show, if server side data will changed then how diffUtil will work.
         *              For Using this manually we can understand how it will work in efficient way.
         * @author : Abhishek Oza
         *
         * @since : 8th nov 22
         */

        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            val item1 = Todo(true,1,"Vashudev Krishna",12)
            val item2 = Todo(true,1,"Abhishek",12)
            val item3 = Todo(false,1,"Ankit",12)
            val item4 = Todo(true,1,"Krunal",12)
            val item5 = Todo(false,1,"Akshay",12)
            val item6 = Todo(false,1,"Kamal",12)
            val todoNew = ArrayList<Todo>()
            todoNew.add(item1)
            todoNew.add(item2)
            todoNew.add(item3)
            todoNew.add(item4)
            todoNew.add(item5)
            todoNew.add(item6)
            myAdapter.todos = todoNew
        },4000)


        apiCallingViewModel.getTodosFromAPI()



    }

    private fun setUpRecyclerView() = activityMainBinding.mainRecyclerTodo.apply {
        myAdapter = MyAdapter()
        adapter = myAdapter
        layoutManager = LinearLayoutManager(this@MainActivity)
    }

    private fun setUpViewModel(){
        apiCallingViewModel = ViewModelProvider(this@MainActivity)[APICallingViewModel::class.java]
    }

}