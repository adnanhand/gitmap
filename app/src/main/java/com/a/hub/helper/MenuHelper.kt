package com.a.hub.helper

import android.content.res.Resources
import androidx.core.content.res.ResourcesCompat
import com.a.hub.R
import com.a.hub.core.SimpleMenuItem

class MenuHelper {

    companion object{

        fun getDrawerMenuItems(resources: Resources): List<SimpleMenuItem> {
            return listOf(
                SimpleMenuItem(1, "Repositories", R.drawable.outline_book_24,
                    ResourcesCompat.getColor(resources, R.color.darkblue, null)),
                SimpleMenuItem(2, "Issues", R.drawable.outline_adjust_24,
                    ResourcesCompat.getColor(resources, R.color.green, null)),
                SimpleMenuItem(3, "Pull Requests", R.drawable.outline_call_merge_24,
                    ResourcesCompat.getColor(resources, R.color.blue, null)),
                SimpleMenuItem(5, "Discussions", R.drawable.outline_chat_bubble_outline_24,
                    ResourcesCompat.getColor(resources, R.color.violet, null)),
                SimpleMenuItem(6, "Organisations", R.drawable.outline_business_24,
                    ResourcesCompat.getColor(resources, R.color.red, null)),
            )
        }

        fun getUserMenuItems(resources: Resources): List<SimpleMenuItem> {
            return listOf(
                SimpleMenuItem(1, "Info", R.drawable.outline_info_24,
                    ResourcesCompat.getColor(resources, R.color.violet, null)),
                SimpleMenuItem(2, "Repositories", R.drawable.outline_book_24,
                    ResourcesCompat.getColor(resources, R.color.darkblue, null)),
                SimpleMenuItem(3, "Organizations", R.drawable.outline_business_24,
                    ResourcesCompat.getColor(resources, R.color.red, null)),
            )
        }

        fun getRepoMenuItems(resources: Resources): List<SimpleMenuItem> {
            return listOf(
                SimpleMenuItem(1, "Readme", R.drawable.outline_info_24,
                    ResourcesCompat.getColor(resources, R.color.red, null)),
                SimpleMenuItem(2, "Code", R.drawable.outline_code_24,
                    ResourcesCompat.getColor(resources, R.color.violet, null)),
                SimpleMenuItem(3, "Issues", R.drawable.outline_adjust_24,
                    ResourcesCompat.getColor(resources, R.color.green, null)),
                SimpleMenuItem(4, "Pull Requests", R.drawable.outline_call_merge_24,
                    ResourcesCompat.getColor(resources, R.color.blue, null)),
                SimpleMenuItem(5, "Contributors", R.drawable.outline_people_24,
                    ResourcesCompat.getColor(resources, R.color.orange, null)),
            )
        }


    }
}