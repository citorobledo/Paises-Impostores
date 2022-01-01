package ar.edu.unahur.obj2.impostoresPaises

import kotlin.math.round
import kotlin.math.roundToInt

class Paises (val nombre: String, val codigoIso3: String, var poblacion: Int,
              val superficie: Double?, val continente: String, var codigoMoneda: String,
              var cotizacionDolar: Double?, var capital: String, var paisesLimitrofes: List<Paises>? = listOf(),
              var bloquesRegionales:List<String>? = listOf() , var idiomasOficiales: List<String> = listOf()) {
  fun esPlurinacional() =  idiomasOficiales.size > 1
  fun esUnaIsla() = paisesLimitrofes!!.isEmpty()
  fun densidadPoblacional() = (poblacion / superficie!!).roundToInt()
  fun vecinoMasPoblado() = if (this.poblacion > paisesLimitrofes!!.maxOf {it.poblacion}) this else paisesLimitrofes?.find { it.poblacion == paisesLimitrofes!!.maxOf {it.poblacion} }
  //Hay una forma de sumar el propio pais (this) a una lista temporal junto con paisesLimitrofes y despues comparar las poblaciones
  //desde esa lista pero no me la acuerdo, si no sale dividir en subtareas vecinoMasPoblado() como esta.
  fun sonLimitrofes(pais: Paises) = paisesLimitrofes?.map { it.nombre } !!.contains(pais.nombre)
  fun necesitanTraduccion(pais: Paises) = !pais.idiomasOficiales.any {auxiliarIdiomas(it)}
  fun sonPotencialesAliados(pais: Paises) = !necesitanTraduccion(pais) && pais.bloquesRegionales!!.any{auxiliarAliados(it)}
  fun convieneIrDeCompras(pais: Paises) = pais.cotizacionDolar!! > cotizacionDolar!!
  fun aCuantoEquivaleEn(pais: Paises, monto: Double) = round(((monto/cotizacionDolar!!) * pais.cotizacionDolar!!))
  fun auxiliarIdiomas(idioma: String) = idiomasOficiales.contains(idioma)
  fun auxiliarAliados(bloque: String) = bloquesRegionales!!.contains(bloque)
}