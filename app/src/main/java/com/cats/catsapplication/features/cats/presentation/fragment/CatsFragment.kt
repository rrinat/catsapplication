package com.cats.catsapplication.features.cats.presentation.fragment

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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


class CatsFragment : BaseFragment(), CatsView {

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

    override fun onCreate(savedInstanceState: Bundle?) {
        App.getComponents().get<CatsComponent>(Screens.CATS_SCREEN).inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_cat_list, container, false)

        with(view.findViewById<RecyclerView>(R.id.recycler_view)) {
            layoutManager = StaggeredGridLayoutManager(SPAN_COUNT, StaggeredGridLayoutManager.VERTICAL)
            adapter = catsAdapter
        }

        return view
    }

    override fun showCats(cats: List<CatModel>) {
        catsAdapter.updateItems(cats)
    }
}
