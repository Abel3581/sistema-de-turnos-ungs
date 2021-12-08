package clases;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SistemaDeTurnos {

	private String nombreDelSistema;
	private Map<Integer, Votante> votantesPorDNI; // Dni, Votante
	private Map<Integer, Mesa> mesasPorNro; // numeroMesa, Mesa

//--- Constructor.

	public SistemaDeTurnos(String nombre) throws Exception {

		if (nombre == null)
			throw new Exception("El nombre del sistema no puede ser nulo.");

		this.nombreDelSistema = nombre;
		mesasPorNro = new HashMap<Integer, Mesa>();
		votantesPorDNI = new HashMap<Integer, Votante>();

	}

//--- Metodos del Sistema de Turnos.

	public void registrarVotante(int dni, String nombre, int edad, boolean tieneEnfermedad, boolean esTrabajador)
			throws Exception {

		if (edad < 16) {
			throw new Exception("El votante no puede tener menos de 16 años.");

		} else if (votantesPorDNI.containsKey(dni)) {
			throw new Exception("Votante ya registrado");

		} else {

			Votante votante = new Votante(dni, nombre, edad, tieneEnfermedad, esTrabajador);

			votantesPorDNI.put(dni, votante);

		}

	}

	public int agregarMesa(String tipoMesa, int dni) throws Exception {

		int numeroMesa = mesasPorNro.size() + 1;
		Votante votante = null;
		votante = obtenerVotante(dni);
		Presidente presidente = new Presidente(dni, votante.getNombre());

		Mesa mesa;

		switch (tipoMesa) {

		case "Enf_Preex":

			mesa = new MesaEnfPreex(presidente, numeroMesa);
			break;

		case "Mayor65":

			mesa = new MesaMayores(presidente, numeroMesa);
			break;

		case "Trabajador":

			mesa = new MesaTrabajadores(presidente, numeroMesa);
			break;

		case "General":

			mesa = new MesaGeneral(presidente, numeroMesa);
			break;

		default:

			throw new Exception("Se ingreso un tipo de mesa no valido.");
		}

		int horario = mesa.getFranjasHorarias()[0];
		votante.asignarTurno(new Tupla<Integer, Integer>(mesa.getNumero(), horario));
		mesa.registrarVotante(horario, dni);

		mesasPorNro.put(mesa.getNumero(), mesa);
		return numeroMesa;
	}

	public Tupla<Integer, Integer> asignarTurno(Integer dni) throws Exception {

		Votante votante;
		Tupla<Integer, Integer> turno;

		votante = obtenerVotante(dni);

		if (votante.tieneTurnoAsignado())
			return votante.getTurnoAsignado();
		else
			turno = obtenerTurno(votante);

		if (turno != null) {
			votante.asignarTurno(turno);
			Mesa mesa = mesasPorNro.get(turno.getX());
			mesa.registrarVotante(turno.getY(), dni);
		}

		return turno;

	}

	public int asignarTurnos() throws Exception {
		int cantidadTurnosAsignados = 0;

		for (Votante votante : votantesPorDNI.values()) {

			Boolean sinTurno = !votante.tieneTurnoAsignado();
			Boolean turnoAsignado = sinTurno && asignarTurno(votante.getDni()) != null;

			if (turnoAsignado)
				cantidadTurnosAsignados++;
		}

		return cantidadTurnosAsignados;
	}

	public boolean votar(int dni) throws Exception {

		Votante votante = obtenerVotante(dni);

		boolean votoEfectuado = false;
		if (!votante.asistioAVotar()) {
			votante.asistioAVotar(true);
			votoEfectuado = true;
		}

		return votoEfectuado;
	}

	public int votantesConTurno(String tipoMesa) {

		int votantesConTurnoAsignado = 0;

		for (Mesa mesa : mesasPorNro.values())
			if (mesa.esDeTipo(tipoMesa))
				votantesConTurnoAsignado += mesa.cantidadVotantesAsignados();

		return votantesConTurnoAsignado;
	}

	public Tupla<Integer, Integer> consultaTurno(Integer dni) {

		Votante votante = votantesPorDNI.get(dni);
		return votante.getTurnoAsignado();
	}

	public Map<Integer, List<Integer>> asignadosAMesa(Integer nroMesa) throws Exception {

		if (mesasPorNro.containsKey(nroMesa)) {

			Mesa mesa = mesasPorNro.get(nroMesa);

			if (mesa.tieneAsignados()) {

				return mesa.getDNIsAsignadosPorFranjaHoraria();

			} else
				return null;
		}

		else
			throw new Exception("NUMERO DE MESA INVALIDO");

	}

	public List<Tupla<String, Integer>> sinTurnoSegunTipoMesa() {

		int cantEnf_Preex = 0;
		int cantMayor65 = 0;
		int cantTrabajador = 0;
		int cantGeneral = 0;

		for (Votante votante : votantesPorDNI.values()) {

			if (!votante.tieneTurnoAsignado()) {

				if (votante.esTrabajador()) {
					cantTrabajador++;
				}

				else if (votante.tieneEnfermedad()) {
					cantEnf_Preex++;
				}

				else if (votante.esMayor()) {
					cantMayor65++;
				}

				else
					cantGeneral++;

			}
		}

		List<Tupla<String, Integer>> sinTurnoSegunTipoMesa = new ArrayList<Tupla<String, Integer>>();

		sinTurnoSegunTipoMesa.add(new Tupla<String, Integer>("Enf_Preex", cantEnf_Preex));
		sinTurnoSegunTipoMesa.add(new Tupla<String, Integer>("Mayor65", cantMayor65));
		sinTurnoSegunTipoMesa.add(new Tupla<String, Integer>("Trabajador", cantTrabajador));
		sinTurnoSegunTipoMesa.add(new Tupla<String, Integer>("General", cantGeneral));

		return sinTurnoSegunTipoMesa;
	}

//--- Metodos private del Sistema de Turnos.

	private Tupla<Integer, Integer> obtenerTurno(Votante votante) {

		Tupla<Integer, Integer> turno = null;
		Mesa mesa = null;
		int horarioDisponible = 0;

		Iterator<Mesa> itMesa = mesasPorNro.values().iterator();
		while (itMesa.hasNext() && horarioDisponible == 0) {

			mesa = itMesa.next();

			if (mesa.aceptaVotante(votante))
				horarioDisponible = mesa.obtenerHorarioDisponible();
		}

		if (horarioDisponible > 0) {
			turno = new Tupla<Integer, Integer>(mesa.getNumero(), horarioDisponible);
		}

		return turno;
	}

	private Votante obtenerVotante(int dni) throws Exception {

		if (votantesPorDNI.containsKey(dni)) {
			return votantesPorDNI.get(dni);
		}

		else
			throw new Exception("Votante no registrado");
	}

//--- Metodo toString (Sobre-escritura) de Sistema de Turnos.

	@Override
	public String toString() {

		StringBuilder toStringNombreSistema = new StringBuilder();

		toStringNombreSistema.append(nombreDelSistema).append("\n").append("\n").append("\n")
				.append("Mesas habilitadas: ").append("\n\n");

		Iterator<Integer> itMesas = mesasPorNro.keySet().iterator();
		while (itMesas.hasNext()) {
			Integer numMesa = itMesas.next();
			Mesa mesa = mesasPorNro.get(numMesa);
			toStringNombreSistema.append(mesa);
		}
		toStringNombreSistema.append("\nVotantes registrados en el sistema: ").append("\n").append("\n");
		Iterator<Integer> itmap = votantesPorDNI.keySet().iterator();

		while (itmap.hasNext()) {

			Integer dniVotante = itmap.next();
			Votante votante = votantesPorDNI.get(dniVotante);
			Tupla<Integer, Integer> turnoVotante = votante.getTurnoAsignado();

			if (votante.tieneTurnoAsignado()) {

				toStringNombreSistema.append("Nombre: ").append(votante.getNombre()).append(" | ").append("DNI: ")
						.append(dniVotante).append(" | Mi turno es en: ").append("Mesa Nro: °")
						.append(turnoVotante.getX()).append(" a las ").append(turnoVotante.getY()).append(" Horas.")
						.append("\n");
			} else {

				toStringNombreSistema.append("Nombre: ").append(votante.getNombre()).append(" | ").append("DNI: ")
						.append(dniVotante).append(" | ").append("Todavia no tengo turno asignado por el sistema.")
						.append("\n");
			}

		}

		return toStringNombreSistema.toString();
	}

}
