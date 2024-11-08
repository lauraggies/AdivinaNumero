import java.io.File
import kotlin.random.Random

// Función para generar un número aleatorio de 4 dígitos únicos entre 1 y 6
fun generarNumeroSecreto(): String {
    val digitos = mutableListOf(1, 2, 3, 4, 5, 6)
    digitos.shuffle()
    return digitos.take(4).joinToString("")
}

// Función para calcular los aciertos y las coincidencias
fun calcularAciertosYCoincidencias(numeroSecreto: String, intento: String): Pair<Int, Int> {
    var aciertos = 0
    var coincidencias = 0
    val numSecretoList = numeroSecreto.toList()
    val intentoList = intento.toList()

    // Calcular aciertos
    for (i in 0 until 4) {
        if (numeroSecreto[i] == intento[i]) {
            aciertos++
        }
    }

    // Calcular coincidencias
    for (i in 0 until 4) {
        if (numeroSecreto.contains(intento[i]) && numeroSecreto[i] != intento[i]) {
            coincidencias++
        }
    }

    return aciertos to coincidencias
}

// Función para leer la última jugada desde el archivo
fun leerUltimaJugada(): String? {
    val archivo = File("jugada.txt")
    return if (archivo.exists()) {
        archivo.readText().trim()
    } else {
        null
    }
}

// Función para guardar la última jugada en un archivo
fun guardarUltimaJugada(jugada: String) {
    val archivo = File("jugada.txt")
    archivo.writeText(jugada)
}

// Función principal
fun main() {
    val numeroSecreto = generarNumeroSecreto()
    println("Bienvenido al juego. El número secreto ha sido generado.")

    // Intentamos leer la última jugada del archivo
    val ultimaJugada = leerUltimaJugada()
    if (ultimaJugada != null) {
        println("Tu última jugada fue: $ultimaJugada")
    }

    var intentos = 0
    var aciertos = 0

    // Ciclo principal de intentos
    while (intentos < 10 && aciertos < 4) {
        println("Introduce un número de 4 cifras (entre 1 y 6) sin repetir cifras:")
        val intento = readLine()?.trim()

        if (intento != null && intento.length == 4 && intento.all { it in '1'..'6' } && intento.toSet().size == 4) {
            val (aciertosLocal, coincidenciasLocal) = calcularAciertosYCoincidencias(numeroSecreto, intento)

            println("Aciertos: $aciertosLocal, Coincidencias: $coincidenciasLocal")

            aciertos = aciertosLocal
            intentos++

            if (aciertos == 4) {
                println("¡Felicidades! Has adivinado el número secreto $numeroSecreto en $intentos intentos.")
                guardarUltimaJugada(intento)
                break
            }
        } else {
            println("Entrada no válida. Asegúrate de ingresar un número de 4 cifras únicas entre 1 y 6.")
        }
    }

    if (aciertos < 4) {
        println("Lo siento, no has adivinado el número secreto. El número era: $numeroSecreto")
        guardarUltimaJugada("Fallaste en los intentos")
    }
}
