public class DoctorGeneral extends Medico {

    private String especializacion;
    private int pacientesPorDia;
    private double tarifa;

    public DoctorGeneral(int id, String nombre, String departamento, int experiencia, double salarioBase, String especializacion, int pacientesPorDia, double tarifa) {
        super(id, nombre, departamento, experiencia, salarioBase);
        this.especializacion = especializacion;
        this.pacientesPorDia = pacientesPorDia;
        this.tarifa = tarifa;
    }
    public String getEspecializacion(){
        return especializacion;
    }
    public int getPacientesPorDia() {
        return pacientesPorDia;
    }
    public double getTarifa() {
        return tarifa;
    }
    @Override
    public double calcularSalario(double pacientesAtendidos) {
        double salarioFinal = this.salarioBase + (pacientesAtendidos * this.tarifa);
        return salarioFinal;
    }
}