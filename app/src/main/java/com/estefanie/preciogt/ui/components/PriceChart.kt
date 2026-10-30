package com.estefanie.preciogt.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.remember
import androidx.compose.ui.viewinterop.AndroidView
import kotlin.collections.getOrNull
import kotlin.collections.mapIndexed
import kotlin.math.abs
import kotlin.math.roundToInt

import android.graphics.Color as AndroidColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.estefanie.preciogt.ui.screens.PrecioPorFecha
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import java.util.Locale

data class PrecioPorFecha(
    val fecha: String,
    val precioA: Float,
    val precioB: Float
)

@Composable
fun PriceChart(
    precios: List<PrecioPorFecha>,
    nombreSupermercadoA: String,
    nombreSupermercadoB: String,
    modifier: Modifier = Modifier
) {
    val colorTexto = MaterialTheme.colorScheme.onSurface.toArgb()
    val colorCuadricula =
        MaterialTheme.colorScheme.outlineVariant.toArgb()

    val datos = remember(
        precios,
        nombreSupermercadoA,
        nombreSupermercadoB
    ) {
        val serieA = crearSerie(
            entradas = precios.mapIndexed { indice, precio ->
                Entry(indice.toFloat(), precio.precioA)
            },
            nombre = nombreSupermercadoA,
            colorLinea = AndroidColor.rgb(33, 150, 243)
        )

        val serieB = crearSerie(
            entradas = precios.mapIndexed { indice, precio ->
                Entry(indice.toFloat(), precio.precioB)
            },
            nombre = nombreSupermercadoB,
            colorLinea = AndroidColor.rgb(239, 108, 0)
        )

        LineData(serieA, serieB)
    }

    AndroidView(
        modifier = modifier,
        factory = { context ->
            LineChart(context).apply {
                description.isEnabled = false
                axisRight.isEnabled = false

                setNoDataText("No hay precios disponibles")
                setTouchEnabled(true)
                isDragEnabled = true
                setScaleEnabled(true)
                setPinchZoom(true)

                setExtraOffsets(8f, 12f, 8f, 12f)

                legend.apply {
                    isEnabled = true
                    isWordWrapEnabled = true
                    textSize = 12f
                }

                xAxis.apply {
                    position = XAxis.XAxisPosition.BOTTOM
                    granularity = 1f
                    isGranularityEnabled = true
                    setDrawGridLines(false)
                    textSize = 11f
                }

                axisLeft.apply {
                    textSize = 11f

                    valueFormatter = object : ValueFormatter() {
                        override fun getFormattedValue(
                            value: Float
                        ): String {
                            return String.format(
                                Locale.US,
                                "Q%.2f",
                                value
                            )
                        }
                    }
                }
            }
        },
        update = { chart ->
            chart.legend.textColor = colorTexto
            chart.xAxis.textColor = colorTexto
            chart.axisLeft.textColor = colorTexto
            chart.axisLeft.gridColor = colorCuadricula
            chart.setNoDataTextColor(colorTexto)

            chart.xAxis.valueFormatter =
                object : ValueFormatter() {
                    override fun getFormattedValue(
                        value: Float
                    ): String {
                        val indice = value.roundToInt()

                        return if (abs(value - indice) < 0.001f) {
                            precios.getOrNull(indice)?.fecha.orEmpty()
                        } else {
                            ""
                        }
                    }
                }

            if (precios.isEmpty()) {
                chart.clear()
            } else {
                chart.data = datos
                chart.notifyDataSetChanged()
                chart.invalidate()



                val precioMinimo = precios.minOf {
                    minOf(it.precioA, it.precioB)
                }

                val precioMaximo = precios.maxOf {
                    maxOf(it.precioA, it.precioB)
                }

                chart.axisLeft.apply {
                    axisMinimum = (precioMinimo * 0.95f).coerceAtLeast(0f)

                    // Evita un eje sin rango si todos los precios son cero.
                    axisMaximum = (precioMaximo * 1.05f)
                        .coerceAtLeast(axisMinimum + 1f)
                }

                chart.data = datos
                chart.notifyDataSetChanged()
                chart.invalidate()
            }
        }
    )
}

private fun crearSerie(
    entradas: List<Entry>,
    nombre: String,
    colorLinea: Int
): LineDataSet {
    return LineDataSet(entradas, nombre).apply {
        color = colorLinea
        lineWidth = 2.5f

        setCircleColor(colorLinea)
        circleRadius = 4f
        setDrawCircleHole(false)
        setDrawValues(false)

        mode = LineDataSet.Mode.LINEAR

        setDrawHorizontalHighlightIndicator(false)
        highLightColor = colorLinea
    }
}





@Preview(
    name = "Historial de precios",
    showBackground = true,
    widthDp = 400,
    heightDp = 360
)
@Composable
private fun PriceChartPreview() {
    MaterialTheme {
        Surface {
            PriceChart(
                precios = listOf(
                    PrecioPorFecha("24/10", 125.00f, 130.00f),
                    PrecioPorFecha("25/10", 127.50f, 119.00f),
                    PrecioPorFecha("26/10", 130.00f, 115.00f),
                    PrecioPorFecha("27/10", 122.50f, 119.50f),
                    PrecioPorFecha("28/10", 110.00f, 125.00f),
                    PrecioPorFecha("29/10", 100.00f, 120.50f)
                ),
                nombreSupermercadoA = "Walmart",
                nombreSupermercadoB = "La Torre",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(320.dp)
            )
        }
    }
}