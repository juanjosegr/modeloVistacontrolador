package modelo

import vista.*
import java.io.File
import java.io.FileWriter
import java.sql.*
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.*

var connection: Connection = establecerConexion()
val vista = Vista()
val personas = Personas()
/**
 * Función que establece una conexión con la base de datos utilizando JDBC.
 *
 * @return Una instancia de `Connection` que representa la conexión a la base de datos.
 */
fun establecerConexion(): Connection {
    try {
        // URL de conexión a la base de datos Oracle
        val jdbcUrl = "jdbc:oracle:thin:@localhost:1521:xe"

        // Se carga el controlador JDBC para Oracle
        Class.forName("oracle.jdbc.driver.OracleDriver")

        // Se establece la conexión a la base de datos y se devuelve
        val connection = DriverManager.getConnection(jdbcUrl, "ADA", "ADA")

        // Se muestra un mensaje indicando que la conexión se ha establecido
        println("Conexión establecida")

        return connection
    } catch (e: ClassNotFoundException) {
        println("Error: No se pudo cargar el controlador JDBC.")
        throw e
    } catch (e: SQLException) {
        println("Error de SQL al establecer la conexión: ${e.message}")
        throw e
    } catch (e: Exception) {
        println("Error inesperado al establecer la conexión: ${e.message}")
        throw e
    }
}

/**
 * Función que consulta y devuelve una lista de nombres de columnas de una tabla en la base de datos.
 *
 * @param nombreTabla El nombre de la tabla cuyas columnas se desean consultar.
 * @return Una lista de cadenas que representan los nombres de las columnas de la tabla.
 */
fun consultarCabeceraTabla(nombreTabla: String): List<String> {
    // Lista para almacenar los nombres de las columnas.
    val columnas = mutableListOf<String>()

    try {
        // Se establece una conexión a la base de datos.
        connection = establecerConexion()


        // Consulta SQL para obtener los nombres de las columnas de la tabla especificada.
        val query = "SELECT column_name FROM all_tab_columns WHERE table_name = ?"

        // Se crea un PreparedStatement para ejecutar la consulta.
        val preparedStatement = connection.prepareStatement(query)
        preparedStatement.setString(1, nombreTabla)

        // Se ejecuta la consulta y se obtiene el resultado.
        val resultadoTabla: ResultSet = preparedStatement.executeQuery()

        // Se recorre el resultado y se agregan los nombres de las columnas a la lista.
        while (resultadoTabla.next()) {
            val columnName = resultadoTabla.getString("column_name")
            columnas.add(columnName)
        }
    } catch (e: SQLException) {
        println("Error de SQL al consultar la cabecera de la tabla '$nombreTabla': ${e.message}")
    } catch (e: Exception) {
        println("Error inesperado al consultar la cabecera de la tabla '$nombreTabla': ${e.message}")
    }

    // Se devuelve la lista de nombres de columnas.
    return columnas
}


/**
 * Función que realiza una consulta a una tabla de la base de datos y devuelve los datos resultantes.
 *
 * @param nombreTabla El nombre de la tabla en la base de datos a consultar.
 * @return Una lista de mapas donde cada mapa representa una fila de datos de la tabla, con nombres de columnas y valores asociados.
 */
fun hacerConsulta(nombreTabla: String): List<Map<String, Any>> {
    try {
        // Se establece una conexión a la base de datos.
        connection = establecerConexion()

        // Lista para almacenar los datos resultantes de la consulta.
        val datos = mutableListOf<Map<String, Any>>()

        // Consulta SQL para obtener todos los datos de la tabla especificada.
        val query = connection.prepareStatement("SELECT * FROM $nombreTabla")
        val result: ResultSet = query.executeQuery()

        // Se obtiene una lista de nombres de columnas de la tabla.
        val nombreColumnas = consultarCabeceraTabla(nombreTabla).toList()

        // Se recorre el resultado y se crea un mapa para cada fila de datos.
        while (result.next()) {
            val fila = mutableMapOf<String, Any>()

            // Se asigna el valor de cada columna a su respectivo nombre de columna en el mapa.
            for (nombreColumna in nombreColumnas) {
                val valor = result.getObject(nombreColumna)
                fila[nombreColumna] = valor
            }
            // Se agrega el mapa (fila de datos) a la lista de datos.
            datos.add(fila)
        }
        // Se devuelve la lista de datos resultantes de la consulta.
        return datos
    } catch (ex: SQLException) {
        // Manejo de excepción específica para errores de conexión.
        throw Vista.ExcepcionPersonalizada("Error al establecer la conexión a la base de datos")
    }
}

