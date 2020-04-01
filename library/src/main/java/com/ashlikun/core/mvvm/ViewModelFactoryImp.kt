package com.ashlikun.core.mvvm

import android.content.Context
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import java.lang.reflect.ParameterizedType

/**
 * @author　　: 李坤
 * 创建时间: 2020/3/27 17:56
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：创建ViewModel的工厂类
 */

open class ViewModelFactoryImp(var mvvmBaseInterface: MvvmBaseInterface? = null)
    : ViewModelProvider.NewInstanceFactory() {

    override fun <T : androidx.lifecycle.ViewModel?> create(modelClass: Class<T>): T {
        val model = super.create(modelClass)
        if (model is BaseViewModel) {
            if (mvvmBaseInterface is Context) {
                model.context = mvvmBaseInterface as Context
            }
            if (mvvmBaseInterface is LifecycleOwner) {
                model.lifecycleOwner = mvvmBaseInterface as LifecycleOwner
            }
            mvvmBaseInterface?.handeBaseEventer(model)
            if (model is LifecycleObserver) {
                model.addObserver.postValue(model as LifecycleObserver)
            }
        }
        return model
    }

    companion object {
        /**
         * 获取ViewModel注解
         */
        fun <VM : BaseViewModel> getViewModelAnnotation(viewClazz: Class<*>) = viewClazz.getAnnotation(IViewModel::class.java)?.value as Class<VM>?

        /**
         * 获取父类的泛型BaseViewModel
         */
        fun <VM : BaseViewModel> getViewModelParameterizedType(viewClazz: Class<*>): Class<VM>? {
            if (viewClazz.genericSuperclass is ParameterizedType && (viewClazz.genericSuperclass as ParameterizedType).actualTypeArguments.isNotEmpty()) {
                val modelClass2 = (viewClazz.genericSuperclass as ParameterizedType).actualTypeArguments[0]
                if (modelClass2 is Class<*>) {
                    return modelClass2 as Class<VM>
                }
            }
            return null
        }
    }
}