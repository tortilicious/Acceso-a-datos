package Tarea_;

/*
Se trata de crear un fichero XML con información de empleados y departamentos,
partiendo de los datos que se encuentran en el fichero DepartamentosEmpleados.xml.
El proceso es el siguiente:

    Acceder con DOM al fichero para leer los datos.
    Escribir con JAXB un nuevo fichero DatosEmpresa.xml
    con los mismos datos de empleados y departamentos.
 */

import UT_02.AD_02.Ejercicio_2.JAXB.Departamento;
import UT_02.AD_02.Ejercicio_2.JAXB.Empleado;
import UT_02.AD_02.Ejercicio_2.JAXB.Empresa;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {
    // Método auxiliar para obtener el texto de un elemento
    private static String obtenerTexto(Element elemento, String tagName) {
        return elemento.getElementsByTagName(tagName)
                .item(0)
                .getTextContent();
    }

    public static void main(String[] args) {


        try {
            // Document Builder
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();

            // Parse del .XML
            Document doc = db.parse("src/DepartamentosEmpleados.xml");

            // Elemento raíz <datos>
            Element datos = doc.getDocumentElement();

            // === ACCESO A NODOS DEPARTAMENTO ===

            // Nodo <departamentos>
            Element nodoDepartamentos = (Element) datos.getElementsByTagName("departamentos")
                    .item(0);

            // Nodo <TITULO>
            Element nodoTitulo = (Element) nodoDepartamentos.getElementsByTagName("TITULO").item(0);

            // Lista nodos <DEP_ROW>
            NodeList nodosDepartamento = nodoDepartamentos.getElementsByTagName("DEP_ROW");
            int numDepartamentos = nodosDepartamento.getLength();

            // === ACCESO A NODOS EMPLEADO ===

            // Nodo <empleados>
            Element nodoEmpleados = (Element) datos.getElementsByTagName("empleados")
                    .item(0);

            // Lista nodos <EMP_ROW>
            NodeList nodosEmpleado = nodoEmpleados.getElementsByTagName("EMP_ROW");
            int numEmpleados = nodosEmpleado.getLength();

            // Recorrer .XML original y crear objetos JAXB

            Empresa empresa = new Empresa();
            empresa.setTitulo(nodoTitulo.getTextContent());
            List<Departamento> departamentos = new ArrayList<>();

            // Iteramos sobre la lista de departamentos
            for (int i = 0; i < numDepartamentos; ++i) {

                // Creamos un objeto JAXB para cada departamento
                Element departamentoDOM = (Element) nodosDepartamento.item(i);
                Departamento departamentoJAXB = new Departamento();

                // Asignamos los atributos extraídos del DOM
                departamentoJAXB.setId(obtenerTexto(departamentoDOM, "DEPT_NO"));
                departamentoJAXB.setLocalizacion(obtenerTexto(departamentoDOM, "LOC"));
                departamentoJAXB.setNombre(obtenerTexto(departamentoDOM, "DNOMBRE"));

                // Iteramos sobre la lista de empleados
                for (int j = 0; j < numEmpleados; ++j) {
                    Element empleadoDOM = (Element) nodosEmpleado.item(j);

                    // Comprobamos que el departamento del empleado coincide con el del departamento del DOM
                    String idDepartamento = obtenerTexto(departamentoDOM, "DEPT_NO");
                    String idDepartamentoEmpleado = obtenerTexto(empleadoDOM, "DEPT_NO");

                    if (idDepartamento.equals(idDepartamentoEmpleado)) {
                        // Creamos un objeto JAXB para cada empleado
                        Empleado empleadoJAXB = new Empleado();

                        // Asignamos los valores del empleado del DOM al objeto JAXB
                        empleadoJAXB.setNumero(obtenerTexto(empleadoDOM, "EMP_NO"));
                        empleadoJAXB.setApellido(obtenerTexto(empleadoDOM, "APELLIDO"));
                        empleadoJAXB.setOficio(obtenerTexto(empleadoDOM, "OFICIO"));
                        empleadoJAXB.setDir(obtenerTexto(empleadoDOM, "DIR"));
                        empleadoJAXB.setFechaAlta(obtenerTexto(empleadoDOM, "FECHA_ALT"));
                        empleadoJAXB.setSalario(obtenerTexto(empleadoDOM, "SALARIO"));
                        empleadoJAXB.setComision(obtenerTexto(empleadoDOM, "COMISION"));

                        // Agregamos el empleado JAXB al departamento JAXB
                        departamentoJAXB.getEmpleados().add(empleadoJAXB);
                    }
                }
                // Agregamos el departamento JAXB a la lista de departamentos del objeto empresa JAXB
                departamentos.add(departamentoJAXB);
            }

            // Agregamos la lista de departamentos al objeto empresa JAXB
            empresa.setDepartamentos(departamentos);

            // Escribimos .XML con JAXB

            // 1.- Context - El intérprete
            JAXBContext context = JAXBContext.newInstance(Empresa.class);

            // 2.- Marshaller - El generador
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            marshaller.marshal(empresa, new File("src/Ejercicio_2/solucionJAXB.xml"));

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();  // ← Añade esto para ver el stack trace completo
        }

    }
}
