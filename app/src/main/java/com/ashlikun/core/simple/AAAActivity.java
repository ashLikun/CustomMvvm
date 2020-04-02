package com.ashlikun.core.simple;

import com.ashlikun.core.mvvm.BaseMvvmActivity;
import com.ashlikun.core.mvvm.IViewModel;

/**
 * 作者　　: 李坤
 * 创建时间: 2020/4/2　15:49
 * 邮箱　　：496546144@qq.com
 * <p>
 * 功能介绍：
 */
@IViewModel(MainViewModel.class)
public class AAAActivity extends BaseMvvmActivity<MainViewModel> {
    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {

    }
}
