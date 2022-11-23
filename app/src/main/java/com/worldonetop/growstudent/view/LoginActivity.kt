package com.worldonetop.growstudent.view

import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.common.util.Base64Utils
import com.worldonetop.growstudent.R
import com.worldonetop.growstudent.databinding.ActivityLoginBinding
import com.worldonetop.growstudent.model.Item
import com.worldonetop.growstudent.model.Resp
import com.worldonetop.growstudent.model.User
import com.worldonetop.growstudent.source.Local
import com.worldonetop.growstudent.source.Remote
import com.worldonetop.growstudent.util.DefaultUser
import com.worldonetop.growstudent.view.dialog.LoadingDialog
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.security.DigestException
import java.security.MessageDigest
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    @Inject lateinit var remote: Remote
    @Inject lateinit var loadingDialog: LoadingDialog

    var isLogin = true

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityLoginBinding.inflate(layoutInflater)
            setContentView(binding.root)

            if(Local(applicationContext).isLogin() || (Local(applicationContext).isAlone() && !intent.hasExtra("user"))){
                var localData = Local(this@LoginActivity)
                startActivity(
                    Intent(this@LoginActivity, MainActivity::class.java)
                        .putExtra("user",localData.getUserData())
                        .putExtra("items",localData.getItemData()))
                finish()

            }else {
                initEditListener()
                initBtnListener()
            }
    }
    private fun initEditListener(){
        binding.alone.setOnClickListener{
            Local(this@LoginActivity).setLogin(false)
            Local(this@LoginActivity).setAlone(true)
            startActivity(
                Intent(this@LoginActivity, MainActivity::class.java)
                    .putExtra("user",DefaultUser().getUser())
            )
            finish()
        }
        binding.pw.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (checkForm()) {
                    if(isLogin)
                        submitLogin()
                    else
                        submitSignUp()
                } else {
                    Toast.makeText(this, "아이디 혹은 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
                }
            }
            false
        }
    }
    private fun initBtnListener(){
        binding.login.setOnClickListener{
            if(isLogin){ //  로그인체크
                if(checkForm())
                    submitLogin()
                else
                    Toast.makeText(this, "아이디 혹은 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
            }else{
                binding.login.text = "로그인"
                isLogin = true
            }
        }

        binding.signUp.setOnClickListener{
            if(isLogin){
                isLogin = false
                binding.login.text = "❮"
            }else{ //회원가입 체크
                if(checkForm())
                    submitSignUp()
                else
                    Toast.makeText(this, "아이디 혹은 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun submitLogin(){
        loadingDialog.dialog.show()
        val response = remote.login(binding.id.text.toString(),getSHA256(binding.pw.text.toString()))
        response.enqueue(object : Callback<Resp<User>> {
            override fun onResponse(call: Call<Resp<User>>,response: Response<Resp<User>>) {
                if(response.code() == 200){
                    response.body()?.let {
                        when(it.code){
                            0 -> {
                                Local(this@LoginActivity).setLogin(true)
                                Local(this@LoginActivity).setAlone(false)
                                startActivity(
                                    Intent(this@LoginActivity, MainActivity::class.java)
                                        .putExtra("user",it.data)
                                        .putExtra("items",it.items))
                                finish()
                            }
                            1 -> binding.id.error = "해당 아이디를 찾을 수 없습니다."
                            2 -> binding.pw.error = "비밀 번호를 확인해주세요."
                        }
                    }
                }else{
                    Toast.makeText(this@LoginActivity,getString(R.string.error_unknown),Toast.LENGTH_SHORT).show()
                }
                loadingDialog.dialog.dismiss()
            }

            override fun onFailure(call: Call<Resp<User>>, t: Throwable) {
                loadingDialog.dialog.dismiss()
                Toast.makeText(this@LoginActivity,getString(R.string.error_failUre),Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun submitSignUp(){
        loadingDialog.dialog.show()

        val u = if(intent.hasExtra("user"))
                intent.getSerializableExtra("user") as User
            else
                DefaultUser().getUser()

        val items = intent.getStringExtra("items") ?: ""
        val id =binding.id.text.toString()

        val response = remote.signUp(
            id, getSHA256(binding.pw.text.toString()),
            u.x,u.y,u.speed,u.money,u.moneyStr,u.hp,u.hpStr,u.int,u.intStr,u.duty,u.dutyStr, items
        )

        response.enqueue(object : Callback<Resp<Unit>> {
            override fun onResponse(call: Call<Resp<Unit>>,response: Response<Resp<Unit>>) {
                if(response.code() == 200){
                    response.body()?.let {
                        if(it.code == 0){
                            u.id = id
                            Local(this@LoginActivity).setLogin(true)
                            Local(this@LoginActivity).setAlone(false)
                            startActivity(
                                Intent(this@LoginActivity, MainActivity::class.java)
                                    .putExtra("user",u)
                            )
                            finish()
                        }else if(it.code == 3){
                            binding.id.error = "해당 아이디가 존재합니다."
                        }
                    }
                }else{
                    Toast.makeText(this@LoginActivity,getString(R.string.error_unknown),Toast.LENGTH_SHORT).show()
                }
                loadingDialog.dialog.dismiss()
            }

            override fun onFailure(call: Call<Resp<Unit>>, t: Throwable) {
                loadingDialog.dialog.dismiss()
                Toast.makeText(this@LoginActivity,getString(R.string.error_failUre),Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun checkForm():Boolean{
        return binding.id.text.toString().isNotBlank() && binding.pw.text.toString().isNotBlank()
    }
    private fun getSHA256(input: String): String{
        val hash: ByteArray
        try{
            val md = MessageDigest.getInstance("SHA-256")
            md.update(input.toByteArray())
            hash = md.digest()
        }catch (e: CloneNotSupportedException){
            throw DigestException("couldn't make digest of patial content")
        }
        return Base64Utils.encode(hash)
    }
}