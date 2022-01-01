package ar.edu.unahur.obj2.impostoresPaises

import ar.edu.unahur.obj2.impostoresPaises.api.Country
import ar.edu.unahur.obj2.impostoresPaises.api.CurrencyConverterAPI
import ar.edu.unahur.obj2.impostoresPaises.api.RestCountriesAPI

class Transforma (
  var apiDePaises: RestCountriesAPI = RestCountriesAPI(),
  var apiDeCambioDeMoneda: CurrencyConverterAPI = CurrencyConverterAPI("2b2380cb469d454be2f3")){//2b2380cb469d454be2f3 //655a758e7a8711b8cf19

  fun transformarAPais( unPais:String):Pais = transformarAPaisConLimitrofes(buscarPais(unPais))
  fun transformarAPaisConLimitrofes(paisAPI: Country): Pais = agregarLimitrofes(transformarPais(paisAPI))
  fun transformarPais(paisAPI: Country):Pais {
    val codigoDeMoneda = if (paisAPI.currencies.isNullOrEmpty()) "USD"  else  paisAPI.currencies?.first()!!.code
    val cotizacionDolar = apiDeCambioDeMoneda.convertirDolarA(codigoDeMoneda)?: 1.0 // aca esta hard codeado por que se me termino el limite de pedidos a la API
    val bloqueRegional = if(paisAPI.regionalBlocs!!.isNotEmpty()) paisAPI.regionalBlocs!!.map{ it.name } else listOf("nada")
    val idiomasOficiales = paisAPI.languages.map { it.name }
    val superficie = paisAPI.population.toDouble()
    return Pais(
      paisAPI.name,
      paisAPI.alpha3Code,
      paisAPI.population.toInt(),
      paisAPI.area?:superficie,
      paisAPI.region,
      codigoDeMoneda,
      cotizacionDolar,
      paisAPI.capital?:"No especificada",
      mutableListOf(),
      bloqueRegional,
      idiomasOficiales)
  }
  fun buscarPais(unPais: String): Country = apiDePaises.buscarPaisesPorNombre(unPais).first()
  fun agregarLimitrofes(unPais: Pais): Pais {
    val country = buscarPais(unPais.nombre)
    unPais.paisesLimitrofes = paisesLimitrofesDe(country).ifEmpty { listOf() }
    return unPais
  }
  fun paisesLimitrofesDe(paisAPI: Country): List<Pais> = listaDeLimitrofesDelPais(paisAPI).map { transformarPais(it) }
  fun listaDeLimitrofesDelPais(paisAPI: Country) = paisAPI.borders!!.map {apiDePaises.paisConCodigo(it)}
}
