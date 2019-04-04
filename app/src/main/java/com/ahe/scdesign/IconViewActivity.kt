package com.ahe.scdesign

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import android.app.LoaderManager
import android.content.AsyncTaskLoader
import android.content.Context
import android.content.Intent
import android.content.Loader
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v7.widget.LinearSnapHelper
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ahe.scdesign.CustomViews.IconView
import kotlinx.android.synthetic.main.activity_icon_view.*

class IconViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_icon_view)
        carousel.setHasFixedSize(true)
        LinearSnapHelper().attachToRecyclerView(carousel)
        carousel.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val center = recyclerView.width / 2
                (0 until recyclerView.childCount).forEach { i ->
                    val child = recyclerView.getChildAt(i) as? IconView ?: return@forEach
                    val cx = (child.left + child.right) / 2
                    val offsetPx = Math.abs(center - cx)
                    child.offset = offsetPx / center.toFloat()
                }
            }
        })

        loaderManager.initLoader(0, Bundle.EMPTY,
            object : LoaderManager.LoaderCallbacks<List<Drawable>> {
                override fun onCreateLoader(id: Int, args: Bundle?) = AppIconLoader(applicationContext)

                override fun onLoadFinished(loader: Loader<List<Drawable>>, icons: List<Drawable>) {
                    loading.visibility = View.GONE
                    carousel.adapter = AppAdapter(icons)
                }

                override fun onLoaderReset(loader: Loader<List<Drawable>>?) {}
            })
    }

    private class AppIconLoader(context: Context) : AsyncTaskLoader<List<Drawable>>(context) {

        private val icons = mutableListOf<Drawable>()

        override fun onStartLoading() {
            if (icons.isNotEmpty()) {
                deliverResult(icons)
            } else {
                forceLoad()
            }
        }

        @RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
        override fun loadInBackground(): List<Drawable>? {
            val pm = context.packageManager
            val launchableAppIcons = mutableListOf<Drawable>()
            val launcherIntent = Intent().apply { addCategory(Intent.CATEGORY_LAUNCHER) }
            pm.getInstalledApplications(0).forEach { appInfo ->
                launcherIntent.`package` = appInfo.packageName
                // only show launch-able apps
                if (pm.queryIntentActivities(launcherIntent, 0).isNotEmpty()) {
                    launchableAppIcons += appInfo.loadUnbadgedIcon(pm)
                }
            }
            return launchableAppIcons
        }

        override fun deliverResult(data: List<Drawable>) {
            icons += data
            super.deliverResult(data)
        }
    }

    class AppAdapter(private val icons: List<Drawable>) : RecyclerView.Adapter<AppViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
            return AppViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_icon_view, parent, false) as IconView)
        }

        override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
            holder.iconView.icon = icons[position]
        }

        override fun getItemCount() = icons.size
    }

    class AppViewHolder(val iconView: IconView) : RecyclerView.ViewHolder(iconView)
}