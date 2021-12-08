package clases;

public abstract class Persona extends Object {
	
	private int dni;
	private String nombre;
	private boolean esPresidente;
	private boolean asistioAVotar;
	
// Constructor  --------------------------------
	
	public Persona(int dni, String nombre)
{
		this.dni = dni;
		this.nombre = nombre;
		
}

//--- Metodos (Sobre-escritura)	
	
	@Override
	public boolean equals(Object obj) {
		
		if (this == obj)
			return true;
		
		if (obj == null)
			return false;
		
		if (!(obj.getClass().equals(getClass())))
			return false;
		
		Persona other = (Persona) obj;
		if(other.getDni() == this.dni)
			return true;
		return false;
	}

// Getters and Setters --------------------------

	public boolean asistioAVotar(){
		return asistioAVotar;
}
	
	public void asistioAVotar(boolean asistioAVotar){
		this.asistioAVotar = asistioAVotar;
}
	
	public boolean getEsPresidente(){
		return esPresidente;
}

	public String getNombre(){
		return nombre;
}
	
	public int getDni() {
		return dni;
}

}
