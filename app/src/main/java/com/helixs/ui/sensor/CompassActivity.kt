package com.helixs.ui.sensor

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.helixs.base.BaseActivity
import com.helixs.tools.Pop

import com.helixs.tools.R
import android.os.Handler
import android.util.Log
import android.view.animation.AccelerateInterpolator
import android.widget.ImageView
import android.view.animation.Animation
import android.view.animation.RotateAnimation


class CompassActivity : BaseActivity() {

    val MAX_ROATE_DEGREE = 1f

    var mSensorManager:SensorManager?=null
    /**
     * 加速传感器
     */
    var mAcceleroMeterSensor:Sensor?=null
    /**
     * 磁场传感器
     */
    var mmagneticFieldSensor:Sensor?=null
    /**
     * 加速度传感器输出的函数
     * 分别对应的x,y,z方向上的加速度
     */
    var mAcceleroMeterValues = FloatArray(3)
    /**
     * 磁场传感器输出的函数
     * 分别是xyz轴的地磁强度
     */
    var mmagneticFieldValues = FloatArray(3)
    /**
     * 保存旋转矩阵
     */
    var mRArray  = FloatArray(9)
    /**
     * 输出得到values
     */
    var mValues  = FloatArray(3)

    val mInterpolator:AccelerateInterpolator = AccelerateInterpolator()

    lateinit var mIvCompass: ImageView

    val  mHandler:Handler = Handler()

    var mCurrentDegree:Float = 0f

    var isRun = false

    var mTargetDegree:Float = 0f

    val runnable:Runnable = Runnable {
        run {
            if (!isRun)return@run
            if (mTargetDegree!=mCurrentDegree){
                var to = mTargetDegree
                if (to - mCurrentDegree>180){
                    to -= 360
                }else if (to - mCurrentDegree<-180){
                    to += 360
                }
                var distance = to - mCurrentDegree
                if (Math.abs(distance)>MAX_ROATE_DEGREE){
                    distance = if (distance > 0) MAX_ROATE_DEGREE else -1.0f * MAX_ROATE_DEGREE

                }
                val current = mCurrentDegree;
                mCurrentDegree = normalizeDegree(mCurrentDegree + (to - mCurrentDegree) * mInterpolator.getInterpolation(if (Math
                        .abs(distance) > MAX_ROATE_DEGREE) 0.4f else 0.3f))
                Log.d("current",current.toString())
                Log.d("mCurrentDegree",mCurrentDegree.toString())
                val ra = RotateAnimation(-current,-mCurrentDegree, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                        0.5f)
                ra.fillAfter = true
                mIvCompass.startAnimation(ra)
            }
            mHandler.postDelayed(runnable, 20)
        }
    }
    fun normalizeDegree(degree: Float):Float{

        return (degree+720)%360
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compass)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        mIvCompass = findViewById(R.id.iv_compass) as ImageView
        setSupportActionBar(toolbar)
        val sensorCtrl:SensorCtrl = SensorCtrl(this)
        mSensorManager = sensorCtrl.getSensorManager()
        if (mSensorManager==null){
            Pop.toast("未找到SensorManager")
            return
        }
        mAcceleroMeterSensor = mSensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        mmagneticFieldSensor = mSensorManager?.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)



    }

    override fun onPause() {
        super.onPause()
        mSensorManager?.unregisterListener(myListener)
        isRun = false
    }

    override fun onResume() {
        super.onResume()
        if (mAcceleroMeterSensor==null){
            Pop.toast("未找到加速传感器")
            return
        }
        if (mmagneticFieldSensor==null){
            Pop.toast("未找到磁场传感器")
            return
        }

        isRun = true
        mSensorManager?.registerListener(myListener,mAcceleroMeterSensor,SensorManager.SENSOR_DELAY_NORMAL )

        mSensorManager?.registerListener(myListener, mmagneticFieldSensor,SensorManager.SENSOR_DELAY_NORMAL)
        mHandler.postDelayed(runnable,20)
    }
    val myListener: SensorEventListener = object : SensorEventListener {

        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {

        }

        override fun onSensorChanged(event: SensorEvent) {

            if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
                mAcceleroMeterValues = event.values
            }
            if (event.sensor.type == Sensor.TYPE_MAGNETIC_FIELD) {
                mmagneticFieldValues = event.values
            }
            //调用getRotaionMatrix获得变换矩阵R[]
            SensorManager.getRotationMatrix(mRArray, null, mAcceleroMeterValues, mmagneticFieldValues)
            SensorManager.getOrientation(mRArray, mValues)
            //经过SensorManager.getOrientation(R, values);得到的values值为弧度
            //转换为角度
            var degrees:Float = Math.toDegrees(mValues[0].toDouble()).toFloat()
            //因为+-180的关系呢 由于脑子转不过来 所以呢 用一个笨办法计算
            if (degrees<0){
                degrees += 360
            }

            mTargetDegree =normalizeDegree(degrees)

        }
    }

}
