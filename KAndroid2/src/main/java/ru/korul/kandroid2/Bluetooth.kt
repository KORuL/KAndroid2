package ru.korul.kandroid2

import android.annotation.SuppressLint
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattDescriptor
import android.content.Context
import android.os.Build

@SuppressLint("MissingPermission")
@Suppress("DEPRECATION")
fun BluetoothGatt?.writeDescriptorCompat(
    descriptor: BluetoothGattDescriptor?,
    type: ByteArray = BluetoothGattDescriptor.ENABLE_INDICATION_VALUE
): Comparable<*>? {
    return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
        descriptor?.value = type
        this?.writeDescriptor(descriptor)
    } else {
        this?.writeDescriptor(descriptor!!, type)
    }
}

@SuppressLint("MissingPermission")
@Suppress("DEPRECATION")
fun BluetoothGatt?.writeCharacteristicCompat(characteristic: BluetoothGattCharacteristic?, data: ByteArray): Comparable<*>? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        this?.writeCharacteristic(characteristic!!, data, BluetoothGattCharacteristic.WRITE_TYPE_DEFAULT)
    } else {
        characteristic?.value = data
        this?.writeCharacteristic(characteristic)
    }
}


inline val Context.bluetoothAdapter
    get() = this.bluetoothManager?.adapter