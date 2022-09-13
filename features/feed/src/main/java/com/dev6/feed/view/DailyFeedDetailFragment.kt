package com.dev6.feed.view

import android.graphics.Color
import android.os.Build
import android.text.InputType
import android.util.Log
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dev6.core.UserData
import com.dev6.core.base.BindingFragment
import com.dev6.core.base.UiState
import com.dev6.core.util.extension.fewDay
import com.dev6.core.util.extension.repeatOnStarted
import com.dev6.domain.model.daily.DailyPost
import com.dev6.feed.R
import com.dev6.feed.adapter.comment.DailyCommentAdapter
import com.dev6.feed.databinding.FragmentDailyFeedDetailBinding
import com.dev6.feed.viewmodel.FeedViewModel
import kotlinx.coroutines.flow.collect

class DailyFeedDetailFragment :
BindingFragment<FragmentDailyFeedDetailBinding>(R.layout.fragment_daily_feed_detail){

    private val feedViewModel: FeedViewModel by activityViewModels()
    lateinit var currentFeed: DailyPost
    private lateinit var dailycommentRc: RecyclerView
    private lateinit var commentAdapter: DailyCommentAdapter
    var likedBoolean = false

    override fun initView() {
        super.initView()

        currentFeed = arguments?.get("dailyPost") as DailyPost
        makeCurrentView()
        commentAdapter = DailyCommentAdapter {
            // 아래 if 문 조건에 해당 댓글을 작성한 userType 먼지 알아와서 분기처리 해주는 코드가 필요할듯 하다
            if(com.dev6.core.UserData.userType == "NORMAL"){
                findNavController().navigate(R.id.action_fragmentFeedDaily_to_userFragment)
            }else{
                findNavController().navigate(R.id.action_fragmentFeedDaily_to_userProfileFragment)
            }
        }
        dailycommentRc = binding.dailyCommentRv
        dailycommentRc.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = commentAdapter
        }
    }

    override fun initViewModel() {
        super.initViewModel()
        feedViewModel.getCommentListByPostId(currentFeed.normalPostId)
    }

    override fun initListener() {
        super.initListener()
        binding.dailyLikeImage.setOnClickListener {
            feedViewModel.postLike(currentFeed.normalPostId, UserData.userId)
        }
        binding.dailyFeedCommentEt.apply {
            imeOptions = EditorInfo.IME_ACTION_SEND
            setRawInputType(InputType.TYPE_CLASS_TEXT)
        }
        binding.dailyFeedCommentEt.setOnKeyListener { view, keyCode, keyEvent ->
            if((keyEvent.action == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)){
                true
            }else{
                false
            }
        }
        binding.dailyFeedCommentTv.setOnClickListener {
            feedViewModel.postCommentListByPostId( binding.dailyFeedCommentEt.text.toString(),currentFeed.normalPostId,UserData.userId)
        }
    }

    override fun afterViewCreated() {
        super.afterViewCreated()
        repeatOnStarted {
            feedViewModel.eventFlowComment.collect { event ->
                when (event) {
                    is FeedViewModel.Event.CommentUiEvnet -> {
                        when (event.uiState) {
                            is UiState.Success -> {
                                event.uiState.data.collect {
                                    commentAdapter.submitData(it)
                                }
                            }
                            is UiState.Error -> {
                                Toast.makeText(
                                    activity,
                                    "실패 했어여",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            is UiState.Loding -> {
                                Toast.makeText(
                                    activity,
                                    "로딩 했어여",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }else->{

                }
                }
            }
        }
        
        repeatOnStarted { 
            feedViewModel.eventFlowCommentPost.collect{event->
                when(event){
                    is FeedViewModel.Event.CommentPostUiEvnet->{
                        when (event.uiState) {
                            is UiState.Success -> {
                                Toast.makeText(
                                    activity,
                                    "댓글성공",
                                    Toast.LENGTH_SHORT
                                ).show()
                                //댓글입력 성공하면 내가 적은 댓글이 반영되서 보여야하기 때문에 새로 로딩
                                feedViewModel.getCommentListByPostId(currentFeed.normalPostId)
                            }
                            is UiState.Error -> {}
                            is UiState.Loding -> {}
                        }
                    }else -> {
                        
                    }
                }
            }
        }


        repeatOnStarted {
            feedViewModel.eventPostLike.collect { event ->
                when (event) {
                    is FeedViewModel.Event.CommentLikeUiEvnet -> {
                        when (event.uiState) {
                            is UiState.Success -> {
                                Toast.makeText(
                                    activity,
                                    "좋아요 성공",
                                    Toast.LENGTH_SHORT
                                ).show()
                                changeLikedHeart()
                            }
                            is UiState.Error -> {
                                Toast.makeText(
                                    activity,
                                    "실패 했어여",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            is UiState.Loding -> {
                                Toast.makeText(
                                    activity,
                                    "로딩 했어여",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }else->{

                }
                }
            }
        }
    }

    private fun makeCurrentView() {
        binding.apply {
            currentFeed.apply {
                dailyFeedTitleTv.text = title
                dailyFeedContentTv.text = contents
                dailyLikeCount.text = likedCount.toString()
                dailyCommentCount.text = commentCount.toString()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    dailyFeedTimeTv.text =
                        fewDay(
                            createdDate[0],
                            createdDate[1],
                            createdDate[2],
                            createdDate[3],
                            createdDate[4],
                            createdDate[5]
                        )
                }
                Log.v("initlike", likedBoolean.toString())
                likedBoolean = liked
                makeLikedHeart(liked)
                makeImageView("")
            }
        }
    }

    private fun makeImageView(uri: String) {
        Glide.with(this).load(uri).error(R.drawable.alarm).into(binding.dailyContentImage)
    }

    private fun makeLikedHeart(boolean: Boolean) {
        if (boolean) {
            binding.dailyLikeImage.setColorFilter(Color.parseColor("#FF675E"))
        } else {
            binding.dailyLikeImage.setColorFilter(Color.parseColor("#C4C4C4"))
        }

    }

    private fun changeLikedHeart() {
        if (likedBoolean) {
            Log.v("initlike2", likedBoolean.toString())
            binding.dailyLikeImage.setColorFilter(Color.parseColor("#C4C4C4"))
            binding.dailyLikeCount.text = (binding.dailyLikeCount.text.toString().toInt() + 1).toString()
            likedBoolean = false
        } else {
            Log.v("initlike3", likedBoolean.toString())
            binding.dailyLikeImage.setColorFilter(Color.parseColor("#FF675E"))
            binding.dailyLikeCount.text = (binding.dailyLikeCount.text.toString().toInt() - 1).toString()
            likedBoolean = true
        }
    }
}