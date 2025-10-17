import java.lang.reflect.Array;
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

    // 2. Reportes de nómina por departamento
    public ArrayList<String> calcularNominaPorDepartamento() {
        ArrayList<String> resultado = new ArrayList<>();
        ArrayList<Medico> medicos = controlador.getMedicos();
        ArrayList<String> departamentos = controlador.getDepartamentos();

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

    // 3. Detectar conflictos (Se puede resolver mediante el reagendamiento de citas)
    public String detectarConflictos() {
        ArrayList<Cita> citas = controlador.getCitas();
        for (Cita cita : citas) {
            for (Cita otraCita : citas) {
                if (!cita.equals(otraCita) || cita.getMedico().equals(otraCita.getMedico()) || cita.getFecha().equals(otraCita.getFecha()) || cita.getHora().equals(otraCita.getHora())) {
                    return "Conflicto detectado entre las citas ID: " + cita.getId() + " y ID: " + otraCita.getId() + ". Se recomienda reagendar una de las citas.";
                }
            }
        }
        return "No hay conflictos encontrados";
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
    
    // 6. Mostrar citas por estado y médico
    public Cita mostrarCitasPorEstadoYMedico(String estado, Medico medico) {
        for (Cita c : controlador.listarCitasPorEstado(estado)) {
            if (c.getMedico().equals(medico)) {
                return c;
            }
        }
        return null;
    }

    // 7. Mostrar nómina total por departamento
    public void mostrarNominaTotalPorDepartamento() {
        ArrayList<String> nomina = calcularNominaPorDepartamento();
        for (String n : nomina) {
            System.out.println(n);
        }
    }

}

