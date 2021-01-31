package com.example.cocktailcatalog.views

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.cocktailcatalog.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_age_check.*


class AgeCheckFragment : Fragment() {
    private lateinit var sharedPref: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE)
        return inflater.inflate(R.layout.fragment_age_check, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button_yes.setOnClickListener {

            sharedPref.edit().putString(getString(R.string.over_18), "true").commit()
            activity?.runOnUiThread {
                activity?.bottomNavigationView!!.visibility = View.VISIBLE
            }
            it.findNavController().navigate(R.id.searchFragment)
        }
        button_no.setOnClickListener {
            activity?.finishAndRemoveTask()
        }

    }


}