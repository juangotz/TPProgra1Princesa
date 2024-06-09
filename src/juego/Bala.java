package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Bala {
	private double x, y, escala, alto, ancho, velocidad;
	private boolean dir; // false = Izq
	private Image spriteIzq;
	private Image spriteDer;

	public Bala(double x, double y, boolean direccion) {
		this.x = x;
		this.y = y;
		spriteIzq = Herramientas.cargarImagen("balaIzq.png");
		spriteDer = Herramientas.cargarImagen("bala.png");
		dir = direccion;
		escala = 0.1;
		alto = spriteIzq.getHeight(null) * escala;
		ancho = spriteIzq.getWidth(null) * escala;
		this.velocidad = 5;
	}

	public void mostrar(Entorno e) {
		if (dir) {
			e.dibujarImagen(spriteDer, x, y, 0, escala);
		} else {
			e.dibujarImagen(spriteIzq, x, y, 0, escala);
		}
	}

	public void moverse() {
		if (this.dir) {
			this.x += velocidad;
		} else {
			this.x -= velocidad;
		}
	}
	
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public boolean isDir() {
		return dir;
	}

	public void setDir(boolean dir) {
		this.dir = dir;
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
