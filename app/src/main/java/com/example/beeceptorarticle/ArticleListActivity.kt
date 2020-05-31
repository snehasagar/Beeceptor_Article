@file:Suppress("DEPRECATION")

package com.example.beeceptorarticle

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.beeceptorarticle.Model.Article
import com.example.beeceptorarticle.Utils.Loading
import com.example.beeceptorarticle.Utils.WidgetUtils
import com.example.beeceptorarticle.ViewModel.ArticleViewModel
import kotlinx.android.synthetic.main.activity_article.*
import org.jetbrains.anko.startActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@Suppress("DEPRECATION")
class ArticleListActivity : AppCompatActivity() {

    private lateinit var articleAdapter: ArticleAdapter
    private lateinit var listView: ListView
    private lateinit var viewModel: ArticleViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article)
        viewModel = ViewModelProviders.of(this).get(ArticleViewModel()::class.java)
        getArticleData()
    }

    internal fun getArticleData() {
        Loading.showLoading(this)

        val call = viewModel.getArticleFromApi()
        call.enqueue(object : Callback<List<Article>> {
            override fun onResponse(call: Call<List<Article>>, response: Response<List<Article>>) {
                Loading.hideLoading()
                val acticleResponse = response.body()
                if (acticleResponse != null) {
                    listView = findViewById<ListView>(R.id.rv_article)
                    articleAdapter =
                        ArticleAdapter(this@ArticleListActivity, (acticleResponse as? ArrayList)!!)
                    listView.adapter = articleAdapter

                    listView.setOnItemClickListener { parent, view, position, id ->
                        val itemIdAtPos = parent.getItemIdAtPosition(position)
                        val id = acticleResponse[position].id
                        val title = acticleResponse[position].title
                        val description = acticleResponse[position].short_description
                        val logo = acticleResponse[position].avatar
                        startActivity<DetailArticleActivity>(
                            "title" to title,
                            "desc" to description,
                            "logo" to logo,
                            "id" to id
                        )
                    }
                    articleAdapter.notifyDataSetChanged()


                } else {
                    textView_error.visibility = View.VISIBLE
                    Loading.hideLoading()
                    WidgetUtils.showErrorDialog(this@ArticleListActivity,
                        "Service Error!!",
                        getString(R.string.ok_btn),
                        object : WidgetUtils.DialogButton {
                            override fun onClickButton1(dlg: Dialog) {
                                dlg.dismiss()
                            }
                        })
                }

            }

            override fun onFailure(call: Call<List<Article>>, t: Throwable) {
                t.printStackTrace()
                Loading.hideLoading()
                Log.d("error------>", "" + t.message)
                textView_error.visibility = View.VISIBLE
                WidgetUtils.showErrorDialog(this@ArticleListActivity,
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


