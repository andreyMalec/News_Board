package com.proj.newsboard.newsFragment

import android.content.Intent
import android.net.Uri
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter
import androidx.interpolator.view.animation.FastOutLinearInInterpolator
import androidx.transition.ChangeBounds
import androidx.transition.Fade
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import com.proj.newsboard.R
import com.squareup.picasso.Picasso

object BindingAdapter {
    @BindingAdapter("normalizeDate")
    @JvmStatic
    fun normalizeDate(textView: TextView, date: String?) {
        if (date == null) return

        val splitted = date.dropLast(4).split("T")
        val normalizedTime = splitted[1]
        val normalizedDay = splitted[0].split("-").asReversed().joinToString("-")
        val normalizeDate = normalizedTime + " " + normalizedDay
        textView.text = normalizeDate
    }

    @BindingAdapter("imageUrl")
    @JvmStatic
    fun imageUrl(imageView: ImageView, urlToImage: String?) {
        if (urlToImage.isNullOrBlank()) Picasso.get().load(R.drawable.empty_image).into(imageView)
        else Picasso.get().load(urlToImage).into(imageView)
    }

    @BindingAdapter("underlinedText")
    @JvmStatic
    fun underlinedText(textView: TextView, text: String?) {
        if (text == null) return

        val underlinedText = "<u>" + text + "</u>"
        textView.text = HtmlCompat.fromHtml(underlinedText, HtmlCompat.FROM_HTML_MODE_LEGACY)
    }

    class ClickHandler {
        private var descriptionVisible = false
        fun onLinkClick(view: View) {
            view as TextView
            val i = Intent(Intent.ACTION_VIEW, Uri.parse(view.text.toString()))
            view.context.startActivity(i)
        }

        fun onTitleClick(view: View) {
            val transition = TransitionSet().apply {
                interpolator = FastOutLinearInInterpolator()
                addTransition(Fade())
                addTransition(ChangeBounds())
            }
            if (!descriptionVisible)
                TransitionManager.beginDelayedTransition(view.parent as ViewGroup, transition)
            descriptionVisible = !descriptionVisible
            (view.parent as ViewGroup).getChildAt(1).visibility = if (descriptionVisible) View.VISIBLE else View.GONE
        }
    }
}