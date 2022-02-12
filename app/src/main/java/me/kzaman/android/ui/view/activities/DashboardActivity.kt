package me.kzaman.android.ui.view.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import me.kzaman.android.R
import me.kzaman.android.adapter.MenuParentAdapter
import me.kzaman.android.base.BaseActivity
import me.kzaman.android.data.response.UserChildMenuModel
import me.kzaman.android.data.response.UserParentMenuModel
import me.kzaman.android.database.SharedPreferenceManager
import me.kzaman.android.databinding.ActivityDashboardBinding
import me.kzaman.android.network.Resource
import me.kzaman.android.ui.viewModel.CommonViewModel
import me.kzaman.android.utils.Constants.Companion.DELIVERY
import me.kzaman.android.utils.Constants.Companion.ORDER
import me.kzaman.android.utils.Constants.Companion.TRACKING
import com.google.android.material.appbar.AppBarLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import me.kzaman.android.utils.handleActivityApiError
import me.kzaman.android.utils.toastWarning
import me.kzaman.android.utils.visible
import me.kzaman.android.utils.menuRouting
import me.kzaman.android.utils.getMenuIcon
import me.kzaman.android.utils.startNewActivityAnimation
import me.kzaman.android.utils.startAlphaAnimation
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

        binding.rvHomeList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = homeMenuParentAdapter
        }

        val shimmerMenuPlaceholder = binding.shimmerMenuPlaceholder

        getMobileMenu()

        viewModel.mobileMenu.observe(this) {
            if (it is Resource.Loading) {
                shimmerMenuPlaceholder.visible(true)
            } else {
                shimmerMenuPlaceholder.visible(false)
                binding.rvHomeList.visible(true)
            }

            when (it) {
                is Resource.Success -> {
                    val response = it.value
                    if (response.code == 200) {
                        response.data.topParentMenu?.let { topMenu ->
                            homeMenuParentAdapter.setHomePrentMenu(topMenu)
                        }
                        response.data.bottomParentMenu?.let { bottomMenu -> setBottomMenu(bottomMenu) }
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

    override fun setToolbarTitle(title: String) {}

    private fun getMobileMenu() {
        viewModel.getMobileMenu()
    }

    private fun setBottomMenu(menuModels: List<UserParentMenuModel>) {
        val bottomMenu: MutableList<UserChildMenuModel> = ArrayList()
        for (bottom in menuModels) {
            if (bottom.menuName == "BOTTOM") {
                bottomMenu.addAll(bottom.menuItems)
            }
        }

        if (bottomMenu.size == 5) {
            binding.bottomMenuRoot.visible(true)
            val bottomCenterMenu = binding.bottomCenterMenu

            bottomCenterMenu.setOnClickListener {
                menuRouting(this, bottomMenu[2].featureId)
            }
            binding.lnFirst.setOnClickListener {
                menuRouting(this, bottomMenu[0].featureId)
            }

            binding.lnSecond.setOnClickListener {
                menuRouting(this, bottomMenu[1].featureId)
            }
            binding.lnThird.setOnClickListener {
                menuRouting(this, bottomMenu[3].featureId)
            }
            binding.lnLast.setOnClickListener {
                menuRouting(this, bottomMenu[4].featureId)
            }

            val firstMenu = binding.bottomFirstMenu
            val secondMenu = binding.bottomSecondMenu
            val thirdMenu = binding.bottomThirdMenu
            val lastMenu = binding.bottomLastMenu

            val ivFirst = binding.ivBottomFirst
            val ivSecond = binding.ivBottomSecond
            val ivThird = binding.ivBottomThird
            val ivLast = binding.ivBottomLast

            for ((count, bottom) in bottomMenu.withIndex()) {
                when (count) {
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
                                    R.drawable.ic_no_image_hm))
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