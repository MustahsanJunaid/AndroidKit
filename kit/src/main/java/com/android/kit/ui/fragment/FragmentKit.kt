package com.android.kit.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.android.kit.contract.ResultContractor

abstract class FragmentKit<Binding : ViewDataBinding> : Fragment() {

    private var _binding: Binding? = null
    protected val binding: Binding
        get() = _binding!!

    private val contractForResult = ResultContractor.registerActivityForResult(this)
    private val contractForPermission = ResultContractor.registerForActivityResult(
        this,
        ActivityResultContracts.RequestPermission()
    )
    private val contractForMultiplePermissions = ResultContractor.registerForActivityResult(
        this,
        ActivityResultContracts.RequestMultiplePermissions()
    )

    abstract fun onCreateBinding(inflater: LayoutInflater, container: ViewGroup?): Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = onCreateBinding(layoutInflater, container)
        return _binding!!.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    protected fun launchForResult(intent: Intent, result: (result: ActivityResult) -> Unit) {
        contractForResult.launch(intent) { result(it) }
    }

    protected fun requestPermission(permission: String, result: (isGranted: Boolean) -> Unit) {
        contractForPermission.launch(permission) { result(it) }
    }

    protected fun requestPermission(
        permissions: Array<String>,
        result: (resultMap: MutableMap<String, Boolean>) -> Unit
    ) {
        contractForMultiplePermissions.launch(permissions) { result(it) }
    }

    fun replaceFragment(containerId: Int, fragment: Fragment, allowStateLoss: Boolean = false) {
        activity?.let {
            childFragmentManager.commit(allowStateLoss) {
                replace(containerId, fragment)
            }
        }
    }
}