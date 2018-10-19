package com.example.rahma.myfootballapi.view.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView

import com.example.rahma.myfootballapi.R
import com.example.rahma.myfootballapi.presenter.TeamsPresenter
import com.example.rahma.myfootballapi.api.Api
import com.example.rahma.myfootballapi.getIdTeam
import com.example.rahma.myfootballapi.invisible
import com.example.rahma.myfootballapi.model.Team
import com.example.rahma.myfootballapi.view.`interface`.TeamsView
import com.example.rahma.myfootballapi.visible
import com.google.gson.Gson


class TeamDescription : Fragment(), TeamsView {
    private lateinit var presenter: TeamsPresenter
    private lateinit var progressBar: ProgressBar
    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showTeamList(data: List<Team>) {
        val descTeam = view?.findViewById(R.id.description) as TextView
        descTeam.text = data[0].strTeamDescription
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        progressBar = view?.findViewById(R.id.progresBar) as ProgressBar
        val request = Api()
        val gson = Gson()
        presenter = TeamsPresenter(this, request, gson)
        presenter.getTeamDetail(arguments?.getString(getIdTeam()))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_team_description, container, false)
    }


    companion object {
        fun newInstance(idTeam: String?): TeamDescription {
            val args = Bundle()
            args.putString(getIdTeam(), idTeam)
            val fragment = TeamDescription()
            fragment.arguments = args
            return fragment
        }
    }
}
