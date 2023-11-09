package controlador

import modelo.*
import vista.*

/**
 * Clase Controlador que gestiona la lógica del menú y las interacciones del usuario.
 */
class Controlador {
    /**
     * Propiedad que almacena una instancia de la clase Vista para interactuar con la interfaz de usuario.
     */
    private val vista = Vista()

    /**
     * Propiedad que almacena el menú actual. Inicialmente, el valor es Menu.MenuPrincipal.
     */
    private var menuActual: Menu = Menu.MenuPrincipal
    // Inicialmente, estás en el menú principal

    /**
     * Enumeración que representa los diferentes menús disponibles.
     */
    enum class Menu {
        MenuPrincipal,
        ConsultarTabla,
        InsertarTablas,
        ModificarTablas,
        EliminarTablas
    }

    /**
     * Función que maneja las llamadas de menú.
     */
    fun llamadaDeMenu() {
        // Se llama a la función inicializarMenu de la vista para mostrar opciones al usuario.
        when (vista.inicializarMenu()) {
            1 -> {
                // Si el usuario selecciona 1, se cambia el menú actual al de "Consultar Tablas" y se llama a la función correspondiente.
                escribirLogControlador("Se ha pulsado el boton 1, que es para ir al menu de consultar tablas.")
                menuActual = Menu.ConsultarTabla
                consultarTablas()
            }

            2 -> {
                // Si el usuario selecciona 2, se cambia el menú actual al de "Insertar Tablas" y se llama a la función correspondiente.
                escribirLogControlador("Se ha pulsado el boton 2, que es para ir al menu de insertal tablas.")
                menuActual = Menu.InsertarTablas
                insertarEnTablas()
            }

            3 -> {
                // Si el usuario selecciona 3, se cambia el menú actual al de "Modificar Tablas" y se llama a la función correspondiente.
                escribirLogControlador("Se ha pulsado el boton 3, que es para ir al menu de modificar tablas.")
                menuActual = Menu.ModificarTablas
                modificarTablas()
            }

            4 -> {
                // Si el usuario selecciona 4, se cambia el menú actual al de "Eliminar Tablas" y se llama a la función correspondiente.
                escribirLogControlador("Se ha pulsado el boton 4, que es para ir al menu de eliminar tablas.")
                menuActual = Menu.EliminarTablas
                eliminarDeTabla()
            }

            else -> {
                // Si el usuario selecciona una opción no válida, se llama a la función para salir del controlador.
                escribirLogControlador("Se ha pulsado cualquier otro boton que sale del programa.")
                salirdelControlador()
            }
        }
    }

    /**
     * Función que gestiona la consulta de tablas.
     */
    private fun consultarTablas() {
        escribirLogControlador("Consulta la tabla personas.")
        vista.imprimirDatosConsulta("PERSONAS")
        seguirCambiaoSalir()
    }

    /**
     * Función que gestiona la inserción de datos en tablas.
     */
    private fun insertarEnTablas() {
        escribirLogControlador("Inserta la tabla personas.")
        insterPersona()
        seguirCambiaoSalir()
    }

    /**
     * Función que gestiona la modificación de datos en tablas.
     */
    private fun modificarTablas() {
        escribirLogControlador("Modifica la tabla personas.")
        modificarPersona()
        seguirCambiaoSalir()
    }

    /**
     * Función que gestiona la eliminación de datos en tablas.
     */
    private fun eliminarDeTabla() {
        escribirLogControlador("Elimina la tabla personas.")
        eliminarPersona()
        seguirCambiaoSalir()
    }

    /**
     * Función que permite salir del controlador y finalizar la aplicación.
     */
    private fun salirdelControlador() {
        escribirLogControlador("Salir del controlador.")
        Vista().salirDelMenu()
    }

    /**
     * Función que decide si el usuario desea seguir, cambiar al menú principal o salir.
     */
    private fun seguirCambiaoSalir() {
        when (vista.seguirCambiarSalir()) {
            1 -> {
                escribirLogControlador("Eligir que hacer en el menu de nuevo.")
                when (menuActual) {
                    Menu.ConsultarTabla -> consultarTablas()
                    Menu.InsertarTablas -> insertarEnTablas()
                    Menu.ModificarTablas -> modificarTablas()
                    Menu.EliminarTablas -> eliminarDeTabla()
                    else -> salirdelControlador()
                }
            }

            2 -> {
                escribirLogControlador("LLamada del menu.")
                llamadaDeMenu()
            }

            else -> {
                escribirLogControlador("salir del programa.")
                salirdelControlador()
            }
        }

    }

}