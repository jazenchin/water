package com.lhr.water.ui.form

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.lhr.water.R
import com.lhr.water.repository.FormRepository
import com.lhr.water.databinding.FragmentFormBinding
import com.lhr.water.room.FormEntity
import com.lhr.water.ui.base.BaseFragment
import com.lhr.water.ui.formContent.FormContentActivity
import com.lhr.water.ui.form.dealMaterial.DealMaterialActivity
import com.lhr.water.util.popupWindow.FilterFormPopupWindow
import com.lhr.water.util.adapter.FormAdapter
import timber.log.Timber

class FormFragment : BaseFragment(), View.OnClickListener, FormAdapter.Listener {

    private var _binding: FragmentFormBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FormViewModel by viewModels { viewModelFactory }
    private lateinit var formAdapter: FormAdapter
    val formRepository: FormRepository by lazy { FormRepository.getInstance(requireContext()) }

    private val callback = object : OnBackPressedCallback(true /* enabled by default */) {
        override fun handleOnBackPressed() {
                requireActivity().finish()
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFormBinding.inflate(layoutInflater)

        initView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewModel()
        viewModel.formRepository.loadSqlData()
    }

    private fun bindViewModel() {
        formRepository.formEntities.observe(viewLifecycleOwner) { newFormRecordList ->
            formAdapter.submitList(viewModel.filterRecord( newFormRecordList, viewModel.searchFormNumber.value!! ,viewModel.filterList.value!!))
        }
        // 表單類別篩選更新
        viewModel.filterList.observe(viewLifecycleOwner) { filterList ->
            formAdapter.submitList(viewModel.filterRecord( formRepository.formEntities.value!!, viewModel.searchFormNumber.value!! ,filterList))
        }
        // 表單代號輸入後篩選更新
        viewModel.searchFormNumber.observe(viewLifecycleOwner) { searchFormNumber ->
            formAdapter.submitList(viewModel.filterRecord(formRepository.formEntities.value!!, searchFormNumber , viewModel.filterList.value!!))
        }
    }

    private fun initView() {
        binding.widgetTitleBar.textTitle.text = requireActivity().getString(R.string.material_in_out)
        binding.widgetTitleBar.imageFilter.visibility = View.VISIBLE
        initRecyclerView()
        binding.editSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // 在文本改變之前執行的操作
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // 在文本改變過程中執行的操作
            }

            override fun afterTextChanged(s: Editable?) {
                // 在文本改變之後執行的操作
                viewModel.searchFormNumber.postValue(s.toString())
            }
        })

        binding.widgetTitleBar.imageFilter.setOnClickListener(this)
    }

    private fun initRecyclerView() {
        formAdapter = FormAdapter(this, requireContext())
        binding.recyclerForm.adapter = formAdapter
        binding.recyclerForm.layoutManager = LinearLayoutManager(activity)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.imageFilter -> {
                showPopupWindow(binding.widgetTitleBar.constraintTitleBar)
            }
        }
    }

    /**
     * 表單列表點擊
     * @param formEntity 被點擊的列資料
     */
    override fun onItemClick(formEntity: FormEntity) {
        val intent = Intent(requireActivity(), FormContentActivity::class.java)
        intent.putExtra("formEntity", formEntity)
        requireActivity().startActivity(intent)
    }

    override fun onDealMaterialClick(formEntity: FormEntity) {
        val intent = Intent(requireActivity(), DealMaterialActivity::class.java)
        intent.putExtra("formEntity", formEntity)
        requireActivity().startActivity(intent)
    }

    /**
     * 顯示篩選清單
     * @param anchorView 要在哪個元件的下方
     */
    private fun showPopupWindow(anchorView: View) {
        // 創建PopupWindow
        val popupWindow = FilterFormPopupWindow(requireActivity(), viewModel)

        // 顯示PopupWindow 在 TitleBar 的下方
        popupWindow.showAsDropDown(anchorView)
    }

    override fun onPause() {
        super.onPause()
        Timber.d("onPause")
        callback.remove()
    }

    override fun onResume() {
        super.onResume()
        Timber.d("onResume")
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }
}