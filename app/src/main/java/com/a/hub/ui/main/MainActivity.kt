package com.a.hub.ui.main

import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.core.content.res.ResourcesCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.a.hub.R
import com.a.hub.app.ThemeHelper
import com.a.hub.core.SimpleMenuItem
import com.a.hub.data.model.Recent
import com.a.hub.databinding.ActivityMainBinding
import com.a.hub.helper.*
import com.a.hub.ui.adapter.MenuAdapter
import com.a.hub.ui.adapter.RecentAdapter
import com.a.hub.ui.auth.AuthActivity
import com.a.hub.ui.base.BaseDrawerActivity
import com.a.hub.ui.main.fragment.MainFragment
import com.a.hub.ui.main.fragment.RecentFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview


@AndroidEntryPoint
class MainActivity : BaseDrawerActivity() {

    private val viewModel: MainViewModel by viewModels()
    private val handler: Handler = Handler(Looper.getMainLooper())
    private lateinit var actionSearch: View

    private lateinit var binding: ActivityMainBinding
    private lateinit var searchView: SearchView

    private var searchItem: MenuItem? = null

    val recentAdapter = RecentAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initToolbar(binding.content.actionbar.toolbar, getString(R.string.app_name))
        drawer = binding.drawer.drawer
        content = binding.content.content
        initDrawer(binding.drawerLayout)

        checkTheme(
            binding.drawer.themeSwitch,
            binding.root,
            ResourcesCompat.getColor(resources, R.color.primaryVariant, null)
        )

        fullTransparent()

        actionSearch = binding.content.search.actionHolder

