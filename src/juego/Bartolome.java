package juego;

import java.awt.Color;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Bartolome {
	double x, y;
	Image spriteIzq;
	Image spriteDer;
	boolean dir; // false = Izq
	boolean estaApoyado;
	boolean estaSaltando; //false = no esta saltando
	double escala;
	double alto;
	double ancho;
	int contadorSalto;
	int momentum;

	public Bartolome(double x, double y) {
		this.x = x;
		this.y = y;
		spriteIzq = Herramientas.cargarImagen("bodyIzq.png");
		spriteDer = Herramientas.cargarImagen("body.png");
		dir = false;
		contadorSalto = 0;
		momentum = 0;
		estaApoyado = false;
		estaSaltando = false;
		escala = 0.2;
		alto = spriteIzq.getHeight(null) * escala;
		ancho = spriteIzq.getWidth(null) * escala;
	}

	public void mostrar(Entorno e) {
		
		if (dir) {
			e.dibujarImagen(spriteDer, x, y, 0, escala);
		} else {
			e.dibujarImagen(spriteIzq, x, y, 0, escala);
		}
		e.dibujarRectangulo(x, y, ancho, 1, 0, Color.GREEN);
		e.dibujarRectangulo(x, y+alto/2, ancho, 1, 0, Color.CYAN);
		e.dibujarRectangulo(x, y-alto/2, ancho, 1, 0, Color.RED);
	}

	public void moverse(boolean dirMov) { // -x == izq, +x == der
//		if (estaApoyado) {
			if (dirMov) {
				if (this.x < 800) {
					this.x += 2;
					this.momentum = 1;	
				}
			} else if (this.x > 0){
				this.x -= 2;
				this.momentum = -1;
			}
			this.dir = dirMov;
//		}
	}

	public void movVertical() { // -x == subiendo, +x == bajando
		if (!estaApoyado && !estaSaltando) {
			this.y += 2;
		}

		if(estaSaltando) {
			this.y -= 7;
			contadorSalto++;
		}
		if(contadorSalto == 20) {
			contadorSalto = 0;
			estaSaltando = false;
		}
		
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
				(ba.getIzquierdo() < (bl.getDerecho())) &&
				(ba.getDerecho() > (bl.getIzquierdo()));		
	}
	
	public boolean detectarColision(Bartolome ba, Piso pi) {
		for(int i = 0; i < pi.bloques.length; i++) {
			if(pi.bloques[i] != null && detectarColision(ba, pi.bloques[i]) && ba.estaSaltando) {
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
	
	public boolean DetectarPared(Bartolome ba, Bloque bl) {
		return (ba.getIzquierdo() < bl.getDerecho() && ba.getDerecho() > bl.getIzquierdo() &&
			    ba.getTecho() < bl.getPiso() && ba.getPiso() > bl.getTecho()); 
	}
	public boolean detectarPared(Bartolome ba, Piso pi) {
		for(int i = 0; i < pi.bloques.length; i++) {
			if(pi.bloques[i]!= null && DetectarPared(ba, pi.bloques[i])) {
				return true;
			}
		}
		return false;
	}
	
	public boolean detectarPared(Bartolome ba, Piso[] pisos) {
		for (int i = 0; i < pisos.length; i++) {
			if(detectarPared(ba, pisos[i])) {
				return true;
			}
		}
		return false;
	}
	public double getTecho() {
		return y - alto / 2;
	}

	public double getPiso() {
		return y + alto / 2;
	}

	public double getDerecho() {
		return x + ancho / 2;
	}

	public double getIzquierdo() {
		return x - ancho / 2;
	}

}
