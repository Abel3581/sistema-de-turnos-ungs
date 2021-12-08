package clases;

public class MesaTrabajadores extends Mesa {

//--- Constructor.
	
	public MesaTrabajadores(Presidente presidente, int numero) {
		super(presidente, numero, "Trabajadores", 0);
		// TODO Auto-generated constructor stub
	}

//--- Metodos (Sobre-escritura)
	
	@Override
	protected boolean aceptaVotante(Votante votante) {
		return votante.esTrabajador();
	}
	
	@Override
	public int obtenerHorarioDisponible() {
		return getFranjasHorarias()[0];
	}

	@Override
	protected boolean esDeTipo(String tipoMesa) {
		return tipoMesa.equals("Trabajadores");
	}
	
	@Override
	public String toString() {
		return super.toString();
	}
	

	
}
