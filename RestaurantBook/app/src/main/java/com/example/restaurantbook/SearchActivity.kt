package com.example.restaurantbook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantbook.recycler_view.RecyclerAdapter
import com.example.restaurantbook.recycler_view.restaurantList
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val restaurants = arrayOf(
            "피자스쿨", "명인만두", "우영야식", "꼴뚜바우", "한림성", "대암감자탕", "과방", "한솥도시락", "ok분식", "이삭토스트", "봉구스밥버거", "아리랑떡볶이",
            "도쿄라멘", "토마토도시락", "별채식당", "엄마손식당", "본죽", "차이나게이트", "맘스터치", "닭발마을", "미가락", "홍대무라", "오팔왕돈까스",
            "아방궁", "병천순대", "송가네 냉면주는알밥집"
        )

        val adapter = ArrayAdapter<String>(
            this, android.R.layout.simple_dropdown_item_1line, restaurants
        )
        auto_complete_text_view.setAdapter(adapter)
        auto_complete_text_view.threshold = 1

        val list = ArrayList<restaurantList>()
        list.add(restaurantList(getDrawable(R.drawable.unnamed)!!, getString(R.string.title1)))
        list.add(restaurantList(getDrawable(R.drawable.unnamed)!!, getString(R.string.title2)))
        list.add(restaurantList(getDrawable(R.drawable.unnamed)!!, getString(R.string.title3)))

        val adapter_list = RecyclerAdapter(list)
        //recycle.adapater = adapter_list
    }
}