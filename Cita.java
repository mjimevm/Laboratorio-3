import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Cita {
    private int id;
    private String nombrePaciente;
    private Medico medico;
    private LocalDate fecha;
    private LocalTime hora;
    private String tipo;
    private String estado;
    private List<String> historial = new ArrayList<>();

    public Cita(int id, String nombre, Medico m, LocalDate fecha, LocalTime hora, String tipo, String estado) {
        this.id = id;
        this.nombrePaciente = nombre;
        this.medico = m;
        this.fecha = fecha;
        this.hora = hora;
        this.tipo = tipo;
        this.estado = estado;
        historial.add("Creada: " + fecha + " a las " + hora + ":00");
    }
    public int getId() { 
        return id; 
    }

    public String getNombrePaciente() { 
        return nombrePaciente; 
    }

    public Medico getMedico() { 
        return medico; 
    }

    public LocalDate getFecha() { 
        return fecha; 
    }

    public LocalTime getHora() { 
        return hora; 
    }

    public String getTipo() { 
        return tipo; 
    }

    public String getEstado() { 
        return estado; 
    }

    public List<String> getHistorial() { 
        return historial; 
    }

    public void setEstado(String nuevoEstado) {
        this.estado = nuevoEstado;
        historial.add("Cambio de estado a: " + nuevoEstado);
    }

    public String reagendar(LocalDate nuevaFecha, LocalTime nuevaHora, Medico nuevoMedico) {
        historial.add("Reagendada de " + fecha + " " + hora + ":00 a " + nuevaFecha + " " + nuevaHora + ":00 con " + nuevoMedico.getNombre());
        this.fecha = nuevaFecha;
        this.hora = nuevaHora;
        this.medico = nuevoMedico;
        setEstado("REAGENDADA");
        return "Cita #" + id + " reagendada a " + nuevaFecha + " " + nuevaHora + ":00 con médico: " + nuevoMedico.getNombre();
    }
    @Override
    public String toString() {
        return "[Cita #" + id + "] Paciente: " + nombrePaciente + ", Médico: " + medico.getNombre() + ", Fecha: " + fecha + ", Hora: " + hora + ":00, Tipo: " + tipo + ", Estado: " + estado;
    }
}