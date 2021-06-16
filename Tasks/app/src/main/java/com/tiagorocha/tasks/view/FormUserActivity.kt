package com.tiagorocha.tasks.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.tiagorocha.tasks.R
import com.tiagorocha.tasks.viewmodel.FormUserViewModel
import com.tiagorocha.tasks.viewmodel.LoginViewModel

class FormUserActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var _viewModel: FormUserViewModel
    private lateinit var _btnCadastrar: Button
    private lateinit var _editName: EditText
    private lateinit var _editEmail: EditText
    private lateinit var _editPassword: EditText



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_user)

        _viewModel = ViewModelProvider(this).get(FormUserViewModel::class.java)
        _btnCadastrar  = findViewById(R.id.button_save)
        _editName  = findViewById(R.id.edit_name)
        _editEmail  = findViewById(R.id.edit_email)
        _editPassword  = findViewById(R.id.edit_password)
        setListener()
        observer()
    }

    fun observer() {
        _viewModel.create.observe(this, Observer {
            if(it.success()){
                startActivity(Intent(this, MainActivity::class.java))
            }else{
                var msg = it.failure()
                Toast.makeText(this,msg, Toast.LENGTH_SHORT).show()
            }
        })

    }
    fun setListener() {
        _btnCadastrar.setOnClickListener(this)
    }
    override fun onClick(v: View) {
      if(v.id == R.id.button_save){
          var name = _editName.text.toString()
          var email = _editEmail.text.toString()
          var password = _editPassword.text.toString()

         _viewModel.create(name, email, password,false)
      }
    }
}