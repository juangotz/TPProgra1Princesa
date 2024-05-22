package juego;

import entorno.Entorno;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego {

	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;
	Bartolome bart;
	Piso[] p;
	Bala bala;
	BalaEnemiga balaMala;
	int timer = 0;
	
	
	public Juego() {
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Prueba del Entorno", 800, 600);
		bart = new Bartolome(300, 550);
		p = new Piso[5];
		
		
		
		for(int i = 0; i < p.length; i++) {
			p[i] = new Piso(120 + i * (entorno.alto() / p.length));
			if(i==p.length-1) {
				p[i].enemigos=null;
			}
		}
		
		// Inicializar lo que haga falta para el juego
		// ...

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
		// Procesamiento de un instante de tiempo
		if(entorno.estaPresionada(entorno.TECLA_DERECHA) && !bart.detectarPared(bart, p)) {
			bart.moverse(true);
		}
		if(entorno.estaPresionada(entorno.TECLA_IZQUIERDA) && !bart.detectarPared(bart, p)) {
			bart.moverse(false);
		}
		if(bala == null && entorno.estaPresionada(entorno.TECLA_ESPACIO)) {
			bala = new Bala(bart.x, bart.y, bart.dir);
		}
		if(entorno.estaPresionada('x') && bart.estaApoyado) {
			bart.estaSaltando = true;
		}
		
		
		bart.movVertical();
		if(bala != null) {
			bala.mostrar(entorno);
			bala.moverse();
		}
		
		if( bala != null && (bala.x < -0.1 * entorno.ancho() 
		|| bala.x > entorno.ancho() * 1.1)) {
			bala = null;
			
		}
		
		if(balaMala != null) {
			balaMala.mostrar(entorno);
			balaMala.moverse();
		}
		
		if( balaMala != null && (balaMala.x < -0.1 * entorno.ancho() 
		|| balaMala.x > entorno.ancho() * 1.1)) {
			balaMala = null;
			
		}
		
		if(bart.detectarApoyo(bart, p)) {
			bart.estaApoyado = true;
		}
		else {
			bart.estaApoyado = false;
		}
		
		
		if(bart.detectarColision (bart, p)) {
			bart.estaSaltando = false;
			bart.contadorSalto = 0;
		}
		for(int i = 0; i < p.length; i++) {
			if (p[i].enemigos!=null) {
				p[i].enemigos.movimiento();
				p[i].enemigos.colision(p, bala);
				p[i].enemigos.comportamientoAgro(balaMala);
			}
		}
		bart.mostrar(entorno);
		for(int i = 0; i < p.length; i++) {
			p[i].mostrar(entorno);
			if (p[i].enemigos!=null) {
				p[i].enemigos.mostrar(entorno);
			}
		}
	}
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}
	

}
