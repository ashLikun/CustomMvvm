package com.ashlikun.core.simple

import android.content.Intent
import android.os.Bundle
import com.ashlikun.core.mvvm.BaseMvvmActivity
import com.ashlikun.utils.other.LogUtils

class MainActivity : BaseMvvmActivity<MainViewModel>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        LogUtils.e("onCreate")
        super.onCreate(savedInstanceState)
    }

    override fun getLayoutId(): Int {
        LogUtils.e("getLayoutId")
        return R.layout.activity_main
    }

    override fun initView() {
        LogUtils.e("initView")
        supportFragmentManager.beginTransaction()
                .add(R.id.fragment, MainFragment()).commit()
    }

    override fun parseIntent(intent: Intent?) {
    }

    override fun clearData() {
        LogUtils.e("clearData")
    }

}
