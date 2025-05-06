
package code;

import java.util.Scanner;

public class Software {

    public static void main(String[] args) throws Exception {
        try (Scanner scanner = new Scanner(System.in)) {
            int tipo = -1;
            int opcion = -1;

            while (true) {
                System.out.println("\nSeleccione el tipo de cifrado:");
                System.out.println("1. Simetrico");
                System.out.println("2. Asimetrico");
                System.out.println("0. Salir");
                System.out.print("Ingrese el numero de la opcion: ");

                if (!scanner.hasNextInt()) {
                    System.out.println("Por favor, ingrese un numero valido.");
                    scanner.next();
                    continue;
                }

                tipo = scanner.nextInt();

                if (tipo == 0) {
                    System.out.println("Saliendo del programa...");
                    break;
                }

                switch (tipo) {
                    case 1 -> { // Simetrico
                        while (true) {
                            System.out.println("\nSeleccione el cifrado simetrico a utilizar:");
                            System.out.println("1. AES");
                            System.out.println("2. Blowfish");
                            System.out.println("3. DES");
                            System.out.println("4. 3DES");
                            System.out.println("5. RC4");
                            System.out.println("0. Volver");
                            System.out.print("Ingrese el numero de la opcion: ");

                            if (!scanner.hasNextInt()) {
                                System.out.println("Por favor, ingrese un numero valido.");
                                scanner.next();
                                continue;
                            }

                            opcion = scanner.nextInt();

                            if (opcion == 0) break;

                            switch (opcion) {
                                case 1 -> code.cypher.symtrcl.aes.test();
                                case 2 -> code.cypher.symtrcl.blowfish.test();
                                case 3 -> code.cypher.symtrcl.des.test();
                                case 4 -> code.cypher.symtrcl.tdes.test();
                                case 5 -> code.cypher.symtrcl.rc4.test();
                                default -> System.out.println("Opcion no valida.");
                            }
                        }
                    }
                    case 2 -> { // Asimetrico
                        while (true) {
                            System.out.println("\nSeleccione el cifrado asimetrico a utilizar:");
                            System.out.println("1. RSA");
                            // Puedes agregar mas como ElGamal, ECC, etc.
                            System.out.println("0. Volver");
                            System.out.print("Ingrese el numero de la opcion: ");

                            if (!scanner.hasNextInt()) {
                                System.out.println("Por favor, ingrese un numero valido.");
                                scanner.next();
                                continue;
                            }

                            opcion = scanner.nextInt();

                            if (opcion == 0) break;

                            switch (opcion) {
                                case 1 -> code.cypher.asymm.rsa.test();
                                default -> System.out.println("Opcion no valida.");
                            }
                        }
                    }
                    default -> System.out.println("Opcion no valida.");
                }
            }
        }
    }
}
