package com.tiagorocha.tasks.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.tiagorocha.tasks.R
import com.tiagorocha.tasks.viewmodel.LoginViewModel
import java.util.concurrent.Executor

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var _viewModel: LoginViewModel
    private lateinit var _btnLogin: Button
    private lateinit var _editEmail: EditText
    private lateinit var _editPassword: EditText
    private lateinit var _register: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        _btnLogin = findViewById(R.id.button_login)
        _editEmail = findViewById(R.id.edit_email)
        _editPassword = findViewById(R.id.edit_password)
        _viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        _register = findViewById(R.id.text_register)


        setListener()
        observer()


       // _viewModel.isAuthenticationAvaliable()
        //verifica login do user
       verifyLoggedUser()
    }

    override fun onClick(v: View) {
        if (v.id == R.id.button_login) {
            handleLogin()
        }else if (v.id == R.id.text_register) {
            startActivity(Intent(this, FormUserActivity::class.java))
        }
    }
    private fun showAutentication(){
        //executor
        val executor : Executor = ContextCompat.getMainExecutor(this)

        //BiometricPrompt
        val biometricPrompt = BiometricPrompt(this@LoginActivity, executor,
            object : BiometricPrompt.AuthenticationCallback(){
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                    finish()
                }
        })

        //BiometricPrompt info
        val biometricPromptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Titulo")
            .setSubtitle("Subtitulo")
            .setDescription("Descrição")
            .setNegativeButtonText("Cancelar")
            .build()

        biometricPrompt.authenticate(biometricPromptInfo)

    }

    fun setListener() {
        _btnLogin.setOnClickListener(this)
        _register.setOnClickListener(this)
    }
    fun observer() {
        //verificar se o usuário esta logado
        _viewModel.fingerPrint.observe(this, Observer{
            if(it){
                showAutentication()
            }
        })
        _viewModel.loogedUser.observe(this, Observer{
            if(it){
                startActivity(Intent(applicationContext, MainActivity::class.java))
                finish()
            }
        })
        _viewModel.login.observe(this, Observer {
            if(it.success()){
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }else{
                var msg = it.failure()
                Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
            }
        })
    }
    /**
     * Verifica se usuário está logado
     */
    private fun verifyLoggedUser() {
        _viewModel.isAuthenticationAvaliable()
    }
    /**
     * Autentica usuário
     */
    private fun handleLogin() {
        var email = _editEmail.text.toString()
        var password = _editPassword.text.toString()

       _viewModel.doLogin(email, password)
    }
}