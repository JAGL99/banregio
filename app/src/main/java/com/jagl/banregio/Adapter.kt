package com.jagl.banregio

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jagl.banregio.databinding.ItemAmortizacionBinding

class Adapter(
    val tablaAmortizaciones: ArrayList<Amortizaciones.Reporte>
    ) :
    RecyclerView.Adapter<Adapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAmortizacionBinding.inflate( LayoutInflater.from(parent.context),parent, false )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val reporte = tablaAmortizaciones[position]
        holder.bind(reporte)
    }

    override fun getItemCount() = tablaAmortizaciones.size

    class ViewHolder (private val binding: ItemAmortizacionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(reporte: Amortizaciones.Reporte) {
            with(binding) {
                periodoField.text = reporte.periodoPago.toString()
                saldoCreditoField.text = reporte.saldoCredito.toString()
                pagoCapitalField.text = reporte.pagoCapital.toString()
                interesPeriodoField.text = reporte.interes.toString()
                totalField.text = reporte.total.toString()
            }
        }
    }


}
