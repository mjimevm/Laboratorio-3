import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Manager {
    private Controlador controlador;

    public Manager(Controlador controlador) {
        this.controlador = controlador;
    }

    public Medico asignarMedicoDisponible(String tipo, LocalDate fecha, LocalTime hora) {
        for (Medico m : controlador.getMedicos()) {
            if (m.getClass().getSimpleName().equalsIgnoreCase(tipo) && controlador.disponible(m, fecha, hora)) {
                return m;
            }
        }
        return null;
    }

    public Map<String, Double> calcularNominaPorDepartamento() {
        Map<String, Double> nomina = new HashMap<>();
        for (Medico m : controlador.getMedicos()) {
            double salario = m.calcularSalario(0); // Puedes pedir datos adicionales según tipo
            nomina.put(m.getDepartamento(),
                nomina.getOrDefault(m.getDepartamento(), 0.0) + salario);
        }
        return nomina;
    }

    public List<Cita> detectarConflictos() {
        List<Cita> conflictos = new ArrayList<>();
        List<Cita> citas = controlador.getCitas();
        for (int i = 0; i < citas.size(); i++) {
            for (int j = i+1; j < citas.size(); j++) {
                if (citas.get(i).getMedico().equals(citas.get(j).getMedico()) &&
                        citas.get(i).getFecha().equals(citas.get(j).getFecha()) &&
                        citas.get(i).getHora() == citas.get(j).getHora() &&
                        !citas.get(i).getEstado().equals("CANCELADA") &&
                        !citas.get(j).getEstado().equals("CANCELADA")) {
                    conflictos.add(citas.get(i));
                    conflictos.add(citas.get(j));
                }
            }
        }
        return conflictos;
    }

    public void resolverConflictos() {
        List<Cita> conflictos = detectarConflictos();
        for (Cita c : conflictos) {
            controlador.reagendarCita(c.getId());
        }
    }

    public Map<Medico, Integer> analizarUtilizacionPersonal() {
        Map<Medico, Integer> conteo = new HashMap<>();
        for (Cita c : controlador.getCitas()) {
            if (!conteo.containsKey(c.getMedico()))
                conteo.put(c.getMedico(), 0);
            conteo.put(c.getMedico(), conteo.get(c.getMedico()) + 1);
        }
        return conteo;
    }

    public Medico mostrarPersonal() {
        for (Medico m : controlador.getMedicos()) {
            return m;
        }
        return null;
    }

    public Cita mostrarCitasPorEstadoYMedico(String estado, Medico medico) {
        for (Cita c : controlador.listarCitasPorEstado(estado)) {
            if (c.getMedico().equals(medico)) {
                return c;
            }
        }
        return null;
    }

    public String mostrarNominaTotalPorDepartamento() {
        Map<String, Double> nomina = calcularNominaPorDepartamento();
        for (String depto : nomina.keySet()) {
            return "Departamento: " + depto + " - Total Nómina: " + nomina.get(depto);
        }
        return "No hay datos de nómina.";
    }

    public void mostrarHistorialReagendamientos() {
        Map<Integer, ArrayList<String>> historial = controlador.obtenerHistorialReagendamientos();
        for (Map.Entry<Integer, ArrayList<String>> entry : historial.entrySet()) {
            System.out.println("Cita ID: " + entry.getKey());
            for (String evento : entry.getValue()) {
                System.out.println("  " + evento);
            }
        }
    }
}

