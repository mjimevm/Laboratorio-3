import java.util.ArrayList;

public class Manager {
    private Controlador controlador;
    // Opciones del Main en Operaciones del Manager

    // Constructor
    public Manager(Controlador controlador) {
        this.controlador = controlador;
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

    // Mostrar citas por estado y médico
    public Cita mostrarCitasPorEstadoYMedico(String estado, Medico medico) {
        for (Cita c : controlador.listarCitasPorEstado(estado)) {
            if (c.getMedico().equals(medico)) {
                return c;
            }
        }
        return null;
    }

}

