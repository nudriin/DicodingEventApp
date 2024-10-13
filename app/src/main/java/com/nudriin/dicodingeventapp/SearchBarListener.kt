package com.nudriin.dicodingeventapp

import com.google.android.material.search.SearchBar

interface SearchBarListener {
    fun showSearchView()
    fun hideSearchView()
    fun getSearchView(): SearchBar
}