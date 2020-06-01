@file:Suppress("DEPRECATION")

package com.example.beeceptorarticle

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.beeceptorarticle.Model.ArticleDetails
import com.example.beeceptorarticle.Utils.Loading
import com.example.beeceptorarticle.Utils.WidgetUtils
import com.example.beeceptorarticle.ViewModel.ArticleViewModel
import kotlinx.android.synthetic.main.activity_detail_article.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@Suppress("DEPRECATION")
class DetailArticleActivity : AppCompatActivity() {

    private lateinit var viewModel: ArticleViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_article)
        viewModel = ViewModelProviders.of(this).get(ArticleViewModel()::class.java)
        initView()
        textView_edit.setOnClickListener {
            editTextText_desc.isFocusable = true
            if (textView_edit.text.toString() == "Edit") {
                textView_edit.text = getString(R.string.cancel_btn)
                button_save.visibility = View.VISIBLE



            } else if (textView_edit.text.toString() == "Cancel") {
                textView_edit.text = getString(R.string.edit_btn)
                button_save.visibility = View.GONE


            }

        }
        button_save.setOnClickListener {
            editTextText_desc.isFocusable = false
            finish()
        }

        back_btn.setOnClickListener {
            finish()
        }

    }

    fun initView() {
        val id = intent?.getIntExtra("id", 0)
        getArticleDetailFromApi(id!!)

        val title = intent.getStringExtra("title")
        if (title != null) {
            textView_title.text = title
        }

        val logo = intent.getStringExtra("logo")
        if (logo != null) {
            Glide.with(this).load(logo).into(imageView_logo)
        }
    }

    private fun getArticleDetailFromApi(id: Int) {
        Loading.showLoading(this)
        val call = viewModel.getArticleDetailsFromApi(id)
        call.enqueue(object : Callback<ArticleDetails> {
            override fun onResponse(
                call: Call<ArticleDetails>,
                response: Response<ArticleDetails>
            ) {
                Loading.hideLoading()
                val acticleResponse = response.body()

                if (acticleResponse != null) {
                    editTextText_desc.setText(acticleResponse.text)


                } else {
                    Loading.hideLoading()
                    WidgetUtils.showErrorDialog(this@DetailArticleActivity,
                        "Service Error!!",
                        getString(R.string.ok_btn),
                        object : WidgetUtils.DialogButton {
                            override fun onClickButton1(dlg: Dialog) {
                                dlg.dismiss()
                            }
                        })
                }

            }

            override fun onFailure(call: Call<ArticleDetails>, t: Throwable) {
                t.printStackTrace()
                Loading.hideLoading()
                Log.d("error------>", "" + t.message)
                WidgetUtils.showErrorDialog(this@DetailArticleActivity,
                    "Service Error!!",
                    getString(R.string.ok_btn),
                    object : WidgetUtils.DialogButton {
                        override fun onClickButton1(dlg: Dialog) {
                            dlg.dismiss()
                        }
                    })


            }
        })


    }
}