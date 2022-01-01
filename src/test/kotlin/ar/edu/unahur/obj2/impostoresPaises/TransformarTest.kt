package ar.edu.unahur.obj2.impostoresPaises

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

//Etapa 3

class TransformarTest: DescribeSpec({
  describe("Transformar un pais"){
    it("recibe el nombre de un pais por parametro y devuelve ese pais transformado a la clase paises") {
      Transforma().transformarAPais("argentina").shouldBeInstanceOf<Paises>()
    }
    it("el nombre del pais es Argentina") {
      Transforma().transformarAPais("argentina").nombre.shouldBe("Argentina")
    }
    it("el codigo ISO3 de venezuela es VEN") {
      Transforma().transformarAPais("venezuela").codigoIso3.shouldBe("VEN")
    }
    it("la poblacion de bolivia es 11673029") {
      Transforma().transformarAPais("bolivia").poblacion.shouldBe(11673029)
    }
    it("la superficie de chile es 756102.0") {
      Transforma().transformarAPais("chile").superficie .shouldBe(756102.0)
    }
    it("el continente de mexico es americas") {
      Transforma().transformarAPais("mexico").continente .shouldBe("Americas")
    }
    it("el codigo de moneda de colombia es COP") {
      Transforma().transformarAPais("colombia").codigoMoneda .shouldBe("COP")
    }
    it("la capital de guatemala es Guatemala City") {
      Transforma().transformarAPais("guatemala").capital .shouldBe("Guatemala City")
    }
    it("los paises limitrofes de Guatemala son Belize, El Salvador, Honduras, Mexico" ) {
      Transforma().transformarAPais("Guatemala").paisesLimitrofes?.map { it.nombre } .shouldBe(setOf("Belize", "El Salvador", "Honduras", "Mexico"))
    }
    it("el bloque regional de panama es Central American Integration System" ) {
      Transforma().transformarAPais("panama").bloquesRegionales .shouldBe(setOf("Central American Integration System"))
    }
    it("los lenguajes de paraguay son Spanish , Guaraní" ) {
      Transforma().transformarAPais("paraguay").idiomasOficiales .shouldBe(setOf("Spanish", "Guaraní"))
    }
  }
})
