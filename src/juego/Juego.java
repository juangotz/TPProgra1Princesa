package juego;

import java.awt.Color;

import entorno.Entorno;
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
	boolean gatoRescatado;
	int timer = 0; // para debugging
	
	
	public Juego() {
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Prueba del Entorno", 800, 600);
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
		gato = new Gato ((Math.random()*700), p[0].bloques[0].getTecho()-15);
		gato.detectarApoyo(gato, p); // para que los bloques que esten abajo no se puedan romper
		gatoRescatado = false;
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
		if (!gatoRescatado) {
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
				if(bart.vidas==0) {
					bart=null;
				}
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
				
			}
			for (int i = 0; i < balas.length; i++) {
				if(balas[i] != null) {
					balas[i].moverse();
					if (bala !=null && balas[i].detectarBala(balas[i], bala)) {
						balas[i]=null;
						bala=null;
					}
				}
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
					if (enemigos[i].disparo(balas[i])) {
						balas[i] = new BalaEnemiga(enemigos[i].x, enemigos[i].y-10, enemigos[i].dir);
					}
					if (enemigos[i].detectarPared(enemigos[i], p)) {
						enemigos[i].changeDir(enemigos[i].dir);
					}
					if (enemigos[i].detectarApoyo(enemigos[i], p)) {
						enemigos[i].estaApoyado=true;
					} else {
						enemigos[i].estaApoyado=false;
					}
					if (bala!=null && enemigos[i].detectarBala(enemigos[i], bala)) {
						enemigos[i]=null;
						bala=null;
						if(balas[i]!=null) {
							balas[i]=null;
						}
					}
				}
				if (enemigos[i]!=null && bart!=null && 
						enemigos[i].detectarPrincesa(enemigos[i], bart) && !bart.invul) {
					bart.vidas--;
					if (bart.vidas!=0)
					bart.invul=true;
				}
			}
			enemigosEnPantalla(enemigos);
			// Metodos mostrar()
			if (bart!=null) {
				bart.mostrar(entorno);
			}
			gato.mostrar(entorno);
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
			} // Pantalla para cuando Princesa Muera
			if (bart==null) {
				entorno.cambiarFont("Arial", 26, Color.RED);
				entorno.escribirTexto("Moriste!", 340, 300);
				entorno.cambiarFont("Arial", 26, Color.WHITE);
				entorno.escribirTexto("Apreta C para reiniciar.", 270, 350);
				if (entorno.estaPresionada('c')) {
					reiniciarJuego();
				}
			}
		}
		if (gatoRescatado) {
			bart=null;
			p=null;
			enemigos=null;
			balas=null;
			gato=null;
			entorno.cambiarFont("Arial", 26, Color.YELLOW);
			entorno.escribirTexto("Ganaste!", 340, 300);
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
				}
				if(cont>6) {
					lista[i] = new Tiranosaurio(10, -10);
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
		gato = new Gato ((Math.random()*700), p[0].bloques[0].getTecho()-15);
		gato.detectarApoyo(gato, p); // para que los bloques que esten abajo no se puedan romper
		gatoRescatado = false;
	}
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}
	

}