/**
 * Función que inserta una persona en la base de datos.
 *
 * Esta función inserta una nueva persona utilizando la instancia de la clase `Persona`, establece una conexión a la base de datos,
 * y luego ejecuta una consulta SQL para insertar los detalles de la persona en la tabla persona.
 */
fun insterPersona() {
    try {
        personas.insertarPersona()
        // Se establece una conexión a la base de datos.
        connection = establecerConexion()

        // Se prepara una consulta SQL para insertar los detalles de la persona en la tabla personas.
        val stmt =
            connection.prepareStatement("INSERT INTO PERSONAS (DNI, FechaNacimiento, Nombre, Apellidos, Trabajo, Sueldo) VALUES (PERSONAS_ID_Seq.NEXTVAL, ?, ?, ?, ?, ?)")
        stmt.setString(1, personas.FechaNacimiento)
        stmt.setString(2, personas.Nombre)
        stmt.setString(3, personas.Apellidos)
        stmt.setString(4, personas.Trabajo)
        stmt.setDouble(5, personas.Sueldo)

        // Se ejecuta la consulta para insertar la factura en la base de datos.
        stmt.executeUpdate()

        // Se cierra el PreparedStatement.
        stmt.close()

        println("Factura insertada exitosamente.\n")
    } catch (e: SQLException) {
        println("Error de SQL al insertar persona: ${e.message}")
    } catch (e: Exception) {
        println("Error inesperado al insertar persona: ${e.message}")
    }
}

/**
 * Función que consulta y devuelve una lista de dni de personas desde la base de datos.
 *
 * @return Una lista de cadenas que representan los dni de personas obtenidos de la base de datos.
 */
fun obtenerDniPersona(): List<String> {
    // Lista para almacenar los dni de las personas.
    val dniPersona = mutableListOf<String>()

    try {
        // Se establece una conexión a la base de datos.
        val connection = establecerConexion()


        // Consulta SQL para obtener los dni de personas desde la tabla personas.
        val query = connection.prepareStatement("SELECT DNI FROM PERSONAS")
        val result: ResultSet = query.executeQuery()

        // Se recorre el resultado y se agregan los DNI de empresas a la lista.
        while (result.next()) {
            val dni = result.getString("DNI")
            dniPersona.add(dni)
        }
    } catch (e: SQLException) {
        // Manejo de excepciones de SQL.
        println("Error de SQL al obtener CIF de empresas: ${e.message}")
    } catch (e: Exception) {
        // Manejo de excepciones generales.
        println("Error inesperado al obtener CIF de empresas: ${e.message}")
    }

    // Se devuelve la lista de CIF de empresas.
    return dniPersona
}

/**
 * Función que modifica los datos de una persona en la base de datos.
 *
 * Esta función obtiene el dni de la persona y el nuevo nombre desde la persona, establece una conexión a la base de datos,
 * y luego ejecuta una consulta SQL para actualizar el precio del producto y el precio total de la factura en la tabla personas.
 */
fun modificarPersona() {
    try {
        // Se obtiene el dni de la persona a modificar desde la vista.
        val dniPersona = vista.modificarDniPersona()

        // Se obtiene el nuevo nombre de la persona desde la vista.
        val nuevosDatos = vista.obtenerNuevoNombre()

        // Se establece una conexión a la base de datos.
        connection = establecerConexion()

        // Se prepara una consulta SQL para actualizar el nombre de la persona en la tabla personas.
        val query = "UPDATE PERSONAS SET Nombre = ? WHERE DNI = ?"
        val stmt = connection.prepareStatement(query)

        // Se establecen los nuevos valores de precio del producto y precio total en la consulta.
        stmt.setString(1, nuevosDatos)
        stmt.setString(2, dniPersona)

        // Se ejecuta la consulta para actualizar los datos de la factura en la base de datos.
        stmt.executeUpdate()

        // Se cierra el PreparedStatement.
        stmt.close()

        println("El nombre de la persona con dni: $dniPersona han sido actualizados exitosamente.\n")
    } catch (e: SQLException) {
        println("Error de SQL al modificar datos de factura: ${e.message}")
    } catch (e: Exception) {
        println("Error inesperado al modificar datos de factura: ${e.message}")
    }
}

