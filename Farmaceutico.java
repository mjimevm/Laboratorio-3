public class Farmaceutico extends Medico {

    private String licencia;
    private int prescripcionesPorDia;

    public Farmaceutico(int id, String nombre, String departamento, int experiencia, double salarioBase, String licencia, int prescripcionesPorDia) {
        super(id, nombre, departamento, experiencia, salarioBase);
        this.licencia = licencia;
        this.prescripcionesPorDia = prescripcionesPorDia;
    }
    public String getTipo(){
        return licencia;
    }
    public int getPrescripcionesPorDia() {
        return prescripcionesPorDia;
    }
    @Override
    public double calcularSalario(double comision) {
        double salarioFinal = this.salarioBase + comision;
        return salarioFinal;
    }
}