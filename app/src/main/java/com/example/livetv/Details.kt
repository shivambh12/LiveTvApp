package com.example.livetv

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.livetv.models.channel
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.SimpleExoPlayer
import androidx.constraintlayout.widget.ConstraintLayout

import android.content.pm.ActivityInfo
import android.view.ViewGroup
import android.widget.ProgressBar
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.upstream.DataSource

import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.Player
class Details : AppCompatActivity() {
    private lateinit var description:TextView
    private lateinit var web:ImageView
    private lateinit var fullscreen:ImageView
    private lateinit var progressBar: ProgressBar
    var isfullscreen=false
    private lateinit var facebook:ImageView
    private lateinit var youtube:ImageView
    private lateinit var twitter:ImageView
    private lateinit var playerView: PlayerView
    private lateinit var player:SimpleExoPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        progressBar=findViewById(R.id.progressBar)
        playerView=findViewById(R.id.playerView)
        fullscreen=findViewById(R.id.exo_fullscreen_icon)

        fullscreen.setOnClickListener {
            //Toast.makeText(this,"clicked",Toast.LENGTH_SHORT).show()
            if(isfullscreen)
            {
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE

                if (supportActionBar != null) {
                    supportActionBar!!.show()
                }

                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

                val params = playerView.layoutParams as ConstraintLayout.LayoutParams
                params.width = ViewGroup.LayoutParams.MATCH_PARENT
                params.height = (200 * applicationContext.resources.displayMetrics.density).toInt()
                playerView.layoutParams = params
                isfullscreen=false;
            }
            else
            {
                window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)

                if (supportActionBar != null) {
                    supportActionBar!!.hide()
                }
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

                val params = playerView.layoutParams as ConstraintLayout.LayoutParams
                params.width = ViewGroup.LayoutParams.MATCH_PARENT
                params.height = ViewGroup.LayoutParams.MATCH_PARENT
                playerView.layoutParams = params
                isfullscreen=true;
            }
        }


        web=findViewById(R.id.website_link)
        facebook=findViewById(R.id.facebook_link)
        twitter=findViewById(R.id.twitter_link)
        youtube=findViewById(R.id.youtube_link)
        description=findViewById(R.id.channel_desc)
        val channeldata= intent.getSerializableExtra("channel") as channel
        val actionBar = supportActionBar
        actionBar!!.title =channeldata.name
        actionBar.setDisplayHomeAsUpEnabled(true)
        if(channeldata.description!=null)
        description.text=channeldata.description
        web.setOnClickListener {
            openlink(channeldata.website)
        }
        facebook.setOnClickListener {
            openlink(channeldata.facebook)
        }
        twitter.setOnClickListener {
            openlink(channeldata.twitter)
        }
        youtube.setOnClickListener {
            openlink(channeldata.youtube)
        }
        playChannel(channeldata.live_url)
    }
    public  fun openlink(link:String)
    {
        val open=Intent(Intent.ACTION_VIEW, Uri.parse(link))
        startActivity(open)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==android.R.id.home)
        {
            player.stop()
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
    fun playChannel(live_url:String)
    {
        player = SimpleExoPlayer.Builder(this).build()
        playerView.player=player
        val dataSourceFactory: DataSource.Factory = DefaultHttpDataSourceFactory()
        val mediaSource: HlsMediaSource =
            HlsMediaSource.Factory(dataSourceFactory).createMediaSource(
                MediaItem.fromUri(live_url)
            )
        player.setMediaSource(mediaSource)
        player.prepare()
        player.playWhenReady = true
        player.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(state: Int) {
                if (state == Player.STATE_READY) {
                    progressBar.visibility = View.GONE
                    player.playWhenReady = true
                } else if (state == Player.STATE_BUFFERING) {
                    progressBar.visibility = View.VISIBLE
                    playerView.keepScreenOn = true
                } else {
                    progressBar.visibility = View.GONE
                    player.playWhenReady = true
                }
            }
        })
    }

    override fun onResume() {
        player.seekToDefaultPosition()
        player.playWhenReady = true
        super.onResume()
    }
    override fun onPause() {
        player.playWhenReady=false
        super.onPause()
    }

    override fun onDestroy() {
        player.release()
        super.onDestroy()
    }
}