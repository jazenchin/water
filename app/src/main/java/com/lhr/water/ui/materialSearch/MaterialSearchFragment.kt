package com.lhr.water.ui.materialSearch

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
import com.lhr.water.databinding.FragmentMaterialSearchBinding
import com.lhr.water.room.FormEntity
import com.lhr.water.ui.base.BaseFragment
import com.lhr.water.ui.formContent.FormContentActivity
import com.lhr.water.ui.form.dealMaterial.DealMaterialActivity
import com.lhr.water.util.adapter.MaterialSearchAdapter
import timber.log.Timber

class MaterialSearchFragment : BaseFragment(), View.OnClickListener {

    private var _binding: FragmentMaterialSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MaterialSearchViewModel by viewModels { viewModelFactory }
    private lateinit var materialSearchAdapter: MaterialSearchAdapter

    private val callback = object : OnBackPressedCallback(true /* enabled by default */) {
        override fun handleOnBackPressed() {
                requireActivity().finish()
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMaterialSearchBinding.inflate(layoutInflater)

        initView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewModel()
    }

    private fun bindViewModel() {

        // 當storageRecordEntities或checkoutEntities更新後需要重新計算tempStorageRecordEntities
        viewModel.formRepository.checkoutEntities.observe(viewLifecycleOwner) { _ ->
            viewModel.tempStorageRecordEntities.postValue(viewModel.getStorageContentList())
            binding.editSearch.text.clear()
            viewModel.searchMaterialName.postValue("")
        }
        viewModel.formRepository.storageRecordEntities.observe(viewLifecycleOwner) { _ ->
            viewModel.tempStorageRecordEntities.postValue(viewModel.getStorageContentList())
            binding.editSearch.text.clear()
            viewModel.searchMaterialName.postValue("")
        }
        // tempStorageRecordEntities有變動後要重新帶入materialSearchAdapter
        viewModel.tempStorageRecordEntities.observe(viewLifecycleOwner) { list ->
            materialSearchAdapter.submitList(list)
        }
        // 貨物名稱輸入後篩選更新
        viewModel.searchMaterialName.observe(viewLifecycleOwner) {searchMaterialName ->
            materialSearchAdapter.submitList(viewModel.filterRecord(viewModel.tempStorageRecordEntities.value!!, searchMaterialName))
        }
    }

    private fun initView() {
        binding.widgetTitleBar.textTitle.text = requireActivity().getString(R.string.search_material)
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
                viewModel.searchMaterialName.postValue(s.toString())
            }
        })

        binding.widgetTitleBar.imageFilter.setOnClickListener(this)
    }

    private fun initRecyclerView() {
        materialSearchAdapter = MaterialSearchAdapter(viewModel, requireContext())
        binding.recyclerForm.adapter = materialSearchAdapter
        binding.recyclerForm.layoutManager = LinearLayoutManager(activity)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.imageFilter -> {
            }
        }
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