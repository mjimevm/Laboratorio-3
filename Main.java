import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Bienvenido al sistema de gestión de médicos.");
        Controlador controlador = new Controlador();
        Scanner teclado = new Scanner(System.in);
        int opcion = 0;

        while (opcion != 8) {
            System.out.println("\n--- Menú Principal ---");
            System.out.println("Seleccione una opción:");
            System.out.println("1. Agregar Médico");
            System.out.println("2. Mostrar Personal Médico");
            System.out.println("3. Calcular Salario");
            System.out.println("4. Agendar Cita");
            System.out.println("5. Mostrar Citas");
            System.out.println("6. Cambiar Cita");
            System.out.println("7. Operaciones de manager");
            System.out.println("8. Salir");
            opcion = teclado.nextInt();

            switch (opcion) {
                case 1:
                    String respuesta = "si";
                    while (respuesta.equalsIgnoreCase("si")) {
                        System.out.print("Ingrese el ID del médico: ");
                        int id = teclado.nextInt();
                        while (!controlador.verificarExistencia(id)) {
                            System.out.println("El ID ya existe. Ingrese un ID diferente.");
                            id = teclado.nextInt();
                        }
                        System.out.print("Ingrese el nombre del médico: ");
                        String nombre = teclado.next();
                        System.out.print("Ingrese el departamento del médico: ");
                        String departamento = teclado.next();
                        System.out.print("Ingrese los años de experiencia del médico: ");
                        int experiencia = teclado.nextInt();
                        System.out.print("Ingrese el salario base del médico: ");
                        int salarioBase = teclado.nextInt();
                        System.out.print("Ingrese el tipo de médico (General, enfermero, radiologo, farmaceutico): ");
                        String tipoMedico = teclado.next();
                        Medico medico;
                        switch (tipoMedico.toLowerCase()) {
                            case "general":
                                System.out.print("Ingrese la especialización del médico general: ");
                                String especializacion = teclado.next();
                                System.out.print("Ingrese la capacidad de pacientes por día: ");
                                int capacidadPacientesPorDia = teclado.nextInt();
                                System.out.print("Ingrese la tarifa por consulta: ");
                                double tarifa = teclado.nextDouble();
                                medico = new DoctorGeneral(id, nombre, departamento, experiencia, salarioBase, especializacion, capacidadPacientesPorDia, tarifa);
                                break;
                            case "enfermero":
                                System.out.print("Ingrese la especialidad del enfermero: ");
                                String tipo = teclado.next();
                                System.out.print("Ingrese la certificacion del enfermero: ");
                                String certificacion = teclado.next();
                                medico = new Enfermero(id, nombre, departamento, experiencia, salarioBase, tipo, certificacion);
                                break;
                            case "radiólogo":
                                System.out.print("Ingrese el tipo de radiólogo: ");
                                String tipo1 = teclado.next();
                                System.out.print("Ingrese la tarifa del radiólogo: ");
                                double tarifa1 = teclado.nextDouble();
                                medico = new Radiologo(id, nombre, departamento, experiencia, salarioBase, tipo1, tarifa1);
                                break;

                            case "farmaceutico":
                                System.out.print("Ingrese la licencia del farmacéutico: ");
                                String licencia = teclado.next();
                                System.out.print("Ingrese el número de prescripciones por día: ");
                                int preescripcionesPorDia = teclado.nextInt();
                                medico = new Farmaceutico(id, nombre, departamento, experiencia, salarioBase, licencia, preescripcionesPorDia);
                                break;
                            default:
                                System.out.println("Tipo de médico no válido. Intente de nuevo.");
                                continue;
                        }
                        controlador.contratarMedico(medico);
                        System.out.print("¿Desea agregar otro médico? (si/no): ");
                        respuesta = teclado.next();
                        while (!respuesta.equalsIgnoreCase("si") && !respuesta.equalsIgnoreCase("no")) {
                            System.out.println("Respuesta no válida. Inténtelo de nuevo (si/no): ");
                            respuesta = teclado.next();
                        }
                    }
                    break;
                case 2:
                    System.out.println("Mostrando todo el personal médico:");
                    if (controlador.getMedicos().isEmpty()) {
                        System.out.println("No hay médicos registrados.");
                        break;
                    }
                    for (Medico m : controlador.getMedicos()) {
                        System.out.println(m);
                    }
                    break;
                case 3:
                    System.out.println("Calcular salario por médico");
                    for (Medico m : controlador.getMedicos()) {
                        System.out.println(m.getId() + ": " + m.getNombre());
                    }
                    System.out.print("Ingrese el ID del médico para calcular su salario: ");
                    int identificador = teclado.nextInt();
                    Medico medico = null;
                    for (Medico m : controlador.getMedicos()) {
                        if (m.getId() == identificador) {
                            medico = m;
                            break;
                        }
                    }
                    if (medico != null) {
                        double var = 0;
                        double salario = medico.calcularSalario(var);
                        System.out.println("El salario del médico " + medico.getNombre() + " es: " + salario);
                    } else {
                        System.out.println("Médico no encontrado.");
                    }

                    break;

                case 4:
                    LocalDateTime ahora = LocalDateTime.now();
                    DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    if (controlador.getMedicos().isEmpty()) {
                        System.out.println("Primero debes agregar médicos.");
                        break;
                    }
                    System.out.print("ID de cita: ");
                    int idCita = teclado.nextInt();
                    System.out.print("Nombre del paciente: ");
                    String paciente = teclado.next();
                    System.out.print("ID del médico: ");
                    int idMed = teclado.nextInt();
                    Medico medicoElegido = null;
                    for (Medico m : controlador.getMedicos()) {
                        if (m.getId() == idMed) {
                            medicoElegido = m;
                            break;
                        }
                    }
                    if (medicoElegido == null) {
                        System.out.println("Médico no encontrado.");
                        break;
                    }
                    System.out.print("Fecha (YYYY-MM-DD): ");
                    String fechaStr = teclado.next();
                    LocalDate fecha = null;
                    try {
                        fecha = LocalDate.parse(fechaStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    } catch (Exception e) {
                        System.out.println("Formato de fecha no válido.");
                        break;
                    }
                    System.out.print("Hora (HH:mm): ");
                    String horaStr = teclado.next();
                    LocalTime hora;
                    try {
                        hora = LocalTime.parse(horaStr, DateTimeFormatter.ofPattern("HH:mm"));
                    } catch (Exception e) {
                        System.out.println("Formato de hora no válido.");
                        break;
                    }
                    System.out.print("Tipo de cita: ");
                    String tipoCita = teclado.next();

                    Cita cita = new Cita(idCita, paciente, medicoElegido, fecha, hora, tipoCita, "PROGRAMADA");
                    controlador.agregarCita(cita);
                    System.out.println("¡Cita agendada!");

                    System.out.println("\nCitas programadas:");
                    for (Cita c : controlador.listarCitasPorEstado("PROGRAMADA")) {
                        System.out.println(c);
                    }

                    break;

                case 5:
                    System.out.print("¿Desea filtrar por estado? (si/no): ");
                    String filtrar = teclado.next();
                    if (filtrar.equalsIgnoreCase("si")) {
                        System.out.print("Ingrese el estado a filtrar: ");
                        String estadoFiltro = teclado.next();
                        for (Cita c : controlador.listarCitasPorEstado(estadoFiltro)) {
                            System.out.println(c);
                        }
                    } else {
                        for (Cita c : controlador.listarCitasPorEstado("")) {
                            System.out.println(c);
                        }
                    }
                    break;

                case 6:
                    System.out.print("Ingrese el ID de la cita a reagendar: ");
                    int idCitaCambiar = teclado.nextInt();
                    boolean resultado = controlador.reagendarCita(idCitaCambiar);
                    if (resultado) {
                        System.out.println("Cita reagendada exitosamente.");
                        // historial
                        for (String evento : controlador.obtenerHistorialReagendamientos().get(idCitaCambiar)) {
                            System.out.println(evento);
                        }
                    } else {
                        System.out.println("No se pudo reagendar la cita (no existe o sin disponibilidad).");
                    }
                    break;
                case 7:
                    System.out.println("\nOperaciones de manager:");
                    System.out.println("Seleccione una opción:");
                    System.out.println("1. Encontrar personal disponible");
                    System.out.println("2. Reportes de Nómina por departamento");
                    System.out.println("3. Gestión de Conflictos de Horarios");
                    System.out.println("4. Análisis de Utilización");
                    System.out.println("5. Reportes de personal");
                    System.out.println("6. Reporte de Citas");
                    System.out.println("7. Análisis financiero");
                    System.out.println("8. Historial de Reagendamientos");
                    System.out.println("9. Volver al menú principal");
                    System.out.println("Seleccione una opción:");
                    int opcionManager = teclado.nextInt();
                    Manager manager = new Manager(controlador);

                    switch (opcionManager) {
                        case 1:
                        // Encontrar personal Disponible por tipo y horario
                            System.out.print("Tipo de médico (General, enfermero, radiólogo, farmacéutico): ");
                            String tipoMed = teclado.next();
                            System.out.print("Fecha (YYYY-MM-DD): ");
                            LocalDate fechaDisp = LocalDate.parse(teclado.next());
                            System.out.print("Hora (HH:mm): ");
                            LocalTime horaDisp = LocalTime.parse(teclado.next());
                            for (Medico m : controlador.getMedicos()) {
                                if (m.getClass().getSimpleName().equalsIgnoreCase(tipoMed)) {
                                    Medico disponible = controlador.buscarMedicoDisponible(m.getClass(), fechaDisp, horaDisp);
                                    if (disponible != null) {
                                        System.out.println("Médico disponible encontrado: " + disponible);
                                    } else {
                                        System.out.println("No hay médicos disponibles de ese tipo en la fecha y hora indicadas.");
                                    }
                                    break;
                                }
                            }
                            break;
                        case 2:
                        // Reportes de Nómina por departamento
                            System.out.println("Nómina por departamento:");
                            manager.mostrarNominaTotalPorDepartamento();
                            break;
                        case 3:
                        // Gestión de Conflictos de Horarios
                            manager.detectarConflictos();
                            break;
                        case 4:
                        // Análisis de Utilización
                            manager.analizarUtilizacionPersonal();
                            break;
                        case 5:
                        // Reportes de personal
                            manager.mostrarPersonal();
                            break;
                        case 6:
                        // Reporte de Citas
                            manager.mostrarCitasPorEstadoYMedico("", null);
                            break;
                        case 7:
                        // Análisis financiero
                            manager.mostrarNominaTotalPorDepartamento();
                            break;
                        case 8:
                        // Historial de Reagendamientos
                        for (int id : controlador.obtenerHistorialReagendamientos().keySet()) {
                            System.out.println("Cita ID: " + id);
                            for (String evento : controlador.obtenerHistorialReagendamientos().get(id)) {
                                System.out.println("  " + evento);
                            }
                        }
                            break;
                        case 9:
                            System.out.println("Volviendo al menú principal.");
                            break;
                        default:
                            System.out.println("Opción no válida. Intente de nuevo.");
                            break;
                    }
                    break;
                case 8:
                    System.out.println("Saliendo del sistema. ¡Hasta luego!");
                    break;

                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
                    break;
            }
        }
    }
}