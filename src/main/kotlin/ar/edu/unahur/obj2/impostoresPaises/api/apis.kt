package ar.edu.unahur.obj2.impostoresPaises.api

object Apis {
  var cambio : CurrencyConverterAPI = CurrencyConverterAPI("655a758e7a8711b8cf19")
  var paises : RestCountriesAPI = RestCountriesAPI()
  fun apiCambio() = cambio
  fun paises() = paises
}