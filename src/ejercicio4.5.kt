class Tiempo(var hora: Int, var minuto: Int = 0, var segundo: Int = 0) {
    init {
        normalizar()
        if (hora >= 24) {
            throw IllegalArgumentException("La hora debe ser menor que 24")
        }
    }

    private fun normalizar() {
        minuto += segundo / 60
        segundo %= 60
        hora += minuto / 60
        minuto %= 60
    }

    override fun toString(): String {
        return "${hora.toString().padStart(2, '0')}h ${minuto.toString().padStart(2, '0')}m ${segundo.toString().padStart(2, '0')}s"
    }

    fun incrementar(t: Tiempo): Boolean {
        val nuevoTiempo = Tiempo(hora + t.hora, minuto + t.minuto, segundo + t.segundo)
        if (nuevoTiempo.hora < 24) {
            hora = nuevoTiempo.hora
            minuto = nuevoTiempo.minuto
            segundo = nuevoTiempo.segundo
            return true
        }
        return false
    }

    fun decrementar(t: Tiempo): Boolean {
        val totalSegundos = (hora * 3600 + minuto * 60 + segundo) - (t.hora * 3600 + t.minuto * 60 + t.segundo)
        if (totalSegundos >= 0) {
            hora = totalSegundos / 3600
            minuto = (totalSegundos % 3600) / 60
            segundo = totalSegundos % 60
            return true
        }
        return false
    }

    fun comparar(t: Tiempo): Int {
        val thisSeconds = hora * 3600 + minuto * 60 + segundo
        val tSeconds = t.hora * 3600 + t.minuto * 60 + t.segundo
        return when {
            thisSeconds < tSeconds -> -1
            thisSeconds > tSeconds -> 1
            else -> 0
        }
    }

    fun copiar(): Tiempo {
        return Tiempo(hora, minuto, segundo)
    }

    fun copiar(t: Tiempo) {
        hora = t.hora
        minuto = t.minuto
        segundo = t.segundo
    }

    fun sumar(t: Tiempo): Tiempo? {
        val nuevoTiempo = Tiempo(hora + t.hora, minuto + t.minuto, segundo + t.segundo)
        return if (nuevoTiempo.hora < 24) nuevoTiempo else null
    }

    fun restar(t: Tiempo): Tiempo? {
        val totalSegundos = (hora * 3600 + minuto * 60 + segundo) - (t.hora * 3600 + t.minuto * 60 + t.segundo)
        return if (totalSegundos >= 0) {
            Tiempo(totalSegundos / 3600, (totalSegundos % 3600) / 60, totalSegundos % 60)
        } else null
    }

    fun esMayorQue(t: Tiempo): Boolean {
        return comparar(t) > 0
    }

    fun esMenorQue(t: Tiempo): Boolean {
        return comparar(t) < 0
    }
}

fun main() {
    println("Ingrese la hora (0-23):")
    val hora = readLine()?.toIntOrNull() ?: 0

    println("Ingrese los minutos (opcional, presione Enter para omitir):")
    val minutos = readLine()?.toIntOrNull() ?: 0

    println("Ingrese los segundos (opcional, presione Enter para omitir):")
    val segundos = readLine()?.toIntOrNull() ?: 0

    try {
        val tiempo = Tiempo(hora, minutos, segundos)
        println("Tiempo inicial: $tiempo")

        println("\nIngrese el tiempo para incrementar:")
        val tiempoIncrementar = leerTiempo()
        if (tiempo.incrementar(tiempoIncrementar)) {
            println("Tiempo después de incrementar: $tiempo")
        } else {
            println("Error: No se pudo incrementar el tiempo (superaría las 23:59:59)")
        }

        println("\nIngrese el tiempo para decrementar:")
        val tiempoDecrementar = leerTiempo()
        if (tiempo.decrementar(tiempoDecrementar)) {
            println("Tiempo después de decrementar: $tiempo")
        } else {
            println("Error: No se pudo decrementar el tiempo (sería menor que 00:00:00)")
        }

        println("\nIngrese el tiempo para comparar:")
        val tiempoComparar = leerTiempo()
        when (tiempo.comparar(tiempoComparar)) {
            -1 -> println("El tiempo actual es menor que el tiempo ingresado")
            0 -> println("Los tiempos son iguales")
            1 -> println("El tiempo actual es mayor que el tiempo ingresado")
        }

        val tiempoCopia = tiempo.copiar()
        println("\nTiempo copiado: $tiempoCopia")

        println("\nIngrese el tiempo para copiar al tiempo actual:")
        val tiempoCopiar = leerTiempo()
        tiempo.copiar(tiempoCopiar)
        println("Tiempo actual después de copiar: $tiempo")

        println("\nIngrese el tiempo para sumar:")
        val tiempoSumar = leerTiempo()
        val resultadoSuma = tiempo.sumar(tiempoSumar)
        if (resultadoSuma != null) {
            println("Resultado de la suma: $resultadoSuma")
        } else {
            println("Error: La suma supera las 23:59:59")
        }

        println("\nIngrese el tiempo para restar:")
        val tiempoRestar = leerTiempo()
        val resultadoResta = tiempo.restar(tiempoRestar)
        if (resultadoResta != null) {
            println("Resultado de la resta: $resultadoResta")
        } else {
            println("Error: La resta resulta en un tiempo negativo")
        }

        println("\nIngrese el tiempo para comparar si es mayor:")
        val tiempoMayor = leerTiempo()
        println("¿El tiempo actual es mayor? ${tiempo.esMayorQue(tiempoMayor)}")

        println("\nIngrese el tiempo para comparar si es menor:")
        val tiempoMenor = leerTiempo()
        println("¿El tiempo actual es menor? ${tiempo.esMenorQue(tiempoMenor)}")

    } catch (e: IllegalArgumentException) {
        println("Error: ${e.message}")
    }
}

fun leerTiempo(): Tiempo {
    println("Hora:")
    val hora = readLine()?.toIntOrNull() ?: 0
    println("Minutos:")

    val minutos = readLine()?.toIntOrNull() ?: 0
    println("Segundos:")

    val segundos = readLine()?.toIntOrNull() ?: 0
    return Tiempo(hora, minutos, segundos)
}