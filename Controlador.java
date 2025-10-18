import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Controlador {
    // Atributos
    private ArrayList<Medico> medicos = new ArrayList<>();
    private ArrayList<Cita> citas = new ArrayList<>();
    private ArrayList<String> historial = new ArrayList<>();
    private ArrayList<String> departamentos = new ArrayList<>();

    // Agrega un médico a la lista de médicos
    public void contratarMedico(Medico m) {
        medicos.add(m);
    }

    // Agrega una cita a la lista de citas
    public void agregarCita(Cita c) {
        citas.add(c);
    }

    // Lista citas por su estado en el que se encuentra
    public ArrayList<Cita> listarCitasPorEstado(String estado) {
        ArrayList<Cita> resultado = new ArrayList<>();
        for (Cita c : citas) {
            if (c.getEstado().equals(estado)) {
                resultado.add(c);
            }
        }
        return resultado;
    } 
    // Getters y Setters
    public ArrayList<String> getDepartamentos() {
        return departamentos;
    }
    public void setDepartamentos(ArrayList<String> departamentos) {
        this.departamentos = departamentos;
    }

    // Obtener historial de reagendamientos de una cita específica
    public ArrayList<String> obtenerHistorialReagendamientos(int idCita) {
        ArrayList<String> historial = new ArrayList<>();
        for (Cita c : citas) {
            if (c.getId() == idCita && c.getHistorial().size() > 0) {
                historial.add("Cita ID: " + c.getId() + " -> " + c.getHistorial().toString());
            }
        }
        return historial;
    }
    // Verificar disponibilidad de un médico en una fecha y hora específicas
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

    // Reagendar una cita
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
                return false;
            }
        }
        return false; 
    }

    // Verificar si existe tal cita o medico por ID
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

    public String agregarEventoHistorial(Cita cita) {
        historial.add("Cita ID: " + cita.getId() + " - " + cita.getEstado());
        return "Evento agregado al historial.";
    }
    public ArrayList<Cita> getCitas() {
        return citas;
    }
    
    // Setea el estado de una cita a CANCELADA y agrega el evento al historial
    public void citaCancelada(Cita cita) {
        cita.setEstado("CANCELADA");
        agregarEventoHistorial(cita);
    }
}
