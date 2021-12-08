package clases;

public class Presidente extends Persona {
	
// Constructor  ---------------------------------
	
	public Presidente(int dni, String nombre) 
{
		super(dni, nombre);
}
	
//--- Metodos (Sobre-escritura)
	
	@Override
	public String toString() {
		StringBuilder nombrePresidente = new StringBuilder();
		
		nombrePresidente.append(getNombre()).append("\n");
		
		return nombrePresidente.toString();
	}


}