        binding.drawer.actionAuth.setOnClickListener {
            startActivity(Intent(this, AuthActivity::class.java))
        }
        binding.drawer.themeSwitch.setOnCheckedChangeListener { _, isChecked ->
            switchTheme(isChecked)
        }
        binding.content.search.recentEdit.setOnClickListener {
            startDialogFragment(supportFragmentManager, RecentFragment())
        }
        binding.content.search.recentList.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = recentAdapter
        }
        recentAdapter.onItemClick = { item: Any, v: View ->
            searchItem?.expandActionView()
            searchView.requestFocus()
            searchView.setQuery((item as Recent).text, true)
        }

        replaceFragment(binding.content.fragmentHolder.id, MainFragment(), "MainFragment")

        val menuAdapter = MenuAdapter(R.layout.item_menu_main).apply { swapData(MenuHelper.getDrawerMenuItems(resources)) }
        menuAdapter.onItemClick = { item: Any, _: View ->
            if(item is SimpleMenuItem)
                Inform.show(applicationContext, "TODO: " + item.name)
        }
        binding.drawer.menuList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = menuAdapter
        }


        binding.content.search.actionSortTimeHolder.setOnClickListener {
            viewModel.onStateChanged(StateChanged.SortChanged(SortState.SORT_UPDATE))
        }
        binding.content.search.actionSortStarsHolder.setOnClickListener {
            viewModel.onStateChanged(StateChanged.SortChanged(SortState.SORT_STARS))
        }
        binding.content.search.actionSortForksHolder.setOnClickListener {
            viewModel.onStateChanged(StateChanged.SortChanged(SortState.SORT_FORKS))
        }
        binding.content.search.actionSortAsc.setOnClickListener {
            viewModel.onStateChanged(StateChanged.OrderChanged(SortState.ORDER_ASC))
        }
        binding.content.search.actionSortDesc.setOnClickListener {
            viewModel.onStateChanged(StateChanged.OrderChanged(SortState.ORDER_DESC))
        }


        subscribe()

    }

    @FlowPreview
    private fun subscribe() {
        viewModel.message.observe(this, {
            Inform.show(applicationContext, "I: $it")
        })

        viewModel.user.observe(this, {
            if(it != null) {
                binding.drawer.userName.visible()
                binding.drawer.userName.setTextOrHide(it.login)
                ImageLoader.loadImageInCircle(this, it.avatarUrl, binding.drawer.userImage)
                binding.drawer.userImage.setColorFilter(0)
            }else{
                binding.drawer.userName.gone()
                binding.drawer.userImage.setImageResource(R.drawable.outline_person_outline_24)
                binding.drawer.userImage.setColorFilter(ResourcesCompat.getColor(resources, R.color.tint, null))
            }
        })

        viewModel.userState.observe(this, {
            binding.drawer.actionAuth.text = getString(if(it) R.string.sign_out else R.string.sign_in)
        })

        viewModel.recent.observe(this, {
            if(it != null && it.isNotEmpty()){
                recentAdapter.swapData(it)
                binding.content.search.recentHolder.visible()
            }else{
                recentAdapter.swapData(listOf())
                binding.content.search.recentHolder.gone()
            }

        })

        viewModel.search.observe(this, {
            if(it) {
                searchItem?.expandActionView()
                viewModel.switchSearch(false)
            }
        })


        viewModel.sortState.observe(this, {
            var color: Int
            val activeColor = ResourcesCompat.getColor(resources, R.color.blue, null)
            val textColor = ResourcesCompat.getColor(resources, R.color.onPrimary, null)

            color = if(it.order == SortState.ORDER_ASC) activeColor else textColor
            binding.content.search.actionSortAsc.setColorFilter(color, PorterDuff.Mode.SRC_IN)
            color = if(it.order == SortState.ORDER_DESC) activeColor else textColor
            binding.content.search.actionSortDesc.setColorFilter(color, PorterDuff.Mode.SRC_IN)

            binding.content.search.actionSortTime.visibility =
                if(it.sort == SortState.SORT_UPDATE) View.VISIBLE else View.GONE

            color = if (it.sort == SortState.SORT_UPDATE) activeColor else textColor
            binding.content.search.actionSortTime.setTextColor(color)
            binding.content.search.actionSortTimeIcon.setColorFilter(color, PorterDuff.Mode.SRC_IN)

            binding.content.search.actionSortStars.visibility =
                if(it.sort == SortState.SORT_STARS) View.VISIBLE else View.GONE

            color = if (it.sort == SortState.SORT_STARS) activeColor else textColor
            binding.content.search.actionSortStars.setTextColor(color)
            binding.content.search.actionSortStarsIcon.setColorFilter(color, PorterDuff.Mode.SRC_IN)

            binding.content.search.actionSortForks.visibility =
                if(it.sort == SortState.SORT_FORKS) View.VISIBLE else View.GONE

            color = if (it.sort == SortState.SORT_FORKS) activeColor else textColor
            binding.content.search.actionSortForks.setTextColor(color)
            binding.content.search.actionSortForksIcon.setColorFilter(color, PorterDuff.Mode.SRC_IN)
        })

        //TODO replace flowCallback (Preview) / ping for real connection state (coroutines)
        viewModel.network?.observe(this, {
            if(!it) Inform.show(applicationContext, getString(R.string.no_internet_connection))
        })

    }

    private fun switchTheme(isChecked: Boolean){
        drawerLayout?.closeDrawers()
        Handler(Looper.getMainLooper()).postDelayed({
            ThemeHelper.switchTheme(
                applicationContext,
                isChecked
            )
        }, 500)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        searchItem = menu?.findItem(R.id.action_search)
        searchView = searchItem?.actionView as SearchView
        searchView.maxWidth = Int.MAX_VALUE

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                querySearch(query, false)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                handler.removeCallbacksAndMessages(null)
                handler.postDelayed({
                    querySearch(newText, true)
                }, 2500)
                return true
            }

        })

        searchItem?.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                actionSearch.gone()
                viewModel.switchMode(false)
                return true
            }

            override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                actionSearch.visible()
                viewModel.switchMode(true)
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    private fun querySearch(query: String?, timer: Boolean) {
        handler.removeCallbacksAndMessages(null)
        if(query.isNullOrEmpty()) return
        if(query.length <= 2){
            if(!timer) Inform.show(applicationContext, "Min length is 2 chars")
            return
        }
        viewModel.onStateChanged(StateChanged.QueryChanged(query))
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                onSearchRequested()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacksAndMessages(null)
    }


    override fun onMenuOpened(){
        searchItem?.collapseActionView()
    }


}