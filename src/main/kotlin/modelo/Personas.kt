package modelo

class Personas {

    var DNI:String = ""
    var FechaNacimiento:String = ""
    var Nombre:String = ""
    var Apellidos:String =""
    var Trabajo:String = ""
    var Sueldo:Double = 0.0


    fun insertarPersona(){

        println("Escribe la fecha de nacimiento.")
        this.FechaNacimiento = readln()

        println("Escribe el nombre.")
        this.Nombre= readln()

        println("Escribe el apellido.")
        this.Apellidos= readln()

        println("Escribe el trabajo.")
        this.Trabajo= readln()

        println("Escribe el sueldo.")
        this.Sueldo = readln().toDouble()



    }

}