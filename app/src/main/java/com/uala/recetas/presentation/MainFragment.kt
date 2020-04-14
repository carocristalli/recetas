package com.uala.recetas.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.uala.recetas.R
import com.uala.recetas.domain.Meal
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.recipes_fragment.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit

class MainFragment : Fragment() {

    private val viewModel: RecipesViewModel by viewModel()
    private var adapter = RecipesAdapter()

    private lateinit var disposable: Disposable

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.recipes_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler()
        initViewModel()
    }

    override fun onStart() {
        super.onStart()
        val searchQueryTextObservable =
            createTextChangeObservable().toFlowable(BackpressureStrategy.BUFFER)

        disposable = searchQueryTextObservable
            .map { viewModel.searchMeal(it) }
            .subscribe()
    }

    private fun setupRecycler() {
        list.adapter = adapter
        list.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        list.layoutManager = LinearLayoutManager(activity)
    }

    private fun initViewModel() {
        viewModel.queryLiveData.observe(activity!!, Observer<String> {
            viewModel.loadRecipes()
        })

        viewModel.mealList.observe(activity!!, Observer<List<Meal>> {
            showEmptyList(it?.size == 0)
            adapter.updateData(it)
        })

        viewModel.showError.observe(activity!!, Observer<String> {
            showEmptyList(true)
            Toast.makeText(activity, "\uD83D\uDE28 Wooops $it", Toast.LENGTH_LONG).show()
        })

        viewModel.showLoading.observe(viewLifecycleOwner, Observer { showLoading ->
            mainProgressBar.visibility = if (showLoading!!) View.VISIBLE else View.GONE
        })

    }

    private fun showEmptyList(show: Boolean) {
        if (show) {
            emptyList.visibility = View.VISIBLE
            list.visibility = View.GONE
            mainProgressBar.visibility = View.GONE
        } else {
            emptyList.visibility = View.GONE
            list.visibility = View.VISIBLE
            mainProgressBar.visibility = View.GONE
        }
    }

    private fun createTextChangeObservable(): Observable<String> {
        val textChangeObservable = Observable.create<String> { emitter ->
            val textWatcher = object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    p0?.toString()?.let { emitter.onNext(it) }
                }

                override fun afterTextChanged(p0: Editable?) {
                }
            }

            queryEditText.addTextChangedListener(textWatcher)
            emitter.setCancellable {
                queryEditText.removeTextChangedListener(textWatcher)
            }
        }
        return textChangeObservable.filter { it.length >= 3 }.debounce(1000, TimeUnit.MILLISECONDS)
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}