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
	boolean gatoRescatado; // True = Termina el juego, false = el juego continua
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
				if(entorno.estaPresionada(entorno.TECLA_DERECHA) && !prpis_detectarPared(bart, p)) {
					bart.moverse(true);
				}
				if(entorno.estaPresionada(entorno.TECLA_IZQUIERDA) && !prpis_detectarPared(bart, p)) {
					bart.moverse(false);
				}
				if(prpis_detectarApoyo(bart, p)) {
					bart.setEstaApoyado(true);
				}
				else {
					bart.setEstaApoyado(false);
				}
				bart.movVertical();
				if(prpis_detectarColision (bart, p)) {
					bart.pararSalto();
				}
				// Disparo Princesa
				if(bala == null && entorno.estaPresionada(entorno.TECLA_ESPACIO)) {
					bala = new Bala(bart.getX(), bart.getY(), bart.isDir());
				}
				// Salto Princesa
				if(entorno.estaPresionada('x') && bart.isEstaApoyado()) {
					bart.setEstaSaltando(true);
				}
				// Invulnerabilidad
				if (bart.isInvul()==true) {
					bart.invulCheck();
				}			
				// Muerte Princesa
				if(bart.getVidas()<0) {
					bart=null;
				}
			}
			//"Añadir" pisos/subir
			if (bart!=null && bart.getY()<5 && gato==null) {
				contSubirPisos++;
				refrescarPisos(contSubirPisos);
			}
			//Obtener vidas
			if (bart!=null && vida!=null && prv_detectarColision(bart, vida)) {
				if (bart.getVidas()<3) {
					bart.setVidas(bart.getVidas()+1);
				} else {
					puntaje+=15;
				}
				vida=null;
			}
			//Terminar Juego
			if(bart!=null && gato != null && prga_detectarColision(bart, gato)) {
				gatoRescatado = true;
			}
			
			// Metodos Bala && BalaEnemiga
			if(bala != null) {
				bala.moverse();
			}
			
			if( bala != null && (bala.getX() < -0.1 * entorno.ancho() 
			|| bala.getX() > entorno.ancho() * 1.1)) {
				bala = null;
				
			} // Metodos BalaEnemiga
			for (int i = 0; i < balas.length; i++) {
				if(balas[i] != null) {
					balas[i].moverse();
					if (bala !=null && blebal_detectarColision(balas[i], bala)) {
						balas[i]=null;
						bala=null;
						puntaje +=5; //elimino balaEnemiga=5pts
					}
				} 
				// Detectar Princesa/Metodo Daño
				if (balas[i]!=null && bart!=null && blepr_detectarColision(balas[i], bart)) {
					if (bart.isInvul()==false) {
						bart.setVidas(bart.getVidas()-1);
						if (bart.getVidas()!=0)
						bart.setInvul(true);
					}
					balas[i]=null;
				}
				if( balas[i] != null && (balas[i].getX() < -0.1 * entorno.ancho() 
				|| balas[i].getX() > entorno.ancho() * 1.1)) {
					balas[i] = null;
					
				}
			}
			// Metodos Enemigos
			for (int i = 0; i<enemigos.length;i++) {
				if(enemigos[i]!=null) {
					enemigos[i].movimiento();
					if (enemigos[i].disparo() & balas[i]==null) {
						balas[i] = new BalaEnemiga(enemigos[i].getX(), enemigos[i].getY()+7, enemigos[i].isDir());
					}
					if (tlpis_detectarPared(enemigos[i], p)) {
						enemigos[i].changeDir(enemigos[i].isDir());
					}
					if (tlpis_detectarApoyo(enemigos[i], p)) {
						enemigos[i].setEstaApoyado(true);
					} else {
						enemigos[i].setEstaApoyado(false);
					} 
					// Metodo muerte
					if (bala!=null && tlbal_detectarColision(enemigos[i], bala)) {
						if (Math.random() > 0.6 && vida==null) {
							vida = new Vida(enemigos[i].getX(),enemigos[i].getY());
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
						tipr_detectarColision(enemigos[i], bart) && bart.isInvul()==false) {
					bart.setVidas(bart.getVidas()-1);
					if (bart.getVidas()!=0)
					bart.setInvul(true);
				}
			}
			enemigosEnPantalla(enemigos);
			// Metodos Vida
			if (vida!=null) {
				vida.caer();
				if (vida.detectarApoyo(vida, p)) {
					vida.setEstaApoyado(true);
				} if (!vida.detectarApoyo(vida, p)) {
					vida.setEstaApoyado(false);
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
				if (bart.getVidas()==3) {
					entorno.escribirTexto("Vidas: " + bart.getVidas() + " (MAX)", 0, 25);
				} else {
					entorno.escribirTexto("Vidas: " + bart.getVidas(), 0, 25);
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
	//Metodos colisión todas las clases

	public boolean prbl_detectarApoyo(Princesa ba, Bloque bl) {
		return Math.abs((ba.getPiso() - bl.getTecho())) < 2 && 
				(ba.getIzquierdo() < (bl.getDerecho())) &&
				(ba.getDerecho() > (bl.getIzquierdo()));		
	}
	public boolean prpi_detectarApoyo(Princesa ba, Piso pi) {
		for(int i = 0; i < pi.bloques.length; i++) {
			if(pi.bloques[i] != null && prbl_detectarApoyo(ba, pi.bloques[i])) {
				return true;
			}
		}
		
		return false;
	}
	public boolean prpis_detectarApoyo(Princesa ba, Piso[] pisos) {
		for(int i = 0; i < pisos.length; i++) {
			if(prpi_detectarApoyo(ba, pisos[i])) {
				return true;
			}
		}
		
		return false;
	}
	public boolean prbl_detectarColision(Princesa ba, Bloque bl) {
		return Math.abs((ba.getTecho() - bl.getPiso())) < 3.5 && 
				(ba.getIzquierdo() < (bl.getDerecho())) &&
				(ba.getDerecho() > (bl.getIzquierdo()));		
	}
	public boolean prpi_detectarColision(Princesa ba, Piso pi) {
		for(int i = 0; i < pi.bloques.length; i++) {
			if(pi.bloques[i] != null && prbl_detectarColision(ba, pi.bloques[i]) && ba.isEstaSaltando()) {
				if(pi.bloques[i].isRompible()) {
					pi.bloques[i] = null;
				}
				return true;
			}
		}
		
		return false;
	}
	public boolean prpis_detectarColision(Princesa ba, Piso[] pisos) {
		for(int i = 0; i < pisos.length; i++) {
			if(prpi_detectarColision(ba, pisos[i])) {
				return true;
			}
		}
		
		return false;
	}
	public boolean prbl_detectarPared(Princesa ba, Bloque bl) {
		return (ba.getIzquierdo() < bl.getDerecho() && ba.getDerecho() > bl.getIzquierdo() &&
			    ba.getTecho() < bl.getPiso() && ba.getPiso() > bl.getTecho()); 
	}
	public boolean prpi_detectarPared(Princesa ba, Piso pi) {
		for(int i = 0; i < pi.bloques.length; i++) {
			if(pi.bloques[i]!= null && prbl_detectarPared(ba, pi.bloques[i])) {
				return true;
			}
		}
		return false;
	}
	
	public boolean prpis_detectarPared(Princesa ba, Piso[] pisos) {
		for (int i = 0; i < pisos.length; i++) {
			if(prpi_detectarPared(ba, pisos[i])) {
				return true;
			}
		}
		return false;
	}
	public boolean prga_detectarColision(Princesa ba, Gato cat) {
		return (ba.getIzquierdo() < cat.getDerecho() && ba.getDerecho() > cat.getIzquierdo() &&
			    ba.getTecho() < cat.getPiso() && ba.getPiso() > cat.getTecho()); 
	}
	
	public boolean prv_detectarColision(Princesa ba, Vida v) {
		return (ba.getIzquierdo() < v.getDerecho() && ba.getDerecho() > v.getIzquierdo() &&
			    ba.getTecho() < v.getPiso() && ba.getPiso() > v.getTecho()); 
	}

	public boolean blepr_detectarColision(BalaEnemiga ble, Princesa ba) {
		return (ble.getIzquierdo() < ba.getDerecho() && ble.getDerecho() > ba.getIzquierdo() &&
					ble.getTecho() < ba.getPiso() && ble.getPiso() > ba.getTecho());
	}
	
	public boolean blebal_detectarColision(BalaEnemiga ble, Bala bal) {
		return (ble.getIzquierdo() < bal.getDerecho() && ble.getDerecho() > bal.getIzquierdo() &&
				ble.getTecho() < bal.getPiso() && ble.getPiso() > bal.getTecho());
	}
	public boolean tlbl_detectarApoyo(Tiranosaurio ti, Bloque bl) {
		return Math.abs((ti.getPiso() - bl.getTecho())) < 2 && 
				(ti.getIzquierdo() < (bl.getDerecho())) &&
				(ti.getDerecho() > (bl.getIzquierdo()));		
	}


	public boolean tlpi_detectarApoyo(Tiranosaurio ti, Piso pi) {
		for(int i = 0; i < pi.bloques.length; i++) {
			if(pi.bloques[i] != null && tlbl_detectarApoyo(ti, pi.bloques[i])) {
				return true;
			}
		}
		
		return false;
	}

	public boolean tlpis_detectarApoyo(Tiranosaurio ti, Piso[] pisos) {
		for(int i = 0; i < pisos.length; i++) {
			if(tlpi_detectarApoyo(ti , pisos[i])) {
				return true;
			}
		}
		
		return false;
	}
	public boolean tlbl_detectarPared(Tiranosaurio ti, Bloque bl) {
		return (ti.getIzquierdo() < bl.getDerecho() && ti.getDerecho() > bl.getIzquierdo() &&
				ti.getTecho() < bl.getPiso() && ti.getPiso() > bl.getTecho());
	}
	public boolean tlpi_detectarPared(Tiranosaurio ti, Piso pi) {
		for(int i = 0; i < pi.bloques.length; i++) {
			if(pi.bloques[i]!= null && tlbl_detectarPared(ti, pi.bloques[i])) {
				return true;
			}
		}
		return false;
	}

	public boolean tlpis_detectarPared(Tiranosaurio ti, Piso[] pisos) {
		for (int i = 0; i < pisos.length; i++) {
			if(tlpi_detectarPared(ti, pisos[i])) {
				return true;
			}
		}
		return false;
	}

	public boolean tlbal_detectarColision(Tiranosaurio ti, Bala bal) {
		return (ti.getIzquierdo() < bal.getDerecho() && ti.getDerecho() > bal.getIzquierdo() &&
				ti.getTecho() < bal.getPiso() && ti.getPiso() > bal.getTecho());
	}
	public boolean tipr_detectarColision(Tiranosaurio ti, Princesa ba) {
		return (ti.getIzquierdo() < ba.getDerecho() && ti.getDerecho() > ba.getIzquierdo() &&
				ti.getTecho() < ba.getPiso() && ti.getPiso() > ba.getTecho());
	}
	// Metodo para mantener siempre 2 enemigos en pantalla
	public void enemigosEnPantalla(Tiranosaurio[] lista) { // para ver cuantos enemigos hay en pantalla y spawnearlos si <2.
		int cont = 0;
		for (int i = 0; i<lista.length; i++) {
			if(lista[i]==null) {
				cont++;
				if(cont>6) {
					lista[i] = new Tiranosaurio(10, -10);
					if (Math.random() > 0.7 && contSubirPisos>1)
						enemigos[i].setPuedeDisparar(false);
				}
				if(cont>6) {
					lista[i] = new Tiranosaurio(10, -10);
					if (Math.random() > 0.8 && contSubirPisos>1)
						enemigos[i].setPuedeDisparar(false);
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
					enemigos[contE].setPuedeDisparar(false);
				contE++;
				enemigos[contE] = new Tiranosaurio (((Math.random()*400)+400), p[contP].bloques[0].getTecho()-30);
				if (Math.random() > 0.6) // Generar distinto tipo de enemigo.
					enemigos[contE].setPuedeDisparar(false);
				contE++;
				contP++;
			}
			balas = new BalaEnemiga[enemigos.length];
			if (contador==4) {
				gato = new Gato ((Math.random()*700), p[0].bloques[0].getTecho()-15);
				gato.detectarApoyo(gato, p); // Para que no pueda romper el bloque en el que esta el gato. 
			}
			bart.setY(600);
			vida = null;
	}
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}
	

}
