package me.kzaman.android.ui.view.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.AppBarLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import me.kzaman.android.R
import me.kzaman.android.adapter.MenuParentAdapter
import me.kzaman.android.base.BaseActivity
import me.kzaman.android.data.response.UserChildMenuModel
import me.kzaman.android.data.response.UserParentMenuModel
import me.kzaman.android.database.SharedPreferenceManager
import me.kzaman.android.database.entities.HomeChildMenuEntities
import me.kzaman.android.database.entities.HomeParentMenuEntities
import me.kzaman.android.databinding.ActivityDashboardBinding
import me.kzaman.android.network.Resource
import me.kzaman.android.ui.viewModel.CommonViewModel
import me.kzaman.android.utils.Constants.Companion.DELIVERY
import me.kzaman.android.utils.Constants.Companion.ORDER
import me.kzaman.android.utils.Constants.Companion.TRACKING
import me.kzaman.android.utils.handleActivityApiError
import me.kzaman.android.utils.menuRouting
import me.kzaman.android.utils.getMenuIcon
import me.kzaman.android.utils.startNewActivityAnimation
import me.kzaman.android.utils.startAlphaAnimation
import me.kzaman.android.utils.toastWarning
import me.kzaman.android.utils.visible
import javax.inject.Inject


@AndroidEntryPoint
class DashboardActivity : BaseActivity(), AppBarLayout.OnOffsetChangedListener {
    @Inject
    lateinit var prefManager: SharedPreferenceManager

    private val viewModel by viewModels<CommonViewModel>()
    private lateinit var homeMenuParentAdapter: MenuParentAdapter
    private lateinit var binding: ActivityDashboardBinding

