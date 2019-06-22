package com.cats.catsapplication.features.cats.presentation.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import com.cats.catsapplication.DI.cats.CatsComponentProvider
import com.cats.catsapplication.R
import com.cats.catsapplication.core.mvvm.BaseFragment
import com.cats.catsapplication.core.utils.decoration.GridSpacingItemDecoration
import com.cats.catsapplication.core.utils.getDisplaySize
import com.cats.catsapplication.features.cats.presentation.adapter.CatsAdapter
import com.cats.catsapplication.features.cats.presentation.model.CatModel
import com.cats.catsapplication.features.cats.presentation.viewModel.CatsViewModel
import com.cats.catsapplication.features.cats.presentation.viewModelFactory.CatsViewModelFactory
import com.cats.catsapplication.features.favorites.presentation.activity.FavoritesActivity
import com.tbruyelle.rxpermissions2.RxPermissions
import javax.inject.Inject


class CatsFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener {

    companion object {

        private const val SPAN_COUNT = 2

        fun newInstance() = CatsFragment().apply { arguments = Bundle().apply {} }
    }

    @Inject
    lateinit var factory: CatsViewModelFactory

    private val viewModel: CatsViewModel by lazy {
        ViewModelProviders.of(this, factory).get(CatsViewModel::class.java)
    }

    override fun provideViewModel() = viewModel

    private var catsAdapter = CatsAdapter(this::onFavoriteClick, this::onDownloadClick)

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        CatsComponentProvider.get().inject(this)
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_cats, container, false)

        recyclerView = view.findViewById(R.id.recycler_view)

        recyclerView.layoutManager = GridLayoutManager(context, SPAN_COUNT)
        recyclerView.adapter = catsAdapter

        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)
        swipeRefreshLayout.setOnRefreshListener(this)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().title = getString(R.string.cats_title)

        val spacing = (requireActivity().getDisplaySize().x - SPAN_COUNT * resources.getDimensionPixelSize(R.dimen.image_size)) / 3
        recyclerView.addItemDecoration(GridSpacingItemDecoration(SPAN_COUNT, spacing, true))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.favorite, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.favorite) {
            openFavorite()
            //viewModel.onFavoriteMenuClick()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    override fun bindingUI() {
        viewModel.getCats().observe(this, Observer {
            catsAdapter.updateItems(it ?: emptyList())
        })
        viewModel.getSwipeProgress().observe(this, Observer {
            swipeRefreshLayout.isRefreshing = it ?: false
        })
    }

    override fun getProgressView(): View? {
        return view?.findViewById(R.id.progress_view)
    }

    override fun onRefresh() {
        viewModel.onRefresh()
    }

    private fun openFavorite() {
        startActivity(FavoritesActivity.makeIntent(requireContext()))
    }

    private fun onFavoriteClick(catModel: CatModel) {
        viewModel.onFavoriteClick(catModel)
    }

    private fun onDownloadClick(catModel: CatModel) {
        viewModel.onDownloadClick(catModel, RxPermissions(requireActivity()))
    }
}
