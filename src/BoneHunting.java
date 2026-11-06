import java.util.Scanner;

public class BoneHunting {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);

        // ---------- CONSTANTES ----------
        final int VIDA_MAXIMA = 130;
        final int MAX_POCIONES = 5;        // maximo de pociones que se pueden llevar
        final int CURACION_POCION = 50;
        final int PRECIO_POCION = 20;
        final int CURACION_HOGUERA = 30;
        final int MAX_HOGUERAS = 3;        // veces que se puede descansar en toda la partida
        final int DAÑO_CONTRAGOLPE = 15;    // daño que haces al monstruo cuando te defiendes

        // ---------- DATOS DEL CAZADOR ----------
        int vida = VIDA_MAXIMA;
        int pociones = 3;
        int esquirlas = 50 ;
        int hogueras = 0;                  // hogueras ya usadas
        int armaEquipada = 0;              // posicion del arma en los arrays de armas

        // ---------- ARRAYS DE ARMAS ----------
        String[] armas = {"Dagas dobles", "Espada corta", "Hacha de caza", "Maza con pinchos"};
        int[] dañoArmas = {27, 35, 43, 60};
        int[] preciosArmas = {0, 30, 70, 100};
        boolean[] armasCompradas = {true, false, false, false};

        // ---------- ARRAYS DE MONSTRUOS ----------
        String[] monstruos = {"Esqueleto", "Perro de huesos", "Guerrero oseo", "Bestia del osario", "Rey de los huesos"};
        int[] vidasMonstruos = {50, 65, 78, 89, 150};
        int[] ataquesMonstruos = {19, 25, 30, 35, 42};
        String[] historias = {
            "Un soldado caido que aun recuerda como empuñar su lanza.",
            "Los perros del antiguo cazador, hechos de costillas y rabia.",
            "Un caballero de la guardia, fiel a un rey que ya no existe.",
            "Una masa de huesos fundidos que guarda la entrada al trono.",
            "El primer cazador de Ossaria. Se corono con los huesos de todos los que lo desafiaron."
        };

        int monstruoActual = 0;
        boolean juegoTerminado = false;

        // ---------- HISTORIA INICIAL ----------
        System.out.println("================================");
        System.out.println("          BONEHUNTING");
        System.out.println("================================");
        System.out.println("Hace siglos, la ciudad de Ossaria enterro a sus muertos");
        System.out.println("bajo la colina. Una noche sin luna, los huesos despertaron.");
        System.out.println("Ahora eres el ultimo cazador que se atreve a bajar al osario.");
        System.out.println("Derrota a los cinco monstruos y acaba con el Rey de los huesos.");

        while (!juegoTerminado) { //menu principal 
            System.out.println("\n--- MENU PRINCIPAL ---");
            System.out.println("1. Cazar un monstruo");
            System.out.println("2. Usar una pocion");
            System.out.println("3. Ver mis datos");
            System.out.println("4. Ver monstruos pendientes");
            System.out.println("5. Descansar junto a una hoguera (" + (MAX_HOGUERAS - hogueras) + " restantes)");
            System.out.println("6. Tienda");
            System.out.println("7. Cambiar de arma");
            System.out.println("8. Salir del juego");
            System.out.print("Elige una opcion: ");

            // Leer un numero sin que el programa se rompa si escriben letras.
            int opcion;
            if (teclado.hasNextInt()) { 
                opcion = teclado.nextInt();
            } else {
                teclado.next(); 
                opcion = -1;
            }

            if (opcion == 1) {
                // ---------- CAZAR ----------
                if (monstruoActual >= monstruos.length) {
                    System.out.println("Ya has derrotado a todos los monstruos.");
                } else {
                    int vidaMonstruo = vidasMonstruos[monstruoActual];
                    int ataqueMonstruo = ataquesMonstruos[monstruoActual];

                    System.out.println("\nAparece " + monstruos[monstruoActual] + "!");
                    System.out.println(historias[monstruoActual]);

                    while (vida > 0 && vidaMonstruo > 0) {
                        System.out.println("\nTu vida: " + vida + "/" + VIDA_MAXIMA + "   Pociones: " + pociones);
                        System.out.println(monstruos[monstruoActual] + ": " + vidaMonstruo + " de vida");
                        System.out.println("1. Atacar con " + armas[armaEquipada] + " (" + dañoArmas[armaEquipada] + " de daño)");
                        System.out.println("2. Defenderse (recibes la mitad de daño y contraatacas)");
                        System.out.println("3. Usar pocion");
                        System.out.print("Elige una accion: ");

                        int accion;
                        if (teclado.hasNextInt()) {
                            accion = teclado.nextInt();
                        } else {
                            teclado.next();
                            accion = -1;
                        }

                        int dañoRecibido = ataqueMonstruo;
                        // Si la accion no es valida, no se gasta el turno y el monstruo no ataca
                        boolean turnoGastado = true;

                        if (accion == 1) {
                            int dañoJugador = dañoArmas[armaEquipada];
                            vidaMonstruo = vidaMonstruo - dañoJugador;
                            System.out.println("Atacas e infliges " + dañoJugador + " de daño.");
                        } else if (accion == 2) {
                            dañoRecibido = ataqueMonstruo / 2;
                            vidaMonstruo = vidaMonstruo - DAÑO_CONTRAGOLPE;
                            System.out.println("Te proteges y contraatacas con " + DAÑO_CONTRAGOLPE + " de daño.");
                        } else if (accion == 3) {
                            if (pociones == 0) {
                                System.out.println("No te quedan pociones.");
                                turnoGastado = false;
                            } else if (vida == VIDA_MAXIMA) {
                                System.out.println("Ya tienes la vida al maximo.");
                                turnoGastado = false;
                            } else {
                                vida = vida + CURACION_POCION;
                                if (vida > VIDA_MAXIMA) {
                                    vida = VIDA_MAXIMA;
                                }
                                pociones--;
                                System.out.println("Bebes una pocion. Tu vida ahora es " + vida + ".");
                            }
                        } else {
                            System.out.println("Accion incorrecta. Elige 1, 2 o 3.");
                            turnoGastado = false;
                        }

                        if (vidaMonstruo < 0) {
                            vidaMonstruo = 0;
                        }

                        // El monstruo solo ataca si el turno se ha gastado y sigue vivo
                        if (turnoGastado && vidaMonstruo > 0) {
                            vida = vida - dañoRecibido;
                            System.out.println(monstruos[monstruoActual] + " te hace " + dañoRecibido + " de daño.");
                        }

                        if (vida < 0) {
                            vida = 0;
                        }
                    }

                    if (vida <= 0) {
                        System.out.println("\nHas caido en el osario. Tus huesos se unen a los demas.");
                        System.out.println("GAME OVER.");
                        juegoTerminado = true;
                    } else {
                        int recompensa = 20 * (monstruoActual + 1);
                        esquirlas = esquirlas + recompensa;
                        System.out.println("\nHas derrotado a " + monstruos[monstruoActual] + "!");
                        System.out.println("Consigues " + recompensa + " esquirlas.");
                        monstruoActual++;

                        if (monstruoActual == monstruos.length) {
                            System.out.println("\nEl Rey de los huesos se desmorona. El osario por fin descansa.");
                            System.out.println("HAS COMPLETADO BONEHUNTING!");
                            System.out.println("Esquirlas finales: " + esquirlas);
                            juegoTerminado = true;
                        }
                    }
                }

            } else if (opcion == 2) {
                // ---------- USAR POCION ----------
                if (pociones > 0) {
                    if (vida == VIDA_MAXIMA) {
                        System.out.println("Ya tienes la vida al maximo.");
                    } else {
                        vida = vida + CURACION_POCION;
                        if (vida > VIDA_MAXIMA) {
                            vida = VIDA_MAXIMA;
                        }
                        pociones--;
                        System.out.println("Usas una pocion. Tu vida ahora es " + vida + ".");
                        System.out.println("Pociones restantes: " + pociones);
                    }
                } else {
                    System.out.println("No te quedan pociones.");
                }

            } else if (opcion == 3) {
                // ---------- VER DATOS ----------
                System.out.println("\n--- DATOS DEL CAZADOR ---");
                System.out.println("Vida: " + vida + "/" + VIDA_MAXIMA);
                System.out.println("Pociones: " + pociones + "/" + MAX_POCIONES);
                System.out.println("Esquirlas: " + esquirlas);
                System.out.println("Arma: " + armas[armaEquipada] + " (" + dañoArmas[armaEquipada] + " de daño)");
                System.out.println("Hogueras restantes: " + (MAX_HOGUERAS - hogueras));
                System.out.println("Monstruos derrotados: " + monstruoActual + "/" + monstruos.length);

            } else if (opcion == 4) {
                // ---------- MONSTRUOS PENDIENTES ----------
                System.out.println("\n--- MONSTRUOS PENDIENTES ---");
                if (monstruoActual >= monstruos.length) {
                    System.out.println("No quedan monstruos.");
                } else {
                    for (int i = monstruoActual; i < monstruos.length; i++) {
                        System.out.println((i + 1) + ". " + monstruos[i]
                                + " - Vida: " + vidasMonstruos[i]
                                + " - Ataque: " + ataquesMonstruos[i]);
                    }
                }

            } else if (opcion == 5) {
                // ---------- HOGUERA (limitada) ----------
                if (hogueras >= MAX_HOGUERAS) {
                    System.out.println("Ya no quedan hogueras encendidas en el osario.");
                } else if (vida == VIDA_MAXIMA) {
                    System.out.println("Estas en plena forma, no necesitas descansar.");
                } else {
                    int vidaAnterior = vida;
                    vida = vida + CURACION_HOGUERA;
                    if (vida > VIDA_MAXIMA) {
                        vida = VIDA_MAXIMA;
                    }
                    hogueras++;
                    System.out.println("Descansas junto a la hoguera.");
                    System.out.println("Recuperas " + (vida - vidaAnterior) + " puntos de vida.");
                    System.out.println("Vida actual: " + vida);
                    System.out.println("Hogueras restantes: " + (MAX_HOGUERAS - hogueras));
                }

            } else if (opcion == 6) {
                // ---------- TIENDA ----------
                boolean enTienda = true;
                System.out.println("\nUn mercader encapuchado te observa entre los huesos...");

                while (enTienda) {
                    System.out.println("\n--- TIENDA --- (Esquirlas: " + esquirlas + ")");
                    System.out.println("1. Comprar pocion (" + PRECIO_POCION + " esquirlas) - Llevas " + pociones + "/" + MAX_POCIONES);
                    System.out.println("2. Comprar arma");
                    System.out.println("3. Salir de la tienda");
                    System.out.print("Elige una opcion: ");

                    int opcionTienda;
                    if (teclado.hasNextInt()) {
                        opcionTienda = teclado.nextInt();
                    } else {
                        teclado.next();
                        opcionTienda = -1;
                    }

                    if (opcionTienda == 1) {
                        if (pociones >= MAX_POCIONES) {
                            System.out.println("No puedes llevar mas pociones.");
                        } else if (esquirlas < PRECIO_POCION) {
                            System.out.println("No tienes esquirlas suficientes.");
                        } else {
                            esquirlas = esquirlas - PRECIO_POCION;
                            pociones++;
                            System.out.println("Compras una pocion. Pociones: " + pociones);
                        }

                    } else if (opcionTienda == 2) {
                        System.out.println("\n--- ARMAS ---");
                        for (int i = 0; i < armas.length; i++) {
                            String estado = armasCompradas[i] ? "[COMPRADA]" : preciosArmas[i] + " esquirlas";
                            System.out.println((i + 1) + ". " + armas[i] + " - Daño: " + dañoArmas[i] + " - " + estado);
                        }
                        System.out.print("Que arma quieres (0 para volver): ");

                        int eleccion;
                        if (teclado.hasNextInt()) {
                            eleccion = teclado.nextInt();
                        } else {
                            teclado.next();
                            eleccion = -1;
                        }

                        if (eleccion == 0) {
                            System.out.println("Vuelves al mostrador.");
                        } else if (eleccion < 1 || eleccion > armas.length) {
                            System.out.println("Esa arma no existe.");
                        } else if (armasCompradas[eleccion - 1]) {
                            System.out.println("Ya tienes esa arma.");
                        } else if (esquirlas < preciosArmas[eleccion - 1]) {
                            System.out.println("No tienes esquirlas suficientes.");
                        } else {
                            esquirlas = esquirlas - preciosArmas[eleccion - 1];
                            armasCompradas[eleccion - 1] = true;
                            armaEquipada = eleccion - 1;
                            System.out.println("Compras y equipas: " + armas[armaEquipada] + ".");
                        }

                    } else if (opcionTienda == 3) {
                        System.out.println("Sales de la tienda.");
                        enTienda = false;
                    } else {
                        System.out.println("Opcion incorrecta. Elige un numero del 1 al 3.");
                    }
                }

            } else if (opcion == 7) {
                // ---------- CAMBIAR ARMA ----------
                System.out.println("\n--- TUS ARMAS ---");
                for (int i = 0; i < armas.length; i++) {
                    if (armasCompradas[i]) {
                        String marca = (i == armaEquipada) ? " (equipada)" : "";
                        System.out.println((i + 1) + ". " + armas[i] + " - Daño: " + dañoArmas[i] + marca);
                    }
                }
                System.out.print("Que arma quieres equipar (0 para volver): ");

                int eleccion;
                if (teclado.hasNextInt()) {
                    eleccion = teclado.nextInt();
                } else {
                    teclado.next();
                    eleccion = -1;
                }

                if (eleccion == 0) {
                    System.out.println("No cambias de arma.");
                } else if (eleccion < 1 || eleccion > armas.length || !armasCompradas[eleccion - 1]) {
                    System.out.println("No tienes esa arma.");
                } else {
                    armaEquipada = eleccion - 1;
                    System.out.println("Equipas: " + armas[armaEquipada] + ".");
                }

            } else if (opcion == 8) {
                System.out.println("Abandonas el osario. Hasta la proxima, cazador.");
                juegoTerminado = true;

            } else {
                System.out.println("Opcion incorrecta. Elige un numero del 1 al 8.");
            }
        }

        teclado.close();
        System.out.println("Fin del juego.");
    }
}
