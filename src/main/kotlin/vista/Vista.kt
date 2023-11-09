package vista

import modelo.*
import java.sql.SQLException

/**
 * Clase Vista que gestiona la parte visual del menú y las interacciones del usuario.
 */
class Vista {
    class ExcepcionPersonalizada(message: String) : Exception(message)

    /**
     * Función que muestra un menú de opciones y permite al usuario seleccionar una de ellas.
     *
     * @return Un entero que representa la opción seleccionada por el usuario o nulo si no es un número válido.
     */
    fun inicializarMenu(): Int? {

        println(
            """
            Dime que deseas hacer:
            1) Consultar personas.
            2) Añadir a personas.
            3) Modificar personas.
            4) Eliminar de personas.
            5) Salir.
            
        """.trimIndent()
        )
        return readln().toIntOrNull()
    }

    /**
     * Función que muestra un menú de opciones para seguir, cambiar al menú principal o salir.
     *
     * @return Un entero que representa la opción seleccionada por el usuario o nulo si no es un número válido.
     */
    fun seguirCambiarSalir(): Int? {
        println(
            """
            Dime que desea hacer ahora:
            1) Seguir en este directorio.
            2) Cambiar al menu principal.
            3) Salir.
            
        """.trimIndent()
        )
        return readln().toIntOrNull()
    }

    /**
     * Función que muestra un mensaje de despedida cuando el usuario elige salir del menú.
     */
    fun salirDelMenu() {
        println("Has elegido la opción salir. \nHasta luego.\n")
    }

    /**
     * Función que imprime datos de una tabla especificada por su nombre.
     *
     * @param nombreTabla El nombre de la tabla a consultar.
     */
    fun imprimirDatosConsulta(nombreTabla: String) {
        try {
            // Se obtienen los datos de la tabla utilizando la función hacerConsulta.
            val datos = hacerConsulta(nombreTabla)

            if (datos.isEmpty()) {
                // Si no se encuentran datos, se muestra un mensaje indicando que no se encontraron datos.
                println("No se encontraron datos para la tabla '$nombreTabla'.\n")
                escribirLogVista("Lectura de que hacer: \"$nombreTabla\"")
            } else {
                // Si se encuentran datos, se imprime el encabezado y los datos de la tabla.
                println("Datos de la tabla '$nombreTabla':")
                println(
                    """
            ---------------
        """.trimIndent()
                )
                println()
                for (fila in datos) {
                    for ((columna, valor) in fila) {
                        println("$columna: $valor")
                        escribirLogVista("Lectura de que hacer: \"$columna: $valor\"")
                    }
                    println()

                }
            }
        } catch (e: ExcepcionPersonalizada) {
            // Aquí manejas una excepción personalizada si la función hacerConsulta lanza una excepción específica.
            println("Error al consultar la tabla '$nombreTabla': ${e.message}")
        } catch (e: Exception) {
            // Aquí manejas excepciones genéricas o desconocidas.
            println("Error inesperado: ${e.message}")
        }
    }

    /**
     * Función que imprime los DNI de las Personas obtenidos a partir de la base de datos.
     */
    private fun imprimirDniPersona() {
        try {
            val dniPersona = obtenerDniPersona()
            for (dni in dniPersona) {
                println(dni)
                escribirLogVista("Lectura de que hacer: \"$dni\"")
            }
        } catch (e: SQLException) {
            println("Error al obtener DNI de persona: ${e.message}")
        } catch (e: Exception) {
            println("Error inesperado: ${e.message}")
        }
    }

    /**
     * Función que muestra los DNI de las personas y solicita al usuario que introduzca el dni de la persona a modificar.
     *
     * @return El DNI de la persona a modificar ingresado por el usuario.
     */
    fun almacenarDniModificar(): String {
        imprimirDniPersona()
        println("Dime la persona a modificar.\n")
        return readln()
    }

    /**
     * Función que muestra los dni de las personas y solicita al usuario que introduzca el dni de la persona a modificar.
     *
     * @return El DNI de la persona a modificar ingresado por el usuario.
     */
    fun modificarDniPersona(): String {

        val dniPersona = obtenerDniPersona()
        for (dni in dniPersona) {
            println(dni)
            escribirLogVista("Lectura de que hacer: \"$dni\"")
        }
        println("Dime el dni a modificar.\n")

        return readln()

    }

    /**
     * Función que solicita al usuario introducir el nuevo nombre de la persona.
     *
     * @return El nuevo nombre de la persona ingresado por el usuario.
     */
    fun obtenerNuevoNombre(): String {
        println("Dime el nuevo nombre de la persona.\n ")
        return readln()
    }



}