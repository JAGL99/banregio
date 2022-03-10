package com.jagl.banregio

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.jagl.banregio.databinding.ActivityMainBinding
import java.math.BigDecimal
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val montoTotalCredito = 10000.00
        val peridoPago = 10
        val amortizaciones = Amortizaciones(montoTotalCredito.toBigDecimal(),peridoPago)
        val tablaAmortizaciones = amortizaciones.getTabla()
        val adapter = Adapter(tablaAmortizaciones)
        binding.recycler.adapter = adapter
    }
}

class Amortizaciones(
    val montoTotalCredito: BigDecimal,
    val periodoPago: Int
){

    private var saldoCredito: BigDecimal? = null

    private fun getPagoCapital() = montoTotalCredito/periodoPago.toBigDecimal()

    private fun getTasaInteres(montoTotalCredito: BigDecimal): Double{
        var tasa: Double
        if(montoTotalCredito <= 1499.99.toBigDecimal()){
            tasa = 0.10
        }else if (montoTotalCredito <= 2999.99.toBigDecimal()){
            tasa = 0.08
        }else if (montoTotalCredito <= 9999.99.toBigDecimal()){
            tasa = 0.05
        }else if (montoTotalCredito <= 19999.99.toBigDecimal()){
            tasa = 0.02
        }else {
            tasa = 0.01
        }
        Log.d(TAG, "Tasa: $tasa")
        return tasa
    }

    private fun getSaldo(): BigDecimal{
        if (saldoCredito == null){
            saldoCredito = montoTotalCredito
        } else {
            saldoCredito = saldoCredito!! - getPagoCapital()
        }
        Log.d(TAG, "Saldo Credito: $saldoCredito")
        return saldoCredito!!
    }

    private fun getInteres(tasa: Double, saldoCredito: BigDecimal) = saldoCredito * tasa.toBigDecimal()

    private fun getTotal(pagoCapital: BigDecimal, interes: BigDecimal) = pagoCapital + interes

    fun getTabla(): ArrayList<Reporte> {
        val tabla: ArrayList<Reporte> = ArrayList<Reporte>()
        val pagoCapital = getPagoCapital()
        val tasa = getTasaInteres(montoTotalCredito)
        for (i in 1..periodoPago){
            val periodoPago = i
            val saldoCredito = getSaldo()
            val interes = getInteres(tasa, saldoCredito)
            val total = getTotal(pagoCapital,interes)
            val reporte = Reporte ( periodoPago,saldoCredito, pagoCapital, interes, total )
            Log.d(TAG, "Periodo: $i")
            Log.d(TAG, "Saldo: $saldoCredito")
            Log.d(TAG, "Interes: $interes")
            Log.d(TAG, "Total: $total")
            Log.d(TAG, "Reporte: $reporte")
            Log.d(TAG, "Estatus: agregado")
            tabla.add( reporte )
        }
        return tabla
    }

    data class Reporte(
        val periodoPago: Int,
        val saldoCredito: BigDecimal,
        val pagoCapital: BigDecimal,
        val interes: BigDecimal,
        val total: BigDecimal
    )
}

