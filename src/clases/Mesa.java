package clases;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class Mesa {

	protected int numero;
	private Presidente presidente;
	private int[] franjasHorarias;
	private int maximoVotantesPorFranjaHoraria;
	private String tipoDeMesa;
	private Map<Integer, List<Integer>> DNIsAsignadosPorFranjaHoraria;// Franja, Dnis
	private int cantidadVotantesAsignados;

// Constructor  ----------------------------

	public Mesa(Presidente presidente, int numero, String tipoDeMesa, int maximoVotantesPorFranjaHoraria) {

		this.presidente = presidente;
		this.numero = numero;
		this.franjasHorarias = new int[] { 8, 9, 10, 11, 12, 13, 14, 15, 16, 17 };
		this.maximoVotantesPorFranjaHoraria = maximoVotantesPorFranjaHoraria;
		this.tipoDeMesa = tipoDeMesa;

		DNIsAsignadosPorFranjaHoraria = new HashMap<Integer, List<Integer>>();
		for (Integer i : getFranjasHorarias()) {
			DNIsAsignadosPorFranjaHoraria.put(i, new ArrayList<Integer>());
		}

	}

// --- Metodos abstract

	protected abstract boolean aceptaVotante(Votante votante);

	protected abstract boolean esDeTipo(String tipoMesa);

//--- Metodos de Mesa.

	public void registrarVotante(int horario, int dni) {
		// Se asume que los horarios son correctos y los dnis unicos
		DNIsAsignadosPorFranjaHoraria.get(horario).add(dni);
		cantidadVotantesAsignados++;
	}

	public boolean tieneAsignados() {
		// TODO Auto-generated method stub
		boolean tieneAsignados = false;
		for (List<Integer> dnisAsignados : DNIsAsignadosPorFranjaHoraria.values())
			tieneAsignados = tieneAsignados || (!dnisAsignados.isEmpty());

		return tieneAsignados;
	}

	public int obtenerHorarioDisponible() {

		boolean horarioDisponible = false;
		int horario = 0;

		Iterator<Integer> itHorarios = DNIsAsignadosPorFranjaHoraria.keySet().iterator();

		while (itHorarios.hasNext() && !horarioDisponible) {

			horario = itHorarios.next();

			horarioDisponible = DNIsAsignadosPorFranjaHoraria.get(horario).size() < maximoVotantesPorFranjaHoraria;
		}

		if (!horarioDisponible)
			horario = 0;

		return horario;

	}

//--- Metodo toString (Sobre-escritura) De Mesa (Falta equals).

	@Override
	public String toString() {
		StringBuilder toStringMesa = new StringBuilder();

		toStringMesa.append("Soy una mesa para: ").append(tipoDeMesa).append(" | ").append("Mi presidente es: ")
				.append(presidente.toString());

		return toStringMesa.toString();
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;

		if (obj == null)
			return false;

		if (getClass() != obj.getClass())
			return false;

		Mesa other = (Mesa) obj;
		if (numero == other.numero)
			return true;
		return false;
	}

// ------- Getters and Setters

	public Presidente getPresidente() {
		return presidente;
	}

	public int[] getFranjasHorarias() {
		return franjasHorarias;
	}

	public int getNumero() {
		return numero;
	}

	public Map<Integer, List<Integer>> getDNIsAsignadosPorFranjaHoraria() {
		return DNIsAsignadosPorFranjaHoraria;
	}

	public int cantidadVotantesAsignados() {
		return cantidadVotantesAsignados;
	}

}
