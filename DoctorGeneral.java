// DoctorGeneral hereda de Medico
public class DoctorGeneral extends Medico {

    // Atributos específicos
    private String especializacion;
    private int pacientesPorDia;
    private double tarifa;

    // Constructor
    public DoctorGeneral(int id, String nombre, String departamento, int experiencia, double salarioBase, String especializacion, int pacientesPorDia, double tarifa) {
        super(id, nombre, departamento, experiencia, salarioBase);
        this.especializacion = especializacion;
        this.pacientesPorDia = pacientesPorDia;
        this.tarifa = tarifa;
    }
    // Getters y otros métodos
    public String getEspecializacion(){
        return especializacion;
    }
    public int getPacientesPorDia() {
        return pacientesPorDia;
    }
    public double getTarifa() {
        return tarifa;
    }
    // Polimorfismo 
    @Override
    public double calcularSalario(double pacientesAtendidos) {
        double salarioFinal = this.salarioBase + (pacientesAtendidos * this.tarifa);
        return salarioFinal;
    }
}