    private var scrollRange = -1
    private var mIsTheTitleVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        init()
    }


    override fun init() {
        binding.appBarLayout.addOnOffsetChangedListener(this)
        homeMenuParentAdapter = MenuParentAdapter(arrayListOf(), this)

        val rvHomeList = binding.rvHomeList
        val shimmerMenuPlaceholder = binding.shimmerMenuPlaceholder

        rvHomeList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = homeMenuParentAdapter
        }

        viewModel.getParentMenuLocalDb()

        viewModel.parentMenuLocal.observe(this) { parentMenuEntities ->
            viewModel.childMenuLocal.observe(this) { childMenuEntities ->
                if (parentMenuEntities.isNotEmpty() && childMenuEntities.isNotEmpty()) {
                    setDashboardLocalMenu(parentMenuEntities, childMenuEntities)
                    shimmerMenuPlaceholder.visibility = View.GONE
                    rvHomeList.visibility = View.VISIBLE
                } else {
                    getMobileMenu()
                }
            }
        }


        viewModel.mobileMenu.observe(this) {
            if (it is Resource.Loading) {
                shimmerMenuPlaceholder.visibility = View.VISIBLE
            } else {
                shimmerMenuPlaceholder.visibility = View.GONE
                rvHomeList.visibility = View.VISIBLE
            }

            when (it) {
                is Resource.Success -> {
                    val response = it.value
                    if (response.code == 200) {
                        val topMenu = response.data.topParentMenu
                        val bottomMenu = response.data.bottomParentMenu
                        homeMenuParentAdapter.setHomePrentMenu(topMenu)
                        setDashboardBottomMenu(bottomMenu)

                        storeMenuToLocalDb(topMenu, bottomMenu)
                    } else {
                        toastWarning("User menu not found!")
                    }
                }
                is Resource.Failure -> handleActivityApiError(it) {
                    Toast.makeText(this, "Menu Can't loaded", Toast.LENGTH_SHORT).show()
                }
                else -> Log.d("error", "Unknown Error")
            }
        }
    }

    private fun setDashboardLocalMenu(
        parentMenuEntities: List<HomeParentMenuEntities>,
        childMenuEntities: List<HomeChildMenuEntities>,
    ) {
        val topMenuModel: ArrayList<UserParentMenuModel> = ArrayList()
        val bottomMenuModel: ArrayList<UserParentMenuModel> = ArrayList()

        parentMenuEntities.forEach { parentMenu ->
            if (parentMenu.parentMenuTitle == "BOTTOM") {
                val parentItem = UserParentMenuModel(
                    menuId = parentMenu.parentMenuId,
                    menuName = parentMenu.parentMenuTitle,
                    menuItems = getChildMenu(parentMenu, childMenuEntities),
                    navType = parentMenu.navType
                )
                bottomMenuModel.add(parentItem)
            } else {
                val parentItem = UserParentMenuModel(
                    menuId = parentMenu.parentMenuId,
                    menuName = parentMenu.parentMenuTitle,
                    menuItems = getChildMenu(parentMenu, childMenuEntities),
                    navType = parentMenu.navType
                )
                topMenuModel.add(parentItem)
            }
        }

        setDashboardBottomMenu(bottomMenuModel)
        homeMenuParentAdapter.setHomePrentMenu(topMenuModel)
    }

    private fun getChildMenu(
        parentMenu: HomeParentMenuEntities,
        childMenuEntities: List<HomeChildMenuEntities>,
    ): ArrayList<UserChildMenuModel> {
        val childMenuModel: ArrayList<UserChildMenuModel> = ArrayList()
        childMenuEntities.forEach { childMenu ->
            if (childMenu.parentMenuId == parentMenu.parentMenuId) {
                val childItem = UserChildMenuModel(
                    menuId = childMenu.menuId,
                    menuName = childMenu.menuTitle,
                    navType = childMenu.menuType,
                    featureId = childMenu.featureId,
                    iconId = childMenu.iconId
                )
                childMenuModel.add(childItem)
            }
        }
        return childMenuModel
    }

    private fun storeMenuToLocalDb(
        topMenu: List<UserParentMenuModel>,
        bottomMenu: List<UserParentMenuModel>,
    ) {
        val parentMenuEntities: ArrayList<HomeParentMenuEntities> = ArrayList()
        val childMenuEntities: ArrayList<HomeChildMenuEntities> = ArrayList()

        val mergeTopAndBottomMenu: ArrayList<UserParentMenuModel> = ArrayList()
        mergeTopAndBottomMenu.addAll(topMenu)
        mergeTopAndBottomMenu.addAll(bottomMenu)

        mergeTopAndBottomMenu.forEach { parentMenu ->
            val homeParentMenuEntities = HomeParentMenuEntities(
                parentMenuId = parentMenu.menuId,
                parentMenuTitle = parentMenu.menuName,
                navType = parentMenu.navType
            )
            parentMenuEntities.add(homeParentMenuEntities)

            parentMenu.menuItems.forEach { childMenu ->
                val childEntities = HomeChildMenuEntities(
                    menuId = childMenu.menuId,
                    menuType = childMenu.navType,
                    parentMenuId = parentMenu.menuId,
                    menuTitle = childMenu.menuName,
                    featureId = childMenu.featureId,
                    iconId = childMenu.iconId
                )
                childMenuEntities.add(childEntities)
            }
        }

        lifecycleScope.launch {
            viewModel.saveHomeParentMenuToLocalDb(parentMenuEntities)
        }
        lifecycleScope.launch {
            viewModel.saveHomeChildMenuToLocalDb(childMenuEntities)
        }
    }

    override fun setToolbarTitle(title: String) {}

    private fun getMobileMenu() {
        viewModel.getMobileMenu()
    }

    private fun setDashboardBottomMenu(menuModels: List<UserParentMenuModel>) {
        val menuItems: ArrayList<UserChildMenuModel> = ArrayList()
        menuModels.forEach {
            if (it.menuName == "BOTTOM") {
                menuItems.addAll(it.menuItems)
            }
        }

        if (menuItems.size == 5) {
            binding.bottomMenuRoot.visible(true)
            val bottomCenterMenu = binding.bottomCenterMenu

            bottomCenterMenu.setOnClickListener {
                menuRouting(this, menuItems[2].featureId)
            }
            binding.lnFirst.setOnClickListener {
                menuRouting(this, menuItems[0].featureId)
            }

            binding.lnSecond.setOnClickListener {
                menuRouting(this, menuItems[1].featureId)
            }
            binding.lnThird.setOnClickListener {
                menuRouting(this, menuItems[3].featureId)
            }
            binding.lnLast.setOnClickListener {
                menuRouting(this, menuItems[4].featureId)
            }

            val firstMenu = binding.bottomFirstMenu
            val secondMenu = binding.bottomSecondMenu
            val thirdMenu = binding.bottomThirdMenu
            val lastMenu = binding.bottomLastMenu

            val ivFirst = binding.ivBottomFirst
            val ivSecond = binding.ivBottomSecond
            val ivThird = binding.ivBottomThird
            val ivLast = binding.ivBottomLast

            menuItems.forEachIndexed { index, bottom ->
                when (index) {
                    0 -> {
                        firstMenu.text = bottom.menuName
                        ivFirst.setImageDrawable(ContextCompat.getDrawable(this,
                            getMenuIcon(bottom.featureId)))
                    }
                    1 -> {
                        secondMenu.text = bottom.menuName
                        ivSecond.setImageDrawable(ContextCompat.getDrawable(this,
                            getMenuIcon(bottom.featureId)))

                    }
                    2 -> {
                        when (bottom.featureId) {
                            ORDER -> {
                                bottomCenterMenu.setImageDrawable(ContextCompat.getDrawable(this,
                                    R.drawable.ic_baseline_shopping_basket_24))
                            }
                            TRACKING -> {
                                bottomCenterMenu.setImageDrawable(ContextCompat.getDrawable(this,
                                    R.drawable.ic_hm_track))
                            }
                            DELIVERY -> {
                                bottomCenterMenu.setImageDrawable(ContextCompat.getDrawable(this,
                                    R.drawable.ic_hm_delivery))
                            }
                            else -> {
                                bottomCenterMenu.setImageDrawable(ContextCompat.getDrawable(this,
                                    R.drawable.ic_baseline_filter_none_24))
                            }
                        }
                    }
                    3 -> {
                        thirdMenu.text = bottom.menuName
                        ivThird.setImageDrawable(ContextCompat.getDrawable(this,
                            getMenuIcon(bottom.featureId)))
                    }
                    4 -> {
                        lastMenu.text = bottom.menuName
                        ivLast.setImageDrawable(ContextCompat.getDrawable(this,
                            getMenuIcon(bottom.featureId)))
                    }
                }
            }
        }
    }


    fun performLogout() = lifecycleScope.launch {
        viewModel.logout()
        prefManager.clearAll()
        startNewActivityAnimation(AuthActivity::class.java)
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack();
        } else {
            super.onBackPressed()
        }
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
        if (scrollRange == -1) {
            scrollRange = appBarLayout.totalScrollRange
        }
        if (scrollRange + verticalOffset == 0) {
            startAlphaAnimation(binding.toolbar, 0, View.VISIBLE)
            binding.clCollapsing.visibility = View.INVISIBLE
            binding.clCollapsing.setBackgroundColor(ContextCompat.getColor(this,
                R.color.colorPrimary))
            mIsTheTitleVisible = true
        } else if (mIsTheTitleVisible) {
            startAlphaAnimation(binding.toolbar, 0, View.INVISIBLE)
            binding.clCollapsing.visibility = View.VISIBLE
            mIsTheTitleVisible = false
            binding.toolbar.setBackgroundColor(ContextCompat.getColor(this,
                R.color.colorPrimary))
            binding.clCollapsing.background =
                ContextCompat.getDrawable(this, R.drawable.bg_home_profile)
        }
    }
}