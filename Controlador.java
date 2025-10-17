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
    public Medico buscarMedicoDisponible(Class tipo, LocalDate fecha, LocalTime hora) {
        for (Medico m : medicos) {
            if (tipo.isInstance(m) && disponible(m, fecha, hora)) {
                return m;
            }
        }
        return null;
    }

    public boolean disponible(Medico m, LocalDate fecha, LocalTime hora) {
        for (Cita c : citas) {
            if (!c.getMedico().equals(m)) {
                return false;
            } else if (!c.getFecha().equals(fecha)) {
                return false;
            } else if (!c.getHora().equals(hora)) {
                return false;
            } else if (c.getEstado().equals("CANCELADA")) {
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
                LocalTime hora = c.getHora();
                Medico medico = c.getMedico();
                for (LocalTime h = hora.plusHours(1); h.isBefore(LocalTime.MAX); h = h.plusHours(1)) {
                    if (disponible(medico, fecha, h)) {
                        c.reagendar(fecha, h, medico);
                        return true;
                    }
                }
                Medico otro = buscarMedicoDisponible(medico.getClass(), fecha, hora);
                if (otro != null && !otro.equals(medico)) {
                    c.reagendar(fecha, hora, otro);
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
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
