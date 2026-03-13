package jic.argumentos;

import java.util.ArrayList;

public class Argumento {

    /**
     * Éste método procesa la linea de comandos y los entrega como un arreglo.
     * Es necesario hacerlo como método, ya que procesa la linea con o sin ""
     * @param lineaComando
     * @return los argumentos como array
     * @throws IllegalArgumentException 
     */
    public static String[] getArgumentos(String lineaComando) throws IllegalArgumentException {
        if (lineaComando.contains("\"")) {
            if (tieneComillasPar(lineaComando)) {
                // linea etsa OK con comillas pares
                
                // Divido el String en comillas
                String[] auxComillas = lineaComando.split("\"");
                // Esta va a ser la lista final de argumentos que retornaré
                ArrayList<String> argumentos = new ArrayList<>();
                
                /*
                Entonces en la siguiente linea
                promtp -p "asdasd" -2 -2 "asda"	
                
                Quedaría: 
                    promtp -p   --> índice 0
                    asdasd      --> índice 1 <-
                     -2 -2      --> índice 2
                    asda        --> índice 3 <-
                
                De esta forma descubri en que los índices impares, 
                queda el contenido de las comillas
                */
               
                for (int i = 0; i < auxComillas.length; i++) {
                    if (i % 2 == 0) {
                        // si el índice es par, debo separarlos por espacio 
                        String[] split = auxComillas[i].split(" ");
                        for (String palabra : split) {
                            if (!palabra.isEmpty()) {
                                argumentos.add(palabra);
                            }
                        }
                    } else {
                        // si el índice es impar, es el contenido dentro 
                        // de la comilla
                        argumentos.add(auxComillas[i]);
                    }
                }

                /* Transformación de ArrayList a vector */
                String[] args = new String[argumentos.size()];
                
                return argumentos.toArray(args);
                /* Transformación de ArrayList a vector */

            } else {
                // tiene comillas impares, por ende hay un error de ingreso
                throw new IllegalArgumentException();
            }
        } else {
            // la linea de comando no contiene ""
            return lineaComando.split(" ");
        }
    }

    /**
     * Este método solo dice si la linea comando tiene comillas pares
     * @param lineaComando
     * @return 
     */
    private static boolean tieneComillasPar(String lineaComando) {
        int cont = 0;

        for (char c : lineaComando.toCharArray()) {
            if (c == '\"') {
                cont++;
            }
        }

        return cont % 2 == 0; // está ok si el contador es par
    }

//    public static void main(String[] args) {
//        while (true) {
//            String lineaComando = new java.util.Scanner(System.in).nextLine();
//
//            try {
//                for (String arg : Argumento.getArgumentos(lineaComando)) {
//                    System.out.println(arg);
//                }
//            } catch (IllegalArgumentException e) {
//                System.out.println("Argumentos ilegales");
//            }
//        }
//    }
}
