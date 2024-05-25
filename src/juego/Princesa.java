package juego;

import java.awt.Color;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Princesa {
	double x, y;
	Image [] sprites;
	boolean dir; // false = Izq
	boolean estaApoyado;
	boolean estaSaltando; //false = no esta saltando
	boolean invul; //true = no recibe daño enemigo
	double escala;
	double alto;
	double ancho;
	int contadorSalto;
	int contadorInvul;
	int vidas;

	public Princesa(double x, double y) {
		this.x = x;
		this.y = y;
		sprites = new Image[4];
		sprites[0] = Herramientas.cargarImagen("pr_idle.png");
		sprites[1] = Herramientas.cargarImagen("pr_idleLeft.png");
		sprites[2] = Herramientas.cargarImagen("pr_jump.png");
		sprites[3] = Herramientas.cargarImagen("pr_jumpLeft.png");
		dir = false;
		contadorSalto = 0;
		contadorInvul = 0;
		estaApoyado = false;
		estaSaltando = false;
		invul = false;
		escala = 0.175;
		alto = sprites[0].getHeight(null) * escala;
		ancho = sprites[0].getWidth(null) * escala;
		vidas = 2;
	}

	public void mostrar(Entorno e) {
		if (!estaApoyado) {
			if (dir) {
				e.dibujarImagen(sprites[2], x, y, 0, escala);
			} else {
				e.dibujarImagen(sprites[3], x, y, 0, escala);
			}
		} else {
			if (dir) {
				e.dibujarImagen(sprites[0], x, y, 0, escala);
			} else {
				e.dibujarImagen(sprites[1], x, y, 0, escala);
			}
		}
		if (this.invul) {
			e.dibujarCirculo(x, y, 30, Color.CYAN);
		}
	}

	public void moverse(boolean dirMov) { // -x == izq, +x == der
			if (dirMov) {
				if (this.x < 800) {
					this.x += 2;
				}
			} else if (this.x > 0){
				this.x -= 2;
			}
			this.dir = dirMov;
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
	public void invulCheck() { // cuenta ticks para darle 1 sec de invulnerabilidad.
		contadorInvul++;
		if (contadorInvul>=60) {
			invul=false;
		}
	}
	public boolean detectarGato (Princesa ba, Gato cat) {
		return (ba.getIzquierdo() < cat.getDerecho() && ba.getDerecho() > cat.getIzquierdo() &&
				ba.getTecho() < cat.getPiso() && ba.getPiso() > cat.getTecho());
	}
	public boolean detectarApoyo(Princesa ba, Bloque bl) {
		return Math.abs((ba.getPiso() - bl.getTecho())) < 2 && 
				(ba.getIzquierdo() < (bl.getDerecho())) &&
				(ba.getDerecho() > (bl.getIzquierdo()));		
	}

	
	public boolean detectarApoyo(Princesa ba, Piso pi) {
		for(int i = 0; i < pi.bloques.length; i++) {
			if(pi.bloques[i] != null && detectarApoyo(ba, pi.bloques[i])) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean detectarApoyo(Princesa ba, Piso[] pisos) {
		for(int i = 0; i < pisos.length; i++) {
			if(detectarApoyo(ba, pisos[i])) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean detectarColision(Princesa ba, Bloque bl) {
		return Math.abs((ba.getTecho() - bl.getPiso())) < 3.5 && 
				(ba.getIzquierdo() < (bl.getDerecho())) &&
				(ba.getDerecho() > (bl.getIzquierdo()));		
	}
	
	public boolean detectarColision(Princesa ba, Piso pi) {
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
	
	public boolean detectarColision(Princesa ba, Piso[] pisos) {
		for(int i = 0; i < pisos.length; i++) {
			if(detectarColision(ba, pisos[i])) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean DetectarPared(Princesa ba, Bloque bl) {
		return (ba.getIzquierdo() < bl.getDerecho() && ba.getDerecho() > bl.getIzquierdo() &&
			    ba.getTecho() < bl.getPiso() && ba.getPiso() > bl.getTecho()); 
	}
	public boolean detectarPared(Princesa ba, Piso pi) {
		for(int i = 0; i < pi.bloques.length; i++) {
			if(pi.bloques[i]!= null && DetectarPared(ba, pi.bloques[i])) {
				return true;
			}
		}
		return false;
	}
	
	public boolean detectarPared(Princesa ba, Piso[] pisos) {
		for (int i = 0; i < pisos.length; i++) {
			if(detectarPared(ba, pisos[i])) {
				return true;
			}
		}
		return false;
	}
	public boolean DetectarGato(Princesa ba, Gato cat) {
		return (ba.getIzquierdo() < cat.getDerecho() && ba.getDerecho() > cat.getIzquierdo() &&
			    ba.getTecho() < cat.getPiso() && ba.getPiso() > cat.getTecho()); 
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
