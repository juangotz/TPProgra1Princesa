package juego;

import entorno.Entorno;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego {

	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;
	Bartolome bart;
	Piso[] p;
	Bala bala;
	
	
	
	public Juego() {
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Prueba del Entorno", 800, 600);
		bart = new Bartolome(300, 550);
		p = new Piso[5];
		
		
		for(int i = 0; i < p.length; i++) {
			p[i] = new Piso(120 + i * (entorno.alto() / p.length));
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
		if(entorno.estaPresionada(entorno.TECLA_DERECHA)) {
			bart.moverse(true);
		}
		if(entorno.estaPresionada(entorno.TECLA_IZQUIERDA)) {
			bart.moverse(false);
		}
		if(bala == null && entorno.estaPresionada(entorno.TECLA_ESPACIO)) {
			bala = new Bala(bart.x, bart.y, bart.dir);
		}
		if(entorno.estaPresionada('x') && bart.estaApoyado) {
			bart.estaSaltando = true;
		}
		
		
		bart.mostrar(entorno);
		bart.movVertical();
		if (!bart.estaApoyado && !bart.estaSaltando && (bart.detectarPared(bart, p))) {
			bart.momentum=0;
		}

		if(bala != null) {
			bala.mostrar(entorno);
			bala.moverse();
		}
		
		for(int i = 0; i < p.length; i++) {
			p[i].mostrar(entorno);
		}
		
		if( bala != null && (bala.x < -0.1 * entorno.ancho() 
		|| bala.x > entorno.ancho() * 1.1)) {
			bala = null;
			
		}
		
		if(bart.detectarApoyo(bart, p)) {
			bart.momentum = 0;
			bart.estaApoyado = true;
		}
		else {
			bart.estaApoyado = false;
		}
		
		if(bart.detectarColision (bart, p)) {
			bart.estaSaltando = false;
			bart.contadorSalto = 0;
		}
//		if(bart.detectarParedDer(bart, p) || bart.detectarParedIzq(bart, p)) {
//			System.out.println("PARED DETECTADA");
//		}
	}
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}
	

}
