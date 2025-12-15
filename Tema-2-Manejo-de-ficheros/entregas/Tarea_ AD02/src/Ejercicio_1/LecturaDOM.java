package JAXB;

/*
    Se trata de listar los datos contenidos en un fichero XML con
    información de empleados y departamentos, utiliza la API DOM
    y lista los números y, nombres y localidad de los departamentos
    y a continuación, apellido, oficio, y salario y comisión de los
    empleados de dicho departamento.
 */


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class LecturaDOM {
    // Método auxiliar para obtener el texto de un elemento
    private static String obtenerTexto(Element elemento, String tagName) {
        return elemento.getElementsByTagName(tagName)
                .item(0)
                .getTextContent();
    }


    public static void main(String[] args) {

        try {
            // DocumentBuilder
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();

            // Parse del .XML
            Document doc = db.parse("Entregas/src/DepartamentosEmpleados.xml");

            // Elemento raiz <datos>
            Element datos = doc.getDocumentElement();

            // Nodo <departamentos>
            Element nodoDepartamentos = (Element) datos.getElementsByTagName("departamentos").item(0);

            // Nodos <DEP_ROW>
            NodeList departamentos = nodoDepartamentos.getElementsByTagName("DEP_ROW");
            int numDepartamentos = departamentos.getLength();

            // Nodo <empleados>
            Element nodoEmpleados = (Element) datos.getElementsByTagName("empleados").item(0);

            // Nodos EMP_ROW
            NodeList empleados = nodoEmpleados.getElementsByTagName("EMP_ROW");
            int numEmpleados = empleados.getLength();

            for (int i = 0; i < numDepartamentos; i++) {
                Element departamento = (Element) departamentos.item(i);
                String numeroDepartamento = obtenerTexto(departamento, "DEPT_NO");
                String nombreDepartamento = obtenerTexto(departamento, "DNOMBRE");
                String localidadDepartamento = obtenerTexto(departamento, "LOC");

                System.out.printf("""
                                Departamento %d:
                                    Número departamento: %s
                                    Nombre departamento: %s
                                    Localización departamento: %s
                                
                                """,
                        i,
                        numeroDepartamento,
                        nombreDepartamento,
                        localidadDepartamento);

                //  Iteramos sobre la lista de empleados para encontrar los del departamento
                for (int j = 0; j < numEmpleados; j++) {
                    Element empleado = (Element) empleados.item(j);
                    String departamentoEmpleado = empleado.getElementsByTagName("DEPT_NO")
                            .item(0)
                            .getTextContent();

                    if (numeroDepartamento.equals(departamentoEmpleado)) {

                        // Extraer apellido, oficio, y salario y comisión de los empleados del departamento
                        String apellidoEmpleado = obtenerTexto(empleado, "APELLIDO");
                        String oficioEmpleado = obtenerTexto(empleado, "OFICIO");
                        String salarioEmpleado = obtenerTexto(empleado, "SALARIO");
                        String comisionEmpleado = obtenerTexto(empleado, "COMISION");
                        System.out.printf("""
                                                Apellido: %s
                                                Oficio: %s
                                                Salario: %s
                                                Comsión: %s
                                        
                                        """,
                                apellidoEmpleado,
                                oficioEmpleado,
                                salarioEmpleado,
                                comisionEmpleado);
                    }
                }
            }

        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new RuntimeException(e);
        }
    }
}


