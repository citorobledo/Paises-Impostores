package ar.edu.unahur.obj2.impostoresPaises.cli
import ar.edu.unahur.obj2.impostoresPaises.api.CurrencyConverterAPI
import ar.edu.unahur.obj2.impostoresPaises.api.RestCountriesAPI
import ar.edu.unahur.obj2.impostoresPaises.Observatorio
import ar.edu.unahur.obj2.impostoresPaises.api.api

// El código de nuestro programa, que (eventualmente) interactuará con una persona real
//object Programa {
//  // Ambas son variables para poder cambiarlas en los tests
//  var entradaSalida = Consola
//  var api = RestCountriesAPI()
//
//  fun iniciar() {
//    entradaSalida.escribirLinea(AsciiArt.mundo)
//    entradaSalida.escribirLinea("Hola, poné el nombre de un país y te mostramos algo de data")
//    val pais = entradaSalida.leerLinea()
//
//    checkNotNull(pais) { "Sin nombre no puedo hacer nada :(" }
//
//    val paisesEncontrados = api.buscarPaisesPorNombre(pais)
//
//    check(paisesEncontrados.isNotEmpty())
//      { "No encontramos nada, fijate si lo escribiste bien" }
//
//    paisesEncontrados.forEach {
//      entradaSalida.escribirLinea(
//        "${it.name} (${it.alpha3Code}) es un país de ${it.region}, con una población de ${it.population} habitantes."
//      )
//    }
//  }
//}
//
// El código de nuestro programa, que (eventualmente) interactuará con una persona real
class Programa (
  var entradaSalida: Consola = Consola,
  var apiPaises: RestCountriesAPI = api.paises(),
  var observatorio: Observatorio = Observatorio(apiDePaises = apiPaises)
){
  // Ambas son variables para poder cambiarlas en los tests

  fun iniciar() {
    var numeroOpcion: Int?
    do {
      entradaSalida.escribirLinea("""
      Hola, escribí el número de la opción que deseas saber:
      1) Si son limítrofes.
      2) Si necesitan traductor para dialogar.
      3) Si son potenciales aliados.
      4) Si conviene ir de compras de un pasi a otro.
      5) Al Convertir moneda de un pais al otro.
      6) El codigos de los 5 paises más poblados.
      7) Cual es el continente con más paises plurinacionales
      8) Cual es el promedio de poblacion en islas
      """.trimIndent())
      numeroOpcion = entradaSalida.leerLinea()?.toIntOrNull()

    }
    while (numeroOpcion == null)

    when (numeroOpcion) {
      1 -> { chequearCondicion("son limítrofes.", observatorio::sonLimitrofes) }
      2 -> { chequearCondicion("nesecitan traduccion.", observatorio::necesitanTraduccion) }
      3 -> { chequearCondicion("son potenciales aliados.", observatorio::sonPotencialesAliados) }
      4 -> { chequearCondicion("conviene ir de compras.", observatorio::convieneIrDeCompras) }
      5 -> { convertirMoneda() }
      6 -> { paisesDeMayorDensidad() }
      7 -> { continenteMasPaisesPlurinacionales() }
      8 -> { promedioPoblacionIslas() }
      else -> { numeroIncorrecto(numeroOpcion) }
    }
  }

  fun numeroIncorrecto(numeroOpcion: Int) {
    if (numeroOpcion > 8) entradaSalida.escribirLinea("El número no es válido.")
  }
  fun convertirMoneda() {
    val (pais, pais2) = pedirPaises()
    entradaSalida.escribirLinea("Por favor, escribí el monto")
    val monto = entradaSalida.leerLinea()

    entradaSalida.escribirLinea("ese monto en la moneda de $pais2 es: " + observatorio.aCuantoEquivaleEn(pais, monto!!.toDouble(), pais2).toString())
  }
  fun paisesDeMayorDensidad() {
    entradaSalida.escribirLinea(
      "Los códigos ISO de los cinco paises más poblados son: ${observatorio.codigosISODe5PaisesMasPoblados()/*los5PaisesConMayorDensidad()*/}"
    )
  }
  fun continenteMasPaisesPlurinacionales() {
    entradaSalida.escribirLinea(
      "El continente con más paises plurinacionales es: ${observatorio.continenteConMasPaisesPlurinacionales()}"
    )
  }
  fun promedioPoblacionIslas(){
    entradaSalida.escribirLinea(
      "El promedio de poblacion en islas es de: ${observatorio.promedioDePoblacionEnIslas()} de personas"
    )
  }

  fun pedirPaises(): Array<String> {
    var pais: String
    do {
      entradaSalida.escribirLinea("Por favor, escribí el nombre del primer pais")
      pais = entradaSalida.leerLinea().toString()
      if(pais.isBlank()) entradaSalida.escribirLinea("Por favor, escribí el nombre del primer pais")
    }
    while(pais.isBlank())

    var pais2: String
    do {
      entradaSalida.escribirLinea("Por favor, escribí el nombre del segundo pais")
      pais2 = entradaSalida.leerLinea().toString()
      if(pais2.isBlank()) entradaSalida.escribirLinea("Por favor, escribí el nombre del segundo pais")
    }
    while(pais2.isBlank())
    try {
      check(observatorio.existe(pais))
    } catch (e: IllegalStateException) {
      entradaSalida.escribirLinea("No existe un pais con el nombre $pais")
    }
    try {
      check(observatorio.existe(pais2))
    } catch (e: IllegalStateException) {
      entradaSalida.escribirLinea("No existe un pais con el nombre $pais2")
    }
    return arrayOf(pais,pais2)
  }

  fun ambosExisten(pais1: String, pais2: String) = observatorio.existe(pais1) && observatorio.existe(pais2)
  fun chequearCondicion(mensaje: String, condicion: (pais1: String, pais2: String) -> Boolean) {
    val (pais, pais2) = pedirPaises()

    if(ambosExisten(pais, pais2)){
      if(condicion(pais, pais2)){
        entradaSalida.escribirLinea(mensaje)
      }
      else {
        entradaSalida.escribirLinea("No " + mensaje)
      }
    }
  }
}

fun main() {
  Programa().iniciar()
}