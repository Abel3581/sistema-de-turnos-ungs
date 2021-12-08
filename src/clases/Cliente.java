package clases;

import java.util.List;
import java.util.Map;

public class Cliente {

	private static final Fixture F = Fixture.INSTANCE;

	public static void main(String[] args) throws Exception {

		SistemaDeTurnos sistema = new SistemaDeTurnos("Sistema de Turnos para Votación - UNGS");

		sistema.registrarVotante(F.dniFrodo, "Frodo", 23, !F.tieneEnfPrevia, !F.trabaja);
		sistema.registrarVotante(F.dniEowyn, "Eowyn", 25, F.tieneEnfPrevia, !F.trabaja);
		sistema.registrarVotante(F.dniBilbo, "Bilbo", 65, F.tieneEnfPrevia, !F.trabaja);
		sistema.registrarVotante(F.dniGandalf, "Gandalf", 70, !F.tieneEnfPrevia, F.trabaja);
		sistema.registrarVotante(F.dniLegolas, "Legolas", 80, !F.tieneEnfPrevia, F.trabaja);
		sistema.registrarVotante(F.dniGaladriel, "Galadriel", 81, !F.tieneEnfPrevia, F.trabaja);
		sistema.registrarVotante(F.dniArwen, "Arwen", 50, !F.tieneEnfPrevia, F.trabaja);

		// frodo es el presidente
		// lo registra como votante y le asigna turno
		final Integer numMesaEnfPreexistente = sistema.agregarMesa(F.enfPreexistente, F.dniFrodo);

		final Integer numMesaMayor65 = sistema.agregarMesa(F.mayor65, F.dniBilbo);

		final Integer numMesaGeneral = sistema.agregarMesa(F.general, F.dniGaladriel);

		final Integer numMesaTrabajador = sistema.agregarMesa(F.trabajador, F.dniGandalf);

		System.out.println("Numeros de mesa generados: " + numMesaEnfPreexistente + " " + numMesaMayor65 + " "
				+ numMesaGeneral + " " + numMesaTrabajador);

		// hacer el toString de tupla!
		System.out.println("Turnos generados [Paso 1]: ");
		System.out.println("\t- " + sistema.consultaTurno(F.dniFrodo));
		System.out.println("\t- " + sistema.consultaTurno(F.dniBilbo));
		System.out.println("\t- " + sistema.consultaTurno(F.dniGaladriel));
		System.out.println("\t- " + sistema.consultaTurno(F.dniGandalf));

		System.out.println("\n======================================================");
		System.out.println("Estado Sistema De Turnos: ");
		System.out.println("------------------------- ");
		System.out.println(sistema.toString());
		System.out.println("======================================================\n");

		sistema.registrarVotante(1, "Nombre1", 30, false, false);
		sistema.registrarVotante(2, "Nombre2", 70, false, false);
		sistema.registrarVotante(3, "Nombre3", 30, true, false);
		sistema.registrarVotante(4, "Nombre4", 30, false, true);

		sistema.asignarTurnos();

		// List<Tupla<TipoMesa, Cant Votantes Sin Turno>>
		List<Tupla<String, Integer>> votantesSinTurno = sistema.sinTurnoSegunTipoMesa();

		System.out.println("Cant votantes sin turno :" + votantesSinTurno.size());

		Map<Integer, List<Integer>> MesaEnfPreexistente = sistema.asignadosAMesa(numMesaEnfPreexistente);
		Map<Integer, List<Integer>> MesaMayor65 = sistema.asignadosAMesa(numMesaMayor65);
		Map<Integer, List<Integer>> MesaGeneral = sistema.asignadosAMesa(numMesaGeneral);
		Map<Integer, List<Integer>> MesaTrabajador = sistema.asignadosAMesa(numMesaTrabajador);

		System.out.println("Cant Turnos generados [Paso 2]:");
		System.out.println("\t- " + MesaEnfPreexistente.size());
		System.out.println("\t- " + MesaMayor65.size());
		System.out.println("\t- " + MesaGeneral.size());
		System.out.println("\t- " + MesaTrabajador.size());

		// Franja -> List<Dni>
		Map<Integer, List<Integer>> franjaHoraria1 = sistema.asignadosAMesa(numMesaEnfPreexistente);
		Map<Integer, List<Integer>> franjaHoraria2 = sistema.asignadosAMesa(numMesaMayor65);
		Map<Integer, List<Integer>> franjaHoraria3 = sistema.asignadosAMesa(numMesaGeneral);
		Map<Integer, List<Integer>> franjaHoraria4 = sistema.asignadosAMesa(numMesaTrabajador);

		System.out.println("Cant Turnos generados [Paso 3]:");
		System.out.println("\t- " + franjaHoraria1.size());
		System.out.println("\t- " + franjaHoraria2.size());
		System.out.println("\t- " + franjaHoraria3.size());
		System.out.println("\t- " + franjaHoraria4.size());

		System.out.println("\n======================================================");
		System.out.println("Estado Sistema De Turnos: ");
		System.out.println("------------------------- ");
		System.out.println(sistema.toString());
		System.out.println("======================================================\n");

	}

}
