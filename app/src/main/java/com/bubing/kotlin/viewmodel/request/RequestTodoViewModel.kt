package com.bubing.kotlin.viewmodel.request

import androidx.lifecycle.MutableLiveData
import com.bubing.kotlin.data.model.bean.TodoResponse
import com.bubing.kotlin.network.apiService
import com.bubing.kotlin.network.callback.ListDataUiState
import com.bubing.kotlin.network.callback.UpdateUiState
import com.bubing.mvvmk.base.viewmodel.BaseViewModel
import com.bubing.mvvmk.ext.request

/**
 * @ClassName: RequestTodoViewModel
 * @Author: Bubing
 * @Date: 2020/8/11 3:18 PM
 * @Description: java类作用描述
 */
class RequestTodoViewModel : BaseViewModel() {

    var pageNo = 1

    //列表集合数据
    var todoDataState = MutableLiveData<ListDataUiState<TodoResponse>>()

    //删除的回调数据
    var delDataState = MutableLiveData<UpdateUiState<Int>>()

    //完成的回调数据
    var doneDataState = MutableLiveData<UpdateUiState<Int>>()

    //添加修改的回调数据
    var updateDataState = MutableLiveData<UpdateUiState<Int>>()


    fun getTodoData(isRefresh: Boolean) {
        if (isRefresh) {
            pageNo = 1
        }
        request({ apiService.getTodoData(pageNo) }, {
            //请求成功
            pageNo++
            val listDataUiState =
                ListDataUiState(
                    isSuccess = true,
                    isRefresh = isRefresh,
                    isEmpty = it.isEmpty(),
                    hasMore = it.hasMore(),
                    isFirstEmpty = isRefresh && it.isEmpty(),
                    listData = it.datas
                )
            todoDataState.postValue(listDataUiState)
        }, {
            //请求失败
            val listDataUiState =
                ListDataUiState(
                    isSuccess = false,
                    errMessage = it.errorMsg,
                    isRefresh = isRefresh,
                    listData = arrayListOf<TodoResponse>()
                )
            todoDataState.postValue(listDataUiState)
        })
    }

    fun delTodo(id: Int, position: Int) {
        request({ apiService.deleteTodo(id) }, {
            val uistate = UpdateUiState(isSuccess = true, data = position)
            delDataState.postValue(uistate)
        }, {
            val uistate = UpdateUiState(isSuccess = false, data = position, errorMsg = it.errorMsg)
            delDataState.postValue(uistate)
        }, isShowDialog = true)
    }

    fun doneTodo(id: Int, position: Int) {
        request({ apiService.doneTodo(id, 1) }, {
            val uistate = UpdateUiState(isSuccess = true, data = position)
            doneDataState.postValue(uistate)
        }, {
            val uistate = UpdateUiState(isSuccess = false, data = position, errorMsg = it.errorMsg)
            doneDataState.postValue(uistate)
        }, isShowDialog = true)
    }

    fun addTodo(todoTitle: String, todoContent: String, todoTime: String, todoLeve: Int) {
        request({
            apiService.addTodo(todoTitle, todoContent, todoTime, 0, todoLeve)
        }, {
            val uistate = UpdateUiState(isSuccess = true, data = 0)
            updateDataState.postValue(uistate)
        }, {
            val uistate = UpdateUiState(isSuccess = false, data = 0, errorMsg = it.errorMsg)
            updateDataState.postValue(uistate)
        }, isShowDialog = true)
    }

    fun updateTodo(
        id: Int,
        todoTitle: String,
        todoContent: String,
        todoTime: String,
        todoLeve: Int
    ) {
        request({
            apiService.updateTodo(todoTitle, todoContent, todoTime, 0, todoLeve, id)
        }, {
            val uistate = UpdateUiState(isSuccess = true, data = 0)
            updateDataState.postValue(uistate)
        }, {
            val uistate = UpdateUiState<Int>(isSuccess = false, errorMsg = it.errorMsg)
            updateDataState.postValue(uistate)

        }, isShowDialog = true)
    }
}