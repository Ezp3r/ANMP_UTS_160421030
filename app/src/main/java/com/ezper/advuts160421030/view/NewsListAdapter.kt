    package com.ezper.advuts160421030.view

    import android.util.Log
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import androidx.navigation.Navigation
    import androidx.recyclerview.widget.RecyclerView
    import com.squareup.picasso.Callback
    import com.squareup.picasso.Picasso
    import com.ezper.advuts160421030.databinding.NewsItemBinding
    import com.ezper.advuts160421030.model.Berita

    class NewsListAdapter(val newsList:ArrayList<Berita>)
        :RecyclerView.Adapter<NewsListAdapter.NewsViewHolder>()
    {
        class NewsViewHolder(var binding: NewsItemBinding): RecyclerView.ViewHolder(binding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
            val binding = NewsItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false)
            return NewsViewHolder(binding)

        }

        override fun getItemCount(): Int {
            return newsList.size
        }

        override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
            holder.binding.txtTitle.text = newsList[position].title
            holder.binding.txtAuthor.text = "@" + newsList[position].author
            holder.binding.txtIsi.text = newsList[position].description

            holder.binding.btnRead.setOnClickListener {
                val action = NewsListFragmentDirections.actionNewsListFragmentToNewsDetailFragment(
                    newsList[position].title,
                    newsList[position].author,
                    newsList[position].url,
                    newsList[position].id
                )
                Navigation.findNavController(it).navigate(action)
            }

            val picasso = Picasso.Builder(holder.itemView.context)
            picasso.listener { picasso, uri, exception ->
                exception.printStackTrace()
            }
            picasso.build().load(newsList[position].url).into(holder.binding.imageView, object :
                Callback {
                override fun onSuccess() {
                    holder.binding.progressLoadImg.visibility = View.INVISIBLE
                    holder.binding.imageView.visibility = View.VISIBLE
                }

                override fun onError(e: Exception?) {
                    Log.e("picasso_error", e.toString())
                }

            })
        }


        fun updateNewsList(newNewsList: ArrayList<Berita>) {
            newsList.clear()
            newsList.addAll(newNewsList)
            notifyDataSetChanged()
        }

    }