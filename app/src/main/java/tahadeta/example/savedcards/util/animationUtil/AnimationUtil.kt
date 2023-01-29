package tahadeta.example.savedcards.util.animationUtil

import android.util.Log
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.TextView

object AnimationUtil {

    // slide the view from below itself to the current position
    fun slideUp(view: View) {
        view.visibility = View.VISIBLE
        val animate = TranslateAnimation(
            0F,
            0F,
            0F, // fromYDelta
            -10F // toYDelta
        ) // toYDelta
        animate.duration = 500
        animate.fillAfter = true
        view.startAnimation(animate)
        view.visibility = View.GONE
    }

    // slide the view from its current position to below itself
    fun slideDown(view: View) {
        val animate = TranslateAnimation(
            0F,
            0F,
            -20F, // fromYDelta
            4F // toYDelta
        ) // toYDelta
        animate.duration = 500
        animate.fillAfter = true
        view.startAnimation(animate)
    }

    fun changeTextContent(textView: TextView, textMsg: String){
        val anim = AlphaAnimation(1.0f, 0.0f)
        anim.duration = 200
        anim.repeatCount = 1
        anim.repeatMode = Animation.REVERSE

        anim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationEnd(animation: Animation?) {
                Log.i("onAnimationEnd","onAnimationEnd")}
            override fun onAnimationStart(animation: Animation?) {
                Log.i("onAnimationStart","onAnimationStart")
            }
            override fun onAnimationRepeat(animation: Animation?) {
                textView.text = textMsg
            }
        })

        textView.startAnimation(anim)
    }
}