package clases;

public class MesaEnfPreex extends Mesa {

//--- Constructor.

	public MesaEnfPreex(Presidente presidente, int numero) {
		super(presidente, numero, "Enfermedades Preexistentes", 20);
	}

//--- Metodos (Sobre-escritura)

	@Override
	public boolean aceptaVotante(Votante votante) {

		return (!votante.esTrabajador() && votante.tieneEnfermedad());
	}

	@Override
	protected boolean esDeTipo(String tipoMesa) {
		return tipoMesa.equals("Enf_Preex");
	}
	@Override
	public String toString() {
		return super.toString();
	}
	
}
