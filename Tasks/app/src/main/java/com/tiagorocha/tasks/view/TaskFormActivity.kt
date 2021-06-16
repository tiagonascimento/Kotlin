package com.tiagorocha.tasks.view

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.tiagorocha.tasks.R
import com.tiagorocha.tasks.service.constants.TaskConstants
import com.tiagorocha.tasks.service.model.TaskModel
import com.tiagorocha.tasks.viewmodel.TaskFormViewModel
import java.text.SimpleDateFormat
import java.util.*

class TaskFormActivity : AppCompatActivity(), View.OnClickListener,
    DatePickerDialog.OnDateSetListener {

    private lateinit var _viewModel: TaskFormViewModel
    private lateinit var _btnDate: Button
    private lateinit var _spinner: Spinner
    private lateinit var _btnSave: Button
    private lateinit var _txtDescription: EditText
    private lateinit var _checkComplete: CheckBox
    private var _priorityListId: MutableList<Int> = arrayListOf()
    private var _TaskId: Int = 0
    private val _dateFormate = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_form)

        _btnDate = findViewById(R.id.button_date)
        _spinner = findViewById(R.id.spinner_priority)
        _btnSave = findViewById(R.id.button_save)
        _txtDescription = findViewById(R.id.edit_description)
        _checkComplete = findViewById(R.id.check_complete)

        _viewModel = ViewModelProvider(this).get(TaskFormViewModel::class.java)

        listener()
        observer()
        _viewModel.listPriorities()
        loadDataFormActivity()
    }

    private fun observer() {
        _viewModel.priorites.observe(this, androidx.lifecycle.Observer {
            var list: MutableList<String> = arrayListOf()
            for (item in it) {
                list.add(item.description)
                _priorityListId.add(item.id)
            }
            var adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, list)
            _spinner.adapter = adapter
        })
        _viewModel.validation.observe(this, androidx.lifecycle.Observer {
            if (it.success()) {
                if(_TaskId==0) {
                    toast(getString(R.string.task_created))
                }else{
                    toast(getString( R.string.task_updated))
                }
                finish()
            } else {
                toast( it.failure())
            }
        })
        _viewModel.task.observe(this, androidx.lifecycle.Observer {
            _txtDescription.setText(it.description)
            _checkComplete.isChecked = it.complete
            val date = SimpleDateFormat("yyy-MM-dd").parse(it.date)
            _btnDate.text = _dateFormate.format(date)
            _spinner.setSelection(getIndex(it.priorityId))

        })
    }
    private fun toast(msg: String){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
    private fun getIndex(priorityId:Int) : Int{
        var index= 0
        for(i in 0 until _priorityListId.count()){
            if(_priorityListId[i]==priorityId){
                index = i
                break
            }
        }
        return index
    }
    private fun loadDataFormActivity() {
        val bundle = intent.extras
        if (bundle != null) {
            _TaskId = bundle.getInt(TaskConstants.BUNDLE.TASKID)
            _viewModel.load(_TaskId)
            _btnSave.text = getText(R.string.update_task)
        }
    }
    private fun listener() {
        _btnDate.setOnClickListener(this)
        _btnSave.setOnClickListener(this)
    }
    override fun onClick(v: View) {
        if (v.id == R.id.button_date) {
            showDatePiker()
        } else if (v.id == R.id.button_save) {
            handleSave()
        }
    }
    private fun handleSave() {
        val task = TaskModel().apply {
            this.id = _TaskId
            this.description = _txtDescription.text.toString()
            this.priorityId = _priorityListId[_spinner.selectedItemPosition]
            this.date = _btnDate.text.toString()
            this.complete = _checkComplete.isChecked
        }
        _viewModel.save(task)
    }
    private fun showDatePiker() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        DatePickerDialog(this, this, year, month, day).show()
    }
    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        val str = _dateFormate.format(calendar.time)
        _btnDate.text = str

    }
}