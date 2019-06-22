package com.cats.catsapplication.features.favorites.presentation.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.cats.catsapplication.DI.favorites.FavoritesComponentProvider
import com.cats.catsapplication.R
import com.cats.catsapplication.core.domain.Cat
import com.cats.catsapplication.core.mvvm.BaseFragment
import com.cats.catsapplication.core.utils.decoration.GridSpacingItemDecoration
import com.cats.catsapplication.core.utils.getDisplaySize
import com.cats.catsapplication.core.utils.gone
import com.cats.catsapplication.core.utils.show
import com.cats.catsapplication.features.favorites.presentation.adapter.FavoritesAdapter
import com.cats.catsapplication.features.favorites.presentation.viewModel.FavoritesViewModel
import com.cats.catsapplication.features.favorites.presentation.viewModelFactory.FavoritesViewModelFactory
import com.tbruyelle.rxpermissions2.RxPermissions
import javax.inject.Inject

class FavoritesFragment : BaseFragment() {

    companion object {

        private const val SPAN_COUNT = 2

        fun newInstance() = FavoritesFragment().apply { arguments = Bundle().apply {} }
    }

    @Inject
    lateinit var factory: FavoritesViewModelFactory

    private val viewModel: FavoritesViewModel by lazy {
        ViewModelProviders.of(this, factory).get(FavoritesViewModel::class.java)
    }

    override fun provideViewModel() = viewModel

    private var favoritesAdapter = FavoritesAdapter(this::onDeleteClick, this::onDownloadClick)
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
        requireActivity().title = getString(R.string.favorites_title)

        val spacing = (requireActivity().getDisplaySize().x - SPAN_COUNT * resources.getDimensionPixelSize(R.dimen.image_size)) / 3
        recyclerView.addItemDecoration(GridSpacingItemDecoration(SPAN_COUNT, spacing, true))
    }

    override fun bindingUI() {
        viewModel.getCats().observe(this, Observer {cats -> cats?.let { dispatchCats(it) } })
    }

    override fun getProgressView(): View? {
        return view?.findViewById(R.id.progress_view)
    }

    private fun dispatchCats(cats: List<Cat>) {
        favoritesAdapter.updateItems(cats)

        if (cats.isEmpty()) {
            emptyTextView.show()
        } else {
            emptyTextView.gone()
        }
    }

    private fun onDeleteClick(cat: Cat) {
        viewModel.onDeleteClick(cat)
    }

    private fun onDownloadClick(cat: Cat) {
        viewModel.onDownloadClick(cat, RxPermissions(requireActivity()))
    }
}