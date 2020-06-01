package com.example.beeceptorarticle

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.beeceptorarticle.Model.Article


class ArticleAdapter(
    private val context: Context,
    private val dataSource: ArrayList<Article>
) : BaseAdapter() {

    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getItem(position: Int): Any {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val rowView = inflater.inflate(R.layout.itemlist_article, parent, false)
        val titleTextView = rowView.findViewById(R.id.textview_title) as TextView
        val detailTextView = rowView.findViewById(R.id.textView_description) as TextView
        val logo = rowView.findViewById(R.id.imageView_logo) as ImageView

        val article = getItem(position) as Article
        titleTextView.text = article.title
        detailTextView.text = article.short_description
        if (article.avatar != null) {
            Glide.with(context).load(article.avatar).into(logo)
        }

        return rowView
    }
}