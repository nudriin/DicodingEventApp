package com.nudriin.dicodingeventapp

import com.google.android.material.search.SearchBar
import com.google.android.material.search.SearchView


interface SearchBarListener {
    fun showSearchView()
    fun hideSearchView()
    fun getSearchView(): SearchView
    fun getSearchBar(): SearchBar
}