package com.example.ott.util
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
fun AppCompatActivity.showToast(message: String)
{
    Toast.makeText(this,message,Toast.LENGTH_LONG).show()
}
fun Fragment.showToast(message: String)
{
    Toast.makeText(requireContext(),message,Toast.LENGTH_LONG).show()
}

fun showToast(context: Context,message: String)
{
    Toast.makeText(context,message,Toast.LENGTH_LONG).show()
}
fun toastConnections(context: Context)
{
    Toast.makeText(context,"Please enable connections",Toast.LENGTH_LONG).show()
}




fun AppCompatActivity.fullScreen()
{
    window.setFlags(512,512)

}

fun AppCompatActivity.moveActivity(activity: Activity)
{
    startActivity(Intent(this,activity::class.java))
}

fun AppCompatActivity.moveWithDataActivity(msg:String,activity: Activity)
{
    startActivity(Intent(this,activity::class.java).putExtra("key",msg))
}


fun AppCompatActivity.getStringFromView(view: EditText):String
{
  return  view.text.toString()
}




fun AppCompatActivity.setBg(view: View,id:Int)
{
    view.setBackgroundResource(id)
}







fun TextView.setClickableTextWithIntent(
    context: Context,
    fullText: String,
    clickableText1: String,
    clickableText2: String,
    intent1: Intent,
    intent2: Intent,
    textColor: Int
) {
    val spannableString = SpannableString(fullText)

    val clickableSpan1 = object : ClickableSpan() {
        override fun onClick(view: View) {
            context.startActivity(intent1)
        }
    }

    val clickableSpan2 = object : ClickableSpan() {
        override fun onClick(view: View) {
            context.startActivity(intent2)
        }
    }

    val startIndex1 = fullText.indexOf(clickableText1)
    val endIndex1 = startIndex1 + clickableText1.length

    val startIndex2 = fullText.indexOf(clickableText2)
    val endIndex2 = startIndex2 + clickableText2.length

    spannableString.setSpan(clickableSpan1, startIndex1, endIndex1, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
    spannableString.setSpan(clickableSpan2, startIndex2, endIndex2, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
    spannableString.setSpan(ForegroundColorSpan(ContextCompat.getColor(context, textColor)), 0, fullText.length, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)

    text = spannableString
    movementMethod = LinkMovementMethod.getInstance()
}
