package juego;

import java.awt.Color;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego {

	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;
	Princesa bart;
	Piso[] p;
	Bala bala;
	Gato gato;
	Tiranosaurio[] enemigos;
	BalaEnemiga[] balas;
	Vida vida;
	boolean gatoRescatado; // True = Termina el juego
	boolean empezarJuego; // True = Empieza el juego
	private int puntaje;
	private int enemigosEliminados;
	private Image fondo;
    private double escalaFondo;
    public int contSubirPisos = 1;
	
	
	public Juego() {
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Super Elizabeth Sis. - VOLCANO EDITION", 800, 600); //crea el entorno
		fondo = Herramientas.cargarImagen("fondo3.gif");
		bart = new Princesa(300, 550); //crea al personaje
		p = new Piso[5]; //crea a los pisos
		enemigos = new Tiranosaurio[8]; //crea a los enemigos
		this.puntaje = 0; //inicializo la variable puntaje
		this.enemigosEliminados = 0; //inicializo la variable enemigosEliminados
		
		for(int i = 0; i < p.length; i++) {
			p[i] = new Piso(120 + i * (entorno.alto() / p.length));
			
		}
		int contE = 0;
		int contP = 0;
		
		while (contP < 4) {
			enemigos[contE++] = new Tiranosaurio ((Math.random()*400), p[contP].bloques[0].getTecho()-30);
			enemigos[contE++] = new Tiranosaurio (((Math.random()*400)+400), p[contP].bloques[0].getTecho()-30);
			contP++;
		}
		balas = new BalaEnemiga[enemigos.length];
		//escala para que la imagen ocupe todo el entorno
		double anchoEntorno = entorno.ancho();
        double altoEntorno = entorno.alto();
        double anchoImagen = fondo.getWidth(null);
        double altoImagen = fondo.getHeight(null);
        
        escalaFondo = Math.max(anchoEntorno / anchoImagen, altoEntorno / altoImagen);
		// Inicia el juego!
		this.entorno.iniciar();
	}
	/**
	 * Durante el juego, el método tick() será ejecutado en cada instante y 
	 * por lo tanto es el método más importante de esta clase. Aquí se debe 
	 * actualizar el estado interno del juego para simular el paso del tiempo 
	 * (ver el enunciado del TP para mayor detalle).
	 */
	public void tick() {
		entorno.dibujarImagen(fondo, entorno.ancho() / 2, entorno.alto() / 2, 0, escalaFondo);
		//Pantalla de inicio
		if (!empezarJuego) {
			entorno.cambiarFont("Arial", 40, Color.RED);
			entorno.escribirTexto("Super Elizabeth Sis.", 230, 250);
			entorno.cambiarFont("Arial", 44, Color.ORANGE);
			entorno.escribirTexto("VOLCANO EDITION.", 205, 300);
			entorno.cambiarFont("Arial", 26, Color.WHITE);
			entorno.escribirTexto("Apreta C para Iniciar.", 275, 350);
			if (entorno.estaPresionada('c')) {
				empezarJuego = true;
			}
		}
		//Juego
		if (!gatoRescatado && empezarJuego) {
			// Metodos Princesa
			if (bart!=null) {
				if(entorno.estaPresionada(entorno.TECLA_DERECHA) && !bart.detectarPared(bart, p)) {
					bart.moverse(true);
				}
				if(entorno.estaPresionada(entorno.TECLA_IZQUIERDA) && !bart.detectarPared(bart, p)) {
					bart.moverse(false);
				}
				if(bart.detectarApoyo(bart, p)) {
					bart.estaApoyado = true;
				}
				else {
					bart.estaApoyado = false;
				}
				bart.movVertical();
				if(bart.detectarColision (bart, p)) {
					bart.estaSaltando = false;
					bart.contadorSalto = 0;
				}
				// Disparo Princesa
				if(bala == null && entorno.estaPresionada(entorno.TECLA_ESPACIO)) {
					bala = new Bala(bart.x, bart.y, bart.dir);
				}
				// Salto Princesa
				if(entorno.estaPresionada('x') && bart.estaApoyado) {
					bart.estaSaltando = true;
				}
				// Invulnerabilidad
				if (bart.invul) {
					bart.invulCheck();
				}			
				// Muerte Princesa
				if(bart.vidas<0) {
					bart=null;
				}
			}
			//"Añadir" pisos/subir
			if (bart!=null && bart.y<5 && gato==null) {
				contSubirPisos++;
				refrescarPisos(contSubirPisos);
			}
			//Obtener vidas
			if (bart!=null && vida!=null && bart.DetectarVida(bart, vida)) {
				if (bart.vidas<3) {
					bart.vidas++;
				} else {
					puntaje+=15;
				}
				vida=null;
			}
			//Terminar Juego
			if(bart!=null && gato != null && bart.detectarGato(bart, gato)) {
				gatoRescatado = true;
			}
			
			// Metodos Bala && BalaEnemiga
			if(bala != null) {
				bala.moverse();
			}
			
			if( bala != null && (bala.x < -0.1 * entorno.ancho() 
			|| bala.x > entorno.ancho() * 1.1)) {
				bala = null;
				
			} // Metodos BalaEnemiga
			for (int i = 0; i < balas.length; i++) {
				if(balas[i] != null) {
					balas[i].moverse();
					if (bala !=null && balas[i].detectarBala(balas[i], bala)) {
						balas[i]=null;
						bala=null;
						puntaje +=5; //elimino balaEnemiga=5pts
					}
				} 
				// Detectar Princesa/Metodo Daño
				if (balas[i]!=null && bart!=null && balas[i].detectarPrincesa(balas[i], bart)) {
					if (!bart.invul) {
						bart.vidas--;
						if (bart.vidas!=0)
						bart.invul=true;
					}
					balas[i]=null;
				}
				if( balas[i] != null && (balas[i].x < -0.1 * entorno.ancho() 
				|| balas[i].x > entorno.ancho() * 1.1)) {
					balas[i] = null;
					
				}
			}
			// Metodos Enemigos
			for (int i = 0; i<enemigos.length;i++) {
				if(enemigos[i]!=null) {
					enemigos[i].movimiento();
					if (enemigos[i].disparo() & balas[i]==null) {
						balas[i] = new BalaEnemiga(enemigos[i].x, enemigos[i].y+7, enemigos[i].dir);
					}
					if (enemigos[i].detectarPared(enemigos[i], p)) {
						enemigos[i].changeDir(enemigos[i].dir);
					}
					if (enemigos[i].detectarApoyo(enemigos[i], p)) {
						enemigos[i].estaApoyado=true;
					} else {
						enemigos[i].estaApoyado=false;
					} 
					// Metodo muerte
					if (bala!=null && enemigos[i].detectarBala(enemigos[i], bala)) {
						if (Math.random() > 0.6 && vida==null) {
							vida = new Vida(enemigos[i].x,enemigos[i].y);
						}
						enemigos[i]=null;
						bala=null;
						if(balas[i]!=null) {
							balas[i]=null;
						}
						puntaje +=10; // elimino enemigo=10pts
						enemigosEliminados +=1;
					}
				} // Detectar Princesa/Metodo Daño
				if (enemigos[i]!=null && bart!=null && 
						enemigos[i].detectarPrincesa(enemigos[i], bart) && !bart.invul) {
					bart.vidas--;
					if (bart.vidas!=0)
					bart.invul=true;
				}
			}
			enemigosEnPantalla(enemigos);
			// Metodos Vida
			if (vida!=null) {
				vida.caer();
				if (vida.detectarApoyo(vida, p)) {
					vida.estaApoyado=true;
				} if (!vida.detectarApoyo(vida, p)) {
					vida.estaApoyado=false;
				}
				if (vida.timeOnScreen()) { //Despues de 210 ticks desaparece.
					vida=null;
				}
			}
			// Metodos mostrar()
			if (bart!=null) {
				bart.mostrar(entorno);
				entorno.cambiarFont("Arial", 14, Color.ORANGE);
				entorno.cambiarFont("Arial", 14, Color.ORANGE);
				entorno.escribirTexto("Nivel: " + contSubirPisos, 0, 10);
				if (bart.vidas==3) {
					entorno.escribirTexto("Vidas: " + bart.vidas + " (MAX)", 0, 25);
				} else {
					entorno.escribirTexto("Vidas: " + bart.vidas, 0, 25);
				}
				entorno.cambiarFont("Arial", 14, Color.ORANGE);
				entorno.escribirTexto("Puntaje: " + puntaje, 0, 40);
				entorno.cambiarFont("Arial", 14, Color.ORANGE);
				entorno.escribirTexto("Enemigos Asesinados: " + enemigosEliminados, 0, 55);
			}
			if (gato!=null) {
				gato.mostrar(entorno);
			}
			for(int i = 0; i < p.length; i++) {
				p[i].mostrar(entorno);
			}
			for (int i = 0; i<enemigos.length;i++) {
				if(enemigos[i]!=null) {
					enemigos[i].mostrar(entorno);
				}
			}
			for (int i = 0; i<balas.length;i++) {
				if(balas[i]!=null) {
					balas[i].mostrar(entorno);
				}
			}
			if (bala!=null) {
				bala.mostrar(entorno);
			} 
			if (vida!=null) {
				vida.mostrar(entorno);
			} // Pantalla para cuando Princesa Muera
			if (bart==null) {
				entorno.cambiarFont("Arial", 26, Color.ORANGE);
				entorno.escribirTexto("Puntaje: " + puntaje, 210, 400);
				entorno.cambiarFont("Arial", 26, Color.ORANGE);
				entorno.escribirTexto("Enemigos eliminados: " + enemigosEliminados, 360, 400);
				entorno.cambiarFont("Arial", 34, Color.ORANGE);
				entorno.escribirTexto("M o r i s t e !", 310, 300);
				entorno.cambiarFont("Arial", 26, Color.WHITE);
				entorno.escribirTexto("Apreta C para reiniciar.", 270, 350);
				if (entorno.estaPresionada('c')) {
					reiniciarJuego();
				}
			}
		}
		if (gatoRescatado) { //Ganar el juego / Rescatar al gato.
			bart=null;
			p=null;
			enemigos=null;
			balas=null;
			gato=null;
			entorno.cambiarFont("Arial", 34, Color.YELLOW);
			entorno.escribirTexto("Ganaste!", 310, 300);
			entorno.cambiarFont("Arial", 26, Color.GREEN);
			entorno.escribirTexto("Puntaje: " + puntaje, 210, 400);
			entorno.cambiarFont("Arial", 26, Color.GREEN);
			entorno.escribirTexto("Enemigos eliminados: " + enemigosEliminados, 360, 400);
			entorno.cambiarFont("Arial", 26, Color.WHITE);
			entorno.escribirTexto("Apreta C para reiniciar.", 270, 350);
			if (entorno.estaPresionada('c')) {
				reiniciarJuego();
			}
		}
	}
	public void enemigosEnPantalla(Tiranosaurio[] lista) { // para ver cuantos enemigos hay en pantalla y spawnearlos si <2.
		int cont = 0;
		for (int i = 0; i<lista.length; i++) {
			if(lista[i]==null) {
				cont++;
				if(cont>6) {
					lista[i] = new Tiranosaurio(10, -10);
					if (Math.random() > 0.7 && contSubirPisos>1)
						enemigos[i].puedeDisparar=false;
				}
				if(cont>6) {
					lista[i] = new Tiranosaurio(10, -10);
					if (Math.random() > 0.8 && contSubirPisos>1)
						enemigos[i].puedeDisparar=false;
				}
			}
		}
	}
	public void reiniciarJuego() { //reiniciar en caso de que muera
		bart = new Princesa(300, 550);
		p = new Piso[5];
		enemigos = new Tiranosaurio[8];
		for(int i = 0; i < p.length; i++) {
			p[i] = new Piso(120 + i * (entorno.alto() / p.length));
		}
		int contE = 0;
		int contP = 0;
		
		while (contP < 4) {
			enemigos[contE++] = new Tiranosaurio ((Math.random()*400), p[contP].bloques[0].getTecho()-30);
			enemigos[contE++] = new Tiranosaurio (((Math.random()*400)+400), p[contP].bloques[0].getTecho()-30);
			contP++;
		}
		balas = new BalaEnemiga[enemigos.length];
		gatoRescatado = false;
		this.puntaje = 0; //reinicio la variable puntaje
		this.enemigosEliminados = 0;
		contSubirPisos = 1;
		vida = null;
	}
	public void refrescarPisos(int contador) { // Si la princesa sube de piso, crear nuevo nivel.
			for(int i = 0; i < p.length; i++) {
				p[i] = new Piso(120 + i * (entorno.alto() / p.length));
			}
			int contE = 0;
			int contP = 0;
			
			while (contP < 4) {
				enemigos[contE] = new Tiranosaurio ((Math.random()*400), p[contP].bloques[0].getTecho()-30);
				if (Math.random() > 0.6) // Generar distinto tipo de enemigo.
					enemigos[contE].puedeDisparar=false;
				contE++;
				enemigos[contE] = new Tiranosaurio (((Math.random()*400)+400), p[contP].bloques[0].getTecho()-30);
				if (Math.random() > 0.6) // Generar distinto tipo de enemigo.
					enemigos[contE].puedeDisparar=false;
				contE++;
				contP++;
			}
			balas = new BalaEnemiga[enemigos.length];
			if (contador==4) {
				gato = new Gato ((Math.random()*700), p[0].bloques[0].getTecho()-15);
				gato.detectarApoyo(gato, p); // Para que no pueda romper el bloque en el que esta el gato. 
			}
			bart.y = 600;
			vida = null;
	}
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}
	

}