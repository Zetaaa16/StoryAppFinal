package com.fadhil.storyappfinal.view.main

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fadhil.storyappfinal.R
import com.fadhil.storyappfinal.data.GetStoryResult
import com.fadhil.storyappfinal.databinding.FragmentStoryBinding
import com.fadhil.storyappfinal.storage.pref.PreferenceDataSource
import com.fadhil.storyappfinal.storage.pref.UserModel
import com.fadhil.storyappfinal.utils.ConstVal.EXTRA_DATE
import com.fadhil.storyappfinal.utils.ConstVal.EXTRA_DESC
import com.fadhil.storyappfinal.utils.ConstVal.EXTRA_IMAGE
import com.fadhil.storyappfinal.utils.ConstVal.EXTRA_KEY
import com.fadhil.storyappfinal.utils.ConstVal.EXTRA_NAME
import com.fadhil.storyappfinal.utils.ViewModelFactory
import com.fadhil.storyappfinal.view.detail.DetailActivity
import com.fadhil.storyappfinal.view.upload.UploadActivity

class StoryFragment : Fragment() {
    private lateinit var binding : FragmentStoryBinding
    private lateinit var rvStory: RecyclerView
    private lateinit var userModel: UserModel
    private lateinit var userPreferences: PreferenceDataSource

    private lateinit var adapter: MainAdapter

    private lateinit var factory: ViewModelFactory
    private val storyViewModel: MainViewModel by viewModels {
        factory
    }

    private var token : String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStoryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        factory = ViewModelFactory.getInstance(requireActivity())

        userPreferences = PreferenceDataSource(requireActivity())
        userModel = userPreferences.getUser()

        rvStory = binding.rvList
        rvStory.setHasFixedSize(true)

        token = userModel.token.toString()

        getStory()

        binding.btnAddStory.setOnClickListener {
            val intent = Intent(activity, UploadActivity::class.java)
            intent.putExtra(EXTRA_KEY,token)
            startActivity(intent)
        }
    }

    private fun getStory() {
        val authToken = "Bearer $token"

        adapter = MainAdapter()
        rvStory.adapter = adapter

        storyViewModel.getStory(authToken).observe(viewLifecycleOwner) {
            adapter.submitData(lifecycle, it)
        }

        rvStory.layoutManager = LinearLayoutManager(context)
        adapter.setOnItemClickCallback(object : MainAdapter.OnItemClickCallback {
            override fun onItemClicked(story: GetStoryResult) {
                showSelectedStory(story)
            }
        })
    }

    private fun showSelectedStory(story: GetStoryResult) {
        Intent(activity, DetailActivity::class.java).also {
            it.putExtra(EXTRA_NAME, story.name)
            it.putExtra(EXTRA_DESC, story.description)
            it.putExtra(EXTRA_DATE, story.createdAt)
            it.putExtra(EXTRA_IMAGE, story.photoUrl)
            startActivity(it)
        }
    }
}