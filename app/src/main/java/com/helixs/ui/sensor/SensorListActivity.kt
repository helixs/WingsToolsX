package com.helixs.ui.sensor


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.helixs.adapter.BaseRecycleAdapter
import com.helixs.base.BaseActivity

import com.helixs.tools.R
import com.helixs.ui.func.FucResUtilRes
import com.helixs.ui.func.binding.ItemFuncBinding
import java.util.ArrayList

class SensorListActivity : BaseActivity() {
    private lateinit var sensorListCtrl: SensorCtrl
    private lateinit var recycleView : RecyclerView
    private val funcContents = arrayOf("指南针")
    private val funcIcons = intArrayOf(R.mipmap.ic_launcher)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sensor_list)
        val fucList = ArrayList<FucResUtilRes>()
        for (i in funcContents.indices) {
            val fucRes = FucResUtilRes()
            fucRes.content = funcContents[i]
            fucRes.image = funcIcons[i]
            fucList.add(fucRes)
        }
        sensorListCtrl = SensorCtrl(this)
        recycleView = findViewById(R.id.ry_sensor) as RecyclerView
        recycleView.layoutManager = LinearLayoutManager(this)
        val itemFuncAdapter = SensorListActivity.ItemSensorAdapter(this, R.layout.item_func)
        recycleView.adapter=itemFuncAdapter
        itemFuncAdapter.setList(fucList)
        itemFuncAdapter.notifyDataSetChanged()
    }

    class ItemSensorAdapter(context: Context?, layout: Int) : BaseRecycleAdapter<FucResUtilRes, ItemFuncBinding>(context, layout) {
        override fun bindViewHolder(b: ItemFuncBinding, position: Int, t1: FucResUtilRes) {
            b.info= FucResUtilRes.FuncItemModel()
            b.info.content.set(t1.content)
            b.info.resourcesImage.set(t1.image)
            b.root.setOnClickListener { view: View -> when (t1.content){
                "指南针" -> {
                    view.context.startActivity(Intent(view.context,CompassActivity::class.java))
                }
            } }
        }
    }
}
