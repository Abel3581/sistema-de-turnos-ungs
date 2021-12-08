package clases;

public class Votante extends Persona{

	private int edad;
	private boolean tieneEnfermedad;
	private boolean esTrabajador;
	private Tupla<Integer, Integer> turnoAsignado; // numMesa, horario
	
//--- Constructor.	
	
	public Votante(int dni, String nombre, int edad, boolean tieneEnfermedad, boolean esTrabajador)
{
		
		super(dni, nombre);
		this.edad = edad;
		this.tieneEnfermedad = tieneEnfermedad;
		this.esTrabajador = esTrabajador;
		
}

//--- Metodos (Sobre-escritura)
	
	@Override
	public String toString() {

		String toString = "NOMBRE: " + getNombre();
		toString += " | DNI: " + getDni();
		toString += " | EDAD : " + edad;
		toString += " | TIENE ENFERMEDAD: " + tieneEnfermedad;
		toString += " | ES TRABAJADOR: " + esTrabajador;
		toString += " | ES MAYOR: " + esMayor();
		toString += " | TIENE TURNO ASIGNADO: " + tieneTurnoAsignado();
		
		return toString;
	}
	
// Getters  ---------------------------

	public Tupla<Integer, Integer> getTurnoAsignado() 
	{
		return turnoAsignado;
	}
	
	public int getEdad() {
		return edad;
	}

	public boolean tieneEnfermedad() {
		return tieneEnfermedad;
	}

	public boolean esTrabajador() {
		return esTrabajador;
	}

	public boolean esMayor() {
		return edad >= 65;
	}
	
	public void asignarTurno(Tupla<Integer, Integer> nuevoTurno) {
		turnoAsignado = nuevoTurno;
	}
	
	public boolean tieneTurnoAsignado() {
		return turnoAsignado != null;
	}

}
