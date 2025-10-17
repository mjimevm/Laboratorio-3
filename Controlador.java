import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Controlador {
    private ArrayList<Medico> medicos = new ArrayList<>();
    private ArrayList<Cita> citas = new ArrayList<>();
    private ArrayList<String> historial = new ArrayList<>();
    private ArrayList<String> departamentos = new ArrayList<>();

    public void contratarMedico(Medico m) {
        medicos.add(m);
    }

    public void agregarCita(Cita c) {
        citas.add(c);
    }

    public ArrayList<Cita> listarCitasPorEstado(String estado) {
        ArrayList<Cita> resultado = new ArrayList<>();
        for (Cita c : citas) {
            if (c.getEstado().equals(estado)) {
                resultado.add(c);
            }
        }
        return resultado;
    }    
    public ArrayList<String> getDepartamentos() {
        return departamentos;
    }
    public void setDepartamentos(ArrayList<String> departamentos) {
        this.departamentos = departamentos;
    }
    public ArrayList<String> listarDepartamentos() {
        ArrayList<String> resultado = new ArrayList<>();
        for (Medico m : medicos) {
            String dep = m.getDepartamento();
            if (dep != null && !resultado.contains(dep)) {
                resultado.add(dep);
            }
        }
        return resultado;
    }


    public ArrayList<String> obtenerHistorialReagendamientos(int idCita) {
        ArrayList<String> historial = new ArrayList<>();
        for (Cita c : citas) {
            if (c.getId() == idCita && c.getHistorial().size() > 0) {
                historial.add("Cita ID: " + c.getId() + " -> " + c.getHistorial().toString());
            }
        }
        return historial;
    }
    public Medico buscarMedicoDisponible(LocalDate fecha, LocalTime hora) {
        for (Cita c : citas) {
            if (disponible(c.getMedico(), fecha, hora)) {
                return c.getMedico();
            }
        }
        return null;
    }

    public boolean disponible(Medico m, LocalDate fecha, LocalTime hora) {
        for (Cita c : citas) {
            if (c.getMedico().equals(m) && c.getFecha().equals(fecha) && c.getHora().equals(hora) && !c.getEstado().equals("CANCELADA")) {
                return false; 
            }
        }
        return true; 
    }

    public ArrayList<Medico> getMedicos() {
        return medicos;
    }

    public boolean reagendarCita(int idCita) {
        for (Cita c : citas) {
            if (c.getId() == idCita) {
                LocalDate fecha = c.getFecha();
                LocalTime horaActual = c.getHora();
                Medico medico = c.getMedico();

                // Busca otro horario en el mismo día, de 8:00 a 18:00 (excepto el actual)
                for (int h = 8; h <= 18; h++) {
                    LocalTime nuevoHorario = LocalTime.of(h, 0);
                    if (nuevoHorario.equals(horaActual)) continue;
                    if (disponible(medico, fecha, nuevoHorario)) {
                        c.reagendar(fecha, nuevoHorario, medico);
                        return true;
                    }
                }

                // Si no hay horario libre, busca otro médico disponible en ese horario
                for (Medico m : medicos) {
                    if (!m.equals(medico) && disponible(m, fecha, horaActual)) {
                        c.reagendar(fecha, horaActual, m);
                        return true;
                    }
                }
                // Si no hay opciones, no reagenda
                return false;
            }
        }
        return false; // No existe la cita
    }

    public boolean verificarExistencia(int id) {
        for (Cita c : citas) {
            if (c.getId() == id) {
                return false;
            }
        }
        for (Medico m : medicos) {
            if (m.getId() == id) {
                return false;
            }
        }
        return true;
    }

    public ArrayList<String> getHistorial() {
        return historial;
    }
    public String agregarEventoHistorial(Cita cita) {
        historial.add("Cita ID: " + cita.getId() + " - " + cita.getEstado());
        return "Evento agregado al historial.";
    }
    public ArrayList<Cita> getCitas() {
        return citas;
    }
    public void citaCompletada(Cita cita) {
         cita.setEstado("COMPLETADA");
         agregarEventoHistorial(cita);
    }
    public void citaCancelada(Cita cita) {
        cita.setEstado("CANCELADA");
        agregarEventoHistorial(cita);
    }
    public void citaEnProgreso(Cita cita) {
        if (cita.getHora() == LocalTime.now() && cita.getFecha().isEqual(LocalDate.now())) {
            cita.setEstado("EN PROGRESO");
            agregarEventoHistorial(cita);
        }
    }
}
