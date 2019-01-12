package com.huaqing.property.ui.login

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.huaqing.property.common.loadings.CommonLoadingViewModel
import com.huaqing.property.ext.viewmodel.addLifecycle
import org.kodein.di.Kodein
import org.kodein.di.android.x.AndroidLifecycleScope
import org.kodein.di.bindings.WeakContextScope
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.scoped
import org.kodein.di.generic.singleton

const val LOGIN_MODULE_TAG = "LOGIN_MODULE_TAG"

val loginKodeinModule = Kodein.Module(LOGIN_MODULE_TAG) {

    bind<LoginNavigator>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        LoginNavigator(context.activity!!)
    }

    bind<LoginViewModel>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        ViewModelProviders.of(context.activity!!, LoginViewModelFactory(instance()))
            .get(LoginViewModel::class.java)
            .apply {
                addLifecycle(context)
            }
    }

    bind<LoginViewDelegate>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        LoginViewDelegate(
            viewModel = instance(),
            navigator = instance(),
            loadingViewModel = CommonLoadingViewModel.instance(context)
        )
    }

    bind<LoginRemoteDataSource>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        LoginRemoteDataSource(instance())
    }

    bind<LoginLocalDataSource>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        LoginLocalDataSource(instance(), instance())
    }

    bind<LoginDataSourceRepository>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        LoginDataSourceRepository(instance(), instance())
    }

}