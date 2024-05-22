package juego;

import java.awt.Color;

import entorno.Entorno;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego {

	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;
	Bartolome bart;
	Piso[] p;
	Bala bala;
	Tiranosaurio enemigo1;
	Tiranosaurio enemigo2;
	Tiranosaurio enemigo3;
	Tiranosaurio enemigo4;
	Tiranosaurio enemigo5;
	
//	Fondo fondo;
	

	public Juego() {
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Prueba del Entorno", 800, 600);
//		fondo = new Fondo(400,300);
		bart = new Bartolome(300, 550);
		enemigo1 = new Tiranosaurio(750, 73);
		enemigo2 = new Tiranosaurio(500, 190);
		enemigo3 = new Tiranosaurio(400, 310);
		enemigo4 = new Tiranosaurio(250, 430);
		enemigo5 = new Tiranosaurio(100, 550);

		
//		for(int i = 0; i <=5; i++) {
//			enemigo[i] = new Tiranosaurio(120 + i * (entorno.alto() / enemigo.length));
//		}
		
		p = new Piso[5];	
		
		for(int i = 0; i < p.length; i++) {
			p[i] = new Piso(120 + i * (entorno.alto() / p.length));
		}
		

		// Mensajes de depuración
        System.out.println("Juego iniciado");

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
		
//		// Mostrar y mover al enemigo
		enemigo1.mostrar(entorno);
		enemigo1.movVertical();
		
		enemigo2.mostrar(entorno);
		enemigo2.movVertical();
		
		
		enemigo3.mostrar(entorno);
		enemigo3.movVertical();
		
		
		enemigo4.mostrar(entorno);
		enemigo4.movVertical();
		
		
		enemigo5.mostrar(entorno);
		enemigo5.movVertical();

	    // Mostrar y caer Bartolome
	    bart.mostrar(entorno);
		bart.movVertical();
		
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
		
		if(detectarApoyo(bart, p)) {
			bart.momentum = 0;
			bart.estaApoyado = true;
		}
		else {
			bart.estaApoyado = false;
		}
		
		if(detectarColision (bart, p)) {
			System.out.println("Colision encontrada");
			bart.estaSaltando = false;
			bart.contadorSalto = 0;
		}
		if(detectarPared(bart, p)){
			System.out.println("bloque");
			bart.momentum=0;
		}
		else {
			System.out.println("libre que");
			bart.puedeMoverse=true;
		}
	}
	
//	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}
	
	public boolean detectarApoyo(Bartolome ba, Bloque bl) {
		return Math.abs((ba.getPiso() - bl.getTecho())) < 2 && 
				(ba.getIzquierdo() < (bl.getDerecho())) &&
				(ba.getDerecho() > (bl.getIzquierdo()));		
	}

	
	public boolean detectarApoyo(Bartolome ba, Piso pi) {
		for(int i = 0; i < pi.bloques.length; i++) {
			if(pi.bloques[i] != null && detectarApoyo(ba, pi.bloques[i])) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean detectarApoyo(Bartolome ba, Piso[] pisos) {
		for(int i = 0; i < pisos.length; i++) {
			if(detectarApoyo(ba, pisos[i])) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean detectarColision(Bartolome ba, Bloque bl) {
		return Math.abs((ba.getTecho() - bl.getPiso())) < 3.5 && 
				(ba.getIzquierdo()+10 < (bl.getDerecho())) &&
				(ba.getDerecho() - 10> (bl.getIzquierdo()));		
	}
	
	public boolean detectarColision(Bartolome ba, Piso pi) {
		for(int i = 0; i < pi.bloques.length; i++) {
			if(pi.bloques[i] != null && detectarColision(ba, pi.bloques[i])) {
				if(pi.bloques[i].rompible) {
					pi.bloques[i] = null;
				}
				return true;
			}
		}
		
		return false;
	}
	
	public boolean detectarColision(Bartolome ba, Piso[] pisos) {
		for(int i = 0; i < pisos.length; i++) {
			if(detectarColision(ba, pisos[i])) {
				return true;
			}
		}
		
		return false;
	}
	public boolean detectarPared(Bartolome ba, Bloque bl) {
		return (ba.getDerecho() > bl.getIzquierdo() && ba.getIzquierdo() < bl.getDerecho()) &&
				(ba.getTecho()-5 >  bl.getPiso() && ba.getPiso()+5 < bl.getTecho());
			
				
	}
	public boolean detectarPared(Bartolome ba, Piso pi) {
		for(int i = 0; i < pi.bloques.length; i++) {
			if(pi.bloques[i]!= null && detectarPared(ba, pi.bloques[i])) {
				System.out.println(pi.y);
				return true;
			}
		}
		return false;
	}
	
	public boolean detectarPared(Bartolome ba, Piso[] pisos) {
		for (int i = 0; i < pisos.length; i++) {
			if(detectarPared(ba, pisos[i])) {
				System.out.println(i);
				return true;
			}
		}
		return false;
	}

	
}
