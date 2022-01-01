package ar.edu.unahur.obj2.impostoresPaises

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe

//Etapa 1

class PaisesTest: DescribeSpec ({
  describe("Saber si") {
    val chile = Pais(
      "Chile", "CHL", 19120000,
      756950.0, "America", "CLP", 798.05,
      "Santiago de Chile", mutableListOf(), listOf("Alianza del Pacífico"), listOf("Español")
    )
    val argentina = Pais(
      "Argentina", "ARG", 44000000,
      278000000.0, "America", "ARS", 100.0,
      "Buenos Aires", mutableListOf(chile), listOf("Mercosur"), listOf("Español")
    )
    val jamaica = Pais(
    "Jamaica", "JAM", 2961000,
    10991.0, "America", "JMD", 155.59,
    "Kingston", mutableListOf(), listOf("CARICOM"), listOf("Ingles", "Jamaiquino")
    )
    val paraguay = Pais(
      "Paraguay", "PRY", 7133000,
      406752.0, "America", "PYG", 6866.72,
      "Asuncion", mutableListOf(argentina, jamaica), listOf("Mercosur"), listOf("Guarani", "Español")
    )


    describe("Paraguay es plurinacional") {
      paraguay.esPlurinacional().shouldBeTrue()
    }
    describe("Argentina no es plurinacional") {
      argentina.esPlurinacional().shouldBeFalse()
    }
    describe("Argentina no es una isla") {
      argentina.esUnaIsla().shouldBeFalse()
    }
    describe("Jamaica es una isla") {
      jamaica.esUnaIsla().shouldBeTrue()
    }
    describe("Jamaica tiene una densidad poblacional de 269 personas por Km2") {
      jamaica.densidadPoblacional().shouldBe(269)
    }
    describe("Paraguay tiene de vecino mas poblado a Argentina") {
      paraguay.vecinoMasPoblado().shouldBe(argentina)
    }
    describe("Paraguay es limitrofe con Argentina") {
      paraguay.sonLimitrofes(argentina).shouldBeTrue()
    }
    describe("Paraguay no es limitrofe con Jamaica") {
      paraguay.sonLimitrofes(jamaica).shouldBeTrue()
    }
    describe("Paraguay no necesita traduccion con Argentina") {
      paraguay.necesitanTraduccion(argentina).shouldBeFalse()
    }
    describe("Paraguay necesita traduccion con Jamaica") {
      paraguay.necesitanTraduccion(jamaica).shouldBeTrue()
    }
    describe("Paraguay es potencial aliado de Argentina") {
      paraguay.sonPotencialesAliados(argentina).shouldBeTrue()
    }
    describe("Paraguay no es potencial aliado de Chile") {
      paraguay.sonPotencialesAliados(chile).shouldBeFalse()
    }
    describe("En Argentina conviene ir de compras a Paraguay") {
      argentina.convieneIrDeCompras(paraguay).shouldBeTrue()
    }
    describe("En Paraguay no conviene ir de compras a Jamaica") {
      paraguay.convieneIrDeCompras(jamaica).shouldBeFalse()
    }
    describe("En Paraguay 100.00 guaranies Paraguayos equivale a 2.0 dolares jamaiquinos") {
      paraguay.aCuantoEquivaleEn(jamaica, 100.00).shouldBe(2.0)
    }
  }
})