package ru.panorobot.airshot

import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothAdapter
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

    // bluetooth adapter
    lateinit var bAdapter: BluetoothAdapter

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // bluetooth adapter
        bAdapter = BluetoothAdapter.getDefaultAdapter()
        // check is on/off
        if (bAdapter == null) {
            bluetoothStatusTV.text = "Bluetooht is not available"
        } else {
            bluetoothStatusTV.text = "Bluetooht is available"
        }
        // Статус bluetooth
        if (bAdapter.isEnabled) {
            bluetoothIv.setImageResource(R.drawable.ic_bluetooth_on)
        } else {
            bluetoothIv.setImageResource(R.drawable.ic_bluetooth_off)
        }

        // Включаем bluetooth
        // TODO Bluetooth включается, но сообщение и иконка неправильные
        turnOnBtn.setOnClickListener {
            if (bAdapter.isEnabled) {
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
            if (!bAdapter.isEnabled) {
                Toast.makeText(this, "Already off", Toast.LENGTH_SHORT).show()
            } else {
                bAdapter.disable()
                bluetoothIv.setImageResource(R.drawable.ic_bluetooth_off)
                Toast.makeText(this, "Bluetooth turned off", Toast.LENGTH_SHORT).show()
            }

        }
        // Ищем bluetooth
        discoverableBtn.setOnClickListener {
            if (!bAdapter.isDiscovering) {
                Toast.makeText(this, "Making Your devices discoverable", Toast.LENGTH_SHORT).show()
                val intent = Intent(Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE))
                startActivityForResult(intent, REQUEST_CODE_DISCOVERBLE_BT)
            }
        }
        // Список сопряженных устроств
        pairedBtn.setOnClickListener {
            if (bAdapter.isEnabled) {
                pairedTv.text = "Paired devices"
                // получаем список сопряженных устройств
                val devices = bAdapter.bondedDevices
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