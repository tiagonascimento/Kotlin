package com.tiagorocha.tasks.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tiagorocha.tasks.R
import com.tiagorocha.tasks.service.constants.TaskConstants
import com.tiagorocha.tasks.service.listener.TaskListener
import com.tiagorocha.tasks.service.listener.ValidationListener
import com.tiagorocha.tasks.view.adapter.TaskAdapter
import com.tiagorocha.tasks.viewmodel.AllTasksViewModel
import com.tiagorocha.tasks.viewmodel.TaskFormViewModel
import java.util.*

class AllTasksFragment : Fragment() {

    private lateinit var viewModel: AllTasksViewModel
    private lateinit var _listener:TaskListener
    private val _adapter = TaskAdapter()
    private var _TaskFilder = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View{

        viewModel = ViewModelProvider(this).get(AllTasksViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_all_tasks, container, false)
        val recycler = root.findViewById<RecyclerView>(R.id.recycle_all_tasks)
        recycler.layoutManager = LinearLayoutManager(context)
        recycler.adapter = _adapter
        _TaskFilder =  requireArguments().getInt(TaskConstants.BUNDLE.TASKFILTER,0)
        //eventos
        _listener = object: TaskListener{
            override fun onListClick(id: Int) {
               val intent = Intent(context, TaskFormActivity::class.java)
                val bundle = Bundle()
                bundle.putInt(TaskConstants.BUNDLE.TASKID, id)
                intent.putExtras(bundle)
                startActivity(intent)
            }

            override fun onDeleteClick(id: Int) {
                viewModel.delete(id)
            }
            override fun onCompleteClick(id: Int) {
                viewModel.complete(id)
            }
            override fun onUndoClick(id: Int) {
                viewModel.undo(id)
            }
        }
        observer()
        return root
    }

    override fun onResume() {
        super.onResume()
       _adapter.attachListener(_listener)
        viewModel.list(_TaskFilder)
    }
    private fun observer(){
        viewModel.tasks.observe(viewLifecycleOwner, Observer {
            if(it.count()>0){
                _adapter.updateList(it)
            }
        })
        viewModel.validation.observe(viewLifecycleOwner, Observer {
            if(it.success()){
                toast(getString(R.string.task_removed))
            }else{
                toast(it.failure())
            }
        })
    }
    private fun toast(msg: String){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }
}