/**
 * Función que elimina una persona de la base de datos.
 *
 * Esta función obtiene el DNI de la persona a eliminar desde la vista, establece una conexión a la base de datos y realiza las siguientes acciones:
 */
fun eliminarPersona() {
    try {
        // Se establece una conexión a la base de datos.
        connection = establecerConexion()

        // Se obtiene el dni de la persona a eliminar desde la vista.
        val personaABorrar = vista.almacenarDniModificar()

        // Se verifica si la persona con ese DNI existe antes de intentar eliminarla.
        if (personaExiste(personaABorrar)) {
            // Se prepara una consulta SQL para eliminar la persona de la base de datos.
            val query = "DELETE FROM PERSONAS WHERE DNI = ?"
            val stmt: PreparedStatement = connection.prepareStatement(query)
            stmt.setString(1, personaABorrar)

            // Se ejecuta la consulta para eliminar la persona de la base de datos.
            stmt.executeUpdate()

            // Se cierra el PreparedStatement.
            stmt.close()

            println("La persona con DNI $personaABorrar ha sido eliminada exitosamente.\n")
        } else {
            println("La persona con DNI $personaABorrar no existe en la base de datos.\n")
        }
    } catch (e: SQLException) {
        println("Error de SQL al eliminar persona: ${e.message}")
    } catch (e: Exception) {
        println("Error inesperado al eliminar persona: ${e.message}")
    }
}

/**
 * Verifica si existe una persona en la base de datos con el DNI dado.
 *
 * @param dni El número de identificación de la persona a verificar.
 * @return `true` si la persona con el DNI dado existe, `false` si no existe.
 * @throws SQLException Si hay un error al ejecutar la consulta SQL.
 * @throws Exception Si ocurre un error inesperado durante la verificación.
 */
fun personaExiste(dni: String): Boolean {
    // Verifica si la persona con el DNI dado existe en la base de datos.
    val query = "SELECT 1 FROM PERSONAS WHERE DNI = ?"
    val stmt: PreparedStatement = connection.prepareStatement(query)
    stmt.setString(1, dni)

    val resultSet: ResultSet = stmt.executeQuery()
    val existe = resultSet.next()

    // Se cierra el PreparedStatement y el ResultSet.
    stmt.close()
    resultSet.close()

    return existe
}


fun escribirLogControlador(mensaje:String){
    val nombreArcvhio = "C:\\Users\\juanj\\IdeaProjects\\modeloVistaExplorador\\logs\\archivoControlador.log"

    val archivo = File(nombreArcvhio)

    //Abre el archivo en modo de adicion
    val escritor = FileWriter(archivo,true)

    val formatoFechaHora = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val fechaHoraActual = formatoFechaHora.format(java.util.Date())
    //Ahora puedes escribir contenido en el archivo sin sobreescribirlo
    val contenido = "$fechaHoraActual - $mensaje\n"
    escritor.write(contenido)

    escritor.close()
}

fun escribirLogVista(mensaje:String){
    val nombreArcvhio = "C:\\Users\\juanj\\IdeaProjects\\modeloVistaExplorador\\logs\\archivoVista.log"

    val archivo = File(nombreArcvhio)

    val formatoFechaHora = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val fechaHoraActual = formatoFechaHora.format(java.util.Date())

    //Abre el archivo en modo de adicion
    val escritor = FileWriter(archivo,true)

    //Ahora puedes escribir contenido en el archivo sin sobreescribirlo
    val contenido = "$fechaHoraActual - $mensaje\n"
    escritor.write(contenido)

    escritor.close()
}