package com.a.hub.ui.auth

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.a.hub.R
import com.a.hub.data.Constants.Auth.REDIRECT_URI
import com.a.hub.data.Constants.Auth.SCOPES
import com.a.hub.databinding.ActivityAuthBinding
import com.a.hub.helper.Inform
import com.a.hub.helper.Nat
import com.a.hub.helper.startClearActivity
import com.a.hub.helper.startExternal
import com.a.hub.ui.base.BaseActivity
import com.a.hub.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AuthActivity: BaseActivity() {

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityAuthBinding.inflate(layoutInflater)
        binding.viewModel = viewModel
        setContentView(binding.root)

        checkTheme(
            null,
            binding.root,
            ResourcesCompat.getColor(resources, R.color.primaryVariant, null)
        )

        fullTransparent()

        val url = "https://www.github.com/login/oauth/authorize" +
                "?client_id=${Nat().getId()}&scope=$SCOPES&redirect_uri=$REDIRECT_URI"

        binding.actionAuth.setOnClickListener { startExternal<AuthActivity>(url) }
        binding.actionAuthEnd.setOnClickListener { viewModel.signOut() }
        binding.actionAuthRevoke.setOnClickListener {
            viewModel.signOut()
            //TODO revoke request; callback signOut
        }

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.token.collect {
                        if(it != null) {
                            startClearActivity<AuthActivity>(MainActivity::class.java)
                        }
                    }
                }
                launch {
                    viewModel.out.collect {
                        Inform.show(applicationContext, "Out")
                        startClearActivity<AuthActivity>(MainActivity::class.java)
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val uri = intent.data
        if(uri != null) {
            val code: String = uri.getQueryParameter("code").toString()
            val nat = Nat()
            viewModel.getAccessToken(
                nat.getId(),
                nat.getSecret(),
                code
            )
            intent.data = null
        }
    }

    companion object {
        init {
            System.loadLibrary("native-lib")
        }
    }
}