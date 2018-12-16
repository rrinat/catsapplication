package com.cats.catsapplication.features.favorites.presentation.fragment

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.cats.catsapplication.App
import com.cats.catsapplication.DI.favorites.FavoritesComponent
import com.cats.catsapplication.R
import com.cats.catsapplication.core.domain.Cat
import com.cats.catsapplication.core.mvp.BaseFragment
import com.cats.catsapplication.core.utils.gone
import com.cats.catsapplication.core.utils.show
import com.cats.catsapplication.features.favorites.presentation.adapter.FavoritesAdapter
import com.cats.catsapplication.features.favorites.presentation.presenter.FavoritesPresenter
import com.cats.catsapplication.features.favorites.presentation.view.FavoritesView
import com.cats.catsapplication.router.Screens
import com.tbruyelle.rxpermissions2.RxPermissions
import javax.inject.Inject

class FavoritesFragment : BaseFragment(), FavoritesView {

    companion object {

        private const val SPAN_COUNT = 2

        @JvmStatic
        fun newInstance() = FavoritesFragment().apply { arguments = Bundle().apply {} }
    }

    @Inject
    @InjectPresenter
    lateinit var presenter: FavoritesPresenter

    @ProvidePresenter
    fun providePresenter() = presenter

    private var favoritesAdapter = FavoritesAdapter(emptyList(), this::onDeleteClick, this::onDownloadClick)
    private lateinit var emptyTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        App.getComponents().get<FavoritesComponent>(Screens.FAVORITES_SCREEN).inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_favorites, container, false)

        with(view.findViewById<RecyclerView>(R.id.recycler_view)) {
            layoutManager = StaggeredGridLayoutManager(SPAN_COUNT, StaggeredGridLayoutManager.VERTICAL)
            adapter = favoritesAdapter
        }

        emptyTextView = view.findViewById(R.id.empty_text_view)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.title = getString(R.string.favorites_title)
    }

    override fun getProgressView(): View? {
        return view?.findViewById(R.id.progress_view)
    }

    override fun showFavorites(cats: List<Cat>) {
        favoritesAdapter.updateItems(cats)
    }

    override fun showEmptyView() {
        emptyTextView.show()
    }

    override fun hideEmptyView() {
        emptyTextView.gone()
    }

    private fun onDeleteClick(cat: Cat) {
        presenter.onDeleteClick(cat)
    }

    private fun onDownloadClick(cat: Cat) {
        presenter.onDownloadClick(cat, RxPermissions(activity!!))
    }
}