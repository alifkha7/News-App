package com.alkhademy.newsapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.alkhademy.newsapp.databinding.ItemArticleBinding
import com.alkhademy.newsapp.model.Article
import com.bumptech.glide.Glide
import java.util.*
import kotlin.collections.ArrayList

class ArticleAdapter :
    RecyclerView.Adapter<ArticleAdapter.ArticleHolder>(), Filterable {

    private lateinit var onItemClickCallback: OnItemClickCallback
    private val list = ArrayList<Article>()
    private var filterList = ArrayList<Article>()

    init {
        filterList = list
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setList(articles: ArrayList<Article>) {
        list.clear()
        list.addAll(articles)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleHolder {
        val view = ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleHolder, position: Int) {
        holder.bind(filterList[position])
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(filterList[holder.adapterPosition]) }
    }

    override fun getItemCount(): Int = filterList.size

    inner class ArticleHolder(private val binding: ItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) {
            binding.apply {
                title.text = article.title
                source.text = article.source.name
                Glide.with(itemView)
                    .load(article.urlToImage)
                    .into(image)
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Article)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                filterList = if (charSearch.isEmpty()) {
                    list
                } else {
                    val resultList = ArrayList<Article>()
                    for (row in list) {
                        if (row.title.toLowerCase(Locale.ROOT)
                                .contains(charSearch.toLowerCase(Locale.ROOT))
                        ) {
                            resultList.add(row)
                        }
                    }
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = filterList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filterList = results?.values as ArrayList<Article>
                notifyDataSetChanged()
            }

        }
    }
}