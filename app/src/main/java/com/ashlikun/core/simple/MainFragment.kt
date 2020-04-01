package com.ashlikun.core.simple

import android.util.Log

import com.ashlikun.core.fragment.BaseMvvmFragment

class MainFragment : BaseMvvmFragment<MainViewModel2>() {


    override fun getLayoutId(): Int {
        Log.e("MainFragment", "getLayoutId")
        return R.layout.activity_main

    }

    override fun initView() {
        Log.e("MainFragment", "initView")
    }
}
