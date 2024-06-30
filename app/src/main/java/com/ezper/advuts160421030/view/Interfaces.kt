package com.ezper.advuts160421030.view

import android.view.View
import com.ezper.advuts160421030.model.User

interface UpdateUserClick {
    fun updateUser(v: View, obj: User)
}


interface NewsDetailClick {
    fun onNewsDetailClick(v: View)
}