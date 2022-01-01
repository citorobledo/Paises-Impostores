package ar.edu.unahur.obj2.impostoresPaises

import kotlin.math.round
import kotlin.math.roundToInt

class Pais (val nombre: String, val codigoIso3: String, var poblacion: Int,
            val superficie: Double?, val continente: String, var codigoMoneda: String,
            var cotizacionDolar: Double?, var capital: String, var paisesLimitrofes: List<Pais>? = listOf(),
            var bloquesRegionales:List<String>? = listOf(), var idiomasOficiales: List<String> = listOf()) {
  fun esPlurinacional() =  idiomasOficiales.size > 1
  fun esUnaIsla() = paisesLimitrofes!!.isEmpty()
  fun densidadPoblacional() = (poblacion / superficie!!).roundToInt()
  fun vecinoMasPoblado() = paisesLimitrofes!!.plus(this).maxByOrNull { it.poblacion } // corregido
  fun sonLimitrofes(pais: Pais) = paisesLimitrofes?.map { it.nombre } !!.contains(pais.nombre)
  fun necesitanTraduccion(pais: Pais) = !pais.idiomasOficiales.any {auxiliarIdiomas(it)}
  fun sonPotencialesAliados(pais: Pais) = !necesitanTraduccion(pais) && pais.bloquesRegionales!!.any{auxiliarAliados(it)}
  fun convieneIrDeCompras(pais: Pais) = pais.cotizacionDolar!! > cotizacionDolar!!
  fun aCuantoEquivaleEn(pais: Pais, monto: Double) = round(((monto/cotizacionDolar!!) * pais.cotizacionDolar!!))
  fun auxiliarIdiomas(idioma: String) = idiomasOficiales.contains(idioma)
  fun auxiliarAliados(bloque: String) = bloquesRegionales!!.contains(bloque)
}