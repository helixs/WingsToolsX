package com.helixs.ui.sensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager

/**
 * Created by helixs on 2017/8/22.
 */

class SensorCtrl(val context:Context){
    var mSensorList:List<Sensor>?=null
    init {
        var  mSensorManager:SensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mSensorList =  mSensorManager.getSensorList(Sensor.TYPE_ALL)

    }
    constructor(context: Context,name:String) : this(context) {
            name.replace("","")
    }
    fun getSensorList(): List<Sensor>? {
        return mSensorList
    }
    fun getSensorManager(): SensorManager? {
        return context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }
}