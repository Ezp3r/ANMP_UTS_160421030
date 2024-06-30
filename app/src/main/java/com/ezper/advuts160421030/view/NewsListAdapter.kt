    package com.ezper.advuts160421030.view

    import android.util.Log
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import android.widget.ImageView
    import androidx.navigation.Navigation
    import androidx.recyclerview.widget.RecyclerView
    import com.squareup.picasso.Callback
    import com.squareup.picasso.Picasso
    import com.ezper.advuts160421030.databinding.NewsItemBinding
    import com.ezper.advuts160421030.model.News

    class NewsListAdapter(val newsList: ArrayList<News>): RecyclerView.Adapter<NewsListAdapter.ListViewHolder>(), NewsDetailClick

    {
        class ListViewHolder(var view: NewsItemBinding): RecyclerView.ViewHolder(view.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
            var view = NewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ListViewHolder(view)
        }

        override fun getItemCount(): Int {
            return newsList.size
        }

        override fun onBindViewHolder(holder: ListViewHolder, position: Int) {

            holder.view.news = newsList[position]
            holder.view.detailListener = this
            load_picture(holder.itemView, newsList[position].url, holder.view.imageView )
        }

        fun load_picture(view: View, photo: String, imageView: ImageView) {
            val picasso = Picasso.Builder(view.context)
            picasso.listener { picasso, uri, exception ->
                exception.printStackTrace()
            }
            picasso.build().load(photo)
                .into(imageView, object : Callback {
                    override fun onSuccess() {
                        imageView.visibility = View.VISIBLE
                    }

                    override fun onError(e: Exception?) {
                        Log.d("picasso error", e.toString())
                    }
                })
        }

        fun updateNewsList(newNewsList: List<News>) {
            newsList.clear()
            newsList.addAll(newNewsList)
            notifyDataSetChanged()
        }


        override fun onNewsDetailClick(v: View) {
            val id = v.tag.toString().toInt()
            val action = NewsListFragmentDirections.actionNewsListFragmentToNewsDetailFragment(id)

            Navigation.findNavController(v).navigate(action)
        }
    }