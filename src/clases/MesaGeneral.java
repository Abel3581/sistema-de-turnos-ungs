package clases;

public class MesaGeneral extends Mesa {

//--- Constructor.
	
	public MesaGeneral(Presidente presidente, int numero) {
		super(presidente, numero, "General", 30);
	}

//--- Metodos (Sobre-escritura)
	
	@Override
	protected boolean aceptaVotante(Votante votante) {

		return (
			!votante.esTrabajador()
			&& !votante.esMayor()
			&& !votante.tieneEnfermedad()
		);
	}

	@Override
	protected boolean esDeTipo(String tipoMesa) {
		return tipoMesa.equals("General");
	}

	@Override
	public String toString() {
		return super.toString();
	}
	
}
