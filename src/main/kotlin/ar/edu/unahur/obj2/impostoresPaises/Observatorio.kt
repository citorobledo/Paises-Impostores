package ar.edu.unahur.obj2.impostoresPaises
import ar.edu.unahur.obj2.impostoresPaises.api.RestCountriesAPI
import ar.edu.unahur.obj2.impostoresPaises.api.Apis

// Etapa 2

class Observatorio (var apiDePaises : RestCountriesAPI = Apis.paises() ){

  fun paises() = apiDePaises.todosLosPaises().map{Transforma().transformarAPais(it.name)}
  fun buscarPais(pais: String) = Transforma().transformarAPais(pais)
  fun sonLimitrofes(pais: String, pais2: String) = buscarPais(paisValido1(pais, pais2)).sonLimitrofes(buscarPais(paisValido2(pais, pais2)))
  fun necesitanTraduccion(pais: String, pais2: String) = buscarPais(paisValido1(pais, pais2)).necesitanTraduccion(buscarPais(paisValido2(pais, pais2)))
  fun sonPotencialesAliados(pais: String, pais2: String) = buscarPais(paisValido1(pais, pais2)).sonPotencialesAliados(buscarPais(paisValido2(pais, pais2)))
  fun convieneIrDeCompras (pais: String, pais2: String) = buscarPais(paisValido1(pais, pais2)).convieneIrDeCompras(buscarPais(paisValido2(pais, pais2)))
  fun aCuantoEquivaleEn (pais: String, monto: Double, pais2: String) = buscarPais(paisValido1(pais, pais2)).aCuantoEquivaleEn(buscarPais(paisValido2(pais, pais2)), monto)
  fun codigosISODe5PaisesMasPoblados() = los5PaisesConMayorDensidad().map { it.codigoIso3 }
  fun los5PaisesConMayorDensidad() = paises().sortedByDescending { it.poblacion }.take(5)
  fun continenteConMasPaisesPlurinacionales() = paisesPlurinacionales().maxOf { it.continente }//corregir
  fun paisesPlurinacionales() = paises().filter { it.esPlurinacional() }
  fun promedioDePoblacionEnIslas() = pasisesQueSonIslas().sumBy { it.poblacion } / pasisesQueSonIslas().size
  fun pasisesQueSonIslas() = paises().filter { it.esUnaIsla() }

  fun validaPaises (pais: String, pais2: String): Array<String> {
    val paises = arrayOf(pais, pais2)
    when {
      pais == pais2 -> error("Error los nombres son iguales")
      !existe(pais) -> error("el pais $pais no existe")
      !existe(pais2) -> error("el pais $pais2 no existe")
      masDeUnPaisConNombre(pais) -> error("Hay mas de un pais con el nombre $pais")
      masDeUnPaisConNombre(pais2) -> error("Hay mas de un pais con el nombre $pais2")
      else -> return paises
    }
  }

  fun paisValido1(pais: String, pais2: String) = validaPaises(pais,pais2).first()
  fun paisValido2(pais: String, pais2: String) = validaPaises(pais,pais2).last()
  fun existe(pais: String): Boolean {
    val paisEncontrado = apiDePaises.buscarPaisesPorNombre(pais)
    return paisEncontrado.isNotEmpty()
  }
  fun masDeUnPaisConNombre(pais:String) = paises().count { it.nombre == pais } > 1
}