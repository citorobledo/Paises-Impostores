package ar.edu.unahur.obj2.impostoresPaises.cli
import ar.edu.unahur.obj2.impostoresPaises.api.RestCountriesAPI
import ar.edu.unahur.obj2.impostoresPaises.Observatorio
import ar.edu.unahur.obj2.impostoresPaises.api.Apis
import kotlin.system.exitProcess

class Programa (
  var entradaSalida: Consola = Consola,
  var apiPaises: RestCountriesAPI = Apis.paises(),
  var observatorio: Observatorio = Observatorio(apiDePaises = apiPaises),
  var numeroOpcion : String = "0"
){
  fun iniciar() {
    while (numeroOpcion.toInt() != 9)
      procesoPedidoYRespuesta()
  }
  fun escribeOpciones(){
    entradaSalida.escribirLinea("""
      Hola, escribí el número de la opción que deseas saber :
      1) Si son limítrofes.
      2) Si necesitan traductor para dialogar.
      3) Si son potenciales aliados.
      4) Si conviene ir de compras de un pasi a otro.
      5) Al Convertir moneda de un pais al otro.
      6) El codigos de los 5 paises más poblados.
      7) Cual es el continente con más paises plurinacionales
      8) Cual es el promedio de poblacion en islas
      9) Salir
      """.trimIndent())
  }
  fun numeroCorrecto(dato: String):String {
    var salida : String = dato
    try {
      if (dato.toInt() > 9 || dato.toInt() == 0)
        entradaSalida.escribirLinea("El número no es válido.\n")
      }catch (e:Exception){
      entradaSalida.escribirLinea("no es un numero.\n")
      salida = "0"
    }
    return salida
  }
  fun procesoPedidoYRespuesta(){
    do {
      escribeOpciones()
      numeroOpcion = entradaSalida.leerLinea().toString()
      numeroOpcion = numeroCorrecto(numeroOpcion)
    }
    while ( numeroOpcion.toInt() > 9 )
    when (numeroOpcion.toInt()) {
      1 -> { chequearCondicion("son limítrofes.\n", observatorio::sonLimitrofes) }
      2 -> { chequearCondicion("nesecitan traduccion.\n", observatorio::necesitanTraduccion) }
      3 -> { chequearCondicion("son potenciales aliados.\n", observatorio::sonPotencialesAliados) }
      4 -> { chequearCondicion("conviene ir de compras.\n", observatorio::convieneIrDeCompras) }
      5 -> { convertirMoneda() }
      6 -> { paisesDeMayorDensidad() }
      7 -> { continenteMasPaisesPlurinacionales() }
      8 -> { promedioPoblacionIslas() }
      9 -> { exitProcess(0) }
    }
  }
  fun convertirMoneda() {
    val (pais, pais2) = arrayOf(validarPais(1),validarPais(2))
    entradaSalida.escribirLinea("Por favor, escribí el monto")
    val monto = entradaSalida.leerLinea()
    entradaSalida.escribirLinea("ese monto en la moneda de $pais2 es: " + observatorio.aCuantoEquivaleEn(pais, monto!!.toDouble(), pais2).toString())
  }
  fun paisesDeMayorDensidad() {
    entradaSalida.escribirLinea(
      "Los códigos ISO de los cinco paises más poblados son: ${observatorio.codigosISODe5PaisesMasPoblados()} \n"
    )
  }
  fun continenteMasPaisesPlurinacionales() {
    entradaSalida.escribirLinea(
      "El continente con más paises plurinacionales es: ${observatorio.continenteConMasPaisesPlurinacionales()} \n"
    )
  }
  fun promedioPoblacionIslas(){
    entradaSalida.escribirLinea(
      "El promedio de poblacion en islas es de: ${observatorio.promedioDePoblacionEnIslas()} personas \n"
    )
  }
  fun validarPais(numeroPais:Int):String{
    var pais : String
    do {
      entradaSalida.escribirLinea("Por favor, escribí el nombre del pais $numeroPais")
      pais = entradaSalida.leerLinea().toString()
      if(pais.isBlank() || !observatorio.existe(pais)) entradaSalida.escribirLinea("El pais $pais no existe")
    }
    while(pais.isBlank() || !observatorio.existe(pais))
    return pais
  }
  fun ambosExisten(pais1: String, pais2: String) = observatorio.existe(pais1) && observatorio.existe(pais2)
  fun chequearCondicion(mensaje: String, condicion: (pais1: String, pais2: String) -> Boolean) {
    val (pais, pais2) = arrayOf(validarPais(1),validarPais(2))
    if (ambosExisten(pais, pais2)) {
      if (condicion(pais, pais2)) {
        entradaSalida.escribirLinea(mensaje)
      } else {
        entradaSalida.escribirLinea("No " + mensaje)
      }
    }
  }
}

