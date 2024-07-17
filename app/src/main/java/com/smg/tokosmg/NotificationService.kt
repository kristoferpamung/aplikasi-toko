package com.smg.tokosmg

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.smg.tokosmg.model.Transaksi
import kotlin.random.Random

class NotificationService (
    private val context: Context,
    private val transaksi: Transaksi
) {

    private val notificationManager = context.getSystemService(NotificationManager::class.java)

    fun showNotification(){
        val notification = NotificationCompat.Builder(context, "transaction_accepted")
            .setContentTitle("Pesanan Anda Diterima")
            .setContentText("Pesanan ${transaksi.idTransaksi} sudah bisa diambil di Toko")
            .setSmallIcon(R.drawable.frame_8)
            .setPriority(NotificationManager.IMPORTANCE_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(
            Random.nextInt(),
            notification
        )
    }
}