package com.android.kit.ui.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.android.kit.contract.ResultContractor
import com.android.kit.listener.AnyEventListener
import com.mustahsan.androidkit.ktx.appName

abstract class KitActivity<Binding : ViewDataBinding> : AppCompatActivity() {

    private val contractForResult = ResultContractor.registerActivityForResult(this)
    private val contractForPermission = ResultContractor.registerForActivityResult(
        this,
        ActivityResultContracts.RequestPermission()
    )
    private val contractForMultiplePermissions = ResultContractor.registerForActivityResult(
        this,
        ActivityResultContracts.RequestMultiplePermissions()
    )

    private var _binding: Binding? = null
    protected val binding: Binding
        get() = _binding!!

    abstract fun onCreateBinding(): Binding

    private var backClickListener: AnyEventListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        _binding = onCreateBinding()
//        val mode = PreferenceUtils.nightMode
//        AppCompatDelegate.setDefaultNightMode(mode.mode)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    fun enableBackButton(backClickListener: AnyEventListener? = null) {
        this.backClickListener = backClickListener
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            android.R.id.home -> {
                backClickListener?.let { it() } ?: run {
                    try {
                        onBackPressed()
                    } catch (ex: Exception) {
                        ex.printStackTrace()
                        finish()
                    }
                }
            }
            else -> {
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    protected fun finish(resultCode: Int) {
        setResult(resultCode)
        finish()
    }

    protected fun finish(resultCode: Int, data: Intent) {
        setResult(resultCode, data)
        finish()

        supportFragmentManager
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

    fun replaceFragment(containerId: Int, fragment: Fragment) {
        supportFragmentManager.commit(allowStateLoss = false) {
            replace(containerId, fragment)
        }
    }

    fun restert() {
        finish()
        startActivity(intent)
    }
}

val Activity.appName: String
    get() = (this as Context).appName