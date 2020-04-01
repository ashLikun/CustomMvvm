package com.ashlikun.core

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.*
import com.ashlikun.core.listener.OnDispatcherMessage
import com.ashlikun.loadswitch.ContextData
import com.ashlikun.okhttputils.http.OkHttpUtils
import com.ashlikun.utils.ui.ActivityManager
import java.util.concurrent.ConcurrentHashMap

/**
 * 作者　　: 李坤
 * 创建时间: 2020/3/27　16:39
 * 邮箱　　：496546144@qq.com
 *
 * 功能介绍：VM 的基础
 */
open abstract class BaseViewModel : ViewModel(), LifecycleObserver, OnDispatcherMessage {
    private val maps: ConcurrentHashMap<String, MutableLiveData<*>> by lazy {
        //初始化集合(线程安全)
        ConcurrentHashMap<String, MutableLiveData<*>>()
    }

    /**
     * 数据是否初始化过
     */
    var dataInit = false

    /**
     * 当Activity创建的时候,一般用于主动获取数据
     */
    open fun onCreate() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    open fun onStart() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    open fun onResume() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    open fun onPause() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    open fun onStop() {
    }

    /**
     * 解析意图数据
     */
    open fun parseIntent(intent: Intent) {

    }

    /**
     * 获取前台Activity
     */
    open fun getActivity(): Activity? = ActivityManager.getForegroundActivity()

    /**
     * 通过指定的数据实体类获取对应的MutableLiveData类
     */
    open operator fun <T> get(clazz: Class<T>): MutableLiveData<T>? {
        return get(null, clazz)
    }

    /**
     * 通过指定的key或者数据实体类获取对应的MutableLiveData类
     */
    open operator fun <T> get(key: String?, clazz: Class<T>): MutableLiveData<T>? {
        var keyName: String = key ?: clazz.canonicalName
        var mutableLiveData: MutableLiveData<T>? = maps[keyName] as MutableLiveData<T>?
        //1.判断集合是否已经存在livedata对象
        if (mutableLiveData != null) {
            return mutableLiveData
        }
        //2.如果集合中没有对应实体类的Livedata对象，就创建并添加至集合中
        mutableLiveData = MutableLiveData()
        maps[keyName] = mutableLiveData
        return mutableLiveData
    }


    //清空数据
    val clearData: MutableLiveData<String?> by lazy {
        MutableLiveData<String?>()
    }

    //添加观察者
    val addObserver: MutableLiveData<LifecycleObserver> by lazy {
        MutableLiveData<LifecycleObserver>()
    }

    //销毁页面
    val finish: MutableLiveData<String?> by lazy {
        MutableLiveData<String?>()
    }

    //显示加载布局
    val showLoading: MutableLiveData<ContextData> by lazy {
        MutableLiveData<ContextData>()
    }

    //显示重新加载页面
    val showRetry: MutableLiveData<ContextData> by lazy {
        MutableLiveData<ContextData>()
    }

    //显示内容
    val showContent: MutableLiveData<String?> by lazy {
        MutableLiveData<String?>()
    }

    //显示空页面
    val showEmpty: MutableLiveData<ContextData> by lazy {
        MutableLiveData<ContextData>()
    }

    /**
     * UI发送过来的事件
     *
     * @param what:事件类型
     * @param bundle    事件传递的数据
     */
    override fun onDispatcherMessage(what: Int, bundle: Bundle?) {}

    override fun onCleared() {
        super.onCleared()
        cancelAllHttp()
    }

    /**
     * 销毁网络访问
     */
    open fun cancelAllHttp() {
        OkHttpUtils.getInstance().cancelTag(this)
    }
}