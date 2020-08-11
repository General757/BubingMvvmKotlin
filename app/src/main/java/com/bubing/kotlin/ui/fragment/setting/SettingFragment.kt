package com.bubing.kotlin.ui.fragment.setting
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.WhichButton
import com.afollestad.materialdialogs.actions.getActionButton
import com.afollestad.materialdialogs.color.colorChooser
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.blankj.utilcode.util.AppUtils
import com.tencent.bugly.beta.Beta
import com.bubing.kotlin.R
import com.bubing.kotlin.app.event.AppViewModel
import com.bubing.kotlin.app.ext.initClose
import com.bubing.kotlin.app.ext.showMessage
import com.bubing.kotlin.network.NetworkApi
import com.bubing.kotlin.util.CacheDataManager
import com.bubing.kotlin.util.CacheUtil
import com.bubing.kotlin.util.ColorUtil
import com.bubing.kotlin.util.SettingUtil
import com.bubing.kotlin.weight.preference.CheckBoxPreference
import com.bubing.kotlin.weight.preference.IconPreference
import com.bubing.kotlin.weight.preference.PreferenceCategory
import com.bubing.kotlin.data.model.bean.BannerResponse
import com.bubing.mvvmk.ext.getAppViewModel
import com.bubing.mvvmk.ext.nav
import com.bubing.mvvmk.ext.navigateAction
/**
 * @ClassName: SettingFragment
 * @Author: Bubing
 * @Date: 2020/8/11 3:07 PM
 * @Description: 系统设置
 */
class SettingFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {

    //这里不能继承BaseFragment了，所以手动获取一下 AppViewModel
    val shareViewModel: AppViewModel by lazy { getAppViewModel<AppViewModel>() }

    private var colorPreview: IconPreference? = null

    var toolbarView: View? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //这里重写根据PreferenceFragmentCompat 的布局 ，往他的根布局插入了一个toolbar
        val containerView = view.findViewById<FrameLayout>(android.R.id.list_container)
        containerView.let {
            //转为线性布局
            val linearLayout = it.parent as? LinearLayout
            linearLayout?.run {
                toolbarView =  LayoutInflater.from(activity).inflate(R.layout.include_toolbar, null)
                toolbarView?.let {view ->
                    view.findViewById<Toolbar>(R.id.toolbar)?.initClose("设置") {
                        nav().navigateUp()
                    }
                    //添加到第一个
                    addView(toolbarView, 0)
                }
            }
        }
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.root_preferences)

        colorPreview = findPreference("color")
        setText()
        findPreference<Preference>("exit")?.isVisible = CacheUtil.isLogin()//未登录时，退出登录需要隐藏

        findPreference<Preference>("exit")?.setOnPreferenceClickListener { preference ->
            showMessage(
                "确定退出登录吗",
                positiveButtonText = "退出",
                negativeButtonText = "取消",
                positiveAction = {
                    //清空cookie
                    NetworkApi.INSTANCE.cookieJar.clear()
                    CacheUtil.setUser(null)
                    shareViewModel.userinfo.postValue(null)
                    nav().navigateUp()
                })
            false
        }

        findPreference<Preference>("clearCache")?.setOnPreferenceClickListener {
            showMessage(
                "确定清理缓存吗",
                positiveButtonText = "清理",
                negativeButtonText = "取消",
                positiveAction = {
                    activity?.let { CacheDataManager.clearAllCache(it as? AppCompatActivity) }
                    setText()
                })
            false
        }
        findPreference<Preference>("mode")?.setOnPreferenceClickListener {
            activity?.let { activity ->
                MaterialDialog(activity).show {
                    cancelable(false)
                    lifecycleOwner(viewLifecycleOwner)
                    listItemsSingleChoice(
                        R.array.setting_modes,
                        initialSelection = SettingUtil.getListMode()
                    ) { dialog, index, text ->
                        SettingUtil.setListMode(index)
                        it.summary = text
                        //通知其他界面立马修改配置
                        shareViewModel.appAnimation.postValue(index)
                    }
                    title(text = "设置列表动画")
                    positiveButton(R.string.confirm)
                    negativeButton(R.string.cancel)
                    getActionButton(WhichButton.POSITIVE).updateTextColor(
                        SettingUtil.getColor(
                            activity
                        )
                    )
                    getActionButton(WhichButton.NEGATIVE).updateTextColor(
                        SettingUtil.getColor(
                            activity
                        )
                    )
                }
            }

            false
        }
        findPreference<IconPreference>("color")?.setOnPreferenceClickListener {
            activity?.let { activity ->
                MaterialDialog(activity).show {
                    title(R.string.choose_theme_color)
                    colorChooser(
                        ColorUtil.ACCENT_COLORS,
                        initialSelection = SettingUtil.getColor(activity),
                        subColors = ColorUtil.PRIMARY_COLORS_SUB
                    ) { dialog, color ->
                        ///修改颜色
                        SettingUtil.setColor(activity, color)
                        findPreference<PreferenceCategory>("base")?.setTitleColor(color)
                        findPreference<PreferenceCategory>("other")?.setTitleColor(color)
                        findPreference<PreferenceCategory>("about")?.setTitleColor(color)
                        findPreference<CheckBoxPreference>("top")?.setBottonColor()
                        toolbarView?.setBackgroundColor(color)
                        //通知其他界面立马修改配置
                        shareViewModel.appColor.postValue(color)
                    }
                    getActionButton(WhichButton.POSITIVE).updateTextColor(
                        SettingUtil.getColor(
                            activity
                        )
                    )
                    getActionButton(WhichButton.NEGATIVE).updateTextColor(
                        SettingUtil.getColor(
                            activity
                        )
                    )
                    positiveButton(R.string.done)
                    negativeButton(R.string.cancel)
                }
            }
            false
        }

        findPreference<Preference>("version")?.setOnPreferenceClickListener {
            Beta.checkUpgrade(true, false)
            false
        }
        findPreference<Preference>("copyRight")?.setOnPreferenceClickListener {
            activity?.let {
                showMessage(it.getString(R.string.copyright_tip))
            }
            false
        }
        findPreference<Preference>("author")?.setOnPreferenceClickListener {
            showMessage(
                title = "联系作者",
                message = "扣　扣：824868922\n\n微　信：hgj840\n\n邮　箱：824868922@qq.com"
            )
            false
        }
        findPreference<Preference>("project")?.setOnPreferenceClickListener {
            val data = BannerResponse(
                title = "一位练习时长两年半的菜虚鲲制作的玩安卓App",
                url = findPreference<Preference>("project")?.summary.toString()
            )
            view?.let {
                nav().navigateAction(R.id.action_to_webFragment, Bundle()
                    .apply { putParcelable("bannerdata", data) })
            }
            false
        }
    }

    /**
     * 初始化设值
     */
    private fun setText() {
        activity?.let {

            findPreference<CheckBoxPreference>("top")?.isChecked = CacheUtil.isNeedTop()

            findPreference<Preference>("clearCache")?.summary =
                CacheDataManager.getTotalCacheSize(it)

            findPreference<Preference>("version")?.summary = "当前版本 " + AppUtils.getAppVersionName()

            val modes = it.resources.getStringArray(R.array.setting_modes)
            findPreference<Preference>("mode")?.summary =
                modes[SettingUtil.getListMode()]
        }
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        if (key == "color") {
            colorPreview?.setView()
        }
        if (key == "top") {
            CacheUtil.setIsNeedTop(sharedPreferences.getBoolean("top", true))
        }
    }
}