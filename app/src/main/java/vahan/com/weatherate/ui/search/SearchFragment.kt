package vahan.com.weatherate.ui.search

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import vahan.com.weatherate.R
import vahan.com.weatherate.databinding.FragmentSearchBinding
import vahan.com.weatherate.ui.WeatherViewModel
import vahan.com.weatherate.util.ViewHelper

class SearchFragment : Fragment() {
    private val viewModel: WeatherViewModel by sharedViewModel()
    private lateinit var binding: FragmentSearchBinding

    private lateinit var citiesAdapter: CityNamesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // as viewModel is shared to clear previous search results
        viewModel.clearWeathersList()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            fragment = this@SearchFragment
            viewmodel = this@SearchFragment.viewModel
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_white_24)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.setHomeButtonEnabled(true)
        setHasOptionsMenu(true)

        citiesAdapter = CityNamesAdapter(requireContext(), mutableListOf()) {
            viewModel.updateWeather(it)
            findNavController().navigateUp()
        }
        binding.cityRecyclerView.adapter = citiesAdapter

        //Search api returns current weather only on exact match with query but code designed in such a way that if it return a list of all locations that contains search query, the list will be shown
        viewModel.currentWeathersLiveData.observe(viewLifecycleOwner, Observer {
            citiesAdapter.updateItems(it)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        requireActivity().menuInflater.inflate(R.menu.fragment_search, menu)

        val searchMenuItem = menu.findItem(R.id.menu_search)
        val searchManager = requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = searchMenuItem.actionView as SearchView
        searchView.apply {
            setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
            setIconifiedByDefault(true)
            queryHint = resources.getText(R.string.search)
        }

        searchView.setOnQueryTextListener(
            DebouncingQueryTextListener(
                this@SearchFragment.lifecycle
            ) { newText ->
                newText?.let {
                    viewModel.getCurrentWeather(it.trim())
                }
            }
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        ViewHelper.hideSoftKeyboard(requireActivity())
    }

    //listener for debouncing search queries for not making request on every char typed
    internal class DebouncingQueryTextListener(
        lifecycle: Lifecycle,
        private val onDebouncingQueryTextChange: (String?) -> Unit
    ) : SearchView.OnQueryTextListener {
        var debouncePeriod: Long = 500

        private val coroutineScope = lifecycle.coroutineScope

        private var searchJob: Job? = null

        override fun onQueryTextSubmit(query: String?): Boolean {
            return false
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            searchJob?.cancel()
            searchJob = coroutineScope.launch {
                newText?.let {
                    delay(debouncePeriod)
                    onDebouncingQueryTextChange(newText)
                }
            }
            return false
        }
    }

}
