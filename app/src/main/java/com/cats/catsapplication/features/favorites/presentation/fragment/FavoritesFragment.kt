package com.cats.catsapplication.features.favorites.presentation.fragment

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.cats.catsapplication.DI.favorites.FavoritesComponentProvider
import com.cats.catsapplication.R
import com.cats.catsapplication.core.domain.Cat
import com.cats.catsapplication.core.mvp.BaseFragment
import com.cats.catsapplication.core.utils.decoration.GridSpacingItemDecoration
import com.cats.catsapplication.core.utils.getDisplaySize
import com.cats.catsapplication.core.utils.gone
import com.cats.catsapplication.core.utils.show
import com.cats.catsapplication.features.cats.presentation.fragment.CatsFragment
import com.cats.catsapplication.features.favorites.presentation.adapter.FavoritesAdapter
import com.cats.catsapplication.features.favorites.presentation.presenter.FavoritesPresenter
import com.cats.catsapplication.features.favorites.presentation.view.FavoritesView
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
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        FavoritesComponentProvider.get().inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_favorites, container, false)

        recyclerView = view.findViewById(R.id.recycler_view)

        recyclerView.layoutManager = GridLayoutManager(context, SPAN_COUNT)
        recyclerView.adapter = favoritesAdapter

        emptyTextView = view.findViewById(R.id.empty_text_view)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity!!.title = getString(R.string.favorites_title)

        val spacing = (activity!!.getDisplaySize().x - SPAN_COUNT * resources.getDimensionPixelSize(R.dimen.image_size)) / 3
        recyclerView.addItemDecoration(GridSpacingItemDecoration(SPAN_COUNT, spacing, true))
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