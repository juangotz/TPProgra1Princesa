package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class BalaEnemiga {
	double x, y, escala, alto, ancho, velocidad;
	boolean dir; // false = Izq
	Image spriteIzq;
	Image spriteDer;

	public BalaEnemiga(double x, double y, boolean direccion) {
		this.x = x;
		this.y = y;
		spriteIzq = Herramientas.cargarImagen("balaIzq.png");
		spriteDer = Herramientas.cargarImagen("bala.png");
		dir = direccion;
		escala = 0.1;
		alto = spriteIzq.getHeight(null) * escala;
		ancho = spriteIzq.getWidth(null) * escala;
		this.velocidad = 3;
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
	public boolean detectarBala(BalaEnemiga ble, Bala bl) {
		return (ble.getIzquierdo() < bl.getDerecho() && ble.getDerecho() > bl.getIzquierdo() &&
				ble.getTecho() < bl.getPiso() && ble.getPiso() > bl.getTecho()) && ble.dir!=bl.dir;
	}
	public boolean detectarPrincesa(BalaEnemiga ble, Princesa ba) {
		return (ble.getIzquierdo() < ba.getDerecho() && ble.getDerecho() > ba.getIzquierdo() &&
					ble.getTecho() < ba.getPiso() && ble.getPiso() > ba.getTecho());
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
