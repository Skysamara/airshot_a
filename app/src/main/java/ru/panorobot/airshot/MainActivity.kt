package ru.panorobot.airshot

import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

// Почему не получалось обратиться к элементам формы напрямую
// https://stackoverflow.com/questions/52271521/import-kotlinx-android-synthetic-main-activity-main-is-not-working
//

class MainActivity : AppCompatActivity() {
    private val REQUEST_CODE_ENABLE_BT: Int = 1
    private val REQUEST_CODE_DISCOVERBLE_BT: Int = 2

    lateinit var btAdapter: BluetoothAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()  // Инициализируем bluetooth adapter
        checkBluetoothStatus()
        setListeners()
        getPairedDevices()
    }

    private fun checkBluetoothStatus() {
        // check is on/off
        if (btAdapter == null) {
            bluetoothStatusTV.text = "Bluetooht is not available"
        } else {
            bluetoothStatusTV.text = "Bluetooht is available"
        }
        // Статус bluetooth
        if (btAdapter.isEnabled) {
            bluetoothIv.setImageResource(R.drawable.ic_bluetooth_on)
        } else {
            bluetoothIv.setImageResource(R.drawable.ic_bluetooth_off)
        }
    }

    @SuppressLint("MissingPermission")
    private fun MainActivity.setListeners() {
        // Включаем bluetooth
        turnOnBtn.setOnClickListener {
            if (btAdapter.isEnabled) {
                // Уже включен
                Toast.makeText(this, "Already on", Toast.LENGTH_SHORT).show()
            } else {
                // Включаем
                val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivityForResult(intent, REQUEST_CODE_ENABLE_BT)
            }
        }
        // Выключаем bluetooth
        turnOffBtn.setOnClickListener {
            if (!btAdapter.isEnabled) {
                Toast.makeText(this, "Already off", Toast.LENGTH_SHORT).show()
            } else {
                btAdapter.disable()
                bluetoothIv.setImageResource(R.drawable.ic_bluetooth_off)
                Toast.makeText(this, "Bluetooth turned off", Toast.LENGTH_SHORT).show()
            }

        }
        // Ищем bluetooth
        discoverableBtn.setOnClickListener {
            if (!btAdapter.isDiscovering) {
                Toast.makeText(this, "Making Your devices discoverable", Toast.LENGTH_SHORT).show()
                val intent = Intent(Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE))
                startActivityForResult(intent, REQUEST_CODE_DISCOVERBLE_BT)
            }
        }
        // Переходим на окно мишени
        shootBtn.setOnClickListener {
            //TODO Проверить, что bluetooth включен и подключена мишень
            startShooting()

        }
    }

    @SuppressLint("MissingPermission")
    private fun getPairedDevices() {
        // Список сопряженных устроств
        pairedBtn.setOnClickListener {
            if (btAdapter.isEnabled) {
                pairedTv.text = "Paired devices"
                // получаем список сопряженных устройств
                val devices = btAdapter.bondedDevices
                for (device in devices) {
                    val deviceName = device.name
                    val deviceAddress = device
                    pairedTv.append("\nDevice: $deviceName , $device")
                }
            } else {
                Toast.makeText(this, "Turn on bluetooth first", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun init(){
        val btManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        btAdapter = btManager.adapter

    }

    @SuppressLint("MissingPermission")  //TODO Что это?
    private fun getPaireddevices(){
        val pairedDevices: Set<BluetoothDevice>? = btAdapter?.bondedDevices

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_CODE_ENABLE_BT ->
//                if (requestCode == Activity.RESULT_OK){
                if (resultCode == Activity.RESULT_OK) {
                    bluetoothIv.setImageResource(R.drawable.ic_bluetooth_on) // 14:14
                    Toast.makeText(this, "Bluetooth is on", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Could not on bluetooth", Toast.LENGTH_SHORT).show()
                }

        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun startShooting() {
        val target = Target(this)
        target.setOnClickListener {
            target.invalidate()
        }
        setContentView(target)
    }
}