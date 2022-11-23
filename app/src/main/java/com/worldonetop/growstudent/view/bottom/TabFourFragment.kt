package com.worldonetop.growstudent.view.bottom

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.worldonetop.growstudent.R
import com.worldonetop.growstudent.databinding.FragmentTabFourBinding
import com.worldonetop.growstudent.model.Resp
import com.worldonetop.growstudent.model.User
import com.worldonetop.growstudent.source.Local
import com.worldonetop.growstudent.source.Remote
import com.worldonetop.growstudent.util.MyConverter
import com.worldonetop.growstudent.view.LoginActivity
import com.worldonetop.growstudent.view.MainActivity
import com.worldonetop.growstudent.view.dialog.LoadingDialog
import com.worldonetop.growstudent.vm.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import kotlin.math.min


@AndroidEntryPoint
class TabFourFragment: Fragment() {
    private var _binding: FragmentTabFourBinding? = null
    private val binding get() = _binding!!
    @Inject lateinit var loadingDialog: LoadingDialog
    @Inject lateinit var remote: Remote
    private val mainVM: MainViewModel by activityViewModels()
    private var rankDialog:Dialog? = null
    private var simpleAdapter:SimpleAdapter? = null
    private var userList:Array<User>? = null
    var dialogItemList: ArrayList<Map<String, Any>> =ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTabFourBinding.inflate(layoutInflater,container, false)
        binding.isLogout.text = if(!Local(requireContext()).isAlone())
            "로그아웃"
        else
            "로그인"
        initListener()
        return binding.root
    }
    private fun initListener(){
        binding.logout.setOnClickListener{
            if(!Local(requireContext()).isAlone()){
                saveRemote(false)
            }else{
                startActivity(
                    Intent(requireContext(), LoginActivity::class.java)
                        .putExtra("user",mainVM.getUser())
                        .putExtra("items",MyConverter.itemsListToStr(mainVM.items.value!!)))
                activity?.finish()
            }
//            binding.save.setOnClickListener{
//                Log.d("asd","ASDASD")
//                saveRemote(true)
//            }
//            binding.rank.setOnClickListener{
//                Log.d("asd","ASDASzxczcxD")
//                showRankDialog()
//            }
        }

    }

    private fun saveRemote(onlySave:Boolean){

        loadingDialog.dialog.show()
        val user = mainVM.getUser().apply {
            x = (activity as MainActivity).userLoc.x
            y = (activity as MainActivity).userLoc.y
        }
        mainVM.items.value?.let {
            Local(requireContext()).setItemData(MyConverter.itemsListToStr(it))
        }

        val response = remote.save(
            user.id,user.x,user.y,user.speed,user.money,user.moneyStr,user.hp,user.hpStr,user.int,user.intStr,user.duty,user.dutyStr,
            MyConverter.itemsListToStr(mainVM.items.value!!)
        )

        response.enqueue(object : Callback<Resp<Unit>> {
            override fun onResponse(call: Call<Resp<Unit>>, response: Response<Resp<Unit>>) {
                if(response.code() == 200){
                    response.body()?.let {
                        when(it.code){
                            0 -> {
                                if(!onlySave) {
                                    Local(requireContext()).setLogin(false)
                                    startActivity(
                                        Intent(requireContext(), LoginActivity::class.java)
                                    )
                                    activity?.finish()
                                }else{
                                    Toast.makeText(requireContext(),"저장되었습니다.", Toast.LENGTH_SHORT).show()
                                }
                            }
                            else -> {
                                Toast.makeText(requireContext(),"로그인을 확인해주세요.", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }else{
                    Toast.makeText(requireContext(),getString(R.string.error_unknown), Toast.LENGTH_SHORT).show()
                }
                loadingDialog.dialog.dismiss()
            }

            override fun onFailure(call: Call<Resp<Unit>>, t: Throwable) {
                loadingDialog.dialog.dismiss()
                Toast.makeText(requireContext(),getString(R.string.error_failUre), Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun showRankDialog(){
        loadingDialog.dialog.show()
        if(userList == null) initUserList()

        if(simpleAdapter==null){
            simpleAdapter = SimpleAdapter(
                requireContext(),
                dialogItemList,
                R.layout.row_dialog_rank,
                arrayOf("id","content"),
                intArrayOf(R.id.rowDialogRankId, R.id.rowDialogRankContent)
            )
        }
        if(rankDialog == null) {
            rankDialog = Dialog(requireContext()).apply {
                setContentView(R.layout.dialog_rank)
                window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//                window!!.setLayout(
//                    (MySize.width - 80 * MySize.density).toInt(),
//                    (MySize.height - 300 * MySize.density).toInt()
//                )
                setCanceledOnTouchOutside(false)
                findViewById<Button>(R.id.rankCloseBtn).setOnClickListener { this.dismiss() }
                findViewById<ListView>(R.id.rankListView).adapter = simpleAdapter
                findViewById<TextView>(R.id.rankTab1).setOnClickListener { rankCategory(1)}
                findViewById<TextView>(R.id.rankTab2).setOnClickListener { rankCategory(2)}
                findViewById<TextView>(R.id.rankTab3).setOnClickListener { rankCategory(3)}
            }
        }
        rankDialog?.show()
    }
    private fun rankCategory(category:Int){
        if(userList != null){
            userList?.sortBy {
                when(category){
                    0->{
                        it.intStr.last()
                    }1->{
                    it.moneyStr.last()
                    }else->{
                    it.hpStr.last()
                    }
                }
            }
            dialogItemList.clear()
            for(i in 0 until min(userList!!.size, 10)) {
                val temp = HashMap<String, String>().apply {
                    put("id",userList!![i].id)
                    put("content",
                    when(category){
                        0->userList!![i].intStr
                        1->userList!![i].moneyStr
                        else->userList!![i].hpStr
                    })
                }
                dialogItemList.add(temp)
            }
            simpleAdapter?.notifyDataSetChanged()
        }
    }
    private fun initUserList(){
        remote.rank().enqueue(object : Callback<Array<User>> {
            override fun onResponse(call: Call<Array<User>>, response: Response<Array<User>>) {
                if(response.code() == 200){
                    response.body()?.let {
                        userList = it
                        rankCategory(1)
                    }
                }else{
                    Toast.makeText(requireContext(),getString(R.string.error_unknown), Toast.LENGTH_SHORT).show()
                }
                loadingDialog.dialog.dismiss()
            }

            override fun onFailure(call: Call<Array<User>>, t: Throwable) {
                loadingDialog.dialog.dismiss()
                Toast.makeText(requireContext(),getString(R.string.error_failUre), Toast.LENGTH_SHORT).show()
            }
        })
    }


}