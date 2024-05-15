package juego;

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
	}

	public void moverse(boolean dirMov) {
		if (estaApoyado) {
			if (dirMov) {
				this.x += 1;
				this.momentum = 1;
			} else {
				this.x -= 1;
				this.momentum = -1;
			}
			this.dir = dirMov;
		}
	}

	public void movVertical() {
		if (!estaApoyado && !estaSaltando) {
			this.y += 1;
			if(this.momentum==1) {
				this.x += 1;
			}
			if(this.momentum==-1) {
				this.x -= 1;
			}
		}
		if(estaSaltando) {
			this.y -= 7;
			contadorSalto++;
			if(this.momentum==1) {
				this.x += 1;
			}
			if(this.momentum==-1) {
				this.x -= 1;
			}
		}
		if(contadorSalto == 20) {
			contadorSalto = 0;
			estaSaltando = false;
		}
		
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
