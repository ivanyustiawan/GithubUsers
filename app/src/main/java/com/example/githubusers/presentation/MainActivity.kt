package com.example.githubusers.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubusers.databinding.ActivityMainBinding
import com.example.githubusers.model.UserModelView
import com.example.githubusers.presentation.adapter.RecyclerViewUserAdapter
import java.util.*

class MainActivity : AppCompatActivity(), MainInterface {

    private lateinit var binding: ActivityMainBinding
    private lateinit var userViewAdapter: RecyclerViewUserAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private var presenter = MainPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        initSearchListener()
        initRecyclerView()
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        presenter.registerObserver()
    }

    override fun onPause() {
        super.onPause()
        presenter.unregisterObserver()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.unregisterObserver()
        presenter.releaseReference()
    }

    private fun initSearchListener() {
        val timer = Timer()
        binding.layoutToolbar.editTextSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                timer.schedule(object : TimerTask() {
                    override fun run() {
                        presenter.getData(s.toString())
                    }

                }, 500)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })
    }

    private fun initRecyclerView() {
        viewManager = LinearLayoutManager(this)
        userViewAdapter =
            RecyclerViewUserAdapter(
                mutableListOf()
            )
        binding.recyclerViewUser.apply {
            layoutManager = viewManager
            adapter = userViewAdapter
            addItemDecoration(
                DividerItemDecoration(
                    binding.recyclerViewUser.context,
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    override fun onDataSet(list: List<UserModelView>) {
        binding.constraintLayoutContent.visibility = View.VISIBLE
        binding.constraintLayoutEmpty.visibility = View.GONE
        binding.constraintLayoutError.visibility = View.GONE
        userViewAdapter.setData(list)
    }

    override fun onEmptyResult() {
        binding.constraintLayoutEmpty.visibility = View.VISIBLE
        binding.constraintLayoutContent.visibility = View.GONE
        binding.constraintLayoutError.visibility = View.GONE
    }

    override fun onError(error: String) {
        binding.constraintLayoutError.visibility = View.VISIBLE
        binding.constraintLayoutContent.visibility = View.GONE
        binding.constraintLayoutEmpty.visibility = View.GONE
        userViewAdapter.clearData()
        binding.textViewError.text = error
    }

    override fun onEmptyQuery() {
        this.runOnUiThread {
            binding.constraintLayoutContent.visibility = View.VISIBLE
            binding.constraintLayoutEmpty.visibility = View.GONE
            binding.constraintLayoutError.visibility = View.GONE
            userViewAdapter.clearData()
        }
    }
}