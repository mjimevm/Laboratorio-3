import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Manager {
    private Controlador controlador;
    // Opciones del Main en Operaciones del Manager

    // Constructor
    public Manager(Controlador controlador) {
        this.controlador = controlador;
    }

    // 1. Encontrar personal médico disponible
    public Medico asignarMedicoDisponible(String tipo, LocalDate fecha, LocalTime hora) {
        for (Medico m : controlador.getMedicos()) {
            if (m.getClass().getSimpleName().equalsIgnoreCase(tipo) && controlador.disponible(m, fecha, hora)) {
                return m;
            }
        }
        return null;
    }

    // 2. Reagendar citas con conflictos
    public ArrayList<String> calcularNominaPorDepartamento(ArrayList<String> departamentos) {
        ArrayList<String> resultado = new ArrayList<>();
        ArrayList<Medico> medicos = controlador.getMedicos();

        // Obtener lista única de departamentos
        for (Medico m : medicos) {
            String depto = m.getDepartamento();
            if (!departamentos.contains(depto)) {
                departamentos.add(depto);
            }
        }

        // Calcular nómina por departamento
        for (String depto : departamentos) {
            double total = 0.0;
            for (Medico m : medicos) {
                if (m.getDepartamento().equals(depto)) {
                    total += m.calcularSalario(0);
                }
            }
            resultado.add("Departamento: " + depto + " - Total Nómina: " + total);
        }

        return resultado;
    }

    // 3. Detectar y resolver conflictos de citas
    public ArrayList<Cita> detectarConflictos() {
        ArrayList<Cita> conflictos = new ArrayList<>();
        ArrayList<Cita> citas = controlador.getCitas();
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

    //  4. Resolver conflictos de citas
    public void resolverConflictos() {
        ArrayList<Cita> conflictos = detectarConflictos();
        for (Cita c : conflictos) {
            controlador.reagendarCita(c.getId());
        }
    }

    // 5. Analizar utilización del personal médico
    public ArrayList<String> analizarUtilizacionPersonal() {
        ArrayList<Medico> medicos = controlador.getMedicos();
        ArrayList<Cita> citas = controlador.getCitas();
        ArrayList<String> resultado = new ArrayList<>();

        for (Medico m : medicos) {
            int contador = 0;
            for (Cita c : citas) {
                if (c.getMedico().equals(m)) {
                    contador++;
                }
            }
            resultado.add("Medico: " + m.getNombre() + " - Citas: " + contador);
        }
        return resultado;
    }
    
    public Cita mostrarCitasPorEstadoYMedico(String estado, Medico medico) {
        for (Cita c : controlador.listarCitasPorEstado(estado)) {
            if (c.getMedico().equals(medico)) {
                return c;
            }
        }
        return null;
    }

    public String mostrarNominaTotalPorDepartamento(ArrayList<String> departamentos) {
        ArrayList<String> nomina = calcularNominaPorDepartamento(departamentos);
        if (nomina.isEmpty()) {
            return "No hay datos de nómina.";
        }
        StringBuilder sb = new StringBuilder();
        for (String deptoNomina : nomina) {
            sb.append(deptoNomina).append("\n");
        }
        return sb.toString().trim();
    }
}

