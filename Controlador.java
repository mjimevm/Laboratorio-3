import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Controlador {
    private ArrayList<Medico> medicos = new ArrayList<>();
    private ArrayList<Cita> citas = new ArrayList<>();

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

    public Map<Integer, ArrayList<String>> obtenerHistorialReagendamientos() {
        Map<Integer, ArrayList<String>> historial = new HashMap<>();
        for (Cita c : citas) {
            if (c.getHistorial().size() > 1) {
                historial.put(c.getId(), new ArrayList<>(c.getHistorial()));
            }
        }
        return historial;
    }
    public Medico buscarMedicoDisponible(Class tipo, String fecha, int hora) {
        for (Medico m : medicos) {
            if (tipo.isInstance(m) && disponible(m, fecha, hora)) {
                return m;
            }
        }
        return null;
    }

    private boolean disponible(Medico m, String fecha, int hora) {
        for (Cita c : citas) {
            if (c.getMedico().equals(m) && c.getFecha().equals(fecha) && c.getHora() == hora && !c.getEstado().equals("CANCELADA")) {
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
                String fecha = c.getFecha();
                int hora = c.getHora();
                Medico medico = c.getMedico();
                for (int h = hora + 1; h < 24; h++) {
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
        for (Medico m : medicos) {
            if (m.getId() == id) {
                return false;
            }
        }
        return true;
    }
}
