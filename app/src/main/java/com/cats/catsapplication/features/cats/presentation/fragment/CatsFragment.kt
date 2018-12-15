package com.cats.catsapplication.features.cats.presentation.fragment

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.*
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.cats.catsapplication.App
import com.cats.catsapplication.R
import com.cats.catsapplication.core.mvp.BaseFragment
import com.cats.catsapplication.features.cats.DI.CatsComponent
import com.cats.catsapplication.features.cats.presentation.adapter.CatsAdapter
import com.cats.catsapplication.features.cats.presentation.model.CatModel
import com.cats.catsapplication.features.cats.presentation.presentor.CatsPresenter
import com.cats.catsapplication.features.cats.presentation.view.CatsView
import com.cats.catsapplication.router.Screens
import javax.inject.Inject


class CatsFragment : BaseFragment(), CatsView, SwipeRefreshLayout.OnRefreshListener {

    companion object {

        private const val SPAN_COUNT = 2

        @JvmStatic
        fun newInstance() = CatsFragment().apply { arguments = Bundle().apply {} }
    }

    @Inject
    @InjectPresenter
    lateinit var presenter: CatsPresenter

    @ProvidePresenter
    fun providePresenter() = presenter

    private var catsAdapter = CatsAdapter(arrayListOf())

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        App.getComponents().get<CatsComponent>(Screens.CATS_SCREEN).inject(this)
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_cat_list, container, false)

        with(view.findViewById<RecyclerView>(R.id.recycler_view)) {
            layoutManager = StaggeredGridLayoutManager(SPAN_COUNT, StaggeredGridLayoutManager.VERTICAL)
            adapter = catsAdapter
        }

        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)
        swipeRefreshLayout.setOnRefreshListener(this)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.title = getString(R.string.cats_title)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.favorite, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.favorite) {
            presenter.onFavoriteClick()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    override fun showCats(cats: List<CatModel>) {
        catsAdapter.updateItems(cats)
    }

    override fun onRefresh() {
        presenter.onRefresh()
    }

    override fun hideSwipeProgress() {
        swipeRefreshLayout.isRefreshing = false
    }

    override fun openFavorite() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
