public class Enfermero extends Medico {

    private String tipo;
    private String certificacion;

    public Enfermero(int id, String nombre, String departamento, int experiencia, int salarioBase, String tipo, String certificacion) {
        super(id, nombre, departamento, experiencia, salarioBase);
        this.tipo = tipo;
        this.certificacion = certificacion;
    }
    public String getTipo(){
        return tipo;
    }
    public String getCertificacion() {
        return certificacion;
    }
    @Override
    public double calcularSalario(double bono) {
        double salarioFinal = this.salarioBase + bono;
        return salarioFinal;
    }
}